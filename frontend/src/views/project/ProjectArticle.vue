<template>
  <div v-if="projectInfo">
    <div class="img-wrapper">
      <img :src="projectInfo.coverPicUri || 'https://picsum.photos/80'" />
      <button class="photo-button" @click="handleClickPhoto">
        <span class="material-icons"> add_photo_alternate </span>
      </button>
      <input
        type="file"
        accept="image/*"
        class="hidden"
        ref="fileInputEl"
        @change="handleFileChange"
      />
    </div>
    <!-- <div class="mt-4 flex justify-center h-40">
      <img
        v-if="projectInfo.coverPicUri"
        :src="projectInfo.coverPicUri"
        alt=""
      />
      <div v-else class="self-end">
        <label for="coverpic" class="cursor-pointer hover:text-gray-500"
          >커버 사진 등록</label
        >
        <input
          type="file"
          accept="image/*"
          name="coverpic"
          id="coverpic"
          hidden
          @change="handleFileChange"
        />
      </div>
    </div> -->
    <div class="container">
      <section class="project-section">
        <header>
          <h2>{{ projectInfo.name }}</h2>
          <div class="grid gap-2">
            <p>
              <span
                class="font-bold rounded px-1"
                :class="projectProgressStateColorClass"
              >
                {{ projectInfo.projectProgressState }}
              </span>
            </p>
            <p>
              <span
                class="font-bold rounded px-1"
                :class="recruitmentStateColorClass"
                >팀원 {{ projectInfo.recruitmentState }}</span
              >
            </p>
            <p class="flex gap-2">
              <span class="text-xs text-gray-500"
                >조회수 {{ projectInfo.viewCount }}회</span
              >
              <span class="text-xs text-gray-500">
                {{ projectInfo.createDate.slice(0, 10) }}
              </span>
            </p>
            <p class="flex gap-2 items-center">
              <img :src="profilePic" alt="" class="w-6 h-6" />
              <span class="text-xs text-gray-500">{{
                projectInfo.host.name
              }}</span>
            </p>
          </div>
        </header>
        <div class="project-container">
          <div class="content">
            <h3>주요 기술 스택</h3>
            <div class="grid gap-4">
              <p
                v-for="tech in projectInfo.techstacks"
                :key="tech.name"
                class="flex gap-2 items-center"
              >
                <img
                  class="w-4 h-4"
                  :src="require('@/assets/images/noStack.png')"
                  alt="로고"
                />
                <span> {{ tech.name }}</span>
              </p>
              <!-- <p class="flex gap-2 items-center">
                <img :src="javaPic" alt="" class="w-4 h-4" />
                <span>java</span>
              </p>
              <p class="flex gap-2 items-center">
                <img :src="pythonPic" alt="" class="w-4 h-4" />
                <span>python</span>
              </p> -->
            </div>
          </div>
          <div class="content">
            <h3>일정</h3>
            <div class="grid gap-2">
              <p>{{ projectInfo.schedule }}</p>
              <p v-if="projectInfo.period">{{ projectInfo.period }} 까지</p>
            </div>
          </div>
          <div class="content">
            <h3>팀원</h3>
            <div class="grid gap-6">
              <div class="grid gap-2">
                <div class="flex justify-between items-center">
                  <h4>개발자</h4>
                  <p
                    class="text-sm"
                    :class="{
                      'text-gray-400':
                        projectInfo.developerMaxCount ===
                        projectInfo.developerCount,
                    }"
                  >
                    {{ projectInfo.developerMaxCount }}명 중
                    {{ projectInfo.developerCount }}명
                  </p>
                </div>
                <div class="flex gap-4">
                  <p
                    class="flex gap-2 items-center"
                    v-for="developer in projectInfo.developers"
                    :key="developer.id"
                  >
                    <img :src="profilePic" alt="" class="w-6 h-6" />
                    <span class="text-xs text-gray-500">{{
                      developer.name
                    }}</span>
                  </p>
                </div>
              </div>
              <div class="grid gap-2">
                <div class="flex justify-between items-center">
                  <h4>기획자</h4>
                  <p
                    class="text-sm"
                    :class="{
                      'text-gray-400':
                        projectInfo.plannerMaxCount ===
                        projectInfo.plannerCount,
                    }"
                  >
                    {{ projectInfo.plannerMaxCount }}명 중
                    {{ projectInfo.plannerCount }}명
                  </p>
                </div>
                <div class="flex gap-4">
                  <p
                    class="flex gap-2 items-center"
                    v-for="planner in projectInfo.planners"
                    :key="planner.id"
                  >
                    <img :src="profilePic" alt="" class="w-6 h-6" />
                    <span class="text-xs text-gray-500">{{
                      planner.name
                    }}</span>
                  </p>
                </div>
              </div>
              <div class="grid gap-2">
                <div class="flex justify-between items-center">
                  <h4>디자이너</h4>
                  <p
                    class="text-sm"
                    :class="{
                      'text-gray-400':
                        projectInfo.designerMaxCount ===
                        projectInfo.designerCount,
                    }"
                  >
                    {{ projectInfo.designerMaxCount }}명 중
                    {{ projectInfo.designerCount }}명
                  </p>
                </div>
                <div class="flex gap-4">
                  <p
                    class="flex gap-2 items-center"
                    v-for="designer in projectInfo.designers"
                    :key="designer.id"
                  >
                    <img :src="profilePic" alt="" class="w-6 h-6" />
                    <span class="text-xs text-gray-500">{{
                      designer.name
                    }}</span>
                  </p>
                </div>
              </div>
            </div>
          </div>
          <div class="content">
            <h3>지역</h3>
            <p>{{ projectInfo.city }}</p>
          </div>
          <div class="content" v-if="projectInfo.club">
            <h3>소속 클럽</h3>
            <div class="flex justify-between">
              <p>{{ projectInfo.club.name }}</p>
              <span class="material-icons text-gray-400"> open_in_new </span>
            </div>
          </div>
          <div class="content">
            <h3>소개</h3>
            <div
              v-html="projectInfo.bio.replace(/(?:\r\n|\r|\n)/g, '<br />')"
            ></div>
          </div>
        </div>
        <div class="flex flex-col items-center gap-2 px-7">
          <button
            v-if="projectInfo.authority === '게스트'"
            class="w-full text-white py-4 rounded-full bg-blue-500"
            @click="isApplyModalActivation = true"
          >
            프로젝트 참가 신청
          </button>
          <button
            v-if="projectInfo.authority === '소유자'"
            class="py-2 px-6"
            @click="editProject"
          >
            <p class="text-gray-600 font-medium text-sm">수정</p>
          </button>
        </div>
      </section>
    </div>
    <ApplyForParticipationModal
      v-if="isApplyModalActivation"
      @close="isApplyModalActivation = false"
      :projectId="projectInfo.id"
    />
  </div>
