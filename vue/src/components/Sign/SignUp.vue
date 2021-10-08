<template>
  <el-row :gutter="0" class="height100" id="signup">
    <el-col :span="9" :offset="0" id="left">
      <div class="height100">
        <div class="height30"></div>
        <div class="height5"></div>
        <div class="height10" id="P1">
          <p id="p1">회원이신가요?</p>
        </div>
        <div class="height10" id="P2">
          <p id="p2">
            로그인 하고 여러분이 원하는 스터디와 프로젝트를 찾아보세요!
          </p>
        </div>
        <div class="height5"></div>
        <div class="height5">
          <el-button id="login" @click="goSignIn">로그인</el-button>
        </div>
        <div class="height30"></div>
        <div class="height5"></div>
      </div>
    </el-col>

    <!-- Step1 기본정보-->
    <el-col :span="15" :offset="0" id="step1" v-if="state.step == 1">
      <form class="height100">
        <div class="height20"></div>
        <div class="height5" id="H1">
          <p id="h1">회원가입</p>
        </div>
        <div class="height10" id="H2">
          <p id="h2">Step1-기본정보</p>
        </div>
        <div class="height50" id="input1">
          <input
            v-model="state.form.email"
            type="text"
            placeholder="이메일"
            id="email"
            onfocus="this.placeholder=''"
            onblur="this.placeholder='이메일'"
            @blur="checkEmail()"
            autocomplete="off"
            maxlength="30"
          />
          <div id="warning1" style="display: none">
            올바른 이메일 형식이 아닙니다.
          </div>
          <div id="warning2" style="display: none">
            이미 존재하는 이메일입니다!
          </div>
          <div id="success1" style="display: none">
            사용가능한 이메일입니다!
          </div>
          <div id="H3">
            <input
              v-model="state.form.name"
              type="text"
              placeholder="이름"
              id="name"
              onfocus="this.placeholder=''"
              onblur="this.placeholder='이름'"
              @blur="checkName()"
              autocomplete="off"
              maxlength="10"
            />
            <input
              v-model="state.form.nickname"
              type="text"
              placeholder="닉네임"
              id="nickname"
              onfocus="this.placeholder=''"
              onblur="this.placeholder='닉네임'"
              @blur="checkNickName()"
              autocomplete="off"
              maxlength="10"
            />
          </div>
          <div id="warning3" style="display: none">
            올바르지 않은 '이름'입니다.
            <br />
            한글 이름은 1~7자 이내, 영문 이름은 2~10자 이내로 작성해주세요.(혼용
            불가)
          </div>
          <div id="success3" style="display: none">
            사용가능한 '이름'입니다!
          </div>

          <div id="warning4" style="display: none">
            올바르지 않은 '닉네임'입니다.
            <br />
            한글, 영문, 숫자만 가능합니다.(혼용가능) 닉네임 길이는 2~7자 이내로
            작성해주세요
          </div>
          <div id="success2" style="display: none">
            사용가능한 닉네임입니다!
          </div>
          <div id="warning5" style="display: none">
            이미 존재하는 닉네임입니다!
          </div>
          <input
            v-model="state.form.password"
            type="password"
            placeholder="비밀번호"
            id="password"
            onfocus="this.placeholder=''"
            onblur="this.placeholder='비밀번호'"
            @blur="checkPassword()"
            autocomplete="off"
            maxlength="255"
          />
          <div id="warning6" style="display: none">
            비밀번호는 최소 8자 이상이어야 합니다.
            <br />
            비밀번호는 영문(소문자), 영문(대문자), 숫자, 특수문자가 반드시
            조합되어야 합니다.
          </div>
          <input
            v-model="state.form.affirmPassword"
            type="password"
            placeholder="비밀번호 확인"
            id="checkpassword"
            onfocus="this.placeholder=''"
            onblur="this.placeholder='비밀번호 확인'"
            @blur="checkAffirmPassword()"
            autocomplete="off"
            maxlength="255"
          />
          <div id="warning7" style="display: none">
            비밀번호가 일치하지 않습니다.
          </div>
          <select id="position" v-model="state.form.position">
            <option value="">포지션을 선택하세요</option>
            <option value="기획자">기획자</option>
            <option value="개발자">개발자</option>
            <option value="디자이너">디자이너</option>
          </select>

          <select id="city" v-model="state.form.city">
            <option value="">지역을 선택하세요</option>
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
        <div class="height10" id="button0">
          <el-row :gutter="0">
            <el-col :span="12" :offset="0"></el-col>
            <el-col :span="12" :offset="0"></el-col>
          </el-row>

          <el-button id="next0" @click="nextStep1">다음</el-button>
        </div>
        <div class="height5"></div>
      </form>
    </el-col>

    <!-- Step2 개발수준 -->
    <el-col :span="15" :offset="0" id="step3" v-else-if="state.step == 2">
      <div class="height100">
        <div class="height20"></div>
        <div class="height5" id="H1">
          <p id="h1">회원가입</p>
        </div>
        <div class="height10" id="H2">
          <p id="h2">Step2-개발수준</p>
        </div>
        <div class="height40" id="circle">
          <div id="H7">
            <div id="circle1">
              Experienced
              <br />
              (이해가 깊음)
            </div>
            <div id="H8">
              <div id="box-text1">(최대 5개) (영어로 입력)</div>
              <div id="box1">
                <div
                  v-for="techStack in state.form.expTechList"
                  :key="techStack"
                  class="addValue1"
                  @click="handleClick(techStack)"
                >
                  {{ techStack }}
                </div>
              </div>
              <div id="box4">
                <div id="box7">
                  <input
                    v-model="state.exp"
                    @input="stackAutoComplete()"
                    autocomplete="off"
                    type="text"
                    placeholder="기술스택을 입력하세요."
                    id="stack1"
                    onfocus="this.placeholder=''"
                    onblur="this.placeholder='기술스택을 입력하세요'"
                    @keyup.enter="addEx()"
                  />
                  <div id="autocomplete">
                    <div
                      class="autocomplete1"
                      v-for="techStack1 in state.result"
                      @click="addStack1(techStack1)"
                      style="cursor: pointer"
                      :key="techStack1"
                    >
                      <span>
                        {{ techStack1 }}
                      </span>
                    </div>
                  </div>
                </div>
                <div id="box5">
                  <div id="warning8" style="display: none">
                    값을 입력해주세요.
                  </div>
                  <div id="warning8_1" style="display: none">
                    유효하지 않은 기술스택입니다.
                  </div>
                  <div id="warning9" style="display: none">
                    이미 포함되어 있습니다.
                  </div>
                  <div id="warning10" style="display: none">
                    최대 5개까지 가능합니다.
                  </div>
                </div>
              </div>
            </div>
          </div>
          <hr style="border: dotted 1px; margin-left: 180px" />
          <div id="H7">
            <div id="circle1">
              Beginner
              <br />
              (경험해본적 있음)
            </div>
            <div>
              <div id="box-text1">(최대 5개) (영어로 입력)</div>
              <div id="box2">
                <div
                  v-for="techStack in state.form.beginTechList"
                  :key="techStack"
                  class="addValue1"
                  @click="handleClick1(techStack)"
                >
                  {{ techStack }}
                </div>
              </div>
              <div id="box4">
                <div id="box7">
                  <input
                    id="stack2"
                    v-model="state.beg"
                    autocomplete="off"
                    @input="stackAutoComplete1()"
                    type="text"
                    placeholder="기술스택을 입력하세요."
                    onfocus="this.placeholder=''"
                    onblur="this.placeholder='기술스택을 입력하세요'"
                    @keyup.enter="addBe()"
                  />
                  <div id="autocomplete1">
                    <div
                      @click="addStack2(techStack2)"
                      v-for="techStack2 in state.result1"
                      class="autocomplete1"
                      style="cursor: pointer"
                      :key="techStack2"
                    >
                      <span>
                        {{ techStack2 }}
                      </span>
                    </div>
                  </div>
                </div>
                <div id="box5">
                  <div id="warning11" style="display: none">
                    값을 입력해주세요.
                  </div>
                  <div id="warning11_1" style="display: none">
                    유효하지 않은 기술스택입니다.
                  </div>
                  <div id="warning12" style="display: none">
                    이미 포함되어 있습니다.
                  </div>
                  <div id="warning13" style="display: none">
                    최대 5개까지 가능합니다.
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>

        <div class="height10" id="button">
          <el-row :gutter="0">
            <el-col :span="12" :offset="0"></el-col>
            <el-col :span="12" :offset="0"></el-col>
          </el-row>

          <el-button id="previous" @click="previousStep">이전</el-button>
          <el-button id="skip" @click="skipStep">건너뛰기</el-button>
          <el-button id="next" @click="nextStep">다음</el-button>
        </div>
      </div>
    </el-col>

    <!-- Step3 세부정보 -->
    <el-col :span="15" :offset="0" id="step4" v-else>
      <div class="height100">
        <div class="height20"></div>
        <div class="height5" id="H1">
          <p id="h1">회원가입</p>
        </div>
        <div class="height10" id="H2">
          <p id="h2">Step3-세부정보</p>
        </div>
        <div class="height40" id="H8">
          <p id="h8">세부 포지션 (최대 5개)</p>
          <div id="box3">
            <div
              v-for="detailPosition in state.form.dpositionList"
              :key="detailPosition"
              class="addValue1"
              @click="handleClick2(detailPosition)"
            >
              {{ detailPosition }}
            </div>
          </div>
          <div id="box6">
            <input
              v-model="state.dp"
              type="text"
              placeholder="세부 포지션을 입력하세요."
              id="stack2"
              onfocus="this.placeholder=''"
              onblur="this.placeholder='세부 포지션을 입력하세요'"
              @keyup.enter="addPosition()"
              maxlength="10"
            />
            <div id="box5">
              <div id="warning14" style="display: none">값을 입력해주세요.</div>
              <div id="warning15" style="display: none">
                이미 포함되어 있습니다.
              </div>
              <div id="warning16" style="display: none">
                최대 5개까지 가능합니다.
              </div>
            </div>
          </div>
        </div>
        <div class="height10" id="button">
          <el-row :gutter="0">
            <el-col :span="12" :offset="0"></el-col>
            <el-col :span="12" :offset="0"></el-col>
          </el-row>

          <el-button id="previous" @click="previousStep">이전</el-button>
          <el-button id="skip" @click="signUp">건너뛰기(완료)</el-button>
          <el-button id="next" @click="signUp">완료</el-button>
        </div>
      </div>
    </el-col>
  </el-row>
