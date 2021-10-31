import { createStore } from "vuex"

import auth from "@/store/modules/auth"
import project from "@/store/modules/project"
import member from "@/store/modules/member"
import file from "@/store/modules/file"
import chat from "@/store/modules/chat"
import study from "@/store/modules/study"
import club from "@/store/modules/club"

export default createStore({
  state: {
    alertMessages: {},
    modalOpen: false,
    isChatDetailPage: false,
  },
  mutations: {
    ADD_MESSAGE(state, message) {
      const key = Date.now()
      state.alertMessages[key] = message
      setTimeout(() => {
        delete state.alertMessages[key]
      }, 3000)
    },
    SET_MODAL_OPEN(state, modalState) {
      state.modalOpen = modalState
    },
    SET_IS_CHAT_DETAIL_PAGE(state, isChatDetailPage) {
      console.log(state.isChatDetailPage)
      state.isChatDetailPage = isChatDetailPage
      console.log(state.isChatDetailPage)
    },
  },
  actions: {},
  getters: {
    getIsChatDetailPage(state) {
      return state.isChatDetailPage
    },
  },
  modules: { auth, project, member, file, chat, study, club },
})
