<template>
  <form class="form" @submit.prevent="handleSubmit">
    <div class="fields">
      <InputFormField
        v-for="field in formFields"
        :key="field.key"
        :field="field"
        :formFields="formFields"
        v-model="field.value"
        @update:errors="handleUpdateErrors"
      />
      <SelectFormField :field="cityField" v-model="cityField.value" />
    </div>
    <SubmitButton :disabled="!canSubmit">다음 단계로</SubmitButton>
  </form>
</template>

<script>
import { computed, onMounted, ref } from "vue"
import {
  confirmPasswordValidator,
  emailValidator,
  nameValidator,
  passwordValidator,
  requiredValidator,
} from "@/libs/validator"
import { cityList } from "@/libs/data"
import InputFormField from "@/components/common/InputFormField.vue"
import SubmitButton from "@/components/common/SubmitButton.vue"
import SelectFormField from "@/components/common/SelectFormField.vue"
import { useStore } from "vuex"
export default {
  name: "SignupStepOne",
  components: { InputFormField, SubmitButton, SelectFormField },
  emits: ["update:step"],
  setup(_, { emit }) {
    const store = useStore()
    const formFields = ref({
      email: {
        key: "email",
        label: "이메일",
        type: "string",
        value: store.getters["auth/getEmail"],
        placeholder: "이메일을 입력하세요",
        errors: {},
        validators: [emailValidator],
        disabled: true,
      },
      password: {
        key: "password",
        label: "비밀번호",
        type: "password",
        value: "",
        placeholder: "대소문자, 숫자, 특수문자 포함 8자 이상",
        errors: {},
        validators: [passwordValidator],
      },
      confirmPassword: {
        key: "confirmPassword",
        label: "비밀번호 확인",
        type: "password",
        value: "",
        placeholder: "동일한 비밀번호를 입력하세요",
        errors: {},
        validators: [confirmPasswordValidator],
      },
      nickname: {
        key: "nickname",
        label: "닉네임",
        type: "string",
        value: "",
        placeholder: "닉네임을 입력하세요",
        errors: {},
        validators: [requiredValidator],
      },
      name: {
        key: "name",
        label: "이름",
        type: "string",
        value: "",
        placeholder: "이름을 입력하세요",
        errors: {},
        validators: [nameValidator],
      },
    })
    const cityField = ref({
      label: "지역",
      value: "서울",
      placeholder: "지역을 선택하세요",
      options: cityList,
    })
    const loading = ref(false)

    const isAllfieldsFilled = computed(() => {
      return (
        Object.values(formFields.value).every((f) => f.value) &&
        cityField.value.value
      )
    })

    const isAllfieldsValid = computed(() =>
      Object.values(formFields.value).every(
        (f) => Object.keys(f.errors).length === 0
      )
    )

    const canSubmit = computed(
      () => isAllfieldsFilled.value && isAllfieldsValid.value
    )

    const handleChange = (e) => {
      console.log(e.target.value)
    }

    const handleUpdateErrors = (validateRes) => {
      const { fieldKey, status, type } = validateRes
      if (status) {
        delete formFields.value[fieldKey].errors[type]
      } else {
        formFields.value[fieldKey].errors[type] = validateRes.message
      }
    }

    const handleSubmit = async () => {
      const formData = {
        email: formFields.value.email.value,
        password: formFields.value.password.value,
        nickname: formFields.value.nickname.value,
        name: formFields.value.name.value,
        city: cityField.value.value,
      }
      loading.value = true
      await store.dispatch("auth/submitStepOne", formData)
      loading.value = false
      emit("update:step", 2)
    }

    onMounted(() => {
      if (store.getters["auth/getStep"] === 2) {
        const { email, password, nickname, name, city } =
          store.getters["auth/getSIgnupFormData"]
        formFields.value.email.value = email
        formFields.value.password.value = password
        formFields.value.confirmPassword.value = password
        formFields.value.nickname.value = nickname
        formFields.value.name.value = name
        cityField.value.value = city
      }
    })

    return {
      formFields,
      cityField,
      canSubmit,
      handleChange,
      handleUpdateErrors,
      handleSubmit,
    }
  },
}
</script>

<style lang="scss" scoped>
.form {
  @apply grid gap-6;
  .fields {
    @apply grid gap-4;
  }
}
</style>
