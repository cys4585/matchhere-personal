<template>
  <div class="grid gap-4 py-4">
    <div class="flex gap-4">
      <img :src="profilePic" alt="" class="w-12 h-12 rounded-full" />
      <div class="grid gap-1">
        <span class="font-medium text-gray-900">김병훈</span>
        <span class="text-gray-500 text-sm">하루 전</span>
      </div>
    </div>
    <div>확인했습니다확인했습니다확인했습니다!</div>
  </div>
</template>

<script>
import { ref } from "@vue/reactivity"
export default {
  name: "Comment",
  setup() {
    const profilePic = ref(require("@/assets/images/test-profile.png"))
    return { profilePic }
  },
}
</script>

<style lang="scss" scoped></style>
