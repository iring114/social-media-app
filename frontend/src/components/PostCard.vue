<template>
  <b-card class="mb-3">
    <b-card-title>{{ post.userName }}</b-card-title>
    <b-card-text class="post-content">{{ post.content }}</b-card-text>
    
    <b-img v-if="post.imageUrl" :src="post.imageUrl" fluid alt="貼文圖片" class="mb-3"></b-img>
    
    <b-card-text class="text-muted small">
      {{ formatDate(post.createdAt) }}
    </b-card-text>
    
    <div class="d-flex justify-content-between">
      <b-button variant="outline-primary" size="sm" @click="toggleComments">
        查看留言 ({{ post.commentCount || 0 }})
      </b-button>
      
      <div v-if="isOwner">
        <b-button variant="outline-warning" size="sm" class="mr-2" @click="$emit('edit', post)">
          編輯
        </b-button>
        <b-button variant="outline-danger" size="sm" @click="$emit('delete', post.id)">
          刪除
        </b-button>
      </div>
    </div>
    
    <div v-if="showComments" class="comment-section mt-3">
      <div v-if="comments.length === 0" class="text-center text-muted my-3">
        暫無留言
      </div>
      
      <div v-else class="comments-list mb-3">
        <div v-for="comment in comments" :key="comment.id" class="comment">
          <div class="d-flex justify-content-between">
            <strong>{{ comment.userName }}</strong>
            <small class="text-muted">{{ formatDate(comment.createdAt) }}</small>
          </div>
          <div>{{ comment.content }}</div>
        </div>
      </div>
      
      <b-form @submit.prevent="addComment" class="mt-3">
        <b-input-group>
          <b-form-input v-model="newComment" placeholder="添加留言..."></b-form-input>
          <b-input-group-append>
            <b-button variant="outline-primary" type="submit">發送</b-button>
          </b-input-group-append>
        </b-input-group>
      </b-form>
    </div>
  </b-card>
</template>

<script>
import axios from 'axios'

export default {
  name: 'PostCard',
  props: {
    post: {
      type: Object,
      required: true
    }
  },
  data() {
    return {
      showComments: false,
      comments: [],
      newComment: ''
    }
  },
  computed: {
    isOwner() {
      return this.$store.getters.currentUser && this.post.userId === this.$store.getters.currentUser.id
    }
  },
  methods: {
    formatDate(dateString) {
      const date = new Date(dateString)
      return date.toLocaleString('zh-TW')
    },
    toggleComments() {
      this.showComments = !this.showComments
      if (this.showComments && this.comments.length === 0) {
        this.fetchComments()
      }
    },
    async fetchComments() {
      try {
        const response = await axios.get(`/api/comments/post/${this.post.id}`)
        this.comments = response.data.data
      } catch (error) {
        console.error('獲取留言失敗:', error)
        this.$bvToast.toast('獲取留言失敗', {
          title: '錯誤',
          variant: 'danger',
          solid: true
        })
      }
    },
    async addComment() {
      if (!this.newComment.trim()) return
      
      try {
        const response = await axios.post('/api/comments', {
          postId: this.post.id,
          content: this.newComment
        })
        
        this.comments.push(response.data.data)
        this.newComment = ''
        
        // 更新貼文的留言數
        this.$emit('comment-added', this.post.id)
      } catch (error) {
        console.error('添加留言失敗:', error)
        this.$bvToast.toast('添加留言失敗', {
          title: '錯誤',
          variant: 'danger',
          solid: true
        })
      }
    }
  }
}
</script>