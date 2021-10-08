<template>
  <div class="bg">
    <el-row :gutter="0">
      <el-col :span="6" :offset="0">
        <div class="height100"></div>
      </el-col>
      <el-col :span="12" :offset="0">
        <div>
          <div id="h1">스터디 수정</div>
          <hr />
          <div id="box1">
            <label id="h2">스터디 이름</label>
            <input
              id="name"
              class="input"
              autocomplete="off"
              type="text"
              v-model="state.form.name"
              placeholder="이름을 입력하세요."
              onfocus="this.placeholder=''"
              onblur="this.placeholder='이름을 입력하세요.'"
            />
          </div>
          <div id="box1">
            <label id="h2">기술스택 (최대5개)</label>
            <div id="box5">
              <!-- v-model="state.form.techList" -->
              <div class="box0">
                <div
                  v-for="useStack in state.form.techList"
                  :key="useStack"
                  class="addValue1"
                  @click="deleteStack(useStack)"
                >
                  {{ useStack }}
                </div>
              </div>
              <label class="label3">(영어로 입력)</label>
              <div class="box6">
                <input
                  id="techList"
                  class="input1"
                  v-model="state.tech"
                  autocomplete="off"
                  @input="stackAutoComplete()"
                  type="text"
                  placeholder="사용하는 기술 스택을 입력하세요."
                  onfocus="this.placeholder=''"
                  onblur="this.placeholder='사용하는 기술 스택을 입력하세요.'"
                />
                <div id="box6">
                  <div id="warning1" style="display: none">
                    유효하지 않은 기술스택입니다.
                  </div>
                  <div id="warning2" style="display: none">
                    이미 포함되어 있습니다.
                  </div>
                  <div id="warning3" style="display: none">
                    최대 5개까지 가능합니다.
                  </div>
                </div>
              </div>
              <div id="autocomplete">
                <div
                  @click="addStack1(techStack1)"
                  v-for="techStack1 in state.result"
                  class="autocomplete1"
                  style="cursor: pointer"
                  :key="techStack1"
                >
                  <span>
                    {{ techStack1 }}
                  </span>
                </div>
              </div>
            </div>
          </div>

          <div id="box3">
            <div id="box2">
              <div id="box1">
                <label id="h2">일정</label>
                <input
                  type="text"
                  placeholder="스터디 일정을 입력하세요."
                  id="input1"
                  onfocus="this.placeholder=''"
                  onblur="this.placeholder='스터디 일정을 입력하세요.'"
                  v-model="state.form.schedule"
                />
              </div>
              <div id="box1">
                <label id="h2">스터디 기간(단위: 주)</label>
                <input
                  type="text"
                  placeholder="숫자를 입력하세요."
                  id="input1"
                  onfocus="this.placeholder=''"
                  onblur="this.placeholder='숫자를 입력하세요.'"
                  v-model="state.form.period"
                />
              </div>
              <div id="box1">
                <label id="h2">인원</label>
                <input
                  type="text"
                  placeholder="숫자를 입력하세요."
                  id="input1"
                  onfocus="this.placeholder=''"
                  onblur="this.placeholder='숫자를 입력하세요.'"
                  v-model="state.form.maxCount"
                />
              </div>
              <div id="box1">
                <label id="h2">스터디 공개 여부</label>
                <div id="radio">
                  <!-- radio 타입은 name명이 같을 경우 하나만 선택된다. -->
                  <label>
                    <input
                      type="radio"
                      name="study"
                      value="true"
                      v-model="state.form.isPublic"
                    />
                    공개
                  </label>
                  <label>
                    <input
                      type="radio"
                      name="study"
                      value="false"
                      v-model="state.form.isPublic"
                    />
                    비공개
                  </label>
                </div>
              </div>
            </div>
            <div id="box4">
              <label id="h2">프로필 사진 등록</label>
              <div id="thumbnail">
                <!-- <img class="previewImg" /> -->
                <img class="previewImg" src="../../assets/Item/basic3.png" />
              </div>
              <el-upload :before-upload="beforeUpload">
                <!-- <button>사진 업로드</button> -->
              </el-upload>
            </div>
          </div>
          <div id="box1">
            <label id="h2">지역</label>
            <select id="region" v-model="state.form.city">
              <option value="서울">서울</option>
              <option value="부산">부산</option>
              <option value="대구">대구</option>
              <option value="인천">인천</option>
              <option value="광주">광주</option>
              <option value="대전">대전</option>
              <option value="울산">울산</option>
              <option value="세종">세종</option>
              <option value="경기">경기</option>
              <option value="강원">강원</option>
              <option value="충북">충북</option>
              <option value="충남">충남</option>
              <option value="전북">전북</option>
              <option value="전남">전남</option>
              <option value="경북">경북</option>
              <option value="경남">경남</option>
              <option value="제주">제주</option>
            </select>
          </div>

          <div id="box1">
            <label id="h2">소속 클럽</label>
            <select id="clubId" v-model="state.form.clubId" class="input3">
              <option
                :value="clubId[index]"
                v-for="(item, index) in clubList"
                :key="index"
              >
                {{ item }}
              </option>
            </select>
          </div>
          <div id="box1">
            <label id="h2">스터디 상태</label>
            <div id="radio">
              <!-- radio 타입은 name명이 같을 경우 하나만 선택된다. -->
              <label>
                <input
                  type="radio"
                  name="state"
                  value="모집"
                  v-model="state.form.status"
                />
                모집중
              </label>
              <label>
                <input
                  type="radio"
                  name="state"
                  value="진행"
                  v-model="state.form.status"
                />
                진행중
              </label>
              <label>
                <input
                  type="radio"
                  name="state"
                  value="종료"
                  v-model="state.form.status"
                />
                종료됨
              </label>
            </div>
          </div>
          <div id="box1">
            <label id="h2">참여 가능 여부(진행중일때)</label>
            <div id="radio">
              <!-- radio 타입은 name명이 같을 경우 하나만 선택된다. -->
              <label>
                <input
                  type="radio"
                  name="participation"
                  value="true"
                  v-model="state.form.isParticipate"
                />
                참여 가능
              </label>
              <label>
                <input
                  type="radio"
                  name="participation"
                  value="false"
                  v-model="state.form.isParticipate"
                />
                참여 불가
              </label>
            </div>
          </div>
          <div id="box1">
            <label id="h2">소개</label>
            <textarea
              type="textarea"
              placeholder="해당 스터디에 대해 소개해주세요."
              id="input2"
              onfocus="this.placeholder=''"
              onblur="this.placeholder='해당 스터디에 대해 소개해주세요.'"
              maxlength="300"
              show-word-limit
              v-model="state.form.bio"
            />
          </div>
          <div id="btn">
            <el-button class="btn-create" @click="goIntroduce">수정</el-button>
            <el-button class="btn-cancel" @click="goBack">취소</el-button>
          </div>
        </div>
      </el-col>
      <el-col :span="6" :offset="0">
        <div class="height100"></div>
      </el-col>
    </el-row>
  </div>
