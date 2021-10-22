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
          @click="handleToggleModal({ type: 'career' })"
        >
          + 추가
        </button>
      </header>
      <div class="grid gap-4">
        <CareerListItem
          v-for="career in careerList"
          :key="career.id"
          :career="career"
          @updateCareer="handleUpdateCareer"
        />
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
          @click="handleToggleModal({ type: 'certification' })"
        >
          + 추가
        </button>
      </header>
      <div class="grid gap-4">
        <CertificationListItem
          v-for="certification in certificationList"
          :key="certification.id"
          :certification="certification"
          @updateCertification="handleUpdateCertification"
        />
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
  <CareerFormModal
    v-if="careerModalOpen"
    type="CREATE"
    @closeModal="handleToggleModal"
  />
  <CertificationFormModal
    v-if="certificationModalOpen"
    type="CREATE"
    @closeModal="handleToggleModal"
  />
  <!-- <AddCareerModal /> -->
</template>

<script>
import CareerFormModal from "@/components/profile/CareerFormModal.vue"
import CertificationFormModal from "@/components/profile/CertificationFormModal.vue"
import CareerListItem from "@/components/profile/CareerListItem.vue"
import CertificationListItem from "@/components/profile/CertificationListItem.vue"
import { onMounted, ref } from "vue"
import { useStore } from "vuex"
export default {
  name: "Career",
  components: {
    CareerFormModal,
    CertificationFormModal,
    CareerListItem,
    CertificationListItem,
  },
  setup() {
    const store = useStore()
    const careerList = ref([])
    const certificationList = ref([])
    const educationList = ref([])
    const careerModalOpen = ref(false)
    const certificationModalOpen = ref(false)
    const eduModalOpen = ref(false)

    const addNewCareer = (data) => {
      careerList.value = [...careerList.value, { ...data }]
    }
    const addNewCertification = (data) => {
      certificationList.value = [...certificationList.value, { ...data }]
    }

    const handleUpdateCareer = (data) => {
      careerList.value = careerList.value.map((c) => {
        return c.id === data.id ? data : c
      })
    }
    const handleUpdateCertification = (data) => {
      certificationList.value = certificationList.value.map((c) => {
        return c.id === data.id ? data : c
      })
    }

    const handleToggleModal = ({ type, action, data }) => {
      switch (type) {
        case "career": {
          careerModalOpen.value = !careerModalOpen.value
          store.commit("SET_MODAL_OPEN", careerModalOpen.value)
          if (action === "create") {
            addNewCareer(data)
          }
          break
        }
        case "certification": {
          certificationModalOpen.value = !certificationModalOpen.value
          store.commit("SET_MODAL_OPEN", certificationModalOpen.value)
          if (action === "create") {
            addNewCertification(data)
          }
          break
        }
        case "education": {
          eduModalOpen.value = !eduModalOpen.value
          store.commit("SET_MODAL_OPEN", eduModalOpen.value)
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
      certificationModalOpen,
      eduModalOpen,
      handleUpdateCareer,
      handleUpdateCertification,
      handleToggleModal,
    }
  },
}
</script>
<style lang="scss" scoped></style>
