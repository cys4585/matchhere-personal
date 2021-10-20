// import axios from "axios"
import ProjectAPI from "@/api/project"

export default {
  namespaced: true,
  state: {
    myClubList: [],
  },
  mutations: {
    SET_MY_CLUB_LIST(state, myClubList) {
      state.myClubList = myClubList
    },
  },
  actions: {
    async getMyClubList({ commit }) {
      const { clubs } = await ProjectAPI.getMyClubList()
      console.log("my clubs: ", clubs)
      commit("SET_MY_CLUB_LIST", clubs)
    },
    async createProject(context, formData) {
      try {
        // 임시로!
        const resMessage = await ProjectAPI.createProject(formData)
        console.log(resMessage)
        const id = resMessage.replace(/[^0-9]/g, "")
        console.log(id)
        return id
      } catch (error) {
        console.log(error.response)
        const { status } = error.response
        throw Error(`임시: ${status} 문제가 발생했어!`)
      }
    },
    async updateProject(context, { formData, projectId }) {
      try {
        console.log(projectId)
        const resData = await ProjectAPI.updateProject(formData, projectId)
        return resData
      } catch (error) {
        console.log(error.response)
        const { status } = error.response
        throw Error(`임시: ${status} 문제가 발생했어!`)
      }
    },
    async getProject(context, projectId) {
      const projectInfo = await ProjectAPI.getProject(projectId)
      console.log(projectInfo)
      return projectInfo
    },
    async getInfoForUpdate(context, projectId) {
      const projectInfo = await ProjectAPI.getInfoForUpdate(projectId)
      return projectInfo
    },
    async applyForParticipation(context, { reqForm, projectId }) {
      try {
        const res = await ProjectAPI.projectApply(reqForm, projectId)
        console.log(res.data)
      } catch (error) {
        console.log(error.response)
        const { status } = error.response
        switch (status) {
          case 400:
            throw Error("이미 신청을 완료했습니다!")
          default:
            throw Error(`임시: ${status} 문제가 발생했어~`)
        }
      }
    },
  },
  getters: {
    getMyClubList(state) {
      return state.myClubList
    },
  },
}
