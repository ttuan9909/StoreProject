<%--
  Created by IntelliJ IDEA.
  User: LEDAT
  Date: 8/21/2025
  Time: 12:31 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin Login</title>
</head>
<body>
<h2>Admin</h2>
<form action="/admin/login" method="post">
    Username: <input type="text" name="username" required> <br>
    Password: <input type="password" name="password" required> <br>
    <input type="submit" value="Login">
</form>
<p style="color: red">${error}</p>
</body>
</html>