</template>
<script>
import { reactive, computed, onBeforeMount } from 'vue';
import { useStore } from 'vuex';
import { useRouter } from 'vue-router';

import techstacks from '@/autocomplete/techstack.js';
import { ElMessage } from 'element-plus';

export default {
  name: 'studyUpdate',
  components: {},
  setup() {
    const router = useRouter();
    const store = useStore();

    // 1. 스터디 정보를 가져오기 위해 study Id 값 가져오기
    const studyId = computed(() => store.getters['study/studyIdGetter']);

    // 2. 스터디 정보 조회
    store.dispatch('study/studyInfo', studyId.value);
    const studyInfo = computed(() => store.getters['study/studyInfoGetter']);

    // 3. 클럽 목록을 출력하기 위한 내 정보 조회
    store.dispatch('member/readMyPage');
    const user = computed(() => store.getters['member/mypageGetter']);

    let clubList = [];
    let clubId = [];
    if (user.value.myClubList.length > 0) {
      for (let index = 0; index < user.value.myClubList.length; index++) {
        clubList[index] = user.value.myClubList[index].name;
        clubId[index] = user.value.myClubList[index].id;
      }
    } else {
      clubList[0] = '없음';
      clubId[0] = null;
    }

    const chatCurrentId = computed(
      () => store.getters['chat/getCurrentUserId']
    );

    const state = reactive({
      form: {
        bio: '', //소개
        city: '', //도시
        clubId: null, //소속 클럽 id
        hostId: chatCurrentId.value,
        isParticipate: true, // 스터디 상태
        isPublic: true, //공개 여부
        maxCount: 0, //최대 인원수
        name: '', //스터디 이름
        period: '', //기간
        schedule: '', //일정 String
        status: '모집',
        techList: [], //기술 목록
        addStackList: [], //기술 목록
        removeStackList: [], //기술 목록
        // uuid: null, //사진 uuid
      },
      studyId: studyId.value,
      tech: '',
      result: null,
      studyInfo: null,
    });

    onBeforeMount(() => {
      state.form.bio = studyInfo.value.bio;
      state.form.city = studyInfo.value.city;
      state.form.clubId = studyInfo.value.club;
      state.form.isParticipate = studyInfo.value.isParticipate;
      state.form.isPublic = studyInfo.value.isPublic;
      state.form.maxCount = studyInfo.value.maxCount;
      state.form.name = studyInfo.value.name;
      state.form.period = studyInfo.value.period;
      state.form.schedule = studyInfo.value.schedule;
      state.form.status = studyInfo.value.status;
      state.form.techList = studyInfo.value.studyTechstack;
    });

    const stackAutoComplete = function () {
      const autocomplete = document.getElementById('autocomplete');
      // state.tech에 값을 입력했을 시 자동완성목록 보여주기
      if (state.tech) {
        autocomplete.style = '';
        // techstack.js 에서 해당 글자로 시작하는 키워드들을 필터링해 state.result에 담는다.
        state.result = techstacks.filter((stack) => {
          // 해당 글자로 시작하는 키워드를 뽑아내기 위해 '^' 를 사용
          // 대소문자 구분 없이 찾아내기 위해 'i' 옵션을 사용
          return stack.match(new RegExp('^' + state.tech, 'i'));
        });
        // state.form.techList에 입력한 값이 없다면 즉시 닫히도록
        if (state.result.length <= 0) {
          autocomplete.style = 'display:none';
        }
      } else {
        autocomplete.style = 'display:none';
      }
    };

    // 자동완성 목록중에서 내가 클릭한 것을 input에 추가하는 함수
    // 내가 클릭한 것: clickedTechStack
    const addStack1 = function (clickedTechStack) {
      const autocomplete = document.getElementById('autocomplete');
      const warning1 = document.getElementById('warning1');
      const warning2 = document.getElementById('warning2');
      const warning3 = document.getElementById('warning3');

      // 기술스택 개수가 5개미만이고 추가되어있는 techStack일때
      if (
        state.form.techList.length < 5 &&
        state.form.techList.includes(clickedTechStack)
      ) {
        warning1.style = 'display:none';
        warning2.style = '';
        warning3.style = 'display:none';
      }
      // 기술스택 개수가 5개이상이고 추가되어있지 않은 techStack일때
      else if (
        state.form.techList.length >= 5 &&
        !state.form.techList.includes(clickedTechStack)
      ) {
        warning1.style = 'display:none';
        warning2.style = 'display:none';
        warning3.style = '';
      }
      // 기술스택 개수가 5개 이상이고 추가되어있는 techStack일때
      else if (
        state.form.techList.length >= 5 &&
        state.form.techList.includes(clickedTechStack)
      ) {
        warning1.style = 'display:none';
        warning2.style = '';
        warning3.style = '';
      }
      // 기술스택 개수가 5개 미만이고 추가되어있지 않은 techStack일때
      else {
        // 내가 추가할 값이 state.expDelTechList에 있다면,
        if (state.form.removeStackList.includes(clickedTechStack)) {
          // 해당 리스트에서 뺀 후
          state.form.removeStackList = state.form.removeStackList.filter(
            (techStack) => techStack !== clickedTechStack
          );
          // state.form.expAddTechList에 추가한다.
          state.form.addStackList.push(clickedTechStack);
        }
        // 내가 추가할 값이 state.form.expDelTechList에 없다면
        else {
          // state.form.expAddTechList에 해당 값을 추가한다.
          state.form.addStackList.push(clickedTechStack);
        }
        // box에 보여줄 요소 Data 처리
        // 회원가입할때 보낼 data값
        // 회원가입할때 보낼 data값
        state.form.techList.push(clickedTechStack);
        //`${state.form.techList}이 추가되었다!

        warning1.style = 'display:none';
        warning2.style = 'display:none';
        warning3.style = 'display:none';
        autocomplete.style = 'display:none';
        state.tech = '';
        //state.form.addStackList, '추가할 목록'
        // state.form.removeStackList, '삭제할 목록'
      }
    };

    // techList에서 내가 클릭한 것을 뺀 나머지를 리스트로 보여주는 함수
    const deleteStack = (clickedTechStack) => {
      // 내가 삭제할 값이 addStackList에 있다면
      if (state.form.addStackList.includes(clickedTechStack)) {
        // 해당 리스트에서 빼고
        state.form.addStackList = state.form.addStackList.filter(
          (techStack) => techStack !== clickedTechStack
        );
        // removeStackList에 추가한다.
        state.form.removeStackList.push(clickedTechStack);
      }
      // 내가 삭제할 값이 removeStackList에 없다면 removeStackList에 추가한다.
      else {
        state.form.removeStackList.push(clickedTechStack);
      }
      // box에 보여줄 요소 Data처리
      state.form.techList = state.form.techList.filter(
        (techStack) => techStack !== clickedTechStack
      );
      //state.form.addStackList, '추가할 목록'
      //state.form.removeStackList, '삭제할 목록'
    };

    const goIntroduce = function () {
      // 스터디 인원 0명일시 수정불가능
      if (state.form.maxCount < 1) {
        ElMessage({
          showClose: true,
          message: '스터디 인원은 최소 1명 이상이어야 합니다.',
          type: 'error',
        });
      } else {
        store.dispatch('study/updateStudy', state);
        router.push({ path: '/subheader/study/introduce' });
      }
    };
    const goBack = function () {
      router.push({ path: '/subheader/study/introduce' });
    };

    // 사진 업로드
    // 프로필 사진 업로드
    const beforeUpload = (file) => {
      let formData = new FormData();
      formData.append('file', file);

      var reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = function (e) {
        // var image = document.createElement('img');
        var image = document.querySelector('.previewImg');
        image.src = e.target.result; //blob 매핑
        image.width = 250;
        image.height = 200;
        image.alt = 'here should be some image';
        // document.body.appendChild(image);
      };

      const res = store.dispatch('uploadFile', formData);
    };

    return {
      store,
      router,

      studyId,
      studyInfo,
      user,
      clubList,
      clubId,
      state,
      goIntroduce,
      goBack,
      beforeUpload,
      stackAutoComplete,
      deleteStack,
      addStack1,
    };
  },
};
</script>
<style scoped>
.bg {
  background: #f2f2f2;
  padding-bottom: 100px;
}
#h1 {
  width: 184px;
  height: 52px;

  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: 500;
  font-size: 36px;
  line-height: 52px;
  margin-top: 50px;
  margin-bottom: 0px;
  margin-left: 1px;
  text-align: left;
  color: #000000;
}
#h2 {
  width: 300px;
  height: 35px;

  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: normal;
  font-size: 20px;
  line-height: 29px;
  margin-top: 10px;
  margin-left: 2px;
  text-align: left;
  color: #000000;
}
#input {
  width: 782px;
  height: 50px;

  /* background: #e8e8e8; */
  border-radius: 10px;
  border: 0px;
  margin-bottom: 10px;
  padding-left: 10px;
  margin-left: 2px;

  /* 비밀번호확인 텍스트 */
  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: normal;
  font-size: 18px;
  /* identical to box height, or 114% */
  text-align: left;

  color: #919191;
}
#input1 {
  width: 363px;
  height: 50px;

  /* background: #e8e8e8; */
  border-radius: 10px;
  border: 0px;
  margin-bottom: 10px;
  padding-left: 10px;
  margin-left: 2px;

  /* 비밀번호확인 텍스트 */
  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: normal;
  font-size: 18px;
  /* identical to box height, or 114% */
  text-align: left;

  color: #919191;
}
#input2 {
  width: 782px;
  height: 200px;

  /* background: #e8e8e8; */
  border-radius: 10px;
  border: 0px;
  margin-bottom: 10px;
  margin-left: 2px;
  padding-top: 15px;
  padding-left: 10px;

  /* 비밀번호확인 텍스트 */
  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: normal;
  font-size: 18px;
  /* identical to box height, or 114% */
  text-align: left;
  align-content: flex-start;
  resize: none;

  color: #919191;
}
#autocomplete {
  position: absolute;
  display: none;
  display: flex;
  flex-flow: column;

  width: 400px;
  max-height: 150px;
  margin-top: -5px;

  overflow: auto;
  margin-left: 0px;
  background: #919191;
  border-radius: 4px;
}
.autocomplete1 {
  background: white;
  /* background: #e8e8e8; */
}
.autocomplete1:hover {
  background: #3f8bfc;
  /* background: #307ff5; */
  color: white;
}
.autocomplete1:active {
  background: black;
}
.input {
  width: 782px;
  height: 50px;

  /* background: #e8e8e8; */
  border-radius: 10px;
  border: 0px;
  margin-bottom: 10px;
  padding-left: 10px;
  margin-left: 2px;

  /* 비밀번호확인 텍스트 */
  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: normal;
  font-size: 18px;
  /* identical to box height, or 114% */
  text-align: left;

  color: #919191;
}
.input1 {
  width: 363px;
  height: 50px;

  /* background: #e8e8e8; */
  /* background: white; */
  border-radius: 10px;
  border: 0px;
  margin-bottom: 10px;
  padding-left: 10px;
  margin-left: 2px;

  /*  텍스트 */
  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: normal;
  font-size: 18px;
  /* identical to box height, or 114% */
  text-align: left;

  color: #919191;
}
.input3 {
  cursor: pointer;
  width: 794px;
  height: 52px;

  /* background: #e8e8e8; */
  border-radius: 10px;
  border: 0px;
  margin-bottom: 10px;
  padding-left: 10px;
  margin-left: 2px;

  /* 텍스트 */
  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: normal;
  font-size: 18px;
  /* identical to box height, or 114% */
  text-align: left;

  color: #919191;
}
#region {
  cursor: pointer;
  width: 794px;
  height: 52px;

  /* background: #e8e8e8; */
  border-radius: 10px;
  border: 0px;
  margin-bottom: 10px;
  padding-left: 10px;
  margin-left: 2px;

  /* 텍스트 */
  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: normal;
  font-size: 18px;
  /* identical to box height, or 114% */
  text-align: left;

  color: #919191;
}
#box1 {
  display: flex;
  flex-flow: column;
}
#box2 {
  display: flex;
  flex-flow: column;
}
#box3 {
  display: flex;
}
#box4 {
  margin-left: 60px;
  display: flex;
  flex-flow: column;
}
#box6 {
  display: flex;
  flex-flow: column;
}
.box0 {
  display: flex;
  align-items: center;
  width: 782px;
  height: 50px;

  /* background: #e8e8e8; */
  background: white;
  border-radius: 10px;
  border: 0px;
  margin-bottom: 10px;
  padding-left: 10px;
  margin-left: 2px;
  /* border: 1px solid blue; */

  /*  텍스트 */
  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: normal;
  font-size: 18px;
  /* identical to box height, or 114% */
  text-align: left;

  color: #919191;
}
.box6 {
  display: flex;
}
.addValue1 {
  height: 30px;
  display: flex;
  /* text-align: center; */
  vertical-align: middle;

  margin-right: 5px;
  padding-top: 3px;
  padding-left: 5px;
  padding-right: 5px;
  border: 1px solid #307ff5;
  border-radius: 10px;
  color: #307ff5;

  font-family: Noto Sans KR;
  font-style: normal;
}
.addValue1:hover {
  height: 30px;
  display: flex;
  /* text-align: center; */
  vertical-align: middle;

  margin-right: 5px;
  padding-top: 3px;
  padding-left: 5px;
  padding-right: 5px;
  border: 1px solid #ff5757;
  border-radius: 10px;
  color: #ff5757;

  font-family: Noto Sans KR;
  font-style: normal;

  cursor: pointer;
}
.label3 {
  min-width: 200px;
  display: flex;
  text-align: left;
  align-items: center;
  margin-top: -5px;
  margin-left: 5px;

  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: normal;
  font-size: 16px;
}
#btn {
  margin-top: 50px;
  width: 782px;
}
.btn-cancel {
  margin-left: 10px;
}
#thumbnail {
  width: 40%;
  height: 80%;

  overflow: hidden;
  border: 0.5px dashed black;
  border-radius: 3%;
}
#radio {
  height: 40px;
  display: flex;
  vertical-align: middle;
}
.previewImg {
  max-width: 100%;
  max-height: 100%;
  object-fit: cover;
  overflow: hidden;
}
#warning1 {
  display: flex;
  align-items: center;
  margin-left: 10px;

  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: normal;
  font-size: 15px;
  color: #ff5757;
}
#warning2 {
  display: flex;
  align-items: center;
  margin-left: 10px;

  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: normal;
  font-size: 15px;
  color: #ff5757;
}
#warning3 {
  display: flex;
  align-items: center;
  margin-left: 10px;

  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: normal;
  font-size: 15px;
  color: #ff5757;
}
</style>
