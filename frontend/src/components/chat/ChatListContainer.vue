<template>
  <div class="flex-1 flex flex-col gap-1">
    <ChatItem v-for="(chatItem,idx) in chatList" :key="chatItem" props:
    :myId="myId" :chatItem="chatItem" :exChatItemId="idx !== 0 &&
    String(chatList[idx-1].sender_id)" />
  </div>
</template>

<script>
import ChatItem from "@/components/chat/ChatItem.vue"

export default {
  name: "ChatListContainer",
  components: { ChatItem },
  props: ["chatList", "myId"],
}
</script>

<style lang="scss" scoped></style>
