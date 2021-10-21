<template>
  <Modal @closeModal="handleCloseModal">
    <h4 class="mb-4">경력 추가</h4>
    <div class="grid gap-4 mb-10">
      <InputFormField :field="companyField" v-model="companyField.value" />
      <InputFormField :field="roleField" v-model="roleField.value" />
      <div class="field grid gap-2">
        <label class="text-sm font-medium">{{ startDate.label }}</label>
        <input
          class="py-2 px-4 border border-gray-400 rounded"
          type="date"
          name="startDate"
          :disabled="startDate.disabled"
          v-model="startDate.value"
        />
      </div>
      <div class="field grid gap-2">
        <label class="text-sm font-medium">{{ endDate.label }}</label>
        <input
          class="py-2 px-4 border border-gray-400 rounded"
          type="date"
          name="endDate"
          :disabled="endDate.disabled"
          v-model="endDate.value"
        />
        <div class="flex gap-2 items-center">
          <input type="checkbox" name="is_incumbent" id="is_incumbent" />
          <label for="is_incumbent">재직중</label>
        </div>
      </div>
      <div class="field grid gap-2">
        <label>설명</label>
        <textarea
          class="border border-gray-400 rounded outline-none p-4 resize-none"
          name="description"
          v-model="description"
        ></textarea>
      </div>
    </div>
    <div class="buttons flex gap-4 w-full justify-center">
      <button
        class="rounded py-2 px-6 text-sm font-bold bg-blue-500 text-white"
      >
        확인
      </button>
      <button
        class="
          rounded
          py-2
          px-6
          text-sm
          font-bold
          text-gray-600
          border border-gray-400
        "
        @click="handleCloseModal"
      >
        취소
      </button>
    </div>
  </Modal>
</template>

<script>
import Modal from "@/components/common/Modal.vue"
import InputFormField from "@/components/common/formField/InputFormField.vue"
import { reactive, ref } from "@vue/reactivity"
import { InputFormFieldMaker } from "@/libs/func"

export default {
  name: "AddCareerModal",
  components: { Modal, InputFormField },
  emits: ["closeModal"],
  setup(_, { emit }) {
    const companyField = reactive(
      new InputFormFieldMaker(
        "company",
        "",
        false,
        "기업명",
        "string",
        "ex) MatchHere"
      )
    )

    const roleField = reactive(
      new InputFormFieldMaker(
        "role",
        "",
        false,
        "역할",
        "string",
        "ex) PM, 기획, 디자인 등"
      )
    )

    const startDate = reactive({
      value: "",
      label: "입사일",
      disabled: false,
    })

    const endDate = reactive({
      value: "",
      label: "퇴직일",
      disabled: false,
    })

    const description = ref("")

    const handleCloseModal = () => {
      emit("closeModal", "career")
    }

    return {
      companyField,
      roleField,
      startDate,
      endDate,
      description,
      handleCloseModal,
    }
  },
}
</script>

<style lang="scss" scoped></style>
