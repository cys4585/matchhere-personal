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
      localStorage.removeItem("signupFormData")
    },
    SET_SIGNUP_STEP(state, signupStep = "CheckEmail") {
      state.signupStep = signupStep
      localStorage.setItem("signupStep", signupStep)
    },
    SET_TOKEN(state, tokenData) {
      state.token = tokenData
      localStorage.setItem("token", JSON.stringify(tokenData))
    },
    RESET_TOKEN(state) {
      state.token = {
        grantType: "",
        accessToken: "",
        accessTokenExpiresIn: 0,
        refreshToken: "",
      }
      localStorage.removeItem("token")
    },
  },
  actions: {
    async checkEmail({ commit }, email) {
      // return으로 Error를 보내주지 않아 trycatch를 사용할 수 없다.
      commit("SET_SIGNUP_STEP", "CheckEmail")
      commit("SET_SIGNUP_FORMDATA", { email })
      try {
        const status = await AuthAPI.checkEmail(email)
        if (status) {
          commit("SET_SIGNUP_STEP", "AuthEmail")
        }
        return status
      } catch (error) {
        alert(error)
        return
      }
    },
    async authEmail({ commit }, authCode) {
      try {
        await AuthAPI.authEmail(authCode)
        commit("SET_SIGNUP_STEP", "Signup")
      } catch (error) {
        throw Error("올바르지 않은 코드입니다")
      }
    },
    async submitStepOne({ commit }, formData) {
      try {
        const isDuplicated = await AuthAPI.checkNickname(formData.nickname)
        if (!isDuplicated) {
          commit("SET_SIGNUP_FORMDATA", formData)
        }
        return isDuplicated
      } catch (error) {
        throw new Error(error)
      }
    },
    async signup({ state, commit }) {
      try {
        await AuthAPI.signup(state.signupFormData)
        commit("RESET_SIGNUP_FORMDATA")
        commit("SET_SIGNUP_STEP")
      } catch (error) {
        throw new Error(error.message)
      }
    },
    async login({ commit }, formData) {
      try {
        const tokenData = await AuthAPI.login(formData)
        commit("SET_TOKEN", tokenData)
      } catch (error) {
        throw new Error(error.message)
      }
    },
    async logout({ commit }) {
      commit("RESET_TOKEN")
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
