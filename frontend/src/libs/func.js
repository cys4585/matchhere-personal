import {
  emailValidator,
  nameValidator,
  passwordValidator,
  requiredValidator,
} from "./validator2"

export class InputFormFieldMaker {
  constructor(key, value = "") {
    this.key = key
    this.value = value
    this.errors = {}
    this.disabled = false

    switch (key) {
      case "email": {
        this.label = "이메일"
        this.type = "string"
        this.placeholder = "이메일을 입력하세요"
        this.validators = [emailValidator]
        break
      }
      case "password": {
        this.label = "비밀번호"
        this.type = "password"
        this.placeholder = "대소문자, 숫자, 특수문자 포함 8자 이상"
        this.errors = {}
        this.validators = [passwordValidator]
        break
      }
      case "confirmPassword": {
        this.label = "비밀번호 확인"
        this.type = "password"
        this.placeholder = "동일한 비밀번호를 입력하세요"
        this.errors = {}
        this.validators = []
        break
      }
      case "authCode": {
        this.label = "인증코드"
        this.type = "string"
        this.placeholder = "인증코드를 입력하세요"
        this.errors = {}
        this.validators = [requiredValidator]
        break
      }
      case "nickname": {
        this.label = "닉네임"
        this.type = "string"
        this.placeholder = "닉네임을 입력하세요"
        this.errors = {}
        this.validators = [requiredValidator]
        break
      }
      case "name": {
        this.label = "이름"
        this.type = "string"
        this.placeholder = "이름을 입력하세요"
        this.errors = {}
        this.validators = [nameValidator]
        break
      }
    }
  }

  updateErrors(res) {
    const { type, status } = res
    if (status) {
      delete this.errors[type]
    } else {
      this.errors[type] = res.message
    }
  }

  validate() {
    return this.validators.every((v) => {
      const res = v(this.value)
      this.updateErrors(res)
      return res.status
    })
  }
}

export class EmailFormFieldMaker {
  constructor() {
    this.key = "email"
    this.label = "이메일"
    this.type = "string"
    this.value = ""
    this.disabled = false
    this.placeholder = "이메일을 입력하세요"
    this.errors = {}
    this.validators = [emailValidator]
    this.buttonDisabled = true
    this.buttonLabel = "메일 인증"
  }
  validate() {
    return this.validators.every((validator) => {
      const validateRes = validator(this.value, this.key)
      this.updateErrors(validateRes)
      return validateRes.status
    })
  }
  updateInputDisabled(status) {
    this.disabled = status
  }
  updateButtonLabel(label) {
    this.buttonLabel = label
  }
  updateButtonDisabled(status) {
    this.buttonDisabled = status
  }
  updateErrors(validateRes) {
    const { status, type } = validateRes
    if (status) {
      delete this.errors[type]
    } else {
      this.errors[type] = validateRes.message
    }
  }
}

export class AuthCodeFormFieldMaker {
  constructor() {
    this.key = "authCode"
    this.label = "인증번호 입력"
    this.type = "string"
    this.value = ""
    this.placeholder = "메일로 전송된 인증번호를 입력하세요"
    this.errors = {}
    this.validators = [requiredValidator]
    this.buttonDisabled = true
    this.buttonLabel = "확인"
  }
  validate() {
    return this.validators.every((validator) => {
      const validateRes = validator(this.value, this.key)
      this.updateErrors(validateRes)
      return validateRes.status
    })
  }
  updateButtonLabel(label) {
    this.buttonLabel = label
  }
  updateButtonDisabled(status) {
    this.buttonDisabled = status
  }
  updateErrors(validateRes) {
    const { status, type } = validateRes
    if (status) {
      delete this.errors[type]
    } else {
      this.errors[type] = validateRes.message
    }
  }
}
