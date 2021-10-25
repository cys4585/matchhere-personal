<template>
  <div class="flex justify-between items-center">
    <div class="flex gap-2 items-center">
      <img :src="profilePic" alt="" class="w-8 h-8" />
      <p class="text-gray-900 font-medium">{{ memberInfo.name }}</p>
    </div>
    <div class="flex gap-2">
      <div>
        <div v-if="myAuth !== '소유자'" class="not-host">
          {{ memberInfo.authority }}
        </div>
        <div v-else class="host">
          <select
            @change="handleAuthorityChange"
            class="font-bold bg-gray-200 outline-none w-full"
          >
            <option>{{ memberInfo.authority }}</option>
            <option v-for="authority in authorityList" :key="authority">
              {{ authority }}
            </option>
          </select>
          <!-- <span class="material-icons text-sm">expand_more</span> -->
        </div>
      </div>

      <div>
        <div v-if="myAuth !== '팀원'" class="role">
          <select
            @change="handleRoleChange"
            class="font-bold bg-blue-100 outline-none w-full"
          >
            <option>{{ memberInfo.role }}</option>
            <option v-for="role in roleList" :key="role">{{ role }}</option>
          </select>
          <!-- <span class="material-icons text-sm">expand_more</span> -->
        </div>
        <div v-else class="role-info">
          {{ memberInfo.role }}
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { onMounted, ref } from "vue"
import { useStore } from "vuex"
import { useRoute } from "vue-router"
export default {
  name: "MemberItem",
  props: ["memberInfo"],
  setup(props) {
    const profilePic = ref(require("@/assets/images/test-profile.png"))

    const roleActive = ref(false)
    const roleList = ref(["개발자", "기획자", "디자이너"])
    const authorityActive = ref(false)
    const authorityList = ref(["소유자", "관리자", "팀원"])

    const myAuth = ref("")
    const route = useRoute()
    const store = useStore()

    onMounted(async () => {
      const resData = await store.dispatch(
        "project/getAuthority",
        route.params.projectId
      )
      console.log(resData)
      myAuth.value = resData
    })

    const handleRoleChange = async (e) => {
      try {
        console.log(e.target.value)
        const projectId = route.params.projectId
        const memberId = props.memberInfo.id
        const role = e.target.value
        const resData = await store.dispatch("project/changeRole", {
          projectId,
          memberId,
          role,
        })
        console.log(resData)
      } catch (error) {
        store.commit("ADD_MESSAGE", {
          text: error.message,
          type: "error",
        })
      }
    }

    const handleAuthorityChange = async (e) => {
      const projectId = route.params.projectId
      const memberId = props.memberInfo.id
      const authority = e.target.value
      const resData = await store.dispatch("project/changeAuthority", {
        projectId,
        memberId,
        authority,
      })
      console.log(resData)
    }
    return {
      profilePic,
      roleList,
      roleActive,
      handleRoleChange,
      handleAuthorityChange,
      authorityActive,
      authorityList,
      myAuth,
    }
  },
}
</script>

<style lang="scss" scoped>
.not-host {
  @apply flex items-center bg-gray-200 text-gray-900 font-bold text-sm px-4 py-1 rounded-lg;
}
.host {
  @apply flex items-center bg-gray-200 text-gray-900 font-bold text-sm pl-2 pr-1 py-1 rounded-lg;
}

.role {
  @apply flex items-center font-bold text-sm rounded-lg  bg-blue-100 text-gray-900 pl-2 pr-1 py-1;
}

.role-info {
  @apply flex
            items-center
            bg-blue-100
            text-gray-900
            font-bold
            text-sm
            px-4
            py-1
            rounded-lg;
}

// select {
//   -moz-appearance: none; /* Firefox */
//   -webkit-appearance: none; /* Safari and Chrome */
//   appearance: none;
// }
</style>
