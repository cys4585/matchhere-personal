<template>
  <div class="container py-10 px-4">
    <header class="page-header">
      <div class="flex items-center justify-between">
        <h1>스터디</h1>
        <router-link class="text-sm" :to="{ name: 'StudyCreate' }">
          스터디 만들기
        </router-link>
      </div>
      <p>여러분이 원하는 스터디를 찾아보세요!</p>
    </header>
    <section class="grid gap-2 mb-10">
      <h2>맞춤형 추천 스터디</h2>
      <HSlider>
        <StudyArticleCard :small="true" />
        <StudyArticleCard :small="true" />
        <StudyArticleCard :small="true" />
        <StudyArticleCard :small="true" />
      </HSlider>
    </section>
    <section>
      <h2 class="mb-6">스터디 리스트</h2>
      <div class="study-card-list-wrapper-v">
        <div class="study-card-list-v">
          <StudyArticleCard />
          <StudyArticleCard />
          <StudyArticleCard />
          <StudyArticleCard />
          <StudyArticleCard />
          <StudyArticleCard />
          <StudyArticleCard />
          <StudyArticleCard />
        </div>
      </div>
    </section>
  </div>
</template>

<script>
import StudyArticleCard from "@/components/study/StudyArticleCard.vue"
import HSlider from "@/components/common/HSlider.vue"

export default {
  name: "StudyList",
  components: { StudyArticleCard, HSlider },
  setup() {
    return {}
  },
}
</script>

<style lang="scss" scoped>
.study-card-list-v {
  @apply grid sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-4;
}
</style>
