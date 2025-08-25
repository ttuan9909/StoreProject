<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Trang chào</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            text-align: center;
            background-color: #f4f4f4;
            padding-top: 100px;
        }
        h1 {
            color: #333;
        }
        .btn {
            background-color: #4CAF50;
            color: white;
            padding: 12px 24px;
            text-decoration: none;
            border-radius: 6px;
            font-size: 16px;
            margin: 5px; /* cách nhau một chút */
            display: inline-block;
        }
        .btn:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
<h1>Chào mừng bạn đến với ứng dụng Quản lý Sản phẩm</h1>
<p>Nhấn nút bên dưới để xem danh sách</p>

<a href="${pageContext.request.contextPath}/products" class="btn">Vào danh sách sản phẩm</a>
<a href="${pageContext.request.contextPath}/categories" class="btn">Vào danh sách danh mục</a>

</body>
</html>
