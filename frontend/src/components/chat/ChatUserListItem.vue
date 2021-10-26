<template>
  <div class="py-2 px-4 flex gap-2">
    <img
      class="w-14 h-14 border border-gray-400 rounded-full"
      :src="require('@/assets/images/test-profile.png')"
      alt="로고"
    />
    <div class="grid gap-1">
      <p class="text-gray-900 text-sm">NickName</p>
      <p class="flex gap-2">
        <span
          class="
            text-sm text-gray-400
            overflow-hidden
            whitespace-nowrap
            overflow-ellipsis
          "
          style="max-width: 202px"
          >가나다라마바사아자차카타파하가나다라마바사가나다라</span
        >
        <span class="text-sm text-gray-400">21.10.25</span>
      </p>
    </div>
  </div>
</template>

<script>
export default {
  name: "ChatUserListItem",
}
</script>
<style lang="scss" scoped></style>
