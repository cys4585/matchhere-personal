import axios from 'axios';
// const BASE_URL = 'http://localhost:8080';
const BASE_URL = 'http://j5d105.p.ssafy.io:8080';
const header = { headers: { 'Content-Type': 'application/json' } };

export const auth = {
  // 모듈별로 구분이 가능하게 하기 위해(독립적이기 위해) vuex namespaced: true
  namespaced: true,
  state: {
    // category 1:스터디 2:프로젝트 3:클럽
    category: null,
    form: {
      email: '',
      name: '',
      nickname: '',
      password: '',
      affirmPassword: '',
      position: '',
      city: '',
      expTechList: [],
      beginTechList: [],
      dpositionList: [],
    },
  },
  mutations: {
    setCategory(state, value) {
      state.category = value;
    },
  },
  actions: {
    // 첫번째 인자: context를 조작하기 위한 명령어 종류
    // 두번째 인자: 넘길 값들(payload)
    // 여기서의 form은 SignIn.vue의 signIn 함수에서 dispatch로 보내는 state.form임.
    signIn({ commit }, form) {
      // swagger url경로
      const res = axios.post(
        BASE_URL + '/api/auth/login',
        JSON.stringify(form),
        header
      );
      return res;
    },

    signUp({ commit }, form) {
      const res = axios.post(
        BASE_URL + '/api/auth/signup',
        JSON.stringify(form),
        header
      );
      return res;
    },

    checkEmail({ commit }, form) {
      const res = axios.get(BASE_URL + `/api/auth/check/email/${form}`);
      return res;
    },

    checkNickName({ commit }, form) {
      // swagger url경로
      const res = axios.get(BASE_URL + `/api/auth/check/nickname/${form}`);
      return res;
    },
  },
  modules: {},
};
