<template>
  <header>
    <div class="container inner">
      <router-link class="logo" to="/">MatchHere</router-link>
      <div class="buttons">
        <router-link :to="{ name: 'Login' }" v-if="!isAuthenticated">
          로그인
        </router-link>
        <template v-else>
          <router-link to="/">프로필</router-link>
          <button @click="handleClickLogoutBtn">로그아웃</button>
        </template>
      </div>
    </div>
  </header>
</template>

<script>
import { computed } from "vue"
import { useStore } from "vuex"
export default {
  name: "Header",
  setup() {
    const store = useStore()
    const isAuthenticated = computed(
      () => store.getters["auth/getIsAuthenticated"]
    )

    const handleClickLogoutBtn = () => {
      store.dispatch("auth/logout")
    }

    return {
      isAuthenticated,
      handleClickLogoutBtn,
    }
  },
}
</script>

<style lang="scss" scoped>
header {
  @apply w-full border-b border-gray-200;

  .inner {
    @apply flex items-center justify-between h-16;

    .buttons {
      @apply flex gap-2;

      a,
      button {
        @apply py-2 px-4 border border-gray-200 rounded transition-colors;

        &:hover {
          @apply bg-gray-100;
        }
      }
    }
  }
}
</style>
