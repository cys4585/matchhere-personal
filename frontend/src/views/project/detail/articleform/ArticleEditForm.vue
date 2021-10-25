<template>
  <div class="py-10 px-4 grid gap-10">
    <div class="grid gap-4">
      <h3 class="font-bold text-xl text-gray-900">게시글 수정</h3>
      <InputFormField :field="title" v-model="title.value" />
      <SelectFormField :field="boardChoice" v-model="boardChoice.value" />
      <!-- <TextAreaFormField :field="content" v-model="content.value" /> -->
      <div class="form-field">
        <label :for="content.key" class="label">{{ content.label }}</label>
        <div class="input-wrapper">
          <textarea
            class="content"
            :name="content.key"
            :id="content.key"
            cols="30"
            rows="7"
            v-model="content.value"
          ></textarea>
        </div>
      </div>
      <InputFormField :field="tags" v-model="tags.value" />
    </div>
    <div class="grid gap-2">
      <button
        class="rounded-xl text-white font-bold py-4 hover:bg-blue-400"
        :class="{ 'bg-gray-400': !canSubmit, 'bg-blue-500': canSubmit }"
        :disabled="!canSubmit"
        @click="handleSubmit"
      >
        확인
      </button>
      <button
        class="justify-self-center rounded-lg py-2 px-6"
        @click="handleDelete"
      >
        <span class="text-red-600 font-bold text-sm hover:text-red-400"
          >삭제</span
        >
      </button>
    </div>
  </div>
</template>

<script>
import { InputFormFieldMaker } from "@/libs/func"
import InputFormField from "@/components/common/formField/InputFormField.vue"
import SelectFormField from "@/components/project/SelectFormField.vue"
// import TextAreaFormField from "@/components/common/TextAreaFormField.vue"
import { computed, ref } from "@vue/reactivity"
import { onMounted } from "@vue/runtime-core"
import { useStore } from "vuex"
import { useRoute, useRouter } from "vue-router"

export default {
  name: "ArticleEditForm",
  components: { InputFormField, SelectFormField },
  setup() {
    const route = useRoute()
    const store = useStore()
    const router = useRouter()

    const boardList = ref()
    const projectId = ref()
    const oldBoardValue = ref()
    onMounted(async () => {
      try {
        const articleInfo = await store.dispatch(
          "project/getBoardArticleDetail",
          route.params.articleId
        )
        console.log(articleInfo)

        oldBoardValue.value = articleInfo.projectBoard

        title.value.value = articleInfo.title
        boardChoice.value.value = articleInfo.projectBoard
        content.value.value = articleInfo.content
        tags.value.value = articleInfo.tags.join(", ")
        projectId.value = route.params.projectId
        const resData = await store.dispatch(
          "project/getBoardList",
          projectId.value
        )
        boardList.value = resData
      } catch (error) {
        console.log(error.message)
      }
    })

    const title = ref(new InputFormFieldMaker("title"))
    const boardChoice = ref({
      label: "게시판 선택",
      value: "",
      placeholder: "게시판을 선택하세요",
      options: ["공지사항", "게시판"],
    })
    const content = ref({
      label: "내용",
      value: "",
      key: "content",
    })
    const tags = ref(new InputFormFieldMaker("tags"))

    const canSubmit = computed(() => {
      return Boolean(
        title.value.value && boardChoice.value.value && content.value.value
      )
    })

    const handleSubmit = async () => {
      const board = boardList.value.find(
        (board) => board.name === boardChoice.value.value
      )
      let tagList = tags.value.value.split(",")
      tagList = tagList.map((tag) => tag.trim())
      tagList = tagList.filter((tag) => tag !== "")

      const reqForm = {
        content: content.value.value,
        projectBoardId: board.id,
        tags: tagList,
        title: title.value.value,
      }
      try {
        const resData = await store.dispatch("project/updateBoardArticle", {
          reqForm,
          articleId: route.params.articleId,
        })
        console.log(resData)

        let name
        let boardId
        if (board.name === "공지사항") {
          name = "NotiBoardArticleList"
          boardId = boardList.value[0].id
        } else if (board.name === "게시판") {
          name = "BoardArticleList"
          boardId = boardList.value[1].id
        }
        router.push({ name, params: { projectId: projectId.value, boardId } })
      } catch (error) {
        console.log(error)
      }
    }

    const handleDelete = async () => {
      try {
        const resData = await store.dispatch(
          "project/deleteBoardArticle",
          route.params.articleId
        )
        console.log(resData)
        const board = oldBoardValue.value
        let name
        let boardId
        if (board === "공지사항") {
          name = "NotiBoardArticleList"
          boardId = boardList.value[0].id
        } else {
          name = "BoardArticleList"
          boardId = boardList.value[1].id
        }
        router.push({ name, params: { projectId: projectId.value, boardId } })
      } catch (error) {
        console.log(error)
      }
    }

    return {
      title,
      boardChoice,
      tags,
      content,
      canSubmit,
      handleSubmit,
      handleDelete,
    }
  },
}
</script>

<style lang="scss" scoped>
.form-field {
  @apply grid gap-2 w-full;

  .label {
    @apply text-sm font-medium;
  }

  .input-wrapper {
    @apply grid gap-1;

    .content {
      @apply border border-gray-400 rounded py-2 px-4 outline-none;

      &:focus {
        @apply ring-2 ring-blue-500;
      }
    }
  }
}
</style>
