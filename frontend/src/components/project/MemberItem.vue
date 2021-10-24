<template>
  <div class="flex justify-between items-center">
    <div class="flex gap-2 items-center">
      <img :src="profilePic" alt="" class="w-8 h-8" />
      <p class="text-gray-900 font-medium">{{ memberInfo.name }}</p>
    </div>
    <div class="flex gap-2">
      <div v-if="memberInfo.authority === '소유자'" class="host">
        {{ memberInfo.authority }}
      </div>
      <div v-else class="authority">
        <span>{{ memberInfo.authority }}</span>
      </div>
      <div class="role">
        {{ memberInfo.role }}
        <span class="material-icons text-base text-gray-600">
          expand_more
        </span>
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

<style lang="scss" scoped>
.host {
  @apply flex
          items-center
          bg-gray-200
          text-gray-900
          font-bold
          text-sm
          px-4
          py-1
          rounded-lg;
}
.authority {
  @apply flex
          items-center
          bg-gray-200
          text-gray-900
          font-bold
          text-sm
          pl-4
          pr-2
          py-1
          rounded-lg;
}

.role {
  @apply flex
          items-center
          bg-blue-100
          text-gray-900
          font-bold
          text-sm
          pl-4
          pr-2
          py-1
          rounded-lg;
}
</style>
