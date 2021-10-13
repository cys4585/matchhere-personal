import AuthAPI from "@/api/auth"

export default {
  namespaced: true,
  state: {
    registerFormData: {
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
    SET_REGISTER_FORMDATA(state, formData) {
      state.registerFormData = { ...state.registerFormData, ...formData }
      localStorage.setItem(
        "registerFormData",
        JSON.stringify(state.registerFormData)
      )
    },
  },
  actions: {
    async checkEmail({ commit }, email) {
      try {
        await AuthAPI.checkEmail(email)
        commit("SET_REGISTER_FORMDATA", { email })
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
        commit("SET_REGISTER_FORMDATA", formData)
      } catch (error) {
        alert(error)
      }
    },
  },
  getters: {
    getEmail(state) {
      return state.registerFormData.email
    },
    getStep(state) {
      if (state.registerFormData.password) {
        return 2
      } else {
        return 1
      }
    },
    getRegisterFormData(state) {
      return state.registerFormData
    },
  },
}
