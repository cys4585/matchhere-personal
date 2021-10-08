<template>
  <a @click="changemodalOpen" class="grayLittle">삭제</a>
  <teleport to="body">
    <div v-if="!modalOpen" class="modal">
      <div class="height40" style="width: 30%">
        <el-row class="height10">
          <el-col :span="24" class="delete-target">
            [{{ article.title }}] 게시글을
          </el-col>
        </el-row>
        <el-row class="height20">
          <el-col :span="24" class="delete-msg">
            정말로 삭제하시겠습니까?
          </el-col>
        </el-row>
        <el-row class="height10">
          <el-col :span="6" :offset="6">
            <el-button
              class="btn-ghost-red font-noto-bold"
              @click="goNoticeRead"
              style="font-size: 14px"
            >
              삭제
            </el-button>
          </el-col>
          <el-col :span="6">
            <el-button
              class="btn-ghost-blue font-noto-bold"
              @click="changemodalOpen"
              style="font-size: 14px"
              >취소</el-button
            >
          </el-col>
          <el-col :span="6"></el-col>
        </el-row>
      </div>
    </div>
  </teleport>
</template>
<script>
import { reactive, computed, watch } from 'vue';
import { useStore } from 'vuex';
import { useRouter } from 'vue-router';

export default {
  data() {
    return {
      modalOpen: false,
    };
  },
  setup() {
    const store = useStore();
    const router = useRouter();
    // board id와 article 번호를 가져와야 함.
    const boardId = computed(
      () => store.getters['study/studyNoticeBoardIdGetter']
    );
    const articleId = computed(
      () => store.getters['study/studyArticleIdGetter']
    );

    // 게시글 내용을 가져와야 함
    // api요청에 보낼 파라미터
    const param = reactive({
      form: {
        boardid: boardId.value,
        articleid: articleId.value,
      },
    });

    watch(articleId, () => {
      param.form.articleid = articleId.value;
      store.dispatch('study/getArticleDetail', param.form);
    });

    watch(boardId, () => {
      param.form.boardid = boardId.value;
      store.dispatch('study/getArticleDetail', param.form);
    });

    // 2. 모달창에 게시글 정보를 일부 띄워야하기 때문에 게시글 정보를 받음
    store.dispatch('study/getArticleDetail', param.form);
    const article = computed(() => store.getters['study/articleGetter']);

    // 모달 처리
    const modalOpen = computed(() => store.getters['scrollGetter']);
    const changemodalOpen = function () {
      store.dispatch('changeScrollModal', !modalOpen.value);
    };

    // 삭제 누를 시
    const goNoticeRead = function () {
      store.dispatch('study/deleteArticle', param.form);
      store.dispatch('changeScrollModal', !modalOpen.value);
      window.location = '/subheader/notice/read';
      // router.push({ path: '/subheader/notice/read' });
    };

    return {
      article,
      modalOpen,
      changemodalOpen,
      goNoticeRead,
    };
  },
};
</script>
<style scoped>
.modal {
  position: absolute;
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

.modal div {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  background-color: white;
  width: 100%;
}

.grayLittle {
  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: 500;
  font-size: 16px;
  line-height: 16px;
  text-align: center;
  color: #999999;
}
.delete-target {
  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: bold;
  font-size: 20px;
}
.delete-msg {
  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: normal;
  font-size: 20px;
}
</style>
