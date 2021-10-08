<template>
  <el-row class="height5">
    <el-col :span="24" class=""></el-col>
  </el-row>
  <el-row class="height90">
    <el-col :span="3"></el-col>

    <!-- 채팅방 목록 -->
    <el-col :span="4">
      <div class="box1">
        <div class="height2"></div>
        <div class="height96">
          <div
            v-for="(session, index) in chatList"
            :key="index"
            @click="changeSession(session[1])"
          >
            <div class="box5">
              <div class="box6">
                <div class="box8">
                  <!-- 보내는사람: {{ session[1].senderId }} <br /> -->
                  {{
                    session[1].receiverId == currentId
                      ? session[1].senderId
                      : session[1].receiverId
                  }}번 사용자
                </div>
                <div class="box9">
                  <!-- 메세지 받은 시간: {{ new Date(session[1].sent_time) }} -->
                  <!-- {{ new Date(session[1].sent_time) }} -->
                  {{ calcTime(new Date(session[1].sent_time)) }}
                </div>
              </div>
              <div v-if="session[1].content.length > 12">
                <div class="box7">{{ session[1].content.slice(0, 12) }}...</div>
              </div>
              <div v-else>
                <div class="box7">{{ session[1].content }}</div>
              </div>
            </div>
          </div>
        </div>
        <div class="height2"></div>
      </div>
    </el-col>
    <el-col :span="14">
      <!-- 채팅창 -->
      <div class="box2">
        <div class="box3">
          <!-- 채팅 내역 -->
          <div v-for="msg in chatMessages" :key="msg.id">
            <!-- 내가 보낸 메세지일 경우 -->
            <div class="box12">
              <div
                v-if="msg.receiverId != currentId && msg.type == 1"
                class="me"
              >
                {{ msg.content }}
              </div>
            </div>
            <div>
              <div
                v-if="msg.receiverId != currentId && msg.type == 1"
                class="my_send_time"
              >
                <div class="box12_1">
                  <!-- {{ new Date(msg.sent_time) }} -->
                  {{ new String(new Date(msg.sent_time)).substr(16, 5) }}

                  <!-- 이건 substr(숫자, 숫자)으로 해결가능! -->
                  <!-- {{msg.read_time.getTime()}} -->
                </div>

                <div class="box12_2" v-if="msg.read_time == 1000">읽지않음</div>
                <div class="box12_2" v-if="msg.read_time !== 1000">읽음</div>
              </div>
            </div>
            <!-- 상대방이 보낸 메세지일 경우 -->
            <div class="box13">
              <div
                v-if="msg.receiverId == currentId && msg.type == 1"
                class="theother"
              >
                {{ msg.content }}
              </div>
            </div>
            <div
              v-if="msg.receiverId == currentId && msg.type == 1"
              class="other_send_time"
            >
              <div class="box13_1">
                {{ new String(new Date(msg.sent_time)).substr(16, 5) }}
                <!-- {{ calcTime(new Date(msg.sent_time)) }} -->
              </div>
            </div>
          </div>
        </div>
        <div class="box4">
          <!-- 검색창 -->
          <div class="box10">
            <el-input
              type="text"
              @keyup.enter="sendMessage"
              v-model="message"
              placeholder="메시지를 입력해주세요:)"
              clearable
            >
            </el-input>
          </div>
          <div class="box11">
            <el-button
              @click="sendMessage"
              icon="el-icon-s-promotion"
              class="icon-m-p green-color-btn"
            >
            </el-button>
          </div>
        </div>
        <!-- 검색창 끝 -->
      </div>
    </el-col>
    <el-col :span="3"></el-col>
  </el-row>
  <el-row class="height5">
    <el-col :span="24" class=""> </el-col>
  </el-row>
</template>

<script>
import { useStore } from 'vuex';
import { onMounted, ref, computed } from 'vue';

