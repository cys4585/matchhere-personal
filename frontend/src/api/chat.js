import http from "@/api/http.js"

const getChatList = async () => {
  const res = await http.get(`/messages/chatroom`)
  return res.data
}

const getChatRoomInfo = async (email) => {
  const res = await http.get(`/messages/member/${email}`)
  return res.data
}

const getChatHistory = async (targetUserId) => {
  const res = await http.get(`/messages/chatroom/${targetUserId}`)
  return res.data
}

export default {
  getChatList,
  getChatRoomInfo,
  getChatHistory,
}
