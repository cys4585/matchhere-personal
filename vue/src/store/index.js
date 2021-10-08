import { createStore } from 'vuex';
import createPersistedState from 'vuex-persistedstate';
import { auth } from '@/store/modules/auth';
import { member } from '@/store/modules/member';
import { chat } from '@/store/modules/chat';
import { study } from '@/store/modules/study';
import axios from 'axios';

// const BASE_URL = '';
const BASE_URL = 'http://j5d105.p.ssafy.io:8080';

export default createStore({
  state: {
    category: '', // category 1:스터디 2:프로젝트 3:클럽
    user: {},
    scrollModal: 'false',
    infoModal: 'false',
    rejectModal: 'false',
    acceptModal: 'false',
    applicationModal: 'false',
    form: {
      email: '',
      name: '',
      nickname: '',
      password: '',
      affirmPassword: '',
      position: '',
      city: '',
    },
  },
  mutations: {
    setCategory(state, value) {
      state.category = value;
    },
    setScrollModal(state, value) {
      state.scrollModal = value;
    },
    setMember(state, value) {
      state.user = value;
    },
    setInfoModal(state, value) {
      state.infoModal = value;
    },
    setRejectModal(state, value) {
      state.rejectModal = value;
    },
    setAcceptModal(state, value) {
      state.acceptModal = value;
    },
    setApplicationModal(state, value) {
      state.applicationModal = value;
    },
  },
  actions: {
    changeScrollModal({ commit }, payload) {
      commit('setScrollModal', payload);
    },
    changeInfoModal({ commit }, payload) {
      commit('setInfoModal', payload);
    },
    changeRejectModal({ commit }, payload) {
      commit('setRejectModal', payload);
    },
    changeAcceptModal({ commit }, payload) {
      commit('setAcceptModal', payload);
    },
    changeApplicationModal({ commit }, payload) {
      commit('setApplicationModal', payload);
    },
    uploadFile({ commit }, formData) {
      const header = {
        headers: {
          Authorization: `Bearer ${localStorage.getItem('accessToken')}`,
          'Content-Type': 'multipart/form-data',
        },
      };
      const res = axios.post(
        BASE_URL + '/api/file/uploadFile',
        formData,
        header
      );
      return res;
    },
  },
  getters: {
    scrollGetter: (state) => {
      return state.scrollModal;
    },
    getUserInfo: (state) => {
      return state.user;
    },
    infoModalGetter: (state) => {
      return state.infoModal;
    },
    rejectModalGetter: (state) => {
      return state.rejectModal;
    },
    acceptModalGetter: (state) => {
      return state.acceptModal;
    },
    applicationModalGetter: (state) => {
      return state.applicationModal;
    },
    categoryGetter: (state) => {
      return state.category;
    },
  },
  modules: {
    auth,
    member,
    chat,
    /* 만약 모듈을 추가하실 필요가 있다면 
    아래 path 프로퍼티에 작성해주시기 바랍니다. */
    study,
  },

  plugins: [
    createPersistedState({
      paths: ['auth', 'member', 'study', 'category'], // <<<<<<여기!
    }),
  ],
});
