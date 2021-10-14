import { createRouter, createWebHistory } from "vue-router"
import store from "@/store"

import Home from "@/views/Home.vue"
import AuthLayout from "@/layouts/Auth.vue"
import ProfileLayout from "@/layouts/Profile.vue"
import ProjectLayout from "@/layouts/Project.vue"
import ProjectList from "@/views/ProjectList.vue"
import Profile from "@/views/Profile.vue"

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
      console.log(to)
      console.log(from)
      next()
    },
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
    component: ProjectLayout,
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
    component: ProfileLayout,
    children: {
      path: "",
      name: "ProjectList",
      component: ProjectList,
    },
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
})

export default router
