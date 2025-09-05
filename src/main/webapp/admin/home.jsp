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
            /* Bảng màu nền sáng */
            --mau_nen:#ffffff;          /* nền trang */
            --mau_the:#ffffff;          /* nền thẻ/card */
            --mau_vien:#e5e7eb;         /* viền xám nhạt */
            --mau_chu:#111827;          /* chữ chính (xám đậm) */
            --mau_phu:#6b7280;          /* chữ phụ */
            --mau_nut:#2563eb;          /* nút chính */
            --mau_nut_hover:#1d4ed8;    /* hover nút */
            --mau_thanh:#f3f4f6;        /* nền thanh bên */
            --bo_cong:14px;
            --bong:0 6px 18px rgba(0,0,0,0.08);
            --hover_soft:#f9fafb;       /* nền hover nhẹ */
            --hover_soft_alt:#eef2ff;   /* hover cho item chọn */
            --input_bg:#ffffff;
        }

        *{box-sizing:border-box}
        body{
            margin:0;
            background:var(--mau_nen);
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
            letter-spacing:0.2px;
            margin-bottom:18px;
            color:var(--mau_chu);
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
            background:var(--input_bg);
            color:var(--mau_chu);
            outline:none;
        }
        .o_tim_kiem input:focus{
            border-color:#c7d2fe;
            box-shadow:0 0 0 4px rgba(99,102,241,0.15);
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
            background:var(--hover_soft);
            border-color:var(--mau_vien);
        }
        .muc.active{
            background:var(--hover_soft_alt);
            border-color:#c7d2fe;
        }

        .phan_phai{
            padding:24px;
            background:var(--mau_nen);
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
            color:var(--mau_chu);
        }
        .nut_dang_xuat{
            padding:10px 14px;
            border-radius:10px;
            border:1px solid var(--mau_vien);
            background:#ffffff;
            color:var(--mau_chu);
            text-decoration:none;
        }
        .nut_dang_xuat:hover{
            background:var(--hover_soft);
            border-color:#d1d5db;
        }

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
            color:var(--mau_chu);
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

        .header-bar {
            display:flex;
            justify-content:space-between;
            align-items:center;
            padding:12px 20px;
            background:#ffffff;
            border-bottom:1px solid var(--mau_vien);
            box-shadow:0 2px 6px rgba(0,0,0,0.05);
        }
        .user-info {
            display:flex;
            align-items:center;
            gap:12px;
            font-size:14px;
            color:var(--mau_chu);
        }
        .user-avatar {
            width:32px; height:32px;
            border-radius:50%;
            background:#c7d2fe;
            display:flex;
            align-items:center;
            justify-content:center;
            font-weight:600;
            color:#4338ca;
        }
        .btn-logout {
            padding:6px 12px;
            border-radius:8px;
            border:1px solid var(--mau_vien);
            background:#ffffff;
            color:var(--mau_chu);
            text-decoration:none;
            font-size:13px;
        }
        .btn-logout:hover {
            background:var(--hover_soft);
            border-color:#d1d5db;
        }

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
            <a class="muc" href="${pageContext.request.contextPath}/admin/products">Sản phẩm</a>
            <a class="muc" href="${pageContext.request.contextPath}/admin/categories">Danh mục sản phẩm</a>
            <a class="muc" href="${pageContext.request.contextPath}/admin/users">Người dùng</a>
            <a class="muc" href="${pageContext.request.contextPath}/admin/discount">Khuyến mãi</a>
            <a class="muc" href="${pageContext.request.contextPath}/admin/payment">Thanh toán</a>
            <a class="muc" href="${pageContext.request.contextPath}/admin/report">Báo cáo</a>
            <a class="muc" href="${pageContext.request.contextPath}/admin/setting">Cấu hình hệ thống</a>
        </nav>
    </aside>

    <main class="phan_phai">
        <div class="header-bar">
            <div class="tieu_de">Bảng điều khiển quản trị</div>
            <div class="user-info">
                <div class="user-avatar">
                    <c:out value="${fn:substring(sessionScope.currentUser.fullName,0,1)}"/>
                </div>
                <span>
                    <c:out value="${sessionScope.currentUser.fullName != null ? sessionScope.currentUser.fullName : sessionScope.currentUser.userName}"/>
                </span>
                <a class="btn-logout" href="${pageContext.request.contextPath}/logout">Đăng xuất</a>
            </div>
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
