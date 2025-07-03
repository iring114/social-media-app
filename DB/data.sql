USE social_media_db;

-- 插入測試用戶數據 (密碼為 'password123' 加鹽後的雜湊值)
INSERT INTO users (user_name, email, phone_number, password, salt, biography)
VALUES 
('張三', 'zhangsan@example.com', '0912345678', 'a7d579ba8b9bed40e0f299cb8f3f6c4a581a8d7524d3a3562cb9b25d2f2a1163', 'salt1', '我是張三，喜歡攝影和旅行。'),
('李四', 'lisi@example.com', '0923456789', 'b7e7ae565ae325e4b97b3d28d915ccc4ca8d82a25cc5c7ae781fc9f8b99d1a7b', 'salt2', '我是李四，喜歡閱讀和音樂。'),
('王五', 'wangwu@example.com', '0934567890', 'c8d9e7f6a5b4c3d2e1f0a9b8c7d6e5f4a3b2c1d0e9f8a7b6c5d4e3f2a1b0c9d8', 'salt3', '我是王五，喜歡運動和美食。');

-- 插入測試發文數據
INSERT INTO posts (user_id, content, image)
VALUES 
(1, '今天天氣真好，分享一張美麗的風景照。', 'landscape.jpg'),
(2, '剛讀完一本很棒的書，強烈推薦給大家！', NULL),
(3, '分享一道我最近做的美食，非常好吃！', 'food.jpg'),
(1, '週末去爬山，感覺真好！', 'mountain.jpg');

-- 插入測試留言數據
INSERT INTO comments (user_id, post_id, content)
VALUES 
(2, 1, '照片拍得真美，是在哪裡拍的？'),
(3, 1, '天氣確實不錯，我也出去走走。'),
(1, 2, '是什麼書？可以分享書名嗎？'),
(2, 3, '看起來很美味，可以分享食譜嗎？'),
(3, 4, '哪座山？下次我也想去！'),
(1, 3, '我也很喜歡這道菜！');