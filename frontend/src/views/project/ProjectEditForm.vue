<template>
  <div class="container py-10">
    <header>
      <h2>프로젝트 수정</h2>
      <p>매치히어에서 내 프로젝트를 만들고 나의 성공시대 시작됐다!</p>
    </header>
    <article class="article-wrapper">
      <section class="project-section">
        <h3>프로젝트</h3>
        <!-- 프로젝트 이름 -->
        <InputFormField :field="projectName" v-model="projectName.value" />
        <!-- 프로젝트 상태 -->
        <InputRadio
          :field="projectProgressState"
          v-model="projectProgressState.value"
        />
        <!-- 기술스택 -->
        <div class="grid gap-2">
          <TechStackField @SelectTeckStack="handleSelectTechStack" />
          <div class="grid gap-2">
            <ul v-for="(userLevel, key) in techstacks.value" :key="key">
              <TechStackListItem
                :name="key"
                :userLevel="userLevel"
                @change:userLevel="handleChangeTechLevel"
                @remove:teckStack="handleRemoveTechStack"
              />
            </ul>
          </div>
        </div>
        <!-- 일정 -->
        <InputFormField :field="schedule" v-model="schedule.value" />
        <!-- 프로젝트 마감 예정일 -->
        <div class="period">
          <p class="label">{{ period.label }}</p>
          <input type="date" v-model="period.value" />
        </div>
        <!-- 지역 -->
        <SelectFormField :field="city" v-model="city.value" />
        <!-- 소개 -->
        <div class="bio">
          <p class="label">{{ bio.label }}</p>
          <textarea
            :placeholder="bio.placeholder"
            v-model="bio.value"
            class="bio-textarea"
          ></textarea>
        </div>
        <!-- 공개 범위 -->
        <InputRadio :field="publicScope" v-model="publicScope.value" />
      </section>
      <hr />
      <section class="member-section">
        <h3>구성원</h3>
        <!-- 모집 상태 -->
        <InputRadio
          :field="recruitmentState"
          v-model="recruitmentState.value"
        />
        <!-- 개발자 -->
        <div class="max-count">
          <p class="label">{{ developerMaxCount.label }}</p>
          <input
            :type="developerMaxCount.type"
            :min="developerMaxCount.minValue"
            max="100"
            v-model="developerMaxCount.value"
          />
        </div>
        <!-- 디자이너 -->
        <div class="max-count">
          <p class="label">{{ designerMaxCount.label }}</p>
          <input
            :type="designerMaxCount.type"
            :min="designerMaxCount.minValue"
            max="100"
            v-model="designerMaxCount.value"
          />
        </div>
        <!-- 기획자 -->
        <div class="max-count">
          <p class="label">{{ plannerMaxCount.label }}</p>
          <input
            :type="plannerMaxCount.type"
            :min="plannerMaxCount.minValue"
            max="100"
            v-model="plannerMaxCount.value"
          />
        </div>
      </section>
      <SubmitButton :disabled="!canSubmit" @click="handleButtonClick">
        수정
      </SubmitButton>
    </article>
  </div>
</template>

<script>
import InputFormField from "@/components/common/formField/InputFormField.vue"
import TechStackField from "@/components/common/TeckStackField.vue"
import TechStackListItem from "@/components/common/TeckStackListItem.vue"
import SelectFormField from "@/components/project/SelectFormField.vue"
import SubmitButton from "@/components/common/SubmitButton.vue"
import InputRadio from "@/components/project/InputRadio.vue"
import { computed, onBeforeMount, ref } from "@vue/runtime-core"
import { useStore } from "vuex"
import { useRoute, useRouter } from "vue-router"
import { InputFormFieldMaker } from "@/libs/func"
import { cityList } from "@/libs/data"

