<template>
  <header class="page-header">
    <h2>회원가입</h2>
  </header>
  <section class="code-section">
    <header>
      <h4>{{ email }}</h4>

      <div>
        <p>인증메일이 발송되었습니다.</p>
        <p>이메일을 확인하고 가입절차를 진행해주세요.</p>
      </div>
    </header>

    <div class="form-link">
      <form class="form" @submit.prevent="handleSubmit">
        <InputFormField
          :field="formFields.authCode"
          :formFields="formFields"
          v-model="formFields.authCode.value"
          @update:errors="handleUpdateErrors"
        />
        <SubmitButton :disabled="!canSubmit">
          {{ submitButtonLabel }}
        </SubmitButton>
      </form>
      <router-link :to="{ name: 'CheckEmail' }"> 뒤로가기 </router-link>
    </div>
  </section>
  <section class="error-section">
    <h5>인증메일을 확인할 수 없나요?</h5>
    <div>
      <p>1. 스팸메일함 확인</p>
      <button @click="handleResendEmail">2. 인증메일 다시 보내기</button>
    </div>
  </section>
</template>

<script>
import InputFormField from "@/components/common/InputFormField.vue"
import SubmitButton from "@/components/common/SubmitButton.vue"
import { computed, ref } from "vue"
import { requiredValidator } from "@/libs/validator"
import { useStore } from "vuex"
import { useRouter } from "vue-router"

export default {
  name: "AuthEmail",
  components: { InputFormField, SubmitButton },
  setup() {
    const store = useStore()
    const router = useRouter()
    const formFields = ref({
      authCode: {
        key: "code",
        label: "인증코드",
        type: "string",
        value: "",
        placeholder: "인증코드를 입력하세요",
        errors: {},
        validators: [requiredValidator],
      },
    })
    const email = computed(() => store.state.auth.email)
    const loading = ref(false)
    const submitButtonLabel = computed(() => {
      if (loading.value) {
        return "인증 중..."
      } else {
        return "이메일 인증하기"
      }
    })

    const canSubmit = computed(() => {
      const { value, errors } = formFields.value.authCode
      return (
        value.length !== 0 && Object.keys(errors).length === 0 && !loading.value
      )
    })

    const handleUpdateErrors = (validateRes) => {
      const { status, type } = validateRes
      if (status) {
        delete formFields.value.authCode.errors[type]
      } else {
        const { message } = validateRes
        formFields.value.authCode.errors[type] = message
      }
    }

    const handleResendEmail = async () => {
      await store.dispatch("auth/checkEmail", email.value)
    }

    const handleSubmit = async () => {
      const authCode = formFields.value.authCode.value
      loading.value = true
      await store.dispatch("auth/authEmail", authCode)
      router.push({ name: "Signup" })
    }

    return {
      canSubmit,
      email,
      formFields,
      handleUpdateErrors,
      handleSubmit,
      handleResendEmail,
      submitButtonLabel,
    }
  },
}
</script>

<style lang="scss" scoped>
.code-section {
  @apply grid gap-4 mb-10;

  header {
    @apply grid gap-4;

    h4 {
      @apply text-lg font-bold;
    }
  }

  .form-link {
    @apply grid gap-2;

    .form {
      @apply grid gap-6;
    }

    a {
      @apply place-self-center p-1 text-sm font-medium;
    }
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
