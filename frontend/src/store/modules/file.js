// import axios from "axios"
import FileAPI from "@/api/file"

export default {
  namespaced: true,
  state: {},
  mutations: {},
  actions: {
    async uploadFile(context, file) {
      const uuid = await FileAPI.uploadFile(file)
      return uuid
    },
  },
  getters: {},
}
