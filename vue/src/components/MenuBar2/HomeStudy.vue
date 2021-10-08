<template>
  <el-row class="height5 margin">
    <el-col :span="21" class="test-border"></el-col>
    <el-col :span="3" class="test-border">
      <el-button class="btn-1747C9 font-noto-bold" @click="goCreate"
        >스터디 생성</el-button
      >
    </el-col>
  </el-row>

  <el-row class="height5 margin">
    <el-col :span="22" :offset="2" class="test-border">
      <p class="itemlist-title-left">내 스터디 목록</p>
    </el-col>
  </el-row>
  <el-row class="height25" v-if="myStudyList">
    <el-col :span="2" class="test-border"></el-col>
    <!-- Item 리스트 1개씩 v-for로 -->
    <el-col
      :span="4"
      :offset="1"
      class="test-border item-border"
      v-for="(item, index) in myStudyList.slice(0, 4)"
      :key="index"
      @click="goIntroduce(item.id)"
    >
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
          <i v-if="item.isPublic == false" class="el-icon-lock icon-size"></i>
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
        <el-col :span="12" class="test-border item-small-content left-content"
          >작성일: {{ item.modifyDate.substr(2, 8) }}</el-col
        >
        <el-col :span="12" class="test-border right-content">
          <div
            v-if="item.status == '종료'"
            class="item-state-badge-black item-small-content"
          >
            {{ item.status }}
          </div>
          <div
            v-else-if="item.status == '진행'"
            class="item-state-badge-green item-small-content"
          >
            {{ item.status }}중
          </div>
          <div v-else class="item-state-badge-blue item-small-content">
            {{ item.status }}중
          </div>
          <div
            v-if="item.isParticipate == true && item.status == '진행'"
            class="item-state-badge-green item-small-content"
          >
            참여 가능
          </div>
          <div
            v-else-if="item.isParticipate == false && item.status == '진행'"
            class="item-state-badge-red item-small-content"
          >
            참여 불가
          </div>
        </el-col>
      </el-row>
    </el-col>
    <el-col :span="2" class="test-border"></el-col>
  </el-row>

  <el-row class="height5 margin">
    <el-col :span="24" class="test-border">
      <a @click="goInfiniteMyStudy">더보기</a>
    </el-col>
  </el-row>

  <el-row class="height5 margin">
    <el-col :span="22" :offset="2" class="test-border">
      <p class="itemlist-title-left">추천 스터디 목록</p>
    </el-col>
  </el-row>

  <!-- 아이템 목록 시작 -->
  <el-row class="height25" v-if="recommendStudyList">
    <el-col :span="2" class="test-border"></el-col>
    <el-col
      :span="4"
      :offset="1"
      class="test-border item-border"
      v-for="(item, index) in recommendStudyList.slice(0, 4)"
      :key="index"
      @click="goIntroduce(item.id)"
    >
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
          <i v-if="item.isPublic == false" class="el-icon-lock icon-size"></i>
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
        <el-col :span="12" class="test-border item-small-content left-content"
          >작성일: {{ item.modifiedDate.substr(2, 8) }}</el-col
        >
        <el-col :span="12" class="test-border right-content">
          <div
            v-if="item.status == '종료'"
            class="item-state-badge-black item-small-content"
          >
            {{ item.status }}
          </div>
          <div
            v-else-if="item.status == '진행'"
            class="item-state-badge-green item-small-content"
          >
            {{ item.status }}중
          </div>
          <div v-else class="item-state-badge-blue item-small-content">
            {{ item.status }}중
          </div>
          <div
            v-if="item.isParticipate == true && item.status == '진행'"
            class="item-state-badge-green item-small-content"
          >
            참여 가능
          </div>
          <div
            v-else-if="item.isParticipate == false && item.status == '진행'"
            class="item-state-badge-red item-small-content"
          >
            참여 불가
          </div>
        </el-col>
      </el-row>
    </el-col>
    <el-col :span="2" class="test-border"></el-col>
  </el-row>
  <!-- 아이템 목록 끝 -->

  <el-row class="height5 margin">
    <el-col :span="24" class="test-border">
      <a @click="goInfiniteRecommendStudy">더보기</a>
    </el-col>
  </el-row>

  <el-row class="height5 margin">
    <el-col :span="22" :offset="2" class="test-border">
      <p class="itemlist-title-left">전체 스터디 목록</p>
    </el-col>
  </el-row>

  <!-- 아이템 목록 시작 -->
  <el-row class="height25" v-if="totalStudyList">
    <el-col :span="2" class="test-border"></el-col>
    <!-- Item 리스트 1개씩 v-for로 -->
    <el-col
      :span="4"
      :offset="1"
      class="test-border item-border"
      v-for="(item, index) in totalStudyList.slice(0, 4)"
      :key="index"
      @click="goIntroduce(item.id)"
    >
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
          <i v-if="item.isPublic == false" class="el-icon-lock icon-size"></i>
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
        <el-col :span="12" class="test-border item-small-content left-content"
          >작성일: {{ item.modifiedDate.substr(2, 8) }}</el-col
        >
        <el-col :span="12" class="test-border right-content">
          <div
            v-if="item.status == '종료'"
            class="item-state-badge-black item-small-content"
          >
            {{ item.status }}
          </div>
          <div
            v-else-if="item.status == '진행'"
            class="item-state-badge-green item-small-content"
          >
            {{ item.status }}중
          </div>
          <div v-else class="item-state-badge-blue item-small-content">
            {{ item.status }}중
          </div>
          <div
            v-if="item.isParticipate == true && item.status == '진행'"
            class="item-state-badge-green item-small-content"
          >
            참여 가능
          </div>
          <div
            v-else-if="item.isParticipate == false && item.status == '진행'"
            class="item-state-badge-red item-small-content"
          >
            참여 불가
          </div>
        </el-col>
      </el-row>
    </el-col>
    <el-col :span="2" class="test-border"></el-col>
  </el-row>
  <!-- 아이템 목록 끝 -->

  <el-row class="height5 margin">
    <el-col :span="24" class="test-border">
      <a @click="goInfiniteTotalStudy">더보기</a>
    </el-col>
  </el-row>
