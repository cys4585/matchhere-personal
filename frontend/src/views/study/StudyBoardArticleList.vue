<template>
  <div class="container py-4 grid gap-4">
    <BoardArticleLink />
    <!-- <BoardArticleLink />
    <BoardArticleLink />
    <BoardArticleLink />
    <BoardArticleLink />
    <BoardArticleLink />
    <BoardArticleLink />
    <BoardArticleLink /> -->
  </div>
  <router-link
    class="material-icons add-btn"
    :to="{ name: 'StudyBoardArticleCreate' }"
    >add</router-link
  >
</template>

<script>
import BoardArticleLink from "@/components/common/board/BoardArticleLink.vue"
export default {
  name: "StudyBoardArticleList",
  components: {
    BoardArticleLink,
  },
  props: {
    studyId: [String, Number],
    boardId: [String, Number],
  },
}
</script>

<style lang="scss" scoped>
.add-btn {
  @apply fixed right-4 bottom-20 flex items-center justify-center p-4 bg-blue-500 rounded-full shadow-md text-white;
}
</style>
