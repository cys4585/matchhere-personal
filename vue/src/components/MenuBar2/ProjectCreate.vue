<template>
  <div>
    <el-row :gutter="0">
      <el-col :span="5" :offset="0">
        <div class="height100">1</div>
      </el-col>
      <el-col :span="13" :offset="0">
        <div class="height100">
          <div id="h1">프로젝트 생성</div>
          <hr />
          <div id="box1">
            <label id="h2">프로젝트 이름</label>
            <input
              type="text"
              placeholder="이름을 입력하세요."
              id="input"
              onfocus="this.placeholder=''"
              onblur="this.placeholder='이름을 입력하세요'"
            />
          </div>
          <div id="box1">
            <label id="h2">기술스택</label>
            <input
              type="text"
              placeholder="사용하는 기술 스택을 입력하세요."
              id="input"
              onfocus="this.placeholder=''"
              onblur="this.placeholder='사용하는 기술 스택을 입력하세요'"
            />
          </div>
          <div id="box3">
            <div id="box2">
              <div id="box1">
                <label id="h2">일정</label>
                <input
                  type="text"
                  placeholder="프로젝트 일정을 입력하세요"
                  id="input1"
                  onfocus="this.placeholder=''"
                  onblur="this.placeholder='프로젝트 일정을 입력하세요'"
                />
              </div>
              <div id="box1">
                <label id="h2">프로젝트 기간(단위: 주)</label>
                <input
                  type="text"
                  placeholder="숫자를 입력하세요"
                  id="input1"
                  onfocus="this.placeholder=''"
                  onblur="this.placeholder='숫자를 입력하세요'"
                />
              </div>

              <div id="box1">
                <label id="h2">프로젝트 공개 여부</label>
                <div id="radio">
                  <!-- radio 타입은 name명이 같을 경우 하나만 선택된다. -->
                  <label>
                    <input type="radio" name="study" value="open" />
                    공개
                  </label>
                  <label>
                    <input type="radio" name="study" value="private" />
                    비공개
                  </label>
                </div>
              </div>
            </div>
            <div id="box4">
              <label id="h2">프로필 사진 등록</label>
              <el-upload
                class="upload-demo"
                drag
                action="https://jsonplaceholder.typicode.com/posts/"
                :on-preview="handlePreview"
                :on-remove="handleRemove"
                :file-list="fileList"
                multiple
              >
                <i class="el-icon-upload"></i>
                <div class="el-upload__text">
                  파일을 드래그 하거나
                  <br />
                  <em>클릭해서 업로드 하세요</em>
                </div>
              </el-upload>
            </div>
          </div>

          <div id="box1">
            <label id="h2">팀장 역할</label>
            <select id="city">
              <option value="1">11</option>
              <option value="2">22</option>
              <option value="3">33</option>
              <option value="4">44</option>
            </select>
          </div>

          <div id="box1">
            <label id="h2">역할</label>
            <div id="box5">
              <label id="h3">개발자</label>
              <input
                type="text"
                placeholder="숫자만 입력"
                id="input3"
                onfocus="this.placeholder=''"
                onblur="this.placeholder='숫자만 입력'"
              />
              <label id="h4"> 명</label>
              <label id="h3">기획자</label>
              <input
                type="text"
                placeholder="숫자만 입력"
                id="input3"
                onfocus="this.placeholder=''"
                onblur="this.placeholder='숫자만 입력'"
              />
              <label id="h4"> 명</label>
              <label id="h3">디자이너</label>
              <input
                type="text"
                placeholder="숫자만 입력"
                id="input3"
                onfocus="this.placeholder=''"
                onblur="this.placeholder='숫자만 입력'"
              />
              <label id="h4"> 명</label>
            </div>
          </div>

          <div id="box1">
            <label id="h2">지역</label>
            <select id="city">
              <option value="1">11</option>
              <option value="2">22</option>
              <option value="3">33</option>
              <option value="4">44</option>
            </select>
          </div>

          <div id="box1">
            <label id="h2">소속 클럽</label>
            <select id="city">
              <option value="none">없음</option>
              <option value="B">B</option>
              <option value="C">C</option>
              <option value="D">D</option>
            </select>
          </div>
          <div id="box1">
            <label id="h2">소개</label>
            <textarea
              type="textarea"
              placeholder="해당 프로젝트에 대해 소개해주세요"
              id="input2"
              onfocus="this.placeholder=''"
              onblur="this.placeholder='해당 프로젝트에 대해 소개해주세요'"
              maxlength="300"
              show-word-limit
            />
          </div>
          <div id="btn">
            <el-button class="btn-create" @click="goIntroduce">생성</el-button>
            <el-button class="btn-cancel" @click="goHome">취소</el-button>
          </div>
        </div>
      </el-col>
      <el-col :span="6" :offset="0">
        <div class="height100">3</div>
      </el-col>
    </el-row>
  </div>
