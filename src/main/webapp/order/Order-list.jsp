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
  <title>Danh sách đơn hàng</title>
</head>
<body>
<h2>Danh sách đơn hàng</h2>
<form method="get" action="${pageContext.request.contextPath}/admin/order">
  <input type="text" name="q" value="${q}" placeholder="Tìm theo tên khách hoặc mã đơn hàng">
  <button type="submit">Tìm kiếm</button>
</form>
<table border="1" cellspacing="0" cellpadding="6">
  <thead>
  <tr>
    <th>Mã đơn</th>
    <th>Mã người dùng</th>
    <th>Tên khách hàng</th>
    <th>Ngày đặt</th>
    <th>Trạng thái</th>
    <th>Tổng tiền</th>
    <th>Chi tiết</th>
  </tr>
  </thead>
  <tbody>
  <c:forEach var="o" items="${orders}">
    <tr>
      <td>${o.orderId}</td>
      <td>${o.userId}</td>
      <td>${o.customerName}</td>
      <td><c:out value="${o.orderDate}"/></td>
      <td>${o.orderStatus}</td>
      <td>${o.totalPrice}</td>
      <td><a href="${pageContext.request.contextPath}/admin/order/detail/${o.orderId}">Xem</a></td>
    </tr>
  </c:forEach>
  <c:if test="${empty orders}">
    <tr><td colspan="7">Không có dữ liệu</td></tr>
  </c:if>
  </tbody>
</table>
<p><a href="${pageContext.request.contextPath}/home.jsp">Trang chủ Admin</a></p>
</body>
</html>
