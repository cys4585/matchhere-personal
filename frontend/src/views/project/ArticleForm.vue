<template>
  <div class="py-10 px-4 grid gap-10">
    <div class="grid gap-4">
      <header>게시글 작성</header>
      <InputFormField :field="title" v-model="title.value" />
      <SelectFormField :field="boardChoice" v-model="boardChoice.value" />
      <TextAreaFormField :field="content" v-model="content.value" />
      <InputFormField :field="tags" v-model="tags.value" />
    </div>
    <button
      class="rounded-xl text-white font-bold py-4"
      :class="{ 'bg-gray-400': !canSubmit, 'bg-blue-500': canSubmit }"
      :disabled="!canSubmit"
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

export default {
  name: "ArticleForm",
  components: { InputFormField, SelectFormField, TextAreaFormField },
  setup() {
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

    return { title, boardChoice, tags, content, canSubmit }
  },
}
</script>

<style lang="scss" scoped></style>
