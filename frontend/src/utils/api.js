import axios from 'axios'
import store from '../store'
import router from '../router'

// 創建axios實例
const api = axios.create({
  baseURL: '/api',
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json'
  }
})

// 請求攔截器
api.interceptors.request.use(
  config => {
    const token = localStorage.getItem('token')
    if (token) {
      config.headers['Authorization'] = `Bearer ${token}`
    }
    return config
  },
  error => {
    return Promise.reject(error)
  }
)

// 響應攔截器
api.interceptors.response.use(
  response => {
    return response
  },
  error => {
    if (error.response) {
      const { status } = error.response
      
      // 處理401未授權錯誤
      if (status === 401) {
        // 清除token和用戶信息
        store.dispatch('logout')
        
        // 重定向到登入頁面
        router.push({
          path: '/login',
          query: { redirect: router.currentRoute.fullPath }
        })
      }
    }
    return Promise.reject(error)
  }
)

export default api