# 社交媒體應用（前後端整合版）

這是一個完整的社交媒體應用，包含Spring Boot後端API和Vue.js前端界面。

## 技術棧

### 後端
- Java 8+
- Spring Boot 2.7.x
- Spring Security
- Spring Data JPA
- MySQL / H2 Database
- JWT (JSON Web Token)
- Maven

### 前端
- Vue.js 2.x
- Vue Router 3.x
- Vuex 3.x
- Axios
- Bootstrap Vue

## 功能

- 用戶註冊和登入
- 發布、編輯和刪除貼文
- 查看所有貼文和特定用戶的貼文
- 在貼文下添加留言
- 用戶個人資料頁面

## 運行項目

### 前提條件

- Java 8+
- Maven 3.6+
- Node.js 14+
- npm 6+
- MySQL（可選，也可使用H2內存數據庫）

### 步驟

#### 1. 克隆項目

```bash
git clone <repository-url>
cd social-media-app
```

#### 2. 配置數據庫

編輯 `src/main/resources/application.properties` 文件，設置數據庫連接信息：

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/social_media_db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
spring.datasource.username=your_username
spring.datasource.password=your_password
```

或者使用H2內存數據庫（無需外部數據庫）：

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

#### 3. 構建和運行後端

```bash
mvn clean install
mvn spring-boot:run
```

後端服務將在 http://localhost:8080 運行。

#### 4. 安裝前端依賴

```bash
cd frontend
npm install
```

#### 5. 開發模式運行前端

```bash
npm run serve
```

前端開發服務器將在 http://localhost:8081 運行。

#### 6. 構建前端（生產環境）

```bash
npm run build
```

這將生成前端資源並自動放置到Spring Boot的靜態資源目錄中。

#### 7. 一體化運行（生產環境）

構建前端後，重新啟動Spring Boot應用：

```bash
cd ..
mvn spring-boot:run
```

現在可以通過 http://localhost:8080 訪問完整的應用。

## 開發說明

### 後端API

所有API端點都以 `/api` 開頭：

- 認證相關：`/api/auth/**`
- 用戶相關：`/api/users/**`
- 貼文相關：`/api/posts/**`
- 留言相關：`/api/comments/**`

### 前端路由

- 首頁：`/`
- 登入頁：`/login`
- 註冊頁：`/register`
- 個人資料頁：`/profile`

### 安全性

- 所有API端點（除了註冊和登入）都需要JWT認證
- 密碼使用PBKDF2WithHmacSHA256算法進行雜湊處理
- 使用Spring Security進行安全控制

## 項目結構

```
social-media-app/
├── src/                           # 後端源代碼
│   ├── main/
│   │   ├── java/
│   │   │   └── com/esunbank/socialmediaapp/
│   │   │       ├── config/        # 配置類
│   │   │       ├── controller/    # REST控制器
│   │   │       ├── dto/           # 數據傳輸對象
│   │   │       ├── exception/     # 異常處理
│   │   │       ├── model/         # 實體類
│   │   │       ├── repository/    # 數據訪問層
│   │   │       ├── security/      # 安全相關
│   │   │       ├── service/       # 業務邏輯層
│   │   │       └── SocialMediaAppApplication.java
│   │   └── resources/
│   │       ├── static/            # 靜態資源（前端構建輸出）
│   │       └── application.properties
│   └── test/                      # 測試代碼
├── frontend/                      # 前端源代碼
│   ├── public/                    # 靜態資源
│   ├── src/
│   │   ├── components/            # 可重用組件
│   │   ├── views/                 # 頁面組件
│   │   ├── router/                # 路由配置
│   │   ├── store/                 # Vuex狀態管理
│   │   ├── utils/                 # 工具函數
│   │   ├── App.vue                # 根組件
│   │   └── main.js                # 入口文件
│   ├── package.json               # 依賴配置
│   └── vue.config.js              # Vue配置
└── pom.xml                        # Maven配置
```