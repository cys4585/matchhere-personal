import { createApp } from "vue"
import App from "./App.vue"
import router from "./router"
import store from "./store"
import "./assets/tailwind.css"

if (localStorage.getItem("signupFormData")) {
  store.commit(
    "auth/SET_SIGNUP_FORMDATA",
    JSON.parse(localStorage.getItem("signupFormData"))
  )
}
if (localStorage.getItem("signupStep")) {
  store.commit("auth/SET_SIGNUP_STEP", localStorage.getItem("signupStep"))
}

createApp(App).use(store).use(router).mount("#app")
