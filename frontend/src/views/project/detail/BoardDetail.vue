<template>
  <div class="py-6 px-4 grid gap-6">
    <div>
      <div class="grid gap-4">
        <h3 class="font-bold text-xl text-gray-900">
          DTO 사용방식에 대한 고찰
        </h3>
        <div class="grid gap-2">
          <div class="flex gap-2">
            <span
              class="rounded bg-gray-100 text-gray-600 font-bold text-sm"
              style="padding: 2px 8px"
              >정보공유</span
            >
            <span
              class="rounded bg-gray-100 text-gray-600 font-bold text-sm"
              style="padding: 2px 8px"
              >BE</span
            >
            <span
              class="rounded bg-gray-100 text-gray-600 font-bold text-sm"
              style="padding: 2px 8px"
              >DTO</span
            >
          </div>
          <p class="flex gap-2 items-center">
            <img :src="profilePic" alt="" class="w-6 h-6 rounded-full" />
            <span class="text-xs text-gray-600">김병훈</span>
            <span class="text-xs text-gray-500">2021. 10. 10</span>
          </p>
        </div>
        <hr />
        <div v-html="test.replace(/(?:\r\n|\r|\n)/g, '<br />')"></div>
      </div>
    </div>
    <div class="pt-4 grid gap-4">
      <hr />
      <div class="grid gap-2">
        <h4 class="font-medium text-lg text-gray-900">3개의 댓글</h4>
        <CommentForm />
      </div>
    </div>
    <div class="grid gap-6">
      <BoardCommentItem />
      <BoardCommentItem />
    </div>
  </div>
</template>

<script>
import { ref } from "@vue/reactivity"
import CommentForm from "@/components/project/comment/CommentForm.vue"
import BoardCommentItem from "@/components/project/comment/BoardCommentItem.vue"

export default {
  name: "BoardDetail",
  components: { BoardCommentItem, CommentForm },
  setup() {
    const test =
      ref(`DTO 사용에 대한 Code Convention이 추가되었습니다. Entity만 사용한다면 발생할 수 있는 문제들이 아래와 같습니다.
      1. 보안 위험성
      2. 유지보수 어려움
      따라서 Entity를 직접적으로 사용하지말고 DTO를 사용하도록 Convention을 작성하였습니다.
      자세한 사항은 Convention 게시판을 참조해주세요.`)
    const profilePic = ref(require("@/assets/images/test-profile.png"))

    return { test, profilePic }
  },
}
</script>

<style lang="scss" scoped></style>
