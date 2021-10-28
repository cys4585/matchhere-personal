<template>
  <router-link
    :to="{
      name: 'StudyBoardArticleDetail',
      params: { articleId: 3 },
    }"
    class="article-link"
  >
    <div class="article-info">
      <p class="title">14회 정기 미팅 (21. 10. 10)</p>
      <div class="tags flex gap-2">
        <Tag text="정기미팅" type="board" />
      </div>
      <div class="created flex gap-2">
        <Member
          :user="{
            email: 'kepy1106@gmail.com',
            name: '김병훈',
            coverPicUri: 'https://picsum.photos/100/100',
          }"
        />
      </div>
    </div>
    <div class="icon-wrapper">
      <router-link
        :to="{ name: 'StudyBoardArticleEdit', params: { articleId: 1 } }"
      >
        수정
      </router-link>
    </div>
  </router-link>
</template>

<script>
import Tag from "@/components/common/Tag.vue"
import Member from "@/components/common/Member.vue"
export default {
  components: { Tag, Member },
  name: "BoardArticleLink",
}
</script>

<style lang="scss" scoped>
.article-link {
  @apply flex p-4 rounded-md bg-white shadow-sm;

  .article-info {
    @apply flex-1 grid gap-2;
  }
}
</style>
