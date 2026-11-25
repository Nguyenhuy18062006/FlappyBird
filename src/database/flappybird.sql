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
('Demon',        'BIRD', 'resources/images/birds/pixel_demon.png', 700, TRUE),
('Dragonkin',    'BIRD', 'resources/images/birds/pixel_dragonkin.png', 900, TRUE),
('Druid',        'BIRD', 'resources/images/birds/pixel_druid.png', 1100, TRUE),
('Elf',          'BIRD', 'resources/images/birds/pixel_elf.png', 1300, TRUE),
('Fairy',        'BIRD', 'resources/images/birds/pixel_fairy.png', 1500, TRUE),
('Knight',       'BIRD', 'resources/images/birds/pixel_knight.png', 1800, TRUE),
('Mage',         'BIRD', 'resources/images/birds/pixel_mage.png', 2100, TRUE),
('Necromancer',  'BIRD', 'resources/images/birds/pixel_necromancer.png', 2400, TRUE),
('Paladin',      'BIRD', 'resources/images/birds/pixel_paladin.png', 2700, TRUE),
('Phoenix',      'BIRD', 'resources/images/birds/pixel_phoenix.png', 3000, TRUE),
('Priest',       'BIRD', 'resources/images/birds/pixel_priest.png', 3400, TRUE),
('Rogue',        'BIRD', 'resources/images/birds/pixel_rogue.png', 3800, TRUE),
('Vampire',      'BIRD', 'resources/images/birds/pixel_vampire.png', 4200, TRUE),
('Witch',        'BIRD', 'resources/images/birds/pixel_witch.png', 4600, TRUE),

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
('Winter', 'BACKGROUND', 'resources/images/backgrounds/bg_winter.png', 400, TRUE),
('Magical Sky Kingdom',     'BACKGROUND', 'resources/images/backgrounds/bg_01_magical_sky_kingdom.png', 0, TRUE),
('Enchanted Forest',        'BACKGROUND', 'resources/images/backgrounds/bg_02_enchanted_forest.png', 200, TRUE),
('Dragon Volcanic Land',    'BACKGROUND', 'resources/images/backgrounds/bg_03_dragon_volcanic_land.png', 400, TRUE),
('Crystal Magic City',      'BACKGROUND', 'resources/images/backgrounds/bg_04_crystal_magic_city.png', 600, TRUE),
('Moonlight Fantasy Kingdom','BACKGROUND', 'resources/images/backgrounds/bg_05_moonlight_fantasy_kingdom.png', 800, TRUE);

-- =====================================================
-- DỮ LIỆU MẪU ĐỂ TEST (đăng nhập được ngay)
-- Tất cả tài khoản dùng chung mật khẩu: Test@123
-- Mật khẩu đã được hash bằng BCrypt (cost 12), giống lúc đăng ký trong game
--   fullskin : điểm cao nhất 5000 -> mở khóa toàn bộ 36/36 skin
--   admin    : điểm cao nhất 750  -> mở khóa 18/36 skin (một nửa)
--   player1..player5 : điểm thấp hơn, để xem bảng xếp hạng và skin bị khóa
-- =====================================================
INSERT INTO USERS(user_name, password, highest_score) VALUES
('admin',   '$2a$12$QFooO7Dmip71Nf8E0fENAelK3pUg/D/JTmqjbMSzA8B9ztiTKCfZe', 750),
('player1', '$2a$12$adwb5oIR4yepEYFMO3f2qefB2IHcrF/97V6G8VgEKaeRb21AFKWdm', 620),
('player2', '$2a$12$6eib1vTA4KFgoNlx/esxtO6UwcQuH3cnVerNywacW8o2JvtSbHVt2', 480),
('player3', '$2a$12$8h4O5KC8j6izSb0mvW.vqeQjvnydbkt6pAJopIR72I8M8C2egXPUK', 320),
('player4', '$2a$12$W6zimlnMxNAffza4d.kLJu..IcQkQliinl.sbccJDUMUWRC1GS7Z.', 150),
('player5', '$2a$12$mZ7UgJsuk4ErG0TJfmj3besiPWP2DzX4cd//uRJEua.qwyE7Q4eI.', 0),
('fullskin', '$2a$12$hQ6wwRq9dEquc.Ncq5AAb./teZQ5Tyj0TbU7TbEjObXDemANnfW6G', 5000);

INSERT INTO SCORES(user_id, score, create_at) VALUES
(1,  250, '2026-11-20 09:15:00'),
(1,  540, '2026-11-21 20:30:00'),
(1,  750, '2026-11-23 21:05:00'),
(2,  180, '2026-11-20 10:00:00'),
(2,  410, '2026-11-22 19:45:00'),
(2,  620, '2026-11-24 15:20:00'),
(3,  230, '2026-11-21 08:40:00'),
(3,  480, '2026-11-23 16:10:00'),
(4,   90, '2026-11-22 11:25:00'),
(4,  320, '2026-11-24 20:00:00'),
(5,   40, '2026-11-23 14:00:00'),
(5,  150, '2026-11-24 18:35:00'),
(7, 2300, '2026-11-21 19:00:00'),
(7, 4100, '2026-11-23 20:15:00'),
(7, 5000, '2026-11-25 10:30:00');

-- Tài khoản MySQL riêng cho dự án (IF NOT EXISTS để chạy lại không bị lỗi)
CREATE USER IF NOT EXISTS 'FlappyBird'@'localhost' IDENTIFIED BY 'flappybird123';

-- Chỉ cấp quyền trên đúng database của dự án
GRANT ALL PRIVILEGES ON `FlappyBird`.* TO 'FlappyBird'@'localhost';

FLUSH PRIVILEGES;