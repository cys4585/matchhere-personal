<template>
  <div class="p-4 grid gap-6" v-if="boardArticleList">
    <BoardArticleItem
      v-for="article in boardArticleList"
      :key="article.articleId"
      :article="article"
    />
  </div>
  <AddBoardArticleButton @addClick="handleAddClick()" />
</template>

<script>
import BoardArticleItem from "@/components/project/BoardArticleItem.vue"
import AddBoardArticleButton from "@/components/common/AddBoardArticleButton.vue"
import { useRoute, useRouter } from "vue-router"
import { onMounted, ref } from "@vue/runtime-core"
import { useStore } from "vuex"

export default {
  name: "BoardArticleList",
  components: { BoardArticleItem, AddBoardArticleButton },
  setup() {
    const route = useRoute()
    const store = useStore()
    const router = useRouter()

    const boardArticleList = ref()
    onMounted(async () => {
      const boardId = route.params.boardId
      const resData = await store.dispatch(
        "project/getBoardArticleList",
        boardId
      )
      console.log(resData)
      boardArticleList.value = resData.content
    })

    const handleAddClick = () => {
      router.push({ name: "ArticleForm" })
    }

    return { handleAddClick, boardArticleList }
  },
}
</script>

<style lang="scss" scoped></style>
