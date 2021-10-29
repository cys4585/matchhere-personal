<template>
  <router-view @routeChat="handleRoute" />
</template>

<script>
// import { useRoute } from "vue-router"
export default {
  name: "ChatLayout",
  emits: ["routeChatDetail", "routeChatList"],
  setup(props, { emit }) {
    // const route = useRoute()
    // console.log(route.name)
    // if (route.name === "ChatDetail") emit("routeChatDetail")

    const handleRoute = (child) => {
      console.log(child)
      if (child === "detail") emit("routeChatDetail")
      else if (child === "list") emit("routeChatList")
    }
    return { handleRoute }
  },
}
</script>
<style lang="scss" scoped></style>
