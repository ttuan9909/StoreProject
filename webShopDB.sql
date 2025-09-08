create database if not exists webshop;
use webshop;

create table vi_tri (
    ma_vi_tri int primary key,
    ten_vi_tri varchar(45)
);

create table nguoi_dung (
    ma_nguoi_dung int auto_increment primary key,
    ten_dang_nhap varchar(50) unique not null,
    mat_khau varchar(255) not null,
    email varchar(100) unique,
    ho_ten varchar(100),
    so_dien_thoai varchar(20),
    dia_chi varchar(255),
    vai_tro enum('admin', 'staff', 'customer') default 'customer',
    ngay_tao datetime default current_timestamp,
    ma_vi_tri int,
    foreign key (ma_vi_tri) references vi_tri(ma_vi_tri)
);

create table danh_muc (
    ma_danh_muc int auto_increment primary key,
    ten_danh_muc varchar(100) not null
);

create table san_pham (
    ma_san_pham int auto_increment primary key,
    ten_san_pham varchar(150) not null,
    gia decimal(10,2) not null,
    so_luong int default 0,
    mo_ta text,
    hinh_anh varchar(255),
    ma_danh_muc int,
    ngay_tao datetime default current_timestamp,
    foreign key (ma_danh_muc) references danh_muc(ma_danh_muc)
);

create table khuyen_mai (
    ma_khuyen_mai int auto_increment primary key,
    ten_khuyen_mai varchar(100),
    mo_ta text,
    giam_phan_tram int check (giam_phan_tram between 0 and 100),
    ngay_bat_dau date,
    ngay_ket_thuc date
);

create table don_hang (
    ma_don_hang int auto_increment primary key,
    ma_nguoi_dung int,
    ngay_dat datetime default current_timestamp,
    trang_thai enum('cho_xu_ly','dang_xu_ly','hoan_thanh','huy') default 'cho_xu_ly',
    tong_tien decimal(10,2),
    ma_khuyen_mai int,
    foreign key (ma_nguoi_dung) references nguoi_dung(ma_nguoi_dung),
    foreign key (ma_khuyen_mai) references khuyen_mai(ma_khuyen_mai)
);

create table chi_tiet_don_hang (
    ma_don_hang int,
    ma_san_pham int,
    so_luong int not null,
    gia decimal(10,2) not null,
    primary key (ma_don_hang, ma_san_pham),
    foreign key (ma_don_hang) references don_hang(ma_don_hang),
    foreign key (ma_san_pham) references san_pham(ma_san_pham)
);

create table thanh_toan (
    ma_thanh_toan int auto_increment primary key,
    ma_don_hang int,
    phuong_thuc enum('tien_mat', 'chuyen_khoan', 'vi_dien_tu') not null,
    ngay_thanh_toan datetime default current_timestamp,
    so_tien decimal(10,2),
    trang_thai enum('da_thanh_toan', 'chua_thanh_toan') default 'chua_thanh_toan',
    foreign key (ma_don_hang) references don_hang(ma_don_hang)
);

-- BẢNG GIỎ HÀNG
create table gio_hang (
    ma_gio_hang int auto_increment primary key,
    ma_nguoi_dung int not null,
    ngay_tao datetime default current_timestamp,
    ngay_cap_nhat datetime default current_timestamp on update current_timestamp,
    unique key uk_cart_user (ma_nguoi_dung), -- mỗi user 1 giỏ
    constraint fk_cart_user
        foreign key (ma_nguoi_dung) references nguoi_dung(ma_nguoi_dung)
        on delete cascade
);

-- CÁC DÒNG SẢN PHẨM TRONG GIỎ
create table chi_tiet_gio_hang (
    ma_gio_hang int not null,
    ma_san_pham int not null,
    so_luong int not null check (so_luong > 0),
    gia decimal(10,2) null, 
    primary key (ma_gio_hang, ma_san_pham),
    constraint fk_cartitem_cart
        foreign key (ma_gio_hang) references gio_hang(ma_gio_hang)
        on delete cascade,
    constraint fk_cartitem_product
        foreign key (ma_san_pham) references san_pham(ma_san_pham)
        on delete restrict
);

