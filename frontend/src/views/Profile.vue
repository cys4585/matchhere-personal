<template>
  <ProfileNav :email="email" />
  <main class="grid gap-6" v-if="!loading">
    <header class="page-header mb-0">
      <div class="flex items-center justify-between">
        <h2>프로필</h2>
        <router-link :to="{ name: 'EditProfile' }">
          <span class="material-icons">settings</span>
        </router-link>
      </div>
    </header>
    <section
      class="basic-info-section grid gap-4 pb-6 border-b border-gray-200"
    >
      <div class="basic-info flex gap-4">
        <img src="https://picsum.photos/80" class="profile-img rounded-full" />
        <div class="infos grid gap-2">
          <div class="names flex gap-1 font-medium">
            <p>{{ profileData.name }}</p>
            <p>({{ profileData.nickname }})</p>
          </div>
          <p class="email text-sm font-medium">{{ profileData.email }}</p>
          <p class="city text-sm">{{ profileData.city }}</p>
        </div>
      </div>
      <button
        class="
          message-button
          border border-gray-400
          rounded
          text-sm
          font-medium
          py-2
          px-6
        "
      >
        메세지
      </button>
      <div
        class="desc h-20 p-4 rounded border border-gray-400"
        v-if="profileData.bio"
      >
        {{ profileData.bio }}
      </div>
    </section>
    <section
      class="position_stack-section grid gap-4 pb-6 border-b border-gray-200"
    >
      <h3 class="text-xl font-bold">직무/기술스택</h3>
      <div class="infos grid gap-4">
        <div class="position_dposition flex gap-2">
          <p class="position font-bold">{{ profileData.position }}</p>
          <div class="dposition-list font-medium">
            (
            {{ dpositionList }}
            )
          </div>
        </div>
        <div class="stack-list grid gap-2">
          <TeckStackListItem
            v-for="tech in profileData.techList"
            :key="tech.name"
            :name="tech.name"
            :userLevel="tech.level"
          />
        </div>
      </div>
    </section>
    <section
      class="career-section grid gap-4 pb-6 border-b border-gray-200"
      v-if="profileData.careerList.length"
    >
      <h3 class="text-xl font-bold">경력</h3>
      <div class="career-list grid gap-4">
        <div
          class="career-card grid gap-2"
          v-for="career in profileData.careerList"
          :key="career.id"
        >
          <h4 class="text-lg font-medium">{{ career.department }}</h4>
          <p class="company text-sm text-gray-700">{{ career.company }}</p>
          <p class="date text-xs text-gray-700">
            {{ career.start_date }} -
            {{ career.is_incumbent ? "재직 중" : career.end_date }}
          </p>
          <p class="desc">{{ career.description }}</p>
        </div>
      </div>
    </section>
    <section
      class="certification-section grid gap-4 pb-6 border-b border-gray-200"
      v-if="profileData.certificationList.length"
    >
      <h3 class="text-xl font-bold">자격증</h3>
      <div class="certification-list grid gap-4">
        <div
          class="certification-card grid gap-2"
          v-for="certification in profileData.certificationList"
          :key="certification.id"
        >
          <h4 class="text-lg font-medium">{{ certification.name }}</h4>
          <p class="company text-sm text-gray-700">
            {{ certification.organization }}
          </p>
          <p class="date text-xs text-gray-700">
            {{ certification.issued_date }} -
            {{ certification.is_expire ? certification.expired_date : "" }}
          </p>
          <p class="desc">{{ certification.description }}</p>
        </div>
      </div>
    </section>
    <section
      class="education-section grid gap-4 pb-6 border-b border-gray-200"
      v-if="profileData.educationList.length"
    >
      <h3 class="text-xl font-bold">학력</h3>
      <div class="education-list grid gap-4">
        <div
          class="education-card grid gap-2"
          v-for="education in profileData.educationList"
          :key="education.id"
        >
          <h4 class="text-lg font-medium">{{ education.institution }}</h4>
          <p class="company text-sm text-gray-700">
            {{ education.major }}
          </p>
          <p class="date text-xs text-gray-700">
            {{ education.start_date }} -
            {{ education.end_date }}
          </p>
          <p class="desc">{{ education.description }}</p>
        </div>
      </div>
    </section>
    <section
      class="portfolio-section grid gap-4 pb-6 border-b border-gray-200"
      v-if="profileData.portfolio && profileData.portfolio_uri"
    >
      <h3 class="text-xl font-bold">포트폴리오</h3>
      <div class="portfolio-list grid gap-4">
        <p>{{ profileData.portfolio }}</p>
        <p>{{ profileData.portfolio_uri }}</p>
      </div>
    </section>
    <section
      class="sns-section grid gap-4 pb-6 border-b border-gray-200"
      v-if="profileData.snsList.length"
    >
      <h3 class="text-xl font-bold">SNS 링크</h3>
      <div class="sns-list grid gap-4">
        <div
          class="sns-list-item"
          v-for="sns in profileData.snsList"
          :key="sns.id"
        >
          <p>{{ sns.snsName }}</p>
          <p>{{ sns.snsAccount }}</p>
        </div>
      </div>
    </section>
  </main>
