import { createRouter, createWebHistory } from "vue-router"
import Home from "@/views/Home.vue"
import AuthLayout from "@/layouts/Auth.vue"
import ProfileLayout from "@/layouts/Profile.vue"
import ProjectLayout from "@/layouts/Project.vue"
import Login from "@/views/Login.vue"
import Profile from "@/views/Profile.vue"
import ProjectForm from "@/views/ProjectForm.vue"
import ProjectList from "@/views/ProjectList.vue"
import Project from "@/views/Project.vue"

const routes = [
  {
    path: "/",
    component: Home,
  },
  {
    path: "/auth",
    component: AuthLayout,
    children: [
      {
        path: "login",
        name: "Login",
        component: Login,
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
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
})

export default router
