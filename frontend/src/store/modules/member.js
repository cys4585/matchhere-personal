import memberAPI from "@/api/member"

export default {
  namespaced: true,
  state: {},
  mutations: {},
  actions: {
    async getMypage({ commit }, email) {
      try {
        const profile = await memberAPI.getMypage(email)
        return profile
      } catch (error) {
        commit(
          "ADD_MESSAGES",
          {
            text: "프로필 정보를 가져올 수 없습니다",
            type: "error",
          },
          { root: true }
        )
        throw new Error()
      }
    },
  },
  getters: {},
}
