<%--
  Created by IntelliJ IDEA.
  User: LEDAT
  Date: 8/22/2025
  Time: 12:14 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>Order Detail</title>
</head>
<body>
<h2>Order Detail - ID: ${orderId}</h2>
<a href="">← Back to Orders</a>

<table border="1" cellpadding="10" cellspacing="0">
  <thead>
  <tr>
    <th>Product</th>
    <th>Quantity</th>
    <th>Price</th>
    <th>Remove</th>
  </tr>
  </thead>
  <tbody>
  <c:forEach items="${details}" var="d">
    <tr>
      <td>${d.productName}</td>
      <td>${d.quantity}</td>
      <td>${d.price}</td>
      <td>
        <form action="admin/order" method="post">
          <input type="hidden" name="action" value="removeProduct"/>
          <input type="hidden" name="orderId" value="${d.orderId}"/>
          <input type="hidden" name="productId" value="${d.productId}"/>
          <input type="submit" value="Remove"/>
        </form>
      </td>
    </tr>
  </c:forEach>
  </tbody>
</table>

</body>
</html>