</template>

<script>
import { computed, onMounted, ref } from "vue"
import { useStore } from "vuex"
import { useRouter } from "vue-router"
import TeckStackListItem from "@/components/common/TeckStackListItem.vue"
import ProfileNav from "@/components/profile/ProfileNav.vue"
const INFO = {
  bio: "let me introduce",
  careerList: [
    {
      company: "string",
      department: "string",
      description: "string",
      end_date: "2021-10-17",
      id: 0,
      is_incumbent: true,
      role: "string",
      start_date: "2021-10-17",
    },
  ],
  certificationList: [
    {
      code: "string",
      expired_date: "2021-10-17",
      grade: "string",
      id: 0,
      is_expire: true,
      issued_date: "2021-10-17",
      name: "string",
      organization: "string",
    },
  ],
  city: "부산",
  cover_pic: "http://cdn.matchhere.me/path/coverpic.png",
  dpositionList: ["프론트엔드", "데브옵스"],
  educationList: [
    {
      id: 2,
      state: "졸업",
      description: "서울대학교가 낳은 최고의 인재",
      institution: "서울대학교",
      major: "컴퓨터과학과",
      end_date: "2020-12-31",
      start_date: "2020-12-31",
      degree: "학사",
    },
  ],
  email: "my_email@gmail.com",
  name: "문일민",
  nickname: "별명",
  portfolio: "http://cdn.matchhere.me/path/portfolio.pdf",
  portfolio_uri: "http://cdn.matchhere.me/path/myportfolio.pdf",
  position: "개발자",
  snsList: [
    {
      id: 1,
      snsName: "github",
      snsAccount: "gitid",
    },
    {
      id: 2,
      snsName: "twitter",
      snsAccount: "twitterid",
    },
  ],
  techList: {
    python: {
      name: "python",
      level: "상",
      img_uri: "http://cdn.matchhere.me/path/python.png",
    },
    java: {
      name: "java",
      level: "중",
      img_uri: "http://cdn.matchhere.me/path/java.png",
    },
  },
}
export default {
  components: { TeckStackListItem, ProfileNav },
  name: "Profile",
  props: {
    email: String,
  },
  setup(props) {
    const store = useStore()
    const router = useRouter()
    const loading = ref(true)
    const profileData = ref(INFO)
    const dpositionList = computed(() =>
      profileData.value.dpositionList.map((dp) => dp.name).join(", ")
    )

    onMounted(async () => {
      try {
        profileData.value = await store.dispatch(
          "member/getMypage",
          props.email
        )
        loading.value = false
      } catch (error) {
        router.push({ name: "Home" })
      }
    })

    return {
      loading,
      profileData,
      dpositionList,
    }
  },
}
</script>

<style lang="scss" scoped></style>
