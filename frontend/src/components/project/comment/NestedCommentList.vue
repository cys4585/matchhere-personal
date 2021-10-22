<template>
  <div class="wrapper">
    <div class="comment-wrapper">
      <Comment />
      <hr />
    </div>
    <div class="grid gap-2">
      <Comment />
      <hr />
    </div>
    <CommentForm />
  </div>
</template>

<script>
import { ref } from "@vue/reactivity"
import Comment from "@/components/project/comment/Comment.vue"
import CommentForm from "@/components/project/comment/CommentForm.vue"

export default {
  name: "NestedCommentList",
  components: { Comment, CommentForm },
  setup() {
    const profilePic = ref(require("@/assets/images/test-profile.png"))
    return { profilePic }
  },
}
</script>

<style lang="scss" scoped>
.wrapper {
  @apply grid gap-2 border border-gray-200 rounded bg-gray-50 p-4;

  .comment-wrapper {
    @apply grid gap-2;
  }
}
</style>
