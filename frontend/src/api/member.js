import http from "@/api/http"

const getMypage = async (email = "") => {
  const res = await http.get(`member/mypage/${email}`)
  return res.data
}

const getMe = async () => {
  const res = await http.get(`member/me`)
  return res.data
}

const getBasicInfo = async () => {
  const res = await http.get(`member/basicinfo`)
  return res.data
}

const updateBasicInfo = async (data) => {
  await http.put(`member/basicinfo`, data)
}

const getSkills = async () => {
  const res = await http.get(`member/skills`)
  const dpositionList = res.data.dpositionList.map((dp) => dp.name)
  const techList = res.data.techList.reduce((acc, dp) => {
    return {
      ...acc,
      [dp.name]: dp.level,
    }
  }, {})
  return {
    position: res.data.position,
    dpositionList,
    techList,
  }
}

const updateSkills = async (data) => {
  await http.put(`member/skills`, data)
}

export default {
  getMypage,
  getMe,
  getBasicInfo,
  updateBasicInfo,
  getSkills,
  updateSkills,
}
