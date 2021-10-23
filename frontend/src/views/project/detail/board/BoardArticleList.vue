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
    const router = useRouter()
    const store = useStore()

    const projectId = route.params.projectId

    const handleAddClick = () => {
      router.push({ name: "ArticleForm" })
    }

    onMounted(async () => {
      const resData = await store.dispatch("project/getBoardList", projectId)
      console.log(resData)
    })

    return { handleAddClick }
  },
}
</script>

<style lang="scss" scoped></style>
