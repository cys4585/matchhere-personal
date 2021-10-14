<template>
  <form class="form" @submit.prevent="handleSubmit">
    <div class="fields">
      <div>
        <SelectFormField
          :field="positionField"
          v-model="positionField.value"
          @update:modelValue="handlePositionUpdate"
        />
        <div class="dplist">
          <DetailPositionButton
            v-for="dp in displayedDPList"
            :key="dp.id"
            :dp="dp"
            @onUpdateStatus="handleDPClick"
          />
        </div>
      </div>
      <TeckStackField />
    </div>
    <SubmitButton>회원가입</SubmitButton>
  </form>
</template>

<script>
import { computed, ref } from "vue"
import SubmitButton from "@/components/common/SubmitButton.vue"
import SelectFormField from "@/components/common/SelectFormField.vue"
import DetailPositionButton from "@/components/auth/DetailPositionButton.vue"
import TeckStackField from "@/components/auth/TeckStackField.vue"
import { detailPositionList } from "@/libs/data"

export default {
  name: "RegisterStepTwo",
  components: {
    SubmitButton,
    SelectFormField,
    DetailPositionButton,
    TeckStackField,
  },
  emits: ["update:step"],
  setup() {
    const dpList = ref({ ...detailPositionList })
    const positionField = ref({
      label: "포지션",
      value: "개발자",
      placeholder: "포지션을 선택하세요",
      options: ["개발자", "기획자", "디자이너"],
    })

    const positionKey = computed(() => {
      if (positionField.value.value === "개발자") {
        return "developer"
      } else if (positionField.value.value === "기획자") {
        return "planner"
      } else {
        return "designer"
      }
    })
    const detailPositionListField = computed(() =>
      dpList.value[positionKey.value]
        .filter((dp) => dp.selected)
        .map((dp) => dp.value)
    )

    const displayedDPList = computed(() => {
      switch (positionField.value.value) {
        case "개발자": {
          return dpList.value.developer
        }
        case "기획자": {
          return dpList.value.planner
        }
        default: {
          return dpList.value.designer
        }
      }
    })

    const handlePositionUpdate = () => {
      Object.keys(dpList.value).forEach((key) => {
        dpList.value[key].forEach((dp) => (dp.selected = false))
      })
    }

    const handleDPClick = (id) => {
      dpList.value[positionKey.value].forEach((dp) => {
        if (dp.id === id) {
          dp.selected = !dp.selected
        }
      })
    }

    return {
      positionField,
      positionKey,
      displayedDPList,
      detailPositionListField,
      handleDPClick,
      handlePositionUpdate,
    }
  },
}
</script>

<style lang="scss" scoped>
.form {
  @apply grid gap-6;
  .fields {
    @apply grid gap-4;

    .dplist {
      @apply flex gap-2 flex-wrap;

      .dp {
        @apply py-2 px-4 border-gray-400 rounded bg-gray-200 text-gray-600 font-medium transition-colors;

        &.selected {
          @apply bg-blue-500 border-0 text-white font-bold;
        }

        &:hover {
          @apply bg-blue-200;
        }
      }
    }
  }
}
</style>
