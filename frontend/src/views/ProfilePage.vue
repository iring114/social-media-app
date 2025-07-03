<template>
  <div>
    <b-card class="mb-4">
      <div v-if="loading" class="text-center">
        <b-spinner variant="primary" label="Loading"></b-spinner>
      </div>
      
      <div v-else>
        <h3 class="card-title">{{ user.userName }}</h3>
        <p class="card-text"><strong>電子郵件:</strong> {{ user.email }}</p>
        <p class="card-text"><strong>手機號碼:</strong> {{ user.phoneNumber }}</p>
        <p class="card-text"><strong>個人簡介:</strong> {{ user.biography || '暫無簡介' }}</p>
      </div>
    </b-card>

    <h3 class="mb-3">我的貼文</h3>
    
    <div v-if="postsLoading" class="text-center my-5">
      <b-spinner variant="primary" label="Loading"></b-spinner>
    </div>
    
    <div v-else-if="userPosts.length === 0" class="text-center my-5">
      <p class="text-muted">您還沒有發布任何貼文</p>
      <b-button variant="primary" to="/">去發布第一篇貼文</b-button>
    </div>
    
    <div v-else>
      <post-card 
        v-for="post in userPosts" 
        :key="post.id" 
        :post="post"
        @edit="editPost"
        @delete="deletePost"
        @comment-added="refreshPosts"
      />
    </div>
    
    <b-modal id="edit-post-modal" title="編輯貼文" hide-footer>
      <post-form 
        v-if="editingPost" 
        :edit-post="editingPost" 
        @update="updatePost" 
        @cancel="cancelEdit"
      />
    </b-modal>
  </div>
</template>

<script>
import { mapGetters, mapActions } from 'vuex'
import PostCard from '@/components/PostCard.vue'
import PostForm from '@/components/PostForm.vue'

export default {
  name: 'ProfilePage',
  components: {
    PostCard,
    PostForm
  },
  data() {
    return {
      loading: false,
      postsLoading: false,
      editingPost: null
    }
  },
  computed: {
    ...mapGetters(['currentUser', 'userPosts']),
    user() {
      return this.currentUser || {}
    }
  },
  created() {
    this.loadUserPosts()
  },
  methods: {
    ...mapActions(['fetchUserPosts', 'updatePost', 'deletePost']),
    async loadUserPosts() {
      this.postsLoading = true
      try {
        await this.fetchUserPosts()
      } catch (error) {
        console.error('獲取用戶貼文失敗:', error)
        this.$bvToast.toast('獲取貼文失敗', {
          title: '錯誤',
          variant: 'danger',
          solid: true
        })
      } finally {
        this.postsLoading = false
      }
    },
    async refreshPosts() {
      await this.fetchUserPosts()
    },
    editPost(post) {
      this.editingPost = post
      this.$bvModal.show('edit-post-modal')
    },
    async updatePost({ postId, postData }) {
      try {
        await this.updatePost({ postId, postData })
        this.$bvModal.hide('edit-post-modal')
        this.editingPost = null
        this.$bvToast.toast('貼文更新成功', {
          title: '成功',
          variant: 'success',
          solid: true
        })
      } catch (error) {
        console.error('更新貼文失敗:', error)
        this.$bvToast.toast('更新貼文失敗', {
          title: '錯誤',
          variant: 'danger',
          solid: true
        })
      }
    },
    async deletePost(postId) {
      if (!confirm('確定要刪除這篇貼文嗎？')) return
      
      try {
        await this.deletePost(postId)
        this.$bvToast.toast('貼文已刪除', {
          title: '成功',
          variant: 'success',
          solid: true
        })
      } catch (error) {
        console.error('刪除貼文失敗:', error)
        this.$bvToast.toast('刪除貼文失敗', {
          title: '錯誤',
          variant: 'danger',
          solid: true
        })
      }
    },
    cancelEdit() {
      this.editingPost = null
      this.$bvModal.hide('edit-post-modal')
    }
  }
}
</script>