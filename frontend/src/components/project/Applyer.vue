<template>
  <div class="wrapper">
    <header>
      <h3>지원자</h3>
      <span>4명</span>
    </header>
    <div class="member-list">
      <ApplyerItem
        v-for="applyer in applyerList"
        :key="applyer.memberId"
        :applyer="applyer"
        :projectId="projectId"
      />
    </div>
  </div>
</template>
<script>
import ApplyerItem from "@/components/project/ApplyerItem.vue"
import { onMounted, ref } from "vue"
import { useStore } from "vuex"

export default {
  name: "Applyer",
  components: {
    ApplyerItem,
  },
  props: {
    projectId: Number,
  },
  setup(props) {
    const store = useStore()

    const applyerList = ref()
    onMounted(async () => {
      applyerList.value = await store.dispatch(
        "project/getAllApplication",
        props.projectId
      )
    })

    return { applyerList }
  },
}
</script>

<style lang="scss" scoped>
.wrapper {
  @apply grid gap-4;

  header {
    @apply flex justify-between items-center;

    h3 {
      @apply font-medium text-xl text-gray-900;
    }
    span {
      @apply font-bold text-gray-900;
    }
  }
  .member-list {
    @apply grid gap-2;
  }
}
</style>
