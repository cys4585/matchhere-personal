<template>
  <header class="page-header">
    <div class="flex items-center justify-between">
      <h2>회원가입</h2>
      <div class="flex gap-2">
        <router-link to="/">Step 1</router-link>
        <div class="divider"></div>
        <router-link to="/">Step 2</router-link>
      </div>
    </div>
  </header>
  <section class="register-section">
    <keep-alive>
      <component :is="currentComponent" />
    </keep-alive>
  </section>
</template>

<script>
import RegisterStepOne from "@/components/auth/RegisterStepOne.vue"
import { computed, ref } from "vue"

export default {
  name: "Register",
  components: { RegisterStepOne },
  setup() {
    const step = ref(1)
    const currentComponent = computed(() => {
      switch (step.value) {
        case 1: {
          return RegisterStepOne
        }
        default: {
          return RegisterStepOne
        }
      }
    })
    return { currentComponent }
  },
}
</script>

<style lang="scss" scoped></style>
