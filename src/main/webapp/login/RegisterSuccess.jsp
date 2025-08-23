<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <title>Đăng ký thành công</title>
</head>
<body>
<h2 style="color:green;">🎉 Đăng ký tài khoản thành công!</h2>

<c:choose>
  <c:when test="${role == 'admin'}">
    <p>Xin chào <strong>Admin ${username}</strong> 👋. Tài khoản của bạn đã được tạo.</p>
  </c:when>
  <c:when test="${role == 'customer'}">
    <p>Xin chào <strong>${username}</strong> 👋. Chào mừng bạn trở thành khách hàng của chúng tôi.</p>
  </c:when>
  <c:otherwise>
    <p>Xin chào <strong>${username}</strong> 👋.</p>
  </c:otherwise>
</c:choose>

<p>Bạn có thể đăng nhập ngay bây giờ.</p>
<a href="${pageContext.request.contextPath}/login">👉 Đi đến trang đăng nhập</a>
</body>
</html>
