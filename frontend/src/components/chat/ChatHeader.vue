<template>
  <div class="flex justify-between py-2 px-4 items-center shadow">
    <div class="flex gap-2 items-center">
      <span class="material-icons text-gray-900"> chevron_left </span>
      <p class="text-gray-900">{{ targetEmail }}</p>
    </div>
    <!-- <p class="text-red-500 text-sm">채팅 목록 삭제</p> -->
  </div>
</template>

<script>
export default {
  name: "ChatHeader",
  props: ["targetEmail"],
}
</script>

<style lang="scss" scoped></style>
