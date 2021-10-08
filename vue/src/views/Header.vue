<template>
  <div class="height100">
    <div class="height8">
      <TopNavBar />
    </div>
    <div class="height92">
      <router-view></router-view>
    </div>
  </div>
</template>
<script>
import TopNavBar from '../components/TopNavBar.vue';

export default {
  components: {
    TopNavBar,
  },
};
</script>
<style scoped></style>
