<template>
  <div>클럽 수정</div>
</template>

<script>
import { useStore } from 'vuex';
import { useRouter } from 'vue-router';

export default {
  name: 'clubUpdate',
  setup() {
    const store = useStore();
    const router = useRouter();

    return {
      store,
      router,
    };
  },
};
</script>

<style></style>
