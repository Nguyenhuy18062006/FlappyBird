## 1:Hướng dẫn sử dụng Git với NetBeans

Tài liệu này hướng dẫn quy trình làm việc hằng ngày với Git trong NetBeans khi phát triển dự án FlappyBird.

Quy trình làm việc hằng ngày

Mỗi khi hoàn thành một chức năng hoặc sửa lỗi, hãy thực hiện theo hai bước sau.

- Bước 1: Commit (Lưu thay đổi trên máy)

Commit giúp lưu lại một phiên bản của mã nguồn trên máy tính trước khi đưa lên GitHub.

Nhấp chuột phải vào dự án FlappyBird.

Chọn Git → Commit...

Nhập nội dung vào ô Commit Message (ví dụ: Sua loi nhay cua chim hoặc Them giao dien chinh).

Nhấn Commit.

Lưu ý: Sau bước này, thay đổi mới chỉ được lưu trên máy cục bộ.

- Bước 2: Push (Đẩy mã nguồn lên GitHub)

Sau khi Commit, cần Push để cập nhật mã nguồn lên kho GitHub.

Nhấp chuột phải vào dự án.

Chọn Git → Remote → Push...

Nhấn Next và Finish qua các cửa sổ hướng dẫn.

NetBeans sẽ sử dụng thông tin đăng nhập (Personal Access Token) đã lưu trước đó nên thường không cần nhập lại.

Sau khi Push thành công, mã nguồn sẽ xuất hiện trên GitHub.

Lấy mã nguồn mới từ GitHub
- Cách 1: Pull (Cập nhật dự án hiện có)

Sử dụng khi muốn lấy những thay đổi mới nhất từ GitHub về máy.

Nhấp chuột phải vào dự án.

Chọn Git → Remote → Pull...

NetBeans sẽ đồng bộ mã nguồn mới nhất từ GitHub.

- Cách 2: Clone (Tải toàn bộ dự án mới)

Sử dụng khi lần đầu tải dự án về hoặc chuyển sang máy tính khác.

Trên thanh menu của NetBeans, chọn Team → Git → Clone...

Dán đường dẫn (URL) của repository GitHub.

Nhấn Next và làm theo hướng dẫn.

Chọn thư mục lưu dự án và nhấn Finish.

Sau khi Clone hoàn tất, dự án sẽ sẵn sàng để mở và làm việc trong NetBeans.


## 2:Cấu trúc dự tính
```text
FlappyBird/
│
├── src/
│   └── flappybird/
│       │
│       ├── Main.java
│       │
│       ├── game/
│       │   ├── Game.java
│       │   ├── GamePanel.java
│       │   ├── Bird.java
│       │   ├── SkinManager.java
│       │   ├── GameState.java
│       │   ├── Skin.java
│       │   ├── PipePair.java
│       │   ├── Background.java
│       │   ├── Ground.java
│       │   ├── SoundManager.java
│       │   └── Pipe.java
│       │
│       ├── database/
│       │   ├── DatabaseConnection.java
│       │   ├── UserDAO.java
│       │   ├── SkinDAO.java
│       │   └── ScoreDAO.java
│       │
│       ├── service/
│       │   ├── UserService.java
│       │   ├── SkinService.java
│       │   └── ScoreService.java
│       │
│       ├── model/
│       │   ├── User.java
│       │   ├── Skin.java
│       │   └── Score.java
│       │
│       ├── ui/
│       │   ├── LoginPanel.java
│       │   ├── SkinPanel.java
│       │   ├── RankingPanel.java
│       │   ├── PausePanel.java
│       │   ├── GameOverPanel.java
│       │   ├── RegisterPanel.java
│       │   └── MenuPanel.java
│       │
│       ├── .env
│       ├── .gitignore
│       └── README.md
│
├── resources/
│   ├── images/
│   │   ├── birds/
│   │   ├── pipes/
│   │   ├── backgrounds/
│   │   └── grounds/
│   │
│   └── sounds/
│       ├── jump.wav
│       ├── jump2.wav
│       ├── score.wav
│       └── die.wav
│
├── database/
    └── flappybird.sql
```

## 3: Database

```text
users
├── id (PK)
├── user_name (UNIQUE)
├── password
└── highest_score

skins
├── id (PK)
├── skin_name
├── skin_type          -- BIRD | PIPE | GROUND | BACKGROUND
├── image_path
├── unlock_score
└── is_active

scores
├── id (PK)
├── user_id (FK -> users.id)
├── score
└── create_at

user_skins             -- skin mà user đã mở khóa
├── user_id (PK, FK -> users.id)
└── skin_id (PK, FK -> skins.id)

user_equipped_skin     -- skin đang dùng theo từng loại
├── user_id (PK, FK -> users.id)
├── skin_type (PK)
└── skin_id (FK -> skins.id)
```

### Quan hệ

- `users` 1 - N `scores`: mỗi user có nhiều lượt chơi.
- `users` N - N `skins` (qua `user_skins`): user mở khóa nhiều skin.
- `users` 1 - N `user_equipped_skin`: mỗi user có tối đa 1 skin đang dùng cho mỗi `skin_type`.

### Có thể mở rộng

```text
settings
├── user_id (PK, FK -> users.id)
└── sound_volume
```

## Dự án nộp cho thầy:
+ Dùng JDBC, và java Swing để hoàn thiện phần GUI và kết nối database ổn định chạy trên local
## Dự án mở rộng:
+ Dùng REST API để phát triển thêm cho dự án chạy trên cả local lẫn online
+ Mở rộng nạp tiền để mua vật phẩm
+ Dùng hệ thống kết nối để cập nhật liên tục khi mất mạng thì vẫn update local storage còn khi có wifi thì cập nhật lên server chung và cập nhật lại bẳng xếp hạng

# Công nghệ sử dụng:
+ Frontend: Java Swing, FX(application), HTML(web)...
+ Backend: Java, REST API, Postman,...
+ Database: MySQL, Docker, cloud server,...