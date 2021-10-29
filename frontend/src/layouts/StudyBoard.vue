<template>
  <div class="min-h-full h-full bg-gray-50">
    <nav>
      <router-link
        :to="{ name: 'StudyBoardArticleList', params: { boardId: 1 } }"
      >
        공지사항
      </router-link>
      <router-link
        :to="{ name: 'StudyBoardArticleList', params: { boardId: 2 } }"
      >
        게시판
      </router-link>
      <router-link :to="{ name: 'StudyManage' }"> 스터디 관리 </router-link>
    </nav>
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: "StudyBoardLayout",
  props: {
    studyId: [String, Number],
    articleId: String,
    boardId: String,
  },
}
</script>

<style lang="scss" scoped>
nav {
  @apply py-3 px-2 border-b bg-white;

  a {
    @apply py-2 px-4 text-gray-500 inline-block rounded-md;

    &:hover {
      @apply bg-gray-100;
    }

    &.router-link-active {
      @apply font-bold text-gray-900;
    }
  }
}
</style>
