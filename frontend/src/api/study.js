import http from "./http"

const createStudy = async (data) => {
  try {
    await http.post("/study", data)
    return {
      text: "스터디를 생성했습니다",
      type: "success",
    }
  } catch (error) {
    console.dir(error)
    switch (error.response?.status) {
      case 400: {
        return {
          text: error.response?.data?.message,
          type: "error",
        }
      }
      case 404: {
        return {
          text: error.response?.data?.message,
          type: "error",
        }
      }
    }
  }
}

const editStudy = async ({ data, studyId }) => {
  await http.put(`/study/${studyId}`, data)
}

const getStudyList = async () => {
  try {
    const res = await http.get("/study")
    return Promise.resolve({
      data: res.data,
      type: "success",
    })
  } catch (error) {
    return Promise.reject({
      text:
        error.response?.data?.message || "스터디 목록을 불러오지 못했습니다",
      type: "error",
    })
  }
}

const getStudy = async (studyId) => {
  try {
    const res = await http.get(`study/${studyId}`)
    return Promise.resolve({
      data: res.data,
      type: "success",
    })
  } catch (error) {
    return Promise.reject({
      text: error.response?.data?.message || "스터디를 불러오지 못했습니다",
      type: "error",
    })
  }
}

const updateViewCount = async (studyId) => {
  try {
    await http.put(`study/view-count/${studyId}`)
  } catch (error) {
    console.log(error)
  }
}

const submitApplication = async ({ bio, studyId }) => {
  try {
    await http.post(`studyapplication/${studyId}`, { bio })
  } catch (error) {
    console.log(error)
  }
}

const getStudyBoards = async (studyId) => {
  try {
    const res = await http.get(`study/${studyId}/boards`)
    return res.data
  } catch (error) {
    console.log(error)
  }
}

export default {
  createStudy,
  editStudy,
  getStudyList,
  getStudy,
  updateViewCount,
  submitApplication,
  getStudyBoards,
}
