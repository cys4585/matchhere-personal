import http from "@/api/http.js"
// import store from "@/store"

const getMyClubList = async () => {
  const res = await http.get(`/project/myclublist`)
  return res
}

const createProject = async (formData) => {
  const res = await http.post(`/project`, formData)
  return res
}

export default {
  getMyClubList,
  createProject,
}
