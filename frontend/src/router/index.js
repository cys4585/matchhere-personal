import { createRouter, createWebHistory } from "vue-router"
import store from "@/store"

import Home from "@/views/Home.vue"
import AuthLayout from "@/layouts/Auth.vue"
import ProfileLayout from "@/layouts/Profile.vue"
import ProjectLayout from "@/layouts/Project.vue"

import Profile from "@/views/Profile.vue"
import ProjectList from "@/views/ProjectList.vue"
import ProjectForm from "@/views/ProjectForm.vue"
import Project from "@/views/Project.vue"

import Login from "@/views/auth/Login.vue"
import Signup from "@/views/auth/Signup.vue"
import CheckEmail from "@/views/auth/CheckEmail.vue"
import AuthEmail from "@/views/auth/AuthEmail.vue"

const routes = [
  {
    path: "/",
    name: "Home",
    component: Home,
  },
  {
    path: "/auth",
    component: AuthLayout,
    beforeEnter: (to, from, next) => {
      if (to.name === "Login") {
        next()
        return
      }
      if (!from.name) {
        const signupStep = store.getters["auth/getSignupStep"]
        if (to.name !== signupStep) {
          next({ name: signupStep })
          return
        }
      }
      next()
    },
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
        path: "form",
        name: "ProjectForm",
        component: ProjectForm,
      },
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
    console.log(store.getters["auth/getIsAuthenticated"])
    console.log(store.state.auth.token.accessToken)
    if (store.getters["auth/getIsAuthenticated"]) {
      alert("여기는 오면 안돼")
      next({ name: "Home" })
      return
    }
  }
  next()
})

export default router
