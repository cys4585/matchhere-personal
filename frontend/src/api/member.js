import http from "@/api/http"

const getMypage = async (email = "") => {
  const res = await http.get(`member/mypage/${email}`)
  return res.data
}

const getBasicInfo = async () => {
  const res = await http.get(`member/basicinfo`)
  return res.data
}

const updateBasicInfo = async (submitData) => {
  await http.put(`member/basicinfo`, submitData)
}

export default {
  getMypage,
  getBasicInfo,
  updateBasicInfo,
}
