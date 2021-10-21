<template>
  <div class="wrapper" @mousedown="handleMousedown">
    <div class="article-info">
      <div>[회의록] 1주차 회의내용</div>
      <div class="flex gap-2">
        <span class="tag"> 회의록 </span>
        <span class="tag"> 회의록 </span>
      </div>
      <div class="other-info">
        <div class="user">
          <img :src="profilePic" alt="" class="w-6 h-6" />
          <span class="text-gray-600">김병훈</span>
        </div>
        <span class="text-gray-600">2021. 10. 10.</span>
      </div>
    </div>
    <div class="icon-wrapper">
      <span
        @click="editButtonActivate = !editButtonActivate"
        class="material-icons text-gray-400 cursor-pointer"
        >more_vert</span
      >
      <button
        v-if="editButtonActivate"
        class="edit-button"
        @click="handleEditClick"
      >
        수정
      </button>
    </div>
  </div>
</template>

<script>
import { ref } from "vue"

export default {
  name: "BoardItem",
  setup() {
    const profilePic = ref(require("@/assets/images/test-profile.png"))

    const editButtonActivate = ref(false)

    const handleMousedown = (e) => {
      const innerText = e.target.innerText
      if (innerText === "more_vert" || innerText === "수정") return
      editButtonActivate.value = false
    }

    const handleEditClick = () => {
      console.log("asdf")
    }

    return {
      profilePic,
      editButtonActivate,
      handleMousedown,
      handleEditClick,
    }
  },
}
</script>

<style lang="scss" scoped>
.wrapper {
  @apply flex hover:bg-blue-50 cursor-pointer;

  .article-info {
    @apply flex-grow grid gap-2;

    .tag {
      @apply px-2 bg-gray-100 rounded font-bold text-sm text-gray-600;
      padding-top: 2px;
      padding-bottom: 2px;
    }

    .other-info {
      @apply flex gap-2 items-center text-xs;

      .user {
        @apply flex gap-2 items-center;
      }
    }
  }

  .icon-wrapper {
    @apply flex flex-col items-end;

    .edit-button {
      @apply border-gray-100 rounded shadow-md text-gray-700 py-1 px-2 font-medium text-sm;
    }
  }
}
</style>