</template>

<script>
import { onUnmounted, reactive, watch } from 'vue';
import { useStore } from 'vuex';
import { useRouter } from 'vue-router';

import techstacks from '@/autocomplete/techstack.js';
import { ElMessage } from 'element-plus';

export default {
  name: 'SignUp',
  setup() {
    const router = useRouter();
    // store/index.js를 쓰겠다는 이야기
    const store = useStore();
    // 독립적인 반응형 값 생성 ref()
    // const signUp = ref(null);
    const state = reactive({
      step: 1,
      form: {
        email: '',
        name: '',
        nickname: '',
        password: '',
        affirmPassword: '',
        position: '',
        city: '',
        expTechList: [],
        beginTechList: [],
        dpositionList: [],
      },
      exp: '',
      beg: '',
      dp: '',
      autoCompleteList: [],
      autoCompleteList1: [],
      autoCompleteList2: [],
      result: null,
      result1: null,
      result2: null,
      validate1: false,
      validate2: false,
      validate3: false,
      validate4: false,
      validate5: false,
    });
    // Step1~4 간의 이동시 이동하는 페이지에 기존에 입력해놨던 값이 하나라도 있었다면 모조리 불러온다.(=값을 입력했으나 unMounted 된 적이 없는 경우)
    // router.push로 해당 페이지로 이동했을 때 store.auth.state.form에 저장되어 있는 내용이 있다면 해당 내용을 불러온다.

    // state.step이 변경되는지 감시한다.
    // 두번째 인자로는 감지할 변수를 작성해야 하나 state.step안에 숫자밖에 없으므로 빈칸으로 해준다.
    watch(state.step, () => {
      // 'state 중 auth 모둘의 값을 쓸 것이다.' 라는 문법
      if (store.state.auth.form) {
        state.form.email = store.state.auth.form.email;
        state.form.name = store.state.auth.form.name;
        state.form.nickname = store.state.auth.form.nickname;
        state.form.password = store.state.auth.form.password;
        state.form.affirmPassword = store.state.auth.form.affirmPassword;
        state.form.position = store.state.auth.form.position;
        state.form.city = store.state.auth.form.city;
        state.form.expTechList = store.state.auth.expTechList;
        state.form.beginTechList = store.state.auth.beginTechList;
        state.form.dpositionList = store.state.auth.dpositionList;
      }
    });

    // 회원가입 페이지를 벗어나면 자동으로 store.state.form에 임시로 저장해놨던 내용들을 초기화 시킨다.
    onUnmounted(() => {
      store.state.auth.form.email = '';
      store.state.auth.form.name = '';
      store.state.auth.form.nickname = '';
      store.state.auth.form.password = '';
      store.state.auth.form.affirmPassword = '';
      store.state.auth.form.position = '';
      store.state.auth.form.city = '';
      store.state.auth.expTechList = [];
      store.state.auth.beginTechList = [];
      store.state.auth.dpositionList = [];
    });
    // 로그인 페이지로 이동
    const goSignIn = function () {
      router.push({ path: '/noheader/signin' });
    };
    // 현제 페이지에서 입력했던 내용들을 vuex-persistedstate가 적용되는 store에 저장시켜놓고
    // 이전 회원가입 Step으로 이동
    const previousStep = function () {
      store.state.auth.form.email = state.form.email;
      store.state.auth.form.name = state.form.name;
      store.state.auth.form.nickname = state.form.nickname;
      store.state.auth.form.password = state.form.password;
      store.state.auth.form.affirmPassword = state.form.affirmPassword;
      store.state.auth.form.position = state.form.position;
      store.state.auth.form.city = state.form.city;
      store.state.auth.expTechList = state.form.expTechList;
      store.state.auth.beginTechList = state.form.beginTechList;
      store.state.auth.dpositionList = state.form.dpositionList;
      state.step = state.step - 1;
      router.push({ path: '/noheader/signup' });
    };
    // 현제 페이지에서 입력했던 내용들을 vuex-persistedstate가 적용되는 store에 저장시켜놓고
    // 회원가입 Step1에서 Step2으로 이동하기전에 유효성 검사
    const nextStep1 = function () {
      // 입력칸이 비었을 경우
      if (state.form.email == '') {
        alert('이메일을 입력하세요.');
      } else if (state.form.name == '') {
        alert('이름을 입력하세요.');
      } else if (state.form.nickname == '') {
        alert('닉네임을 입력하세요.');
      } else if (state.form.password == '') {
        alert('비밀번호를 입력하세요.');
      } else if (state.form.affirmPassword == '') {
        alert('비밀번호 확인을 입력하세요.');
      } else if (state.form.position == '') {
        alert('포지션을 선택하세요');
      } else if (state.form.city == '') {
        alert('지역을 선택하세요');
      }
      // 입력칸이 비진 않았지만, 유효성 검사를 하나라도 통과하지 못했을 경우
      else if (!state.validate1) {
        alert('올바른 이메일 형식이 아닙니다!');
      } else if (!state.validate2) {
        alert('올바른 이름이 아닙니다!');
      } else if (!state.validate3) {
        alert('올바른 닉네임이 아닙니다!');
      } else if (!state.validate4) {
        alert('올바른 비밀번호 형식이 아닙니다!');
      } else if (!state.validate5) {
        alert('비밀번호가 일치하지 않습니다!');
      } else {
        store.state.auth.form.email = state.form.email;
        store.state.auth.form.name = state.form.name;
        store.state.auth.form.nickname = state.form.nickname;
        store.state.auth.form.password = state.form.password;
        store.state.auth.form.affirmPassword = state.form.affirmPassword;
        store.state.auth.form.position = state.form.position;
        store.state.auth.form.city = state.form.city;
        state.step = state.step + 1;
        // window.location.reload();
        router.push({ path: '/noheader/signup' });
      }
    };
    // 현제 페이지에서 입력했던 내용들을 vuex-persistedstate가 적용되는 store에 저장시켜놓고
    // 회원가입 다음 Step으로 이동
    const nextStep = function () {
      store.state.auth.form.email = state.form.email;
      store.state.auth.form.name = state.form.name;
      store.state.auth.form.nickname = state.form.nickname;
      store.state.auth.form.password = state.form.password;
      store.state.auth.form.affirmPassword = state.form.affirmPassword;
      store.state.auth.form.position = state.form.position;
      store.state.auth.form.city = state.form.city;
      store.state.auth.expTechList = state.form.expTechList;
      store.state.auth.beginTechList = state.form.beginTechList;
      store.state.auth.dpositionList = state.form.dpositionList;
      state.step = state.step + 1;
      // window.location.reload();
      router.push({ path: '/noheader/signup' });
    };
    // 회원가입 Step 입력안하고 다음 Step으로 이동 (필수 입력이 아닌 Step들만 건너뛰기 가능)
    const skipStep = function () {
      store.state.auth.form.email = state.form.email;
      store.state.auth.form.name = state.form.name;
      store.state.auth.form.nickname = state.form.nickname;
      store.state.auth.form.password = state.form.password;
      store.state.auth.form.affirmPassword = state.form.affirmPassword;
      store.state.auth.form.position = state.form.position;
      store.state.auth.form.city = state.form.city;
      store.state.auth.expTechList = state.form.expTechList;
      store.state.auth.beginTechList = state.form.beginTechList;
      store.state.auth.dpositionList = state.form.dpositionList;
      state.step = state.step + 1;
      router.push({ path: '/noheader/signup' });
    };

    // 이메일 유효성 검사
    const checkEmail = function () {
      var warning1 = document.getElementById('warning1');
      var warning2 = document.getElementById('warning2');
      var success1 = document.getElementById('success1');
      let emailVal = state.form.email;
      let reg =
        /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
      if (emailVal.match(reg) == null) {
        warning1.style = '';
        success1.style = 'display:none';
        state.validate1 = false;
        // alert('올바른 이메일 형식이 아닙니다.');
      } else {
        warning1.style = 'display:none';
        store.dispatch('auth/checkEmail', state.form.email).then((res) => {
          if (res.data == false) {
            warning2.style = 'display:none';
            success1.style = '';
            state.validate1 = true;
            // alert('사용가능한 이메일 입니다!');
          } else {
            warning2.style = '';
            success1.style = 'display:none';
            state.validate1 = false;
            // alert('이미 존재하는 이메일 입니다!');
          }
        });
      }
    };
    // 이름 유효성 검사
    const checkName = function () {
      const warning3 = document.getElementById('warning3');
      const success3 = document.getElementById('success3');
      let nameVal = state.form.name;
      let reg = /^[가-힣]{1,7}$/;
      let reg1 = /^[a-zA-Z]{2,10}$/;
      // 유효성 검사를 통과하지 못했을 경우
      if (nameVal.match(reg) == null && nameVal.match(reg) == null) {
        warning3.style = '';
        success3.style = 'display:none';
        state.validate2 = false;
        // alert(
        //   '올바른 형식이 아닙니다.\n한글 이름은 1~7자 이내, \n영문 이름은 2~10자 이내로 작성해주세요.\n(혼용 불가)'
        // );
      } else {
        warning3.style = 'display:none';
        success3.style = '';
        state.validate2 = true;
      }
    };

    // 닉네임 유효성 검사
    const checkNickName = function () {
      var warning4 = document.getElementById('warning4');
      var warning5 = document.getElementById('warning5');
      var success2 = document.getElementById('success2');
      let nickNameVal = state.form.nickname;
      let reg = /^[(가-힣a-zA-Z0-9)]{2,7}$/;

      if (nickNameVal.match(reg) == null) {
        warning4.style = '';
        success2.style = 'display:none';
        state.validate3 = false;
        // alert(
        //   '올바른 형식이 아닙니다.\n한글, 영문, 숫자만 가능합니다.\n닉네임 길이는 2~7자 이내여야 합니다.'
        // );
      } else {
        store
          .dispatch('auth/checkNickName', state.form.nickname)
          .then((res) => {
            if (res.data == false) {
              // alert('사용가능한 닉네임 입니다!');
              warning4.style = 'display:none';
              warning5.style = 'display:none';
              success2.style = '';
              state.validate3 = true;
            } else {
              // alert('이미 존재하는 닉네임 입니다!');
              warning4.style = 'display:none';
              warning5.style = '';
              success2.style = 'display:none';
              state.validate3 = false;
            }
          });
      }
    };

    // 비밀번호 유효성 검사
    const checkPassword = function () {
      var warning6 = document.getElementById('warning6');
      let passwordVal = state.form.password;
      let reg =
        /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
      // 유효성 검사를 통과하지 못했을 경우
      if (passwordVal.match(reg) == null) {
        warning6.style = '';
        state.validate4 = false;
        // alert(
        //   '올바른 비밀번호 형식이 아닙니다.\n비밀번호는 최소 8자 이상이어야 합니다.\n비밀번호는 영문(소문자), 영문(대문자), 숫자, 특수문자가 반드시 조합되어야 합니다.'
        // );
      } else {
        warning6.style = 'display:none';
        state.validate4 = true;
      }
    };

    // 비밀번호 확인 유효성 검사
    const checkAffirmPassword = function () {
      var warning7 = document.getElementById('warning7');
      let affirmPasswordVal = state.form.affirmPassword;

      // 유효성 검사를 통과하지 못했을 경우
      if (affirmPasswordVal != state.form.password) {
        warning7.style = '';
        state.validate5 = false;
        // alert('두 비밀번호가 일치하지 않습니다.');
      } else {
        warning7.style = 'display:none';
        state.validate5 = true;
      }
    };

    // Step3 입력한 값을 박스에 추가(Experience)
    const addEx = function () {
      var warning8 = document.getElementById('warning8');
      var warning8_1 = document.getElementById('warning8_1');
      var warning9 = document.getElementById('warning9');
      var warning10 = document.getElementById('warning10');

      // 입력한 값이 유효한 기술스택 목록에 있을 경우
      if (techstacks.includes(state.exp)) {
        // 값을 입력하지 않았고 추가되어있는 techStack이 5개 미만일떄
        if (!state.exp && state.form.expTechList.length < 5) {
          warning8.style = '';
          warning9.style = 'display:none';
          warning10.style = 'display:none';
        }
        // 값을 입력하지 않았고 추가되어있는 techStack이 5개 이상일떄
        else if (!state.exp && state.form.expTechList.length >= 5) {
          warning8.style = '';
          warning9.style = 'display:none';
          warning10.style = '';
        }
        // 기술스택 개수가 5개미만이고 추가되어있는 techStack일때
        else if (
          state.form.expTechList.length < 5 &&
          state.form.expTechList.includes(state.exp)
        ) {
          warning8.style = 'display:none';
          warning9.style = '';
          warning10.style = 'display:none';
        }
        // 기술스택 개수가 5개이상이고 추가되어있지 않은 techStack일때
        else if (
          state.form.expTechList.length >= 5 &&
          !state.form.expTechList.includes(state.exp)
        ) {
          warning8.style = 'display:none';
          warning9.style = 'display:none';
          warning10.style = '';
        }
        // 기술스택 개수가 5개 이상이고 추가되어있는 techStack일때
        else if (
          state.form.expTechList.length >= 5 &&
          state.form.expTechList.includes(state.exp)
        ) {
          warning8.style = 'display:none';
          warning9.style = '';
          warning10.style = '';
        }
        // 기술스택 개수가 5개 미만이고 추가되어있지 않은 techStack일때
        else {
          // 회원가입할때 보낼 data값
          state.form.expTechList.push(state.exp);
          warning8.style = 'display:none';
          warning8_1.style = 'display:none';
          warning9.style = 'display:none';
          warning10.style = 'display:none';
          state.exp = '';
        }
      }
      // 입력한 값이 유효한 기술스택 목록에 없을 경우
      else {
        warning8_1.style = '';
      }
    };

    // Exp자동완성 목록을 보여주는 함수
    const stackAutoComplete = function () {
      // const autocomplete = document.querySelector('.autocomplete');
      const autocomplete = document.getElementById('autocomplete');
      if (state.exp) {
        // autocomplete.classList.remove('disabled');
        autocomplete.style = '';
        // techstack.js 에서 해당 글자로 시작하는 키워드들을 필터링해 state.result에 담는다.
        state.result = techstacks.filter((stack) => {
          // 해당 글자로 시작하는 키워드를 뽑아내기 위해 '^' 를 사용
          // 대소문자 구분 없이 찾아내기 위해 'i' 옵션을 사용
          return stack.match(new RegExp('^' + state.exp, 'i'));
        });
      } else {
        // autocomplete.classList.add('disabled');
        autocomplete.style = 'display:none';
      }
    };

    // 자동완성 목록중에서 내가 클릭한 것을 input에 추가하는 함수
    // 내가 클릭한 것: clickedTechStack
    const addStack1 = function (clickedTechStack) {
      const autocomplete = document.getElementById('autocomplete');
      const warning8_1 = document.getElementById('warning8_1');
      const warning9 = document.getElementById('warning9');
      const warning10 = document.getElementById('warning10');

      // 기술스택 개수가 5개미만이고 추가되어있는 techStack일때
      if (
        state.form.expTechList.length < 5 &&
        state.form.expTechList.includes(clickedTechStack)
      ) {
        warning8_1.style = 'display:none';
        warning9.style = '';
        warning10.style = 'display:none';
      }
      // 기술스택 개수가 5개이상이고 추가되어있지 않은 techStack일때
      else if (
        state.form.expTechList.length >= 5 &&
        !state.form.expTechList.includes(clickedTechStack)
      ) {
        warning8_1.style = 'display:none';
        warning9.style = 'display:none';
        warning10.style = '';
      }
      // 기술스택 개수가 5개 이상이고 추가되어있는 techStack일때
      else if (
        state.form.expTechList.length >= 5 &&
        state.form.expTechList.includes(clickedTechStack)
      ) {
        warning8_1.style = 'display:none';
        warning9.style = '';
        warning10.style = '';
      }
      // 기술스택 개수가 5개 미만이고 추가되어있지 않은 techStack일때
      else {
        // 회원가입할때 보낼 data값
        state.form.expTechList.push(clickedTechStack);
        // `${state.form.expTechList}이 추가되었다!`
        warning8_1.style = 'display:none';
        warning9.style = 'display:none';
        warning10.style = 'display:none';
        autocomplete.style = 'display:none';
        state.exp = '';
      }
    };

    // const addEx = function () {
    //   // 1.추가할 값을 input태그에서 읽어온다
    //   const addValue1 = document.getElementById('stack1').value;
    //   // 2.추가할 div element 생성
    //   // 2-1.추가할 div element 생성
    //   const box = document.createElement('div');
    //   // 2-2. box에 addValue1이라는 이름의 id속성 추가
    //   box.setAttribute('class', 'addValue1');
    //   // 2-3. box에 textnode추가
    //   const textNode = document.createTextNode(addValue1);
    //   box.appendChild(textNode);
    //   // 3. 생성된 박스를 상위div에 추가
    //   document.getElementById('box1').appendChild(box);
    //   // 인풋박스 초기화
    //   // 이미 추가했던 기술스택이라면 경고메세지 띄우기
    //   state.exp = '';
    // };

    // Step3 박스에 요소 추가(Beginner)
    const addBe = function () {
      var warning11 = document.getElementById('warning11');
      var warning11_1 = document.getElementById('warning11_1');
      var warning12 = document.getElementById('warning12');
      var warning13 = document.getElementById('warning13');

      // 입력한 값이 유효한 기술스택 목록에 있을 경우
      if (techstacks.includes(state.beg)) {
        // 값을 입력하지 않았고 추가되어있는 techStack이 5개 미만일떄
        if (!state.beg && state.form.beginTechList.length < 5) {
          warning11.style = '';
          warning12.style = 'display:none';
          warning13.style = 'display:none';
        }
        // 값을 입력하지 않았고 추가되어있는 techStack이 5개 이상일떄
        else if (!state.beg && state.form.beginTechList.length >= 5) {
          warning11.style = '';
          warning12.style = 'display:none';
          warning13.style = '';
        }
        // 기술스택 개수가 5개미만이고 추가되어있는 techStack일때
        else if (
          state.form.beginTechList.length < 5 &&
          state.form.beginTechList.includes(state.beg)
        ) {
          warning11.style = 'display:none';
          warning12.style = '';
          warning13.style = 'display:none';
        }
        // 기술스택 개수가 5개이상이고 추가되어있지 않은 techStack일때
        else if (
          state.form.beginTechList.length >= 5 &&
          !state.form.beginTechList.includes(state.beg)
        ) {
          warning11.style = 'display:none';
          warning12.style = 'display:none';
          warning13.style = '';
        }
        // 기술스택 개수가 5개 이상이고 추가되어있는 techStack일때
        else if (
          state.form.beginTechList.length >= 5 &&
          state.form.beginTechList.includes(state.beg)
        ) {
          warning11.style = 'display:none';
          warning12.style = '';
          warning13.style = '';
        }
        // 기술스택 개수가 5개 미만이고 추가되어있지 않은 techStack일때
        else {
          // 회원가입할때 보낼 data값
          state.form.beginTechList.push(state.beg);
          // state.form, 'exp찍을거임!'
          warning11.style = 'display:none';
          warning11_1.style = 'display:none';
          warning12.style = 'display:none';
          warning13.style = 'display:none';
          state.beg = '';
        }
      }
      // 입력한 값이 유효한 기술스택 목록에 없을 경우
      else {
        warning11_1.style = '';
      }
    };

    // Beg자동완성 목록을 보여주는 함수
    const stackAutoComplete1 = function () {
      // const autocomplete = document.querySelector('.autocomplete');
      const autocomplete = document.getElementById('autocomplete1');
      if (state.beg) {
        // autocomplete.classList.remove('disabled');
        autocomplete.style = '';
        // techstack.js 에서 해당 글자로 시작하는 키워드들을 필터링해 state.result에 담는다.
        state.result1 = techstacks.filter((stack) => {
          // 해당 글자로 시작하는 키워드를 뽑아내기 위해 '^' 를 사용
          // 대소문자 구분 없이 찾아내기 위해 'i' 옵션을 사용
          return stack.match(new RegExp('^' + state.beg, 'i'));
        });
      } else {
        // autocomplete.classList.add('disabled');
        autocomplete.style = 'display:none';
      }
    };

    // 자동완성 목록중에서 내가 클릭한 것을 박스에 추가하는 함수
    // 내가 클릭한 것: clickedTechStack
    const addStack2 = function (clickedTechStack) {
      const autocomplete = document.getElementById('autocomplete1');
      const warning11_1 = document.getElementById('warning11_1');
      const warning12 = document.getElementById('warning12');
      const warning13 = document.getElementById('warning13');

      // 기술스택 개수가 5개미만이고 추가되어있는 techStack일때
      if (
        state.form.beginTechList.length < 5 &&
        state.form.beginTechList.includes(clickedTechStack)
      ) {
        warning11_1.style = 'display:none';
        warning12.style = '';
        warning13.style = 'display:none';
      }
      // 기술스택 개수가 5개이상이고 추가되어있지 않은 techStack일때
      else if (
        state.form.beginTechList.length >= 5 &&
        !state.form.beginTechList.includes(clickedTechStack)
      ) {
        warning11_1.style = 'display:none';
        warning12.style = 'display:none';
        warning13.style = '';
      }
      // 기술스택 개수가 5개 이상이고 추가되어있는 techStack일때
      else if (
        state.form.beginTechList.length >= 5 &&
        state.form.beginTechList.includes(clickedTechStack)
      ) {
        warning11_1.style = 'display:none';
        warning12.style = '';
        warning13.style = '';
      }
      // 기술스택 개수가 5개 미만이고 추가되어있지 않은 techStack일때
      else {
        // 회원가입할때 보낼 data값
        state.form.beginTechList.push(clickedTechStack);
        // `${state.form.beginTechList}이 추가되었다!`

        warning11_1.style = 'display:none';
        warning12.style = 'display:none';
        warning13.style = 'display:none';
        autocomplete.style = 'display:none';
        state.beg = '';
      }
    };

    // const addBe = function () {
    //   // 1.추가할 값을 input태그에서 읽어온다
    //   const addValue2 = document.getElementById('stack2').value;
    //   // 2.추가할 div element 생성
    //   // 2-1.추가할 div element 생성
    //   const box = document.createElement('div');
    //   // 2-2. box에 addValue2라는 이름의 id속성 추가
    //   box.setAttribute('id', 'addValue2');
    //   // 2-3. box에 textnode추가
    //   const textNode = document.createTextNode(addValue2);
    //   box.appendChild(textNode);
    //   // 3. 생성된 박스를 상위div에 추가
    //   document.getElementById('box2').appendChild(box);
    //   // 인풋박스 초기화
    //   // 이미 추가했던 기술스택이라면 경고메세지 띄우기
    //   state.beg = '';
    // };

    // 내가 입력한 값을 세부정보 Box에 추가
    const addPosition = function () {
      var warning14 = document.getElementById('warning14');
      var warning15 = document.getElementById('warning15');
      var warning16 = document.getElementById('warning16');
      // 값을 입력하지 않았고 추가되어있는 detailPosition이 5개 미만일떄
      if (!state.dp && state.form.dpositionList.length < 5) {
        warning14.style = '';
        warning15.style = 'display:none';
        warning16.style = 'display:none';
      }
      // 값을 입력하지 않았고 추가되어있는 detailPosition이 5개 이상일떄
      else if (!state.dp && state.form.dpositionList.length >= 5) {
        warning14.style = '';
        warning15.style = 'display:none';
        warning16.style = '';
      }
      // 기술스택 개수가 5개이하이고 추가되어있는 techStack일때
      else if (
        state.form.dpositionList.length < 5 &&
        state.form.dpositionList.includes(state.dp)
      ) {
        warning14.style = 'display:none';
        warning15.style = '';
        warning16.style = 'display:none';
      }
      // 기술스택 개수가 5개이상이고 추가되어있지 않은 techStack일때
      else if (
        state.form.dpositionList.length >= 5 &&
        !state.form.dpositionList.includes(state.dp)
      ) {
        warning14.style = 'display:none';
        warning15.style = 'display:none';
        warning16.style = '';
      }
      // 기술스택 개수가 5개 이상이고 추가되어있는 techStack일때
      else if (
        state.form.dpositionList.length >= 5 &&
        state.form.dpositionList.includes(state.dp)
      ) {
        warning14.style = 'display:none';
        warning15.style = '';
        warning16.style = '';
      }
      // 기술스택 개수가 5개 이하이고 추가되어있지 않은 techStack일때
      else {
        // 회원가입할때 보낼 data값
        state.form.dpositionList.push(state.dp);
        // state.form, '세부포지션을 찍을거임!'
        warning14.style = 'display:none';
        warning15.style = 'display:none';
        warning16.style = 'display:none';
        state.dp = '';
      }
    };
    // expTechList에서 내가 클릭한 것을 뺀 나머지를 리스트로 보여주는 함수
    const handleClick = (clickedTechStack) => {
      state.form.expTechList = state.form.expTechList.filter(
        (techStack) => techStack !== clickedTechStack
      );
    };
    // beginTechList에서 내가 클릭한 것을 뺀 나머지를 리스트로 보여주는 함수
    const handleClick1 = (clickedTechStack) => {
      state.form.beginTechList = state.form.beginTechList.filter(
        (techStack) => techStack !== clickedTechStack
      );
    };
    // dpositionList에서 내가 클릭한 것을 뺀 나머지를 리스트로 보여주는 함수
    const handleClick2 = (clickedDetailPosition) => {
      state.form.dpositionList = state.form.dpositionList.filter(
        (detailPosition) => detailPosition !== clickedDetailPosition
      );
    };

    // 회원가입 요청
    const signUp = function () {
      // modules의 auth.js에서 signIn 액션을 dispatch함
      store
        .dispatch('auth/signUp', state.form)
        .then((res) => {
          if (res.status == 200) {
            ElMessage({
              showClose: true,
              message: '회원가입이 성공적으로 완료되었습니다!',
              type: 'success',
            });
            window.location = '/noheader/signin';
          } else {
            ElMessage({
              showClose: true,
              message: '회원가입에 실패하였습니다.',
              type: 'error',
            });

            window.location = '/noheader/signup';
          }
        })
        .catch((err) => {
          ElMessage({
            showClose: true,
            message: '회원가입에 실패하였습니다.',
            type: 'error',
          });
          window.location = '/noheader/signup';
        });
    };
    return {
      store,
      state,
      router,
      goSignIn,
      previousStep,
      nextStep1,
      nextStep,
      skipStep,
      signUp,
      checkEmail,
      checkName,
      checkNickName,
      checkPassword,
      checkAffirmPassword,
      addEx,
      addBe,
      addPosition,
      handleClick,
      handleClick1,
      handleClick2,
      stackAutoComplete,
      stackAutoComplete1,
      addStack1,
      addStack2,
    };
  },
};
</script>

