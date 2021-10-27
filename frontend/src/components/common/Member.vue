<template>
  <router-link
    :to="{ name: 'Profile', params: { email: user.email } }"
    class="flex gap-1 items-center p-1 hover:bg-yellow-100 rounded"
  >
    <img
      :src="user.coverPicUri"
      alt=""
      class="w-6 h-6 object-cover rounded-full border"
    />
    <span class="text-sm font-medium">{{ user.name }}</span>
  </router-link>
</template>

<script>
export default {
  name: "Member",
  props: {
    user: Object,
  },
}
</script>

<style lang="scss" scoped></style>
