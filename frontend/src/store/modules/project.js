// import axios from "axios"
import res from "@/assets/test-ys.json"

export default {
  state: {
    data: "hi",
    projectInfo: {},
  },
  mutations: {
    setProjectInfo(state, projectInfo) {
      state.projectInfo = projectInfo
    },
  },
  actions: {
    async getProject({ commit }, projectId) {
      console.log("projectId:", projectId)
      // const res = await axios.get(
      //   `http://127.0.0.1:8080/api/project/${projectId}`
      // )
      // console.log(project)
      const projectInfo = res.data
      commit("setProjectInfo", projectInfo)
    },
    async applyForParticipation({ commit }, { position, introduce }) {
      console.log(position)
      console.log(introduce)
      console.log(commit)
    },
  },
  getters: {},
}
