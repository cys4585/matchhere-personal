<template>
  <div class="p-2">
    <div class="wrapper">
      <textarea
        :style="{ height: textareaHeight }"
        placeholder="메세지를 입력하세요"
        @keypress="resize"
        @keydown.enter="handleEnter"
        v-model="content"
      ></textarea>
      <span
        class="material-icons text-gray-400"
        :class="{ 'text-gray-600': content }"
      >
        send
      </span>
    </div>
  </div>
</template>

<script>
import { ref } from "@vue/reactivity"
import { watch } from "@vue/runtime-core"
export default {
  name: "ChatControler",
  setup() {
    const textareaHeight = ref("24px")
    const resize = (e) => {
      const scrollHeight = e.target.scrollHeight
      if (scrollHeight > 48) textareaHeight.value = "72px"
      else if (scrollHeight > 24) textareaHeight.value = "48px"
    }

    const content = ref("")

    watch(content, () => {
      if (content.value === "") textareaHeight.value = "24px"
    })

    const handleEnter = (e) => {
      console.log(e.keyCode)
      // just Enter
      if (!e.shiftKey) {
        e.preventDefault()
        console.log(content.value)
        content.value = ""
        // shift + Enter
      } else {
        const scrollHeight = e.target.scrollHeight
        if (scrollHeight === 24) textareaHeight.value = "48px"
        else if (scrollHeight === 48) textareaHeight.value = "72px"
      }
    }

    return { resize, textareaHeight, content, handleEnter }
  },
}
</script>

<style lang="scss" scoped>
.wrapper {
  @apply bg-gray-100 rounded-lg flex items-center gap-2 p-2;

  textarea {
    @apply flex-grow outline-none resize-none bg-gray-100;
  }
}
</style>
