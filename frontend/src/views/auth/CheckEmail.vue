<template>
  <header class="page-header">
    <h2>회원가입</h2>
    <div>
      <p>MatchHere!</p>
      <p>여러분이 원하는 스터디, 프로젝트를 찾아보세요!</p>
    </div>
  </header>
  <section class="email-section">
    <form class="form" @submit.prevent="handleSubmit">
      <InputFormField
        :field="formFields.email"
        :formFields="formFields"
        v-model="formFields.email.value"
        @update:errors="handleUpdateErrors"
      />
      <SubmitButton :disabled="!canSubmit">
        {{ submitButtonLabel }}
      </SubmitButton>
    </form>
    <router-link :to="{ name: 'Login' }">로그인</router-link>
  </section>
</template>

<script>
import InputFormField from "@/components/common/InputFormField.vue"
import SubmitButton from "@/components/common/SubmitButton.vue"
import { computed, ref } from "vue"
import { emailValidator } from "@/libs/validator"
import { useStore } from "vuex"
import { useRouter } from "vue-router"

export default {
  name: "CheckEmail",
  components: { InputFormField, SubmitButton },
  setup() {
    const store = useStore()
    const router = useRouter()
    const formFields = ref({
      email: {
        key: "email",
        label: "이메일",
        type: "string",
        value: "",
        placeholder: "이메일을 입력하세요",
        errors: {},
        validators: [emailValidator],
      },
    })
    const sendEmailLoading = ref(false)
    const submitButtonLabel = computed(() => {
      if (sendEmailLoading.value) {
        return "메일을 보내고 있는 중입니다"
      } else {
        return "인증 메일 전송하기"
      }
    })

    const canSubmit = computed(() => {
      const { value, errors } = formFields.value.email
      return (
        value.length !== 0 &&
        Object.keys(errors).length === 0 &&
        !sendEmailLoading.value
      )
    })

    const handleUpdateErrors = (validateRes) => {
      const { status, type } = validateRes
      if (status) {
        delete formFields.value.email.errors[type]
      } else {
        const { message } = validateRes
        formFields.value.email.errors[type] = message
      }
    }

    const handleSubmit = async () => {
      const email = formFields.value.email.value
      sendEmailLoading.value = true
      await store.dispatch("auth/checkEmail", email)
      router.push({ name: "AuthEmail" })
    }

    return {
      canSubmit,
      formFields,
      handleUpdateErrors,
      handleSubmit,
      submitButtonLabel,
    }
  },
}
</script>

<style lang="scss" scoped>
.email-section {
  @apply grid gap-2;

  .form {
    @apply grid gap-6;
  }

  a {
    @apply place-self-center p-1 text-sm font-medium;
  }
}
</style>
