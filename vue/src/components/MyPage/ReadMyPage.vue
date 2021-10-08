<template>
  <el-row>
    <!-- 유저: {{ user }} -->
    <el-col :span="1"></el-col>
    <el-col :span="21" class="test-border title">
      마이페이지
      <hr style="border: 1px color= black" width="100%"
    /></el-col>
    <el-col :span="2"></el-col>
  </el-row>
  <!-- <el-row class="height5"> </el-row> -->
  <!-- 좌측 | 우측 갈리는 부분 1:9:1: 12: 1-->
  <el-row class="height90">
    <!-- 좌측 -->
    <el-col :span="1"></el-col>

    <el-col :span="8" :offset="1" class="test-border font-14 flex-parent">
      <el-row class="test-border">
        <el-col :span="8" class="test-border">
          <el-row>
            <div class="profile">
              <!-- <img
                v-if="user.cover_pic"
                :src="`${user.cover_pic}`"
                class="previewImg"
              /> -->
              <img src="../../assets/Item/basic.png" class="previewImg" />
            </div>
          </el-row>
        </el-col>
        <el-col :span="14" :offset="2" class="test-border flex-parent">
          <el-row class="test-border info-size">
            <el-col :span="5" class="test-border"> email </el-col>
            <el-col :span="15" :offset="4" class="info test-border">
              {{ user.email }}</el-col
            >
          </el-row>
          <el-row class="test-border info-size">
            <el-col :span="5" class="test-border"> 이름 </el-col>
            <el-col :span="15" :offset="4" class="info test-border">
              {{ user.name }}</el-col
            >
          </el-row>
          <el-row class="test-border info-size">
            <el-col :span="5" class="test-border"> 닉네임 </el-col>
            <el-col :span="15" :offset="4" class="info test-border">
              {{ user.nickname }}</el-col
            >
          </el-row>
          <el-row class="test-border info-size">
            <el-col :span="5" class="test-border"> 역할 </el-col>
            <el-col :span="15" :offset="4" class="info test-border">
              {{ user.position }}</el-col
            >
          </el-row>
          <el-row class="test-border info-size">
            <el-col :span="5" class="test-border"> 지역 </el-col>
            <el-col :span="15" :offset="4" class="info">
              &nbsp;{{ user.city }}
            </el-col>
          </el-row>
        </el-col>
      </el-row>

      <!-- 마이페이지 정보 깃~ -->
      <el-row class="test-border info-size">
        <el-col :span="7" class="test-border"> github </el-col>
        <el-col :span="16" :offset="1" class="info">
          <span
            v-if="
              user.snsList &&
              user.snsList.length > 0 &&
              user.snsList[0].snsName == 'github'
            "
            >{{ user.snsList[0].snsAccount }}</span
          >
          <span
            v-else-if="
              user.snsList &&
              user.snsList.length > 1 &&
              user.snsList[1].snsName == 'github'
            "
            >{{ user.snsList[1].snsAccount }}</span
          >
          <span
            v-else-if="
              user.snsList &&
              user.snsList.length > 2 &&
              user.snsList[2].snsName == 'github'
            "
            >{{ user.snsList[2].snsAccount }}</span
          >
          <span
            v-else-if="
              user.snsList &&
              user.snsList.length > 3 &&
              user.snsList[3].snsName == 'github'
            "
            >{{ user.snsList[3].snsAccount }}</span
          >
        </el-col>
      </el-row>
      <el-row class="test-border info-size">
        <el-col :span="7" class="test-border"> 트위터 </el-col>
        <el-col :span="16" :offset="1" class="info">
          <span
            v-if="
              user.snsList &&
              user.snsList.length > 0 &&
              user.snsList[0].snsName == 'twitter'
            "
            >{{ user.snsList[0].snsAccount }}</span
          >
          <span
            v-else-if="
              user.snsList &&
              user.snsList.length > 1 &&
              user.snsList[1].snsName == 'twitter'
            "
            >{{ user.snsList[1].snsAccount }}</span
          >
          <span
            v-else-if="
              user.snsList &&
              user.snsList.length > 2 &&
              user.snsList[2].snsName == 'twitter'
            "
            >{{ user.snsList[2].snsAccount }}</span
          >
          <span
            v-else-if="
              user.snsList &&
              user.snsList.length > 3 &&
              user.snsList[3].snsName == 'twitter'
            "
            >{{ user.snsList[3].snsAccount }}</span
          >
        </el-col>
      </el-row>

      <el-row class="test-border info-size">
        <el-col :span="7" class="test-border"> 페이스북 </el-col>
        <el-col :span="16" :offset="1" class="info">
          <span
            v-if="
              user.snsList &&
              user.snsList.length > 0 &&
              user.snsList[0].snsName == 'facebook'
            "
            >{{ user.snsList[0].snsAccount }}</span
          >
          <span
            v-else-if="
              user.snsList &&
              user.snsList.length > 1 &&
              user.snsList[1].snsName == 'facebook'
            "
            >{{ user.snsList[1].snsAccount }}</span
          >
          <span
            v-else-if="
              user.snsList &&
              user.snsList.length > 2 &&
              user.snsList[2].snsName == 'facebook'
            "
            >{{ user.snsList[2].snsAccount }}</span
          >
          <span
            v-else-if="
              user.snsList &&
              user.snsList.length > 3 &&
              user.snsList[3].snsName == 'facebook'
            "
            >{{ user.snsList[3].snsAccount }}</span
          >
        </el-col>
      </el-row>
      <el-row class="test-border info-size">
        <el-col :span="7" class="test-border"> 백준 </el-col>
        <el-col :span="16" :offset="1" class="info">
          <span
            v-if="
              user.snsList &&
              user.snsList.length > 0 &&
              user.snsList[0].snsName == 'backjoon'
            "
            >{{ user.snsList[0].snsAccount }}</span
          >
          <span
            v-else-if="
              user.snsList &&
              user.snsList.length > 1 &&
              user.snsList[1].snsName == 'backjoon'
            "
            >{{ user.snsList[1].snsAccount }}</span
          >
          <span
            v-else-if="
              user.snsList &&
              user.snsList.length > 2 &&
              user.snsList[2].snsName == 'backjoon'
            "
            >{{ user.snsList[2].snsAccount }}</span
          >
          <span
            v-else-if="
              user.snsList &&
              user.snsList.length > 3 &&
              user.snsList[3].snsName == 'backjoon'
            "
            >{{ user.snsList[3].snsAccount }}</span
          >
        </el-col>
      </el-row>
      <el-row class="test-border info-size">
        <el-col :span="7" class="test-border"> 포트폴리오 </el-col>
        <el-col :span="16" :offset="1" class="info">
          <a v-if="user.portfolio" :href="`${user.portfolio}`">포트폴리오</a>
        </el-col>
      </el-row>
      <el-row class="test-border info-size">
        <el-col :span="7" class="test-border"> 포트폴리오 URI </el-col>
        <el-col :span="16" :offset="1" class="info">
          {{ user.portfolio_uri }}
        </el-col>
      </el-row>
      <el-row class="test-border info-size">
        <el-col :span="7" class="test-border"> Experienced </el-col>
        <el-col :span="16" :offset="1" class="info">
          <span v-for="item in user.expTechList" :key="item"
            >{{ item }}&nbsp;</span
          >
        </el-col>
      </el-row>
      <el-row class="test-border info-size">
        <el-col :span="7" class="test-border"> Beginner</el-col>
        <el-col :span="16" :offset="1" class="info">
          <span v-for="item in user.beginTechList" :key="item"
            >{{ item }}&nbsp;</span
          >
        </el-col>
      </el-row>
      <el-row class="test-border info-size">
        <el-col :span="7" class="test-border"> 세부 포지션 </el-col>
        <el-col :span="16" :offset="1" class="info">
          <span v-for="item in user.dpositionList" :key="item"
            >{{ item.name }}&nbsp;</span
          >
        </el-col>
      </el-row>
      <el-row class="test-border">
        <el-col :span="7" class="test-border"> 자기소개 </el-col>
        <el-col :span="16" :offset="1" class="info">
          <span v-if="user.bio">{{ user.bio }}</span>
        </el-col>
      </el-row>
      <el-row class="test-border align-center"> <PasswordCheckModal /> </el-row>
    </el-col>

    <!-- 우측 -->
    <el-col :span="12" :offset="1" class="test-border">
      <el-row class="test-border title">내가 속한 클럽</el-row>
      <!-- <el-row class="height5"></el-row> -->
      <!-- 아이템 목록 시작 -->
      <el-row class="height25" v-if="user.myClubList">
        <el-col :span="2" class="test-border"></el-col>
        <!-- Item 리스트 1개씩 v-for로 -->
        <el-col
          :span="5"
          class="test-border item-border list"
          v-for="(item, index) in user.myClubList.slice(0, 4)"
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
        </el-col>
        <el-col :span="2" class="test-border"></el-col>
      </el-row>
      <!-- 아이템 목록 끝 -->
      <!-- <el-row class="height5"></el-row> -->
      <el-row class="test-border title">내가 속한 스터디</el-row>
      <!-- <el-row class="height5"></el-row> -->
      <!-- 아이템 목록 시작 -->
      <el-row class="height25" v-if="user.myStudyList">
        <el-col :span="2" class="test-border"></el-col>
        <!-- Item 리스트 1개씩 v-for로 -->
        <el-col
          :span="5"
          class="test-border item-border list"
          v-for="(item, index) in user.myStudyList.slice(0, 4)"
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
              >작성일: {{ item.modifyDate.substr(2, 8) }}</el-col
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
        </el-col>
        <el-col :span="2" class="test-border"></el-col>
      </el-row>
      <!-- 아이템 목록 끝 -->
      <!-- <el-row class="height5"></el-row> -->
      <el-row class="height5 test-border title">내가 속한 프로젝트</el-row>
      <!-- <el-row class="height5"></el-row> -->
      <!-- 아이템 목록 시작 -->
      <el-row class="height25" v-if="user.myProjectList">
        <el-col :span="2" class="test-border"></el-col>
        <!-- Item 리스트 1개씩 v-for로 -->
        <el-col
          :span="5"
          class="test-border item-border list"
          v-for="(item, index) in user.myProjectList.slice(0, 4)"
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
        </el-col>
        <el-col :span="2" class="test-border"></el-col>
      </el-row>
      <!-- 아이템 목록 끝 -->
      <el-row
        ><el-col :span="22"></el-col>
        <el-col :span="2"><ServiceQuitModal /></el-col
      ></el-row>
    </el-col>
  </el-row>
