<template>
  <div id="app">
    <nav-bar />
    <div class="container mt-4">
      <router-view/>
    </div>
  </div>
</template>

<script>
import NavBar from '@/components/NavBar.vue'

export default {
  name: 'App',
  components: {
    NavBar
  }
}
</script>

<style>
body {
  background-color: #f8f9fa;
}

.card {
  margin-bottom: 20px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.post-content {
  white-space: pre-line;
}

.comment-section {
  background-color: #f8f9fa;
  padding: 15px;
  border-radius: 5px;
  margin-top: 10px;
}

.comment {
  padding: 10px;
  border-bottom: 1px solid #dee2e6;
}

.comment:last-child {
  border-bottom: none;
}
</style>