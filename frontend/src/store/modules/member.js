import memberAPI from "@/api/member"

export default {
  namespaced: true,
  state: {
    basicInfo: {},
  },
  mutations: {
    SET_BASIC_INFO(state, basicInfo) {
      state.basicInfo = basicInfo
    },
  },
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
    async getBasicInfo({ commit }) {
      try {
        const basicInfo = await memberAPI.getBasicInfo()
        commit("SET_BASIC_INFO", basicInfo)
        return basicInfo
      } catch (error) {
        commit(
          "ADD_MESSAGES",
          {
            text: "내 기본정보를 가져올 수 없습니다",
            type: "error",
          },
          { root: true }
        )
      }
    },
    async updateBasicInfo({ commit }, submitData) {
      // TODO: 닉네임 중복 에러 핸들링
      try {
        await memberAPI.updateBasicInfo(submitData)
        commit(
          "ADD_MESSAGES",
          {
            text: "기본 정보가 업데이트되었습니다",
            type: "success",
          },
          { root: true }
        )
      } catch (error) {
        commit(
          "ADD_MESSAGES",
          {
            text: "업데이트 실패",
            type: "error",
          },
          { root: true }
        )
        console.log(error)
      }
    },
  },
  getters: {},
}
