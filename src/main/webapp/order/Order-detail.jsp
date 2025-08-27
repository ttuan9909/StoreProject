<%--
  Created by IntelliJ IDEA.
  User: LEDAT
  Date: 8/22/2025
  Time: 12:14 PM
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <title>Chi tiết đơn hàng</title>
</head>
<body>
<h2>Chi tiết đơn hàng</h2>
<c:if test="${not empty order}">
  <p>Mã đơn: ${order.orderId}</p>
  <p>Mã người dùng: ${order.userId}</p>
  <p>Trạng thái: ${order.orderStatus}</p>
  <p>Tổng tiền: ${order.totalPrice}</p>
  <form method="post" action="${pageContext.request.contextPath}/admin/order">
    <input type="hidden" name="action" value="approve">
    <input type="hidden" name="orderId" value="${order.orderId}">
    <button type="submit">Duyệt đơn</button>
  </form>
</c:if>
<h3>Sản phẩm trong đơn</h3>
<table border="1" cellspacing="0" cellpadding="6">
  <thead>
  <tr>
    <th>Mã sản phẩm</th>
    <th>Tên sản phẩm</th>
    <th>Số lượng</th>
    <th>Giá</th>
    <th>Xoá khỏi đơn</th>
  </tr>
  </thead>
  <tbody>
  <c:forEach var="d" items="${details}">
    <tr>
      <td>${d.productId}</td>
      <td>${d.productName}</td>
      <td>${d.quantity}</td>
      <td>${d.price}</td>
      <td>
        <form method="post" action="${pageContext.request.contextPath}/admin/order" onsubmit="return confirm('Xoá sản phẩm này khỏi đơn?');">
          <input type="hidden" name="action" value="deleteItem">
          <input type="hidden" name="orderId" value="${order.orderId}">
          <input type="hidden" name="productId" value="${d.productId}">
          <button type="submit">Xoá</button>
        </form>
      </td>
    </tr>
  </c:forEach>
  <c:if test="${empty details}">
    <tr><td colspan="5">Không có sản phẩm</td></tr>
  </c:if>
  </tbody>
</table>
<p><a href="${pageContext.request.contextPath}/admin/order">Quay lại danh sách</a></p>
</body>
</html>
