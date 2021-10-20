<template>
  <div class="py-10 px-4 grid gap-10">
    <div class="grid gap-6">
      <div class="flex justify-between">
        <h2 class="font-bold text-2xl">프로젝트</h2>
        <button class="font-medium text-gray-400">수정</button>
      </div>
      <ProjectCard />
    </div>
    <div class="grid gap-6">
      <div class="flex justify-between">
        <h2 class="font-bold text-2xl">구성원 관리</h2>
      </div>
      <div class="grid gap-6">
        <Invite />
        <hr />
        <Member />
        <hr />
        <div>지원자</div>
      </div>
    </div>
  </div>
</template>

<script>
import ProjectCard from "@/components/project/ProjectCard.vue"
import Invite from "@/components/project/Invite.vue"
import Member from "@/components/project/Member.vue"

export default {
  name: "ProjectManage",
  components: { ProjectCard, Invite, Member },
}
</script>

<style lang="scss" scoped></style>
