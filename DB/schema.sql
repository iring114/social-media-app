-- 創建數據庫
CREATE DATABASE IF NOT EXISTS social_media_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE social_media_db;

-- 用戶表
CREATE TABLE IF NOT EXISTS users (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone_number VARCHAR(20) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    salt VARCHAR(100) NOT NULL,
    cover_image VARCHAR(255),
    biography TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 發文表
CREATE TABLE IF NOT EXISTS posts (
    post_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    content TEXT NOT NULL,
    image VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- 留言表
CREATE TABLE IF NOT EXISTS comments (
    comment_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    post_id BIGINT NOT NULL,
    content TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (post_id) REFERENCES posts(post_id) ON DELETE CASCADE
);

-- 創建存儲過程: 註冊新用戶
DELIMITER //
CREATE PROCEDURE sp_register_user(
    IN p_user_name VARCHAR(50),
    IN p_email VARCHAR(100),
    IN p_phone_number VARCHAR(20),
    IN p_password VARCHAR(255),
    IN p_salt VARCHAR(100),
    IN p_biography TEXT
)
BEGIN
    INSERT INTO users(user_name, email, phone_number, password, salt, biography)
    VALUES(p_user_name, p_email, p_phone_number, p_password, p_salt, p_biography);
    SELECT LAST_INSERT_ID() AS user_id;
END //
DELIMITER ;

-- 創建存儲過程: 獲取用戶信息通過手機號碼
DELIMITER //
CREATE PROCEDURE sp_get_user_by_phone(
    IN p_phone_number VARCHAR(20)
)
BEGIN
    SELECT * FROM users WHERE phone_number = p_phone_number;
END //
DELIMITER ;

-- 創建存儲過程: 創建新發文
DELIMITER //
CREATE PROCEDURE sp_create_post(
    IN p_user_id BIGINT,
    IN p_content TEXT,
    IN p_image VARCHAR(255)
)
BEGIN
    INSERT INTO posts(user_id, content, image)
    VALUES(p_user_id, p_content, p_image);
    SELECT LAST_INSERT_ID() AS post_id;
END //
DELIMITER ;

-- 創建存儲過程: 獲取所有發文
DELIMITER //
CREATE PROCEDURE sp_get_all_posts()
BEGIN
    SELECT p.*, u.user_name, u.email
    FROM posts p
    JOIN users u ON p.user_id = u.user_id
    ORDER BY p.created_at DESC;
END //
DELIMITER ;

-- 創建存儲過程: 更新發文
DELIMITER //
CREATE PROCEDURE sp_update_post(
    IN p_post_id BIGINT,
    IN p_content TEXT,
    IN p_image VARCHAR(255)
)
BEGIN
    UPDATE posts
    SET content = p_content, image = p_image
    WHERE post_id = p_post_id;
    SELECT ROW_COUNT() AS updated;
END //
DELIMITER ;

-- 創建存儲過程: 刪除發文
DELIMITER //
CREATE PROCEDURE sp_delete_post(
    IN p_post_id BIGINT
)
BEGIN
    DELETE FROM comments WHERE post_id = p_post_id;
    DELETE FROM posts WHERE post_id = p_post_id;
    SELECT ROW_COUNT() AS deleted;
END //
DELIMITER ;

-- 創建存儲過程: 添加留言
DELIMITER //
CREATE PROCEDURE sp_add_comment(
    IN p_user_id BIGINT,
    IN p_post_id BIGINT,
    IN p_content TEXT
)
BEGIN
    INSERT INTO comments(user_id, post_id, content)
    VALUES(p_user_id, p_post_id, p_content);
    SELECT LAST_INSERT_ID() AS comment_id;
END //
DELIMITER ;

-- 創建存儲過程: 獲取發文的所有留言
DELIMITER //
CREATE PROCEDURE sp_get_comments_by_post(
    IN p_post_id BIGINT
)
BEGIN
    SELECT c.*, u.user_name
    FROM comments c
    JOIN users u ON c.user_id = u.user_id
    WHERE c.post_id = p_post_id
    ORDER BY c.created_at ASC;
END //
DELIMITER ;