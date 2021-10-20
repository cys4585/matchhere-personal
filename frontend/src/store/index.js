import { createStore } from "vuex"

import auth from "@/store/modules/auth"
import project from "@/store/modules/project"
import member from "@/store/modules/member"
import file from "@/store/modules/file"

export default createStore({
  state: {
    alertMessages: {},
  },
  mutations: {
    ADD_MESSAGES(state, message) {
      const key = Date.now()
      state.alertMessages[key] = message
      setTimeout(() => {
        delete state.alertMessages[key]
      }, 3000)
    },
  },
  actions: {},
  modules: { auth, project, member, file },
})
