<template>
  <div class="flex">
    <div class="grid gap-2 flex-1">
      <p class="text-lg font-medium">{{ career.role }}</p>
      <p class="font-medium">{{ career.company }}</p>
      <p class="text-sm text-gray-700">
        {{ career.start_date }} - {{ career.end_date || "재직 중" }}
      </p>
      <p class="truncate">
        {{ career.description }}
      </p>
    </div>
    <div>
      <button>
        <span
          class="material-icons text-gray-300 hover:text-blue-500"
          style="font-size: 1rem"
          @click="handleToggleModal"
        >
          edit
        </span>
      </button>
    </div>
    <CareerFormModal
      v-if="careerModalOpen"
      :careerId="career.id"
      type="EDIT"
      @closeModal="handleToggleModal"
    />
  </div>
</template>

<script>
import CareerFormModal from "@/components/profile/CareerFormModal.vue"
import { ref } from "vue"
import { useStore } from "vuex"
export default {
  name: "CareerListItem",
  components: { CareerFormModal },
  props: {
    career: {
      type: Object,
    },
  },
  emits: ["updateCareer"],
  setup(_, { emit }) {
    const store = useStore()
    const careerModalOpen = ref(false)
    const handleToggleModal = (payload) => {
      careerModalOpen.value = !careerModalOpen.value
      store.commit("SET_MODAL_OPEN", careerModalOpen)
      if (payload.action === "update") {
        emit("updateCareer", payload.data)
      }
    }
    return {
      careerModalOpen,
      handleToggleModal,
    }
  },
}
</script>

<style lang="scss" scoped></style>
