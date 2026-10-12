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
    image_path varchar(255) NOT NULL,
    unlock_score INT NOT NULL DEFAULT 0,
    is_active BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE SCORES(
	id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    score INT NOT NULL,
    create_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (user_id) REFERENCES USERS(id)
);

CREATE TABLE USER_SKINS (
	user_id INT NOT NULL,
    skin_id INT NOT NULL,
    PRIMARY KEY (user_id, skin_id),
    
    FOREIGN KEY (user_id) REFERENCES USERS(id),
    FOREIGN KEY (skin_id) REFERENCES SKINS(id)
);

CREATE TABLE USER_EQUIPPED_SKIN(
	user_id INT NOT NULL,
    skin_type VARCHAR(50) NOT NULL,
    skin_id INT NOT NULL,

    PRIMARY KEY (user_id, skin_type),

    FOREIGN KEY (user_id) REFERENCES USERS(id),
    FOREIGN KEY (skin_id) REFERENCES SKINS(id)
);

CREATE DATABASE IF NOT EXISTS `FlappyBird`;

-- 2. Tạo tài khoản MySQL riêng cho dự án này
CREATE USER 'FlappyBird'@'localhost' IDENTIFIED BY 'flappybird123';

-- 3. CHỈ CẤP QUYỀN trên đúng Database của dự án này (An toàn cho dự án)
GRANT ALL PRIVILEGES ON `FlappyBird`.* TO 'FlappyBird'@'localhost';

-- 4. Áp dụng thay đổi
FLUSH PRIVILEGES;