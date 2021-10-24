<template>
  <div class="grid gap-4">
    <Comment :comment="parentComment" />
    <DropDownButton
      :isDropDownActive="isDropDownActive"
      :cntNestedComment="nestedCommentList.length"
      @btnClick="isDropDownActive = !isDropDownActive"
    />
    <NestedCommentList
      v-if="isDropDownActive"
      :nestedCommentList="nestedCommentList"
      :articleId="articleId"
      :parentId="parentComment.id"
    />
    <hr />
  </div>
</template>

<script>
import { ref } from "@vue/reactivity"
import NestedCommentList from "@/components/project/comment/NestedCommentList.vue"
import DropDownButton from "@/components/project/comment/DropDownButton.vue"
import Comment from "@/components/project/comment/Comment.vue"

export default {
  name: "BoardCommentItem",
  props: ["parentComment", "nestedCommentList", "articleId"],
  components: { NestedCommentList, DropDownButton, Comment },
  setup() {
    const isDropDownActive = ref(false)

    return { isDropDownActive }
  },
}
</script>

<style lang="scss" scoped></style>
