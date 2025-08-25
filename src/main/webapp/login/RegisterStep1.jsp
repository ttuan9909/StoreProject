<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Đăng ký</title>
</head>
<body>
<h2>Đăng ký: Tài khoản</h2>

<form action="${pageContext.request.contextPath}/admin/register" method="post">
    <input type="hidden" name="step" value="1"/>
    <div>
        <label>Username:
            <input type="text" name="username" required value="<c:out value='${pending_username}'/>">
        </label>
    </div>
    <div>
        <label>Password:
            <input type="password" name="password" required>
        </label>
    </div>
    <button type="submit">Tiếp tục</button>
</form>

<p style="color:red"><c:out value="${error}"/></p>

<p><a href="${pageContext.request.contextPath}/login">Quay lại đăng nhập</a></p>
</body>
</html>
