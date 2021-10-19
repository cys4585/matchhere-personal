<template>
  <Header />
  <main>
    <router-view />
  </main>
  <BottomNav />
  <AlertMessageCenter />
</template>

<script>
import Header from "@/components/common/Header.vue"
import BottomNav from "@/components/common/BottomNav.vue"
import AlertMessageCenter from "@/components/common/AlertMessageCenter.vue"

export default {
  components: { Header, BottomNav, AlertMessageCenter },
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
