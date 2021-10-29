import studyAPI from "@/api/study"

export default {
  namespaced: true,
  state: {},
  mutations: {},
  actions: {
    async createStudy({ commit }, data) {
      const res = await studyAPI.createStudy(data)
      commit(
        "ADD_MESSAGE",
        {
          text: res.text,
          type: res.type,
        },
        { root: true }
      )
      return res
    },
    async getStudyList({ commit }) {
      const res = await studyAPI.getStudyList()
      if (res.type === "error") {
        commit(
          "ADD_MESSAGE",
          {
            text: res.text,
            type: res.type,
          },
          { root: true }
        )
      }
      return res.data
    },
    async getStudy({ commit }, studyId) {
      await studyAPI.updateViewCount(studyId)
      const res = await studyAPI.getStudy(studyId)
      if (res.type === "error") {
        commit(
          "ADD_MESSAGE",
          {
            text: res.text,
            type: res.type,
          },
          { root: true }
        )
      }
      return res.data
    },
    async submitApplication(_, payload) {
      await studyAPI.submitApplication(payload)
    },
    async getStudyBoards(_, studyId) {
      return await studyAPI.getStudyBoards(studyId)
    },
  },
  getters: {},
}
