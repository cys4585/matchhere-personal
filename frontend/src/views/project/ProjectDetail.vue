<template>
  <nav>
    <button
      @click="handleClick"
      name="ProjectNotiBoard"
      :class="{ active: activedView === 'ProjectNotiBoard' }"
    >
      공지사항
    </button>
    <button
      @click="handleClick"
      name="BoardArticleList"
      :class="{ active: activedView === 'BoardArticleList' }"
    >
      게시판
    </button>
    <button
      @click="handleClick"
      name="ProjectManage"
      :class="{ active: activedView === 'ProjectManage' }"
    >
      프로젝트 관리
    </button>
  </nav>
  <hr />
  <router-view></router-view>
</template>

<script>
import { ref } from "@vue/reactivity"
import { useRoute, useRouter } from "vue-router"
import { onMounted, onUpdated } from "@vue/runtime-core"
export default {
  name: "ProjectDetail",
  setup() {
    const route = useRoute()
    const router = useRouter()

    const projectId = route.params.projectId

    const activedView = ref()

    onMounted(() => {
      activedView.value = route.name
    })

    onUpdated(() => {
      activedView.value = route.name
    })

    const handleClick = (e) => {
      const name = e.target.name
      router.push({ name, params: { projectId } })
    }
    return { handleClick, activedView }
  },
}
</script>
<style lang="scss" scoped>
nav {
  @apply py-3 px-2;
  button {
    @apply py-2 px-4 text-gray-500;

    &.active {
      @apply font-bold text-black;
    }
  }
}
</style>
