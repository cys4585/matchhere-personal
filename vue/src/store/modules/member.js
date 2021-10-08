import axios from 'axios';
import { ElMessage } from 'element-plus';
// const BASE_URL = '';
const BASE_URL = 'http://j5d105.p.ssafy.io:8080';
const header = {
  headers: {
    Authorization: `Bearer ${localStorage.getItem('accessToken')}`,
  },
};

export const member = {
  // 모듈별로 구분이 가능하게 하기 위해(독립적이기 위해) vuex namespaced: true
  namespaced: true,
  state: {
    mypage: {}, // 내 정보.
    userEmail: '', //어떤 유저의 정보를 보고 싶은지
    userInfo: {}, //보고자하는 유저의 정보
  },
  mutations: {
    updateMypage(state, payload) {
      state.mypage = payload;
    },
    updateUserEmail(state, payload) {
      state.userEmail = payload;
    },
    updateUserInfo(state, payload) {
      state.userInfo = payload;
    },
  },
  actions: {
    // 마이페이지 정보
    readMyPage({ commit }) {
      const res = axios
        .get(BASE_URL + `/api/member/mypage`, header)
        .then((res) => {
          commit('updateMypage', res.data);
        });
      return res;
    },
    // 다른 회원 정보 가져오기
    readInfoPage({ commit }, email) {
      // form = encodeURIComponent(form);
      console.log(`${new String(encodeURI(email)).replace('@', '%40')}`);
      const res = axios
        .get(
          BASE_URL +
            `/api/member/mypage/${new String(encodeURI(email)).replace(
              '@',
              '%40'
            )}`,
          {
            headers: {
              Authorization: `Bearer ${localStorage.getItem('accessToken')}`,
            },
          }
        )
        .then((res) => {
          commit('updateUserInfo', res.data);
        });
      return res;
    },
    // 프로필 다운로드
    readProfile({ commit }, form) {
      const res = axios.get(form, {
        headers: {
          // Authorization: `Bearer ${localStorage.getItem('accessToken')}`,
          // 'Content-Type': 'image/png',
        },
      });
      return res;
    },
    // 비밀번호 체크
    checkPassword({ commit }, form) {
      const res = axios.post(BASE_URL + `/api/member/check/password`, form, {
        headers: {
          Authorization: `Bearer ${localStorage.getItem('accessToken')}`,
        },
      });
      return res;
    },
    // 회원 정보 수정
    updateMember({ commit }, form) {
      const res = axios
        .put(BASE_URL + `/api/member`, form, {
          headers: {
            Authorization: `Bearer ${localStorage.getItem('accessToken')}`,
          },
        })
        .then((res) => {
          commit('updateMypage', res.data);
          ElMessage({
            showClose: true,
            message: '회원정보 수정 성공 :D',
            type: 'success',
          });
        })
        .catch((error) => {
          ElMessage({
            showClose: true,
            message: '문제가 발생했습니다 :(',
            type: 'error',
          });
        });
      return res;
    },
  },
  getters: {
    mypageGetter: (state) => {
      return state.mypage;
    },
    myStudyListGetter: (state) => {
      return state.mypage.myStudyList;
    },
    userEmailGetter: (state) => {
      return state.userEmail;
    },
    userInfoGetter: (state) => {
      return state.userInfo;
    },
  },
  modules: {},
};
