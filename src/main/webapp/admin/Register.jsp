<%--
  Created by IntelliJ IDEA.
  User: LEDAT
  Date: 8/21/2025
  Time: 12:31 AM
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin</title>
</head>
<body>
<h2>Register Admin</h2>

<form action="${pageContext.request.contextPath}/admin/register" method="post">
    Username: <input type="text" name="username" required/><br/>
    Password: <input type="password" name="password" required/><br/>
    <input type="submit" value="Register"/>
</form>

<c:if test="${not empty success}">
    <p style="color: green">${success}</p>
</c:if>
<c:if test="${not empty error}">
    <p style="color: red">${error}</p>
</c:if>

</body>
</html>
