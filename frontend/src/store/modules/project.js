// import axios from "axios"
import ProjectAPI from "@/api/project"

export default {
  namespaced: true,
  state: {
    myClubList: [],
    projectInfo: {},
  },
  mutations: {
    SET_MY_CLUB_LIST(state, myClubList) {
      state.myClubList = myClubList
    },
    SET_PROJECT_INFO(state, projectInfo) {
      state.projectInfo = projectInfo
    },
  },
  actions: {
    async getMyClubList({ commit }) {
      const { clubs } = await ProjectAPI.getMyClubList()
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
        console.log(`error.response: ${error.response}`)
        const { status } = error.response
        throw Error(`${status} 문제가 발생했어!`)
      }
    },
    async getProject({ commit }, projectId) {
      console.log("projectId:", projectId)
      const projectInfo = await ProjectAPI.getProject(projectId)
      console.log(projectInfo)
      commit("SET_PROJECT_INFO", projectInfo)
      return projectInfo
    },
    async applyForParticipation({ commit }, { position, introduce }) {
      console.log(position)
      console.log(introduce)
      console.log(commit)
    },
  },
  getters: {
    getMyClubList(state) {
      return state.myClubList
    },
  },
}
