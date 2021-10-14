import AuthAPI from "@/api/auth"

export default {
  namespaced: true,
  state: {
    signupFormData: {
      email: "",
      password: "",
      nickname: "",
      name: "",
      city: "",
      position: "",
      dpositionList: [],
      techList: [],
    },
  },
  mutations: {
    SET_SIGNUP_FORMDATA(state, formData) {
      state.signupFormData = { ...state.signupFormData, ...formData }
      localStorage.setItem(
        "signupFormData",
        JSON.stringify(state.signupFormData)
      )
    },
  },
  actions: {
    async checkEmail({ commit }, email) {
      try {
        await AuthAPI.checkEmail(email)
        commit("SET_SIGNUP_FORMDATA", { email })
      } catch (error) {
        alert(error)
      }
    },
    async authEmail(_, authCode) {
      try {
        await AuthAPI.authEmail(authCode)
      } catch (error) {
        alert(error)
      }
    },
    async submitStepOne({ commit }, formData) {
      try {
        await AuthAPI.checkNickname(formData.nickname)
        commit("SET_SIGNUP_FORMDATA", formData)
      } catch (error) {
        alert(error)
      }
    },
  },
  getters: {
    getEmail(state) {
      return state.signupFormData.email
    },
    getStep(state) {
      if (state.signupFormData.password) {
        return 2
      } else {
        return 1
      }
    },
    getSIgnupFormData(state) {
      return state.signupFormData
    },
  },
}
