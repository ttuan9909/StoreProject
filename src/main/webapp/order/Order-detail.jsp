<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Quản trị - Chi tiết đơn #${order.orderId}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container py-4">
  <div class="d-flex align-items-center justify-content-between mb-3">
    <h3 class="mb-0">Đơn hàng #${order.orderId}</h3>
    <div class="d-flex gap-2">
      <a class="btn btn-outline-secondary"
         href="${pageContext.request.contextPath}/admin/order">Quay lại</a>

      <c:if test="${order.orderStatus ne 'hoan_thanh' && order.orderStatus ne 'huy'}">
        <form method="post" action="${pageContext.request.contextPath}/admin/order" class="d-inline">
          <input type="hidden" name="action" value="approve"/>
          <input type="hidden" name="orderId" value="${order.orderId}"/>
          <button class="btn btn-success"
                  onclick="return confirm('Xác nhận duyệt đơn #${order.orderId}?');">
            Duyệt đơn
          </button>
        </form>
      </c:if>
    </div>
  </div>

  <div class="card">
    <div class="table-responsive">
      <table class="table align-middle mb-0">
        <thead class="table-light">
        <tr>
          <th style="width:120px">Mã SP</th>
          <th>Tên sản phẩm</th>
          <th style="width:120px">SL</th>
          <th style="width:160px">Giá</th>
          <th style="width:140px" class="text-end">Thao tác</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="d" items="${details}">
          <tr>
            <td>${d.productId}</td>
            <td><c:out value="${d.productName}"/></td>
            <td>${d.quantity}</td>
            <td>
              <fmt:formatNumber value="${d.price}" type="currency" currencySymbol="₫" pattern="#,##0 ₫"/>
            </td>
            <td class="text-end">
              <form method="post" action="${pageContext.request.contextPath}/admin/order"
                    class="d-inline"
                    onsubmit="return confirm('Xoá SP ${d.productId} khỏi đơn #${order.orderId}?');">
                <input type="hidden" name="action" value="deleteItem"/>
                <input type="hidden" name="orderId" value="${order.orderId}"/>
                <input type="hidden" name="productId" value="${d.productId}"/>
                <button class="btn btn-sm btn-outline-danger">Xoá</button>
              </form>
            </td>
          </tr>
        </c:forEach>
        <c:if test="${empty details}">
          <tr><td colspan="5" class="text-center text-muted py-4">Không có chi tiết đơn</td></tr>
        </c:if>
        </tbody>
      </table>
    </div>
  </div>
</div>
</body>
</html>