-- Dữ liệu mẫu
-- Thêm dữ liệu cho bảng vi_tri
insert into vi_tri (ma_vi_tri, ten_vi_tri) values
(1, 'admin'),
(2, 'user'),
(3, 'staff');

-- Thêm dữ liệu cho bảng nguoi_dung
insert into nguoi_dung (ten_dang_nhap, mat_khau, email, ho_ten, so_dien_thoai, dia_chi, vai_tro, ma_vi_tri) values
('admin01', '123456', 'admin@example.com', 'Nguyễn Văn A', '0901234567', '123 Đường A, Hà Nội', 'admin', 1),
('staff01', '123456', 'staff@example.com', 'Trần Thị B', '0912345678', '456 Đường B, TP. HCM', 'staff', 2),
('user01', '123456', 'user01@example.com', 'Lê Văn C', '0923456789', '789 Đường C, Đà Nẵng', 'customer', 3),
('user02', '123456', 'user02@example.com', 'Phạm Thị D', '0934567890', '12 Nguyễn Huệ, Hà Nội', 'customer', 1);

-- Thêm dữ liệu cho bảng danh_muc
insert into danh_muc (ten_danh_muc) values
('Điện thoại'),
('Laptop'),
('Phụ kiện'),
('Đồng hồ');

-- Thêm dữ liệu cho bảng san_pham
insert into san_pham (ten_san_pham, gia, so_luong, mo_ta, hinh_anh, ma_danh_muc) values
('iPhone 15 Pro', 29990000, 50, 'Điện thoại Apple cao cấp', '', 1),
('Samsung Galaxy S23', 24990000, 40, 'Flagship Samsung mới nhất', '', 1),
('MacBook Pro M2', 45990000, 30, 'Laptop hiệu năng cao của Apple', '', 2),
('Dell XPS 13', 32990000, 25, 'Laptop siêu mỏng nhẹ', '', 2),
('Tai nghe AirPods Pro', 5490000, 100, 'Tai nghe không dây Apple', '', 3),
('Đồng hồ Apple Watch', 11990000, 60, 'Smartwatch Apple cao cấp', '', 4);

-- Thêm dữ liệu cho bảng khuyen_mai
insert into khuyen_mai (ten_khuyen_mai, mo_ta, giam_phan_tram, ngay_bat_dau, ngay_ket_thuc) values
('Giảm giá mùa hè', 'Khuyến mãi giảm giá 20%', 20, '2025-08-01', '2025-08-31'),
('Black Friday', 'Giảm giá sốc 50%', 50, '2025-11-25', '2025-11-30');

-- Thêm dữ liệu cho bảng don_hang
insert into don_hang (ma_nguoi_dung, trang_thai, tong_tien, ma_khuyen_mai) values
(3, 'cho_xu_ly', 29990000, 1), -- user01 mua iPhone 15
(4, 'dang_xu_ly', 51480000, null); -- user02 mua MacBook + AirPods

-- Thêm dữ liệu cho bảng chi_tiet_don_hang
insert into chi_tiet_don_hang (ma_don_hang, ma_san_pham, so_luong, gia) values
(1, 1, 1, 29990000),
(2, 3, 1, 45990000),
(2, 5, 1, 5490000);

-- Thêm dữ liệu cho bảng thanh_toan
insert into thanh_toan (ma_don_hang, phuong_thuc, so_tien, trang_thai) values
(1, 'chuyen_khoan', 29990000, 'da_thanh_toan'),
(2, 'vi_dien_tu', 51480000, 'chua_thanh_toan');

-- Thêm dữ liệu cho bảng gio_hang
insert into gio_hang (ma_nguoi_dung) values
(3),
(4);

-- Thêm dữ liệu cho bảng chi_tiet_gio_hang
insert into chi_tiet_gio_hang (ma_gio_hang, ma_san_pham, so_luong, gia) values
(1, 2, 1, 24990000), -- user01 cho Galaxy S23 vào giỏ
(2, 6, 2, 11990000); -- user02 cho 2 Apple Watch vào giỏ

-- ĐƠN HÀNG #3 (user01) - chờ xử lý
INSERT INTO don_hang (ma_nguoi_dung, trang_thai, tong_tien, ma_khuyen_mai)
VALUES (3, 'cho_xu_ly', 35970000, 1);         -- 24,990,000 + 2*5,490,000
SET @order3 := LAST_INSERT_ID();

