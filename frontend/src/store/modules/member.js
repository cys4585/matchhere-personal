import memberAPI from "@/api/member"

export default {
  namespaced: true,
  state: {
    user: {
      email: "",
      id: 0,
      name: "",
      nickname: "",
    },
    basicInfo: {},
  },
  mutations: {
    SET_USER(state, data) {
      state.user = {
        ...state.user,
        ...data,
      }
    },
    SET_BASIC_INFO(state, basicInfo) {
      state.basicInfo = basicInfo
    },
  },
  actions: {
    async getMypage({ commit }, email) {
      try {
        const profile = await memberAPI.getMypage(email)
        return profile
      } catch (error) {
        commit(
          "ADD_MESSAGE",
          {
            text: "프로필 정보를 가져올 수 없습니다",
            type: "error",
          },
          { root: true }
        )
        throw new Error()
      }
    },
    async getMe({ commit }) {
      try {
        const user = await memberAPI.getMe()
        commit("SET_USER", user)
      } catch (error) {
        console.log(error)
      }
    },
    async getBasicInfo({ commit }) {
      try {
        const basicInfo = await memberAPI.getBasicInfo()
        commit("SET_BASIC_INFO", basicInfo)
        return basicInfo
      } catch (error) {
        commit(
          "ADD_MESSAGE",
          {
            text: "내 기본정보를 가져올 수 없습니다",
            type: "error",
          },
          { root: true }
        )
      }
    },
    async updateBasicInfo({ commit }, data) {
      // TODO: 닉네임 중복 에러 핸들링
      try {
        await memberAPI.updateBasicInfo(data)
        commit(
          "ADD_MESSAGE",
          {
            text: "기본 정보가 업데이트되었습니다",
            type: "success",
          },
          { root: true }
        )
      } catch (error) {
        commit(
          "ADD_MESSAGE",
          {
            text: "업데이트 실패",
            type: "error",
          },
          { root: true }
        )
        console.log(error)
      }
    },
    async getSkills({ commit }) {
      try {
        return await memberAPI.getSkills()
      } catch (error) {
        commit(
          "ADD_MESSAGE",
          {
            text: "내 스킬을 불러올 수 없습니다",
            type: "error",
          },
          { root: true }
        )
      }
    },
    async updateSkills({ commit, dispatch }, data) {
      try {
        await memberAPI.updateSkills(data)
        commit(
          "ADD_MESSAGE",
          {
            text: "업데이트 성공!",
            type: "success",
          },
          { root: true }
        )
      } catch (error) {
        commit(
          "ADD_MESSAGE",
          {
            text: "업데이트 실패",
            type: "error",
          },
          { root: true }
        )
      }
      dispatch("getSkills")
    },
    async getCareerAll({ commit }) {
      try {
        return await memberAPI.getCareerAll()
      } catch (error) {
        commit(
          "ADD_MESSAGE",
          {
            text: "데이터를 가져오는 데 실패했습니다",
            type: "error",
          },
          { root: true }
        )
      }
    },
    async createCareer({ commit }, data) {
      try {
        await memberAPI.createCareer(data)
        commit(
          "ADD_MESSAGE",
          {
            text: "경력이 추가되었습니다",
            type: "success",
          },
          { root: true }
        )
      } catch (error) {
        commit(
          "ADD_MESSAGE",
          {
            text: "추가 실패",
            type: "error",
          },
          { root: true }
        )
        console.log(error)
      }
    },
    async getCareer({ commit }, careerId) {
      try {
        return await memberAPI.getCareer(careerId)
      } catch (error) {
        commit(
          "ADD_MESSAGE",
          {
            text: "경력을 불러오지 못했습니다",
            type: "error",
          },
          { root: true }
        )
      }
    },
    async updateCareer({ commit }, data) {
      try {
        const career = await memberAPI.updateCareer(data)
        commit(
          "ADD_MESSAGE",
          {
            text: "경력을 업데이트했습니다",
            type: "success",
          },
          { root: true }
        )
        return career
      } catch (error) {
        commit(
          "ADD_MESSAGE",
          {
            text: "경력을 업데이트하지 못했습니다",
            type: "error",
          },
          { root: true }
        )
      }
    },
    async createCertification({ commit }, data) {
      try {
        await memberAPI.createCertification(data)
        commit(
          "ADD_MESSAGE",
          {
            text: "자격증이 추가되었습니다",
            type: "success",
          },
          { root: true }
        )
      } catch (error) {
        commit(
          "ADD_MESSAGE",
          {
            text: "추가 실패",
            type: "error",
          },
          { root: true }
        )
        console.log(error)
      }
    },
    async getCertification({ commit }, certificationId) {
      try {
        return await memberAPI.getCertification(certificationId)
      } catch (error) {
        commit(
          "ADD_MESSAGE",
          {
            text: "자격증을 불러오지 못했습니다",
            type: "error",
          },
          { root: true }
        )
      }
    },
    async updateCertification({ commit }, data) {
      try {
        const certification = await memberAPI.updateCertification(data)
        commit(
          "ADD_MESSAGE",
          {
            text: "자격증을 업데이트했습니다",
            type: "success",
          },
          { root: true }
        )
        return certification
      } catch (error) {
        commit(
          "ADD_MESSAGE",
          {
            text: "자격증을 업데이트하지 못했습니다",
            type: "error",
          },
          { root: true }
        )
      }
    },
  },
  getters: {
    getMyEmail(state) {
      return state.user?.email
    },
  },
}
