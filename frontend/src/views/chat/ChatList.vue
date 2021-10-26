<template>
  <ChatUserListItem />
</template>

<script>
import ChatUserListItem from "@/components/chat/ChatUserListItem.vue"
import { onMounted } from "@vue/runtime-core"
import { useStore } from "vuex"

export default {
  name: "ChatList",
  components: { ChatUserListItem },
  setup() {
    const store = useStore()

    onMounted(async () => {
      try {
        const resData = await store.dispatch("chat/getChatList")
        console.log(resData)
      } catch (error) {
        console.log(error.message)
      }
    })
  },
}
</script>
<style lang="scss" scoped></style>