INSERT INTO chi_tiet_don_hang (ma_don_hang, ma_san_pham, so_luong, gia) VALUES
(@order3, 2, 1, 24990000),   -- Samsung Galaxy S23
(@order3, 5, 2,  5490000);   -- AirPods Pro x2

INSERT INTO thanh_toan (ma_don_hang, phuong_thuc, so_tien, trang_thai)
VALUES (@order3, 'chuyen_khoan', 35970000, 'chua_thanh_toan');

-- ĐƠN HÀNG #4 (user02) - đã hoàn thành
INSERT INTO don_hang (ma_nguoi_dung, trang_thai, tong_tien, ma_khuyen_mai)
VALUES (4, 'hoan_thanh', 44980000, 2);        -- 32,990,000 + 11,990,000
SET @order4 := LAST_INSERT_ID();

INSERT INTO chi_tiet_don_hang (ma_don_hang, ma_san_pham, so_luong, gia) VALUES
(@order4, 4, 1, 32990000),   -- Dell XPS 13
(@order4, 6, 1, 11990000);   -- Apple Watch

INSERT INTO thanh_toan (ma_don_hang, phuong_thuc, so_tien, trang_thai)
VALUES (@order4, 'vi_dien_tu', 44980000, 'da_thanh_toan');

select * from thanh_toan;
select * from nguoi_dung;
select * from chi_tiet_don_hang;

-- BỔ SUNG DỮ LIỆU CHI TIẾT ĐƠN HÀNG
-- Đảm bảo @order3, @order4 tồn tại (nếu bạn chạy đoạn này độc lập)
SET @order3 := COALESCE(@order3, (
  SELECT ma_don_hang FROM don_hang
  WHERE ma_nguoi_dung = 3 AND trang_thai = 'cho_xu_ly'
  ORDER BY ma_don_hang DESC LIMIT 1
));
SET @order4 := COALESCE(@order4, (
  SELECT ma_don_hang FROM don_hang
  WHERE ma_nguoi_dung = 4 AND trang_thai = 'hoan_thanh'
  ORDER BY ma_don_hang DESC LIMIT 1
));

/* Đơn #1 (đã có: (1,1) iPhone 15 Pro) -> thêm AirPods Pro */
INSERT INTO chi_tiet_don_hang (ma_don_hang, ma_san_pham, so_luong, gia) VALUES
(1, 5, 1, 5490000);              -- + Tai nghe AirPods Pro

/* Đơn #2 (đã có: (2,3) MacBook Pro M2; (2,5) AirPods Pro) -> thêm Apple Watch */
INSERT INTO chi_tiet_don_hang (ma_don_hang, ma_san_pham, so_luong, gia) VALUES
(2, 6, 1, 11990000);             -- + Apple Watch

/* Đơn #3 (@order3) (đã có: (@order3,2) S23; (@order3,5) AirPods x2) -> thêm iPhone 15 Pro */
INSERT INTO chi_tiet_don_hang (ma_don_hang, ma_san_pham, so_luong, gia) VALUES
(@order3, 1, 1, 29990000);       -- + iPhone 15 Pro

/* Đơn #4 (@order4) (đã có: (@order4,4) XPS13; (@order4,6) Apple Watch) -> thêm AirPods Pro */
INSERT INTO chi_tiet_don_hang (ma_don_hang, ma_san_pham, so_luong, gia) VALUES
(@order4, 5, 1, 5490000);        -- + AirPods Pro

-- CẬP NHẬT LẠI TỔNG TIỀN CHO TẤT CẢ ĐƠN HÀNG ĐỂ KHỚP CHI TIẾT
UPDATE don_hang d
JOIN (
  SELECT ma_don_hang, SUM(so_luong * gia) AS tong
  FROM chi_tiet_don_hang
  GROUP BY ma_don_hang
) x ON x.ma_don_hang = d.ma_don_hang
SET d.tong_tien = x.tong;

-- Kiểm tra nhanh
SELECT * FROM chi_tiet_don_hang ORDER BY ma_don_hang, ma_san_pham;
SELECT * FROM don_hang ORDER BY ma_don_hang;