<style scoped>
/* 공통 */
.el-row {
  margin-bottom: 20px;
}
.el-col {
  border-radius: 4px;
}
.bg-purple-dark {
  background: red;
}
.bg-purple {
  background: green;
}
.bg-purple-light {
  background: blue;
}
.grid-content {
  border-radius: 4px;
  min-height: 36px;
}
.row-bg {
  padding: 10px 0;
  background-color: #f9fafc;
}
#warning1 {
  display: flex;
  margin-left: 190px;

  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: normal;
  font-size: 15px;
  color: #ff5757;
}
#warning2 {
  display: flex;
  margin-left: 190px;

  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: normal;
  font-size: 15px;
  color: #ff5757;
}
#warning3 {
  display: flex;
  text-align: left;
  margin-left: 190px;

  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: normal;
  font-size: 15px;
  color: #ff5757;
}
#warning4 {
  display: flex;
  text-align: left;
  margin-left: 190px;

  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: normal;
  font-size: 15px;
  color: #ff5757;
}
#warning5 {
  display: flex;
  text-align: left;
  margin-left: 190px;

  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: normal;
  font-size: 15px;
  color: #ff5757;
}
#warning6 {
  display: flex;
  text-align: left;
  margin-left: 190px;

  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: normal;
  font-size: 15px;
  color: #ff5757;
}
#warning7 {
  display: flex;
  text-align: left;
  margin-left: 190px;

  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: normal;
  font-size: 15px;
  color: #ff5757;
}
#warning8 {
  display: flex;
  align-items: center;
  margin-left: 10px;

  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: normal;
  font-size: 15px;
  color: #ff5757;
}
#warning8_1 {
  display: flex;
  align-items: center;
  margin-left: 10px;

  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: normal;
  font-size: 15px;
  color: #ff5757;
}
#warning9 {
  display: flex;
  align-items: center;
  margin-left: 10px;

  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: normal;
  font-size: 15px;
  color: #ff5757;
}
#warning10 {
  display: flex;
  align-items: center;
  margin-left: 10px;

  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: normal;
  font-size: 15px;
  color: #ff5757;
}
#warning11 {
  display: flex;
  align-items: center;
  margin-left: 10px;

  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: normal;
  font-size: 15px;
  color: #ff5757;
}
#warning11_1 {
  display: flex;
  align-items: center;
  margin-left: 10px;

  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: normal;
  font-size: 15px;
  color: #ff5757;
}
#warning12 {
  display: flex;
  align-items: center;
  margin-left: 10px;

  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: normal;
  font-size: 15px;
  color: #ff5757;
}
#warning13 {
  display: flex;
  align-items: center;
  margin-left: 10px;

  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: normal;
  font-size: 15px;
  color: #ff5757;
}
#warning14 {
  display: flex;
  align-items: center;
  margin-left: 10px;

  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: normal;
  font-size: 15px;
  color: #ff5757;
}
#warning15 {
  display: flex;
  align-items: center;
  margin-left: 10px;

  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: normal;
  font-size: 15px;
  color: #ff5757;
}
#warning16 {
  display: flex;
  align-items: center;
  margin-left: 10px;

  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: normal;
  font-size: 15px;
  color: #ff5757;
}
#success1 {
  display: flex;
  margin-left: 190px;

  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: normal;
  font-size: 15px;
  color: #307ff5;
}
#success2 {
  display: flex;
  margin-left: 190px;

  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: normal;
  font-size: 15px;
  color: #307ff5;
}
#success3 {
  display: flex;
  margin-left: 190px;

  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: normal;
  font-size: 15px;
  color: #307ff5;
}
#left {
  background: linear-gradient(
    180deg,
    rgba(45, 109, 206, 0.6) 0%,
    rgba(66, 142, 255, 0.7) 55%,
    #c4e6ff 100%
  );
}
#P1 {
  display: flex;
  align-items: center;
  justify-content: center;
}
#p1 {
  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: bold;
  font-size: 40px;
  line-height: 58px;
  /* identical to box height, or 145% */

  text-align: center;
  margin-top: 0px;
  margin-bottom: 0px;
  color: #ffffff;
}
#P2 {
  display: flex;
  align-items: center;
  justify-content: center;
}
#p2 {
  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: 500;
  font-size: 20px;
  line-height: 30px;
  /* or 150% */

  text-align: center;
  margin-top: 0px;
  margin-bottom: 0px;
  color: #ffffff;
}
#login {
  width: 64px;
  height: 18px;

  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: 500;
  font-size: 16px;
  line-height: 16px;
  /* or 100% */

  text-align: center;

  color: #000000;

  /* 버튼 */
  width: 178px;
  height: 50px;

  border: 0px;
  background: #ffffff;
  border-radius: 30px;
}
#button0 {
  display: flex;
  align-items: center;
}
#button {
  display: flex;
  align-items: center;
}
#previous {
  /* 버튼 */
  width: 150px;
  height: 54px;

  background: #ff5757;
  border: 0px;
  border-radius: 30px;
  margin-left: 20%;

  /* 글자 */
  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: 500;
  font-size: 16px;
  line-height: 16px;
  /* identical to box height, or 100% */

  text-align: center;

  color: #ffffff;
}
#skip {
  /* 버튼 */
  width: 150px;
  height: 54px;

  background: #ffffff;
  border: 2px solid #307ff5;
  box-sizing: border-box;
  border-radius: 30px;
  margin-left: 30%;

  /* 글자 */
  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: 500;
  font-size: 16px;
  line-height: 16px;
  /* identical to box height, or 100% */

  text-align: center;
  color: #307ff5;
}
#next {
  /* 버튼 */
  width: 150px;
  height: 54px;

  background: #307ff5;
  border: 0px;
  border-radius: 30px;
  margin-left: 10px;

  /* 글자 */
  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: 500;
  font-size: 16px;
  line-height: 16px;
  /* identical to box height, or 100% */

  text-align: center;

  color: #ffffff;
}
#H1 {
  display: flex;
  align-items: center;
  justify-content: left;
}
#h1 {
  width: 200px;
  height: 58px;

  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: 500;
  font-size: 48px;
  line-height: 58px;
  /* identical to box height, or 121% */

  display: flex;
  align-items: center;
  margin-top: 0px;
  margin-bottom: 0px;
  margin-left: 180px;

  color: #202121;
}
#H2 {
  display: flex;
  align-items: center;
  justify-content: left;
}
#h2 {
  width: 200px;
  height: 40px;

  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: 500;
  font-size: 24px;
  line-height: 40px;
  /* identical to box height, or 167% */

  display: flex;
  align-items: center;
  vertical-align: middle;
  margin-top: 0px;
  margin-bottom: 0px;
  margin-left: 180px;

  color: #202121;
}
#H3 {
  display: flex;
  align-items: center;
  justify-content: left;
}
/* -----------------------------------------------step1------------------------------------- */
#next0 {
  /* 버튼 */
  width: 150px;
  height: 54px;

  background: #307ff5;
  border: 0px;
  border-radius: 30px;
  margin-left: 70%;

  /* 글자 */
  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: 500;
  font-size: 16px;
  line-height: 16px;
  /* identical to box height, or 100% */

  text-align: center;

  color: #ffffff;
}
#input1 {
  display: flex;
  flex-flow: column;
}
#email {
  width: 400px;
  height: 48px;

  background: #e8e8e8;
  border-radius: 10px;
  border: 0px;

  margin-top: 10px;

  margin-left: 180px;

  /* 이메일 텍스트 */
  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: normal;
  font-size: 14px;
  line-height: 16px;
  /* identical to box height, or 114% */
  padding-left: 10px;
  text-align: left;

  color: #919191;
}
#name {
  width: 189px;
  height: 48px;

  background: #e8e8e8;
  border-radius: 10px;
  border: 0px;
  margin-top: 10px;
  margin-left: 180px;

  /* 이름 텍스트 */
  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: normal;
  font-size: 14px;
  line-height: 16px;
  /* identical to box height, or 114% */
  padding-left: 10px;
  text-align: left;

  color: #919191;
}
#nickname {
  width: 189px;
  height: 48px;

  background: #e8e8e8;
  border-radius: 10px;
  border: 0px;
  margin-left: 10px;
  margin-top: 10px;
  padding-left: 10px;

  /* 닉네임 텍스트 */
  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: normal;
  font-size: 14px;
  line-height: 16px;
  /* identical to box height, or 114% */
  text-align: left;

  color: #919191;
}
#password {
  width: 400px;
  height: 48px;

  background: #e8e8e8;
  border-radius: 10px;
  border: 0px;
  margin-top: 10px;
  padding-left: 10px;
  margin-left: 180px;

  /* 비밀번호 텍스트 */
  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: normal;
  font-size: 14px;
  line-height: 16px;
  /* identical to box height, or 114% */
  text-align: left;

  color: #919191;
}
#checkpassword {
  width: 400px;
  height: 48px;

  background: #e8e8e8;
  border-radius: 10px;
  border: 0px;
  margin-top: 10px;
  padding-left: 10px;
  margin-left: 180px;

  /* 비밀번호확인 텍스트 */
  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: normal;
  font-size: 14px;
  line-height: 16px;
  /* identical to box height, or 114% */
  text-align: left;

  color: #919191;
}
#field {
  width: 400px;
  height: 48px;

  background: #e8e8e8;
  border-radius: 10px;
  border: 0px;
  margin-bottom: 10px;
  padding-left: 10px;
  margin-left: 180px;

  /* 분야 선택 텍스트 */
  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: normal;
  font-size: 14px;
  line-height: 16px;
  /* identical to box height, or 114% */
  text-align: left;

  color: #919191;
}
#position {
  width: 412px;
  height: 50px;

  background: #e8e8e8;
  border-radius: 10px;
  border: 0px;
  padding-left: 10px;
  margin-top: 10px;
  margin-left: 180px;

  /* 지역 선택 텍스트 */
  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: normal;
  font-size: 14px;
  line-height: 16px;
  /* identical to box height, or 114% */
  text-align: left;

  color: #919191;
}
#city {
  width: 412px;
  height: 50px;

  background: #e8e8e8;
  border-radius: 10px;
  border: 0px;
  padding-left: 10px;
  margin-top: 10px;
  margin-left: 180px;

  /* 지역 선택 텍스트 */
  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: normal;
  font-size: 14px;
  line-height: 16px;
  /* identical to box height, or 114% */
  text-align: left;

  color: #919191;
}

