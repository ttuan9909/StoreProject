<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
</head>
<body>
<h2>Đăng nhập</h2>

<form action="${pageContext.request.contextPath}/login" method="post" accept-charset="UTF-8">
    <div>
        <label>Username:
            <input type="text" name="username" required>
        </label>
    </div>
    <div>
        <label>Password:
            <input type="password" name="password" required>
        </label>
    </div>
    <button type="submit">Đăng nhập</button>
</form>

<p style="color:red"><c:out value="${message}"/></p>

<p>
    Chưa có tài khoản?
    <a href="${pageContext.request.contextPath}/admin/register">Đăng ký</a>
</p>
</body>
</html>
