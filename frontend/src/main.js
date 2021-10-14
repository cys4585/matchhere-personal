import { createApp } from "vue"
import App from "./App.vue"
import router from "./router"
import store from "./store"
import "./assets/tailwind.css"

if (localStorage.getItem("registerFormData")) {
  store.commit(
    "auth/SET_SIGNUP_FORMDATA",
    JSON.parse(localStorage.getItem("registerFormData"))
  )
}

createApp(App).use(store).use(router).mount("#app")
