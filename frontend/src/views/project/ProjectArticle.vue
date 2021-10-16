<template>
  <div class="flex justify-center"><img :src="coverPic" alt="" /></div>
  <div class="container">
    <section class="project-section">
      <header>
        <h2>{{ projectInfo.name }}</h2>
        <div class="grid gap-2">
          <p>
            <!-- <span class="bg-green-100 text-green-600 font-bold rounded px-1">
            프로젝트 준비 중
          </span> -->
            <span class="bg-blue-100 text-blue-600 font-bold rounded px-1">
              프로젝트 진행 중
            </span>
            <!-- <span class="bg-red-100 text-red-600 font-bold rounded px-1"
            >프로젝트 종료</span> -->
          </p>
          <p>
            <span class="bg-green-100 text-green-600 font-bold rounded px-1"
              >팀원 모집 중</span
            >
            <!-- <span class="bg-red-100 text-red-600 font-bold rounded px-1"
            >팀원 모집 마감</span
          > -->
          </p>
          <p class="flex gap-4">
            <span class="text-xs text-gray-500">조회수 62,858회</span>
            <span class="text-xs text-gray-500">
              {{ projectInfo.modifiedDate.slice(0, 11) }}
            </span>
          </p>
          <p class="flex gap-2 items-center">
            <img :src="profilePic" alt="" class="w-6 h-6" />
            <span class="text-xs text-gray-500">{{
              projectInfo.host.nickname
            }}</span>
          </p>
        </div>
      </header>
      <div class="project-container">
        <div class="content">
          <h3>주요 기술 스택</h3>
          <div class="grid gap-4">
            <!-- <p v-for="tech in projectInfo.techList" :key="tech.id">
              {{ tech }}
            </p> -->
            <p class="flex gap-2 items-center">
              <img :src="javaPic" alt="" class="w-4 h-4" />
              <span>java</span>
            </p>
            <p class="flex gap-2 items-center">
              <img :src="pythonPic" alt="" class="w-4 h-4" />
              <span>python</span>
            </p>
          </div>
        </div>
        <div class="content">
          <h3>일정</h3>
          <div class="grid gap-2">
            <p>{{ projectInfo.schedule }}</p>
            <p>2021. 11. 01 까지</p>
          </div>
        </div>
        <div class="content">
          <h3>팀원</h3>
          <div class="grid gap-6">
            <div class="grid gap-2">
              <div class="flex justify-between items-center">
                <h4>개발자</h4>
                <p
                  class="text-sm"
                  :class="{
                    'text-gray-400':
                      projectInfo.developerMaxCount ===
                      projectInfo.developerCount,
                  }"
                >
                  {{ projectInfo.developerMaxCount }}명 중
                  {{ projectInfo.developerCount }}명
                </p>
              </div>
              <div class="flex gap-4">
                <p
                  class="flex gap-2 items-center"
                  v-for="nickname in projectInfo.developerNicknames"
                  :key="nickname.id"
                >
                  <img :src="profilePic" alt="" class="w-6 h-6" />
                  <span class="text-xs text-gray-500">{{ nickname }}</span>
                </p>
              </div>
            </div>
            <div class="grid gap-2">
              <div class="flex justify-between items-center">
                <h4>기획자</h4>
                <p
                  class="text-sm"
                  :class="{
                    'text-gray-400':
                      projectInfo.plannerMaxCount === projectInfo.plannerCount,
                  }"
                >
                  {{ projectInfo.plannerMaxCount }}명 중
                  {{ projectInfo.plannerCount }}명
                </p>
              </div>
              <div class="flex gap-4">
                <p
                  class="flex gap-2 items-center"
                  v-for="nickname in projectInfo.developerNicknames"
                  :key="nickname.id"
                >
                  <img :src="profilePic" alt="" class="w-6 h-6" />
                  <span class="text-xs text-gray-500">{{ nickname }}</span>
                </p>
              </div>
            </div>
            <div class="grid gap-2">
              <div class="flex justify-between items-center">
                <h4>디자이너</h4>
                <p
                  class="text-sm"
                  :class="{
                    'text-gray-400':
                      projectInfo.designerMaxCount ===
                      projectInfo.designerCount,
                  }"
                >
                  {{ projectInfo.designerMaxCount }}명 중
                  {{ projectInfo.designerCount }}명
                </p>
              </div>
              <div class="flex gap-4">
                <p
                  class="flex gap-2 items-center"
                  v-for="nickname in projectInfo.developerNicknames"
                  :key="nickname.id"
                >
                  <img :src="profilePic" alt="" class="w-6 h-6" />
                  <span class="text-xs text-gray-500">{{ nickname }}</span>
                </p>
              </div>
            </div>
          </div>
        </div>
        <div class="content">
          <h3>지역</h3>
          <p>{{ projectInfo.city }}</p>
        </div>
        <div class="content">
          <h3>소속 클럽</h3>
          <div class="flex justify-between">
            <p>{{ projectInfo.club.name }}</p>
            <span class="material-icons text-gray-400"> open_in_new </span>
          </div>
        </div>
        <div class="content">
          <h3>소개</h3>
          <p>{{ projectInfo.bio }}</p>
        </div>
      </div>
      <div class="flex flex-col items-center gap-2 px-7">
        <button
          class="w-full text-white py-4 rounded-full bg-blue-500"
          @click="isApplyModalActivation = true"
        >
          프로젝트 참가 신청
        </button>
        <button class="py-2 px-6">
          <p class="text-gray-600 font-medium text-sm">수정</p>
        </button>
      </div>
    </section>
  </div>
  <ApplyForParticipationModal
    v-if="isApplyModalActivation"
    @close="isApplyModalActivation = false"
  />
</template>

<script>
import { computed, onBeforeMount, ref } from "@vue/runtime-core"
import { useRoute } from "vue-router"
import { useStore } from "vuex"
import ApplyForParticipationModal from "@/components/project/ApplyForParticipationModal.vue"

export default {
  name: "Project",
  components: { ApplyForParticipationModal },
  setup() {
    const route = useRoute()
    const store = useStore()

    onBeforeMount(() => {
      store.dispatch("getProject", route.params.projectId)
    })

    const projectInfo = computed(() => store.state.project.projectInfo)

    const isApplyModalActivation = ref()

    const coverPic = ref(require("@/assets/test-ys.png"))
    const profilePic = ref(require("@/assets/test-profile.png"))
    const javaPic = ref(require("@/assets/test-java.png"))
    const pythonPic = ref(require("@/assets/test-python.png"))

    return {
      projectInfo,
      isApplyModalActivation,
      coverPic,
      profilePic,
      javaPic,
      pythonPic,
    }
  },
}
</script>

<style lang="scss" scoped>
.project-section {
  @apply pt-6 pb-10 grid gap-10;

  header {
    @apply grid gap-4;

    h2 {
      @apply font-bold text-2xl;
    }
  }

  .project-container {
    @apply flex flex-col gap-8 items-center;

    .content {
      @apply w-full grid gap-2;

      h3 {
        @apply font-bold text-xl;
      }

      h4 {
        @apply font-medium text-lg;
      }
    }
  }
}
</style>
