<template>
  <el-row class="height5">
    <el-col :span="24" class="test-border">
      Advanced Search 여백: 입력칸이 생각보다 빡빡함.
    </el-col>
  </el-row>

  <el-row class="test-border height5">
    <el-col :span="2" class="test-border">
      <el-dropdown size="mini" split-button type="">
        분류
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item>스터디</el-dropdown-item>
            <el-dropdown-item>프로젝트</el-dropdown-item>
            <el-dropdown-item>클럽</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </el-col>
    <el-col :span="2" class="test-border">
      <el-dropdown size="mini" split-button type="">
        기술 스택
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item>Java</el-dropdown-item>
            <el-dropdown-item>C++</el-dropdown-item>
            <el-dropdown-item>Python</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </el-col>
    <el-col :span="1" class="test-border">
      <el-input placeholder="기한" v-model="temp" size="mini"></el-input>
    </el-col>
    <el-col :span="1" class="test-border"> ~ </el-col>
    <el-col :span="1" class="test-border">
      <el-input placeholder="기한" v-model="temp" size="mini"></el-input>
    </el-col>
    <el-col :span="1" class="test-border">
      <el-input placeholder="인원" v-model="temp" size="mini"></el-input>
    </el-col>
    <el-col :span="1" class="test-border"> ~ </el-col>
    <el-col :span="1" class="test-border">
      <el-input placeholder="인원" v-model="temp" size="mini"></el-input>
    </el-col>
    <el-col :span="2" class="test-border">
      <el-dropdown size="mini" split-button type="">
        상태
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item>모집중</el-dropdown-item>
            <el-dropdown-item>진행중</el-dropdown-item>
            <el-dropdown-item>종료됨</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </el-col>
    <el-col :span="2" class="test-border">
      <el-dropdown size="mini" split-button type="">
        참여 여부
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item>참여 가능</el-dropdown-item>
            <el-dropdown-item>참여 불가</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </el-col>
    <el-col :span="1" class="test-border">
      <el-dropdown size="mini" split-button type="">
        지역
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item>무관</el-dropdown-item>
            <el-dropdown-item>서울</el-dropdown-item>
            <el-dropdown-item>대구</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </el-col>
    <el-col :span="2" class="test-border">
      <el-input placeholder="팀장 아이디" v-model="temp" size="mini"></el-input>
    </el-col>
    <el-col :span="5" class="test-border">
      <el-input
        placeholder="검색어를 입력하세요"
        v-model="temp"
        size="mini"
      ></el-input>
    </el-col>
    <el-col :span="1" class="test-border">
      <el-button class="btn-ghost font-noto-bold">일반</el-button>
    </el-col>
    <el-col :span="1" class="test-border">
      <el-button class="btn-ghost font-noto-bold">검색</el-button>
    </el-col>
  </el-row>

  <el-row class="test-border height95">
    <el-col :span="2" class="test-border"></el-col>
    <el-col :span="19" class="test-border">
      <el-row class="height5">
        <el-col :span="24" class="test-border">
          스터디(or 프로젝트 or 클럽) 검색 결과: 여백
        </el-col>
      </el-row>

      <!-- Item 목록 시작 -->
      <el-row class="height25">
        <el-col :span="2" class="test-border"></el-col>

        <!-- item 한개 -->
        <el-col :span="4" class="test-border">
          <el-row class="height40 item-img"></el-row>
          <el-row class="height10 item-head-title"
            >백준 알고 스터디 하실 분</el-row
          >
          <el-row class="height10 item-content">기술 스택: Java, Kotlin</el-row>
          <el-row class="height10"></el-row>
          <el-row class="height10 item-content">
            <el-col :span="12" class="test-border left-content"
              >지역: 무관</el-col
            >
            <el-col :span="12" class="test-border right-content">
              <i class="el-icon-lock icon-size"></i>
            </el-col>
          </el-row>
          <el-row class="height10 item-content">
            <el-col :span="12" class="test-border left-content"
              >기간: 14주</el-col
            >
            <el-col :span="12" class="test-border right-content"
              >인원: 4/10</el-col
            >
          </el-row>
          <el-row class="height10">
            <el-col
              :span="12"
              class="test-border item-small-content left-content"
              >작성일: 21.09.01</el-col
            >
            <el-col :span="12" class="test-border right-content">
              <div class="item-state-badge-blue item-small-content">
                모집 중
              </div>
            </el-col>
          </el-row>
        </el-col>
        <el-col :span="1" class="test-border"></el-col>
        <el-col :span="4" class="test-border">
          <el-row class="height40 item-img"></el-row>
          <el-row class="height10 item-head-title"
            >단기간 면접 대비 CS 공부</el-row
          >
          <el-row class="height10 item-content">기술 스택: Java, Kotlin</el-row>
          <el-row class="height10"></el-row>
          <el-row class="height10 item-content">
            <el-col :span="12" class="test-border left-content"
              >지역: 무관</el-col
            >
            <el-col :span="12" class="test-border right-content">자물쇠</el-col>
          </el-row>
          <el-row class="height10 item-content">
            <el-col :span="12" class="test-border left-content"
              >기간: 14주</el-col
            >
            <el-col :span="12" class="test-border right-content"
              >인원: 4/10</el-col
            >
          </el-row>
          <el-row class="height10">
            <el-col
              :span="12"
              class="test-border item-small-content left-content"
              >작성일: 21.09.01</el-col
            >
            <el-col :span="12" class="test-border right-content">
              <div class="item-state-badge-green item-small-content">
                참여 가능
              </div>
              <div class="item-state-badge-green item-small-content">
                진행 중
              </div>
            </el-col>
          </el-row>
        </el-col>
        <el-col :span="1" class="test-border"></el-col>
        <el-col :span="4" class="test-border">정처기</el-col>
        <el-col :span="1" class="test-border"></el-col>
        <el-col :span="4" class="test-border">웹스터디</el-col>
        <el-col :span="3" class="test-border"></el-col>
      </el-row>

      <el-row class="height5"> </el-row>

      <!-- Item 목록 시작 -->
      <el-row class="height25">
        <el-col :span="2" class="test-border"></el-col>

        <!-- item 한개 -->
        <el-col :span="4" class="test-border">
          <el-row class="height40 item-img"></el-row>
          <el-row class="height10 item-head-title"
            >백준 알고 스터디 하실 분</el-row
          >
          <el-row class="height10 item-content">기술 스택: Java, Kotlin</el-row>
          <el-row class="height10"></el-row>
          <el-row class="height10 item-content">
            <el-col :span="12" class="test-border left-content"
              >지역: 무관</el-col
            >
            <el-col :span="12" class="test-border right-content">
              <i class="el-icon-lock icon-size"></i>
            </el-col>
          </el-row>
          <el-row class="height10 item-content">
            <el-col :span="12" class="test-border left-content"
              >기간: 14주</el-col
            >
            <el-col :span="12" class="test-border right-content"
              >인원: 4/10</el-col
            >
          </el-row>
          <el-row class="height10">
            <el-col
              :span="12"
              class="test-border item-small-content left-content"
              >작성일: 21.09.01</el-col
            >
            <el-col :span="12" class="test-border right-content">
              <div class="item-state-badge-blue item-small-content">
                모집 중
              </div>
            </el-col>
          </el-row>
        </el-col>
        <el-col :span="1" class="test-border"></el-col>
        <el-col :span="4" class="test-border">
          <el-row class="height40 item-img"></el-row>
          <el-row class="height10 item-head-title"
            >단기간 면접 대비 CS 공부</el-row
          >
          <el-row class="height10 item-content">기술 스택: Java, Kotlin</el-row>
          <el-row class="height10"></el-row>
          <el-row class="height10 item-content">
            <el-col :span="12" class="test-border left-content"
              >지역: 무관</el-col
            >
            <el-col :span="12" class="test-border right-content">자물쇠</el-col>
          </el-row>
          <el-row class="height10 item-content">
            <el-col :span="12" class="test-border left-content"
              >기간: 14주</el-col
            >
            <el-col :span="12" class="test-border right-content"
              >인원: 4/10</el-col
            >
          </el-row>
          <el-row class="height10">
            <el-col
              :span="12"
              class="test-border item-small-content left-content"
              >작성일: 21.09.01</el-col
            >
            <el-col :span="12" class="test-border right-content">
              <div class="item-state-badge-green item-small-content">
                참여 가능
              </div>
              <div class="item-state-badge-green item-small-content">
                진행 중
              </div>
            </el-col>
          </el-row>
        </el-col>
        <el-col :span="1" class="test-border"></el-col>
        <el-col :span="4" class="test-border">정처기</el-col>
        <el-col :span="1" class="test-border"></el-col>
        <el-col :span="4" class="test-border">웹스터디</el-col>
        <el-col :span="3" class="test-border"></el-col>
      </el-row>

      <el-row class="height5"> </el-row>

      <!-- Item 목록 시작 -->
      <el-row class="height25">
        <el-col :span="2" class="test-border"></el-col>

        <!-- item 한개 -->
        <el-col :span="4" class="test-border">
          <el-row class="height40 item-img"></el-row>
          <el-row class="height10 item-head-title"
            >백준 알고 스터디 하실 분</el-row
          >
          <el-row class="height10 item-content">기술 스택: Java, Kotlin</el-row>
          <el-row class="height10"></el-row>
          <el-row class="height10 item-content">
            <el-col :span="12" class="test-border left-content"
              >지역: 무관</el-col
            >
            <el-col :span="12" class="test-border right-content">
              <i class="el-icon-lock icon-size"></i>
            </el-col>
          </el-row>
          <el-row class="height10 item-content">
            <el-col :span="12" class="test-border left-content"
              >기간: 14주</el-col
            >
            <el-col :span="12" class="test-border right-content"
              >인원: 4/10</el-col
            >
          </el-row>
          <el-row class="height10">
            <el-col
              :span="12"
              class="test-border item-small-content left-content"
              >작성일: 21.09.01</el-col
            >
            <el-col :span="12" class="test-border right-content">
              <div class="item-state-badge-blue item-small-content">
                모집 중
              </div>
            </el-col>
          </el-row>
        </el-col>
        <el-col :span="1" class="test-border"></el-col>
        <el-col :span="4" class="test-border">
          <el-row class="height40 item-img"></el-row>
          <el-row class="height10 item-head-title"
            >단기간 면접 대비 CS 공부</el-row
          >
          <el-row class="height10 item-content">기술 스택: Java, Kotlin</el-row>
          <el-row class="height10"></el-row>
          <el-row class="height10 item-content">
            <el-col :span="12" class="test-border left-content"
              >지역: 무관</el-col
            >
            <el-col :span="12" class="test-border right-content">자물쇠</el-col>
          </el-row>
          <el-row class="height10 item-content">
            <el-col :span="12" class="test-border left-content"
              >기간: 14주</el-col
            >
            <el-col :span="12" class="test-border right-content"
              >인원: 4/10</el-col
            >
          </el-row>
          <el-row class="height10">
            <el-col
              :span="12"
              class="test-border item-small-content left-content"
              >작성일: 21.09.01</el-col
            >
            <el-col :span="12" class="test-border right-content">
              <div class="item-state-badge-green item-small-content">
                참여 가능
              </div>
              <div class="item-state-badge-green item-small-content">
                진행 중
              </div>
            </el-col>
          </el-row>
        </el-col>
        <el-col :span="1" class="test-border"></el-col>
        <el-col :span="4" class="test-border">정처기</el-col>
        <el-col :span="1" class="test-border"></el-col>
        <el-col :span="4" class="test-border">웹스터디</el-col>
        <el-col :span="3" class="test-border"></el-col>
      </el-row>

      <el-row class="height5">
        <el-col :span="24" class="test-border">
          <a href="">더보기</a> <a href="">접기</a> 무한스크롤?
        </el-col>
      </el-row>
    </el-col>
    <el-col :span="3" class="test-border"></el-col>
  </el-row>
</template>

<script>
export default {
  components: {},
};
</script>

<style scoped>
.test-border {
  padding: 0 3px 0 3px;
}
</style>
