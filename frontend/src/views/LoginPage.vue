<template>
  <div class="login-container">
    <b-card class="login-card">
      <h2 class="text-center mb-4">登入</h2>
      
      <b-alert v-model="showError" variant="danger" dismissible>
        {{ errorMessage }}
      </b-alert>
      
      <b-form @submit.prevent="handleLogin">
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
        
        <b-button type="submit" variant="primary" class="w-100" :disabled="loading">
          <b-spinner small v-if="loading"></b-spinner>
          {{ loading ? '登入中...' : '登入' }}
        </b-button>
      </b-form>
      
      <div class="text-center mt-3">
        <p>還沒有帳號？ <router-link to="/register">立即註冊</router-link></p>
      </div>
    </b-card>
  </div>
</template>

<script>
import { mapActions } from 'vuex'

export default {
  name: 'LoginPage',
  data() {
    return {
      form: {
        phoneNumber: '',
        password: ''
      },
      loading: false,
      showError: false,
      errorMessage: ''
    }
  },
  methods: {
    ...mapActions(['login']),
    async handleLogin() {
      this.loading = true
      this.showError = false
      
      try {
        await this.login(this.form)
        
        // 檢查是否有重定向URL
        const redirectPath = this.$route.query.redirect || '/'
        this.$router.push(redirectPath)
      } catch (error) {
        console.error('登入失敗:', error)
        this.errorMessage = error.response?.data?.message || '登入失敗，請檢查您的憑證'
        this.showError = true
      } finally {
        this.loading = false
      }
    }
  }
}
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 70vh;
}

.login-card {
  width: 100%;
  max-width: 400px;
}
</style>