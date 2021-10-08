<template>
  <div class="height100">
    <div class="height8">
      <SubNavBar />
    </div>
    <div style="height: 5%"></div>
    <div style="height: 87%">
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
import SubNavBar from '../components/SubNavBar.vue';

export default {
  components: {
    SubNavBar,
  },
};
</script>
