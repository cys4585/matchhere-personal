<template>
  <el-button @click="changemodalOpen" class="btn-ghost-blue font-noto-bold">
    신청
  </el-button>
  <teleport to="body">
    <div v-if="!modalOpen" class="modal">
      <div class="height50 flex-parent" style="width: 50%">
        <el-row class="height10"></el-row>
        <el-row class="height10">
          <el-col :span="18" :offset="6" class="font-noto-bold font-20">
            {{ state.form.nickname }}의 스터디 신청서
          </el-col>
        </el-row>
        <el-row class="height10"></el-row>
        <!-- 세 줄 -->
        <el-row class="height40">
          <!-- 좌 우 -->
          <el-col
            :span="12"
            :offset="3"
            class="test-border font-noto-md font-14 height100"
          >
            <el-row class="test-border height25">
              <el-col :span="3"></el-col>
              <el-col :span="6" class="test-border"> 닉네임 </el-col>
              <el-col :span="2"></el-col>
              <el-col :span="13" class="test-border">
                <input
                  type="text"
                  class="input1"
                  v-model="state.form.nickname"
                  readonly
                />
              </el-col>
            </el-row>
            <el-row class="test-border height25">
              <el-col :span="3"></el-col>
              <el-col :span="6" class="test-border"> 지역 </el-col>
              <el-col :span="2"></el-col>
              <el-col :span="13" class="test-border">
                <input
                  type="text"
                  class="input1"
                  v-model="state.form.city"
                  readonly
                />
              </el-col>
            </el-row>
            <el-row class="test-border height25">
              <el-col :span="3"></el-col>
              <el-col :span="6" class="test-border"> 깃 </el-col>
              <el-col :span="2"></el-col>
              <el-col :span="13" class="test-border">
                <!-- git icon이 현재 동작 안 함
                <i class="el-icon-github"></i>
                <i class="el-icon-github-circled"></i>
                <i class="el-icon-lock lock font-noto-md"></i> -->
                <input
                  type="text"
                  class="input1"
                  v-model="state.form.git"
                  readonly
                />
              </el-col>
            </el-row>
            <el-row class="test-border height25">
              <el-col :span="3"></el-col>
              <el-col :span="6" class="test-border"> twitter </el-col>
              <el-col :span="2"></el-col>
              <el-col :span="13" class="test-border">
                <input
                  type="text"
                  class="input1"
                  v-model="state.form.twitter"
                  readonly
                />
              </el-col>
            </el-row>
          </el-col>
          <!-- 사진 -->
          <el-col
            :span="6"
            class="test-border flex-parent"
            style="width: 100%; height: 100%"
          >
            <el-row class="height100">
              <div class="profile">
                <img src="../../assets/Item/basic.png" class="previewImg" />
                <!-- <img
                  v-if="user.cover_pic"
                  :src="`${user.cover_pic}`"
                  class="previewImg"
                /> -->
              </div>
            </el-row>
          </el-col>
          <el-col :span="3"></el-col>
        </el-row>

        <!-- 한 줄 -->
        <el-row class="font-14 font-noto-md" style="height: 15%">
          <el-col :span="4" :offset="2" class="test-border"
            >&nbsp;facebook
          </el-col>
          <el-col :span="16" class="test-border"
            ><input
              type="text"
              class="input2"
              v-model="state.form.facebook"
              readonly
          /></el-col>
        </el-row>
        <el-row class="font-14 font-noto-md" style="height: 15%">
          <el-col :span="4" :offset="2" class="test-border"
            >&nbsp;baekjoon
          </el-col>
          <el-col :span="16" class="test-border"
            ><input
              type="text"
              class="input2"
              v-model="state.form.backjoon"
              readonly
          /></el-col>
        </el-row>
        <!-- 한 줄 -->
        <el-row class="font-14 font-noto-md" style="height: 15%">
          <el-col :span="4" :offset="2" class="test-border">
            &nbsp;Experienced</el-col
          >
          <el-col :span="16" class="test-border font-14 font-noto-md"
            ><input
              type="text"
              class="input2"
              v-model="state.form.expTechList"
              readonly
          /></el-col>
        </el-row>
        <el-row class="font-14 font-noto-md" style="height: 15%">
          <el-col :span="4" :offset="2" class="test-border"
            >&nbsp;Beginner</el-col
          >
          <el-col :span="16" class="test-border font-14 font-noto-md"
            ><input
              type="text"
              class="input2"
              v-model="state.form.beginTechList"
              readonly
          /></el-col>
        </el-row>
        <!-- 한 줄 -->
        <el-row class="font-14 font-noto-md" style="height: 15%">
          <el-col :span="4" :offset="2" class="test-border"
            >&nbsp;자기소개</el-col
          >
          <el-col :span="16" class="test-border"
            ><input type="text" class="input2" v-model="state.form.bio"
          /></el-col>
        </el-row>
        <!-- 한 줄 -->
        <el-row class="height10">
          <!-- 여백 -->
        </el-row>
        <!-- 한 줄 -->
        <el-row class="height10">
          <el-button class="btn-ghost-round-blue-filled" @click="goApplicate"
            >신청</el-button
          >
          <el-button class="btn-ghost-round-red" @click="changemodalOpen"
            >취소</el-button
          >
        </el-row>
        <el-row class="height20">
          <!-- 여백 -->
        </el-row>
      </div>
    </div>
  </teleport>
