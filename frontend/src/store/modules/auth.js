import AuthAPI from "@/api/auth"

export default {
  namespaced: true,
  state: {
    signupStep: "CheckEmail",
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
    token: {
      grantType: "",
      accessToken: "",
      accessTokenExpiresIn: 0,
      refreshToken: "",
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
    RESET_SIGNUP_FORMDATA(state) {
      state.signupFormData = {
        email: "",
        password: "",
        nickname: "",
        name: "",
        city: "",
        position: "",
        dpositionList: [],
        techList: [],
      }
      localStorage.setItem(
        "signupFormData",
        JSON.stringify(state.signupFormData)
      )
    },
    SET_SIGNUP_STEP(state, signupStep = "CheckEmail") {
      state.signupStep = signupStep
      localStorage.setItem("signupStep", signupStep)
    },
    SET_TOKEN(state, tokenData) {
      state.token = tokenData
      localStorage.setItem("token", JSON.stringify(tokenData))
    },
  },
  actions: {
    async checkEmail({ commit }, email) {
      try {
        await AuthAPI.checkEmail(email)
        commit("SET_SIGNUP_FORMDATA", { email })
        commit("SET_SIGNUP_STEP", "AuthEmail")
      } catch (error) {
        alert(error)
      }
    },
    async authEmail({ commit }, authCode) {
      try {
        await AuthAPI.authEmail(authCode)
        commit("SET_SIGNUP_STEP", "Signup")
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
    async signup({ state, commit }) {
      try {
        await AuthAPI.signup(state.signupFormData)
        commit("RESET_SIGNUP_FORMDATA")
        commit("SET_SIGNUP_STEP")
      } catch (error) {
        alert(error)
      }
    },
    async login({ commit }, formData) {
      try {
        const tokenData = await AuthAPI.login(formData)
        commit("SET_TOKEN", tokenData)
      } catch (error) {
        alert(error)
      }
    },
    async reissue({ commit }, tokenData) {
      try {
        const { accessToken, refreshToken } = tokenData
        const newTokenData = await AuthAPI.reissue({
          accessToken,
          refreshToken,
        })
        commit("SET_TOKEN", newTokenData)
      } catch (error) {
        alert(error)
        console.log(error)
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
    getSignupStep(state) {
      return state.signupStep
    },
    getIsAuthenticated(state) {
      return state.token.accessToken !== ""
    },
    getToken(state) {
      return state.token
    },
  },
}
