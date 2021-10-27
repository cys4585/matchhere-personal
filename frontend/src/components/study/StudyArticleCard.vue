<template>
  <div
    class="study-card"
    :class="{ small }"
    @mousedown="handleMousedown"
    @mouseup="handleMouseup"
  >
    <div class="thumbnail-wrapper">
      <img
        class=""
        src="https://picsum.photos/500/300"
        alt=""
        draggable="false"
      />
      <p class="recruit-state">팀원 모집 중</p>
    </div>
    <div class="info-wrapper">
      <p class="name">백준 알고리즘 스터디 하실 분</p>
      <div class="tags mb-2">
        <p class="tag subject">알고리즘</p>
        <p class="tag subject">Level 2</p>
      </div>
      <div class="tags mb-2">
        <p class="tag study-state">스터디 진행 중</p>
      </div>
      <p class="city">지역: 무관(인터넷)</p>
    </div>
    <div class="bottom-wrapper">
      <div class="left">
        <div class="profile-img-wrapper"></div>
        <span class="host-name">김병훈</span>
        <span class="created">5일 전</span>
      </div>
      <button>
        <span class="material-icons">more_vert</span>
      </button>
    </div>
  </div>
</template>

<script>
import { ref } from "@vue/reactivity"
export default {
  name: "StudyArticleCard",
  props: {
    study: Object,
    small: {
      type: Boolean,
      default: false,
    },
  },
  setup() {
    const mousePosition = ref([0, 0])
    const handleMousedown = (e) => {
      console.log("down")
      mousePosition.value = [e.clientX, e.clientY]
    }
    const handleMouseup = (e) => {
      if (
        mousePosition.value[0] !== e.clientX ||
        mousePosition.value[1] !== e.clientY
      ) {
        return
      }
      console.log("click")
    }
    return { handleMousedown, handleMouseup }
  },
}
</script>

<style lang="scss" scoped>
.study-card {
  @apply rounded-md overflow-hidden shadow-md w-full flex-shrink-0 select-none;

  &.small {
    @apply w-80;
  }

  .thumbnail-wrapper {
    padding-top: 50%;
    @apply relative;

    img {
      @apply absolute inset-0 w-full h-full object-cover;
    }

    .recruit-state {
      @apply absolute top-2 right-2 py-1 px-2 text-sm font-bold rounded bg-green-100 text-green-600 shadow select-none;
    }
  }

  .info-wrapper {
    @apply p-4 border-b border-gray-200;

    .name {
      @apply font-medium mb-2;
    }

    .tags {
      @apply flex flex-wrap gap-2;

      .tag {
        @apply py-1 px-2 text-sm font-bold rounded;

        &.subject {
          @apply bg-pink-100 text-pink-600;
        }
        &.study-state {
          @apply bg-blue-100 text-blue-600;
        }
        &.recruit-state {
          @apply bg-green-100 text-green-600;
        }
      }
    }

    .city {
      @apply text-sm text-gray-600;
    }
  }

  .bottom-wrapper {
    @apply p-2 flex items-center justify-between;
    .left {
      @apply flex gap-2 items-center;

      .profile-img-wrapper {
        @apply rounded-full bg-teal-200 w-6 h-6;
      }
      .host-name {
        @apply text-sm font-medium;
      }
      .created {
        @apply text-sm text-gray-700;
      }
    }

    button {
      @apply flex;
    }
  }
}
</style>
