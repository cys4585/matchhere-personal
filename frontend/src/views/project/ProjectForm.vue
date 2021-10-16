<template>
  <div class="container">
    <section class="project-form-section">
      <header>
        <h2>프로젝트 만들기</h2>
        <p>매치히어에서 내 프로젝트를 만들고 나의 성공시대 시작됐다!</p>
      </header>
      <div class="project-form-container">
        <div
          class="form"
          v-for="(form, name, index) in formFields"
          :key="form.id"
        >
          <h3 v-if="name === 'project'">프로젝트</h3>
          <h3 v-else-if="name === 'member'">구성원</h3>
          <div class="fields" v-for="field in form" :key="field.id">
            <InputFormField
              v-if="field.type === 'string'"
              :field="field"
              :formFields="form"
              v-model="field.value"
              @update:errors="handleUpdateErrors"
            />
            <SelectFormField
              v-else-if="field.type === 'select'"
              :field="field"
              v-model="field.value"
            />
            <div v-else-if="field.type === 'search'" class="grid gap-2">
              <TechStackField @SelectTeckStack="handleSelectTechStack" />
              <div class="flex flex-wrap gap-1">
                <TechStackItem
                  v-for="(item, idx) in field.value"
                  :key="item.id"
                  :techStack="item"
                  :idx="idx"
                  @remove="handleRemoveTeckStack"
                />
              </div>
            </div>
            <div v-else class="form-field">
              <p class="label">{{ field.label }}</p>
              <div v-if="field.type === 'radio'">
                <div v-for="(state, index, key) in field.stateList" :key="key">
                  <input
                    class="input-radio"
                    :type="field.type"
                    :id="field.idList[index]"
                    :value="state"
                    v-model="field.value"
                  />
                  <label :for="field.idList[index]">{{ state }}</label>
                </div>
              </div>
              <div v-else-if="field.type === 'file'">
                <!-- file 타입은 양방향 바인딩 할 수 없다. method로 구현 -->
                <input
                  :type="field.type"
                  accept="image/png, image/jpeg"
                  @change="selectThumbnailImage"
                />
                <p>{{ field.value }}</p>
              </div>
              <div v-else-if="field.type === 'date'">
                <input :type="field.type" v-model="field.value" />
              </div>
              <div v-else-if="field.type === 'textarea'">
                <textarea
                  :placeholder="field.placeholder"
                  v-model="field.value"
                  class="
                    w-full
                    h-32
                    border-2
                    rounded-md
                    outline-none
                    focus:ring-2 focus:ring-blue-500
                  "
                ></textarea>
              </div>
              <div v-else-if="field.type === 'number'">
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
        <SubmitButton :disabled="!canSubmit" @click="createProject"
          >생성</SubmitButton
        >
      </div>
    </section>
  </div>
</template>

<script>
import InputFormField from "@/components/common/InputFormField.vue"
import SelectFormField from "@/components/common/SelectFormField.vue"
import SubmitButton from "@/components/common/SubmitButton.vue"
import TechStackField from "@/components/common/TeckStackField.vue"
import TechStackItem from "@/components/project/TechStackItem.vue"
import { ref, computed } from "vue"
import { requiredValidator } from "@/libs/validator"
import { cityList } from "@/libs/data"

