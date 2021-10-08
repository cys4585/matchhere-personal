<template>
  <el-button @click="changemodalOpen" class="btn-ghost-round">
    수정페이지로 이동
  </el-button>
  <teleport to="body">
    <div v-if="!modalOpen" class="modal">
      <div class="height40" style="width: 40%">
        <el-row class="height10">
          <el-col :span="24" class="font-noto-bold font-20">
            비밀번호를 입력해주세요
          </el-col>
        </el-row>
        <el-row class="height10">
          <el-col :span="8" class="font-noto-bold font-20"> 비밀번호 </el-col>
          <el-col :span="14" class="font-noto-bold font-20">
            <el-input type="password" v-model="state.form.password"></el-input>
          </el-col>
          <el-col :span="2"> </el-col>
        </el-row>
        <el-row class="height10">
          <el-col :span="6" :offset="6">
            <el-button
              class="btn-ghost-blue font-noto-bold"
              @click="goUpdateMyPage"
              style="font-size: 14px"
              >확인
            </el-button>
          </el-col>
          <el-col :span="6">
            <el-button
              class="btn-ghost-red font-noto-bold"
              @click="changemodalOpen"
              style="font-size: 14px"
              >취소</el-button
            >
          </el-col>
          <el-col :span="6"></el-col>
        </el-row>
      </div>
    </div>
  </teleport>
</template>
<script>
import { computed, reactive } from 'vue';
import { useStore } from 'vuex';
import { useRouter } from 'vue-router';

import { ElMessage } from 'element-plus';
export default {
  setup() {
    const store = useStore();
    const router = useRouter();
    const modalOpen = computed(() => store.getters['scrollGetter']);
    const user = computed(() => store.getters['member/mypageGetter']);

    const state = reactive({
      form: {
        email: user.value.email,
        password: '',
      },
    });

    const changemodalOpen = function () {
      store.dispatch('changeScrollModal', !modalOpen.value);
    };

    // 업데이트 누를 시
    const goUpdateMyPage = function () {
      store.dispatch('changeScrollModal', false);
      // state.form.email = user.value.email;
      //'인풋값' + state.form.password

      store.dispatch('changeScrollModal', !modalOpen.value);

      const res = store
        .dispatch('member/checkPassword', state.form)
        .then((res) => {
          if (res.status == 200) {
            router.push({ path: '/nosubheader/updatemypage' });
          }
        })
        .catch((err) => {
          ElMessage({
            showClose: true,
            message: '잘못된 비밀번호를 입력하셨습니다',
            type: 'error',
          });
        });

      // router.push({ path: '/nosubheader/updatemypage' });
    };

    return {
      store,
      router,
      modalOpen,
      changemodalOpen,
      goUpdateMyPage,
      state,
      user,
    };
  },
};
</script>
<style scoped>
.modal {
  position: absolute;
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

.modal div {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  background-color: white;
  width: 100%;
}

.quitService {
  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: 500;
  font-size: 16px;
  line-height: 16px;
  text-align: center;
  color: #999999;
}
</style>
