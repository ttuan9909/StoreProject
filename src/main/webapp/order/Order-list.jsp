<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <title>Quản trị - Danh sách đơn hàng</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">
      <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container py-4">
  <div class="d-flex align-items-center justify-content-between mb-3">
    <h3 class="mb-0">Danh sách đơn hàng</h3>
    <a class="btn btn-outline-secondary" href="${pageContext.request.contextPath}/admin">Về bảng điều khiển</a>
  </div>

  <form class="row g-2 mb-3" method="get" action="${pageContext.request.contextPath}/admin/order">
    <div class="col-md-4">
      <input type="text" class="form-control" name="q" value="${param.q}" placeholder="Tìm theo tên KH hoặc mã đơn">
    </div>
    <div class="col-md-3">
      <select class="form-select" name="status">
        <option value="" ${empty param.status ? 'selected' : ''}>-- Tất cả trạng thái --</option>
        <option value="cho_xu_ly"  ${param.status == 'cho_xu_ly'  ? 'selected' : ''}>Đơn chờ xử lý</option>
        <option value="dang_xu_ly" ${param.status == 'dang_xu_ly' ? 'selected' : ''}>Đơn đang xử lý</option>
        <option value="hoan_thanh" ${param.status == 'hoan_thanh' ? 'selected' : ''}>Đơn đã hoàn thành</option>
        <option value="huy"        ${param.status == 'huy'        ? 'selected' : ''}>Đơn đã huỷ</option>
      </select>
    </div>
    <div class="col-md-auto">
      <button class="btn btn-primary">Lọc / Tìm</button>
      <a class="btn btn-outline-secondary" href="${pageContext.request.contextPath}/admin/order">Xoá lọc</a>
    </div>
  </form>

  <!-- Hiển thị thông báo -->
  <c:if test="${not empty param.msg}">
    <div class="alert alert-info alert-dismissible fade show" role="alert">
      <c:choose>
        <c:when test="${param.msg == 'processing_started'}">
          <strong>Thành công!</strong> Đơn hàng đã được bắt đầu xử lý.
        </c:when>
        <c:when test="${param.msg == 'ready_for_approval'}">
          <strong>Thành công!</strong> Đơn hàng đã sẵn sàng để duyệt.
        </c:when>
        <c:when test="${param.msg == 'approved'}">
          <strong>Thành công!</strong> Đơn hàng đã được duyệt.
        </c:when>
        <c:when test="${param.msg == 'order_cancelled'}">
          <strong>Thành công!</strong> Đơn hàng đã được huỷ.
        </c:when>
        <c:when test="${param.msg == 'processing_failed'}">
          <strong>Lỗi!</strong> Không thể bắt đầu xử lý đơn hàng.
        </c:when>
        <c:when test="${param.msg == 'status_update_failed'}">
          <strong>Lỗi!</strong> Không thể cập nhật trạng thái đơn hàng.
        </c:when>
        <c:when test="${param.msg == 'approve_failed'}">
          <strong>Lỗi!</strong> Không thể duyệt đơn hàng.
        </c:when>
        <c:when test="${param.msg == 'cancel_failed'}">
          <strong>Lỗi!</strong> Không thể huỷ đơn hàng.
        </c:when>
        <c:otherwise>
          <strong>Thông báo:</strong> ${param.msg}
        </c:otherwise>
      </c:choose>
      <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
  </c:if>

  <div class="card">
    <div class="table-responsive">
      <table class="table align-middle mb-0">
        <thead class="table-light">
        <tr>
          <th style="width:120px">Mã đơn</th>
          <th>Khách hàng</th>
          <th style="width:180px">Ngày đặt</th>
          <th style="width:160px">Trạng thái</th>
          <th style="width:160px">Tổng tiền</th>
          <th style="width:140px" class="text-end">Thao tác</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="o" items="${orders}">
          <tr>
            <td><strong>#${o.orderId}</strong></td>
            <td><c:out value="${o.customerName}"/></td>
            <td><c:out value="${o.orderDate}"/></td>
            <td>
              <c:choose>
                <c:when test="${o.orderStatus == 'cho_xu_ly'}">
                  <span class="badge bg-warning">Chờ xử lý</span>
                </c:when>
                <c:when test="${o.orderStatus == 'dang_xu_ly'}">
                  <span class="badge bg-info">Đang xử lý</span>
                </c:when>
                <c:when test="${o.orderStatus == 'cho_duyet'}">
                  <span class="badge bg-primary">Chờ duyệt</span>
                </c:when>
                <c:when test="${o.orderStatus == 'hoan_thanh'}">
                  <span class="badge bg-success">Hoàn thành</span>
                </c:when>
                <c:when test="${o.orderStatus == 'huy'}">
                  <span class="badge bg-danger">Đã huỷ</span>
                </c:when>
                <c:otherwise>
                  <span class="badge bg-secondary">${o.orderStatus}</span>
                </c:otherwise>
              </c:choose>
            </td>
            <td class="text-end">
              <fmt:formatNumber value="${o.totalPrice}" type="currency" currencySymbol="₫" 
                               pattern="#,##0 ₫" var="formattedPrice"/>
              <c:out value="${formattedPrice}"/>
            </td>
            <td class="text-end">
              <a class="btn btn-sm btn-outline-primary" href="${pageContext.request.contextPath}/admin/order/detail/${o.orderId}">Chi tiết</a>
            </td>
          </tr>
        </c:forEach>
        <c:if test="${empty orders}">
          <tr><td colspan="6" class="text-center text-muted py-4">Không có dữ liệu</td></tr>
        </c:if>
        </tbody>
      </table>
    </div>
  </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script>
document.addEventListener('DOMContentLoaded', function() {
    // Xử lý nút toggle chi tiết đơn hàng
    const toggleButtons = document.querySelectorAll('.toggle-details');
    
    toggleButtons.forEach(button => {
        button.addEventListener('click', function() {
            const orderId = this.getAttribute('data-order-id');
            const detailsRow = document.getElementById(`details-${orderId}`);
            const icon = this.querySelector('i');
            
            if (detailsRow.style.display === 'none') {
                // Mở rộng chi tiết
                detailsRow.style.display = 'table-row';
                icon.className = 'fas fa-chevron-up';
                this.innerHTML = '<i class="fas fa-chevron-up"></i> Thu gọn';
                this.classList.remove('btn-outline-primary');
                this.classList.add('btn-primary');
            } else {
                // Thu gọn chi tiết
                detailsRow.style.display = 'none';
                icon.className = 'fas fa-chevron-down';
                this.innerHTML = '<i class="fas fa-chevron-down"></i> Chi tiết';
                this.classList.remove('btn-primary');
                this.classList.add('btn-outline-primary');
            }
        });
    });
});
</script>
</body>
</html>
