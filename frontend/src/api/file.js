import http from "@/api/http.js"

const uploadFile = async (file) => {
  const res = await http.post(`/file/uploadFile`, file)
  return res.data.id
}

export default {
  uploadFile,
}
