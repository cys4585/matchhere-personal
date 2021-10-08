<template>
  <el-row class="height5"> </el-row>
  <el-row class="height5">
    <el-col :span="22" :offset="2" class="test-border">
      <p class="itemlist-title-left">추천 스터디 목록</p>
    </el-col>
  </el-row>

  <el-row v-if="recommendStudyList">
    <el-col :span="2" class="test-border"></el-col>

    <el-col :span="20" class="test-border">
      <el-row class="top-margin">
        <el-col
          :span="4"
          :offset="1"
          class="test-border item-border flex-wrap item-object"
          v-for="(item, index) in recommendStudyList"
          :key="index"
          @click="goIntroduce(item.id)"
        >
          <div class="wh100">
            <el-row class="height40 item-img"></el-row>
            <el-row class="height10 item-head-title">{{ item.name }}</el-row>
            <el-row class="height10 item-content"
              >기술 스택:&nbsp;
              <div v-for="(tech, index) in item.techList" :key="index">
                {{ tech }} &nbsp;
              </div>
            </el-row>
            <el-row class="height10"></el-row>
            <el-row class="height10 item-content">
              <el-col :span="12" class="test-border left-content"
                >지역: {{ item.city }}</el-col
              >
              <el-col :span="12" class="test-border right-content">
                <i
                  v-if="item.isPublic == false"
                  class="el-icon-lock icon-size"
                ></i>
              </el-col>
            </el-row>
            <el-row class="height10 item-content">
              <el-col :span="12" class="test-border left-content"
                >기간: {{ item.period }}주</el-col
              >
              <el-col :span="12" class="test-border right-content"
                >인원: {{ item.memberCount }}/{{ item.maxCount }}</el-col
              >
            </el-row>
            <el-row class="height10">
              <el-col
                :span="12"
                class="test-border item-small-content left-content"
                >작성일: {{ item.modifiedDate.substr(2, 8) }}</el-col
              >
              <el-col :span="12" class="test-border right-content">
                <div
                  v-if="item.status == '종료'"
                  class="item-state-badge-blue item-small-content"
                >
                  {{ item.status }}
                </div>
                <div v-else class="item-state-badge-blue item-small-content">
                  {{ item.status }}중
                </div>
                <div
                  v-if="item.isParticipate == true && item.status == '진행'"
                  class="item-state-badge-blue item-small-content"
                >
                  참여 가능
                </div>
              </el-col>
            </el-row>
          </div>
        </el-col>
      </el-row>
    </el-col>
    <!-- Item 리스트 1개씩 v-for로 -->
    <el-col :span="2" class="test-border"></el-col>
  </el-row>

  <el-row class="height5">
    <el-col :span="24" class="test-border">
      <a @click="goHomeStudy">돌아가기</a>
    </el-col>
  </el-row>
</template>
<script>
import { computed } from 'vue';
import { useStore } from 'vuex';
import { useRouter } from 'vue-router';

export default {
  setup() {
    const store = useStore();
    const router = useRouter();

    // 추천 스터디 목록
    store.dispatch('study/getRecommendStudyList');
    const recommendStudyList = computed(
      () => store.getters['study/recommendStudyListGetter']
    );

    const goIntroduce = function (id) {
      store.dispatch('study/callUpdateStudyId', id);
      router.push({ path: '/subheader/study/introduce' });
    };

    const goHomeStudy = function () {
      router.push({ path: '/nosubheader/study/home' });
    };

    return {
      store,
      router,
      recommendStudyList,
      goIntroduce,
      goHomeStudy,
    };
  },
};
</script>
<style scoped>
.item-img {
  background-image: url('../../assets/Item/basic2.png');
  background-repeat: no-repeat;
  background-size: cover;
  box-sizing: border-box;
}

.item-head-title {
  font-size: 16pt;
  font-weight: bold;
}
.item-content {
  font-size: 12pt;
  font-weight: normal;
}
.item-small-content {
  font-size: 10pt;
  font-weight: normal;
}

.left-content {
  text-align: left;
}
.right-content {
  text-align: right;
}

.item-state-badge-blue {
  display: inline-block;
  padding: 0 3px 0 3px;
  margin: 0 2px 0 2px;
  color: white;
  background: #4361ff;
  border-radius: 3px;
  /* vertical-align: middle; */
}
.item-state-badge-green {
  display: inline-block;
  padding: 0 3px 0 3px;
  margin: 0 2px 0 2px;
  color: white;
  background: #00bf00;
  border-radius: 3px;
  /* vertical-align: middle; */
}
.item-state-badge-red {
  display: inline-block;
  padding: 0 3px 0 3px;
  margin: 0 2px 0 2px;
  color: white;
  background: #bf0000;
  border-radius: 3px;
  /* vertical-align: middle; */
}
.item-state-badge-black {
  display: inline-block;
  padding: 0 3px 0 3px;
  margin: 0 2px 0 2px;
  color: white;
  background: #000000;
  border-radius: 3px;
  /* vertical-align: middle; */
}

/* icon 크기는 font-size로 작성함 */
.icon-size {
  font-size: 20px;
  font-weight: bold;
}

.item {
  cursor: pointer;
}

.flex-wrap {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-evenly;
  align-content: space-evenly;
}

.top-margin {
  margin-top: 10px;
}

.item-object {
  /* margin: 0px; */
  /* padding: 0px; */
  min-height: 200px;
  margin-top: 50px;
}

.wh100 {
  width: 100%;
  height: 100%;
}
</style>