</template>
<script>
// import { useStore } from 'vuex';
import { useRouter } from 'vue-router';
import { reactive } from 'vue';
import { useStore } from 'vuex';

export default {
  name: 'projectCreate',
  components: {},
  setup() {
    const router = useRouter();
    const store = useStore();
    // 독립적인 반응형 값 생성 ref()
    // const create = ref(null);
    const state = reactive({
      form: {},
    });

    const goIntroduce = function () {
      router.push({ path: '/subheader/introduce' });
    };
    const goHome = function () {
      router.push({ path: '/nosubheader/home' });
    };

    return {
      goIntroduce,
      goHome,
      store,
      state,
    };
  },
};
</script>
<style scoped>
#h1 {
  width: 300px;
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
#h3 {
  width: 200px;
  height: 35px;

  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: normal;
  font-size: 17px;
  line-height: 29px;
  margin-top: 10px;
  margin-left: 2px;
  text-align: left;
  color: #000000;

  display: flex;
  justify-content: flex-end;
}
#h4 {
  width: 200px;
  height: 35px;

  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: normal;
  font-size: 17px;
  line-height: 29px;
  margin-top: 10px;
  margin-left: 2px;
  text-align: left;
  color: #000000;
}
#input {
  width: 782px;
  height: 50px;

  background: #e8e8e8;
  border-radius: 10px;
  border: 0px;
  margin-bottom: 10px;
  padding-left: 10px;
  margin-left: 2px;

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
#input1 {
  width: 363px;
  height: 50px;

  background: #e8e8e8;
  border-radius: 10px;
  border: 0px;
  margin-bottom: 10px;
  padding-left: 10px;
  margin-left: 2px;

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
#input2 {
  width: 782px;
  height: 200px;

  background: #e8e8e8;
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
  font-size: 14px;
  line-height: 16px;
  /* identical to box height, or 114% */
  text-align: left;
  align-content: flex-start;
  resize: none;

  color: #919191;
}
#input3 {
  width: 114px;
  height: 50px;

  background: #e8e8e8;
  border-radius: 10px;
  border: 0px;
  margin-bottom: 10px;
  padding-left: 10px;
  margin-left: 2px;

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
#city {
  cursor: pointer;
  width: 794px;
  height: 52px;

  background: #e8e8e8;
  border-radius: 10px;
  border: 0px;
  margin-bottom: 10px;
  padding-left: 10px;
  margin-left: 2px;

  /* 텍스트 */
  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: normal;
  font-size: 14px;
  line-height: 16px;
  /* identical to box height, or 114% */
  text-align: left;

  color: #919191;
}
#box1 {
  width: 794px;
  display: flex;
  flex-flow: column;
}
#box2 {
  width: 375px;
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
#box5 {
  display: flex;
}
#btn {
  margin-top: 50px;
}
.btn-cancel {
  margin-left: 10px;
}
#radio {
  height: 40px;
  display: flex;
  vertical-align: middle;
}
</style>
