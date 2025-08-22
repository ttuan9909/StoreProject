<%--
  Created by IntelliJ IDEA.
  User: LEDAT
  Date: 8/21/2025
  Time: 12:31 AM
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page session="true" %>
<%
    String adminUsername = (String) session.getAttribute("admin_username");
    if (adminUsername == null) {
        response.sendRedirect(request.getContextPath() + "/admin/Login.jsp");
        return;
    }
%>
<html>
<head><title>Welcome Admin</title></head>
<body>
<h2>Welcome to your admin account, <%= adminUsername %>!</h2>
</body>
</html>
