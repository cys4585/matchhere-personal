import axios from 'axios';
import { ElMessage } from 'element-plus';
// const BASE_URL = '';
const BASE_URL = 'http://j5d105.p.ssafy.io:8080';
const header = {
  headers: {
    Authorization: `Bearer ${localStorage.getItem('accessToken')}`,
    'Content-Type': 'application/json',
  },
};

export const study = {
  // 모듈별로 구분이 가능하게 하기 위해(독립적이기 위해) vuex namespaced: true
  namespaced: true,
  state: {
    totalStudyList: [], //전체 스터디 목록
    recommendStudyList: [], //추천 스터디 목록
    studyId: '',
    studyInfo: {}, //스터디 수정을 위한 정보
    memberNickname: '', //어떤 회원의 정보를 조회할지
    memberId: '', //어떤 회원의 정보를 조회할지
    studyIntroduce: {},
    studyApplications: [],
    studyApplication: {}, //특정 회원의 지원서
    studyBoardIdList: [],
    studyArticleId: '', // 스터디 게시글 아이디
    studyArticleList: [], // 스터디 (공지/일반) 게시글 목록
    studyNoticeBoardId: '', //스터디 공지사항 보드 ID
    studyNormalBoardId: '', // 스터디 게시판 보드 ID
    article: {}, //게시글 내용
    currentPage: '1', //현재 페이지네이션 할 페이지 위치
    checkHost: '', //검사한게 host가 맞는지 아닌지 T/F
  },
  mutations: {
    updateTotalStudyList(state, payload) {
      state.totalStudyList = payload;
    },
    updateRecommendStudyList(state, payload) {
      state.recommendStudyList = payload;
    },
    updateStudyId(state, payload) {
      state.studyId = payload;
    },
    updateStudyInfo(state, payload) {
      state.studyInfo = payload;
    },
    updateStudyMemberNickname(state, payload) {
      state.memberNickname = payload;
    },
    updateStudyMemberId(state, payload) {
      state.memberId = payload;
    },
    updateStudyIntroduce(state, payload) {
      state.studyIntroduce = payload;
    },
    updateStudyApplications(state, payload) {
      state.studyApplications = payload;
    },
    updateStudyApplication(state, payload) {
      state.studyApplication = payload;
    },
    updateBoardIdList(state, payload) {
      state.studyBoardIdList = payload;
    },
    updateArticleId(state, payload) {
      state.studyArticleId = payload;
    },
    updateNoticeArticleList(state, payload) {
      state.studyArticleList = payload;
    },
    updateStudyNoticeBoardId(state, payload) {
      state.studyNoticeBoardId = payload;
    },
    updateStudyNormalBoardId(state, payload) {
      state.studyNormalBoardId = payload;
    },
    updateArticle(state, payload) {
      state.article = payload;
    },
    updateCurrentPage(state, payload) {
      state.currentPage = payload;
    },
    updateCheckHost(state, payload) {
      state.checkHost = payload;
    },
  },
  actions: {
    // 전체 스터디 목록
    getTotalStudyList({ commit }) {
      const res = axios.get(BASE_URL + '/api/study', header).then((res) => {
        commit('updateTotalStudyList', res.data.content);
      });
      return res;
    },
    // 추천 스터디 목록
    getRecommendStudyList({ commit }) {
      axios.get(BASE_URL + '/api/study/recommend', header).then((res) => {
        commit('updateRecommendStudyList', res.data.content);
      });
    },

    // 스터디 생성
    // JSON 형태로 보내야 하고, null인건 null로 보내야함 ""말고
    async createStudy({ commit }, form) {
      axios
        .post(BASE_URL + '/api/study', JSON.stringify(form), header)
        .then((res) => {
          commit('updateStudyId', res.data);
          ElMessage({
            showClose: true,
            message: '스터디 생성에 성공했습니다',
            type: 'success',
          });
        })
        .catch((error) => {
          ElMessage({
            showClose: true,
            message: '문제가 발생했습니다',
            type: 'error',
          });
        });
    },
    // 스터디 수정
    // JSON 형태로 보내야 하고, null인건 null로 보내야함 ""말고
    updateStudy({ commit }, state) {
      axios
        .patch(
          BASE_URL + `/api/study/${state.studyId}`,
          JSON.stringify(state.form),
          header
        )
        .then((res) => {
          // ok 이런거 반환함 저장 ㄴㄴ
          // commit('updateStudyId', res.data);
          ElMessage({
            showClose: true,
            message: '스터디 수정 성공 :)',
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
    },
    // 스터디 수정을 위한 정보 조회
    // studyInfo
    studyInfo({ commit }, studyId) {
      axios
        .get(BASE_URL + `/api/study/infoforupdate/${studyId}`, header)
        .then((res) => {
          commit('updateStudyInfo', res.data);
        });
    },
    callUpdateStudyId({ commit }, data) {
      commit('updateStudyId', data);
    },
    callUpdateArticleId({ commit }, data) {
      commit('updateArticleId', data);
    },
    introduce({ commit }, studyId) {
      const res = axios
        .get(BASE_URL + `/api/study/${studyId}`, header)
        .then((res) => {
          commit('updateStudyIntroduce', res.data);
        });
      return res;
    },
    checkHost({ commit }, data) {
      axios
        .get(BASE_URL + '/api/auth/check/nickname/' + data, header)
        .then((res) => {
          commit('updateCheckHost', res.data);
        });
    },
    // 해당 스터디 전체 신청서 조회
    applicationAll({ commit }, data) {
      const res = axios
        .get(BASE_URL + '/api/studyapplication/all/' + data, header)
        .then((res) => {
          commit('updateStudyApplications', res.data);
        });
      return res.data;
    },
    // 해당 스터디 특정 회원 신청서 조회
    applicationOne({ commit }, form) {
      const res = axios
        .get(
          BASE_URL +
            `/api/studyapplication/all/${form.studyId}/${form.nickname}`,
          header
        )
        .then((res) => {
          commit('updateStudyApplication', res.data);
        });
      return res.data;
    },

    // 지원서 정보 가져오기
    getStudyApplicationOne({ commit }, para) {
      axios
        .get(
          BASE_URL +
            `/api/studyapplication/one/${para.studyId}/${para.memberId}`,
          header
        )
        .then((res) => {
          commit('updateStudyApplication', res.data);
        });
      // return res.data;
    },

    getBoardId({ commit }, data) {
      const res = axios.get(BASE_URL + `/api/study/${data}/boards`, header);
      res.then((res) => {
        commit('updateBoardIdList', res.data);
      });
      // return res.data;
    },
    // 게시글 작성
    createArticle({ commit }, param) {
      const articleContent = {
        content: param.content,
        title: param.title,
      };

      axios
        .post(
          BASE_URL + `/api/studyboards/${param.boardId}/articles`,
          JSON.stringify(articleContent),
          header
        )
        .then((res) => {
          commit('updateArticleId', res.data);
          ElMessage({
            showClose: true,
            message: '게시글 작성을 성공했습니다.',
            type: 'success',
          });
        })
        .catch((error) => {
          ElMessage({
            showClose: true,
            message: '문제가 발생했습니다.',
            type: 'error',
          });
        });
    },
    // 게시글 수정
    updateArticle({ commit }, param) {
      const articleContent = {
        studyArticleUpdateRequestDto: {
          content: param.studyArticleUpdateRequestDto.content, //초기값 세팅
          title: param.studyArticleUpdateRequestDto.title,
        },
      };

      axios
        .put(
          BASE_URL +
            `/api/studyboards/${param.boardid}/articles/${param.articleid}`,
          JSON.stringify(articleContent.studyArticleUpdateRequestDto),
          header
        )
        .then((res) => {
          commit('updateArticle', res.data);
          ElMessage({
            showClose: true,
            message: '게시글 수정에 성공했습니다.',
            type: 'success',
          });
        })
        .catch((error) => {
          ElMessage({
            showClose: true,
            message: '문제가 발생했습니다.',
            type: 'error',
          });
        });
    },

    // 게시판 무관: 글 목록 가져오기
    getArticleList({ commit }, boardid) {
      axios
        .get(BASE_URL + `/api/studyboards/${boardid.value}/articles`, header)
        .then((res) => {
          commit('updateNoticeArticleList', res.data);
        });
    },

    // 페이지별 게시글 요청
    getArticleListPage({ commit }, param) {
      axios
        .get(
          BASE_URL +
            `/api/studyboards/${param.boardid}/articles?page=${param.pageNumber}`,
          header
        )
        .then((res) => {
          commit('updateNoticeArticleList', res.data);
        });
    },
    // 스터디 신청
    applicateStudy({ commit }, form) {
      const res = axios
        .post(
          BASE_URL + `/api/studyapplication/${form.studyId}`,
          JSON.stringify(form),
          header
        )
        .then((res) => {
          ElMessage({
            showClose: true,
            message: '스터디 신청에 성공했습니다',
            type: 'success',
          });
        })
        .catch((error) => {
          ElMessage({
            showClose: true,
            message: '문제가 발생했습니다',
            type: 'error',
          });
        });
      return res.data;
    },

    // 스터디 신청 수락
    approvalStudy({ commit }, form) {
      axios
        .post(
          BASE_URL +
            `/api/studyapplication/approval/${form.studyId}/${form.memberId}`,
          form,
          header
        )
        .then((res) => {
          ElMessage({
            showClose: true,
            message: '스터디 신청 수락 :)',
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
    },

    // 스터디 신청 거절
    rejectStudy({ commit }, form) {
      axios
        .delete(
          BASE_URL + `/api/studyapplication/${form.studyId}/${form.memberId}`,
          header
        )
        .then((res) => {
          ElMessage({
            showClose: true,
            message: '스터디 신청을 거절했습니다.',
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
    },

    // 특정 게시글 하나 조회
    getArticleDetail({ commit }, form) {
      const res = axios.get(
        BASE_URL +
          `/api/studyboards/${form.boardid}/articles/${form.articleid}`,
        header
      );
      res.then((res) => {
        commit('updateArticle', res.data);
      });
      return res.data;
    },

    // 특정 게시글 하나 삭제
    deleteArticle({ commit }, form) {
      axios
        .delete(
          BASE_URL +
            `/api/studyboards/${form.boardid}/articles/${form.articleid}`,
          header
        )
        .then((res) => {
          commit('updateArticle', {});
          ElMessage({
            showClose: true,
            message: '게시글 삭제 성공 :)',
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
    },

    deleteStudy({ commit }, form) {
      const res = axios
        .delete(BASE_URL + `/api/study/${form}`, header)
        .then(() => {
          commit('updateStudyId', '');
          ElMessage({
            showClose: true,
            message: '스터디 삭제 성공 :)',
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
    },

    quitStudy({ commit }, form) {
      axios
        .delete(BASE_URL + `/api/study/${form}/member`, header)
        .then(() => {
          // commit('updateStudyId', '');
          ElMessage({
            showClose: true,
            message: '스터디 탈퇴 성공 :)',
            type: 'success',
          });
        })
        .catch((err) => {
          ElMessage({
            showClose: true,
            message: '문제가 발생했습니다 :(',
            type: 'error',
          });
        });
    },

    updateStudyMemberNickname({ commit }, data) {
      commit('updateStudyMemberNickname', data);
    },
  },
  getters: {
    totalStudyGetter: (state) => {
      return state.totalStudyList;
    },
    recommendStudyListGetter: (state) => {
      return state.recommendStudyList;
    },
    studyIdGetter: (state) => {
      return state.studyId;
    },
    studyInfoGetter: (state) => {
      return state.studyInfo;
    },

    studyIntroduceGetter: (state) => {
      return state.studyIntroduce;
    },
    studyApplicationsGetter: (state) => {
      return state.studyApplications;
    },
    studyApplicationGetter: (state) => {
      return state.studyApplication;
    },
    studyBoardIdListGetter: (state) => {
      return state.studyBoardIdList;
    },
    studyArticleListGetter: (state) => {
      return state.studyArticleList;
    },
    studyNoticeBoardIdGetter: (state) => {
      return state.studyNoticeBoardId;
    },
    studyNormalBoardIdGetter: (state) => {
      return state.studyNormalBoardId;
    },
    studyArticleIdGetter: (state) => {
      return state.studyArticleId;
    },
    articleGetter: (state) => {
      return state.article;
    },
    currentPageGetter: (state) => {
      return state.currentPage;
    },
    studyMemberNicknameGetter: (state) => {
      return state.memberNickname;
    },
    memberIdGetter: (state) => {
      return state.memberId;
    },
    checkHostGetter: (state) => {
      return state.checkHost;
    },
  },
  modules: {},
};