</template>
<script>
import { useRouter } from 'vue-router';
import { reactive, computed, onBeforeMount } from 'vue';
import { useStore } from 'vuex';

export default {
  setup() {
    const router = useRouter();
    const store = useStore();
    const modalOpen = computed(() => store.getters['applicationModalGetter']);
    // 스터디 ID 가져오기
    const studyId = computed(() => store.getters['study/studyIdGetter']);

    // 해당 참가자 정보 가져오기
    store.dispatch('member/readMyPage');
    const user = computed(() => store.getters['member/mypageGetter']);

    const state = reactive({
      form: {
        studyId: studyId.value,
        bio: user.value.bio,
        city: user.value.city,
        backjoon: '',
        git: '',
        twitter: '',
        facebook: '',
        nickname: user.value.nickname,
        beginTechList: user.value.beginTechList,
        expTechList: user.value.expTechList,
        // uuid: null,
      },
    });

    onBeforeMount(() => {
      // 유저 snsList에서 snsName에 따라 snsAccount 계정 설정
      for (var i = 0; i < user.value.snsList.length; i++) {
        if (user.value.snsList[i].snsName == 'github') {
          state.form.git = user.value.snsList[i].snsAccount;
        } else if (user.value.snsList[i].snsName == 'twitter') {
          state.form.twitter = user.value.snsList[i].snsAccount;
        } else if (user.value.snsList[i].snsName == 'facebook') {
          state.form.facebook = user.value.snsList[i].snsAccount;
        } else if (user.value.snsList[i].snsName == 'backjoon') {
          state.form.backjoon = user.value.snsList[i].snsAccount;
        }
      }
    });

    store.dispatch('study/applicationOne', state.form);

    const changemodalOpen = function () {
      store.dispatch('changeApplicationModal', !modalOpen.value);
    };

    // 신청 누를 시
    const goApplicate = function () {
      store.dispatch('study/applicateStudy', state.form); //신청서 전송
      store.dispatch('changeApplicationModal', !modalOpen.value);
      router.push({ path: '/subheader/study/introduce' });
    };

    return {
      store,
      router,
      state,
      modalOpen,
      goApplicate,
      changemodalOpen,
      user,
      studyId,
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
  /* flex-direction: column; */
  align-items: center;
  justify-content: center;
}

.modal div {
  /* display: flex; */
  /* flex-direction: column; */
  align-items: center;
  justify-content: center;
  background-color: white;
  width: 100%;
}

.btn-ghost-round-blue-filled {
  background: #1747c9;
  border: 1px solid #1747c9;
  box-sizing: border-box;
  border-radius: 30px;

  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: 500;
  font-size: 16px;
  line-height: 16px;
  text-align: center;
  color: #ffffff;
}
.previewImg {
  width: 200px;
  height: 200px;
}
.input1 {
  width: 200px;
  height: 40px;
  border: none;
  border-radius: 4px;
  padding-left: 10px;

  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: normal;
  font-size: 14px;
}
.input2 {
  min-width: 527px;
  height: 40px;
  border: none;
  border-radius: 4px;
  padding-left: 10px;

  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: normal;
  font-size: 14px;
}
</style>
