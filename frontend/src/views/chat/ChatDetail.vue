<template>
  <ChatHeader />
  <ChatListContainer />
  <ChatControler />
</template>

<script>
import ChatHeader from "@/components/chat/ChatHeader.vue"
import ChatListContainer from "@/components/chat/ChatListContainer.vue"
import ChatControler from "@/components/chat/ChatControler.vue"
export default {
  name: "ChatDetail",
  components: { ChatHeader, ChatListContainer, ChatControler },
}
</script>
<style lang="scss" scoped></style>
