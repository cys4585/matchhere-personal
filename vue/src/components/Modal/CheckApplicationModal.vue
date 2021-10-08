<template>
  <el-button @click="changemodalOpen" class="">
    Check Application Modal
  </el-button>
  <teleport to="body">
    <div v-if="!modalOpen" class="modal">
      <div class="height50 flex-parent" style="width: 50%">
        <el-row class="height10"></el-row>
        <el-row class="height10">
          <el-col
            :span="18"
            :offset="6"
            class="font-noto-bold font-20"
            v-if="application"
            >{{ application }}
            <!-- {{ application[0].nickname }}의 (스터디 이름) 스터디 신청서 -->
          </el-col>
        </el-row>
        <el-row class="height10"></el-row>
        <!-- 세 줄 -->
        <el-row class="height40">
          <!-- 좌 우 -->
          <el-col :span="12" :offset="3" class="test-border font-14 height100">
            <el-row class="test-border height25">
              <el-col :span="3"></el-col>
              <el-col :span="6" class="test-border"> 닉네임 </el-col>
              <el-col :span="2"></el-col>
              <el-col :span="13" class="test-border">
                <!-- {{ application[0].nickname }} -->
              </el-col>
            </el-row>
            <el-row class="test-border height25">
              <el-col :span="3"></el-col>
              <el-col :span="6" class="test-border"> 지역 </el-col>
              <el-col :span="2"></el-col>
              <el-col :span="13" class="test-border">
                <!-- {{ application[0].city }} -->
              </el-col>
            </el-row>
            <el-row class="test-border height25">
              <el-col :span="3"></el-col>
              <el-col :span="6" class="test-border"> 깃 </el-col>
              <el-col :span="2"></el-col>
              <el-col :span="13" class="test-border">
                <!-- {{ application[0].git }} -->
              </el-col>
            </el-row>
            <el-row class="test-border height25">
              <el-col :span="3"></el-col>
              <el-col :span="6" class="test-border"> twitter </el-col>
              <el-col :span="2"></el-col>
              <el-col :span="13" class="test-border">
                <!-- {{ application[0].twitter }} -->
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
                <img class="previewImg" />
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
        <el-row class="height10 font-14">
          <el-col :span="4" :offset="2" class="test-border"
            >&nbsp;facebook
          </el-col>
          <el-col
            :span="16"
            class="test-border"
            v-model="state.form.memberId"
          ></el-col>
        </el-row>
        <el-row class="height10 font-14">
          <el-col :span="4" :offset="2" class="test-border"
            >&nbsp;baekjoon
          </el-col>
          <el-col :span="16" class="test-border">
            <!-- {{
            application[0].backjoon
          }} -->
          </el-col>
        </el-row>
        <!-- 한 줄 -->
        <el-row class="height10 font-14">
          <el-col :span="4" :offset="2" class="test-border">
            &nbsp;Experienced</el-col
          >
          <el-col :span="16" class="test-border">
            <!-- {{
            application[0].strong
          }} -->
          </el-col>
        </el-row>
        <el-row class="height10 font-14">
          <el-col :span="4" :offset="2" class="test-border"
            >&nbsp;Beginner</el-col
          >
          <el-col :span="16" class="test-border">
            <!-- {{
            application[0].knowledgeable
          }} -->
          </el-col>
        </el-row>
        <!-- 한 줄 -->
        <el-row class="height10 font-14">
          <el-col :span="4" :offset="2" class="test-border"
            >&nbsp;자기소개</el-col
          >
          <el-col :span="16" class="test-border">
            <!-- {{
            application[0].bio
          }} -->
          </el-col>
        </el-row>
        <!-- 한 줄 -->
        <el-row class="height10">
          <!-- 여백 -->
        </el-row>
        <!-- 한 줄 -->
        <el-row class="height10">
          <el-button class="btn-ghost-round-blue-filled" @click="goManage"
            >수락</el-button
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
import { reactive, computed, watch } from 'vue';
import { useStore } from 'vuex';

export default {
  props: {
    propData: String,
  },
  setup() {
    const router = useRouter();
    const store = useStore();
    const modalOpen = computed(() => store.getters['scrollGetter']);

    // 1. 스터디 ID 가져오기
    const studyId = computed(() => store.getters['study/studyIdGetter']);

    //2. member id 가져오기
    const currentId = computed(() => store.getters['study/memberIdGetter']);

    // 3. 지원서 정보 가져오기
    const para = reactive({
      studyId: studyId.value,
      currentId: currentId.value,
    });

    store.dispatch('study/getStudyApplicationOne', para);
    // getters로 저장
    const application = computed(
      () => store.getters['study/studyApplicationGetter']
    );

    const param = reactive({
      form: {
        studyId: studyId.value,
        memberId: '',
      },
    });

    const state = reactive({
      //출력되는 정보
      form: {
        backjoon: '',
        bio: '',
        city: '',
        email: '',
        facebook: '',
        git: '',
        knowledgeable: [],
        memberId: '',
        nickname: '',
        strong: '',
        twitter: '',
      },
    });

    // 모달처리
    const changemodalOpen = function () {
      store.dispatch('changeScrollModal', !modalOpen.value);
    };

    // 수락 누를 시
    const goManage = function () {
      store.dispatch('study/approvalStudy', param.form); //신청서 수락
      store.dispatch('changeScrollModal', !modalOpen.value);
      router.push({ path: '/subheader/study/manage' });
    };

    return {
      store,
      router,

      currentId,
      studyId,

      application,

      param,
      state,
      modalOpen,
      goManage,
      changemodalOpen,
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
.profile {
  width: 100%;
  height: 100%;
  background-color: red;
}
.previewImg {
  width: 100%;
  height: 100%;
  max-width: 100%;
  max-height: 100%;
}
</style>
