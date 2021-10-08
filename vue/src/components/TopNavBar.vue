<template>
  <el-row class="height100 top-nav-bar">
    <el-col :span="2"></el-col>
    <el-col :span="5"
      ><el-row>
        <el-col :span="1"></el-col>
        <el-col :span="4">
          <el-button class="top-nav-btn font-s-md" type="text">
            <!-- @click="clickProject"
            프로젝트 -->
          </el-button>
        </el-col>
        <el-col :span="4"></el-col>
        <el-col :span="5">
          <el-button
            class="top-nav-btn font-s-md"
            type="text"
            @click="clickStudy"
          >
            스터디
          </el-button>
        </el-col>
        <el-col :span="4"> </el-col>
        <el-col :span="3">
          <el-button class="top-nav-btn font-s-md" type="text">
            <!-- @click="clickClub"
            클럽 -->
          </el-button>
        </el-col>
        <el-col :span="3"></el-col>
      </el-row>
    </el-col>

    <el-col :span="2"></el-col>
    <el-col :span="5"
      ><el-row class="height100 logo-div">
        <img src="../assets/Item/logo.png" class="logo" @click="clickMain" />
      </el-row> </el-col
    ><el-col :span="3"></el-col
    ><el-col :span="6"
      ><el-row class="height100">
        <!-- <el-col :span="2" id="search"
          ><i class="el-icon-search" id="search-icon"></i
        ></el-col> -->
        <el-col :span="3"></el-col
        ><el-col :span="3"
          ><el-button
            class="top-nav-btn font-s-md"
            type="text"
            @click="clickChat"
            >채팅</el-button
          >
          <div class="chat_count">{{ chatUnreadCounts }}</div> </el-col
        ><el-col :span="3"></el-col
        ><el-col :span="6"
          ><el-button
            class="top-nav-btn font-s-md"
            type="text"
            @click="clickMyPage"
            >마이페이지</el-button
          ></el-col
        ><el-col :span="2"></el-col
        ><el-col :span="5">
          <el-button
            v-if="!token"
            class="top-nav-btn font-s-md"
            type="text"
            @click="clickLogIn"
            >로그인</el-button
          >
          <el-button
            v-if="token"
            class="top-nav-btn font-s-md"
            type="text"
            @click="clickLogOut"
            >로그아웃</el-button
          >
        </el-col>
      </el-row> </el-col
    ><el-col :span="1"></el-col>
  </el-row>
</template>

<script>
import { useStore } from 'vuex';
import { useRouter } from 'vue-router';

import { computed, onMounted } from 'vue';
import jwt_decode from 'jwt-decode';

export default {
  setup() {
    const store = useStore();
    const router = useRouter();
    const chatUnreadCounts = computed(
      () => store.getters['chat/getUnreadCounts']
    );
    const chatCurrentId = computed(
      () => store.getters['chat/getCurrentUserId']
    );
    const clickStudy = function () {
      if (token) {
        store.commit('setCategory', 1);

        window.location = '/nosubheader/study/home';
        // router.push({ path: '/nosubheader/study/home' });
      } else {
        router.push({ path: '/noheader/signin' });
      }
    };
    onMounted(() => {
      // 토큰 decode해서 내 id 알아내는 과정
      if (localStorage.getItem('accessToken') != null) {
        store.dispatch(
          'chat/startup',
          jwt_decode(localStorage.getItem('accessToken'))['sub']
        );
      }
    });

    const clickProject = function () {
      if (token) {
        store.commit('setCategory', 2);
        router.push({ path: '/nosubheader/project/home' });
      } else {
        router.push({ path: '/noheader/signin' });
      }
    };

    const clickClub = function () {
      if (token) {
        store.commit('setCategory', 3);
        router.push({ path: '/nosubheader/club/home' });
      } else {
        router.push({ path: '/noheader/signin' });
      }
    };

    const clickMain = function () {
      router.push({ path: '/' });
    };

    const clickChat = function () {
      if (token) {
        router.push({ path: '/nosubheader/chat' });
      } else {
        router.push({ path: '/noheader/signin' });
      }
    };

    const clickMyPage = function () {
      if (token) {
        store.dispatch('member/readMyPage');
        // router.push({ path: '/nosubheader/readmypage' });
        window.location = '/nosubheader/readmypage';
      } else {
        router.push({ path: '/noheader/signin' });
      }
    };
    const clickLogIn = function () {
      router.push({ path: '/noheader/signin' });
    };
    const clickLogOut = function () {
      localStorage.removeItem('accessToken');
      store.dispatch('chat/cleanup');
      window.location = '/';
    };
    const token = localStorage.getItem('accessToken');
    return {
      store,
      router,
      chatUnreadCounts,
      chatCurrentId,
      clickStudy,
      clickProject,
      clickClub,
      clickMain,
      clickChat,
      clickMyPage,
      clickLogIn,
      clickLogOut,
      token,
    };
  },
};
</script>

<style>
.top-nav-bar {
  background-color: #24305e;
  /* vertical-align: middle; */
  display: flex;
  align-items: center;
}
.logo-div {
  display: table-cell;
  vertical-align: middle;
}
.logo {
  cursor: pointer;
  width: 80%;
  object-fit: cover;
  vertical-align: middle;
}
#search {
  display: flex;
  align-items: center;
  justify-content: center;
}
#search-icon {
  margin-top: 7%;
  font-size: 130%;
  cursor: pointer;
  color: white;
}
.top-nav-btn {
  font-size: 18px;
  text-align: center;
  color: white;
}
.chat_count {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 20px;
  height: 20px;
  position: absolute;
  margin-top: -45px;
  margin-left: 35px;
  background: #f53030;
  border-radius: 10px;
  opacity: 0.9;
  color: white;
}
</style>
