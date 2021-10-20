import { createRouter, createWebHistory } from "vue-router"
import store from "@/store"

import Home from "@/views/Home.vue"
import AuthLayout from "@/layouts/Auth.vue"
import ProfileLayout from "@/layouts/Profile.vue"
import ProjectLayout from "@/layouts/Project.vue"

import Profile from "@/views/Profile.vue"

import ProjectList from "@/views/project/ProjectList.vue"
import ProjectForm from "@/views/project/ProjectForm.vue"
import ProjectArticle from "@/views/project/ProjectArticle.vue"
import ProjectDetail from "@/views/project/ProjectDetail.vue"
import ProjectManage from "@/views/project/ProjectManage.vue"
import ProjectBoard from "@/views/project/ProjectBoard.vue"
import ProjectNotiBoard from "@/views/project/ProjectNotiBoard.vue"

import Login from "@/views/auth/Login.vue"
import Signup from "@/views/auth/Signup.vue"
import CheckEmail from "@/views/auth/CheckEmail.vue"
import AuthEmail from "@/views/auth/AuthEmail.vue"
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
    // beforeEnter: (to, from, next) => {
    //   if (to.name === "Login" || to.name === "FindPassword") {
    //     next()
    //     return
    //   }
    //   if (!from.name) {
    //     const signupStep = store.getters["auth/getSignupStep"]
    //     if (to.name !== signupStep) {
    //       next({ name: signupStep })
    //       return
    //     }
    //   }
    //   next()
    // },
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
        path: "check-email",
        name: "CheckEmail",
        component: CheckEmail,
      },
      {
        path: "auth-email",
        name: "AuthEmail",
        component: AuthEmail,
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
        path: ":userId",
        name: "Profile",
        component: Profile,
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
            path: "board",
            name: "ProjectBoard",
            component: ProjectBoard,
          },
          {
            path: "noti-board",
            name: "ProjectNotiBoard",
            component: ProjectNotiBoard,
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
