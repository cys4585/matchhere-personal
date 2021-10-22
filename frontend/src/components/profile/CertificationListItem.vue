<template>
  <div class="flex">
    <div class="grid gap-2 flex-1">
      <div class="flex items-center gap-1 font-medium">
        <span class="text-lg">{{ certification.name }}</span>
        <span v-if="certification.grade" class="text-sm"
          >({{ certification.grade }})</span
        >
      </div>
      <p class="text-sm font-medium">{{ certification.code }}</p>
      <p class="font-medium">{{ certification.organization }}</p>
      <p class="text-sm text-gray-700">
        {{ certification.issued_date }} -
        {{ certification.expired_date || "만료기한 없음" }}
      </p>
      <p class="truncate">
        {{ certification.description }}
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
    <CertificationFormModal
      v-if="modalOpen"
      :certificationId="certification.id"
      type="Edit"
      @closeModal="handleToggleModal"
    />
  </div>
</template>

<script>
import CertificationFormModal from "@/components/profile/CertificationFormModal.vue"
import { ref } from "vue"
import { useStore } from "vuex"
export default {
  name: "CertificationListItem",
  components: { CertificationFormModal },
  props: {
    certification: {
      type: Object,
    },
  },
  setup() {
    const store = useStore()
    const modalOpen = ref(false)
    const handleToggleModal = () => {
      modalOpen.value = !modalOpen.value
      store.commit("SET_MODAL_OPEN", modalOpen)
    }
    return {
      modalOpen,
      handleToggleModal,
    }
  },
}
</script>

<style lang="scss" scoped></style>
