<template>
  <div class="bg-indigo-50">
    <div class="image-wrapper">
      <img src="https://picsum.photos/1200/300" alt="" />
    </div>
    <div class="container pb-10">
      <article class="py-6 mb-10">
        <section class="mb-10 bg-white p-4 rounded-xl shadow-md">
          <h2 class="mb-4">{{ study.name }}</h2>
          <div class="tags flex gap-2 mb-2">
            <Tag text="스터디 준비 중" type="green" />
            <Tag text="팀원 모집 중" type="green" />
          </div>
          <div class="flex justify-between text-sm mb-2">
            <span class="text-gray-600">{{ study.modifiedDate }}</span>
            <span class="text-gray-600">조회수 {{ study.viewCount }}회</span>
          </div>
          <Member :user="study.host" />
        </section>
        <div class="grid gap-4 md:grid-cols-2">
          <section class="rounded-xl p-6 bg-white">
            <h3 class="mb-4">스터디 주제</h3>
            <div class="tags flex gap-2">
              <Tag text="알고리즘" type="red" />
              <Tag text="Python" type="red" />
              <Tag text="Java" type="red" />
            </div>
          </section>
          <section class="rounded-xl p-6 bg-white">
            <h3 class="mb-4">일정</h3>
            <p>{{ study.schedule }}</p>
          </section>
          <section class="rounded-xl p-6 bg-white">
            <div class="flex items-center justify-between mb-4">
              <h3>스터디원</h3>
              <p class="text-sm font-bold">
                {{ study.maxCount }}명 중 {{ study.memberCount }}명
              </p>
            </div>
            <div class="members flex gap-4 flex-wrap">
              <Member
                v-for="user in study.members"
                :key="user.id"
                :user="user"
              />
            </div>
          </section>
          <section class="rounded-xl p-6 bg-white">
            <h3 class="mb-4">지역</h3>
            <p>{{ study.city }}</p>
          </section>
          <section class="rounded-xl p-6 bg-white">
            <h3 class="mb-4">소개</h3>
            <p>{{ study.bio }}</p>
          </section>
        </div>
      </article>
      <button class="join-btn" @click="modalOpen = true">
        스터디 참가 신청
      </button>
    </div>
  </div>
  <Modal v-if="modalOpen" @closeModal="modalOpen = false">
    <header class="flex items-center justify-between pb-4 border-b mb-6">
      <h4>지원하기</h4>
      <button class="flex material-icons" @click="modalOpen = false">
        close
      </button>
    </header>
    <div>
      <div class="form-field mb-6">
        <label for="bio">자기소개</label>
        <textarea name="bio" id="bio" rows="6"></textarea>
      </div>
      <div class="buttons flex gap-4 w-full justify-center">
        <button
          class="rounded py-2 px-6 text-sm font-bold bg-blue-500 text-white"
          @click="handleSubmit"
        >
          확인
        </button>
      </div>
    </div>
  </Modal>
</template>

<script>
import { onMounted, reactive, ref } from "vue"
import moment from "moment"
import "moment/locale/ko"
import Tag from "@/components/common/Tag.vue"
import Member from "@/components/common/Member.vue"
import Modal from "@/components/common/Modal.vue"

const DUMMY = {
  bio: "알고리즘 스터디입니다.",
  city: "서울",
  club: {
    id: 0,
    name: "string",
  },
  cover_pic: "커버사진 uri",
  host: {
    coverPicUri: "https://picsum.photos/100/100",
    email: "kepy1106@gmail.com",
    id: 0,
    name: "김병훈",
    nickname: "향긋하다",
  },
  id: 4,
  isParticipate: false,
  isPublic: false,
  maxCount: 3,
  memberCount: 3,
  members: [
    {
      id: 4,
      name: "박범진",
      nickname: "BJP",
      email: "qjawls@naver.com",
      coverPicUri: "https://picsum.photos/100/100",
    },
    {
      id: 5,
      name: "문일민",
      nickname: "IMM",
      email: "IMM@naver.com",
      coverPicUri: "https://picsum.photos/100/100",
    },
  ],
  modifiedDate: "2021-09-06 06:57:37.667537",
  name: "알고리즘 스터디",
  period: 7,
  schedule: "매주 화, 수 6시",
  status: "모집, 진행, 종료 중 하나",
  techList: ["java", "python"],
  viewCount: 32321,
}

export default {
  components: { Tag, Member, Modal },
  name: "StudyArticle",
  setup() {
    const study = reactive(DUMMY)
    const modalOpen = ref(false)

    onMounted(() => {
      study.modifiedDate = moment().format("YYYY. MM. DD")
    })

    return {
      study,
      modalOpen,
    }
  },
}
</script>

<style lang="scss" scoped>
.image-wrapper {
  @apply relative pt-40;

  img {
    @apply absolute inset-0 w-full h-full object-cover;
  }
}

.join-btn {
  @apply w-full sm:w-96 sm:mx-auto py-4 flex items-center justify-center rounded-full bg-blue-500 text-white font-bold;
}
</style>
