import { createStore } from "vuex"

import auth from "@/store/modules/auth"
import project from "@/store/modules/project"
import file from "@/store/modules/file"

export default createStore({
  state: {},
  mutations: {},
  actions: {},
  modules: { auth, project, file },
})
