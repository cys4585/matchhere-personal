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
import Project from "@/views/project/Project.vue"

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
        path: ":projectId",
        name: "Project",
        component: Project,
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