export default {
  components: {},

  setup() {
    const currentId = computed(() => store.getters['chat/getCurrentUserId']);
    const currentCounterpart = computed(
      () => store.getters['chat/getCurrentCounterpart']
    );
    const message = ref('');
    const stompClient = ref('');
    const currentCounterPart = ref('');
    const chatList = computed(() => store.getters['chat/getChatList']);
    const chatMessages = computed(() => store.getters['chat/getMessages']);

    // const chatList = [{content:"123"}, {content:"456"}];
    const store = useStore();

    const sendMessage = () => {
      store.dispatch('chat/sendMessage', {
        type: 1,
        senderId: currentId.value,
        // receiverId: 47,
        receiverId: currentCounterpart.value,
        sent_time: 1000,
        read_time: 1000,
        content: message.value,
      });
      message.value = '';
    };
    const sendReadSignal = () => {};

    const loadMessages = () => {
      // store.dispatch('chat/loadMessages');
    };

    const connect = () => {};
    const changeSession = (msg) => {
      let counter = getCounterPart(msg);
      store.dispatch('chat/changeSession', counter).then(() => {
        if (!chatMessages.value) {
          store.dispatch('chat/loadMessages').then(() => {
            store.dispatch('chat/sendMessage', {
              type: 2,
              senderId: currentId.value,
              receiverId: currentCounterpart.value,
              sent_time: 1000,
              read_time: 1000,
              content: '',
            });
          });
        } else {
          store.dispatch('chat/sendMessage', {
            type: 2,
            senderId: currentId.value,
            receiverId: currentCounterpart.value,
            sent_time: 1000,
            read_time: 1000,
            content: '',
          });
        }
      });
      // load session
    };
    const getCounterPart = (msg) => {
      return msg.senderId == currentId.value ? msg.receiverId : msg.senderId;
    };

    const calcTime = (time) => {
      var now = new Date();
      var writeDay = new Date(time);
      let timeMsg = '';

      // Tue Aug 17 2021 21:39:53 GMT+0900 (한국 표준시)
      // Tue Aug 17 2021 05:06:23 GMT+0900 (한국 표준시)
      // console.log(now);
      // console.log(writeDay);
      var minus;
      //현재 년도랑 글쓴시간의 년도 비교
      if (now.getFullYear() > writeDay.getFullYear()) {
        //두개의 차이를 구해서 표시
        timeMsg = time.slice(0, 11);
      } else if (now.getMonth() > writeDay.getMonth()) {
        //년도가 같을 경우 달을 비교해서 출력
        timeMsg = time.slice(0, 11);
      } else if (now.getDate() > writeDay.getDate()) {
        //같은 달일 경우 일을 계산
        minus = now.getDate() - writeDay.getDate();
        if (minus == 1) {
          timeMsg = '어제';
        } else {
          timeMsg = time.slice(0, 11);
        }
      } else if (now.getDate() == writeDay.getDate()) {
        //당일인 경우에는
        var nowTime = now.getTime();
        var writeTime = writeDay.getTime();
        if (nowTime > writeTime) {
          //시간을 비교
          let sec = parseInt(nowTime - writeTime) / 1000;
          let day = parseInt(sec / 60 / 60 / 24);
          sec = sec - day * 60 * 60 * 24;
          let hour = parseInt(sec / 60 / 60);
          sec = sec - hour * 60 * 60;
          let min = parseInt(sec / 60);
          sec = parseInt(sec - min * 60);
          if (hour > 0) {
            timeMsg = hour + '시간 전';
          } else if (min > 0) {
            timeMsg = min + '분 전';
          } else if (sec > 0) {
            timeMsg = sec + '초 전';
          }
        }
      }
      return timeMsg;
    };

    return {
      /*
        Variables
      */
      message,
      store,
      stompClient,
      currentId,
      chatList,
      chatMessages,
      currentCounterPart,

      /*
        Functions
      */
      connect,
      sendMessage,
      sendReadSignal,
      loadMessages,
      changeSession,
      getCounterPart,
      calcTime,
    };
  },
};
</script>
<style scoped>
.box1 {
  height: 100%;
  overflow: auto;
  display: flex;
  flex-flow: column;
  background: white;
  /* border: 1px solid blue; */
  border-top-left-radius: 15px;
  border-bottom-left-radius: 15px;
}
.box2 {
  height: 100%;
  overflow: auto;
  display: flex;
  flex-flow: column;
  justify-content: space-between;
  background: #e9e9e9;
  /* border: 1px solid red; */
  border-top-right-radius: 15px;
  border-bottom-right-radius: 15px;
}
.box3 {
  display: flex;
  flex-flow: column;
  padding-top: 10px;
}
.box4 {
  display: flex;
  /* border: 2px solid orange; */
  border-bottom-right-radius: 15px;
}
.box5 {
  height: 50px;
  display: flex;
  flex-flow: column;
  margin-left: 5px;
  margin-right: 5px;
  padding-top: 5px;
  border-top: 1px solid lightgrey;
  /* border: 2px solid palegreen; */
}
.box6 {
  display: flex;
  justify-content: space-between;
}
.box7 {
  display: flex;
  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: 500;
  font-size: 14px;
  color: rgba(0, 0, 0, 0.5);
}
.box8 {
  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: 500;
  font-size: 16px;
}
.box9 {
  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: 500;
  font-size: 14px;
  color: rgba(0, 0, 0, 0.5);
}
.box10 {
  width: 90%;
}
.box11 {
  width: 10%;
}
.box12 {
  display: flex;
  justify-content: right;
  margin-right: 10px;
}
.box12_1 {
  display: flex;
  align-items: center;
}
.box12_2 {
  display: flex;
  align-items: center;
  margin-left: 10px;
}
.box13 {
  display: flex;
  justify-content: left;
  margin-left: 10px;
}
.box13_1 {
  display: flex;
  align-items: center;
}
.box13_2 {
  display: flex;
  align-items: center;
  margin-left: 10px;
}
.me {
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  max-width: 60%;
  min-height: 30px;
  border-radius: 10px 0px 10px 10px;
  text-align: left;
  padding-top: 4px;
  padding-bottom: 4px;
  padding-left: 10px;
  padding-right: 10px;
  background: #7b9dff;
  color: #ffffff;

  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: 500;
  font-size: 18px;
  line-height: 20px;
}
.my_send_time {
  display: flex;
  justify-content: end;
  align-items: center;
  margin-bottom: 5px;
  padding-right: 10px;

  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: 500;
  font-size: 13px;
  line-height: 20px;
}
.theother {
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  max-width: 60%;
  min-height: 30px;
  text-align: left;

  padding-top: 4px;
  padding-bottom: 4px;
  padding-left: 10px;
  padding-right: 10px;
  background: white;
  color: #000000;
  border-radius: 0px 10px 10px 10px;

  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: 500;
  font-size: 18px;
  line-height: 20px;
}
.other_send_time {
  display: flex;
  justify-content: start;
  align-items: center;
  margin-bottom: 5px;
  padding-left: 10px;

  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: 500;
  font-size: 13px;
  line-height: 20px;
}

.chat-list {
  background-color: #ffffff;
  border-radius: 15px 0px 0px 15px;
}

.chat-detail {
  background: #f6f5f8;
  border-radius: 0px 15px 15px 0px;
  display: flex;
  flex-flow: column;
  justify-content: space-between;
}
</style>
