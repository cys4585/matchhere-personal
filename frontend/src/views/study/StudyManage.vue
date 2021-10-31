<template>
  <div class="container py-10 grid gap-6">
    <h1 v-if="loading">로딩 중</h1>
    <template v-else>
      <section class="grid gap-6">
        <h2>구성원 관리</h2>
        <div class="grid gap-2 pb-6 border-b border-gray-200">
          <p class="text-xl font-medium">초대</p>
          <div
            class="
              flex
              items-center
              justify-center
              font-medium
              py-4
              bg-blue-50
              rounded
            "
          >
            링크를 공유하고, 팀원을 초대하세요!
          </div>
          <button class="rounded bg-blue-500 font-bold text-white py-2">
            초대 링크 복사하기
          </button>
        </div>
        <!-- 구성원 -->
        <div class="grid gap-4 pb-6 border-b border-gray-200">
          <div class="flex items-center justify-between">
            <span class="text-xl font-medium">구성원</span>
            <span class="font-bold">TODO:5명</span>
          </div>
          <div class="member-list grid gap-2">
            <div
              v-for="i in 2"
              :key="i"
              class="member-list-item flex items-center justify-between py-2"
            >
              <Member
                :user="{
                  email: 'kepy1106@gmail.com',
                  name: '김병훈',
                }"
                :large="true"
              />
              <select>
                <option value="소유자">소유자</option>
                <option value="관리자">관리자</option>
                <option value="팀원">팀원</option>
              </select>
            </div>
          </div>
        </div>
        <!-- 지원자 -->
        <div class="grid gap-4 pb-6 border-b border-gray-200">
          <div class="flex items-center justify-between">
            <span class="text-xl font-medium">지원자</span>
            <span class="font-bold">TODO:4명</span>
          </div>
          <div class="member-list grid gap-2">
            <div
              v-for="i in 2"
              :key="i"
              class="member-list-item flex items-center justify-between py-2"
            >
              <Member
                :user="{
                  email: 'kepy1106@gmail.com',
                  name: '김병훈',
                }"
                :large="true"
              />
            </div>
          </div>
        </div>
      </section>
      <section class="grid gap-6">
        <header class="flex items-center justify-between">
          <h2>스터디 관리</h2>
          <router-link
            :to="{ name: 'StudyEdit', parmas: { studyId } }"
            class="font-medium text-gray-400"
          >
            수정
          </router-link>
        </header>
        <StudyArticleCard :study="managedArticle" :noImage="true" />
      </section>
    </template>
  </div>
</template>

<script>
import Member from "@/components/common/Member.vue"
import StudyArticleCard from "@/components/study/StudyArticleCard.vue"
import { onMounted, ref } from "vue"
import { useStore } from "vuex"

export default {
  name: "StudyManage",
  components: { Member, StudyArticleCard },
  props: {
    studyId: [Number, String],
  },
  setup(props) {
    const store = useStore()
    const loading = ref(true)
    const managedArticle = ref(null)

    onMounted(async () => {
      const managedArticleData = await store.dispatch(
        "study/getManagedStudyArticle",
        props.studyId
      )
      managedArticle.value = { ...managedArticleData }
      loading.value = false
    })
    return { loading, managedArticle }
  },
}
</script>

<style lang="scss" scoped></style>
