<template>
  <el-button @click="changemodalOpen" class="btn-ghost-red font-noto-bold">
    거절
  </el-button>
  <teleport to="body">
    <div v-if="!modalOpen" class="modal">
      <div class="height40" style="width: 30%">
        <el-row class="height20">
          <el-col :span="24" class="title-msg">
            {{ userNickname }}님의 <br />
            [{{ studyIntroduce.name }}] 가입을 <br />
            거절하시겠습니까?
          </el-col>
        </el-row>
        <el-row class="height10"> </el-row>
        <el-row class="height10">
          <el-col :span="12">
            <el-button
              class="btn-ghost-red font-noto-bold"
              @click="reject"
              style="font-size: 14px"
              >거절</el-button
            >
          </el-col>
          <el-col :span="12">
            <el-button
              class="btn-ghost-blue font-noto-bold"
              @click="changemodalOpen"
              style="font-size: 14px"
            >
              취소
            </el-button>
          </el-col>
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
  setup() {
    const router = useRouter();
    const store = useStore();
    const modalOpen = computed(() => store.getters['rejectModalGetter']);
    // 스터디 ID 가져오기
    const studyId = computed(() => store.getters['study/studyIdGetter']);

    // 2. 해당 멤버 정보 가져오기
    const userNickname = computed(
      () => store.getters['study/studyMemberNicknameGetter']
    );
    const userId = computed(() => store.getters['study/memberIdGetter']);

    // 3. 스터디 상세 정보 가져오기
    store.dispatch('study/introduce', studyId.value);
    const studyIntroduce = computed(
      () => store.getters['study/studyIntroduceGetter']
    );

    const state = reactive({
      form: {
        studyId: studyId.value,
        memberId: userId.value,
      },
    });

    watch(userId, () => {
      state.form.memberId.value = userId.value;
    });

    // 거절
    const reject = function () {
      store.dispatch('study/rejectStudy', state.form);
      store.dispatch('changeRejectModal', !modalOpen.value);
      window.location = '/subheader/study/manage';
    };

    const changemodalOpen = function () {
      store.dispatch('changeRejectModal', !modalOpen.value);
    };

    return {
      state,
      modalOpen,
      reject,
      changemodalOpen,
      studyId,

      userNickname,
      userId,
      studyIntroduce,
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

.title-msg {
  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: normal;
  font-size: 20px;
}
</style>
