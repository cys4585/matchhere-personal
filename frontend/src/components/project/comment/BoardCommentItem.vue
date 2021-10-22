<template>
  <div class="grid gap-4">
    <Comment />
    <DropDownButton
      :isDropDownActive="isDropDownActive"
      @btnClick="isDropDownActive = !isDropDownActive"
    />
    <NestedCommentList v-if="isDropDownActive" />
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
  components: { NestedCommentList, DropDownButton, Comment },
  setup() {
    const isDropDownActive = ref(false)

    return { isDropDownActive }
  },
}
</script>

<style lang="scss" scoped></style>
