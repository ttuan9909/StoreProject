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
        body { padding: 24px; }
        .table thead th { white-space: nowrap; }
        .modal .form-label { font-weight: 600; }
        .img-thumb { width: 56px; height: 56px; object-fit: cover; border-radius: 8px; }
        .price { white-space: nowrap; }
        .desc-cell { max-width: 380px; }
    </style>
</head>
<body>

<div class="container">
    <h3 class="mb-3">Quản lý Sản phẩm</h3>

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
            <button class="btn btn-primary" type="submit">Tìm</button>
        </form>

        <button class="btn btn-success" data-bs-toggle="modal" data-bs-target="#modalCreate">
            + Thêm sản phẩm
        </button>
    </div>

    <!-- Table -->
    <div class="table-responsive">
        <table class="table table-bordered align-middle">
            <thead class="table-light">
            <tr>
                <th>ID</th>
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
                            <td>${p.productId}</td>
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
                            <td>
                                <c:out value="${p.dateCreated}"/>
                            </td>
                            <td>
                                <button
                                        class="btn btn-sm btn-warning me-1"
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
                                        class="btn btn-sm btn-danger"
                                        data-bs-toggle="modal"
                                        data-bs-target="#modalDelete"
                                        data-id="${p.productId}"
                                        data-name="${p.productName}">
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
                <button type="submit" class="btn btn-success">Lưu</button>
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
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
                <button type="submit" class="btn btn-warning">Cập nhật</button>
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
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
                <button type="submit" class="btn btn-danger">Xóa</button>
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
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
