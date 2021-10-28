<template>
  <div
    class="overflow-y-scroll flex-1 w-full"
    @scroll="handleScroll"
    ref="scrollBarDiv"
  >
    <div class="flex flex-col gap-1 px-4 pb-4 w-full" ref="chatListDiv">
      <ChatItem v-for="(chatItem,idx) in chatList" :key="chatItem" props:
      :myId="myId" :chatItem="chatItem" :exChatItemId="idx !== 0 &&
      String(chatList[idx-1].sender_id)" />
    </div>
  </div>
</template>

<script>
import ChatItem from "@/components/chat/ChatItem.vue"
import { watch, onUpdated, ref } from "@vue/runtime-core"

export default {
  name: "ChatListContainer",
  components: { ChatItem },
  // props: ["chatList", "myId"],
  props: {
    chatList: Object,
    myId: String,
  },
  emits: ["handleScroll"],
  setup(props, { emit }) {
    const scrollBarDiv = ref(null)
    const chatListDiv = ref(null)

    const oldScrollHeight = ref()
    const handleScroll = (e) => {
      if (e.target.scrollTop === 0) {
        oldScrollHeight.value = e.target.scrollHeight
        emit("handleScroll")
      }
    }

    const isFirstHistory = ref(false)
    const isAddedNewMsg = ref(false)
    const isAddedHistory = ref(false)
    watch(
      () => props.chatList,
      (newChatList, oldChatList) => {
        const [newLength, oldLength] = [newChatList.length, oldChatList.length]
        console.log(oldLength, newLength)
        if (oldLength === 0) isFirstHistory.value = true
        else if (newLength - oldLength === 1) isAddedNewMsg.value = true
        else if (newLength - oldLength === 10) isAddedHistory.value = true
      }
    )

    onUpdated(() => {
      // chatListDiv.value.scrollIntoView({
      //   behavior: "auto", // "smooth", "auto"(default)
      //   block: "end", // "start", "center", "end", "nearest"(default)
      //   inline: "nearest", // "start", "center", "end", "nearest"(default)
      // })
      if (isFirstHistory.value) {
        chatListDiv.value.scrollIntoView(false)
        isFirstHistory.value = false
      } else if (isAddedNewMsg.value) {
        chatListDiv.value.scrollIntoView(false)
        isAddedNewMsg.value = false
      } else if (isAddedHistory.value) {
        scrollBarDiv.value.scrollTop =
          scrollBarDiv.value.scrollHeight - oldScrollHeight.value
        isAddedHistory.value = false
      }
    })

    return { chatListDiv, handleScroll, scrollBarDiv }
  },
}
</script>

<style lang="scss" scoped></style>
