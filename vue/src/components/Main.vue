<template>
  <el-row id="main">
    <el-col :span="5"></el-col>
    <el-col :span="14">
      <el-row class="height20"></el-row>
      <el-row id="main-box">
        <el-row class="height10"></el-row>
        <el-row class="height20" style="font-size: 64px">
          <el-col :span="3"></el-col>
          <el-col :span="18">Eat Sleep Code<br />Repeat</el-col>
          <el-col :span="3"></el-col>
        </el-row>
        <el-row class="height20"></el-row>
        <el-row class="height10" style="font-size: 36px">
          <el-col :span="6"></el-col>
          <el-col :span="12">
            좋은 사람<br />
            좋은 스터디<br />
            좋은 프로젝트
          </el-col>
          <el-col :span="6"></el-col>
        </el-row>
        <el-row class="height20"></el-row>
        <el-row class="height5">
          <el-col :span="8"></el-col>
          <el-col :span="4">
            <button class="sign-btn" @click="goSignIn" v-if="!token">
              로그인
            </button>
          </el-col>
          <el-col :span="4">
            <button class="sign-btn" @click="goSignUp" v-if="!token">
              회원가입
            </button>
          </el-col>
          <el-col :span="8"></el-col>
        </el-row>
      </el-row>
    </el-col>
    <el-col :span="5"></el-col>
  </el-row>
</template>

<script>
import { useStore } from 'vuex';
import { useRouter } from 'vue-router';
import { onMounted } from 'vue';

export default {
  components: {},
  setup() {
    onMounted(() => {
      // chatInit();
    });

    const store = useStore();
    const router = useRouter();
    // 채팅을 위한 준비
    // const chatInit = function () {
    //   console.log(window.location.search);
    //   if (window.location.search == '?logined=true') {
    //     // 토큰 decode해서 내 id 알아내는 과정
    //     store.dispatch(
    //       'chat/startup',
    //       jwt_decode(localStorage.getItem('accessToken'))['sub']
    //     );
    //   }
    // };
    const goSignIn = function () {
      router.push({ path: '/noheader/signin' });
    };
    const goSignUp = function () {
      router.push({ path: '/noheader/signup' });
    };
    const token = localStorage.getItem('accessToken');
    return {
      store,
      router,
      goSignIn,
      goSignUp,
      // chatInit,
      token,
    };
  },
};
</script>
<style>
#main {
  height: 100%;
  background-image: url('../assets/main.jpg');
  background-repeat: no-repeat;
  background-size: cover;
  box-sizing: border-box;
}
#main-box {
  display: block;
  height: 60%;
  color: white;
  border: 3px solid #ffffff;
}
.sign-btn {
  display: block;
  border: 0;
  outline: 0;
  background-color: transparent;
  color: white;
  text-decoration: underline;
  font-weight: bold;
  font-size: 18px;
  cursor: pointer;
  margin: auto;
}
</style>
