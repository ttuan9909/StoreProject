<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Đăng ký</title>
</head>
<body>
<h2>Đăng ký: Thông tin cá nhân</h2>

<p>Username: <strong><c:out value="${pending_username}"/></strong></p>

<form action="${pageContext.request.contextPath}/admin/register" method="post">
    <input type="hidden" name="step" value="2"/>
    <div>
        <label>Họ tên:
            <input type="text" name="ho_ten">
        </label>
    </div>
    <div>
        <label>Email:
            <input type="email" name="email">
        </label>
    </div>
    <div>
        <label>Số điện thoại:
            <input type="text" name="so_dien_thoai">
        </label>
    </div>
    <div>
        <label>Địa chỉ:
            <input type="text" name="dia_chi">
        </label>
    </div>
    <div>
        <label>Vai trò:
            <select name="vai_tro">
                <option value="customer" selected>customer</option>
                <option value="admin">admin</option>
                <option value="staff">staff</option>
            </select>
        </label>
    </div>

    <button type="submit">Hoàn tất đăng ký</button>
</form>

<p style="color:red"><c:out value="${error}"/></p>

<p><a href="${pageContext.request.contextPath}/admin/register">Quay lại</a></p>
</body>
</html>
