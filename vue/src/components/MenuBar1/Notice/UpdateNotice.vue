<template>
  <el-row class="height100">
    <el-col :span="3"></el-col>
    <el-col :span="19" v-if="parameter"
      ><el-row class="height5">
        <el-col :span="2">제목:</el-col>
        <el-col :span="22" style="margin: auto 0">
          <input
            type="text"
            id="title"
            placeholder="제목을 입력하세요"
            v-model="parameter.studyArticleUpdateRequestDto.title"
          />
        </el-col>
      </el-row>
      <el-row class="height5"> </el-row>
      <el-row style="height: 65%">
        <el-col :span="24">
          <QuillEditor
            v-model:content="parameter.studyArticleUpdateRequestDto.content"
            contentType="text"
          />
        </el-col>
      </el-row>
      <el-row class="height5"> </el-row>
      <el-row class="height5">
        <el-col :span="9"></el-col>
        <el-col :span="2">
          <el-button
            class="btn-1747C9 font-noto-bold"
            @click="goReadDetailNotice"
            >수정</el-button
          >
        </el-col>
        <el-col :span="1"></el-col>
        <el-col :span="2">
          <el-button
            class="btn-ghost-red"
            style="font-size: 14px"
            @click="goBack"
          >
            취소
          </el-button>
        </el-col>
        <el-col :span="10"></el-col>
      </el-row>
      <el-row style="height: 20%"></el-row>
    </el-col>
    <el-col :span="2"></el-col>
  </el-row>
</template>
<script>
import { QuillEditor } from '@vueup/vue-quill';
import '@vueup/vue-quill/dist/vue-quill.snow.css';

import { reactive, computed, watch } from 'vue';
import { useStore } from 'vuex';
import { useRouter } from 'vue-router';

export default {
  components: {
    QuillEditor,
  },
  setup() {
    const store = useStore();
    const router = useRouter();

    // 1. 게시글 상세조회로 우선 출력할 값을 가져오고
    // 1-1. board id와 article 번호를 가져와야 함.
    const boardId = computed(
      () => store.getters['study/studyNoticeBoardIdGetter']
    );
    const articleId = computed(
      () => store.getters['study/studyArticleIdGetter']
    );
    // 1-2. 게시글 내용을 가져와야 함
    // api요청에 보낼 파라미터
    const param = reactive({
      form: {
        boardid: boardId.value,
        articleid: articleId.value,
      },
    });

    // 1-3. 가져오기
    store.dispatch('study/getArticleDetail', param.form);
    const article = computed(() => store.getters['study/articleGetter']);

    // 2. 새롭게 값을 입력 받은 것으로

    // parameter 값을 v-model로 위랑 연결해야 함.
    const parameter = reactive({
      boardid: boardId.value,
      articleid: articleId.value,
      studyArticleUpdateRequestDto: {
        content: article.value.content, //초기값 세팅
        title: article.value.title,
      },
    });

    // 3. 게시글을 수정한다.
    // parameter 값이 제대로 들어왔는지,넘어가는지 확인 필요
    const goReadDetailNotice = function () {
      store.dispatch('study/updateArticle', parameter);
      router.push({ path: '/subheader/notice/detail' });
    };
    const goBack = function () {
      router.push({ path: '/subheader/notice/detail' });
    };

    return {
      store,
      router,

      parameter,
      article,

      goBack,
      goReadDetailNotice,
    };
  },
};
</script>
<style>
#title {
  width: 98%;
  height: 100%;
  /* margin: 0 auto; */
  background-color: #f2f2f2;
  border: 0px solid black;
}
</style>
