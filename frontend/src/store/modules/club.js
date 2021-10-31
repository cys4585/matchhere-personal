// import axios from "axios"
import ClubAPI from "@/api/club"

export default {
  namespaced: true,
  state: {},
  mutations: {},
  actions: {
    async createClub({ commit }, data) {
      const res = await ClubAPI.createClub(data)
      console.log(res)
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
  },
  getters: {},
}
