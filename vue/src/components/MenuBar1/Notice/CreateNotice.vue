<template>
  <el-row class="height100"
    ><el-col :span="3"></el-col>
    <el-col :span="19">
      <el-row class="height5">
        <el-col :span="1" class="flex-items">제목:</el-col>
        <el-col :span="23" style="margin: auto 0">
          <input
            type="text"
            id="title"
            placeholder="제목을 입력하세요"
            v-model="state.form.title"
          />
        </el-col>
      </el-row>
      <el-row class="height5"> </el-row>
      <el-row style="height: 65%">
        <el-col :span="24">
          <!-- contentType="html" 도 가능함. 원하는 걸로 선택 -->
          <quill-editor
            v-model:content="state.form.content"
            contentType="text"
            theme="snow"
          ></quill-editor>
        </el-col>
      </el-row>
      <el-row class="height5"> </el-row>
      <el-row class="height5">
        <el-col :span="9"> </el-col>
        <el-col :span="2">
          <el-button
            class="btn-1747C9 font-noto-bold"
            @click="goReadDetailNotice"
          >
            생성
          </el-button>
        </el-col>
        <el-col :span="1"></el-col>
        <el-col :span="2">
          <el-button
            class="btn-ghost-red"
            style="font-size: 14px"
            @click="goReadNotice"
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

import { reactive, computed } from 'vue';
import { useStore } from 'vuex';
import { useRouter } from 'vue-router';

export default {
  components: {
    QuillEditor,
  },
  setup() {
    const store = useStore();
    const router = useRouter();

    // 1. notice board id를 가져와야 함.
    const boardId = computed(
      () => store.getters['study/studyNoticeBoardIdGetter']
    );

    // 값을 넘길 파라미터
    const state = reactive({
      form: {
        title: '', //게시글 제목
        content: '', //게시글 내용ㅊ
        boardId: boardId.value, //user.memberId
      },
    });

    // 글 작성
    const goReadDetailNotice = function () {
      store.dispatch('study/createArticle', state.form);

      router.push({ path: '/subheader/notice/detail' });
    };
    const goReadNotice = function () {
      router.push({ path: '/subheader/notice' });
    };

    return {
      state,
      goReadDetailNotice,
      goReadNotice,
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
