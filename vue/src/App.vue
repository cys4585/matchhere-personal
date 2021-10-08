<template>
  <div class="bg-color">
    <router-view />
  </div>
</template>
<script>
export default {};
</script>
<style>
body {
  margin: 0;
}

#app {
  font-family: 'Noto Sans KR', 'S-CoreDream-5Medium', '맑은 고딕', Avenir,
    Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  width: 100vw;
  height: 100vh;
  margin: 0px;
  padding: 0px;
  text-align: center;
  color: #2c3e50;
}

body {
  margin: 0;
  /* 스크롤바 생김->그만큼 x가 안보임->좌우스크롤 발생 << 이를 방지 */
  overflow-x: hidden;
  overflow-y: scroll;
}

/* font cdn:s-core dream */
@font-face {
  font-family: 'S-CoreDream-5Medium';
  src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_six@1.2/S-CoreDream-5Medium.woff')
    format('woff');
  font-weight: normal;
  font-style: normal;
}

.font-noto-bold {
  font-family: 'noto-sans-kr';
  font-weight: 700;
}
.font-noto-md {
  /* 적용 안되면 이걸로 바꿔보기 -> Noto Sans KR  */
  font-family: 'noto-sans-kr';
  font-weight: 500;
}

.font-s-md {
  font-family: 'S-CoreDream-5Medium';
}

.itemlist-title-left {
  font-size: 36px;
  text-align: left;
  margin: 0;
}

.font-20 {
  font-size: 20px;
  margin: 0;
}
.font-14 {
  font-size: 14px;
  margin: 0;
}

.test-border {
  box-sizing: border-box;
  padding: 0 3px 0 3px;
}
.test-router-border {
  box-sizing: border-box;
  height: 90%;
}
.vh100 {
  height: 100vh;
}

.bg-color {
  background-color: #f2f2f2;
  width: 100%;
  height: 100%;
  overflow-y: scroll;
}
.height100 {
  box-sizing: border-box;
  height: 100%;
}
.height96 {
  box-sizing: border-box;
  height: 96%;
}
.height95 {
  box-sizing: border-box;
  height: 95%;
}
.height92 {
  box-sizing: border-box;
  height: 92%;
}
.height90 {
  box-sizing: border-box;
  height: 90%;
}
.height85 {
  box-sizing: border-box;
  height: 85%;
}
.height50 {
  box-sizing: border-box;
  height: 50%;
}
.height40 {
  box-sizing: border-box;
  height: 40%;
}
.height30 {
  box-sizing: border-box;
  height: 30%;
}
.height25 {
  box-sizing: border-box;
  height: 25%;
}
.height20 {
  box-sizing: border-box;
  height: 20%;
}
.height10 {
  box-sizing: border-box;
  height: 10%;
}
.height8 {
  box-sizing: border-box;
  height: 8%;
}
.height5 {
  box-sizing: border-box;
  height: 5%;
}
.height4 {
  box-sizing: border-box;
  height: 4%;
}
.height3 {
  box-sizing: border-box;
  height: 3%;
}
.height2 {
  box-sizing: border-box;
  height: 2%;
}
.height1 {
  box-sizing: border-box;
  height: 1%;
}
.item-border {
  border: 0.5px solid rgba(0, 0, 0, 0.2);
  box-sizing: border-box;
  border-radius: 5px;
  background: #ffffff;
  filter: drop-shadow(2px 4px 4px rgba(0, 0, 0, 0.25));
  padding: 0px;
}

/* buttons */
/*
  color: 글자 색
  background-color: 버튼 배경 색
*/
.btn-1747C9 {
  color: white;
  background-color: #1747c9;
}
.btn-1747C9:hover {
  color: #1747c9;
  background-color: #ffffff;
  border: 2px solid #1747c9;
}
.btn-ghost {
  color: black;
  border: 1px solid black;
  background-color: #f2f2f2;

  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: normal;
  font-size: 14px;
  line-height: 15px;
  text-align: center;
  color: #000000;
}
.btn-ghost:hover {
  color: #f2f2f2;
  border: 1px solid black;
  background-color: #000000;

  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: normal;
  font-size: 14px;
  line-height: 15px;
  text-align: center;
  color: #f2f2f2;
}
.btn-ghost-round {
  background: #ffffff;
  border: 1px solid #000000;
  box-sizing: border-box;
  border-radius: 30px;

  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: 500;
  font-size: 16px;
  line-height: 16px;
  text-align: center;
  color: #000000;
}
.btn-ghost-round:hover {
  background: #000000;
  border: 1px solid #ffffff;
  box-sizing: border-box;
  border-radius: 30px;

  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: 500;
  font-size: 16px;
  line-height: 16px;
  text-align: center;
  color: #ffffff;
}

