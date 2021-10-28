<template>
  <div class="container py-10">
    <h2 class="mb-4">게시글 수정</h2>
    <StudyBoardForm @onSubmit="handleSubmit" />
  </div>
</template>

<script>
import StudyBoardForm from "@/components/common/board/BoardArticleForm.vue"

export default {
  name: "StudyBoardArticleEdit",
  components: { StudyBoardForm },
  setup() {
    const handleSubmit = (e) => {
      console.log(e)
    }

    return {
      handleSubmit,
    }
  },
}
</script>

<style lang="scss" scoped>
.tags {
  @apply flex gap-2 flex-wrap;
}
</style>
