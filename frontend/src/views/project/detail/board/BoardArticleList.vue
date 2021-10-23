<template>
  <div class="p-4 grid gap-6">
    <BoardArticleItem />
    <BoardArticleItem />
    <BoardArticleItem />
    <BoardArticleItem />
    <BoardArticleItem />
  </div>
  <AddBoardArticleButton @addClick="handleAddClick()" />
</template>

<script>
import BoardArticleItem from "@/components/project/BoardArticleItem.vue"
import AddBoardArticleButton from "@/components/common/AddBoardArticleButton.vue"
import { useRoute, useRouter } from "vue-router"
import { onMounted } from "@vue/runtime-core"
import { useStore } from "vuex"

export default {
  name: "BoardArticleList",
  components: { BoardArticleItem, AddBoardArticleButton },
  setup() {
    const route = useRoute()
    const store = useStore()
    console.log(route.params)

    const router = useRouter()

    onMounted(async () => {
      const boardId = route.params.boardId
      const resData = await store.dispatch(
        "project/getBoardArticleList",
        boardId
      )
      console.log(resData)
    })

    const handleAddClick = () => {
      router.push({ name: "ArticleForm" })
    }

    return { handleAddClick }
  },
}
</script>

<style lang="scss" scoped></style>