.btn-ghost-round-blue {
  background: #ffffff;
  border: 1px solid #1747c9;
  box-sizing: border-box;
  border-radius: 30px;

  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: 500;
  font-size: 16px;
  line-height: 16px;
  text-align: center;
  color: #1747c9;
}
.btn-ghost-round-blue:hover {
  background: #1747c9;
  border: 2px solid #1747c9;
  box-sizing: border-box;
  border-radius: 30px;

  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: 500;
  font-size: 16px;
  line-height: 16px;
  text-align: center;
  color: #ffffff;
}
.btn-ghost-round-red {
  background: #ffffff;
  border: 1px solid #f53030;
  box-sizing: border-box;
  border-radius: 30px;
  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: 500;
  font-size: 16px;
  line-height: 16px;
  text-align: center;
  color: #f53030;
}
.btn-ghost-round-red:hover {
  background: #f53030;
  border: 1px solid #f53030;
  box-sizing: border-box;
  border-radius: 30px;
  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: 500;
  font-size: 16px;
  line-height: 16px;
  text-align: center;
  color: #ffffff;
}
.btn-ghost-red {
  font-size: 14pt;
  color: #f53030;
  border: 1px solid #f53030;
}
.btn-ghost-red:hover {
  font-size: 14pt;
  color: #ffffff;
  background-color: #f53030;
  border: 1px solid #f53030;
}
.btn-ghost-blue {
  font-size: 14pt;
  color: #1747c9;
  border: 1px solid #1747c9;
}
.btn-ghost-blue:hover {
  font-size: 14pt;
  color: #ffffff;
  background-color: #1747c9;
  border: 1px solid #1747c9;
}
.btn-create {
  width: 76px;
  height: 32px;
  color: white;
  background-color: #1747c9;

  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: normal;
  font-size: 16px;
}
.btn-create:hover {
  width: 76px;
  height: 32px;
  color: #1747c9;
  background-color: #ffffff;
  border: solid 2px #1747c9;

  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: normal;
  font-size: 16px;
}
.btn-cancel {
  width: 76px;
  height: 32px;
  color: #f53030;
  border: 1px solid #f53030;

  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: normal;
  font-size: 16px;
}
.btn-cancel:hover {
  width: 76px;
  height: 32px;
  color: #ffffff;
  background-color: #f53030;
  border: 1px solid #f53030;

  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: normal;
  font-size: 16px;
}

/* btns 끝 */
/* 정렬 시작 */
/* 정리하고자 하는 것의 부모에 넣어주면 된다. */
.align-center {
  display: flex;
  justify-content: center;
}
/* 자식들을 세로 간격 동등하게 정렬함 */
.flex-parent {
  display: flex;
  flex-direction: column;
  text-align: left;
  justify-content: space-evenly;
}
/* 자식들이 가로 축 기준으로 동일한 높이에 오도록 */
.flex-container {
  display: flex;
  align-content: center;
}

/* 아이콘 높이 가운데 정렬: introduceStudy 참고 */
.flex-items {
  display: flex;
  align-items: center;
}
/* 정렬 끝 */

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
}
.item-state-badge-green {
  display: inline-block;
  padding: 0 3px 0 3px;
  margin: 0 2px 0 2px;
  color: white;
  background: #00bf00;
  border-radius: 3px;
}
.item-state-badge-red {
  display: inline-block;
  padding: 0 3px 0 3px;
  margin: 0 2px 0 2px;
  color: white;
  background: #bf0000;
  border-radius: 3px;
}
.item-state-badge-black {
  display: inline-block;
  padding: 0 3px 0 3px;
  margin: 0 2px 0 2px;
  color: white;
  background: #000000;
  border-radius: 3px;
}

/* icon 크기는 font-size로 작성함 */
.icon-size {
  font-size: 20px;
  font-weight: bold;
}

.item {
  cursor: pointer;
}
/* Home css 시작 */
</style>
