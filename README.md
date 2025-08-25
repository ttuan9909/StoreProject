# WebShop - Ứng dụng mua sắm trực tuyến

## Mô tả dự án

WebShop là một ứng dụng web mua sắm trực tuyến được xây dựng bằng Java Servlet/JSP với cơ sở dữ liệu MySQL. Ứng dụng cung cấp đầy đủ các chức năng cơ bản của một trang web thương mại điện tử.

## Tính năng chính

### 1. Quản lý sản phẩm
- ✅ **Xem danh sách sản phẩm**: Hiển thị tất cả sản phẩm với phân trang
- ✅ **Tìm kiếm sản phẩm**: Tìm kiếm theo tên hoặc mô tả
- ✅ **Lọc theo danh mục**: Lọc sản phẩm theo danh mục
- ✅ **Xem chi tiết sản phẩm**: Hiển thị thông tin chi tiết sản phẩm

### 2. Quản lý giỏ hàng
- ✅ **Thêm sản phẩm vào giỏ hàng**: Thêm sản phẩm với số lượng tùy chọn
- ✅ **Xem giỏ hàng**: Hiển thị danh sách sản phẩm trong giỏ hàng
- ✅ **Cập nhật số lượng**: Thay đổi số lượng sản phẩm
- ✅ **Xóa sản phẩm**: Xóa sản phẩm khỏi giỏ hàng
- ✅ **Xóa toàn bộ giỏ hàng**: Xóa tất cả sản phẩm

### 3. Quản lý đơn hàng
- ✅ **Đặt hàng**: Tạo đơn hàng từ giỏ hàng
- ✅ **Xem lịch sử đơn hàng**: Hiển thị tất cả đơn hàng đã đặt
- ✅ **Xem chi tiết đơn hàng**: Hiển thị thông tin chi tiết đơn hàng
- ✅ **Theo dõi trạng thái**: Hiển thị trạng thái đơn hàng

## Công nghệ sử dụng

- **Backend**: Java Servlet, JSP
- **Database**: MySQL
- **Frontend**: HTML5, CSS3, JavaScript, Bootstrap 5
- **Build Tool**: Gradle
- **Server**: Apache Tomcat

## Cấu trúc dự án

```
StoreProject/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/example/storeproject/
│       │       ├── config/
│       │       │   └── FilterUTF8.java
│       │       ├── controller/
│       │       │   ├── ProductServlet.java
│       │       │   ├── CartServlet.java
│       │       │   └── OrderServlet.java
│       │       ├── entity/
│       │       │   ├── Product.java
│       │       │   ├── Category.java
│       │       │   ├── Cart.java
│       │       │   ├── CartDetail.java
│       │       │   ├── Order.java
│       │       │   ├── OrderDetail.java
│       │       │   └── User.java
│       │       ├── repository/
│       │       │   ├── DBConnection.java
│       │       │   ├── product/
│       │       │   │   ├── IProductRepository.java
│       │       │   │   ├── ProductRepository.java
│       │       │   │   ├── ICategoryRepository.java
│       │       │   │   └── CategoryRepository.java
│       │       │   ├── cart/
│       │       │   │   ├── ICartRepository.java
│       │       │   │   └── CartRepository.java
│       │       │   └── order/
│       │       │       ├── IOrderRepository.java
│       │       │       └── OrderRepository.java
│       │       └── service/
│       │           ├── product/
│       │           │   ├── IProductService.java
│       │           │   └── ProductService.java
│       │           ├── cart/
│       │           │   ├── ICartService.java
│       │           │   └── CartService.java
│       │           └── order/
│       │               ├── IOrderService.java
│       │               └── OrderService.java
│       └── webapp/
│           ├── dashboard.jsp
│           ├── WEB-INF/
│           │   ├── web.xml
│           │   └── page/
│           │       ├── product/
│           │       │   ├── product-list.jsp
│           │       │   └── product-detail.jsp
│           │       ├── cart/
│           │       │   └── cart.jsp
│           │       └── order/
│           │           ├── order-history.jsp
│           │           └── order-detail.jsp
├── build.gradle
├── webShopDB.sql
└── README.md
```

