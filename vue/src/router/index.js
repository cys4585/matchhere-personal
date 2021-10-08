import { createRouter, createWebHistory } from 'vue-router';
import Header from '../views/Header.vue';

// sub header 시작
import SubHeader from '../components/SubHeader.vue';
import IntroduceStudy from '../components/MenuBar1/IntroduceStudy.vue';
import IntroduceProject from '../components/MenuBar1/IntroduceProject.vue';
import IntroduceClub from '../components/MenuBar1/IntroduceClub.vue';
import CreateNotice from '../components/MenuBar1/Notice/CreateNotice.vue';
import ReadNotice from '../components/MenuBar1/Notice/ReadNotice.vue';
import ReadDetailNotice from '../components/MenuBar1/Notice/ReadDetailNotice.vue';
import UpdateNotice from '../components/MenuBar1/Notice/UpdateNotice.vue';
import CreateBoard from '../components/MenuBar1/Board/CreateBoard.vue';
import ReadBoard from '../components/MenuBar1/Board/ReadBoard.vue';
import ReadDetailBoard from '../components/MenuBar1/Board/ReadDetailBoard.vue';
import UpdateBoard from '../components/MenuBar1/Board/UpdateBoard.vue';
import ManageStudy from '../components/MenuBar1/ManageStudy.vue';
import ApplicationStudy from '../components/MenuBar1/ApplicationStudy.vue';
// sub header 끝

// no sub header 시작
import NoSubHeader from '../components/NoSubHeader.vue';
import Main from '../components/Main.vue';
import StudyCreate from '../components/MenuBar2/StudyCreate.vue';
import StudyUpdate from '../components/MenuBar2/StudyUpdate.vue';
import ProjectCreate from '../components/MenuBar2/ProjectCreate.vue';
import ProjectUpdate from '../components/MenuBar2/ProjectUpdate.vue';
import ClubCreate from '../components/MenuBar2/ClubCreate.vue';
import ClubUpdate from '../components/MenuBar2/ClubUpdate.vue';
import HomeStudy from '../components/MenuBar2/HomeStudy.vue';
import HomeProject from '../components/MenuBar2/HomeProject.vue';
import HomeClub from '../components/MenuBar2/HomeClub.vue';

import InfiniteStudyMy from '../components/MenuBar2/InfiniteStudyMy.vue';
import InfiniteStudyRecommend from '../components/MenuBar2/InfiniteStudyRecommend.vue';
import InfiniteStudyTotal from '../components/MenuBar2/InfiniteStudyTotal.vue';

import GeneralSearch from '../components/Search/GeneralSearch.vue';
import AdvancedSearch from '../components/Search/AdvancedSearch.vue';
import Chat from '../components/Chat.vue';
import ReadMyPage from '../components/MyPage/ReadMyPage.vue';
import ReadInfoPage from '../components/MyPage/ReadInfoPage.vue';
import UpdateMyPage from '../components/MyPage/UpdateMyPage.vue';
import PasswordCheck from '../components/MyPage/PasswordCheck.vue';
// no sub header 끝

import NoHeader from '../views/NoHeader.vue';
import SignIn from '../components/Sign/SignIn.vue';
import SignUp from '../components/Sign/SignUp.vue';

import NotFound from '../views/NotFound.vue';
const routes = [
  // 1-1. Top Nav Bar가 있는 경우: Header
  {
    path: '/',
    component: Header,
    children: [
      {
        path: '',
        component: Main,
      },
      {
        path: '/subheader',
        component: SubHeader,
        // 2-1. sub header가 있는 경우: NoSubHeader
        children: [
          {
            path: '',
            component: Main,
          },
          {
            path: '/subheader/study/introduce',
            component: IntroduceStudy,
          },
          {
            path: '/subheader/club/introduce',
            component: IntroduceClub,
          },
          {
            path: '/subheader/project/introduce',
            component: IntroduceProject,
          },
          {
            path: '/subheader/notice',
            component: ReadNotice,
          },
          {
            path: '/subheader/notice/create',
            component: CreateNotice,
          },
          {
            path: '/subheader/notice/read',
            component: ReadNotice,
          },
          {
            path: '/subheader/notice/detail',
            component: ReadDetailNotice,
          },
          {
            path: '/subheader/notice/update',
            component: UpdateNotice,
          },
          {
            path: '/subheader/board',
            component: ReadBoard,
          },
          {
            path: '/subheader/board/detail',
            component: ReadDetailBoard,
          },
          {
            path: '/subheader/board/create',
            component: CreateBoard,
          },
          {
            path: '/subheader/board/read',
            component: ReadBoard,
          },
          {
            path: '/subheader/notice/detail',
            component: ReadDetailNotice,
          },
          {
            path: '/subheader/board/update',
            component: UpdateBoard,
          },
          {
            path: '/subheader/study/manage',
            component: ManageStudy,
          },
          {
            path: '/subheader/study/application',
            component: ApplicationStudy,
          },
        ],
      },
      {
        path: '/nosubheader',
        component: NoSubHeader,
        // 2-2. sub header가 없는 경우: NoSubHeader
        children: [
          {
            path: '',
            component: Main,
          },
          {
            path: '/nosubheader/main',
            component: Main,
          },
          {
            path: '/nosubheader/study/home',
            component: HomeStudy,
          },
          {
            path: '/nosubheader/project/home',
            component: HomeProject,
          },
          {
            path: '/nosubheader/club/home',
            component: HomeClub,
          },
          {
            path: '/nosubheader/study/create',
            component: StudyCreate,
          },
          {
            path: '/nosubheader/study/update',
            component: StudyUpdate,
          },
          {
            path: '/nosubheader/project/create',
            component: ProjectCreate,
          },
          {
            path: '/nosubheader/study/infinite/my',
            component: InfiniteStudyMy,
          },
          {
            path: '/nosubheader/study/infinite/recommend',
            component: InfiniteStudyRecommend,
          },
          {
            path: '/nosubheader/study/infinite/total',
            component: InfiniteStudyTotal,
          },
          {
            path: '/nosubheader/project/update',
            component: ProjectUpdate,
          },
          {
            path: '/nosubheader/club/create',
            component: ClubCreate,
          },
          {
            path: '/nosubheader/club/update',
            component: ClubUpdate,
          },
          {
            path: '/nosubheader/generalsearch',
            component: GeneralSearch,
          },
          {
            path: '/nosubheader/advancedsearch',
            component: AdvancedSearch,
          },
          {
            path: '/nosubheader/chat',
            component: Chat,
          },
          {
            path: '/nosubheader/readmypage',
            component: ReadMyPage,
          },
          {
            path: '/nosubheader/updatemypage',
            component: UpdateMyPage,
          },
          {
            path: '/nosubheader/passwordcheck',
            component: PasswordCheck,
          },
          {
            path: '/nosubheader/readinfopage',
            component: ReadInfoPage,
          },
        ],
      },
    ],
  },

  //1-2. Top Nav Bar가 없는 경우: NoHeader
  {
    path: '/noheader',
    component: NoHeader,
    children: [
      {
        path: '',
        component: SignIn,
      },
      {
        path: '/noheader/signin',
        component: SignIn,
      },
      {
        path: '/noheader/signup',
        component: SignUp,
      },
    ],
  },
  {
    path: '/:catchAll(.*)',
    component: NotFound,
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

// const router = createRouter({
//   history: createWebHistory(process.env.PATH),
//   // base: 'configure-admin', <-- this does not work in vue 3
//   routes: routes,
//   linkActiveClass: 'active',
// });

export default router;