</template>

<script>
import { onMounted, ref } from "@vue/runtime-core"
import { useRoute, useRouter } from "vue-router"
import { useStore } from "vuex"
import ApplyForParticipationModal from "@/components/project/ApplyForParticipationModal.vue"

export default {
  name: "ProjectArticle",
  components: { ApplyForParticipationModal },
  setup() {
    const route = useRoute()
    const store = useStore()
    const router = useRouter()

    const projectInfo = ref()
    const projectProgressStateColorClass = ref()
    const recruitmentStateColorClass = ref()

    onMounted(async () => {
      projectInfo.value = await store.dispatch(
        "project/getProject",
        route.params.projectId
      )
      const projectProgressState = projectInfo.value.projectProgressState
      if (projectProgressState === "프로젝트 준비 중") {
        projectProgressStateColorClass.value = "bg-green-100 text-green-600"
      } else if (projectProgressState === "프로젝트 진행 중 ") {
        projectProgressStateColorClass.value = "bg-blue-100 text-blue-600"
      } else {
        projectProgressStateColorClass.value = "bg-red-100 text-red-600"
      }
      recruitmentStateColorClass.value =
        projectInfo.value.recruitmentState === "모집 중"
          ? "bg-green-100 text-green-600"
          : "bg-red-100 text-red-600"
    })

    const fileInputEl = ref(null)
    const handleClickPhoto = () => {
      fileInputEl.value.click()
    }

    const handleFileChange = async (e) => {
      try {
        const files = e.target.files || e.dataTransfer.files
        if (!files.length) return
        const formData = new FormData()
        formData.append("file", files[0])
        const picInfo = await store.dispatch("file/uploadFile", formData)
        console.log(picInfo)
        const pjtPicInfo = await store.dispatch("project/updatePicture", {
          projectId: route.params.projectId,
          uuid: picInfo.id,
        })
        console.log(pjtPicInfo)
        projectInfo.value.coverPicUri = pjtPicInfo.download_uri
      } catch (error) {
        console.log(error.message)
      }
    }

    const isApplyModalActivation = ref()

    const editProject = () => {
      router.push({
        name: "ProjectEditForm",
        params: { projectId: projectInfo.value.id },
      })
    }

    const profilePic = ref(require("@/assets/images/test-profile.png"))
    const javaPic = ref(require("@/assets/images/test-java.png"))
    const pythonPic = ref(require("@/assets/images/test-python.png"))

    return {
      projectInfo,
      handleFileChange,
      projectProgressStateColorClass,
      recruitmentStateColorClass,
      isApplyModalActivation,
      editProject,
      profilePic,
      javaPic,
      pythonPic,
      handleClickPhoto,
      fileInputEl,
    }
  },
}
</script>

<style lang="scss" scoped>
.img-wrapper {
  @apply relative mt-4 flex justify-center h-40 w-full;

  .photo-button {
    @apply absolute bottom-0 right-0 flex items-center justify-center bg-white rounded-full p-1 transition-all;

    span {
      @apply text-gray-700 transition-all;
    }

    &:hover {
      @apply bg-blue-50;
      span {
        @apply text-blue-500;
      }
    }
  }
}

.project-section {
  @apply pt-6 pb-10 grid gap-10;

  header {
    @apply grid gap-4;

    h2 {
      @apply font-bold text-2xl;
    }
  }

  .project-container {
    @apply flex flex-col gap-8 items-center;

    .content {
      @apply w-full grid gap-2;

      h3 {
        @apply font-bold text-xl;
      }

      h4 {
        @apply font-medium text-lg;
      }
    }
  }
}
</style>
