import http from "@/api/http.js"

const createClub = async (data) => {
  try {
    await http.post("/club", data)
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
      case 500: {
        return {
          text: "생성 실패. 서버에 문제가 있어요 😢",
          type: "error",
        }
      }
    }
  }
}
export default { createClub }
