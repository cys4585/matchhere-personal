import { createRouter, createWebHistory } from "vue-router"
import store from "@/store"

import Home from "@/views/Home.vue"
import AuthLayout from "@/layouts/Auth.vue"
import ProfileLayout from "@/layouts/Profile.vue"
import ProjectLayout from "@/layouts/Project.vue"

import Profile from "@/views/Profile.vue"
import EditProfile from "@/views/EditProfile.vue"

import ProjectList from "@/views/project/ProjectList.vue"
import ProjectForm from "@/views/project/ProjectForm.vue"
import ProjectArticle from "@/views/project/ProjectArticle.vue"
import ProjectDetail from "@/views/project/ProjectDetail.vue"
import ProjectManage from "@/views/project/detail/manage/ProjectManage.vue"
import ProjectBoard from "@/views/project/detail/board/ProjectBoard.vue"
import ProjectNotiBoard from "@/views/project/detail/notiboard/ProjectNotiBoard.vue"
import ArticleForm from "@/views/project/detail/articleform/ArticleForm.vue"
import ArticleEditForm from "@/views/project/detail/articleform/ArticleEditForm.vue"
import BoardArticleList from "@/views/project/detail/board/BoardArticleList.vue"
import ArticleDetail from "@/views/project/detail/ArticleDetail.vue"
import NotiBoardArticleList from "@/views/project/detail/notiboard/NotiBoardArticleList.vue"

import Login from "@/views/auth/Login.vue"
import Signup from "@/views/auth/Signup.vue"
import FindPassword from "@/views/auth/FindPassword.vue"

const routes = [
  {
    path: "/",
    name: "Home",
    component: Home,
  },
  {
    path: "/auth",
    component: AuthLayout,
    meta: { requiresNoAuth: true },
    children: [
      {
        path: "login",
        name: "Login",
        component: Login,
      },
      {
        path: "signup",
        name: "Signup",
        component: Signup,
      },
      {
        path: "find-password",
        name: "FindPassword",
        component: FindPassword,
      },
    ],
  },
  {
    path: "/profile",
    component: ProfileLayout,
    children: [
      {
        path: ":email",
        name: "Profile",
        component: Profile,
        props: true,
      },
      {
        path: "edit",
        name: "EditProfile",
        component: EditProfile,
      },
    ],
  },
  {
    path: "/projects",
    component: ProjectLayout,
    children: [
      {
        path: "",
        name: "ProjectList",
        component: ProjectList,
      },
      {
        path: "article/form",
        name: "ProjectCreateForm",
        component: ProjectForm,
      },
      {
        path: "article/:projectId",
        name: "ProjectArticle",
        component: ProjectArticle,
      },
      {
        path: "article/:projectId/form",
        name: "ProjectEditForm",
        component: ProjectForm,
      },
      {
        path: "detail/:projectId",
        name: "ProjectDetail",
        component: ProjectDetail,
        children: [
          {
            path: "manage",
            name: "ProjectManage",
            component: ProjectManage,
          },
          {
            path: "board/:boardId",
            component: ProjectBoard,
            children: [
              {
                path: "",
                name: "BoardArticleList",
                component: BoardArticleList,
              },
              {
                path: "detail/:articleId",
                name: "ArticleDetail",
                component: ArticleDetail,
              },
            ],
          },
          {
            path: "noti-board/:boardId",
            component: ProjectNotiBoard,
            children: [
              {
                path: "",
                name: "NotiBoardArticleList",
                component: NotiBoardArticleList,
              },
              {
                path: "detail/:articleId",
                name: "ArticleDetail",
                component: ArticleDetail,
              },
            ],
          },
          {
            path: "article/form",
            name: "ArticleForm",
            component: ArticleForm,
          },
          {
            path: "article/:articleId/form",
            name: "ArticleEditForm",
            component: ArticleEditForm,
          },
        ],
      },
    ],
  },
  {
    path: "/:notFound(.*)",
    name: "NotFound",
    redirect: () => {
      return { name: "Home" }
    },
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
})

router.beforeEach((to, from, next) => {
  // 로그인 상태에서 접근할 수 없는 페이지
  if (to?.meta?.requiresNoAuth) {
    if (store.getters["auth/getIsAuthenticated"]) {
      alert("여기는 오면 안돼")
      next({ name: "Home" })
      return
    }
  }
  next()
})

export default router
