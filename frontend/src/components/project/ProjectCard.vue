<template>
  <div class="project-card">
    <div class="cover-pic">사진</div>
    <div class="info">
      <h4>백준 알고리즘 스터디 하실 분</h4>
      <div class="content">
        <p class="text-gray-900 text-sm">
          주요 기술 스택: Python, Java, Holololo, Javas...
        </p>
        <div class="flex justify-between">
          <span class="text-xs">지역: 무관</span>
          <span class="text-xs">2021-12-25 까지</span>
        </div>
        <div class="flex gap-2">
          <span class="state-common">프로젝트 진행 중</span>
          <span class="state-common">팀원 모집 중</span>
        </div>
      </div>
    </div>
    <hr />
    <div class="create-info">
      <div>
        <span>사진</span>
        <span>김병훈</span>
        <span>5일 전</span>
      </div>
      <span class="material-icons text-gray-400"> more_vert </span>
    </div>
  </div>
</template>

<script>
export default {
  name: "ProjectCard",
  setup() {},
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