## Cài đặt và chạy dự án

### Yêu cầu hệ thống
- Java 8 trở lên
- MySQL 5.7 trở lên
- Apache Tomcat 9.0 trở lên
- Gradle 6.0 trở lên

### Bước 1: Cài đặt cơ sở dữ liệu
1. Tạo database MySQL mới
2. Import file `webShopDB.sql` để tạo bảng và dữ liệu mẫu

### Bước 2: Cấu hình kết nối database
1. Mở file `src/main/java/com/example/storeproject/repository/DBConnection.java`
2. Cập nhật thông tin kết nối database:
   - URL: `jdbc:mysql://localhost:3306/webshop`
   - Username: `root`
   - Password: `your_password`

### Bước 3: Build và chạy dự án
```bash
# Build dự án
./gradlew build

# Chạy dự án
./gradlew tomcatRun
```

### Bước 4: Truy cập ứng dụng
- Mở trình duyệt và truy cập: `http://localhost:8080/StoreProject`

## API Endpoints

### Sản phẩm
- `GET /products` - Danh sách sản phẩm
- `GET /product/detail/{id}` - Chi tiết sản phẩm
- `GET /product/category/{id}` - Sản phẩm theo danh mục
- `GET /search?keyword={keyword}` - Tìm kiếm sản phẩm

### Giỏ hàng
- `GET /cart` - Xem giỏ hàng
- `POST /cart` - Thêm/cập nhật/xóa sản phẩm trong giỏ hàng

### Đơn hàng
- `GET /order` - Lịch sử đơn hàng
- `GET /order/detail/{id}` - Chi tiết đơn hàng
- `POST /order` - Tạo đơn hàng mới

## Cơ sở dữ liệu

### Các bảng chính
- `nguoi_dung` - Thông tin người dùng
- `san_pham` - Thông tin sản phẩm
- `danh_muc` - Danh mục sản phẩm
- `gio_hang` - Giỏ hàng
- `chi_tiet_gio_hang` - Chi tiết giỏ hàng
- `don_hang` - Đơn hàng
- `chi_tiet_don_hang` - Chi tiết đơn hàng
- `khuyen_mai` - Khuyến mãi

### Dữ liệu mẫu
Dự án đã bao gồm dữ liệu mẫu với:
- 4 danh mục sản phẩm
- 6 sản phẩm mẫu
- 4 tài khoản người dùng
- 2 đơn hàng mẫu

## Tính năng bổ sung có thể phát triển

### 1. Xác thực và phân quyền
- Đăng nhập/đăng ký người dùng
- Phân quyền admin/user
- Quản lý phiên đăng nhập

### 2. Quản lý sản phẩm nâng cao
- Upload hình ảnh sản phẩm
- Quản lý kho hàng
- Đánh giá và bình luận sản phẩm

### 3. Thanh toán
- Tích hợp cổng thanh toán
- Quản lý địa chỉ giao hàng
- Theo dõi vận chuyển

### 4. Báo cáo và thống kê
- Thống kê doanh thu
- Báo cáo bán hàng
- Dashboard admin

## Đóng góp

Để đóng góp vào dự án:
1. Fork repository
2. Tạo branch mới cho tính năng
3. Commit thay đổi
4. Push lên branch
5. Tạo Pull Request

## Giấy phép

Dự án này được phát hành dưới giấy phép MIT.

## Liên hệ

Nếu có câu hỏi hoặc góp ý, vui lòng tạo issue trên GitHub repository.

---

**Lưu ý**: Đây là phiên bản demo với các chức năng cơ bản. Để sử dụng trong môi trường production, cần bổ sung thêm các tính năng bảo mật và tối ưu hóa hiệu suất.
