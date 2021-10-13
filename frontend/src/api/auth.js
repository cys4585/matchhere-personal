import http from "@/api/http.js"
import store from "@/store"

const checkEmail = async (email) => {
  await http.get(`/auth/check/email/${email}`)
}

const authEmail = async (authCode) => {
  const email = store.getters["auth/getEmail"]
  await http.post(`/auth/signup/authcode`, {
    authCode,
    email,
  })
}

const checkNickname = async (nickname) => {
  await http.get(`/auth/check/nickname/${nickname}`)
}

export default {
  checkEmail,
  authEmail,
  checkNickname,
}
