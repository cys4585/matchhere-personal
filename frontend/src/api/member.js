import http from "@/api/http"

const getMypage = async (email = "") => {
  const res = await http.get(`member/mypage/${email}`)
  return res.data
}

export default {
  getMypage,
}
