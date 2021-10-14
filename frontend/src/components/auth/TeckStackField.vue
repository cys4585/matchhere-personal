<template>
  <div class="field">
    <div class="input-field">
      <label for="teckstack-input">기술스택</label>
      <div class="input-wrapper">
        <input type="text" id="teckstack-input" v-model="searchKeyword" />
        <button class="material-icons" v-if="searchKeyword.length === 0">
          search
        </button>
        <button class="material-icons" v-else @click="searchKeyword = ''">
          close
        </button>
      </div>
    </div>
    <ul class="teckstack-list" v-if="searchKeyword">
      <template v-if="searchedTechStackList.length !== 0">
        <li v-for="ts in searchedTechStackList" :key="ts.refIndex">
          <button @click="handleClickTeckStack(ts.item)">
            <img :src="require(`${ts.item}.png`)" :alt="`$${ts.item}로고`" />
            <span>{{ ts.item }}</span>
          </button>
        </li>
      </template>
      <li v-else>검색 결과가 없습니다.</li>
    </ul>
  </div>
</template>

<script>
import Fuse from "fuse.js"
import { teckStackList } from "@/libs/data"
import { computed, ref } from "vue"

export default {
  name: "TeckStackField",
  emits: ["clickTeckStack"],
  setup(_, { emit }) {
    const fuseOptions = {
      // includeScore: true,
      threshold: 0.06,
    }
    const teckStackFuse = new Fuse(teckStackList, fuseOptions)
    const searchKeyword = ref("")
    const searchedTechStackList = computed(() =>
      teckStackFuse.search(searchKeyword.value)
    )

    const handleClickTeckStack = (ts) => {
      emit("clickTeckStack", ts)
    }

    return { searchKeyword, searchedTechStackList, handleClickTeckStack }
  },
}
</script>

<style lang="scss" scoped>
.field {
  @apply relative;

  .input-field {
    @apply grid gap-2;

    label {
      @apply place-self-start;
    }

    .input-wrapper {
      @apply relative;

      input {
        @apply w-full border border-gray-400 rounded-md py-2 px-4 outline-none;

        &:focus {
          @apply ring-1 ring-blue-500;
        }
      }

      .material-icons {
        transform: translateY(-50%);
        @apply absolute top-1/2 right-2;
      }
    }
  }

  .teckstack-list {
    @apply grid gap-2 max-h-40 overflow-scroll absolute left-0 w-full bg-white shadow-md p-4;

    li {
      button {
        @apply w-full text-left;
      }
    }
  }
}
</style>
