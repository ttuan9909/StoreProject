<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Quản lý Danh mục</title>
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"/>

    <style>
        :root{
            /* Bảng màu nền sáng (đồng bộ với trang Sản phẩm) */
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
            font-family:system-ui,-apple-system,Segoe UI,Roboto,Inter,Helvetica,Arial,sans-serif;
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

        /* ===== Card/table/nút đẹp hơn ===== */
        .card{
            background:var(--mau_the);
            border:1px solid var(--mau_vien);
            border-radius:var(--bo_cong);
            box-shadow:var(--bong);
        }
        .table thead th { white-space: nowrap; }
        .modal .form-label { font-weight: 600; }

        .btn-rounded{
            border-radius:12px !important;
            box-shadow:0 2px 8px rgba(0,0,0,0.06);
        }
        .btn-primary, .btn-success, .btn-warning, .btn-danger, .btn-secondary{
            border:1px solid transparent;
        }
        .btn-primary:hover{ filter:brightness(0.95); }
        .btn-success:hover{ filter:brightness(0.95); }
        .btn-warning:hover{ filter:brightness(0.98); }
        .btn-danger:hover{ filter:brightness(0.95); }
        .btn-secondary:hover{ filter:brightness(0.98); }

        @media (max-width:1100px){
            .khung{grid-template-columns:1fr}
            .ben_trai{position:relative;height:auto}
        }
    </style>
</head>
<body>

<div class="khung">
    <!-- ===== Sidebar (đồng bộ) ===== -->
    <aside class="ben_trai">
        <div class="ten_he">Bảng điều khiển quản trị</div>

<%--        <form class="o_tim_kiem" method="get" action="${pageContext.request.contextPath}/admin/order">--%>
<%--            <input type="text" name="q" placeholder="Tìm đơn hàng theo tên hoặc mã">--%>
<%--            <button type="submit">Tìm kiếm</button>--%>
<%--        </form>--%>

        <nav class="menu">
            <a class="muc" href="${pageContext.request.contextPath}/admin/order">Đơn hàng</a>
            <a class="muc" href="${pageContext.request.contextPath}/admin/order?q=cho_xu_ly">Đơn chờ xử lý</a>
            <a class="muc" href="${pageContext.request.contextPath}/admin/order?q=da_duyet">Đơn đã duyệt</a>
            <a class="muc" href="${pageContext.request.contextPath}/admin/products">Sản phẩm</a>
            <a class="muc active" href="${pageContext.request.contextPath}/admin/categories">Danh mục sản phẩm</a>
            <a class="muc" href="${pageContext.request.contextPath}/admin/users">Người dùng</a>
            <a class="muc" href="${pageContext.request.contextPath}/admin/discount">Khuyến mãi</a>
            <a class="muc" href="${pageContext.request.contextPath}/admin/payment">Thanh toán</a>
            <a class="muc" href="${pageContext.request.contextPath}/admin/report">Báo cáo</a>
            <a class="muc" href="${pageContext.request.contextPath}/admin/setting">Cấu hình hệ thống</a>
        </nav>
    </aside>

    <!-- ===== Nội dung chính ===== -->
    <main class="phan_phai">
        <div class="d-flex align-items-center justify-content-between mb-3">
            <h3 class="mb-0">Quản lý Danh mục</h3>
            <a class="btn btn-outline-secondary btn-rounded" href="${pageContext.request.contextPath}/login">Đăng xuất</a>
        </div>

        <!-- Flash message -->
        <c:if test="${not empty sessionScope.flash}">
            <div class="alert alert-info alert-dismissible fade show" role="alert">
                    ${sessionScope.flash}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
            <c:remove var="flash" scope="session"/>
        </c:if>

        <!-- Search + Add -->
        <div class="d-flex align-items-center justify-content-between mb-3">
            <form class="d-flex" method="get" action="${pageContext.request.contextPath}/categories">
                <input type="text" class="form-control me-2" name="q" placeholder="Tìm theo tên..."
                       value="${q}">
                <button class="btn btn-primary btn-rounded" type="submit">Tìm</button>
            </form>

            <button class="btn btn-success btn-rounded" data-bs-toggle="modal" data-bs-target="#modalCreate">
                + Thêm danh mục
            </button>
        </div>

        <!-- Bảng -->
        <div class="card p-3">
            <div class="table-responsive">
                <table class="table table-bordered align-middle mb-0">
                    <thead class="table-light">
                    <tr>
                        <th>Tên danh mục</th>
                        <th style="width: 180px;">Hành động</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:choose>
                        <c:when test="${empty categories}">
                            <tr>
                                <td colspan="2" class="text-center text-muted">Không có dữ liệu</td>
                            </tr>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="c" items="${categories}">
                                <tr>
                                    <td>${c.categoryName}</td>
                                    <td>
                                        <div class="d-flex">
                                            <button
                                                    class="btn btn-sm btn-warning btn-rounded me-2"
                                                    data-bs-toggle="modal"
                                                    data-bs-target="#modalEdit"
                                                    data-id="${c.categoryId}"
                                                    data-name="${c.categoryName}">
                                                Sửa
                                            </button>

                                            <button
                                                    class="btn btn-sm btn-danger btn-rounded"
                                                    data-bs-toggle="modal"
                                                    data-bs-target="#modalDelete"
                                                    data-id="${c.categoryId}"
                                                    data-name="${c.categoryName}">
                                                Xóa
                                            </button>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                    </tbody>
                </table>
            </div>
        </div>
    </main>
</div>

<!-- Modal: Create -->
<div class="modal fade" id="modalCreate" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog">
        <form class="modal-content" method="post" action="${pageContext.request.contextPath}/categories">
            <div class="modal-header">
                <h5 class="modal-title">Thêm danh mục</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Đóng"></button>
            </div>
            <div class="modal-body">
                <input type="hidden" name="action" value="create"/>
                <input type="hidden" name="q" value="${q}"/>
                <div class="mb-3">
                    <label class="form-label">Tên danh mục</label>
                    <input name="name" class="form-control" required maxlength="100" />
                </div>
            </div>
            <div class="modal-footer">
                <button type="submit" class="btn btn-success btn-rounded">Lưu</button>
                <button type="button" class="btn btn-secondary btn-rounded" data-bs-dismiss="modal">Hủy</button>
            </div>
        </form>
    </div>
</div>

<!-- Modal: Edit -->
<div class="modal fade" id="modalEdit" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog">
        <form class="modal-content" method="post" action="${pageContext.request.contextPath}/categories">
            <div class="modal-header">
                <h5 class="modal-title">Sửa danh mục</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Đóng"></button>
            </div>
            <div class="modal-body">
                <input type="hidden" name="action" value="update"/>
                <input type="hidden" name="id" id="edit-id"/>
                <input type="hidden" name="q" value="${q}"/>
                <div class="mb-3">
                    <label class="form-label">Tên danh mục</label>
                    <input name="name" id="edit-name" class="form-control" required maxlength="100"/>
                </div>
            </div>
            <div class="modal-footer">
                <button type="submit" class="btn btn-warning btn-rounded">Cập nhật</button>
                <button type="button" class="btn btn-secondary btn-rounded" data-bs-dismiss="modal">Hủy</button>
            </div>
        </form>
    </div>
</div>

<!-- Modal: Delete -->
<div class="modal fade" id="modalDelete" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog">
        <form class="modal-content" method="post" action="${pageContext.request.contextPath}/categories">
            <div class="modal-header">
                <h5 class="modal-title text-danger">Xác nhận xóa</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Đóng"></button>
            </div>
            <div class="modal-body">
                <input type="hidden" name="action" value="delete"/>
                <input type="hidden" name="id" id="delete-id"/>
                <input type="hidden" name="q" value="${q}"/>
                <p>Bạn có chắc chắn muốn xóa danh mục <b id="delete-label"></b> (ID: <span id="delete-id-text"></span>)?</p>
                <p class="text-muted mb-0">Lưu ý: Nếu danh mục đang được tham chiếu bởi sản phẩm, thao tác sẽ thất bại.</p>
            </div>
            <div class="modal-footer">
                <button type="submit" class="btn btn-danger btn-rounded">Xóa</button>
                <button type="button" class="btn btn-secondary btn-rounded" data-bs-dismiss="modal">Hủy</button>
            </div>
        </form>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script>
    // ===== Modal Edit =====
    const modalEdit = document.getElementById('modalEdit');
    if (modalEdit) {
        modalEdit.addEventListener('show.bs.modal', function (event) {
            const btn = event.relatedTarget;
            modalEdit.querySelector('#edit-id').value = btn.getAttribute('data-id');
            modalEdit.querySelector('#edit-name').value = btn.getAttribute('data-name');
        });
    }

    // ===== Modal Delete =====
    const modalDelete = document.getElementById('modalDelete');
    if (modalDelete) {
        modalDelete.addEventListener('show.bs.modal', function (event) {
            const btn = event.relatedTarget;
            const id = btn.getAttribute('data-id');
            const name = btn.getAttribute('data-name') || '';
            modalDelete.querySelector('#delete-id').value = id;
            modalDelete.querySelector('#delete-id-text').innerText = id;
            modalDelete.querySelector('#delete-label').innerText = name;
        });
    }
</script>
</body>
</html>
