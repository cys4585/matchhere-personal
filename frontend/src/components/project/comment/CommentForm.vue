<template>
  <div class="grid gap-2">
    <textarea placeholder="댓글을 작성하세요"></textarea>
    <span class="justify-self-end">
      <button class="">작성</button>
    </span>
  </div>
</template>

<script>
export default {
  name: "CommentForm",
}
</script>

<style lang="scss" scoped>
textarea {
  @apply w-full h-20 border border-gray-200 rounded p-4;
}

button {
  @apply py-1 px-6 rounded bg-blue-500 text-white text-sm font-bold;
}
</style>
