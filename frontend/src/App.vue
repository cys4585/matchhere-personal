<template>
  <router-view />
</template>

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
