import http from "@/api/http.js"
// import store from "@/store"

const getMyClubList = async () => {
  const res = await http.get(`/project/myclublist`)
  return res.data
}

const createProject = async (formData) => {
  const res = await http.post(`/project`, formData)
  return res.data
}

const getProject = async (projectId) => {
  const res = await http.get(`/project/${projectId}`)
  return res.data
}

export default {
  getMyClubList,
  createProject,
  getProject,
}
