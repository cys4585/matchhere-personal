import http from "@/api/http.js"
import store from "@/store"

const sendEmailForSignup = async (email) => {
  const res = await http.get(`/auth/cert/email/${email}`)
  return res.data
}

const confirmAuthCodeForSignup = async (authCode) => {
  const email = store.getters["auth/getEmail"]
  await http.post(`/auth/cert/authcode`, {
    authCode,
    email,
  })
}

const checkNickname = async (nickname) => {
  const res = await http.get(`/auth/check/nickname/${nickname}`)
  return res.data
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
  sendEmailForSignup,
  confirmAuthCodeForSignup,
  checkNickname,
  signup,
  login,
  reissue,
}
