<template>
  <header class="page-header">
    <h2>비밀번호 찾기</h2>
    <div>
      <p>비밀번호를 초기화합니다.</p>
      <p>사용하실 새 비밀번호를 입력하세요.</p>
    </div>
  </header>
  <section class="form-section">
    <div class="form">
      <div class="fields">
        <InputFormField
          v-for="field in formFields"
          :key="field.key"
          v-model="field.value"
          :field="field"
          :formFields="formFields"
          @update:errors="handleUpdateErrors"
        />
      </div>
      <SubmitButton :disabled="!canSubmit" @click="handleSubmit">
        비밀번호 변경
      </SubmitButton>
    </div>
  </section>
</template>

<script>
import InputFormField from "@/components/common/InputFormField.vue"
import { computed, ref } from "vue"
import { confirmPasswordValidator, passwordValidator } from "@/libs/validator"
import SubmitButton from "@/components/common/SubmitButton.vue"

export default {
  name: "FindPWStepTwo",
  components: { InputFormField, SubmitButton },
  setup() {
    const formFields = ref({
      password: {
        key: "password",
        label: "새 비밀번호",
        type: "password",
        value: "",
        placeholder: "대소문자, 숫자, 특수문자 포함 10자 이상",
        errors: {},
        validators: [passwordValidator],
      },
      confirmPassword: {
        key: "confirmPassword",
        label: "새 비밀번호 확인",
        type: "password",
        value: "",
        placeholder: "대소문자, 숫자, 특수문자 포함 10자 이상",
        errors: {},
        validators: [confirmPasswordValidator],
      },
    })

    const canSubmit = computed(() =>
      Object.values(formFields.value).every(
        (f) => f.value && !Object.keys(f.errors).length
      )
    )

    const handleUpdateErrors = (validateRes) => {
      const { fieldKey, status, type } = validateRes
      if (status) {
        delete formFields.value[fieldKey].errors[type]
      } else {
        const { message } = validateRes
        formFields.value[fieldKey].errors[type] = message
      }
    }

    const handleSubmit = () => {
      console.log(Object.values(formFields.value).map((f) => f.value))
    }

    return {
      formFields,
      canSubmit,
      handleUpdateErrors,
      handleSubmit,
    }
  },
}
</script>

<style lang="scss" scoped>
.form-section {
  .form {
    @apply grid gap-6;

    .fields {
      @apply grid gap-4;
    }
  }
}
</style>
