import http from "@/api/http.js"

const getChatList = async () => {
  const res = await http.get(`/messages/chatroom`)
  return res.data
}

export default {
  getChatList,
}