export default {
  name: "ProjectEditForm",
  components: {
    InputFormField,
    InputRadio,
    TechStackField,
    TechStackListItem,
    SelectFormField,
    SubmitButton,
  },
  setup() {
    const route = useRoute()
    const store = useStore()
    const router = useRouter()

    onBeforeMount(async () => {
      const projectId = route.params.projectId
      try {
        const resAuth = await store.dispatch("project/getAuthority", projectId)
        if (resAuth !== "소유자")
          router.push({ name: "ProjectArticle", params: { projectId } })

        const resProjectInfo = await store.dispatch(
          "project/getInfoForUpdate",
          projectId
        )
        console.log(resProjectInfo)
        // =============================================================
        // 기존 데이터 복사
        projectName.value.value = resProjectInfo.name
        projectProgressState.value.value = resProjectInfo.projectProgressState
        schedule.value.value = resProjectInfo.schedule
        period.value.value = resProjectInfo.period
        city.value.value = resProjectInfo.city
        bio.value.value = resProjectInfo.bio
        publicScope.value.value = resProjectInfo.publicScope
        recruitmentState.value.value = resProjectInfo.recruitmentState
        developerMaxCount.value.value = resProjectInfo.developerMaxCount
        developerMaxCount.value.minValue = resProjectInfo.developerCount
        designerMaxCount.value.value = resProjectInfo.designerMaxCount
        designerMaxCount.value.minValue = resProjectInfo.designerCount
        plannerMaxCount.value.value = resProjectInfo.plannerMaxCount
        plannerMaxCount.value.minValue = resProjectInfo.plannerCount
        resProjectInfo.techstacks.forEach(
          (item) => (techstacks.value.value[item.name] = item.level)
        )
        // =============================================================
      } catch (error) {
        console.log(error.message)
      }
    })

    // 프로젝트 이름
    const projectName = ref(new InputFormFieldMaker("projectName"))
    // 프로젝트 상태
    const projectProgressState = ref({
      key: "projectProgressState",
      label: "프로젝트 상태",
      type: "radio",
      idList: ["will", "ing", "done"],
      stateList: ["프로젝트 준비 중", "프로젝트 진행 중", "프로젝트 종료"],
      value: "프로젝트 준비 중",
    })
    // 기술스택
    const techstacks = ref({
      key: "techstacks",
      label: "기술스택",
      type: "search",
      value: {},
      placeholder: "ex) Vue, Spring, MySQL",
    })
    const handleSelectTechStack = (techStackKey) => {
      if (techstacks.value.value[techStackKey]) return
      techstacks.value.value[techStackKey] = "하"
    }
    const handleChangeTechLevel = ({ key, level }) =>
      (techstacks.value.value[key] = level)
    const handleRemoveTechStack = (key) => delete techstacks.value.value[key]
    // 일정
    const schedule = ref(new InputFormFieldMaker("schedule"))
    // 프로젝트 마감 예정일 (기간)
    const period = ref({
      key: "period",
      label: "프로젝트 마감 예정일",
      type: "date",
      value: "",
    })
    // 지역
    const city = ref({
      key: "city",
      label: "지역",
      placeholder: "지역을 선택하세요",
      options: ["온라인", "무관", ...cityList],
      value: "",
      notNull: true,
    })
    // 소속 클럽 pass
    // ...

    // 소개
    const bio = ref({
      key: "bio",
      label: "소개",
      type: "textarea",
      value: "",
      notNull: false,
      placeholder: "해당 프로젝트에 대해 소개해주세요",
      errors: {},
      validators: [],
    })
    // 공개 범위
    const publicScope = ref({
      key: "openScope",
      label: "공개 범위",
      type: "radio",
      idList: ["all", "project"],
      stateList: ["전체 공개", "비공개"],
      value: "전체 공개",
    })
    // 모집 상태
    const recruitmentState = ref({
      key: "recruitmentState",
      label: "모집 상태",
      type: "radio",
      idList: ["rec-ing", "rec-done"],
      stateList: ["모집 중", "모집 마감"],
      value: "모집 중",
    })
    // 개발자
    const developerMaxCount = ref({
      key: "developerMaxCount",
      label: "개발자",
      type: "number",
      minValue: 0,
      value: 0,
    })
    // 디자이너
    const designerMaxCount = ref({
      key: "designerMaxCount",
      label: "디자이너",
      type: "number",
      minValue: 0,
      value: 0,
    })
    // 기획자
    const plannerMaxCount = ref({
      key: "plannerMaxCount",
      label: "기획자",
      type: "number",
      minValue: 0,
      value: 0,
    })

    // 수정버튼 활성화
    const canSubmit = computed(() =>
      Boolean(projectName.value.value && city.value.value)
    )

    // 제출
    const handleButtonClick = async () => {
      const projectId = route.params.projectId
      const formData = {
        name: projectName.value.value,
        projectProgressState: projectProgressState.value.value,
        techstacks: techstacks.value.value,
        schedule: schedule.value.value,
        period: period.value.value,
        city: city.value.value,
        bio: bio.value.value,
        publicScope: publicScope.value.value,
        recruitmentState: recruitmentState.value.value,
        developerMaxCount: developerMaxCount.value.value,
        designerMaxCount: designerMaxCount.value.value,
        plannerMaxCount: plannerMaxCount.value.value,
      }
      console.log(projectId)
      console.log(formData)
      try {
        const resData = await store.dispatch("project/updateProject", {
          formData,
          projectId,
        })
        console.log(resData)
        store.commit("ADD_MESSAGE", {
          text: "수정이 완료되었습니다",
        })
        router.push({
          name: "ProjectArticle",
          params: { projectId },
        })
      } catch (error) {
        alert(error.message)
      }
    }

    return {
      projectName,
      projectProgressState,
      handleButtonClick,
      techstacks,
      handleSelectTechStack,
      handleChangeTechLevel,
      handleRemoveTechStack,
      schedule,
      period,
      city,
      bio,
      publicScope,
      recruitmentState,
      developerMaxCount,
      designerMaxCount,
      plannerMaxCount,
      canSubmit,
    }
  },
}
</script>

<style lang="scss" scoped>
.container {
  @apply py-10 grid gap-4;

  header {
    @apply grid gap-4;
  }

  .article-wrapper {
    @apply grid gap-6;

    .project-section {
      @apply grid gap-4;

      .label {
        @apply text-sm font-medium;
      }

      .period {
        @apply grid gap-2 w-full;
      }

      .bio {
        @apply grid gap-2 w-full;
        .bio-textarea {
          @apply py-2 px-4 w-full h-40 border-2 rounded-md outline-none focus:ring-2 focus:ring-blue-500;
        }
      }
    }

    .member-section {
      @apply grid gap-4;

      .max-count {
        @apply grid gap-2 w-full;
      }
    }
  }
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
</style>
