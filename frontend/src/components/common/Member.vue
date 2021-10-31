<template>
  <router-link
    :to="{ name: 'Profile', params: { email: user.email } }"
    :class="{ large: large }"
  >
    <div class="image-wrapper">
      <img v-if="user.coverPicUri" :src="user.coverPicUri" alt="" />
    </div>
    <span class="name">{{ user.name }}</span>
  </router-link>
</template>

<script>
export default {
  name: "Member",
  props: {
    user: Object,
    large: {
      type: Boolean,
      default: false,
    },
  },
}
</script>

<style lang="scss" scoped>
a {
  @apply flex gap-1 items-center p-1 hover:bg-yellow-100 rounded;

  .image-wrapper {
    @apply w-6 h-6 rounded-full border bg-pink-100;

    img {
      @apply w-full h-full object-cover;
    }
  }

  .name {
    @apply text-sm font-medium;
  }

  &.large {
    @apply gap-2;

    .image-wrapper {
      @apply w-8 h-8;
    }

    .name {
      @apply text-base;
    }
  }
}
</style>
