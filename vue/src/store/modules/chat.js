import axios from 'axios';
import SockJS from 'sockjs-client';
import Stomp from 'webstomp-client';
import { ElMessage } from 'element-plus';

// const BASE_URL = '';
const BASE_URL = 'http://j5d105.p.ssafy.io:8080';
const header = { headers: { 'Content-Type': 'application/json' } };
const authHeader = {
  headers: {
    Authorization: `Bearer ${localStorage.getItem('accessToken')}`,
    'Content-Type': 'application/json',
  },
};

export const chat = {
  //   모듈별로 구분이 가능하게 하기 위해(독립적이기 위해) vuex namespaced: true
  namespaced: true,
  getters: {
    getChatList: (state) => {
      return state.chatordered;
    },
    getMessages: (state) => {
      return state.chatdetail[`${state.currentCounterpart}`];
    },
    getCurrentCounterpart: (state) => {
      return state.currentCounterpart;
    },
    getCurrentUserId: (state) => {
      return state.currentUserId;
    },
    getUnreadCounts: (state) => {
      return state.unreadCounts;
    },
  },
  state: {
    chatlist: {},
    chatordered: [],
    chatdetail: {},
    currentCounterpart: 0,
    currentUserId: 0,
    stompClient: {},
    connected: false,
    socket: {},
    unreadSession: {},
    unreadCounts: 0,
  },
  mutations: {
    cleanup(state) {
      state.chatlist = {};
      state.chatordered = [];
      state.chatdetail = {};
      state.currentCounterpart = 0;
      state.currentUserId = 0;
      state.stompClient = {};
      state.connected = false;
      state.unreadSession = {};
      state.unreadCounts = 0;
      state.socket.close();
      state.socket = {};
    },
    setUnreadFlag(state, payload) {
      if (
        state.unreadSession[payload] === undefined ||
        state.unreadSession[payload] == false
      ) {
        state.unreadCounts++;
        state.unreadSession[payload] = true;
      }
    },
    setReadFlag(state, payload) {
      if (state.unreadSession[payload] === true) {
        state.unreadCounts--;
        state.unreadSession[payload] = false;
      }
    },
    readMyMessage(state, payload) {
      let counterpart = payload.receiverId;
      let sessionMsgs = state.chatdetail[counterpart];
      for (let i = sessionMsgs.length - 1; i >= 0; i--) {
        if (sessionMsgs[i].senderId == state.currentUserId) {
          continue;
        }
        if (sessionMsgs[i].read_time != 1000) {
          break;
        }
        sessionMsgs[i].read_time = payload.sent_time;
      }
    },
    readSignal(state, payload) {
      if (payload.senderId == state.currentUserId) {
        return;
      }
      let counterpart = payload.senderId;
      let sessionMsgs = state.chatdetail[counterpart];
      for (let i = sessionMsgs.length - 1; i >= 0; i--) {
        if (sessionMsgs[i].receiverId == state.currentUserId) {
          continue;
        }
        if (sessionMsgs[i].read_time != 1000) {
          break;
        }
        sessionMsgs[i].read_time = payload.sent_time;
      }
    },
    setCurrentId(state, payload) {
      state.currentUserId = Number(payload);
    },
    setSocket(state, payload) {
      if (!state.connected) {
        state.socket = payload;
        state.connected = true;
      }
    },
    closeSocket(state) {
      if (state.connected) {
        state.connected = false;
        state.socket.close();
        state.socket = undefined;
      }
    },
    setStompClient(state, payload) {
      state.stompClient = payload;
    },
    addSession(state, payload) {
      //payload can be message itself or
      //{msg, counterpart}
      let counterpart = payload.counterpart;
      let data = payload.item;
      let chatlist = state.chatlist;
      if (!(counterpart in chatlist)) {
        chatlist[counterpart] = data;
      } else {
        if (chatlist[counterpart].id < data.id) {
          chatlist[counterpart] = data;
        }
      }
      state.chatordered = Object.entries(state.chatlist).sort(
        (a, b) => b[1].id - a[1].id
      );
    },
    initMessages(state, payload) {
      for (let i = 0; i < payload.length; i++) {
        console.log(payload[i]);
        payload[i].read_time = new Date(payload[i].read_time).getTime();
        payload[i].sent_time = new Date(payload[i].sent_time).getTime();
      }
      state.chatdetail[`${state.currentCounterpart}`] = payload;
    },
    addMessage(state, payload) {
      // let counterpart = Number(`${payload.counterpart}`);
      let counterpart = payload.counterpart;
      let msg = payload.item;
      if (!state.chatdetail[counterpart]) {
        state.chatdetail[counterpart] = [msg];
      } else {
        state.chatdetail[counterpart].push(msg);
      }
    },
    changeSession(state, payload) {
      state.currentCounterpart = payload;
    },
  },
  actions: {
    // 채팅 시작
    startChat({ commit }, body) {
      axios
        .post(
          BASE_URL + '/api/chat/sessions/start',
          JSON.stringify(body),
          authHeader
        )
        .then((res) => {
          ElMessage({
            showClose: true,
            message: '채팅방 생성을 성공했습니다.',
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
    cleanup({ commit }) {
      commit('cleanup');
    },
    startup({ dispatch, commit }, pk) {
      commit('setCurrentId', pk);
      dispatch('connectSocket');
      dispatch('initSession', pk);
    },

    sendMessage({ state }, msg) {
      if (!state.connected) {
        //예외처리
        return false;
      } else {
        state.stompClient.send(
          `/pub/${msg.receiverId}`,
          JSON.stringify(msg),
          {}
        );
        return true;
      }
    },
    getCounterpart({ state }, msg) {
      return msg.senderId != state.currentId ? msg.receiverId : msg.senderId;
    },
    connectSocket({ state, dispatch, commit }) {
      // const serverURL = 'http://localhost:8080/api/socket/chat'; // 서버 채팅 주소
      const serverURL = 'http://j5d105.p.ssafy.io:8080/api/socket/chat'; // 서버 채팅 주소
      let socket = new SockJS(serverURL);
      socket.onclose = function () {
        state.stompClient.disconnect();
        state.stompClient = undefined;
        state.socket = undefined;
      };
      commit('setSocket', socket);

      // store.commit('stompSetter', Stomp.over(socket));
      let stompClient = Stomp.over(socket);
      commit('setStompClient', stompClient);
      stompClient.connect(
        {},
        (frame) => {
          // 구독 == 채팅방 입장.
          stompClient.subscribe('/sub/' + `${state.currentUserId}`, (res) => {
            const item = JSON.parse(res.body);
            // stomp on
            // item.read_time = new Date(item.read_time);
            // item.sent_time = new Date(item.sent_time);
            switch (item.type) {
              case 1: // 일반 메세지
                // let counterpart = dispatch('getCounterpart', item)
                // .then((res) =>{
                //     console.log(res);
                //     return res;
                // });
                let counterpart =
                  item.senderId == state.currentUserId
                    ? item.receiverId
                    : item.senderId;
                // item.read_time = new Date(item.read_time);
                // item.sent_time = new Date(item.sent_time);

                commit('addSession', { item, counterpart });
                commit('addMessage', { item, counterpart });
                if (item.senderId == state.currentCounterpart) {
                  dispatch('sendMessage', {
                    type: 2,
                    senderId: state.currentUserId,
                    receiverId: state.currentCounterpart,
                    sent_time: 1000,
                    read_time: 1000,
                    content: '',
                  });
                  // 메세지 보내서 읽고있음을 알려주기
                  // unreadflag 설정 ㄴㄴ
                } else {
                  commit('setUnreadFlag', counterpart);
                }
                break;
              case 2:
                console.log('readSignal');
                if (item.receiverId == state.currentUserId) {
                  commit('readSignal', item);
                } else {
                  commit('readMyMessage', item);
                  commit('setReadFlag', item.receiverId);
                }
                break;
              default:
                break;
            }
          });
        },
        (error) => {
          // 소켓 연결 실패
        }
      );
    },
    changeSession({ commit }, changeId) {
      commit('changeSession', changeId);
    },

    //     // 첫번째 인자: context를 조작하기 위한 명령어 종류
    //     // 두번째 인자: 넘길 값들(payload)
    //     // 여기서의 form은 SignIn.vue의 signIn 함수에서 dispatch로 보내는 state.form임.
    initSession({ commit, state }, currentId) {
      // currentId += 0;
      // currentId = "asdf";
      axios
        .get(
          // 'http://localhost:8080/api/chat/sessions/' + `${state.currentUserId}`,
          'http://j5d105.p.ssafy.io:8080/api/chat/sessions/' +
          // `${state.currentUserId}`,
          // JSON.stringify(form),
          { headers: { 'Content-Type': 'application/json' } }
        )
        .then((response) => {
          response.data.forEach((item) => {
            let counterpart =
              item['senderId'] == state.currentUserId
                ? item['receiverId']
                : item['senderId'];
            item.read_time = new Date(item.read_time).getTime();
            item.sent_time = new Date(item.sent_time).getTime();

            if (
              item.receiverId == state.currentUserId &&
              item.read_time == 1000
            ) {
              let counterpart =
                item.senderId == state.currentUserId
                  ? item.receiverId
                  : item.senderId;
              this.commit('chat/setUnreadFlag', counterpart);
            }
            commit('addSession', { counterpart, item });
          });
        });
    },
    loadMessages({ state, commit }) {
      axios
        .get(
          // 'http://localhost:8080/api/chat/messages/' +
            'http://j5d105.p.ssafy.io:8080/api/chat/messages/' +
            `${state.currentUserId}` +
            '/' +
            `${state.currentCounterpart}`,
          // JSON.stringify(form),
          { headers: { 'Content-Type': 'application/json' } }
        )
        .then((response) => {
          commit('initMessages', response.data);
        });
    },
    loadMoreMessages({ state, commit }, pk) {
      console.log(`loadMessages`);
      const res = axios
        .get(
          // const serverURL = 'http://114.129.238.179:8080/api/socket/chat';
          // 'http://localhost:8080/api/chat/messages/' +
            'http://j5d105.p.ssafy.io:8080/api/chat/messages/' +
            `${state.currentUserId}` +
            '/' +
            `${state.currentCounterpart}` +
            '/' +
            pk,
          // JSON.stringify(form),
          { headers: { 'Content-Type': 'application/json' } }
        )
        .then((response) => {
          commit('initMessages', response.data);
          console.log(response.data);
        });
    },
  },
  modules: {},
};
