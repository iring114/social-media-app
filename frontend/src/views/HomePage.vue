<template>
  <div>
    <post-form @create="createPost" @update="updatePost" @cancel="cancelEdit" :edit-post="editingPost" />
    
    <h3 class="mb-3">最新貼文</h3>
    
    <div v-if="loading" class="text-center my-5">
      <b-spinner variant="primary" label="Loading"></b-spinner>
    </div>
    
    <div v-else-if="posts.length === 0" class="text-center my-5">
      <p class="text-muted">暫無貼文，成為第一個發文的人吧！</p>
    </div>
    
    <div v-else>
      <post-card 
        v-for="post in posts" 
        :key="post.id" 
        :post="post"
        @edit="editPost"
        @delete="deletePost"
        @comment-added="refreshPosts"
      />
    </div>
  </div>
</template>

<script>
import { mapGetters, mapActions } from 'vuex'
import PostForm from '@/components/PostForm.vue'
import PostCard from '@/components/PostCard.vue'

export default {
  name: 'HomePage',
  components: {
    PostForm,
    PostCard
  },
  data() {
    return {
      loading: false,
      editingPost: null
    }
  },
  computed: {
    ...mapGetters(['allPosts']),
    posts() {
      return this.allPosts
    }
  },
  created() {
    this.loadPosts()
  },
  methods: {
    ...mapActions(['fetchPosts', 'createPost', 'updatePost', 'deletePost']),
    async loadPosts() {
      this.loading = true
      try {
        await this.fetchPosts()
      } catch (error) {
        console.error('獲取貼文失敗:', error)
        this.$bvToast.toast('獲取貼文失敗', {
          title: '錯誤',
          variant: 'danger',
          solid: true
        })
      } finally {
        this.loading = false
      }
    },
    async refreshPosts() {
      await this.fetchPosts()
    },
    async createPost(postData) {
      try {
        await this.createPost(postData)
        this.$bvToast.toast('貼文發布成功', {
          title: '成功',
          variant: 'success',
          solid: true
        })
      } catch (error) {
        console.error('發布貼文失敗:', error)
        this.$bvToast.toast('發布貼文失敗', {
          title: '錯誤',
          variant: 'danger',
          solid: true
        })
      }
    },
    editPost(post) {
      this.editingPost = post
    },
    async updatePost({ postId, postData }) {
      try {
        await this.updatePost({ postId, postData })
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
    }
  }
}
</script>