export default {
  name: "ProjectForm",
  components: {
    InputFormField,
    SubmitButton,
    SelectFormField,
    TechStackField,
    TechStackItem,
  },
  setup() {
    const formFields = ref({
      project: {
        pjtName: {
          key: "pjtName",
          label: "프로젝트 이름",
          type: "string",
          value: "",
          notNull: true,
          placeholder: "ex) 11월까지 진행하는 사이드 프로젝트",
          errors: {},
          validators: [requiredValidator],
        },
        pjtState: {
          key: "pjtState",
          label: "프로젝트 상태",
          type: "radio",
          idList: ["will", "ing", "done"],
          stateList: ["진행 예정", "진행 중", "완료"],
          value: "진행 예정",
          notNull: true,
          errors: {},
          validators: [],
        },
        ThumbnailImageFile: {
          key: "ThumbnailImageFile",
          label: "썸네일 이미지",
          type: "file",
          value: "",
          notNull: false,
          errors: {},
          validators: [],
        },
        techStacks: {
          key: "techStacks",
          label: "기술스택",
          type: "search",
          value: [],
          notNull: false,
          placeholder: "ex) Vue, Spring, MySQL",
          errors: {},
          validators: [],
        },
        schedule: {
          key: "schedule",
          label: "일정",
          type: "string",
          value: "",
          notNull: false,
          placeholder: "ex) 주말 10시 - 18시 / 평일 논의",
          errors: {},
          validators: [],
        },
        dueDate: {
          key: "dueDate",
          label: "프로젝트 마감 예정일",
          type: "date",
          value: "",
          notNull: false,
          errors: {},
          validators: [],
        },
        city: {
          key: "city",
          label: "지역",
          type: "select",
          placeholder: "지역을 선택하세요",
          options: cityList,
          value: "",
          notNull: true,
          errors: {},
          validators: [],
        },
        club: {
          key: "club",
          label: "소속 클럽",
          type: "select",
          placeholder: "클럽을 선택하세요",
          // 일단은!
          options: ["최고의 클럽", "멋쟁이 클럽", "굇수모임"],
          value: "",
          notNull: false,
          errors: {},
          validators: [],
        },
        introduction: {
          key: "introduction",
          label: "소개",
          type: "textarea",
          value: "",
          notNull: false,
          placeholder: "해당 프로젝트에 대해 소개해주세요",
          errors: {},
          validators: [],
        },
        openScope: {
          key: "openScope",
          label: "공개 범위",
          type: "radio",
          idList: ["all", "club", "project"],
          stateList: ["전체 공개", "클럽 멤버에게만 공개", "비공개"],
          value: "전체 공개",
          notNull: true,
          errors: {},
          validators: [],
        },
      },
      member: {
        recruitState: {
          key: "recruitState",
          label: "모집 상태",
          type: "radio",
          idList: ["rec-ing", "rec-done"],
          stateList: ["모집 중", "완료"],
          value: "모집 중",
          notNull: true,
          errors: {},
          validators: [],
        },
        developer: {
          key: "developer",
          label: "개발자",
          type: "number",
          value: 0,
          notNull: true,
          errors: {},
          validators: [],
        },
        designer: {
          key: "designer",
          label: "디자이너",
          type: "number",
          value: 0,
          notNull: true,
          errors: {},
          validators: [],
        },
        planner: {
          key: "planner",
          label: "기획자",
          type: "number",
          value: 0,
          notNull: true,
          errors: {},
          validators: [],
        },
      },
    })
    // 유효성 검사
    const isAllFieldsValid = computed(() => {
      const fields = [
        ...Object.values(formFields.value.project),
        ...Object.values(formFields.value.member),
      ]
      return fields.every((f) => Object.keys(f.errors).length === 0)
    })
    // notNull인 데이터 필수입력
    const notNullCheck = computed(() => {
      const fields = [
        ...Object.values(formFields.value.project),
        ...Object.values(formFields.value.member),
      ]
      return fields.every(
        (f) =>
          (f.notNull && f.value === 0) || (f.notNull && f.value) || !f.notNull
      )
    })
    // 모집구성원 적어도 1명 이상
    const totalMember = computed(() => {
      const memberFields = formFields.value.member
      return (
        memberFields.developer.value +
        memberFields.designer.value +
        memberFields.planner.value
      )
    })

    const canSubmit = computed(
      // 유효성 검사 + notNull인 데이터 필수입력 + 모집구성원 적어도 1명 이상
      () =>
        isAllFieldsValid.value && notNullCheck.value && totalMember.value > 0
    )

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

    const handleSelectTechStack = (techStack) => {
      if (formFields.value.project.techStacks.value.includes(techStack)) return
      formFields.value.project.techStacks.value.push(techStack)
    }
    const handleRemoveTeckStack = (idx) => {
      formFields.value.project.techStacks.value.splice(idx)
    }

    const createProject = () => {}

    return {
      formFields,
      selectThumbnailImage,
      handleUpdateErrors,
      canSubmit,
      createProject,
      handleSelectTechStack,
      handleRemoveTeckStack,
    }
  },
}
</script>

<style lang="scss" scoped>
.project-form-section {
  @apply py-10 px-4 grid gap-4;

  header {
    @apply grid gap-4;

    h2 {
      @apply font-bold text-2xl;
    }
  }

  .project-form-container {
    @apply flex flex-col gap-6 items-center;

    .form {
      @apply w-full grid gap-4;

      h3 {
        @apply font-bold text-xl;
      }

      .fields {
        @apply grid gap-4;

        .form-field {
          @apply grid gap-2 w-full;

          .label {
            @apply text-sm font-medium;
          }
          input,
          select {
            @apply border border-gray-400 rounded py-2 px-4 outline-none w-full bg-white;

            &:focus {
              @apply ring-2 ring-blue-500;
            }

            &.error {
              @apply border-red-500 bg-red-50;
            }
          }
          .input-radio {
            @apply w-auto;
          }
        }
      }
    }
  }
}
</style>
