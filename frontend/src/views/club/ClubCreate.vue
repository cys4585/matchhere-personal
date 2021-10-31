<template>
  <section class="container max-w-3xl py-10 grid gap-4">
    <header class="grid gap-4">
      <h2>클럽 만들기</h2>
      <p>매치히어에서 내 클럽을 만들고 나의 성공시대 시작됐다!</p>
    </header>
    <ClubForm @onSubmit="handleSubmit" />
  </section>
</template>

<script>
import ClubForm from "@/components/club/ClubForm.vue"
import { useStore } from "vuex"
import { useRouter } from "vue-router"

export default {
  name: "StudyCreate",
  components: { ClubForm },
  setup() {
    const store = useStore()
    const router = useRouter()
    const handleSubmit = async (data) => {
      data.isPublic = true
      data.topic = "매칭 클럽"
      data.city = "서울"
      delete data.publicScope
      delete data.topics
      delete data.recruitmentState
      console.log(data)
      const res = await store.dispatch("club/createClub", data)
      console.log(res)
      if (res.type === "success") {
        router.push({ name: "StudyList" })
      }
    }
    return {
      handleSubmit,
    }
  },
}
</script>

<style lang="scss" scoped></style>
