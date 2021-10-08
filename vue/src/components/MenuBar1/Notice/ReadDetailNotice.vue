<template>
  <el-row>
    <el-col :span="5"></el-col>
    <el-col :span="13" v-if="article">
      <el-row class="author-font"> 공지사항 </el-row>
      <el-row id="article-title"> {{ article.title }} </el-row>
      <el-row class="author-font"> {{ article.createdMember }} </el-row>
      <el-row>
        <el-col :span="10" class="gray-font left-align">
          {{ article.createdDate.substr(2, 8) }}&nbsp;
          {{ article.createdDate.substr(11, 8) }}
        </el-col>
        <el-col :span="10"></el-col>
        <el-col :span="2">
          <a @click="goUpdateNotice" class="grayLittle">수정</a>
        </el-col>
        <el-col :span="2"><ArticleDeleteModal /></el-col>
      </el-row>

      <el-row class="height10"> </el-row>
      <el-row class="gray-font">
        {{ article.content }}
      </el-row>

      <el-row class="height30"> </el-row>
      <hr />
      <el-row>
        <el-col :span="21"></el-col>
        <el-col :span="2">
          <el-button class="btn-ghost" @click="goReadNotice">목록</el-button>
        </el-col>
        <el-col :span="1"></el-col>
      </el-row>

      <!-- 여기부터 댓글 -->
      <!-- <el-row>
        <el-col :span="2">댓글</el-col>
        <el-col :span="22"></el-col>
      </el-row>
      <el-row>
        <el-col :span="24"><textarea>등록버튼</textarea></el-col>
      </el-row>
      <el-row>
        <el-col :span="1"></el-col>
        <el-col :span="22"></el-col>
        <el-row>
          <el-col :span="1">사진</el-col>
          <el-col :span="21">닉네임<br />3시간전</el-col
          ><el-col :span="1"><button>수정</button></el-col
          ><el-col :span="1"><button>삭제</button></el-col>
        </el-row>
        <el-row>
          <el-col :span="2"><button>답글쓰기</button></el-col>
          <el-col :span="22"></el-col>
        </el-row>

        <el-col :span="1"></el-col>
      </el-row>
      <el-row>
        <el-col :span="24"><textarea>등록버튼</textarea></el-col>
      </el-row> -->
      <!-- 댓글 끝 -->
    </el-col>
    <el-col :span="6"></el-col>
  </el-row>
</template>
<script>
import ArticleDeleteModal from '../../Modal/ArticleDeleteModal.vue';

import { reactive, computed, watch } from 'vue';
import { useStore } from 'vuex';
import { useRouter } from 'vue-router';

export default {
  components: { ArticleDeleteModal },
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

    // 필요없는 코드일 수 있음
    watch(articleId, () => {
      param.form.articleid = articleId.value;
      store.dispatch('study/getArticleDetail', param.form);
    });

    // 필요없는 코드일 수 있음
    watch(boardId, () => {
      param.form.boardid = boardId.value;
      store.dispatch('study/getArticleDetail', param.form);
    });

    store.dispatch('study/getArticleDetail', param.form);
    const article = computed(() => store.getters['study/articleGetter']);

    const goUpdateNotice = function () {
      router.push({ path: '/subheader/notice/update' });
    };
    const goReadNotice = function () {
      router.push({ path: '/subheader/notice/read' });
      // 새로고침을 위해
      // window.location = '/subheader/notice/read';
    };

    return {
      article,
      boardId,
      articleId,
      goUpdateNotice,
      goReadNotice,
    };
  },
};
</script>
<style scoped>
.grayLittle {
  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: 500;
  font-size: 16px;
  line-height: 16px;
  text-align: center;
  color: #999999;
}
#article-title {
  font-family: Inter;
  font-style: normal;
  font-weight: bold;
  font-size: 45px;
}
.author-font {
  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: bold;
  font-size: 18px;
  line-height: 22px;
}
.gray-font {
  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: normal;
  font-size: 16px;
  line-height: 19px;
  color: #718096;
}
.left-align {
  text-align: left;
}
</style>
