<template>
  <div class="grid gap-4 sm:gap-6">
    <h3>스터디</h3>
    <div class="grid gap-2">
      <label class="text-sm font-medium">썸네일 이미지</label>
      <div class="img-wrapper">
        <img
          v-if="fields.coverpic.id"
          :src="fields.coverpic.url"
          :alt="fields.coverpic.name"
        />
        <button
          class="add-photo-btn"
          :class="{ hide: fields.coverpic.id }"
          @click="handleClickAddPhotoBtn"
        >
          <span class="material-icons">photo</span>
          <span class="label">이미지 업로드</span>
        </button>
        <input
          type="file"
          ref="fileInputEl"
          class="hidden"
          @change="handleChangeFileInput"
        />
      </div>
    </div>
    <div class="form-field">
      <label for="name">스터디 이름</label>
      <input
        type="text"
        id="name"
        v-model="fields.name.value"
        placeholder="ex) 11월까지 진행하는 스터디"
      />
    </div>
    <div class="form-field">
      <label for="subject">스터디 주제</label>
      <input
        type="text"
        id="subject"
        v-model="fields.subject.value"
        placeholder="ex) javascript, CS, 인터뷰"
        @keydown.enter="handleAddSubject"
      />
      <div class="subject-list">
        <div v-if="!subjectList.length" class="default">
          스터디 주제를 입력하세요 😀
        </div>
        <button
          class="subject-list-item"
          v-for="subject in subjectList"
          :key="subject"
          @click="handleRemoveSubject(subject)"
        >
          <span class="label">{{ subject }}</span>
          <!-- <span class="material-icons">close</span> -->
        </button>
      </div>
    </div>
    <div class="form-field">
      <label for="schedule">일정</label>
      <input
        type="text"
        id="schedule"
        v-model="fields.schedule.value"
        placeholder="ex) 주말 10시 - 18시 / 평일 논의"
      />
    </div>
    <div class="form-field">
      <label>스터디 상태</label>
      <div class="radio-label-input">
        <input
          type="radio"
          name="project-progress-state"
          value="준비 중"
          id="pps-ready"
          v-model="fields.projectProgressState.value"
        />
        <label for="pps-ready">준비 중</label>
      </div>
      <div class="radio-label-input">
        <input
          type="radio"
          name="project-progress-state"
          value="진행 중"
          id="pps-doing"
          v-model="fields.projectProgressState.value"
        />
        <label for="pps-doing">진행 중</label>
      </div>
      <div class="radio-label-input">
        <input
          type="radio"
          name="project-progress-state"
          value="종료"
          id="pps-done"
          v-model="fields.projectProgressState.value"
        />
        <label for="pps-done">종료</label>
      </div>
    </div>
    <SelectFormField :field="fields.city" v-model="fields.city.value" />
    <div class="form-field">
      <label for="bio">소개</label>
      <textarea id="bio" v-model="fields.bio.value" />
    </div>
    <div class="form-field">
      <label>모집글 공개 범위</label>
      <div class="radio-label-input">
        <input
          type="radio"
          name="public-scope"
          value="전체 공개"
          id="ps-public"
          v-model="fields.publicScope.value"
        />
        <label for="ps-public">전체 공개</label>
      </div>
      <div class="radio-label-input">
        <input
          type="radio"
          name="public-scope"
          value="비공개"
          id="ps-private"
          v-model="fields.publicScope.value"
        />
        <label for="ps-private">비공개</label>
      </div>
    </div>
    <hr />
    <h3>스터디원</h3>
    <div class="form-field">
      <label for="max-count">모집 정원</label>
      <input
        type="number"
        id="max-count"
        min="1"
        v-model="fields.maxCount.value"
      />
    </div>
    <div class="form-field">
      <label>모집 상태</label>
      <div class="radio-label-input">
        <input
          type="radio"
          name="recruitment-state"
          value="모집 중"
          id="rs-doing"
          v-model="fields.recruitmentState.value"
        />
        <label for="rs-doing">모집 중</label>
      </div>
      <div class="radio-label-input">
        <input
          type="radio"
          name="recruitment-state"
          value="모집 마감"
          id="ps-done"
          v-model="fields.recruitmentState.value"
        />
        <label for="ps-done">모집 마감</label>
      </div>
    </div>
    <SubmitButton :disabled="!canEdit" @click="handleSubmit">
      확인
    </SubmitButton>
  </div>
