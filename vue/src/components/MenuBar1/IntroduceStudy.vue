<template>
  <!-- 상단 소개 정보 부분 -->
  <el-row class="font-20" v-if="studyIntroduce">
    <el-col :span="3"></el-col>
    <el-col :span="3"
      ><el-row class="height1"> </el-row>
      <el-row class="height8">
        <i class="el-icon-postcard flex-items"></i>&nbsp;스터디 이름
      </el-row>
      <el-row class="height1"> </el-row>
      <el-row class="height8">
        <i class="el-icon-setting flex-items"></i>&nbsp;주요 기술 스택
      </el-row>
      <el-row class="height1"> </el-row>
      <el-row class="height8">
        <i class="el-icon-date flex-items"></i>&nbsp;스터디 일정
      </el-row>
      <el-row class="height1"> </el-row>
      <el-row class="height8">
        <i class="el-icon-time flex-items"></i>&nbsp;스터디 기간
      </el-row>
      <el-row class="height1"> </el-row>
      <el-row class="height8">
        <i class="el-icon-star-off flex-items"></i>&nbsp;팀장
      </el-row>
      <el-row class="height1"> </el-row>
      <el-row class="height8">
        <i class="el-icon-user flex-items"></i>&nbsp;인원
      </el-row>
      <el-row class="height1"> </el-row>
      <el-row class="height8">
        <i class="el-icon-lock flex-items"></i>&nbsp;공개여부
      </el-row>
      <el-row class="height1"> </el-row>
      <el-row class="height8">
        <i class="el-icon-position flex-items"></i>&nbsp;지역
      </el-row>
      <el-row class="height1"> </el-row>
      <el-row class="height8">
        <i class="el-icon-video-play flex-items"></i>&nbsp;스터디 상태
      </el-row>
      <el-row class="height1"> </el-row>
      <el-row class="height8">
        <i class="el-icon-folder flex-items"></i>&nbsp;소속 클럽
      </el-row>
      <el-row class="height1"> </el-row>
      <el-row class="height8">
        <i class="el-icon-edit flex-items"></i>&nbsp;마지막 수정일
      </el-row>
    </el-col>
    <el-col :span="1"></el-col>
    <el-col :span="5"
      ><el-row class="height1"> </el-row>
      <el-row class="height8"> {{ studyIntroduce.name }}</el-row>
      <el-row class="height1"> </el-row>
      <el-row class="height8">
        <div v-for="(tech, index) in studyIntroduce.techList" :key="index">
          {{ tech }}&nbsp;
        </div>
      </el-row>
      <el-row class="height1"> </el-row>
      <el-row class="height8"> {{ studyIntroduce.schedule }} </el-row>
      <el-row class="height1"> </el-row>
      <el-row class="height8"> {{ studyIntroduce.period }}주 </el-row>
      <el-row class="height1"> </el-row>
      <el-row class="height8">
        <el-popover v-model:visible="visible" placement="top" :width="200">
          <div style="text-align: right; margin: 0">
            <el-button size="mini" class="btn-ghost-round" @click="goOtherPage"
              >마이페이지
            </el-button>
            <el-button
              class="btn-ghost-round-blue"
              size="mini"
              @click="makeChat"
              >채팅</el-button
            >
          </div>
          <template #reference>
            <div @click="visible = true">
              {{ studyIntroduce.host.nickname }}
            </div>
          </template>
        </el-popover>
      </el-row>
      <el-row class="height1"> </el-row>

      <el-row class="height8">
        <MemberListModal />
      </el-row>
      <el-row class="height1"> </el-row>
      <el-row class="height8">
        <div v-if="studyIntroduce.isPublic">공개</div>
        <div v-else>비공개</div>
      </el-row>
      <el-row class="height1"> </el-row>
      <el-row class="height8"> {{ studyIntroduce.city }} </el-row>
      <el-row class="height1"> </el-row>
      <el-row class="height8">
        <div v-if="studyIntroduce.status == '종료'">종료</div>
        <div v-else-if="studyIntroduce.status == '모집'">모집 중</div>
        <div v-else>
          진행 중 - 참여&nbsp;
          <div v-if="studyIntroduce.isParticipate">가능</div>
          <div v-else>불가</div>
        </div>
      </el-row>
      <el-row class="height1"> </el-row>
      <el-row class="height8">
        <div v-if="studyIntroduce.club">{{ studyIntroduce.club }}</div>
        <div v-else>없음</div>
      </el-row>
      <el-row class="height1"> </el-row>
      <el-row class="height8">
        {{ studyIntroduce.modifiedDate.substr(2, 8) }}&nbsp;
        {{ studyIntroduce.modifiedDate.substr(11, 8) }}
        <!-- ???? 왜 역으로 가야되는거지 -->
      </el-row>
      <el-row class="height1"> </el-row>
    </el-col>
    <el-col :span="1"></el-col>
    <el-col :span="8">
      <!-- <el-row class="height5"></el-row> -->
      <el-row style="height: 100%">
        <img class="previewImg" src="../../assets/Item/basic3.png" />
      </el-row>
    </el-col>
    <el-col :span="3"></el-col>
  </el-row>

  <!-- 여백 -->
  <el-row class="height5"> </el-row>

  <!-- 버튼 영역 -->
  <el-row class="height30 font-20">
    <el-col :span="3"></el-col>
    <el-col :span="18">
      <el-row class="height8">
        <i class="el-icon-chat-dot-round font-20 flex-items"></i>&nbsp;소개
      </el-row>
      <el-row class="height92 font-20">
        {{ studyIntroduce.bio }}
      </el-row>

      <el-row>
        <el-col :span="6"></el-col>
        <el-col :span="6" v-if="auth == 2">
          <el-button class="btn-ghost-blue font-noto-bold" @click="goUpdate">
            수정
          </el-button>
        </el-col> 
        <el-col :span="6" v-if="auth == 2"> <StudyDeleteModal /> </el-col>
        <el-col :span="12" v-if="auth == 1"> <StudyQuitModal /> </el-col>
        <el-col :span="6" v-if="auth == 0">
          <el-button class="btn-ghost-blue font-noto-bold" @click="goHome">
            돌아가기
          </el-button>
        </el-col>
        <el-col :span="6" v-if="auth == 0">
          <CreateApplicationModal />
        </el-col>
        <el-col :span="6"></el-col>
      </el-row>
    </el-col>
    <el-col :span="3"></el-col>
  </el-row>
