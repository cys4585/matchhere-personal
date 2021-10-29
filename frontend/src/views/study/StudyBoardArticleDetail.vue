<template>
  <div class="container py-6">
    <article class="grid gap-4 mb-4">
      <header class="pb-4 border-b">
        <h3 class="mb-4">DTO 사용방식에 대한 고찰</h3>
        <div class="tags flex gap-2 mb-2">
          <Tag text="정보공유" type="board" />
          <Tag text="정보공유" type="board" />
          <Tag text="정보공유" type="board" />
        </div>
        <div class="flex gap-2 items-center justify-between">
          <Member
            :user="{
              email: 'kepy1106@gmail.com',
              name: '김병훈',
              coverPicUri: 'https://picsum.photos/100/100',
            }"
          />
          <span class="text-sm">2021. 10. 10.</span>
        </div>
      </header>
      <div class="pb-10 border-b">
        <p>
          DTO 사용에 대한 Code Convention이 추가되었습니다. Entity만 사용한다면
          발생할 수 있는 문제들이 아래와 같습니다. 1. 보안 위험성 2. 유지보수
          어려움 따라서 Entity를 직접적으로 사용하지말고 DTO를 사용하도록
          Convention을 작성하였습니다. 자세한 사항은 Convention 게시판을
          참조해주세요.
        </p>
      </div>
    </article>
    <section>
      <h4 class="font-medium mb-2">3개의 댓글</h4>
      <CommentForm />
      <div class="comment-list">
        <CommentListItem />
      </div>
    </section>
  </div>
</template>

<script>
import Tag from "@/components/common/Tag.vue"
import Member from "@/components/common/Member.vue"
import CommentListItem from "@/components/common/comment/CommentListItem.vue"
import CommentForm from "@/components/common/comment/CommentForm.vue"

export default {
  name: "StudyBoardArticleDetail",
  components: {
    Tag,
    Member,
    CommentListItem,
    CommentForm,
  },
}
</script>

<style lang="scss" scoped>
.comment-submit-btn {
  @apply flex py-2 px-6 bg-blue-500 text-white font-bold justify-self-end rounded;
}
</style>
