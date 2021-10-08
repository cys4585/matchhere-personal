<template>
  <el-row class="height90 font-noto-md">
    <el-col :span="3"></el-col>
    <el-col :span="11">
      <el-row class="height100 sub-nav-bar">
        <el-col :span="6">
          <el-button
            v-if="category == 1"
            class="sub-nav-btn font-noto-md"
            type="text"
            @click="clickIntroduceStudy"
          >
            스터디 소개
          </el-button>
          <el-button
            v-else-if="category == 2"
            class="sub-nav-btn font-noto-md"
            type="text"
            @click="clickIntroduceProject"
          >
            프로젝트 소개
          </el-button>
          <el-button
            v-else-if="category == 3"
            class="sub-nav-btn font-noto-md"
            type="text"
            @click="clickIntroduceClub"
          >
            클럽 소개
          </el-button>
        </el-col>
        <el-col :span="1"> </el-col>
        <el-col :span="5">
          <el-button
            class="sub-nav-btn font-noto-md"
            type="text"
            @click="clickNotice"
          >
            공지사항
          </el-button>
        </el-col>
        <el-col :span="1"> </el-col>
        <el-col :span="4">
          <el-button
            class="sub-nav-btn font-noto-md"
            type="text"
            @click="clickBoard"
          >
            게시판
          </el-button>
        </el-col>
        <el-col :span="1"></el-col>
        <el-col :span="6">
          <el-button
            v-if="category == 1"
            class="sub-nav-btn font-noto-md"
            type="text"
            @click="clickManageStudy"
          >
            스터디 관리
          </el-button>
          <el-button
            v-else-if="category == 2"
            class="sub-nav-btn font-noto-md"
            type="text"
            @click="clickManageProject"
          >
            프로젝트 관리
          </el-button>
          <el-button
            v-if="category == 3"
            class="sub-nav-btn font-noto-md"
            type="text"
            @click="clickManageClub"
          >
            클럽 관리
          </el-button>
        </el-col>
      </el-row>
    </el-col>
    <el-col :span="8"> </el-col>
    <el-col :span="1"></el-col>
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

    const category = computed(() => store.getters['categoryGetter']);

    const clickIntroduceStudy = function () {
      store.commit('setCategory', 1);
      router.push({ path: '/subheader/study/introduce' });
    };

    const clickIntroduceProject = function () {
      store.commit('setCategory', 2);
      router.push({ path: '/subheader/project/introduce' });
    };

    const clickIntroduceClub = function () {
      store.commit('setCategory', 3);
      router.push({ path: '/subheader/club/introduce' });
    };

    const clickNotice = function () {
      router.push({ path: '/subheader/notice' });
    };

    const clickBoard = function () {
      router.push({ path: '/subheader/board' });
    };

    const clickManageStudy = function () {
      store.commit('setCategory', 1);
      router.push({ path: '/subheader/study/manage' });
    };

    const clickManageProject = function () {
      router.push({ path: '/subheader/manage' });
    };
    const clickManageClub = function () {
      router.push({ path: '/subheader/manage' });
    };

    return {
      store,
      router,
      category,
      clickIntroduceStudy,
      clickIntroduceProject,
      clickIntroduceClub,
      clickNotice,
      clickBoard,
      clickManageStudy,
      clickManageProject,
      clickManageClub,
    };
  },
};
</script>

<style>
.sub-nav-bar {
  display: flex;
  align-items: flex-end;
}
.sub-nav-btn {
  font-size: 28px;
  text-align: center;
  color: black;
  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: 500;
  font-size: 36px;
}
.sub-nav-btn:hover {
  font-size: 28px;
  text-align: center;
  color: black;
  text-decoration: underline;
  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: 500;
  font-size: 36px;
}
.lock-div {
  display: flex;
  align-items: center;
}
.lock {
  font-size: 26px;
  text-align: center;
  color: black;
  vertical-align: middle;
}
</style>
