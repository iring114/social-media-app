<template>
  <div class="register-container">
    <b-card class="register-card">
      <h2 class="text-center mb-4">註冊</h2>
      
      <b-alert v-model="showError" variant="danger" dismissible>
        {{ errorMessage }}
      </b-alert>
      
      <b-form @submit.prevent="handleRegister">
        <b-form-group label="用戶名稱" label-for="username">
          <b-form-input
            id="username"
            v-model="form.userName"
            type="text"
            placeholder="請輸入用戶名稱"
            required
          ></b-form-input>
        </b-form-group>
        
        <b-form-group label="電子郵件" label-for="email">
          <b-form-input
            id="email"
            v-model="form.email"
            type="email"
            placeholder="請輸入電子郵件"
            required
          ></b-form-input>
        </b-form-group>
        
        <b-form-group label="手機號碼" label-for="phone-number">
          <b-form-input
            id="phone-number"
            v-model="form.phoneNumber"
            type="text"
            placeholder="請輸入手機號碼"
            required
          ></b-form-input>
        </b-form-group>
        
        <b-form-group label="密碼" label-for="password">
          <b-form-input
            id="password"
            v-model="form.password"
            type="password"
            placeholder="請輸入密碼"
            required
          ></b-form-input>
        </b-form-group>
        
        <b-form-group label="個人簡介" label-for="biography">
          <b-form-textarea
            id="biography"
            v-model="form.biography"
            placeholder="請輸入個人簡介"
            rows="3"
          ></b-form-textarea>
        </b-form-group>
        
        <b-button type="submit" variant="primary" class="w-100" :disabled="loading">
          <b-spinner small v-if="loading"></b-spinner>
          {{ loading ? '註冊中...' : '註冊' }}
        </b-button>
      </b-form>
      
      <div class="text-center mt-3">
        <p>已有帳號？ <router-link to="/login">立即登入</router-link></p>
      </div>
    </b-card>
  </div>
</template>

<script>
import { mapActions } from 'vuex'

export default {
  name: 'RegisterPage',
  data() {
    return {
      form: {
        userName: '',
        email: '',
        phoneNumber: '',
        password: '',
        biography: ''
      },
      loading: false,
      showError: false,
      errorMessage: ''
    }
  },
  methods: {
    ...mapActions(['register', 'login']),
    async handleRegister() {
      this.loading = true
      this.showError = false
      
      try {
        // 註冊用戶
        await this.register(this.form)
        
        // 註冊成功後自動登入
        await this.login({
          phoneNumber: this.form.phoneNumber,
          password: this.form.password
        })
        
        // 導航到首頁
        this.$router.push('/')
        
        this.$bvToast.toast('註冊成功！', {
          title: '成功',
          variant: 'success',
          solid: true
        })
      } catch (error) {
        console.error('註冊失敗:', error)
        this.errorMessage = error.response?.data?.message || '註冊失敗，請檢查您的輸入'
        this.showError = true
      } finally {
        this.loading = false
      }
    }
  }
}
</script>

<style scoped>
.register-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 70vh;
  margin: 2rem 0;
}

.register-card {
  width: 100%;
  max-width: 500px;
}
</style>