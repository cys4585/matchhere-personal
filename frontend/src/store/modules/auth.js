import AuthAPI from "@/api/auth"

export default {
  namespaced: true,
  state: {
    emailCertRequestDto: {
      email: "",
      authCode: "",
    },
    signupFormData: {
      id: 0,
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
    SET_EMAIL_CERT_DTO(state, payload) {
      state.emailCertRequestDto = { ...state.emailCertRequestDto, ...payload }
    },
    SET_SIGNUP_FORMDATA(state, formData) {
      state.signupFormData = { ...state.signupFormData, ...formData }
    },
    RESET_SIGNUP_FORMDATA(state) {
      state.signupFormData = {
        id: 0,
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
    async sendEmailForSignup({ commit }, email) {
      // return으로 Error를 보내주지 않아 trycatch를 사용할 수 없다.
      try {
        const id = await AuthAPI.sendEmailForSignup(email)
        commit(
          "ADD_MESSAGES",
          { text: "인증 메일을 전송했습니다" },
          { root: true }
        )
        commit("SET_EMAIL_CERT_DTO", { email })
        commit("SET_SIGNUP_FORMDATA", { id })
        return id
      } catch (error) {
        commit(
          "ADD_MESSAGES",
          { text: "이미 가입된 이메일입니다", type: "error" },
          { root: true }
        )
        return
      }
    },
    async sendEmailForFindPW({ commit }, email) {
      // return으로 Error를 보내주지 않아 trycatch를 사용할 수 없다.
      try {
        const status = await AuthAPI.sendEmailForFindPW(email)
        if (status) {
          commit(
            "ADD_MESSAGES",
            { text: "인증 메일을 전송했습니다" },
            { root: true }
          )
          commit("SET_EMAIL_CERT_DTO", { email })
        } else {
          commit(
            "ADD_MESSAGES",
            { text: "가입하지 않은 이메일입니다", type: "error" },
            { root: true }
          )
        }
        return status
      } catch (error) {
        alert(error)
        return
      }
    },
    async confirmEmailAuthCode({ getters, commit }, authCode) {
      try {
        commit("SET_EMAIL_CERT_DTO", { authCode })
        const id = await AuthAPI.confirmEmailAuthCode(
          getters["getEmailAuthData"]
        )
        commit("ADD_MESSAGES", { text: "이메일 인증 성공 😎" }, { root: true })
        console.log(id)
      } catch (error) {
        commit(
          "ADD_MESSAGES",
          { text: "올바르지 않은 인증코드입니다", type: "error" },
          { root: true }
        )
        throw Error()
      }
    },
    async submitStepOne({ commit }, formData) {
      try {
        const isDuplicated = await AuthAPI.checkNickname(formData.nickname)
        if (!isDuplicated) {
          commit("SET_SIGNUP_FORMDATA", formData)
        } else {
          commit(
            "ADD_MESSAGES",
            { text: "이미 존재하는 닉네임입니다 🥲", type: "error" },
            { root: true }
          )
        }
        return isDuplicated
      } catch (error) {
        throw new Error(error)
      }
    },
    async signup({ state, commit }, formData) {
      commit("SET_SIGNUP_FORMDATA", formData)
      try {
        await AuthAPI.signup(state.signupFormData)
        commit("RESET_SIGNUP_FORMDATA")
        commit("ADD_MESSAGES", { text: "회원가입 성공 😎" }, { root: true })
      } catch (error) {
        throw new Error(error.message)
      }
    },
    async login({ commit }, formData) {
      try {
        const tokenData = await AuthAPI.login(formData)
        commit("SET_TOKEN", tokenData)
        commit("ADD_MESSAGES", { text: "안녕하세요 👍" }, { root: true })
      } catch (error) {
        commit(
          "ADD_MESSAGES",
          { text: "이메일 또는 비밀번호를 확인하세요", type: "error" },
          {
            root: true,
          }
        )
        throw new Error()
      }
    },
    async logout({ commit }) {
      commit("RESET_TOKEN")
      commit(
        "ADD_MESSAGES",
        {
          text: "로그아웃 성공",
          type: "success",
        },
        { root: true }
      )
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
    // async sendEmailForFindPassword(_, email) {
    //   console.log(email)
    //   return
    // },
    // async confirmAuthCodeForFindPassword(_, authCode) {
    //   console.log(authCode)
    //   return
    // },
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
    getIsAuthenticated(state) {
      return state.token.accessToken !== ""
    },
    getToken(state) {
      return state.token
    },
    getEmailAuthData(state) {
      return {
        requestData: { ...state.emailCertRequestDto },
        id: state.signupFormData.id,
      }
    },
  },
}
