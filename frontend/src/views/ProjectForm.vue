<template>
  <div>
    <div>
      <h1>프로젝트 만들기</h1>
      <p>매치히어에서 내 프로젝트를 만들고 나의 성공시대 시작됐다!</p>
    </div>
    <div>
      <div v-for="(form, name, index) in formFields" :key="form.id">
        <h3 v-if="name == 'project'">프로젝트</h3>
        <h3 v-else-if="name == 'member'">구성원</h3>
        <div v-for="field in form" :key="field.id">
          <InputFormField
            v-if="field.type == 'string'"
            :field="field"
            v-model="field.value"
            @update:errors="handleUpdateErrors"
          />
          <div v-else>
            <p>{{ field.label }}</p>
            <div v-if="field.type == 'radio'">
              <div v-for="(state, index, key) in field.stateList" :key="key">
                <input
                  :type="field.type"
                  :id="field.idList[index]"
                  :value="state"
                  v-model="field.value"
                />
                <label :for="field.idList[index]">{{ state }}</label>
              </div>
            </div>
            <div v-else-if="field.type == 'file'">
              <!-- file 타입은 양방향 바인딩 할 수 없다. method로 구현 -->
              <input
                :type="field.type"
                accept="image/png, image/jpeg"
                @change="selectThumbnailImage"
              />
              <p>{{ field.value }}</p>
            </div>
            <div v-else-if="field.type == 'date'">
              <input :type="field.type" v-model="field.value" />
            </div>
            <div v-else-if="field.type == 'textarea'">
              <textarea
                :placeholder="field.placeholder"
                v-model="field.value"
                class="w-full h-32 border-2"
              ></textarea>
            </div>
            <div v-else-if="field.type == 'number'">
              <input
                :type="field.type"
                min="0"
                max="10"
                v-model="field.value"
              />
            </div>
          </div>
        </div>
        <hr v-if="index < Object.keys(formFields).length - 1" />
      </div>
    </div>
  </div>
</template>

<script>
import InputFormField from "@/components/common/InputFormField.vue"
import { ref } from "vue"
import { requiredValidator } from "@/libs/validator"

export default {
  name: "ProjectForm",
  components: {
    InputFormField,
  },
  setup() {
    const formFields = ref({
      project: {
        pjtName: {
          // handleValidate의 fieldKey에 들어가는 인자도 label
          // 브라우저에 랜더링되는 데이터도 label
          label: "pjtName",
          // label: "프로젝트 이름",
          type: "string",
          value: "",
          placeholder: "ex) 11월까지 진행하는 사이드 프로젝트",
          errors: {},
          validators: [requiredValidator],
        },
        pjtState: {
          label: "프로젝트 상태",
          type: "radio",
          idList: ["will", "ing", "done"],
          stateList: ["진행 예정", "진행 중", "완료"],
          value: "진행 예정",
          errors: {},
          validators: [],
        },
        ThumbnailImageFile: {
          label: "썸네일 이미지",
          type: "file",
          value: "",
          errors: {},
          validators: [],
        },
        // 자동완성, value 아마 리스트..? 일단 패스
        techStacks: {
          label: "techStacks",
          // label: "기술스택",
          type: "string",
          value: "",
          placeholder: "ex) Vue, Spring, MySQL",
          errors: {},
          validators: [],
        },
        schedule: {
          label: "schedule",
          // label: "일정",
          type: "string",
          value: "",
          placeholder: "ex) 주말 10시 - 18시 / 평일 논의",
          errors: {},
          validators: [],
        },
        dueDate: {
          label: "프로젝트 마감 예정일",
          type: "date",
          value: "",
          erros: {},
          validators: [],
        },
        introduction: {
          label: "소개",
          type: "textarea",
          value: "",
          placeholder: "해당 프로젝트에 대해 소개해주세요",
          errors: {},
          validators: [],
        },
        openScope: {
          label: "공개 범위",
          type: "radio",
          idList: ["all", "club", "project"],
          stateList: ["전체 공개", "클럽 멤버에게만 공개", "비공개"],
          value: "전체 공개",
          errors: {},
          validators: [],
        },
      },
      member: {
        recruitState: {
          label: "모집 상태",
          type: "radio",
          idList: ["rec-ing", "rec-done"],
          stateList: ["모집 중", "완료"],
          value: "모집 중",
          errors: {},
          validators: [],
        },
        developer: {
          label: "개발자",
          type: "number",
          value: 0,
          errors: {},
          validators: [],
        },
        designer: {
          label: "디자이너",
          type: "number",
          value: 0,
          errors: {},
          validators: [],
        },
        planner: {
          label: "기획자",
          type: "number",
          value: 0,
          errors: {},
          validators: [],
        },
      },
    })

    // 취소 버튼 누르면 기존에 선택된 것도 사라짐.
    const selectThumbnailImage = (event) => {
      if (event.target.files.length) {
        const file = event.target.files[0]
        formFields.value.project.ThumbnailImageFile.value =
          window.URL.createObjectURL(file)
      } else {
        formFields.value.project.ThumbnailImageFile.value = ""
      }
    }

    const handleUpdateErrors = (validateRes) => {
      const { fieldKey, status, type } = validateRes
      if (status) {
        delete formFields.value.project[fieldKey].errors[type]
      } else {
        formFields.value.project[fieldKey].errors[type] = validateRes.message
      }
    }

    return {
      formFields,
      selectThumbnailImage,
      handleUpdateErrors,
    }
  },
}
</script>

<style lang="scss" scoped></style>
