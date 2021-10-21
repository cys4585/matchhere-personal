<template>
  <div>
    <div class="p-4 grid gap-6">
      <BoardArticleItem />
      <BoardArticleItem />
      <BoardArticleItem />
      <BoardArticleItem />
      <BoardArticleItem />
    </div>
    <AddBoardArticleButton @addClick="handleAddClick()" />
  </div>
</template>

<script>
import BoardArticleItem from "@/components/project/BoardArticleItem.vue"
import AddBoardArticleButton from "@/components/common/AddBoardArticleButton.vue"
import { useRouter } from "vue-router"

export default {
  name: "ProjectNotiBoard",
  components: { BoardArticleItem, AddBoardArticleButton },
  setup() {
    const router = useRouter()

    const handleAddClick = () => {
      router.push({ name: "ArticleForm" })
    }
    return { handleAddClick }
  },
}
</script>

<style lang="scss" scoped></style>
