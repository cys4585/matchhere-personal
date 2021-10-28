import ChatAPI from "@/api/chat"

export default {
  namespaced: true,
  state: { stompClient: null, targetUserId: null },
  mutations: {
    SET_CHAT_MATERIALS(state, { stompClient, targetUserId }) {
      state.stompClient = stompClient
      state.targetUserId = targetUserId
    },
  },
  actions: {
    async getChatList() {
      try {
        console.log("asdfklasdfhkl")
        const resData = await ChatAPI.getChatList()
        console.log("asdfklasdfhkl")
        return resData
      } catch (error) {
        console.log(error.response)
      }
    },
    async getChatRoomInfo(context, email) {
      try {
        const resData = await ChatAPI.getChatRoomInfo(email)
        return resData
      } catch (error) {
        console.log(error.response)
      }
    },
    async getChatHistory(context, { targetUserId, pageNumber, size }) {
      try {
        const resData = await ChatAPI.getChatHistory(
          targetUserId,
          pageNumber,
          size
        )
        return resData
      } catch (error) {
        console.log(error.response)
      }
    },
  },
  getters: {
    getStompClient(state) {
      return state.stompClient
    },
    getTargetUserId(state) {
      return state.targetUserId
    },
  },
}
