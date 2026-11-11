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
│       │   ├── AccountPanel.java
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

## 4: CRUD với JDBC

### 4.1 Luồng xử lý (kiến trúc phân tầng)

```text
UI (Swing Panel)  ->  Game  ->  Service  ->  DAO  ->  JDBC  ->  MySQL
  lấy input         điều phối   nghiệp vụ    chạy SQL
  hiển thị kết quả              (kiểm tra,   (PreparedStatement)
                                hash BCrypt)
```

- **UI** (`flappybird/ui`): chỉ hiển thị và lấy dữ liệu người dùng nhập, không viết SQL.
- **Service** (`flappybird/service`): xử lý nghiệp vụ như kiểm tra mật khẩu cũ, hash mật khẩu bằng BCrypt, tính lại điểm kỷ lục.
- **DAO** (`flappybird/database`): mỗi hàm chạy đúng một câu SQL qua JDBC.
- **Model** (`flappybird/model`): `User`, `Score`, `Skin` là các object Java tương ứng với từng dòng trong bảng.

### 4.2 JDBC được dùng như thế nào

| Thành phần JDBC | Vai trò trong dự án |
|---|---|
| `DriverManager.getConnection(url, user, pass)` | Mở kết nối tới MySQL (`DatabaseConnection.java`) |
| `Connection` | Phiên làm việc với database |
| `PreparedStatement` | Câu SQL có dấu `?`, giá trị truyền bằng `setInt` / `setString` để **chống SQL Injection** |
| `executeQuery()` | Dùng cho `SELECT`, trả về `ResultSet` |
| `executeUpdate()` | Dùng cho `INSERT` / `UPDATE` / `DELETE`, trả về **số dòng bị ảnh hưởng** |
| `ResultSet` | Đọc từng dòng kết quả (`next()`, `getInt`, `getString`, `getTimestamp`) |
| `try-with-resources` | Tự đóng `Connection`, `PreparedStatement` và `ResultSet` sau khi dùng, tránh rò rỉ kết nối |

Mẫu chung của mọi hàm DAO:

```java
String sql = "DELETE FROM SCORES WHERE id = ? AND user_id = ?";
try (Connection connection = DatabaseConnection.getConnection();
     PreparedStatement statement = connection.prepareStatement(sql)) {
    statement.setInt(1, scoreId);
    statement.setInt(2, userId);
    return statement.executeUpdate() > 0;   // > 0 nghĩa là đã xóa được
}
```

### 4.3 Bảng CRUD

**USERS** (`UserDAO` / `UserService`)

| | Chức năng trên giao diện | Hàm | SQL |
|---|---|---|---|
| **C** | Đăng ký (REGISTER) | `createUser` | `INSERT INTO USERS (user_name, password) VALUES (?, ?)`. Mật khẩu được hash BCrypt trước khi lưu |
| **R** | Đăng nhập, bảng xếp hạng (LOGIN, RANKING) | `getUserByUserName`, `getTopUsers` | `SELECT ... WHERE user_name = ?`, `SELECT ... ORDER BY highest_score DESC LIMIT 20` |
| **U** | Cập nhật kỷ lục sau mỗi ván; đổi mật khẩu (ACCOUNT) | `updateUser`, `changePassword` | `UPDATE USERS SET user_name = ?, password = ?, highest_score = ? WHERE id = ?` |
| **D** | Xóa tài khoản (ACCOUNT) | `deleteAccount` → `deleteUser` | `DELETE FROM USERS WHERE id = ?` |

**SCORES** (`ScoreDAO` / `ScoreService`)

| | Chức năng trên giao diện | Hàm | SQL |
|---|---|---|---|
| **C** | Tự lưu điểm khi thua (đã đăng nhập) | `createScore` | `INSERT INTO SCORES (user_id, score) VALUES (?, ?)` |
| **R** | Lịch sử chơi (ACCOUNT) | `getScoresByUserId` | `SELECT * FROM SCORES WHERE user_id = ? ORDER BY create_at DESC` |
| **D** | Xóa 1 lượt chơi (ACCOUNT) | `deleteScoreOfUser` | `DELETE FROM SCORES WHERE id = ? AND user_id = ?`, sau đó tính lại kỷ lục bằng `SELECT COALESCE(MAX(score), 0) ...` |

**SKINS** (`SkinDAO` / `SkinService`): đã có đủ `createSkin`, `getSkinById`, `getAllSkins`, `updateSkin`, `deleteSkin`. Giao diện hiện mới dùng **R** (màn hình SKINS).

### 4.4 Các điểm đáng chú ý

- **Chống SQL Injection:** mọi câu SQL đều dùng `PreparedStatement` với `?`, không cộng chuỗi.
- **Bảo mật mật khẩu:** chỉ lưu hash BCrypt (cost 12). Đăng nhập, đổi mật khẩu và xóa tài khoản đều so khớp bằng `BCrypt.verifyer()`.
- **Kiểm tra quyền sở hữu:** khi xóa lượt chơi có thêm điều kiện `AND user_id = ?`, nên user không xóa được điểm của người khác.
- **Toàn vẹn dữ liệu:** các khóa ngoại dùng `ON DELETE CASCADE`. Khi xóa user, MySQL tự xóa luôn `SCORES`, `USER_SKINS` và `USER_EQUIPPED_SKIN` của user đó.
- **Đồng bộ dữ liệu:** xóa ván đang là kỷ lục thì `highest_score` được tính lại từ các ván còn lại.

### 4.5 Cách demo CRUD

1. Chạy `database/flappybird.sql` trong MySQL Workbench bằng tài khoản root.
2. **REGISTER** để tạo tài khoản (**C**), rồi **LOGIN** (**R**).
3. Chơi vài ván. Mỗi ván được lưu điểm (**C** Score) và cập nhật kỷ lục (**U** User).
4. Vào **RANKING** để xem bảng xếp hạng (**R**).
5. Vào **ACCOUNT**:
   - xem lịch sử chơi (**R**),
   - chọn một dòng rồi bấm *Xóa lượt chơi* (**D**) để thấy kỷ lục được tính lại,
   - đổi mật khẩu (**U**), sau đó LOGOUT và đăng nhập lại bằng mật khẩu mới,
   - bấm *Xóa tài khoản* (**D**) và nhập mật khẩu để xác nhận.
6. Kiểm tra lại trong Workbench: `SELECT * FROM USERS; SELECT * FROM SCORES;`