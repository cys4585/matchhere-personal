<template>
  <div class="project-card">
    <img :src="coverPic" alt="" class="rounded-t-md" />
    <div class="info">
      <h4>백준 알고리즘 스터디 하실 분</h4>
      <div class="content">
        <p class="text-gray-900 text-sm">
          주요 기술 스택: Python, Java, Holololo, Javas...
        </p>
        <div class="flex justify-between">
          <span class="text-xs text-gray-600">지역: 무관</span>
          <span class="text-xs text-gray-600">2021-12-25 까지</span>
        </div>
        <div class="flex gap-2">
          <span class="state-common">프로젝트 진행 중</span>
          <span class="state-common">팀원 모집 중</span>
        </div>
      </div>
    </div>
    <hr />
    <div class="create-info">
      <div class="flex gap-2 items-center">
        <img :src="profilePic" alt="" class="w-6 h-6" />
        <div class="flex gap-1">
          <span class="text-gray-900 text-xs">김병훈</span>
          <span class="text-gray-500 text-xs">5일 전</span>
        </div>
      </div>
      <span class="material-icons text-gray-400"> more_vert </span>
    </div>
  </div>
</template>

<script>
import { ref } from "vue"
export default {
  name: "ProjectCard",
  setup() {
    const coverPic = ref(require("@/assets/images/test-card.png"))
    const profilePic = ref(require("@/assets/images/test-profile.png"))

    return { coverPic, profilePic }
  },
}
</script>
<style lang="scss" scoped>
.project-card {
  @apply rounded-md shadow-md;

  .info {
    @apply p-4 grid gap-2;

    h4 {
      @apply font-medium text-gray-900;
    }
    .content {
      @apply grid gap-4;

      .state-common {
        @apply py-1 px-2 rounded font-bold text-sm;
      }
    }
  }

  .create-info {
    @apply p-2 flex justify-between;
  }
}
</style>
