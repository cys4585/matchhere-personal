// import axios from "axios"
import res from "@/assets/test-ys.json"
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
      const res = await ProjectAPI.getMyClubList()
      commit("SET_MY_CLUB_LIST", res.data.clubs)
    },
    async createProject(context, formData) {
      const res = await ProjectAPI.createProject(formData)
      console.log(res)
    },
    async getProject({ commit }, projectId) {
      console.log("projectId:", projectId)
      // const res = await axios.get(
      //   `http://127.0.0.1:8080/api/project/${projectId}`
      // )
      // console.log(project)
      const projectInfo = res.data
      commit("SET_PROJECT_INFO", projectInfo)
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
