-- =====================================================
-- FlappyBird - Script tạo lại toàn bộ database
-- Chạy cả file một lần (Ctrl + Shift + Enter trong Workbench)
-- CẢNH BÁO: dòng DROP bên dưới sẽ XÓA SẠCH dữ liệu cũ
-- =====================================================

DROP DATABASE IF EXISTS FlappyBird;
CREATE DATABASE FlappyBird;

USE FlappyBird;

CREATE TABLE USERS (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_name VARCHAR(155) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    highest_score INT NOT NULL DEFAULT 0
);

CREATE TABLE SKINS(
    id INT AUTO_INCREMENT PRIMARY KEY,
    skin_name VARCHAR(100) NOT NULL,
    skin_type VARCHAR(50) NOT NULL,
    image_path VARCHAR(255) NOT NULL,
    unlock_score INT NOT NULL DEFAULT 0,
    is_active BOOLEAN NOT NULL DEFAULT TRUE
);

-- ON DELETE CASCADE: khi xóa 1 user (hoặc 1 skin) thì MySQL tự xóa
-- luôn các dòng ở bảng con đang tham chiếu tới nó, không bị lỗi khóa ngoại.

CREATE TABLE SCORES(
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    score INT NOT NULL,
    create_at DATETIME DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_scores_user
        FOREIGN KEY (user_id) REFERENCES USERS(id) ON DELETE CASCADE
);

CREATE TABLE USER_SKINS (
    user_id INT NOT NULL,
    skin_id INT NOT NULL,
    PRIMARY KEY (user_id, skin_id),

    CONSTRAINT fk_user_skins_user
        FOREIGN KEY (user_id) REFERENCES USERS(id) ON DELETE CASCADE,
    CONSTRAINT fk_user_skins_skin
        FOREIGN KEY (skin_id) REFERENCES SKINS(id) ON DELETE CASCADE
);

CREATE TABLE USER_EQUIPPED_SKIN(
    user_id INT NOT NULL,
    skin_type VARCHAR(50) NOT NULL,
    skin_id INT NOT NULL,

    PRIMARY KEY (user_id, skin_type),

    CONSTRAINT fk_equipped_user
        FOREIGN KEY (user_id) REFERENCES USERS(id) ON DELETE CASCADE,
    CONSTRAINT fk_equipped_skin
        FOREIGN KEY (skin_id) REFERENCES SKINS(id) ON DELETE CASCADE
);

INSERT INTO SKINS(skin_name, skin_type, image_path, unlock_score, is_active) VALUES
-- BIRD
('Yellow', 'BIRD', 'resources/images/birds/pixel_skin1_yellow.png', 0, TRUE),
('Blue',   'BIRD', 'resources/images/birds/pixel_skin2_blue.png', 100, TRUE),
('Red',    'BIRD', 'resources/images/birds/pixel_skin3_red.png', 200, TRUE),
('Pink',   'BIRD', 'resources/images/birds/pixel_skin4_pink.png', 300, TRUE),
('Green',  'BIRD', 'resources/images/birds/pixel_skin5_green.png', 500, TRUE),

-- PIPE
('Spring', 'PIPE', 'resources/images/pipes/pipe_spring.png', 0, TRUE),
('Summer', 'PIPE', 'resources/images/pipes/pipe_summer.png', 400, TRUE),
('Autumn', 'PIPE', 'resources/images/pipes/pipe_autumn.png', 700, TRUE),
('Winter', 'PIPE', 'resources/images/pipes/pipe_winter.png', 800, TRUE),

-- GROUND
('Spring', 'GROUND', 'resources/images/grounds/ground_spring.png', 0, TRUE),
('Summer', 'GROUND', 'resources/images/grounds/ground_summer.png', 900, TRUE),
('Autumn', 'GROUND', 'resources/images/grounds/ground_autumn.png', 1000, TRUE),
('Winter', 'GROUND', 'resources/images/grounds/ground_winter.png', 1000, TRUE),

-- BACKGROUND
('Spring', 'BACKGROUND', 'resources/images/backgrounds/bg_spring.png', 0, TRUE),
('Summer', 'BACKGROUND', 'resources/images/backgrounds/bg_summer.png', 200, TRUE),
('Autumn', 'BACKGROUND', 'resources/images/backgrounds/bg_autumn.png', 300, TRUE),
('Winter', 'BACKGROUND', 'resources/images/backgrounds/bg_winter.png', 400, TRUE);

-- Tài khoản MySQL riêng cho dự án (IF NOT EXISTS để chạy lại không bị lỗi)
CREATE USER IF NOT EXISTS 'FlappyBird'@'localhost' IDENTIFIED BY 'flappybird123';

-- Chỉ cấp quyền trên đúng database của dự án
GRANT ALL PRIVILEGES ON `FlappyBird`.* TO 'FlappyBird'@'localhost';

FLUSH PRIVILEGES;