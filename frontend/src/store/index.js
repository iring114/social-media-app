import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    token: localStorage.getItem('token') || '',
    user: JSON.parse(localStorage.getItem('user')) || null,
    posts: [],
    userPosts: []
  },
  getters: {
    isAuthenticated: state => !!state.token,
    currentUser: state => state.user,
    allPosts: state => state.posts,
    userPosts: state => state.userPosts
  },
  mutations: {
    SET_TOKEN(state, token) {
      state.token = token
    },
    SET_USER(state, user) {
      state.user = user
    },
    SET_POSTS(state, posts) {
      state.posts = posts
    },
    SET_USER_POSTS(state, posts) {
      state.userPosts = posts
    },
    ADD_POST(state, post) {
      state.posts.unshift(post)
      if (post.userId === state.user.id) {
        state.userPosts.unshift(post)
      }
    },
    UPDATE_POST(state, updatedPost) {
      const index = state.posts.findIndex(post => post.id === updatedPost.id)
      if (index !== -1) {
        state.posts.splice(index, 1, updatedPost)
      }
      
      const userIndex = state.userPosts.findIndex(post => post.id === updatedPost.id)
      if (userIndex !== -1) {
        state.userPosts.splice(userIndex, 1, updatedPost)
      }
    },
    DELETE_POST(state, postId) {
      state.posts = state.posts.filter(post => post.id !== postId)
      state.userPosts = state.userPosts.filter(post => post.id !== postId)
    },
    LOGOUT(state) {
      state.token = ''
      state.user = null
      state.posts = []
      state.userPosts = []
    }
  },
  actions: {
    // 登入
    async login({ commit }, credentials) {
      const response = await axios.post('/api/auth/login', credentials)
      const { token, user } = response.data.data
      
      localStorage.setItem('token', token)
      localStorage.setItem('user', JSON.stringify(user))
      
      // 設置axios默認請求頭
      axios.defaults.headers.common['Authorization'] = `Bearer ${token}`
      
      commit('SET_TOKEN', token)
      commit('SET_USER', user)
      
      return response
    },
    
    // 註冊
    async register(_, userData) {
      const response = await axios.post('/api/auth/register', userData)
      return response
    },
    
    // 登出
    logout({ commit }) {
      localStorage.removeItem('token')
      localStorage.removeItem('user')
      delete axios.defaults.headers.common['Authorization']
      commit('LOGOUT')
    },
    
    // 獲取所有貼文
    async fetchPosts({ commit }) {
      const response = await axios.get('/api/posts')
      commit('SET_POSTS', response.data.data)
      return response
    },
    
    // 獲取用戶貼文
    async fetchUserPosts({ commit, state }) {
      const response = await axios.get(`/api/posts/user/${state.user.id}`)
      commit('SET_USER_POSTS', response.data.data)
      return response
    },
    
    // 創建貼文
    async createPost({ commit }, postData) {
      const response = await axios.post('/api/posts', postData)
      commit('ADD_POST', response.data.data)
      return response
    },
    
    // 更新貼文
    async updatePost({ commit }, { postId, postData }) {
      const response = await axios.put(`/api/posts/${postId}`, postData)
      commit('UPDATE_POST', response.data.data)
      return response
    },
    
    // 刪除貼文
    async deletePost({ commit }, postId) {
      await axios.delete(`/api/posts/${postId}`)
      commit('DELETE_POST', postId)
    }
  }
})