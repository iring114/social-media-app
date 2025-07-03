# 社交媒體應用前端

這是一個使用Vue.js構建的社交媒體應用前端，與Spring Boot後端API配合使用。

## 技術棧

- Vue.js 2.x
- Vue Router 3.x
- Vuex 3.x
- Axios
- Bootstrap Vue

## 功能

- 用戶認證（登入/註冊/登出）
- 貼文管理（創建/查看/編輯/刪除）
- 留言功能
- 個人資料頁面

## 開發設置

### 安裝依賴

```bash
npm install
```

### 啟動開發服務器

```bash
npm run serve
```

### 構建生產版本

```bash
npm run build
```

## 與後端集成

前端默認配置為通過代理將API請求轉發到後端服務器（http://localhost:8080）。

構建後的文件會自動輸出到Spring Boot的靜態資源目錄（`../src/main/resources/static`），這樣Spring Boot就可以直接提供前端資源。

## 項目結構

- `src/components/`: 可重用的Vue組件
- `src/views/`: 頁面級Vue組件
- `src/router/`: Vue Router配置
- `src/store/`: Vuex狀態管理
- `src/utils/`: 工具函數和服務
- `public/`: 靜態資源