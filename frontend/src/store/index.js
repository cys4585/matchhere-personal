import { createStore } from "vuex"

import auth from "@/store/modules/auth"
import project from "@/store/modules/project"

export default createStore({
  state: {},
  mutations: {},
  actions: {},
  modules: { auth, project },
})
