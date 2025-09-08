<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*, com.example.storeproject.entity.User" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    // ===== Lấy dữ liệu từ request =====
    List<User> users = (List<User>) request.getAttribute("users");

    Integer currentPage = (Integer) request.getAttribute("page");
    if (currentPage == null) currentPage = 1;

    Integer pageSize = (Integer) request.getAttribute("size");
    if (pageSize == null) pageSize = 10;

    Integer total = (Integer) request.getAttribute("total");
    if (total == null) total = 0;

    String q = (String) request.getAttribute("q");
    if (q == null) q = "";

    String roleFilter = (String) request.getAttribute("role");
    if (roleFilter == null) roleFilter = "";

    int totalPages = (int) Math.ceil((double) total / pageSize);
    if (totalPages == 0) totalPages = 1;

    String ctx = request.getContextPath();
    String baseUrl = ctx + "/admin/users";
%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Quản lý người dùng</title>
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"/>

    <style>
        :root{
            /* Light theme đồng bộ */
            --mau_nen:#ffffff;
            --mau_the:#ffffff;
            --mau_vien:#e5e7eb;
            --mau_chu:#111827;
            --mau_phu:#6b7280;
            --mau_nut:#2563eb;
            --mau_nut_hover:#1d4ed8;
            --mau_thanh:#f3f4f6;
            --bo_cong:14px;
            --bong:0 6px 18px rgba(0,0,0,0.08);
            --hover_soft:#f9fafb;
            --hover_soft_alt:#eef2ff;
            --input_bg:#ffffff;
        }

        *{box-sizing:border-box}
        body{
            margin:0;
            background:var(--mau_nen);
            color:var(--mau_chu);
            font-family:system-ui,-apple-system,Segoe UI,Roboto,Ubuntu,Inter,Helvetica,Arial,sans-serif;
        }

        /* ===== Layout có sidebar ===== */
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
            letter-spacing:.2px;
            margin-bottom:18px;
            color:var(--mau_chu);
        }
        .o_tim_kiem{
            display:flex; gap:8px; margin-bottom:18px;
        }
        .o_tim_kiem input{
            flex:1; padding:10px 12px; border-radius:10px;
            border:1px solid var(--mau_vien); background:var(--input_bg); color:var(--mau_chu);
            outline:none;
        }
        .o_tim_kiem input:focus{
            border-color:#c7d2fe; box-shadow:0 0 0 4px rgba(99,102,241,0.15);
        }
        .o_tim_kiem button{
            padding:10px 14px; border-radius:10px; border:0; background:var(--mau_nut); color:#fff; cursor:pointer;
        }
        .o_tim_kiem button:hover{ background:var(--mau_nut_hover); }

        .menu{ display:flex; flex-direction:column; gap:8px; }
        .muc{
            display:block; padding:12px 12px; color:var(--mau_chu); text-decoration:none;
            border-radius:10px; border:1px solid transparent;
        }
        .muc:hover{ background:var(--hover_soft); border-color:var(--mau_vien); }
        .muc.active{ background:var(--hover_soft_alt); border-color:#c7d2fe; }

        .phan_phai{ padding:0; background:var(--mau_nen); }

        /* ===== Header bar (user + tiêu đề) ===== */
        .header-bar{
            display:flex; justify-content:space-between; align-items:center;
            padding:14px 24px; border-bottom:1px solid var(--mau_vien); background:#fff;
            position:sticky; top:0; z-index:2;
        }
        .user-info{ display:flex; align-items:center; gap:12px; }
        .user-avatar{
            width:32px; height:32px; border-radius:50%;
            display:flex; align-items:center; justify-content:center;
            background:#e5e7eb; color:#374151; font-weight:700;
        }
        .btn-logout{
            padding:8px 12px; border-radius:10px; border:1px solid var(--mau_vien);
            background:#fff; color:var(--mau_chu); text-decoration:none; font-size:14px;
        }
        .btn-logout:hover{ background:var(--hover_soft); border-color:#d1d5db; }

        /* ===== Card/Bảng/Nút ===== */
        .content-wrap{ padding:24px; }
        .card{
            background:var(--mau_the);
            border:1px solid var(--mau_vien);
            box-shadow:var(--bong);
            border-radius:var(--bo_cong);
            padding:18px;
        }
        .toolbar{ display:flex; gap:10px; flex-wrap:wrap; justify-content:space-between; margin-bottom:14px; }
        .row-flex{ display:flex; gap:10px; flex-wrap:wrap; align-items:center; }
        input[type="text"], input[type="number"], select{
            background:var(--input_bg); color:var(--mau_chu); border:1px solid var(--mau_vien);
            border-radius:10px; padding:10px 12px; outline:none; min-width:180px;
        }
        input[type="number"]{ width:110px; }

        .btn-rounded{ border-radius:12px !important; box-shadow:0 2px 8px rgba(0,0,0,0.06); }
        .btn-primary, .btn-success, .btn-warning, .btn-danger, .btn-secondary{ border:1px solid transparent; }
        .btn:hover{ filter:brightness(0.97); }
        .btn.small{ padding:6px 10px; font-size:12px; border-radius:8px; }

        table{ width:100%; border-collapse:separate; border-spacing:0; margin-top:8px; }
        thead th{
            text-align:left; font-size:13px; color:var(--mau_phu); font-weight:600;
            padding:12px; background:#f8fafc; position:sticky; top:0; z-index:1;
            border-bottom:1px solid var(--mau_vien);
        }
        tbody td{ padding:12px; border-bottom:1px solid var(--mau_vien); }
        tbody tr:hover{ background:#fafafa; }

        .badge{
            display:inline-block; padding:4px 8px; font-size:12px; border-radius:999px;
            border:1px solid var(--mau_vien); color:var(--mau_phu); background:#fff;
        }
        .badge.green{ color:#065f46; border-color:#a7f3d0; background:#d1fae5; }
        .badge.red{ color:#7f1d1d;  border-color:#fecaca; background:#fee2e2; }

        .flash{
            margin:10px 0; padding:10px 12px; border-radius:10px;
            background:#ecfdf5; color:#065f46; border:1px solid #a7f3d0;
        }

        .pagination{ margin-top:14px; display:flex; gap:8px; flex-wrap:wrap; align-items:center }
        .page-link{
            padding:8px 12px; border-radius:10px; border:1px solid var(--mau_vien); text-decoration:none; color:var(--mau_chu);
            background:#fff; display:inline-block; font-size:13px;
        }
        .page-link.active{ background:var(--mau_nut); color:#fff; border-color:transparent; }

        .action-group{ display:flex; gap:8px; align-items:center }
        form.inline{ display:inline }
        @media (max-width:1100px){
            .khung{ grid-template-columns:1fr; }
            .ben_trai{ position:relative; height:auto; }
        }
    </style>
</head>
<body>

<div class="khung">
    <!-- ===== Sidebar ===== -->
    <aside class="ben_trai">
        <div class="ten_he">Bảng điều khiển quản trị</div>
        <nav class="menu">
            <a class="muc" href="<%= ctx %>/admin/orders">Đơn hàng</a>
            <a class="muc" href="<%= ctx %>/admin/orders?q=cho_xu_ly">Đơn chờ xử lý</a>
            <a class="muc" href="<%= ctx %>/admin/orders?q=da_duyet">Đơn đã duyệt</a>
            <a class="muc" href="<%= ctx %>/admin/products">Sản phẩm</a>
            <a class="muc" href="<%= ctx %>/admin/categories">Danh mục sản phẩm</a>
            <a class="muc active" href="<%= ctx %>/admin/users">Người dùng</a>
            <a class="muc" href="<%= ctx %>/admin/discount">Khuyến mãi</a>
            <a class="muc" href="<%= ctx %>/admin/payment">Thanh toán</a>
            <a class="muc" href="<%= ctx %>/admin/report">Báo cáo</a>
            <a class="muc" href="<%= ctx %>/admin/setting">Cấu hình hệ thống</a>
        </nav>
    </aside>

    <!-- ===== Nội dung chính ===== -->
    <main class="phan_phai">
        <!-- Header bar (tiêu đề + user + logout) -->
        <!-- Header bar (tiêu đề + user + logout) -->
        <div class="header-bar">
            <h2 class="mb-0">Quản lý người dùng</h2>

            <div class="user-info">
                <c:choose>
                    <c:when test="${not empty sessionScope.currentUser}">
                        <%-- Lấy tên hiển thị: fullName -> userName -> username --%>
                        <c:set var="displayName"
                               value="${not empty sessionScope.currentUser.fullName
                               ? sessionScope.currentUser.fullName
                               : (not empty sessionScope.currentUser.userName
                                  ? sessionScope.currentUser.userName
                                  : sessionScope.currentUser.username)}" />

                        <div class="user-avatar">
                            <c:out value="${fn:toUpperCase(fn:substring(displayName,0,1))}" />
                        </div>

                        <!-- BỎ d-none d-md-block để luôn hiển thị tên -->
                        <div>
                            <div class="fw-semibold"><c:out value="${displayName}"/></div>

                        </div>


                        <a class="btn-logout" href="<%= ctx %>/logout">Đăng xuất</a>
                    </c:when>
                    <c:otherwise>
                        <a class="btn-logout" href="<%= ctx %>/login">Đăng nhập</a>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>


        <div class="content-wrap">
            <% String flash = (String) session.getAttribute("flash");
                if (flash != null) { %>
            <div class="flash"><%= flash %></div>
            <%  session.removeAttribute("flash"); } %>

            <div class="card">
                <!-- Toolbar (bạn có thể bật lại các form lọc nếu cần) -->
                <div class="toolbar">
                    <div class="row-flex"></div>
                </div>

                <!-- Bảng danh sách -->
                <div class="table-responsive">
                    <table class="table align-middle mb-0">
                        <thead class="table-light">
                        <tr>
                            <th>Họ tên</th>
                            <th>Email</th>
                            <th>Vai trò</th>
                            <th>Ngày tạo</th>
                            <th>Last login</th>
                            <th>Active</th>
                            <th>Thao tác</th>
                        </tr>
                        </thead>
                        <tbody>
                        <%
                            if (users == null || users.isEmpty()) {
                        %>
                        <tr><td colspan="9" class="text-center text-muted py-3">Không có dữ liệu</td></tr>
                        <%
                        } else {
                            for (User u : users) {
                                boolean isActive = Boolean.TRUE.equals(u.getActive());
                        %>
                        <tr>
                            <td><%= u.getFullName() %></td>
                            <td><%= u.getEmail() %></td>
                            <td><span class="badge"><%= u.getRole() != null ? u.getRole() : "-" %></span></td>
                            <td><%= u.getCreatedAt() != null ? u.getCreatedAt() : "" %></td>
                            <td><%= u.getLastLogin() != null ? u.getLastLogin() : "" %></td>
                            <td>
                                <% if (isActive) { %>
                                <span class="badge green">Active</span>
                                <% } else { %>
                                <span class="badge red">InActive</span>
                                <% } %>
                            </td>
                            <td>
                                <div class="action-group">
                                    <form method="post" action="<%= baseUrl %>" class="inline">
                                        <input type="hidden" name="action" value="toggle-active"/>
                                        <input type="hidden" name="id" value="<%= u.getUserId() %>"/>
                                        <input type="hidden" name="activeTo" value="<%= isActive ? 0 : 1 %>"/>
                                        <button class="btn small btn-rounded <%= isActive ? "btn-danger" : "btn-success" %>" type="submit"
                                                onclick="return confirm('<%= isActive ? "Vô hiệu hoá tài khoản này?" : "Kích hoạt tài khoản này?" %>')">
                                            <%= isActive ? "Vô hiệu hoá" : "Kích hoạt" %>
                                        </button>
                                    </form>
                                </div>
                            </td>
                        </tr>
                        <%
                                } // end for
                            } // end else
                        %>
                        </tbody>
                    </table>
                </div>

                <!-- Phân trang -->
                <div class="pagination">
                    <span class="badge">Trang: <%= currentPage %>/<%= totalPages %></span>
                    <%
                        for (int p = 1; p <= totalPages; p++) {
                            String href = baseUrl + "?page=" + p + "&size=" + pageSize +
                                    "&q=" + java.net.URLEncoder.encode(q, "UTF-8") +
                                    "&role=" + java.net.URLEncoder.encode(roleFilter, "UTF-8");
                    %>
                    <a class="page-link <%= (p == currentPage ? "active" : "") %>" href="<%= href %>"><%= p %></a>
                    <% } %>
                </div>
            </div>
        </div>
    </main>
</div>

</body>
</html>
