<template>
  <div class="container grid gap-10">
    <section class="grid gap-4">
      <header class="flex items-center justify-between">
        <h3>경력</h3>
        <button
          class="
            text-blue-600
            font-medium
            py-1
            px-2
            rounded
            transition-colors
            hover:text-white hover:bg-blue-600
          "
          @click="handleOpenModal('career')"
        >
          + 추가
        </button>
      </header>
      <div class="grid gap-4">
        <div class="flex">
          <div class="grid gap-2 flex-1">
            <p class="text-lg font-medium">부서</p>
            <p class="text-sm text-gray-700">회사</p>
            <p class="text-xs text-gray-700">시작 일 - 종료 일</p>
            <p class="truncate">
              설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명
            </p>
          </div>
          <div>
            <button>
              <span
                class="material-icons text-gray-300 hover:text-blue-500"
                style="font-size: 1rem"
              >
                edit
              </span>
            </button>
          </div>
        </div>
      </div>
    </section>
    <section class="grid gap-4">
      <header class="flex items-center justify-between">
        <h3>자격증</h3>
        <button
          class="
            text-blue-600
            font-medium
            py-1
            px-2
            rounded
            transition-colors
            hover:text-white hover:bg-blue-600
          "
        >
          + 추가
        </button>
      </header>
      <div class="grid gap-4">
        <div class="flex">
          <div class="grid gap-2 flex-1">
            <p class="text-lg font-medium">자격증 이름</p>
            <p class="text-sm text-gray-700">발급기관</p>
            <p class="text-xs text-gray-700">취득일 - 만료일</p>
          </div>
          <div>
            <button>
              <span
                class="material-icons text-gray-300 hover:text-blue-500"
                style="font-size: 1rem"
              >
                edit
              </span>
            </button>
          </div>
        </div>
      </div>
    </section>
    <section class="grid gap-4">
      <header class="flex items-center justify-between">
        <h3>학력</h3>
        <button
          class="
            text-blue-600
            font-medium
            py-1
            px-2
            rounded
            transition-colors
            hover:text-white hover:bg-blue-600
          "
        >
          + 추가
        </button>
      </header>
      <div class="grid gap-4">
        <div class="flex">
          <div class="grid gap-2 flex-1">
            <p class="text-lg font-medium">교육기관 이름</p>
            <p class="text-sm text-gray-700">전공</p>
            <p class="text-xs text-gray-700">
              시작 일 - 종료 일 | 상태(졸업, 재학, 휴학, 중퇴)
            </p>
            <p class="truncate">
              설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명설명
            </p>
          </div>
          <div>
            <button>
              <span
                class="material-icons text-gray-300 hover:text-blue-500"
                style="font-size: 1rem"
              >
                edit
              </span>
            </button>
          </div>
        </div>
      </div>
    </section>
  </div>
  <AddCareerModal v-if="careerModalOpen" @closeModal="handleOpenModal" />
  <!-- <AddCareerModal /> -->
  <!-- <AddCareerModal /> -->
</template>

<script>
import AddCareerModal from "@/components/profile/AddCareerModal.vue"
// import AddCareerModal from "@/components/profile/AddCareerModal.vue"
// import AddCareerModal from "@/components/profile/AddCareerModal.vue"
import { onMounted, ref } from "vue"
import { useStore } from "vuex"
export default {
  name: "Career",
  components: { AddCareerModal },
  setup() {
    const store = useStore()
    const careerList = ref([])
    const certificationList = ref([])
    const educationList = ref([])
    const careerModalOpen = ref(false)
    const certModalOpen = ref(false)
    const eduModalOpen = ref(false)

    const handleOpenModal = (type) => {
      switch (type) {
        case "career": {
          careerModalOpen.value = !careerModalOpen.value
          store.commit("SET_MODAL_OPEN", careerModalOpen.value)
          break
        }
        case "education": {
          eduModalOpen.value = !eduModalOpen.value
          store.commit("SET_MODAL_OPEN", eduModalOpen.value)
          break
        }
        case "certification": {
          certModalOpen.value = !certModalOpen.value
          store.commit("SET_MODAL_OPEN", certModalOpen.value)
          break
        }
      }
    }

    onMounted(async () => {
      const careerAll = await store.dispatch("member/getCareerAll")
      careerList.value = careerAll.careerList
      certificationList.value = careerAll.certificationList
      educationList.value = careerAll.educationList
    })

    return {
      careerList,
      certificationList,
      educationList,
      careerModalOpen,
      certModalOpen,
      eduModalOpen,
      handleOpenModal,
    }
  },
}
</script>
<style lang="scss" scoped></style>
