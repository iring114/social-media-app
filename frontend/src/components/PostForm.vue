<template>
  <b-card class="mb-4">
    <h5 class="card-title">{{ isEditing ? '編輯貼文' : '發表新貼文' }}</h5>
    <b-form @submit.prevent="submitPost">
      <b-form-group>
        <b-form-textarea
          id="post-content"
          v-model="form.content"
          placeholder="分享你的想法..."
          rows="3"
          max-rows="6"
          required
        ></b-form-textarea>
      </b-form-group>
      
      <b-form-group label="圖片URL (選填)" label-for="post-image">
        <b-form-input
          id="post-image"
          v-model="form.imageUrl"
          placeholder="https://example.com/image.jpg"
        ></b-form-input>
      </b-form-group>
      
      <div class="d-flex justify-content-between">
        <b-button type="submit" variant="primary">
          {{ isEditing ? '更新' : '發布' }}
        </b-button>
        <b-button v-if="isEditing" variant="outline-secondary" @click="cancelEdit">
          取消
        </b-button>
      </div>
    </b-form>
  </b-card>
</template>

<script>
export default {
  name: 'PostForm',
  props: {
    editPost: {
      type: Object,
      default: null
    }
  },
  data() {
    return {
      form: {
        content: '',
        imageUrl: ''
      }
    }
  },
  computed: {
    isEditing() {
      return !!this.editPost
    }
  },
  watch: {
    editPost(post) {
      if (post) {
        this.form.content = post.content
        this.form.imageUrl = post.imageUrl || ''
      } else {
        this.resetForm()
      }
    }
  },
  methods: {
    submitPost() {
      if (!this.form.content.trim()) return
      
      const postData = {
        content: this.form.content,
        imageUrl: this.form.imageUrl || null
      }
      
      if (this.isEditing) {
        this.$emit('update', { postId: this.editPost.id, postData })
      } else {
        this.$emit('create', postData)
      }
      
      this.resetForm()
    },
    cancelEdit() {
      this.$emit('cancel')
      this.resetForm()
    },
    resetForm() {
      this.form.content = ''
      this.form.imageUrl = ''
    }
  }
}
</script>