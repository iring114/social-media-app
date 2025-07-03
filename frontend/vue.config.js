const { defineConfig } = require('@vue/cli-service')

module.exports = defineConfig({
  transpileDependencies: true,
  // 輸出目錄設置為Spring Boot的靜態資源目錄
  outputDir: '../src/main/resources/static',
  // 開發服務器設置
  devServer: {
    port: 8081,
    proxy: {
      '/api': {
        target: 'http://localhost:8080',
        changeOrigin: true
      }
    }
  }
})