<template>
  <div
    class="py-2 px-4 flex gap-2 w-screen hover:bg-blue-50 cursor-pointer"
    @click="handleClick"
  >
    <img
      class="w-14 h-14 border border-gray-400 rounded-full"
      :src="chatRoom.pic_uri || require('@/assets/images/test-profile.png')"
      alt="로고"
    />
    <div class="flex flex-col gap-1">
      <span class="text-gray-900 text-sm">{{ chatRoom.user_nickname }}</span>
      <p class="flex gap-2">
        <span
          class="
            text-sm text-gray-400
            overflow-hidden
            whitespace-nowrap
            overflow-ellipsis
          "
          style="max-width: 180px"
          >가나다라마바사아자차카타파하가나다라마바사가나다라</span
        >
        <span class="text-sm text-gray-400">21.10.25</span>
      </p>
    </div>
  </div>
</template>

<script>
import { useRouter } from "vue-router"
export default {
  name: "ChatUserListItem",
  props: {
    chatRoom: Object,
  },
  setup() {
    const router = useRouter()

    const handleClick = () => {
      // console.log(props.chatRoom)
      router.push({
        name: "ChatDetail",
        params: { email: "soc4585@naver.com" },
      })
    }

    return { handleClick }
  },
}
</script>
<style lang="scss" scoped></style>
