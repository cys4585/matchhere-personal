<template>
  <div class="py-10 px-4 grid gap-10">
    <div class="grid gap-4">
      <h3 class="font-bold text-xl text-gray-900">게시글 작성</h3>
      <InputFormField :field="title" v-model="title.value" />
      <SelectFormField :field="boardChoice" v-model="boardChoice.value" />
      <TextAreaFormField :field="content" v-model="content.value" />
      <InputFormField :field="tags" v-model="tags.value" />
    </div>
    <button
      class="rounded-xl text-white font-bold py-4"
      :class="{ 'bg-gray-400': !canSubmit, 'bg-blue-500': canSubmit }"
      :disabled="!canSubmit"
      @click="handleSubmit"
    >
      확인
    </button>
  </div>
</template>

<script>
import { InputFormFieldMaker } from "@/libs/func"
import InputFormField from "@/components/common/formField/InputFormField.vue"
import SelectFormField from "@/components/common/SelectFormField.vue"
import TextAreaFormField from "@/components/common/TextAreaFormField.vue"
import { computed, ref } from "@vue/reactivity"
import { onMounted } from "@vue/runtime-core"
import { useStore } from "vuex"
import { useRoute } from "vue-router"

export default {
  name: "ArticleForm",
  components: { InputFormField, SelectFormField, TextAreaFormField },
  setup() {
    const route = useRoute()
    const store = useStore()

    const boardList = ref()
    onMounted(async () => {
      const projectId = route.params.projectId
      const resData = await store.dispatch("project/getBoardList", projectId)
      boardList.value = resData
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

      const resData = await store.dispatch(
        "project/createBoardArticle",
        reqForm
      )
      console.log(resData)
    }

    return { title, boardChoice, tags, content, canSubmit, handleSubmit }
  },
}
</script>

<style lang="scss" scoped></style>