</template>
<script>
import { computed, watch } from 'vue';
import { useStore } from 'vuex';
import { useRouter } from 'vue-router';

export default {
  setup() {
    const store = useStore();
    const router = useRouter();

    // 내가 속한 스터디 목록 받을 것임
    store.dispatch('member/readMyPage');
    const myStudyList = computed(
      () => store.getters['member/myStudyListGetter']
    );

    // 추천 스터디 목록
    store.dispatch('study/getRecommendStudyList');
    const recommendStudyList = computed(
      () => store.getters['study/recommendStudyListGetter']
    );

    // 전체 스터디 목록
    store.dispatch('study/getTotalStudyList');
    const totalStudyList = computed(
      () => store.getters['study/totalStudyGetter']
    );


    const goCreate = function () {
      router.push({ path: '/nosubheader/study/create' });
    };

    const goIntroduce = function (id) {
      store.dispatch('study/callUpdateStudyId', id);
      router.push({ path: '/subheader/study/introduce' });
    };

    const goInfiniteMyStudy = function () {
      router.push({ path: '/nosubheader/study/infinite/my' });
    };

    const goInfiniteRecommendStudy = function () {
      router.push({ path: '/nosubheader/study/infinite/recommend' });
    };

    const goInfiniteTotalStudy = function () {
      router.push({ path: '/nosubheader/study/infinite/total' });
    };

    return {
      store,
      router,
      totalStudyList,
      recommendStudyList,
      myStudyList,
      goCreate,
      goIntroduce,
      goInfiniteMyStudy,
      goInfiniteRecommendStudy,
      goInfiniteTotalStudy,
    };
  },
};
</script>
<style scoped>
.item-img {
  background-image: url('../../assets/Item/basic2.png');
  background-repeat: no-repeat;
  background-size: contain;
  box-sizing: border-box;
  border-radius: 5px 5px 0px 0px;
  left: 0px;
  top: 0px;

  padding: 0px;
}
.margin {
  margin-top: 30px;
  margin-bottom: 30px;
}
</style>
