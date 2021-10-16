<template>
  <Header />
  <main>
    <router-view />
  </main>
  <BottomNav />
</template>

<script>
import Header from "@/components/common/Header.vue"
import BottomNav from "@/components/common/BottomNav.vue"

export default {
  components: { Header, BottomNav },
}
</script>

<style lang="scss">
* {
  @apply text-gray-900;
}

#app {
  @apply min-h-screen relative flex flex-col;
}

main {
  @apply flex-1;
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
