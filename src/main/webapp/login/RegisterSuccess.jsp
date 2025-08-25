<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <title>Đăng ký thành công</title>
  <style>
    @import url('https://fonts.googleapis.com/css2?family=Inter:wght@400;600;700&display=swap');

    body{
      margin:0;
      font-family:'Inter',sans-serif;
      background:
              radial-gradient(1200px 600px at 80% -10%, rgba(99,102,241,.15), transparent 60%),
              radial-gradient(900px 500px at -10% 110%, rgba(14,165,233,.18), transparent 60%),
              #0b1220;
      display:grid; place-items:center;
      height:100vh; padding:24px; color:#0f172a;
    }

    .card{
      max-width:520px; width:100%;
      background:#fff; padding:36px 30px;
      border-radius:18px; box-shadow:0 12px 32px rgba(2,132,199,.15);
      text-align:center;
    }

    h2{
      margin:0 0 18px; font-size:26px; font-weight:800;
      color:#16a34a; /* xanh lá nổi bật */
    }

    p{ margin:12px 0; font-size:15px; }
    strong{ font-weight:700; }

    a{
      display:inline-block;
      margin-top:18px; padding:12px 20px;
      background:linear-gradient(135deg,#0ea5e9,#6366f1);
      color:#fff; font-weight:700; text-decoration:none;
      border-radius:12px;
      transition:filter .15s, transform .06s;
    }
    a:hover{ filter:brightness(1.05); }
    a:active{ transform:translateY(1px); }
  </style>
</head>
<body>
<div class="card">
  <h2>🎉 Đăng ký tài khoản thành công!</h2>

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
</div>
</body>
</html>
