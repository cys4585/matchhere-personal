<template>
  <el-row class="height5">
    <el-col :span="24" class="test-border"> PWD CHECK 여백: </el-col>
  </el-row>
  <el-row class="height5">
    <el-col :span="22" :offset="2" class="test-border"> 비밀번호 확인 </el-col>
  </el-row>
  <el-row class="height5"></el-row>
  <el-row class="height5"></el-row>
  <el-row class="height5"></el-row>
  <el-row class="height5"></el-row>
  <el-row class="height5"></el-row>
  <el-row class="height5">비밀번호를 입력해 주세요</el-row>
  <el-row class="height10">
    <el-col :span="2" :offset="8" class="test-border">비밀번호</el-col>
    <el-col :span="6" class="test-border">
      <el-input
        placeholder="(숫자만 입력하세요)"
        v-model="temp"
        size="mini"
      ></el-input>
    </el-col>
  </el-row>
  <el-row class="height5">
    <el-col :span="2" :offset="10" class="test-border">확인</el-col>
    <el-col :span="2" class="test-border">취소</el-col>
  </el-row>
</template>

<script>
export default {
  components: {},
};
</script>

<style></style>