</template>
<script>
import { computed, ref, watch, reactive } from 'vue';
import { useStore } from 'vuex';
import { useRouter } from 'vue-router';

import MemberListModal from '../Modal/MemberListModal.vue';
import StudyDeleteModal from '../Modal/StudyDeleteModal.vue';
import StudyQuitModal from '../Modal/StudyQuitModal.vue';
import CreateApplicationModal from '../Modal/CreateApplicationModal.vue';

export default {
  setup() {
    const store = useStore();
    const router = useRouter();
    const studyId = computed(() => store.getters['study/studyIdGetter']);

    // 스터디 id를 가지고 dispatch 해야함
    store.dispatch('study/introduce', studyId.value);
    const studyIntroduce = computed(
      () => store.getters['study/studyIntroduceGetter']
    );
    // 1. 새롭게 스터디 id가 바뀌는 것을 감지
    watch(studyId, () => {
      store.dispatch('study/introduce', studyId.value);
    });

    store.dispatch('member/readMyPage');
    const user = computed(() => store.getters['member/mypageGetter']);

    //스터디 장-host2, 팀원-mystudylist에 있음1, 외부인-없음0
    const auth = ref(0);
    //스터디 장인지 아닌지는
    //api/auth/check/nickname/에다가
    //내 토큰이랑 스터디 장의 별명을 넣어서 일치하는지 확인

    // 권한 체크
    // 내 정보(닉네임), 팀장 정보(닉네임) 일치 여부 확인

    if (studyIntroduce.value.host.nickname == user.value.nickname) {
      auth.value = 2;
    } else {
      for (let index = 0; index < user.value.myStudyList.length; index++) {
        if (user.value.myStudyList[index].id == studyId.value) {
          auth.value = 1;
          break;
        } else {
          auth.value = 0;
        }
      }
    }
    watch(studyIntroduce, () => {
      if (studyIntroduce.value.host.nickname == user.value.nickname) {
        auth.value = 2;
      } else {
        for (let index = 0; index < user.value.myStudyList.length; index++) {
          if (user.value.myStudyList[index].id == studyId.value) {
            auth.value = 1;
            break;
          } else {
            auth.value = 0;
          }
        }
      }
    });

    // const state = reactive({
    //   form: {
    //     name: studyIntroduce.value.name, //스터디 이름
    //     techList: ['java', 'python'], //기술 목록
    //     schedule: '', //일정 String
    //     period: 7, //기간
    //     host: {}, //팀장
    //     memberCount: 0, //현재 인원수
    //     maxCount: 0, //최대 인원수
    //     memberDtos: [], //인원 정보
    //     isPublic: false, //공개 여부
    //     city: '', //지역
    //     isParticipate: '', //스터디 상태(참여 가능/불가)
    //     club: null, //소속 클럽 id
    //     modifyDate: '', //마지막 수정일
    //     bio: '', //소개
    //     dbFile: null, //사진 file
    //   },
    // });
    const visible = ref(false);

    // 팀장과 채팅 생성
    const makeChat = function () {
      visible.value = false;
      const currentId = computed(() => store.getters['chat/getCurrentUserId']);

      // 채팅방 개설에 필요한 body
      const body = {
        content: '채팅방이 개설되었습니다.',
        read_time: 1000,
        receiverId: studyIntroduce.value.host.id, // 받는 사람
        senderId: currentId.value, //보내는 사람
        sent_time: new Date(),
        type: 1,
      };

      store.dispatch('chat/startChat', body);
      router.push({ path: '/nosubheader/chat' });
    };
    // 팀장의 마이페이지 방문
    const goOtherPage = function () {
      visible.value = false;

      // 팀장의 마이지페이지로 이동
      store.dispatch('member/readInfoPage', studyIntroduce.value.host.email);
      router.push({ path: '/nosubheader/readinfopage' });
    };
    const goUpdate = function () {
      router.push({ path: '/nosubheader/study/update' });
    };
    const goHome = function () {
      router.push({ path: '/nosubheader/study/home' });
    };
    return {
      store,
      router,
      studyIntroduce,
      auth,

      visible,
      makeChat,
      goUpdate,
      goOtherPage,
      goHome,
    };
  },
  components: {
    MemberListModal,
    StudyQuitModal,
    StudyDeleteModal,
    CreateApplicationModal,
  },
};
</script>
<style scoped>
.previewImg {
  width: 60%;
  height: 100%;
  object-fit: cover;
  /* overflow: hidden; */
}
.margin {
  padding-bottom: 150px;
}
</style>
