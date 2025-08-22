<%--
  Created by IntelliJ IDEA.
  User: LEDAT
  Date: 8/22/2025
  Time: 12:14 PM
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>Order List</title>
</head>
<body>
<h2>All Orders</h2>

<form action="${pageContext.request.contextPath}/admin/order" method="get">
  <input type="text" name="keyword" placeholder="Search by order ID or customer name"/>
  <input type="submit" value="Search"/>
</form>

<table border="1" cellpadding="10" cellspacing="0">
  <thead>
  <tr>
    <th>ID</th>
    <th>Customer</th>
    <th>Created At</th>
    <th>Status</th>
    <th>Total</th>
    <th>Actions</th>
  </tr>
  </thead>
  <tbody>
  <c:forEach items="${orders}" var="o">
    <tr>
      <td>${o.orderId}</td>
      <td>${o.customerName}</td>
      <td>${o.createdAt}</td>
      <td>
        <c:choose>
          <c:when test="${o.status == 0}">Chờ xử lý</c:when>
          <c:when test="${o.status == 1}">Đã duyệt</c:when>
          <c:otherwise>Khác</c:otherwise>
        </c:choose>
      </td>
      <td>${o.total}</td>
      <td>
        <a href="${pageContext.request.contextPath}/admin/order?action=detail&orderId=${o.orderId}">View Detail</a>
        <c:if test="${o.status == 0}">
          <form action="${pageContext.request.contextPath}/admin/order" method="post" style="display:inline">
            <input type="hidden" name="action" value="approve"/>
            <input type="hidden" name="orderId" value="${o.orderId}"/>
            <input type="submit" value="Approve"/>
          </form>
        </c:if>
      </td>
    </tr>
  </c:forEach>
  </tbody>
</table>

</body>
</html>
