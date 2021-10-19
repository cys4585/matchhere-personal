<template>
  <nav>
    <div class="nav-center">
      <router-link class="logo" to="/">MatchHere</router-link>
      <div class="buttons">
        <router-link :to="{ name: 'Login' }" v-if="!isAuthenticated">
          로그인
        </router-link>
        <template v-else>
          <router-link
            :to="{
              name: 'Profile',
              params: { email: 'kepy1106@hanyang.ac.kr' },
            }"
          >
            프로필
          </router-link>
          <button @click="handleClickLogoutBtn">로그아웃</button>
        </template>
      </div>
    </div>
  </nav>
</template>

<script>
import { computed } from "vue"
import { useStore } from "vuex"
import { useRouter } from "vue-router"
export default {
  name: "MainNav",
  setup() {
    const store = useStore()
    const router = useRouter()
    const isAuthenticated = computed(
      () => store.getters["auth/getIsAuthenticated"]
    )

    const handleClickLogoutBtn = () => {
      store.dispatch("auth/logout")
      router.push({ name: "Home" })
    }

    return {
      isAuthenticated,
      handleClickLogoutBtn,
    }
  },
}
</script>

<style lang="scss" scoped>
nav {
  @apply w-full border-b border-gray-200;

  .nav-center {
    @apply container flex items-center justify-between h-16;

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
