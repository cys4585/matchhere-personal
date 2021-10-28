<template>
  <router-view />
</template>

<script>
import { useRoute } from "vue-router"
export default {
  name: "ChatLayout",
  emits: ["routeChatDetail"],
  setup(props, { emit }) {
    const route = useRoute()
    console.log(route.name)
    if (route.name === "ChatDetail") emit("routeChatDetail")
  },
}
</script>
<style lang="scss" scoped></style>
