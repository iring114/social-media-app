<template>
  <b-navbar toggleable="lg" type="dark" variant="secondary" class="mb-4">
    <div class="container">
      <b-navbar-brand to="/">社交媒體應用</b-navbar-brand>

      <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

      <b-collapse id="nav-collapse" is-nav>
        <b-navbar-nav>
          <b-nav-item to="/" v-if="isAuthenticated">首頁</b-nav-item>
          <b-nav-item to="/profile" v-if="isAuthenticated">個人資料</b-nav-item>
        </b-navbar-nav>

        <b-navbar-nav class="ml-auto">
          <template v-if="!isAuthenticated">
            <b-nav-item to="/login">登入</b-nav-item>
            <b-nav-item to="/register">註冊</b-nav-item>
          </template>
          <b-nav-item v-else @click="logout">登出</b-nav-item>
        </b-navbar-nav>
      </b-collapse>
    </div>
  </b-navbar>
</template>

<script>
import { mapGetters, mapActions } from 'vuex'

export default {
  name: 'NavBar',
  computed: {
    ...mapGetters(['isAuthenticated', 'currentUser'])
  },
  methods: {
    ...mapActions(['logout']),
    async handleLogout() {
      this.logout()
      this.$router.push('/login')
    }
  }
}
</script>

<style scoped>
.navbar {
  background-color: #6c757d !important;
}

.navbar-brand {
  font-weight: bold;
  color: white !important;
}

.nav-link {
  color: rgba(255, 255, 255, 0.8) !important;
}

.nav-link:hover {
  color: white !important;
}
</style>