<template>
  <div>{{ isAuthenticated || "Unknown" }}</div>
  <router-view />
</template>

<script>
import { computed } from "vue"
import { useStore } from "vuex"
export default {
  setup() {
    const store = useStore()
    const isAuthenticated = computed(
      () => store.getters["auth/getIsAuthenticated"]
    )
    return {
      isAuthenticated,
    }
  },
}
</script>

<style lang="scss">
* {
  @apply text-gray-900;
}

.divider {
  @apply w-px bg-gray-300;
}

.page-header {
  @apply grid gap-4 mb-10;

  h2 {
    @apply font-bold text-2xl;
  }
}
</style>
