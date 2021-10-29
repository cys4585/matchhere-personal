<template>
  <div class="comment" :class="{ nested: nestedComment }">
    <div class="info-wrapper">
      <div class="img"></div>
      <div class="info-text-wrapper">
        <p class="name">김병훈</p>
        <p class="date">하루 전</p>
      </div>
    </div>
    <p class="content">확인했습니다!</p>
  </div>
</template>

<script>
export default {
  name: "Comment",
  props: {
    nestedComment: {
      type: Boolean,
      default: false,
    },
  },
}
</script>

<style lang="scss" scoped>
.comment {
  @apply py-4 grid gap-4;

  &.nested {
    @apply pb-6 border-b border-gray-200;
  }

  .info-wrapper {
    @apply flex gap-4;

    .img {
      @apply w-12 h-12 bg-pink-200 rounded-full;
    }

    .info-text-wrapper {
      @apply grid gap-1;

      .name {
        @apply font-medium;
      }

      .date {
        @apply text-sm text-gray-500;
      }
    }
  }
}
</style>
