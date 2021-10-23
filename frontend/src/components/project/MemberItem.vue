<template>
  <div class="flex justify-between items-center">
    <div class="flex gap-2 items-center">
      <img :src="profilePic" alt="" class="w-8 h-8" />
      <p class="text-gray-900 font-medium">{{ memberInfo.name }}</p>
    </div>
    <div class="flex gap-2">
      <div
        class="bg-gray-200 text-gray-900 font-bold text-sm px-4 py-1 rounded-lg"
      >
        {{ memberInfo.authority }}
      </div>
      <div
        class="bg-blue-100 text-gray-900 font-bold text-sm px-4 py-1 rounded-lg"
      >
        {{ memberInfo.role }}
      </div>
    </div>
  </div>
</template>

<script>
import { ref } from "vue"
export default {
  name: "MemberItem",
  props: ["memberInfo"],
  setup() {
    const profilePic = ref(require("@/assets/images/test-profile.png"))

    return {
      profilePic,
    }
  },
}
</script>

<style lang="scss" scoped></style>
