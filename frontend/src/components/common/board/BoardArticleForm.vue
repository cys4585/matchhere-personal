<template>
  <form @submit.prevent="" class="grid gap-10">
    <div class="grid gap-4">
      <div class="form-field">
        <label for="name">스터디 이름</label>
        <input type="text" id="name" v-model="fields.name.value" />
      </div>
      <SelectFormField :field="fields.board" v-model="fields.board.value" />
      <div class="form-field">
        <label for="content">내용</label>
        <textarea id="content" v-model="fields.content.value" />
      </div>
      <div class="form-field">
        <label for="tag">태그</label>
        <input
          type="text"
          id="tag"
          v-model="fields.tag.value"
          @keydown.enter="handleAddTag"
        />
        <div class="tags">
          <Tag
            v-for="tag in fields.tag.selectedTags"
            @click="handleRemoveTag(tag)"
            :key="tag"
            :text="tag"
          />
        </div>
      </div>
    </div>
    <SubmitButton>확인</SubmitButton>
  </form>
</template>

<script>
import { reactive } from "@vue/reactivity"
import SelectFormField from "@/components/common/formField/SelectFormField.vue"
import Tag from "@/components/common/Tag.vue"
import SubmitButton from "@/components/common/SubmitButton.vue"

export default {
  name: "StudyBoardForm",
  components: { SelectFormField, Tag, SubmitButton },
  emits: ["onSubmit"],
  setup() {
    const fields = reactive({
      name: {
        value: "",
      },
      board: {
        label: "게시판 선택",
        value: "",
        placeholder: "게시판을 선택하세요",
        options: ["공지사항", "게시판"],
      },
      content: {
        value: "",
      },
      tag: {
        value: "",
        selectedTags: [],
      },
    })

    const handleAddTag = () => {
      if (fields.tag.selectedTags.indexOf(fields.tag.value) === -1) {
        fields.tag.selectedTags.push(fields.tag.value)
      }
      fields.tag.value = ""
    }

    const handleRemoveTag = (selectedTag) => {
      fields.tag.selectedTags = fields.tag.selectedTags.filter(
        (tag) => tag !== selectedTag
      )
    }

    return {
      fields,
      handleAddTag,
      handleRemoveTag,
    }
  },
}
</script>

<style></style>
