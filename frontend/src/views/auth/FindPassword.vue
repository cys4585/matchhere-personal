<template>
  <component :is="currentComponent" />
</template>

<script>
import { computed, ref } from "@vue/reactivity"
import FindPWStepOne from "@/components/auth/FindPWStepOne.vue"
import FindPWStepTwo from "@/components/auth/FindPWStepTwo.vue"

export default {
  name: "FindPassword",
  components: { FindPWStepOne, FindPWStepTwo },
  setup() {
    const step = ref(1)
    const currentComponent = computed(() =>
      step.value === 1 ? FindPWStepOne : FindPWStepTwo
    )
    return {
      currentComponent,
    }
  },
}
</script>

<style></style>
