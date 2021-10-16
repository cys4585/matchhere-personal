<template>
  <header class="page-header">
    <h2>비밀번호 찾기</h2>
    <div>
      <p>인증메일이 발송되었습니다.</p>
      <p>이메일을 확인하고 인증코드를 입력하세요.</p>
    </div>
  </header>
  <section class="form-section">
    <div class="form">
      <div class="fields">
        <InputFormField
          v-for="field in formFields"
          :key="field.key"
          :field="field"
          :formFields="formFields"
          v-model="field.value"
          @update:errors="handleUpdateErrors"
        />
      </div>
      <SubmitButton @click="handleSubmit" :disabled="!canSubmit">
        {{ step === "sendEmail" ? "인증 메일 전송하기" : "이메일 인증하기" }}
      </SubmitButton>
    </div>
    <router-link :to="{ name: 'Login' }">로그인</router-link>
  </section>
  <section class="error-section">
    <h5>인증메일을 확인할 수 없나요?</h5>
    <div>
      <p>1. 스팸메일함 확인</p>
      <button @click="handleSendEmail">2. 인증메일 다시 보내기</button>
    </div>
  </section>
</template>

<script>
import { computed, ref } from "vue"
import { useStore } from "vuex"
import { emailValidator, requiredValidator } from "@/libs/validator"
import InputFormField from "@/components/common/InputFormField.vue"
import SubmitButton from "@/components/common/SubmitButton.vue"

export default {
  name: "FindPWStepOne",
  components: { InputFormField, SubmitButton },
  emits: ["update:step"],
  setup(_, { emit }) {
    const store = useStore()
    const loading = ref(false)
    const step = ref("sendEmail")
    const formFields = ref({
      email: {
        key: "email",
        label: "이메일",
        type: "email",
        value: "",
        placeholder: "이메일을 입력하세요",
        errors: {},
        validators: [emailValidator],
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

    const handleSendEmail = async () => {
      // TODO: 이메일 전송
      loading.value = true
      const email = formFields.value.email.value
      await store.dispatch("auth/sendEmailForFindPassword", email)
      formFields.value.email.disabled = true
      formFields.value.authCode = {
        key: "authCode",
        label: "인증코드",
        type: "string",
        value: "",
        placeholder: "인증코드를 입력하세요",
        errors: {},
        validators: [requiredValidator],
      }
      step.value = "confirmAuthCode"
      loading.value = false
    }

    const handleSubmitAuthCode = async () => {
      loading.value = true
      const authCode = formFields.value.authCode.value
      await store.dispatch("auth/confirmAuthCodeForFindPassword", authCode)
      emit("update:step")
      loading.value = false
    }

    const handleSubmit = () => {
      switch (step.value) {
        case "sendEmail": {
          handleSendEmail()
          break
        }
        case "confirmAuthCode": {
          handleSubmitAuthCode()
          break
        }
      }
    }

    return {
      step,
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
  @apply grid gap-2 mb-10;

  .form {
    @apply grid gap-6 w-full;

    .fields {
      @apply grid gap-4;
    }
  }
  a {
    @apply text-sm font-medium place-self-center;
  }
}

.error-section {
  @apply grid gap-2;

  h5 {
    @apply font-bold;
  }
  div {
    @apply grid gap-1 place-items-start;

    p {
      @apply text-gray-600;
    }

    button {
      @apply text-blue-500 font-bold;
    }
  }
}
</style>
