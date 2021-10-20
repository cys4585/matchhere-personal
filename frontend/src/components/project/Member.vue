<template>
  <div class="wrapper">
    <header>
      <h3>구성원</h3>
      <span>5명</span>
    </header>
    <div class="member-list">
      <MemberItem />
    </div>
  </div>
</template>

<script>
import MemberItem from "@/components/project/MemberItem.vue"
export default {
  name: "Member",
  components: { MemberItem },
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