/* -------------------------------------Step2----------------------------- */
#H7 {
  display: flex;
}
#circle {
}
#circle1 {
  margin-left: 180px;
  width: 150px;
  height: 150px;
  border: solid 1px black;
  border-radius: 100px;

  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: 500;
  font-size: 14px;
  line-height: 20px;
  display: flex;
  align-items: center;
  justify-content: center;

  color: #000000;
}
#box-text1 {
  display: flex;
  width: 332px;
  margin-left: 65px;

  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: normal;
  font-size: 14px;
}
#box1 {
  display: flex;
  flex-wrap: wrap;
  width: 332px;
  height: 87px;
  padding-left: 10px;
  padding-top: 5px;

  border: 1px solid #000000;
  box-sizing: border-box;
  border-radius: 15px;
  margin-left: 65px;
}
#box2 {
  display: flex;
  flex-wrap: wrap;
  width: 332px;
  height: 87px;
  padding-left: 10px;
  padding-top: 5px;

  border: 1px solid #000000;
  box-sizing: border-box;
  border-radius: 15px;
  margin-left: 65px;
}
#stack1 {
  width: 200px;
  height: 40px;

  background: #e8e8e8;
  border-radius: 10px;
  border: 0px;

  display: flex;
  margin-left: 65px;
  margin-top: 15px;
  margin-bottom: 5px;

  /* 기술스택 텍스트 */
  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: normal;
  font-size: 14px;
  line-height: 16px;
  /* identical to box height, or 114% */
  padding-left: 10px;
  text-align: left;

  color: #919191;
}
#stack2 {
  width: 200px;
  height: 40px;

  background: #e8e8e8;
  border-radius: 10px;
  border: 0px;

  display: flex;
  margin-left: 65px;
  margin-top: 15px;
  margin-bottom: 10px;

  /* 기술스택 텍스트 */
  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: normal;
  font-size: 14px;
  line-height: 16px;
  /* identical to box height, or 114% */
  padding-left: 10px;
  text-align: left;

  color: #919191;
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
#box7 {
  display: flex;
  flex-flow: column;
}
#autocomplete {
  position: absolute;
  display: none;
  display: flex;
  flex-flow: column;
  max-height: 150px;
  overflow: auto;
  margin-top: 60px;
  margin-left: 66px;
  border-radius: 4px;
}
#autocomplete1 {
  position: absolute;
  display: none;
  display: flex;
  flex-flow: column;
  max-height: 150px;
  overflow: auto;
  margin-top: 60px;
  margin-left: 66px;
  border-radius: 4px;
}
.autocomplete1 {
  width: 200px;
  background: white;
  /* background: #e8e8e8; */
}
.autocomplete1:hover {
  width: 200px;
  background: #3f8bfc;
  /* background: #307ff5; */
  color: white;
}
.autocomplete1:active {
  width: 200px;
  background: black;
}
.autocomplete2 {
  width: 200px;
  background: white;
  /* background: #e8e8e8; */
}
.autocomplete2:hover {
  width: 200px;
  background: #3f8bfc;
  /* background: #307ff5; */
  color: white;
}
.autocomplete2:active {
  width: 200px;
  background: black;
}
/* --------------------------------------------Step3-------------------------------------------- */
#H8 {
  display: flex;
  flex-flow: column;
}
#h8 {
  display: flex;
  margin-left: 180px;
}
#box3 {
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  justify-content: left;

  width: 397px;
  height: 87px;
  margin-left: 180px;
  padding-left: 10px;
  padding-top: 2px;

  border: 1px solid #000000;
  box-sizing: border-box;
  border-radius: 15px;
}
#box4 {
  display: flex;
}
#box5 {
  display: flex;
  flex-flow: column;
  justify-content: center;
}
#box6 {
  display: flex;
  margin-left: 115px;
}
</style>
