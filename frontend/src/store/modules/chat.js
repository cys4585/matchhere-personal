import ChatAPI from "@/api/chat"

export default {
  namespaced: true,
  state: {},
  mutations: {},
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
  },
  getters: {},
}
