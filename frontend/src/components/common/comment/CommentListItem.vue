<template>
  <Comment :comment="comment" />
  <button class="re-comment-toggle-btn" @click="handleClickOpenBtn">
    <span class="material-icons">
      {{ reCommentContainerOpen ? "arrow_drop_up" : "arrow_drop_down" }}
    </span>
    <span class="label">{{
      comment.replyCount ? `${comment.replyCount}개의 답글` : "답글 달기"
    }}</span>
  </button>
  <div class="re-comment-container" v-if="reCommentContainerOpen">
    <div class="re-comment-list" v-if="comment.reCommentList.length">
      <Comment
        v-for="reComment in comment.reCommentList"
        :key="reComment.id"
        :comment="reComment"
        :nestedComment="true"
      />
    </div>
    <CommentForm @onSubmit="handleSubmitComment" />
  </div>
</template>

<script>
import Comment from "@/components/common/comment/Comment.vue"
import CommentForm from "@/components/common/comment/CommentForm.vue"
import { ref } from "vue"
import { useStore } from "vuex"

export default {
  name: "CommentListItem",
  components: { Comment, CommentForm },
  props: {
    comment: Array,
    articleId: Number,
  },
  emits: ["onSubmitComment"],
  setup(props, { emit }) {
    const store = useStore()
    const reCommentContainerOpen = ref(false)

    const handleClickOpenBtn = () => {
      reCommentContainerOpen.value = !reCommentContainerOpen.value
    }

    const handleSubmitComment = async (content) => {
      const payload = {
        articleId: props.articleId,
        parentId: props.comment.parentId,
        content,
      }
      const newComment = await store.dispatch(
        "study/createArticleComment",
        payload
      )
      emit("onSubmitComment", { newComment, parentId: props.comment.parentId })
    }

    return {
      reCommentContainerOpen,
      handleClickOpenBtn,
      handleSubmitComment,
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
