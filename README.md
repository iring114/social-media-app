# 社交媒體應用

這是一個完整的社交媒體應用，包含基於 Spring Boot 的後端 API 和基於 Vue.js 的前端應用。它提供用戶註冊、登入、發文和留言等核心功能。

## 技術棧

### 後端 (Backend)

- Java 8+
- Spring Boot 2.7.x
- Spring Security
- Spring Data JPA
- MySQL / H2 Database
- JWT (JSON Web Token)
- Maven

### 前端 (Frontend)

- Vue.js 2.x
- Vue CLI 5.x
- Vuex (狀態管理)
- Vue Router (路由)
- Axios (HTTP 客戶端)
- BootstrapVue (UI 框架)

## 功能特點

### 後端功能

- 用戶管理：註冊、登入、獲取用戶信息
- 發文管理：創建、獲取、更新、刪除發文
- 留言管理：添加留言、獲取發文的留言
- JWT認證：安全的API訪問控制
- 數據驗證：請求參數的驗證
- 全局異常處理：統一的API錯誤響應

### 前端功能

- 用戶認證與授權：登入、註冊、登出
- 貼文展示與互動：瀏覽所有貼文、發布新貼文、編輯/刪除自己的貼文
- 個人資料頁面：查看個人信息、管理自己的貼文
- 留言功能：查看貼文留言、添加新留言

## 項目結構

```
social-media-app/
├── frontend/                         # Vue.js 前端應用
├── src/                              # Spring Boot 後端應用源碼
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── esunbank/
│   │   │           └── socialmediaapp/
│   │   │               ├── controller/    # API控制器
│   │   │               ├── dto/           # 數據傳輸對象
│   │   │               ├── exception/     # 異常處理
│   │   │               ├── model/         # 實體模型
│   │   │               ├── repository/    # 數據訪問層
│   │   │               ├── security/      # 安全配置
│   │   │               ├── service/       # 業務邏輯層
│   │   │               └── SocialMediaAppApplication.java  # 應用入口
│   │   └── resources/
│   │       ├── application.properties  # 應用配置
│   │       ├── static/                 # 靜態資源 (前端構建後的靜態文件可部署於此)
│   │       └── templates/              # 模板文件
│   └── test/                           # 測試代碼
├── DB/                                 # 數據庫腳本
│   ├── schema.sql                      # 數據庫結構
│   └── data.sql                        # 測試數據
└── pom.xml                             # Maven 配置 (後端)
```

## API端點

### 認證

- `POST /api/auth/register` - 用戶註冊
- `POST /api/auth/login` - 用戶登入

### 用戶

- `GET /api/users/me` - 獲取當前登入用戶信息
- `GET /api/users/{userId}` - 根據ID獲取用戶信息

### 發文

- `POST /api/posts` - 創建新發文
- `GET /api/posts` - 獲取所有發文
- `GET /api/posts/{postId}` - 根據ID獲取發文
- `GET /api/posts/user/{userId}` - 獲取指定用戶的所有發文
- `PUT /api/posts/{postId}` - 更新發文
- `DELETE /api/posts/{postId}` - 刪除發文

### 留言

- `POST /api/comments` - 添加留言
- `GET /api/comments/post/{postId}` - 獲取指定發文的所有留言

## 運行項目

### 前提條件

- Java 8+
- Maven 3.6+
- Node.js (推薦 LTS 版本)
- npm 或 Yarn
- MySQL 5.7+ (或使用內置H2數據庫)

### 步驟

1. 克隆項目

```bash
git clone <repository-url>
cd social-media-app
```

2. 配置後端數據庫

編輯 `src/main/resources/application.properties` 文件，設置數據庫連接信息：

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/social_media_db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
spring.datasource.username=your_username
spring.datasource.password=your_password
```

3. 運行後端應用

```bash
mvn clean install
mvn spring-boot:run
```

後端應用將在 `http://localhost:8080` 啟動。

4. 運行前端應用

進入 `frontend` 目錄並安裝依賴項：

```bash
cd frontend
npm install
```

啟動前端開發伺服器：

```bash
npm run dev
```

前端應用將在 `http://localhost:8081` (或其他 Vue CLI 預設端口) 啟動，並代理 API 請求到後端 `http://localhost:8080`。

## 使用H2內存數據庫 (後端)

如果您想使用H2內存數據庫進行測試，請在 `application.properties` 中啟用H2配置：

```properties
# 啟用H2數據庫
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

## 安全性

- 所有API端點（除了註冊和登入）都需要JWT認證
- 密碼使用PBKDF2WithHmacSHA256算法進行雜湊處理
- 使用Spring Security進行安全控制

## 貢獻

歡迎提交問題和改進建議！

## 許可證

[MIT](LICENSE)