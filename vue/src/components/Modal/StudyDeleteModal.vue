<template>
  <el-button @click="changemodalOpen" class="btn-ghost-red font-noto-bold">
    삭제
  </el-button>
  <teleport to="body">
    <div v-if="!modalOpen" class="modal">
      <div class="height40" style="width: 40%">
        <el-row class="height8">
          <el-col :span="24" class="title-msg">
            [{{ studyIntroduce.name }}]
          </el-col>
        </el-row>
        <el-row class="height8">
          <el-col :span="24" class="sub-msg">정말로 삭제하시겠습니까</el-col>
        </el-row>
        <el-row class="height8">
          <el-col :span="24" class="font-noto-md font-14 warn-msg"
            >모든 항목이 완전히 삭제되며 복원할 수 없게 됩니다.</el-col
          >
        </el-row>
        <el-row class="height10">
          <el-col :span="6" :offset="6">
            <el-button
              class="btn-ghost-red font-noto-bold"
              @click="goStudyHome"
              style="font-size: 14px"
            >
              삭제
            </el-button>
          </el-col>
          <el-col :span="6">
            <el-button
              class="btn-ghost-blue font-noto-bold"
              @click="changemodalOpen"
              style="font-size: 14px"
            >
              취소
            </el-button>
          </el-col>
          <el-col :span="6"></el-col>
        </el-row>
      </div>
    </div>
  </teleport>
</template>
<script>
import { computed, watch } from 'vue';
import { useStore } from 'vuex';
import { useRouter } from 'vue-router';

export default {
  data() {
    return {
      modalOpen: false,
    };
  },
  setup() {
    const store = useStore();
    const router = useRouter();
    // 1. 스터디 ID를 받아옴
    const studyId = computed(() => store.getters['study/studyIdGetter']);

    // 2. 모달창에 스터디 정보를 일부 띄워야하기 때문에 스터디 정보를 받음
    const studyIntroduce = computed(
      () => store.getters['study/studyIntroduceGetter']
    );
    // 혹시나 스터디 정보가 바뀌면 다시 정보 가져오기
    watch(studyId, () => {
      store.dispatch('study/introduce', studyId.value);
    });

    // 모달 처리
    const modalOpen = computed(() => store.getters['scrollGetter']);
    const changemodalOpen = function () {
      store.dispatch('changeScrollModal', !modalOpen.value);
    };
    // 삭제 누를 시
    const goStudyHome = function () {
      store.dispatch('study/deleteStudy', studyId.value);
      store.dispatch('changeScrollModal', !modalOpen.value);
      router.push({ path: '/nosubheader/study/home' });
    };

    return {
      modalOpen,
      changemodalOpen,
      studyIntroduce,
      goStudyHome,
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
.warn-msg {
  color: #ff0000;
  font-weight: bold;
}

.title-msg {
  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: normal;
  font-size: 20px;
}

.sub-msg {
  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: normal;
  font-size: 14px;
}
</style>
