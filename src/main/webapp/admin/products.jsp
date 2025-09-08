<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Quản lý Sản phẩm</title>
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"/>

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

        /* ===== Styling riêng trang sản phẩm ===== */
        .card{
            background:var(--mau_the);
            border:1px solid var(--mau_vien);
            border-radius:var(--bo_cong);
            box-shadow:var(--bong);
        }
        .table thead th { white-space: nowrap; }
        .modal .form-label { font-weight: 600; }
        .img-thumb { width: 56px; height: 56px; object-fit: cover; border-radius: 8px; }
        .price { white-space: nowrap; }
        .desc-cell { max-width: 380px; }

        /* Nút đẹp hơn một chút */
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
    <!-- ===== Sidebar ===== -->
    <aside class="ben_trai">
        <div class="ten_he">Bảng điều khiển quản trị</div>

<%--        <form class="o_tim_kiem" method="get" action="${pageContext.request.contextPath}/admin/order">--%>
<%--            <input type="text" name="q" placeholder="Tìm đơn hàng theo tên hoặc mã">--%>
<%--            <button type="submit">Tìm kiếm</button>--%>
<%--        </form>--%>

        <nav class="menu">
            <a class="muc" href="${pageContext.request.contextPath}/admin/orders">Đơn hàng</a>
            <a class="muc" href="${pageContext.request.contextPath}/admin/orders?q=cho_xu_ly">Đơn chờ xử lý</a>
            <a class="muc" href="${pageContext.request.contextPath}/admin/orders?q=da_duyet">Đơn đã duyệt</a>
            <a class="muc active" href="${pageContext.request.contextPath}/products">Sản phẩm</a>
            <a class="muc" href="${pageContext.request.contextPath}/admin/categories">Danh mục sản phẩm</a>
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
            <h3 class="mb-0">Quản lý Sản phẩm</h3>
            <a class="btn btn-outline-secondary btn-rounded" href="${pageContext.request.contextPath}/login">Đăng xuất</a>
        </div>

        <!-- Flash message -->
        <c:if test="${not empty sessionScope.flash}">
            <div class="alert alert-info alert-dismissible fade show" role="alert">
                    ${sessionScope.flash}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Đóng"></button>
            </div>
            <c:remove var="flash" scope="session"/>
        </c:if>

        <!-- Search + Add -->
        <div class="d-flex align-items-center justify-content-between mb-3">
            <form class="d-flex" method="get" action="${pageContext.request.contextPath}/products">
                <input type="hidden" name="action" value="search"/>
                <input type="text" class="form-control me-2" name="q" placeholder="Tìm theo tên..."
                       value="${q}">
                <button class="btn btn-primary btn-rounded" type="submit">Tìm</button>
            </form>

            <button class="btn btn-success btn-rounded" data-bs-toggle="modal" data-bs-target="#modalCreate">
                + Thêm sản phẩm
            </button>
        </div>

        <!-- Bảng dữ liệu -->
        <div class="card p-3">
            <div class="table-responsive">
                <table class="table table-bordered align-middle mb-0">
                    <thead class="table-light">
                    <tr>

                        <th>Ảnh</th>
                        <th>Tên sản phẩm</th>
                        <th class="text-end">Giá</th>
                        <th class="text-end">SL</th>
                        <th>Danh mục</th>
                        <th class="desc-cell">Mô tả</th>
                        <th>Ngày tạo</th>
                        <th style="width: 210px;">Hành động</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:choose>
                        <c:when test="${empty products}">
                            <tr>
                                <td colspan="9" class="text-center text-muted">Không có dữ liệu</td>
                            </tr>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="p" items="${products}">
                                <tr>

                                    <td>
                                        <c:if test="${not empty p.image}">
                                            <img class="img-thumb" src="${p.image}" alt="${p.productName}"/>
                                        </c:if>
                                    </td>
                                    <td>${p.productName}</td>
                                    <td class="text-end price">
                                            ${p.price}
                                    </td>
                                    <td class="text-end">${p.quantity}</td>
                                    <td>${p.categoryName}</td>
                                    <td class="small text-muted">${p.description}</td>
                                    <td><c:out value="${p.dateCreated}"/></td>
                                    <td>
                                        <div class="d-flex">
                                            <button
                                                    class="btn btn-sm btn-warning btn-rounded me-2"
                                                    data-bs-toggle="modal"
                                                    data-bs-target="#modalEdit"
                                                    data-id="${p.productId}"
                                                    data-name="${p.productName}"
                                                    data-price="${p.price}"
                                                    data-quantity="${p.quantity}"
                                                    data-categoryid="${p.categoryId}"  <%-- nếu DTO không có, bỏ dòng này --%>
                                                    data-categoryname="${p.categoryName}"
                                                    data-description='${fn:escapeXml(p.description)}'
                                                    data-image="${p.image}">
                                                Sửa
                                            </button>

                                            <button
                                                    class="btn btn-sm btn-danger btn-rounded"
                                                    data-bs-toggle="modal"
                                                    data-bs-target="#modalDelete"
                                                    data-id="${p.productId}"
                                                    data-name="${p.productName}">
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
    <div class="modal-dialog modal-lg">
        <form class="modal-content" method="post" action="${pageContext.request.contextPath}/products">
            <div class="modal-header">
                <h5 class="modal-title">Thêm sản phẩm</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Đóng"></button>
            </div>
            <div class="modal-body">
                <input type="hidden" name="action" value="create"/>
                <input type="hidden" name="q" value="${q}"/>

                <div class="row g-3">
                    <div class="col-md-6">
                        <label class="form-label">Tên sản phẩm</label>
                        <input name="name" class="form-control" required maxlength="200"/>
                    </div>
                    <div class="col-md-3">
                        <label class="form-label">Giá</label>
                        <input name="price" type="number" min="0" step="0.01" class="form-control" required/>
                    </div>
                    <div class="col-md-3">
                        <label class="form-label">Số lượng</label>
                        <input name="quantity" type="number" min="0" step="1" class="form-control" required/>
                    </div>

                    <div class="col-md-6">
                        <label class="form-label">Danh mục</label>
                        <select name="categoryId" class="form-select" required>
                            <option value="">-- Chọn danh mục --</option>
                            <c:forEach var="c" items="${categories}">
                                <option value="${c.categoryId}">${c.categoryName}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-md-6">
                        <label class="form-label">Ảnh (URL)</label>
                        <input name="image" class="form-control" placeholder="https://..."/>
                    </div>

                    <div class="col-12">
                        <label class="form-label">Mô tả</label>
                        <textarea name="description" class="form-control" rows="3" maxlength="1000"></textarea>
                    </div>
                </div>

                <c:if test="${not empty error}">
                    <div class="alert alert-danger mt-3">${error}</div>
                </c:if>
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
    <div class="modal-dialog modal-lg">
        <form class="modal-content" method="post" action="${pageContext.request.contextPath}/products">
            <div class="modal-header">
                <h5 class="modal-title">Sửa sản phẩm</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Đóng"></button>
            </div>
            <div class="modal-body">
                <input type="hidden" name="action" value="edit"/>
                <input type="hidden" name="id" id="edit-id"/>
                <input type="hidden" name="q" value="${q}"/>

                <div class="row g-3">
                    <div class="col-md-6">
                        <label class="form-label">Tên sản phẩm</label>
                        <input name="name" id="edit-name" class="form-control" required maxlength="200"/>
                    </div>
                    <div class="col-md-3">
                        <label class="form-label">Giá</label>
                        <input name="price" id="edit-price" type="number" min="0" step="0.01" class="form-control" required/>
                    </div>
                    <div class="col-md-3">
                        <label class="form-label">Số lượng</label>
                        <input name="quantity" id="edit-quantity" type="number" min="0" step="1" class="form-control" required/>
                    </div>

                    <div class="col-md-6">
                        <label class="form-label">Danh mục</label>
                        <select name="categoryId" id="edit-categoryId" class="form-select" required>
                            <option value="">-- Chọn danh mục --</option>
                            <c:forEach var="c" items="${categories}">
                                <option value="${c.categoryId}">${c.categoryName}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-md-6">
                        <label class="form-label">Ảnh (URL)</label>
                        <input name="image" id="edit-image" class="form-control" placeholder="https://..."/>
                    </div>

                    <div class="col-12">
                        <label class="form-label">Mô tả</label>
                        <textarea name="description" id="edit-description" class="form-control" rows="3" maxlength="1000"></textarea>
                    </div>
                </div>

                <c:if test="${not empty error}">
                    <div class="alert alert-danger mt-3">${error}</div>
                </c:if>
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
        <form class="modal-content" method="post" action="${pageContext.request.contextPath}/products">
            <div class="modal-header">
                <h5 class="modal-title text-danger">Xác nhận xóa</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Đóng"></button>
            </div>
            <div class="modal-body">
                <input type="hidden" name="action" value="delete"/>
                <input type="hidden" name="id" id="delete-id"/>
                <input type="hidden" name="q" value="${q}"/>
                <p>Bạn có chắc muốn xóa sản phẩm <b id="delete-name"></b> (ID: <span id="delete-id-text"></span>)?</p>
                <p class="text-muted mb-0">Lưu ý: Nếu sản phẩm đang được tham chiếu, thao tác sẽ thất bại.</p>
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
            const id = btn.getAttribute('data-id');
            const name = btn.getAttribute('data-name');
            const price = btn.getAttribute('data-price');
            const quantity = btn.getAttribute('data-quantity');
            const categoryId = btn.getAttribute('data-categoryid');
            const description = btn.getAttribute('data-description') || '';
            const image = btn.getAttribute('data-image') || '';

            modalEdit.querySelector('#edit-id').value = id;
            modalEdit.querySelector('#edit-name').value = name;
            modalEdit.querySelector('#edit-price').value = price;
            modalEdit.querySelector('#edit-quantity').value = quantity;
            modalEdit.querySelector('#edit-description').value = description;
            modalEdit.querySelector('#edit-image').value = image;

            const sel = modalEdit.querySelector('#edit-categoryId');
            if (sel && categoryId) sel.value = categoryId;
        });
    }

    // ===== Modal Delete =====
    const modalDelete = document.getElementById('modalDelete');
    if (modalDelete) {
        modalDelete.addEventListener('show.bs.modal', function (event) {
            const btn = event.relatedTarget;
            const id = btn.getAttribute('data-id');
            const name = btn.getAttribute('data-name');

            modalDelete.querySelector('#delete-id').value = id;
            modalDelete.querySelector('#delete-id-text').innerText = id;
            modalDelete.querySelector('#delete-name').innerText = name || '';
        });
    }
</script>
</body>
</html>