</template>

<script>
import PasswordCheckModal from '../Modal/PasswordCheckModal.vue';
import ServiceQuitModal from '../Modal/ServiceQuitModal.vue';
import { useStore } from 'vuex';
import { reactive, watch, computed } from 'vue';
import { useRouter } from 'vue-router';
import axios from 'axios';

export default {
  name: 'ReadMyPage',
  components: {
    PasswordCheckModal,
    ServiceQuitModal,
  },
  setup() {
    const store = useStore();
    const router = useRouter();

    store.dispatch('member/readMyPage');
    const user = computed(() => store.getters['member/mypageGetter']);

    const goIntroduce = function (id) {
      store.dispatch('study/callUpdateStudyId', id);
      router.push({ path: '/subheader/study/introduce' });
    };

    return { store, router, user, goIntroduce };
  },
};
</script>
<style scoped>
.title {
  font-size: 24px;
  /* padding-top: 50px; */
  margin-bottom: 30px;
  text-align: left;
  font-size: 36px;
}

.info {
  border-radius: 4px;
  border: 0px;
  background-color: white;
  color: black;
}
.info-size {
  height: 32px;
}
.profile {
  width: 100%;
  height: 100%;
}
.previewImg {
  max-width: 100%;
  max-height: 100%;
}
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
.item-head-title {
  font-size: 16pt;
  font-weight: bold;
  padding: 0 3px 0 3px;
}
.item-content {
  font-size: 12pt;
  font-weight: normal;
  padding: 0 3px 0 3px;
}
.item-small-content {
  font-size: 10pt;
  font-weight: normal;
  padding: 0 3px 0 3px;
}

.left-content {
  text-align: left;
  padding: 0 3px 0 3px;
}
.right-content {
  text-align: right;
  padding: 0 3px 0 3px;
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

.list {
  margin-right: 10px;
}
</style>
