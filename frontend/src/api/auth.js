import http from "@/api/http.js"
import store from "@/store"

const checkEmail = async (email) => {
  await http.get(`/auth/cert/email/${email}`)
}

const authEmail = async (authCode) => {
  const email = store.getters["auth/getEmail"]
  await http.post(`/auth/cert/authcode`, {
    authCode,
    email,
  })
}

const checkNickname = async (nickname) => {
  await http.get(`/auth/check/nickname/${nickname}`)
}

const signup = async (formData) => {
  await http.post(`/auth/signup`, formData)
}

const login = async (formData) => {
  const res = await http.post(`/auth/login`, formData)
  return res.data
}

const reissue = async (tokenData) => {
  const res = await http.post(`/auth/reissue`, tokenData)
  return res.data
}

export default {
  checkEmail,
  authEmail,
  checkNickname,
  signup,
  login,
  reissue,
}
