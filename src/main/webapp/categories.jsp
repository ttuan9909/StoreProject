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
        body { padding: 24px; }
        .table thead th { white-space: nowrap; }
        .modal .form-label { font-weight: 600; }
    </style>
</head>
<body>

<div class="container">
    <h3 class="mb-3">Quản lý Danh mục</h3>

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
            <button class="btn btn-primary" type="submit">Tìm</button>
        </form>

        <button class="btn btn-success" data-bs-toggle="modal" data-bs-target="#modalCreate">
            + Thêm danh mục
        </button>
    </div>

    <!-- Table -->
    <div class="table-responsive">
        <table class="table table-bordered align-middle">
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
                        <td colspan="3" class="text-center text-muted">Không có dữ liệu</td>
                    </tr>
                </c:when>
                <c:otherwise>
                    <c:forEach var="c" items="${categories}">
                        <tr>
                            <td>${c.categoryName}</td>
                            <td>
                                <button
                                        class="btn btn-sm btn-warning me-1"
                                        data-bs-toggle="modal"
                                        data-bs-target="#modalEdit"
                                        data-id="${c.categoryId}"
                                        data-name="${c.categoryName}">
                                    Sửa
                                </button>

                                <button
                                        class="btn btn-sm btn-danger"
                                        data-bs-toggle="modal"
                                        data-bs-target="#modalDelete"
                                        data-id="${c.categoryId}">
                                    Xóa
                                </button>
                            </td>
                        </tr>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
            </tbody>
        </table>
    </div>
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
                <button type="submit" class="btn btn-success">Lưu</button>
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
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
                <button type="submit" class="btn btn-warning">Cập nhật</button>
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
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
                <button type="submit" class="btn btn-danger">Xóa</button>
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
            </div>
        </form>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script>
    // Đổ data vào modal Edit
    const modalEdit = document.getElementById('modalEdit');
    modalEdit.addEventListener('show.bs.modal', function (event) {
        const btn = event.relatedTarget;
        const id = btn.getAttribute('data-id');
        const name = btn.getAttribute('data-name');
        modalEdit.querySelector('#edit-id').value = id;
        modalEdit.querySelector('#edit-name').value = name;
    });

    // Đổ data vào modal Delete
    const modalDelete = document.getElementById('modalDelete');
    modalDelete.addEventListener('show.bs.modal', function (event) {
        const btn = event.relatedTarget;
        const id = btn.getAttribute('data-id');
        modalDelete.querySelector('#delete-id').value = id;
        modalDelete.querySelector('#delete-id-text').innerText = id;

        // Lấy tên ngay ở hàng tương ứng (nếu muốn hiển thị)
        const tr = btn.closest('tr');
        const name = tr ? tr.children[1].innerText : '';
        modalDelete.querySelector('#delete-label').innerText = name;
    });
</script>
</body>
</html>
