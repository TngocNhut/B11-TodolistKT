## Bai 11 - Todolist in Kotlin
## Trần Ngọc Nhất - 24162086

### Yều cầu: Áp dụng ***MVVM*** vào app Todolist và chuyển sang ngôn ngữ **Kotlin**
> MVVM: Modle - View ↔ ViewModel bằng DataBinding / State
> và thực hiện chuyển đổi App ToDoList bằng ngôn ngữ Kotlin có các chức năng: đăng ký tài khoản, đăng nhập qua mô hình MVVM. Dữ liệu công việc được luu trữ trong SQL Lite

### Idea triển khai:
- Chuyển đổi Todolist thành ngôn ngữ Kotlin từ Bài 10
- Sử dụng Room database (cũng là SQLite nhưng hiệu quả hơn SQLiteOpenHelper mặc định)
- Loại bỏ `textView.text = cursor.getString(...)` query trực tiếp vào SQLite khi còn dùng SQLiteOpenHelper, bây giờ sử dụng Model MVVM:
> - Chia thành các bước riêng: 
> - **Model** (chỉ quan tâm DATA) -> **ViewModel** (lấy DATA từ Model và chuyển data lên View) -> **View** (giao diện của app, nhận data từ ViewModel và thể hiện nó trên màn hình)

## Logic demo chức năng theo thứ tự:
- Khi mở app người dùng cần đăng nhập, nếu chưa có tài khoản sẽ đăng kí (Ảnh 1)
- Sau khi đăng nhập chuyển sang View màn hình chính nơi hiển thị các Todolist list (Ảnh 3) hoặc nếu chưa tạo sẽ có màn hình yêu cầu tạo (Ảnh 2)
- Có thể tạo mới 1 Todo (Ảnh 4) hoặc bấm vào 1 todo trên list hiển thị trên màn hình để chỉnh sửa.

## Demo giao diện app Todolist:
> ẢNH 1: giao diện app khi mở 
![image](https://github.com/TngocNhut/B11-TodolistKT/blob/49443e81ba20a5a551196794342e583651781af8/app/Screenshot%202025-12-19%20173225.png)
> ẢNH 2: giao diện sau khi đăng nhập (nếu bạn chưa có bất kì todo nào sẽ hiển thị view này)
![image2](https://github.com/TngocNhut/B11-TodolistKT/blob/49443e81ba20a5a551196794342e583651781af8/app/Screenshot%202025-12-19%20173431.png)
> ẢNH 3: giao diện sau khi đăng nhập (nếu đã có sẵn các todo từ trước - được lưu trong SQLite)
![image3](https://github.com/TngocNhut/B11-TodolistKT/blob/49443e81ba20a5a551196794342e583651781af8/app/Screenshot%202025-12-19%20173234.png)
> ẢNH 4: giao diện THÊM MỚI 1 TODO gồm Title và Description.
![image4](https://github.com/TngocNhut/B11-TodolistKT/blob/49443e81ba20a5a551196794342e583651781af8/app/Screenshot%202025-12-19%20173403.png)
> ẢNH 5: giao diện sau khi mới thêm mới 1 todo (hiển thị từ trên -> dưới dựa vào thời gian create todo)
![image5](https://github.com/TngocNhut/B11-TodolistKT/blob/49443e81ba20a5a551196794342e583651781af8/app/Screenshot%202025-12-19%20173409.png)
