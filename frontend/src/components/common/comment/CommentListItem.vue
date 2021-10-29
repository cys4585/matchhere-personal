<template>
  <Comment key="1" />
  <button class="re-comment-toggle-btn" @click="handleClickOpenBtn">
    <span class="material-icons">
      {{ reCommentContainerOpen ? "arrow_drop_up" : "arrow_drop_down" }}
    </span>
    <span class="label">2개의 답글</span>
  </button>
  <div class="re-comment-container" v-if="reCommentContainerOpen">
    <div class="re-comment-list" v-if="true">
      <Comment :nestedComment="true" key="2" />
      <Comment :nestedComment="true" key="3" />
    </div>
    <CommentForm />
  </div>
</template>

<script>
import Comment from "@/components/common/comment/Comment.vue"
import CommentForm from "@/components/common/comment/CommentForm.vue"
import { ref } from "vue"

export default {
  name: "CommentListItem",
  components: { Comment, CommentForm },
  setup() {
    const reCommentContainerOpen = ref(false)

    const handleClickOpenBtn = () => {
      reCommentContainerOpen.value = !reCommentContainerOpen.value
    }

    return {
      reCommentContainerOpen,
      handleClickOpenBtn,
    }
  },
}
</script>

<style lang="scss" scoped>
.re-comment-toggle-btn {
  @apply flex gap-1 mb-4;

  .label {
    @apply text-sm text-blue-600 font-medium;
  }
}

.re-comment-container {
  @apply grid gap-2 p-4 border border-gray-200 bg-gray-50 rounded;

  .re-comment-list {
    @apply grid gap-2;
  }
}
</style>
