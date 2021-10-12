<template>
  <div class="container">
    <section class="login-section">
      <header>
        <h2>로그인</h2>
        <p>여러분이 원하는 스터디와 프로젝트를 찾아보세요!</p>
      </header>
      <div class="login-container">
        <div class="form">
          <div class="fields">
            <InputFormField
              v-for="(field, key) in formFields"
              :key="key"
              :field="field"
              v-model="field.value"
              @update:errors="handleUpdateErrors"
            />
          </div>
          <SubmitButton :disabled="!canSubmit">로그인</SubmitButton>
        </div>
        <div class="links">
          <router-link to="/" class="link">회원가입</router-link>
          <div class="divider"></div>
          <router-link to="/" class="link">비밀번호 찾기</router-link>
        </div>
      </div>
    </section>
  </div>
</template>

<script>
import { computed, ref } from "vue"
import InputFormField from "@/components/common/InputFormField.vue"
import SubmitButton from "@/components/common/SubmitButton.vue"

import { emailValidator, passwordValidator } from "@/libs/validator"

export default {
  name: "Login",
  components: { InputFormField, SubmitButton },
  setup() {
    const formFields = ref({
      email: {
        label: "email",
        type: "string",
        value: "",
        placeholder: "이메일을 입력하세요",
        errors: {},
        validators: [emailValidator],
      },
      password: {
        label: "password",
        type: "password",
        value: "",
        placeholder: "비밀번호를 입력하세요",
        errors: {},
        validators: [passwordValidator],
      },
    })

    const isAllfieldsFill = computed(() => {
      return Object.values(formFields.value).every((f) => f.value.length)
    })

    const isAllfieldsValid = computed(() => {
      return Object.values(formFields.value).every(
        (f) => Object.keys(f.errors).length === 0
      )
    })

    const canSubmit = computed(
      () => isAllfieldsFill.value && isAllfieldsValid.value
    )

    const handleUpdateErrors = (validateRes) => {
      const { fieldKey, status, type } = validateRes
      if (status) {
        delete formFields.value[fieldKey].errors[type]
      } else {
        formFields.value[fieldKey].errors[type] = validateRes.message
      }
    }

    return { formFields, handleUpdateErrors, canSubmit }
  },
}
</script>

<style lang="scss" scoped>
.login-section {
  @apply py-10 px-4 grid gap-10;

  header {
    @apply grid gap-4;

    h2 {
      @apply font-bold text-2xl;
    }
  }
  .login-container {
    @apply flex flex-col gap-2 items-center;

    .form {
      @apply w-full grid gap-6;

      .fields {
        @apply grid gap-4;
      }
    }

    .links {
      @apply flex gap-2;

      .link {
        @apply text-sm font-medium py-1 px-2;
      }
    }
  }
}
</style>
