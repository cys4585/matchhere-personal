<template>
  <div class="container py-4 grid gap-4">
    <BoardArticleLink />
    <BoardArticleLink />
    <BoardArticleLink />
    <BoardArticleLink />
    <BoardArticleLink />
    <BoardArticleLink />
    <BoardArticleLink />
    <BoardArticleLink />
  </div>
  <button class="material-icons add-btn">add</button>
</template>

<script>
import BoardArticleLink from "@/components/common/BoardArticleLink.vue"
export default {
  name: "StudyBoardArticleList",
  components: {
    BoardArticleLink,
  },
}
</script>

<style lang="scss" scoped>
.add-btn {
  @apply fixed right-4 bottom-20 flex items-center justify-center p-4 bg-blue-500 rounded-full shadow-md text-white;
}
</style>
