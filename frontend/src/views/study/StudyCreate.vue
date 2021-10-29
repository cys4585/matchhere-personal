<template>
  <section class="container py-10 grid gap-4">
    <header class="grid gap-4">
      <h2>스터디 만들기</h2>
      <p>매치히어에서 내 스터디를 만들고 나의 성공시대 시작됐다!</p>
    </header>
    <StudyForm />
  </section>
</template>

<script>
import StudyForm from "@/components/study/StudyForm.vue"

export default {
  name: "StudyCreate",
  components: { StudyForm },
  setup() {},
}
</script>

<style lang="scss" scoped></style>
