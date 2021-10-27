<template>
  <div class="flex-1 flex flex-col">
    <ChatHeader />
    <ChatListContainer :chatList="chatList" :myId="myId" />
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
import { onBeforeUnmount, onMounted } from "@vue/runtime-core"
import { useRoute } from "vue-router"
import { useStore } from "vuex"
export default {
  name: "ChatDetail",
  components: { ChatHeader, ChatListContainer, ChatControler },
  setup() {
    const route = useRoute()
    const store = useStore()

    const roomId = ref()
    const targetUserId = ref()
    const chatList = ref()
    const myId = ref()

    onMounted(async () => {
      const { email } = route.params
      const resData = await store.dispatch("chat/getChatRoomInfo", email)
      roomId.value = resData.room_id
      targetUserId.value = resData.user_id
      console.log(resData)

      myId.value = roomId.value
        .split("-")
        .filter((id) => Number(id) !== targetUserId.value)[0]

      const chatHistory = await store.dispatch(
        "chat/getChatHistory",
        targetUserId.value
      )
      console.log(chatHistory)
      chatList.value = chatHistory.content.reverse()
      console.log(chatList.value)
      // 웹소켓 연결
      connection()
    })
    onBeforeUnmount(() => disconnection())

    const stompClient = ref()
    const connection = () => {
      const socket = new SockJS("http://127.0.0.1:8080/api/chat")
      stompClient.value = Stomp.over(socket)

      const token = JSON.parse(localStorage.getItem("token")).accessToken
      const headers = { Authorization: token }

      // 연결
      stompClient.value.connect(
        headers,
        (frame) => {
          console.log("소켓 연결 성공", frame)
          // 구독
          stompClient.value.subscribe(`/room/${roomId.value}`, (msg) => {
            chatList.value.push(JSON.parse(msg.body))
            console.log(chatList.value)
          })
        },
        (err) => console.log(err)
      )

      store.commit("chat/SET_CHAT_MATERIALS", {
        stompClient: stompClient.value,
        targetUserId: targetUserId.value,
      })
    }

    const disconnection = () => {
      if (stompClient.value !== null) {
        stompClient.value.disconnect()
        console.log("Disconnected!")
      }
    }

    return { chatList, myId }
  },
}
</script>
<style lang="scss" scoped></style>
