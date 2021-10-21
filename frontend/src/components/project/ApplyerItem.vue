<template>
  <div
    class="flex justify-between items-center hover:bg-blue-50 cursor-pointer"
    @click="isApplicationModalActivation = true"
  >
    <div class="flex gap-2 items-center">
      <img :src="profilePic" alt="" class="w-8 h-8" />
      <p class="text-gray-900 font-medium">{{ applyer.name }}</p>
    </div>
    <div
      class="bg-blue-100 text-gray-900 font-bold text-sm px-4 py-1 rounded-lg"
    >
      {{ applyer.role }}
    </div>
  </div>
  <ApplicationModal
    @close="isApplicationModalActivation = false"
    v-if="isApplicationModalActivation"
    :projectId="projectId"
    :memberId="applyer.memberId"
  />
</template>

<script>
import { ref } from "vue"
import ApplicationModal from "@/components/project/ApplicationModal.vue"

export default {
  name: "ApplyerItem",
  components: { ApplicationModal },
  props: {
    applyer: Object,
    projectId: Number,
  },
  setup() {
    const profilePic = ref(require("@/assets/images/test-profile.png"))

    const isApplicationModalActivation = ref(false)

    return {
      profilePic,
      isApplicationModalActivation,
    }
  },
}
</script>

<style lang="scss" scoped></style>
