<template>
  <section class="container max-w-3xl py-10 grid gap-4">
    <header class="grid gap-4">
      <h2>스터디 수정</h2>
      <p>매치히어에서 내 스터디를 만들고 나의 성공시대 시작됐다!</p>
    </header>
    <StudyForm
      v-if="study"
      @onSubmit="handleSubmit"
      :study="study"
      mode="edit"
    />
  </section>
</template>

<script>
import StudyForm from "@/components/study/StudyForm.vue"
import { useStore } from "vuex"
import { useRouter } from "vue-router"
import { onMounted, ref } from "vue"

export default {
  name: "StudyEdit",
  components: { StudyForm },
  props: {
    studyId: [Number, String],
  },
  setup(props) {
    const store = useStore()
    const router = useRouter()
    const study = ref(null)
    const handleSubmit = async (data) => {
      await store.dispatch("study/editStudy", { data, studyId: props.studyId })
      router.push({ name: "StudyManage" })
    }

    onMounted(async () => {
      const studyData = await store.dispatch("study/getStudy", props.studyId)
      study.value = { ...studyData }
    })

    return {
      study,
      handleSubmit,
    }
  },
}
</script>

<style lang="scss" scoped></style>
