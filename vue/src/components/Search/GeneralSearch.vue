<template>
  <el-row class="height5">
    <el-col :span="24" class="test-border"> General Search 여백 </el-col>
  </el-row>

  <el-row class="test-border height95">
    <el-col :span="2" class="test-border"></el-col>
    <el-col :span="19" class="test-border">
      <el-row class="test-border height5 flex-container">
        <el-col :span="18" :offset="2" class="test-border">
          <el-input
            placeholder="검색어를 입력하세요"
            v-model="temp"
            size="mini"
          ></el-input>
        </el-col>
        <el-col :span="2" class="test-border">
          <el-button class="btn-ghost font-noto-bold flex-item">상세</el-button>
        </el-col>
        <el-col :span="2" class="test-border flex-item">
          <el-button class="btn-ghost font-noto-bold">검색</el-button>
        </el-col>
      </el-row>

      <el-row class="height5">
        <el-col :span="22" :offset="2" class="test-border">
          <p class="itemlist-title-left">내 스터디 목록</p>
        </el-col>
      </el-row>

      <el-row class="height25">
        <el-col :span="2" class="test-border"></el-col>
        <el-col :span="4" class="test-border">백준알고스터디</el-col>
        <el-col :span="1" class="test-border"></el-col>
        <el-col :span="4" class="test-border">단기간면접</el-col>
        <el-col :span="1" class="test-border"></el-col>
        <el-col :span="4" class="test-border">정처기</el-col>
        <el-col :span="1" class="test-border"></el-col>
        <el-col :span="4" class="test-border">웹스터디</el-col>
        <el-col :span="3" class="test-border"></el-col>
      </el-row>

      <el-row class="height5">
        <el-col :span="24" class="test-border">
          <a href="">더보기</a> <a href="">접기</a>
        </el-col>
      </el-row>

      <el-row class="height5">
        <el-col :span="22" :offset="2" class="test-border">
          <p class="itemlist-title-left">추천 스터디 목록</p>
        </el-col>
      </el-row>

      <!-- 아이템 목록 시작 -->
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
      <!-- 아이템 목록 끝 -->

      <el-row class="height5">
        <el-col :span="24" class="test-border">
          <a href="">더보기</a> <a href="">접기</a>
        </el-col>
      </el-row>

      <el-row class="height5">
        <el-col :span="22" :offset="2" class="test-border">
          <p class="itemlist-title-left">전체 스터디 목록</p>
        </el-col>
      </el-row>

      <!-- 아이템 목록 시작 -->
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
      <!-- 아이템 목록 끝 -->

      <el-row class="height5">
        <el-col :span="24" class="test-border">
          <a href="">더보기</a> <a href="">접기</a>
        </el-col>
      </el-row>
    </el-col>
  </el-row>
</template>

<script>
export default {
  components: {},
};
</script>

<style>

</style>
