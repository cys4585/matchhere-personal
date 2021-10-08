<template>
  <el-row :gutter="0" class="height100">
    <el-col :span="15" :offset="0">
      <div class="height100">
        <div class="height30"></div>
        <div class="height5"></div>
        <div class="height10" id="H1">
          <p id="h1">환영합니다!</p>
        </div>
        <div class="height5"></div>
        <div class="height5">
          <input
            v-model="state.form.email"
            type="text"
            placeholder="이메일"
            id="email"
            onfocus="this.placeholder=''"
            onblur="this.placeholder='이메일'"
            @keyup.enter="signIn()"
          />
        </div>
        <div class="height10">
          <input
            v-model="state.form.password"
            type="password"
            placeholder="비밀번호"
            id="password"
            onfocus="this.placeholder=''"
            onblur="this.placeholder='비밀번호'"
            @keyup.enter="signIn()"
            autocomplete="off"
          />
        </div>
        <div class="height5">
          <el-button id="login" @click="signIn">로그인</el-button>
        </div>
        <div class="height30"></div>
      </div>
    </el-col>
    <el-col :span="9" :offset="0" id="right">
      <div class="height100">
        <div class="height30"></div>
        <div class="height5"></div>
        <div class="height10" id="P1">
          <p id="p1">아직 회원이 아니신가요?</p>
        </div>
        <div class="height10" id="P2">
          <p id="p2">
            회원가입을 하고 여러분이 원하는 스터디와 프로젝트를 찾아보세요!
          </p>
        </div>
        <div class="height5"></div>
        <div class="height5">
          <el-button id="signin" @click="goSignUp()">회원가입</el-button>
        </div>
        <div class="height5"></div>
        <div class="height30"></div>
      </div>
    </el-col>
  </el-row>
</template>

<script>
import { reactive } from 'vue';
import { useStore } from 'vuex';
import { useRouter } from 'vue-router';

import { ElMessage } from 'element-plus';

export default {
  name: 'signIn',
  components: {},
  setup() {
    const router = useRouter();
    const store = useStore();
    // 독립적인 반응형 값 생성 ref()
    // const signin = ref(null);
    const state = reactive({
      form: {
        email: '',
        password: '',
      },
    });
    const goSignUp = function () {
      router.push({ path: '/noheader/signup' });
    };
    const signIn = function () {
      // modules의 auth.js에서 signIn 액션을 dispatch함
      store
        .dispatch('auth/signIn', state.form)
        .then((res) => {
          if (res.status == 200) {
            // 한 번 값 비우기
            localStorage.removeItem('accessToken');
            localStorage.setItem('accessToken', res.data.accessToken);
            window.location = '/?logined=true';
            store.dispatch('member/readMyPage');
            ElMessage({
              showClose: true,
              message: '로그인 성공!',
              type: 'success',
            });
          } else if (res.status == 404) {
            ElMessage({
              showClose: true,
              message: '해당 아이디가 존재하지 않습니다.',
              type: 'error',
            });
            state.form.email = '';
            state.form.password = '';
          }
        })
        .catch((err) => {
          ElMessage({
            showClose: true,
            message: '아이디 또는 비밀번호가 틀렸습니다!',
            type: 'error',
          });
        });
    };
    return {
      goSignUp,
      store,
      state,
      router,
      signIn,
    };
  },
};
</script>
<style scoped>
#right {
  background: linear-gradient(
    180deg,
    rgba(45, 109, 206, 0.6) 0%,
    rgba(66, 142, 255, 0.7) 55%,
    #c4e6ff 100%
  );
}
#H1 {
  display: flex;
  align-items: center;
  justify-content: center;
}
#h1 {
  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: 500;
  font-size: 48px;
  line-height: 58px;
  /* identical to box height, or 121% */

  text-align: center;
  margin-top: 0px;
  margin-bottom: 0px;
  color: #202121;
}
#email {
  /* 버튼 */
  width: 400px;
  height: 48px;

  background: #e8e8e8;
  border-radius: 10px;
  border: 0px;

  /* 텍스트 */
  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: normal;
  font-size: 14px;
  line-height: 16px;
  /* identical to box height, or 114% */

  text-align: left;
  padding-left: 10px;

  color: #919191;
}
#password {
  width: 400px;
  height: 48px;

  background: #e8e8e8;
  border-radius: 10px;
  border: 0px;

  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: normal;
  font-size: 14px;
  margin-top: 10px;
  line-height: 16px;
  /* identical to box height, or 114% */

  text-align: left;
  padding-left: 10px;

  color: #919191;
}

#login {
  /* 버튼 */
  width: 178px;
  height: 50px;

  background: #307ff5;
  border-radius: 30px;
  border: 0px;

  /* 텍스트 */
  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: 500;
  font-size: 16px;
  line-height: 16px;
  /* or 100% */

  text-align: center;

  color: #ffffff;
}
#P1 {
  display: flex;
  align-items: center;
  justify-content: center;
}
#p1 {
  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: bold;
  /* font-size: 250%; */
  font-size: 40px;
  line-height: 58px;
  /* identical to box height, or 145% */
  text-align: center;
  margin-top: 0px;
  margin-bottom: 0px;
  color: #ffffff;
}
#P2 {
  display: flex;
  align-items: center;
  justify-content: center;
}
#p2 {
  width: 290px;

  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: 500;
  font-size: 20px;
  line-height: 30px;
  /* or 150% */

  text-align: center;
  margin-top: 0px;
  margin-bottom: 0px;
  color: #ffffff;
}
#signin {
  /* 텍스트 */
  width: 64px;
  height: 18px;

  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: 500;
  font-size: 16px;
  line-height: 16px;
  /* or 100% */

  text-align: center;

  color: #000000;

  /* 버튼 */
  width: 178px;
  height: 50px;

  border: 0px;
  background: #ffffff;
  border-radius: 30px;
}
</style>
