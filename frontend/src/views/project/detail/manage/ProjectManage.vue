<template>
  <div class="py-10 px-4 grid gap-10">
    <div class="grid gap-6">
      <div class="flex justify-between">
        <h2 class="font-bold text-2xl">프로젝트</h2>
        <button class="font-medium text-gray-400">수정</button>
      </div>
      <ProjectCard :projectId="projectId" />
    </div>
    <div class="grid gap-6">
      <div class="flex justify-between">
        <h2 class="font-bold text-2xl">구성원 관리</h2>
      </div>
      <div class="grid gap-6">
        <Invite :projectId="projectId" />
        <hr />
        <Member :projectId="projectId" />
        <hr />
        <Applyer :projectId="projectId" />
      </div>
    </div>
  </div>
</template>

<script>
import ProjectCard from "@/components/project/ProjectCard.vue"
import Invite from "@/components/project/Invite.vue"
import Member from "@/components/project/Member.vue"
import Applyer from "@/components/project/Applyer.vue"
import { useRoute } from "vue-router"
import { ref } from "@vue/reactivity"

export default {
  name: "ProjectManage",
  components: { ProjectCard, Invite, Member, Applyer },
  setup() {
    const route = useRoute()

    const projectId = ref(Number(route.params.projectId))
    return {
      projectId,
    }
  },
}
</script>

<style lang="scss" scoped></style>