</template>

<script>
import { computed, reactive, ref } from "vue"
import { useStore } from "vuex"
import { cityList } from "@/libs/data"
import SelectFormField from "@/components/common/formField/SelectFormField.vue"
import SubmitButton from "@/components/common/SubmitButton.vue"

export default {
  name: "StudyForm",
  components: { SelectFormField, SubmitButton },
  setup() {
    const store = useStore()
    const fields = reactive({
      coverpic: {
        id: "",
        url: "",
        name: "",
      },
      name: {
        value: "",
      },
      subject: {
        value: "",
      },
      schedule: {
        value: "",
      },
      projectProgressState: {
        value: "준비 중",
      },
      city: {
        value: "서울",
        label: "지역",
        placeholder: "지역을 선택하세요",
        options: cityList,
      },
      bio: {
        value: "",
      },
      publicScope: {
        value: "전체 공개",
      },
      maxCount: {
        value: 1,
      },
      recruitmentState: {
        value: "모집 중",
      },
    })

    const subjectList = ref([])

    const canEdit = computed(() => {
      return (
        fields.coverpic.id &&
        fields.name.value &&
        subjectList.value.length &&
        fields.schedule.value
      )
    })

    const fileInputEl = ref(null)

    const handleClickAddPhotoBtn = () => {
      fileInputEl.value.click()
    }

    const handleChangeFileInput = async (e) => {
      const files = e.target.files || e.dataTransfer.files
      if (!files.length) {
        fields.coverpic.id = ""
        return
      }
      const formData = new FormData()
      formData.append("file", files[0])
      const { id, fileDownloadUri, fileName } = await store.dispatch(
        "file/uploadFile",
        formData
      )
      fields.coverpic.id = id
      fields.coverpic.url = fileDownloadUri
      fields.coverpic.name = fileName
    }

    const handleAddSubject = () => {
      if (
        subjectList.value &&
        !subjectList.value.includes(fields.subject.value)
      ) {
        subjectList.value.push(fields.subject.value)
      }
      fields.subject.value = ""
    }

    const handleRemoveSubject = (subject) => {
      subjectList.value = subjectList.value.filter((s) => s !== subject)
    }

    const handleSubmit = () => {
      const data = {
        uuid: fields.coverpic.id,
        name: fields.name.value,
        subjects: [...subjectList.value],
        schedule: fields.schedule.value,
        projectProgressState: fields.projectProgressState.value,
        city: fields.city.value,
        bio: fields.bio.value,
        publicScope: fields.publicScope.value,
        maxCount: fields.maxCount.value,
        recruitmentState: fields.recruitmentState.value,
      }
      console.log(data)
    }

    return {
      fields,
      cityList,
      subjectList,
      fileInputEl,
      canEdit,
      handleClickAddPhotoBtn,
      handleChangeFileInput,
      handleAddSubject,
      handleRemoveSubject,
      handleSubmit,
    }
  },
}
</script>

<style lang="scss" scoped>
.img-wrapper {
  @apply relative h-24 bg-pink-100 rounded border border-gray-400 overflow-hidden;

  img {
    @apply w-full h-full object-cover;
  }

  .add-photo-btn {
    background: rgba(255, 255, 255, 0.7);
    @apply absolute inset-0 w-full h-full flex gap-1 items-center justify-center cursor-pointer;

    &.hide {
      @apply hidden;
    }

    .material-icons {
      @apply text-gray-700;
    }
    .label {
      @apply text-gray-700;
    }
  }

  &:hover {
    .add-photo-btn {
      @apply flex;
    }
  }
}

.subject-list {
  @apply border border-gray-400 rounded p-4 flex gap-2 flex-wrap;

  .default {
    @apply text-sm font-medium text-gray-400;
  }

  .subject-list-item {
    @apply py-2 px-3 bg-blue-100 rounded flex items-center transition-all;

    &:hover {
      @apply bg-red-100;
    }

    .label {
      @apply text-sm font-medium;
    }

    .material-icons {
      font-size: 1.125rem;
    }
  }
}

.radio-label-input {
  @apply flex items-center gap-2 place-self-start;

  label {
    @apply cursor-pointer text-base;
  }
}
</style>
