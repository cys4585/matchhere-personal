export const requiredValidator = (value, fieldKey) => {
  if (typeof value === "string") {
    if (value.length === 0) {
      return {
        fieldKey,
        status: false,
        type: "required",
        message: "필수 입력 요소입니다",
      }
    }
    return {
      fieldKey,
      status: true,
      type: "required",
    }
  } else {
    alert("requiredValidator에 올바른 타입을 입력하세요")
  }
}

export const emailValidator = (value, fieldKey) => {
  if (typeof value !== "string") {
    alert("emailValidator 올바른 타입을 입력하세요")
    return
  }
  const regex = /([\d\w-_]+)[@]([\w]+)[.]([\w]{1,3})/
  if (!regex.test(value)) {
    return {
      fieldKey,
      status: false,
      type: "invalidEmail",
      message: "이메일을 다시 확인하세요",
    }
  }
  return {
    fieldKey,
    status: true,
    type: "invalidEmail",
  }
}

export const passwordValidator = (value, fieldKey) => {
  if (typeof value !== "string") {
    alert("passwordValidator에 올바른 타입을 입력하세요")
    return
  }
  const regex =
    /^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{10,}$/
  if (!regex.test(value)) {
    return {
      fieldKey,
      status: false,
      type: "invalidPassword",
      message:
        "대소문자, 숫자, 특수문자로 구성된 10자리 이상의 문자로 입력하세요",
    }
  }
  return {
    fieldKey,
    status: true,
    type: "invalidPassword",
  }
}
