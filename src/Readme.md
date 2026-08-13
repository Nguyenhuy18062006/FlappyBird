Hướng dẫn sử dụng Git với NetBeans

Tài liệu này hướng dẫn quy trình làm việc hằng ngày với Git trong NetBeans khi phát triển dự án FlappyBird.

Quy trình làm việc hằng ngày

Mỗi khi hoàn thành một chức năng hoặc sửa lỗi, hãy thực hiện theo hai bước sau.

##Bước 1: Commit (Lưu thay đổi trên máy)

Commit giúp lưu lại một phiên bản của mã nguồn trên máy tính trước khi đưa lên GitHub.

Nhấp chuột phải vào dự án FlappyBird.

Chọn Git → Commit...

Nhập nội dung vào ô Commit Message (ví dụ: Sua loi nhay cua chim hoặc Them giao dien chinh).

Nhấn Commit.

Lưu ý: Sau bước này, thay đổi mới chỉ được lưu trên máy cục bộ.

##Bước 2: Push (Đẩy mã nguồn lên GitHub)

Sau khi Commit, cần Push để cập nhật mã nguồn lên kho GitHub.

Nhấp chuột phải vào dự án.

Chọn Git → Remote → Push...

Nhấn Next và Finish qua các cửa sổ hướng dẫn.

NetBeans sẽ sử dụng thông tin đăng nhập (Personal Access Token) đã lưu trước đó nên thường không cần nhập lại.

Sau khi Push thành công, mã nguồn sẽ xuất hiện trên GitHub.

Lấy mã nguồn mới từ GitHub
##Cách 1: Pull (Cập nhật dự án hiện có)

Sử dụng khi muốn lấy những thay đổi mới nhất từ GitHub về máy.

Nhấp chuột phải vào dự án.

Chọn Git → Remote → Pull...

NetBeans sẽ đồng bộ mã nguồn mới nhất từ GitHub.

##Cách 2: Clone (Tải toàn bộ dự án mới)

Sử dụng khi lần đầu tải dự án về hoặc chuyển sang máy tính khác.

Trên thanh menu của NetBeans, chọn Team → Git → Clone...

Dán đường dẫn (URL) của repository GitHub.

Nhấn Next và làm theo hướng dẫn.

Chọn thư mục lưu dự án và nhấn Finish.

Sau khi Clone hoàn tất, dự án sẽ sẵn sàng để mở và làm việc trong NetBeans.