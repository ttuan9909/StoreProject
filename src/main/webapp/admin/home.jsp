<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Bảng điều khiển quản trị</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <style>
        :root{
            --mau_nen:#0f172a;
            --mau_the:#111827;
            --mau_vien:#1f2937;
            --mau_chu:#e5e7eb;
            --mau_phu:#9ca3af;
            --mau_nut:#2563eb;
            --mau_nut_hover:#1d4ed8;
            --mau_thanh:#0b1224;
            --bo_cong:14px;
            --bong:0 10px 30px rgba(0,0,0,0.35);
        }
        *{box-sizing:border-box}
        body{
            margin:0;
            background:linear-gradient(180deg,#0b132e 0%,#0f172a 60%,#0b1224 100%);
            color:var(--mau_chu);
            font-family:system-ui,-apple-system,Segoe UI,Roboto,Inter,Helvetica,Arial,sans-serif;
        }
        .khung{
            display:grid;
            grid-template-columns:260px 1fr;
            min-height:100vh;
        }
        .ben_trai{
            background:var(--mau_thanh);
            border-right:1px solid var(--mau_vien);
            padding:20px 16px;
            position:sticky;
            top:0;
            height:100vh;
        }
        .ten_he{
            font-size:20px;
            font-weight:700;
            letter-spacing:0.5px;
            margin-bottom:18px;
        }
        .o_tim_kiem{
            display:flex;
            gap:8px;
            margin-bottom:18px;
        }
        .o_tim_kiem input{
            flex:1;
            padding:10px 12px;
            border-radius:10px;
            border:1px solid var(--mau_vien);
            background:#0e162c;
            color:var(--mau_chu);
            outline:none;
        }
        .o_tim_kiem button{
            padding:10px 14px;
            border-radius:10px;
            border:0;
            background:var(--mau_nut);
            color:white;
            cursor:pointer;
        }
        .o_tim_kiem button:hover{background:var(--mau_nut_hover)}
        .menu{
            display:flex;
            flex-direction:column;
            gap:8px;
        }
        .muc{
            display:block;
            padding:12px 12px;
            color:var(--mau_chu);
            text-decoration:none;
            border-radius:10px;
            border:1px solid transparent;
        }
        .muc:hover{
            background:#141c33;
            border-color:var(--mau_vien);
        }
        .phan_phai{
            padding:24px;
        }
        .hang_tieu_de{
            display:flex;
            justify-content:space-between;
            align-items:center;
            margin-bottom:22px;
        }
        .tieu_de{
            font-size:24px;
            font-weight:800;
        }
        .nut_dang_xuat{
            padding:10px 14px;
            border-radius:10px;
            border:1px solid var(--mau_vien);
            background:#121a31;
            color:var(--mau_chu);
            text-decoration:none;
        }
        .nut_dang_xuat:hover{background:#141f3a}
        .luoi_thong_ke{
            display:grid;
            grid-template-columns:repeat(4,1fr);
            gap:16px;
            margin-bottom:24px;
        }
        .the{
            background:var(--mau_the);
            border:1px solid var(--mau_vien);
            border-radius:var(--bo_cong);
            box-shadow:var(--bong);
            padding:16px;
        }
        .ten_chi_so{
            font-size:13px;
            color:var(--mau_phu);
            margin-bottom:8px;
        }
        .gia_tri{
            font-size:22px;
            font-weight:800;
        }
        .luoi_hai_cot{
            display:grid;
            grid-template-columns:2fr 1fr;
            gap:16px;
        }
        .hang{
            display:flex;
            align-items:center;
            justify-content:space-between;
            padding:12px 0;
            border-bottom:1px dashed var(--mau_vien);
        }
        .hang:last-child{border-bottom:0}
        .nut_chuyen{
            display:inline-block;
            padding:10px 12px;
            border-radius:10px;
            background:var(--mau_nut);
            color:white;
            text-decoration:none;
        }
        .nut_chuyen:hover{background:var(--mau_nut_hover)}
        @media (max-width:1100px){
            .khung{grid-template-columns:1fr}
            .ben_trai{position:relative;height:auto}
            .luoi_thong_ke{grid-template-columns:repeat(2,1fr)}
            .luoi_hai_cot{grid-template-columns:1fr}
        }
    </style>
</head>
<body>
<div class="khung">
    <aside class="ben_trai">
        <div class="ten_he">Bảng điều khiển quản trị</div>

        <form class="o_tim_kiem" method="get" action="${pageContext.request.contextPath}/admin/order">
            <input type="text" name="q" placeholder="Tìm đơn hàng theo tên hoặc mã">
            <button type="submit">Tìm kiếm</button>
        </form>

        <nav class="menu">
            <a class="muc" href="${pageContext.request.contextPath}/admin/order">Đơn hàng</a>
            <a class="muc" href="${pageContext.request.contextPath}/admin/order?q=cho_xu_ly">Đơn chờ xử lý</a>
            <a class="muc" href="${pageContext.request.contextPath}/admin/order?q=da_duyet">Đơn đã duyệt</a>
            <a class="muc" href="${pageContext.request.contextPath}/products">Sản phẩm</a>
            <a class="muc" href="${pageContext.request.contextPath}/products?category=all">Danh mục sản phẩm</a>
            <a class="muc" href="${pageContext.request.contextPath}/admin/user">Người dùng</a>
            <a class="muc" href="${pageContext.request.contextPath}/admin/discount">Khuyến mãi</a>
            <a class="muc" href="${pageContext.request.contextPath}/admin/payment">Thanh toán</a>
            <a class="muc" href="${pageContext.request.contextPath}/admin/report">Báo cáo</a>
            <a class="muc" href="${pageContext.request.contextPath}/admin/setting">Cấu hình hệ thống</a>
        </nav>
    </aside>

    <main class="phan_phai">
        <div class="hang_tieu_de">
            <div class="tieu_de">Tổng quan</div>
            <a class="nut_dang_xuat" href="${pageContext.request.contextPath}/login">Đăng xuất</a>
        </div>

        <section class="luoi_thong_ke">
            <div class="the">
                <div class="ten_chi_so">Đơn hàng hôm nay</div>
                <div class="gia_tri">${requestScope.tong_don_hom_nay != null ? requestScope.tong_don_hom_nay : 0}</div>
            </div>
            <div class="the">
                <div class="ten_chi_so">Đơn chờ xử lý</div>
                <div class="gia_tri">${requestScope.tong_cho_xu_ly != null ? requestScope.tong_cho_xu_ly : 0}</div>
            </div>
            <div class="the">
                <div class="ten_chi_so">Tổng doanh thu</div>
                <div class="gia_tri">${requestScope.tong_doanh_thu != null ? requestScope.tong_doanh_thu : 0}</div>
            </div>
            <div class="the">
                <div class="ten_chi_so">Sản phẩm đang bán</div>
                <div class="gia_tri">${requestScope.tong_san_pham != null ? requestScope.tong_san_pham : 0}</div>
            </div>
        </section>

        <section class="luoi_hai_cot">
            <div class="the">
                <div class="hang" style="margin-bottom:8px;font-weight:700">Đơn hàng mới nhất</div>
                <c:forEach var="dong" items="${requestScope.don_moi}">
                    <div class="hang">
                        <div>
                            <div>Mã đơn: ${dong.orderId}</div>
                            <div style="color:var(--mau_phu)">Khách hàng: ${dong.customerName}</div>
                        </div>
                        <div>
                            <a class="nut_chuyen" href="${pageContext.request.contextPath}/admin/order/detail/${dong.orderId}">Xem</a>
                        </div>
                    </div>
                </c:forEach>
                <c:if test="${empty requestScope.don_moi}">
                    <div class="hang">
                        <div>Chưa có dữ liệu</div>
                    </div>
                </c:if>
            </div>

            <div class="the">
                <div class="hang" style="margin-bottom:8px;font-weight:700">Tác vụ nhanh</div>
                <div class="hang">
                    <div>Tạo báo cáo</div>
                    <div><a class="nut_chuyen" href="${pageContext.request.contextPath}/admin/report">Mở</a></div>
                </div>
                <div class="hang">
                    <div>Quản lý sản phẩm</div>
                    <div><a class="nut_chuyen" href="${pageContext.request.contextPath}/products">Mở</a></div>
                </div>
                <div class="hang">
                    <div>Quản lý đơn hàng</div>
                    <div><a class="nut_chuyen" href="${pageContext.request.contextPath}/admin/order">Mở</a></div>
                </div>
                <div class="hang">
                    <div>Cấu hình hệ thống</div>
                    <div><a class="nut_chuyen" href="${pageContext.request.contextPath}/admin/setting">Mở</a></div>
                </div>
            </div>
        </section>
    </main>
</div>
</body>
</html>
