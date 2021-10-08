<template>
  <div @click="modalOpen = true" id="quitService">회원 탈퇴</div>
  <teleport to="body">
    <div v-if="modalOpen" class="modal">
      <div class="height40" style="width: 40%">
        <el-row class="height10">
          <el-col :span="24" class="title-msg">정말로 탈퇴하시겠습니까?</el-col>
        </el-row>
        <el-row class="height10">
          <el-col :span="24" class="font-noto-md font-14 warn-msg"
            >모든 항목이 완전히 삭제되며 복원할 수 없게 됩니다.</el-col
          >
        </el-row>
        <el-row class="height10">
          <el-col :span="6" :offset="6">
            <el-button
              class="btn-ghost-red font-noto-bold"
              @click="modalOpen = false"
              style="font-size: 14px"
              >탈퇴</el-button
            >
          </el-col>
          <el-col :span="6">
            <el-button
              class="btn-ghost-blue font-noto-bold"
              @click="modalOpen = false"
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
export default {
  data() {
    return {
      modalOpen: false,
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
