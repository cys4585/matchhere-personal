<template>
  <div class="flex flex-col h-screen w-screen max-w-screen">
    <ChatHeader :targetEmail="targetEmail" />
    <ChatListContainer
      :chatList="chatList"
      :myId="myId"
      @handleScroll="handleGetHistory"
    />
    <hr />
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
    const targetEmail = ref(route.params.email)
    const targetUserId = ref()
    const chatList = ref([])
    const myId = ref()
    const currentPage = ref(0)
    const lastPage = ref()

    onMounted(async () => {
      const { email } = route.params
      const resData = await store.dispatch("chat/getChatRoomInfo", email)
      roomId.value = resData.room_id
      targetUserId.value = resData.user_id
      // console.log(resData)

      myId.value = roomId.value
        .split("-")
        .filter((id) => Number(id) !== targetUserId.value)[0]

      const chatHistory = await store.dispatch("chat/getChatHistory", {
        targetUserId: targetUserId.value,
        pageNumber: currentPage.value++,
        size: 20,
      })
      // console.log(chatHistory)
      lastPage.value = chatHistory.totalPages - 1
      chatList.value = chatHistory.content.reverse()
      // console.log(chatList.value)
      // 웹소켓 연결
      connection()
    })
    onBeforeUnmount(() => disconnection())

    const handleGetHistory = async () => {
      // console.log(currentPage.value)
      if (currentPage.value <= lastPage.value) {
        const chatHistory = await store.dispatch("chat/getChatHistory", {
          targetUserId: targetUserId.value,
          pageNumber: currentPage.value++,
          size: 10,
        })
        // console.log(currentPage.value)
        // console.log(chatHistory)
        lastPage.value = chatHistory.totalPages - 1
        chatList.value = [...chatHistory.content.reverse(), ...chatList.value]
        // console.log(chatList.value)
      }
    }

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
            // chatList.value.push(JSON.parse(msg.body))
            chatList.value = [...chatList.value, JSON.parse(msg.body)]
            // console.log(chatList.value)
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

    return { chatList, myId, currentPage, handleGetHistory, targetEmail }
  },
}
</script>
<style lang="scss" scoped></style>
