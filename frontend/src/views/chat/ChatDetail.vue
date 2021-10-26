<template>
  <div class="flex-1 flex flex-col">
    <ChatHeader />
    <ChatListContainer />
    <ChatControler />
  </div>
</template>

<script>
import SockJS from "sockjs-client"
import Stomp from "stompjs"
import ChatHeader from "@/components/chat/ChatHeader.vue"
import ChatListContainer from "@/components/chat/ChatListContainer.vue"
import ChatControler from "@/components/chat/ChatControler.vue"
import { ref } from "@vue/reactivity"
import { onMounted } from "@vue/runtime-core"
export default {
  name: "ChatDetail",
  components: { ChatHeader, ChatListContainer, ChatControler },
  setup() {
    const stompClient = ref()
    const connection = () => {
      const token = JSON.parse(localStorage.getItem("token")).accessToken
      const headers = {
        Authorization: token,
      }

      const socket = new SockJS("http://127.0.0.1:8080/api/chat")
      stompClient.value = Stomp.over(socket)

      stompClient.value.connect(headers, () => {
        stompClient.value.subscribe("")
      })
    }

    onMounted(() => {
      connection()
    })

    return {
      connection,
    }
  },
}
</script>
<style lang="scss" scoped></style>
