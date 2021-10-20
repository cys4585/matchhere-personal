<template>
  <div class="wrapper">
    <header>
      <h3>지원자</h3>
      <span>4명</span>
    </header>
    <div class="member-list">
      <ApplyerItem />
    </div>
  </div>
</template>
<script>
import ApplyerItem from "@/components/project/ApplyerItem.vue"

export default {
  name: "Applyer",
  components: {
    ApplyerItem,
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
