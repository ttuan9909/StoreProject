<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        :root{
            --grad: linear-gradient(135deg, var(--bs-primary) 0%, #6366f1 100%);
            --card-bg: #fff;
            --border: var(--bs-border-color);
            --shadow: 0 12px 32px rgba(13,110,253,.15);
        }

        html, body { height: 100%; }
        body > p:last-of-type{
            text-align: center;
            margin-top: 18px;
            font-size: 15px;
            color: #f1f5f9;
            font-weight: 500;
        }

        body > p:last-of-type a{
            color: #38bdf8;
            font-weight: 700;
            text-decoration: none;
        }
        body{
            margin: 0;
            font-family: var(--bs-body-font-family);
            background:
                    radial-gradient(1200px 600px at 80% -10%, rgba(99,102,241,.14), transparent 60%),
                    radial-gradient(900px 500px at -10% 110%, rgba(13,110,253,.16), transparent 60%),
                    #0b1220;
            display: grid;
            place-items: center;
            padding: 24px;
            color: var(--bs-body-color);
        }

        h2{
            text-align: center;
            margin: 0 0 16px;
            font-weight: 800;
            font-size: 28px;
            letter-spacing: .3px;
            color: #ffffff;
            text-shadow: 0 2px 6px rgba(0,0,0,.4);
        }

        form{
            width: min(520px, 92vw);
            background: var(--card-bg);
            padding: 32px 28px;
            border-radius: 18px;
            border: 1px solid var(--border);
            box-shadow: var(--shadow);
        }

        form > div{
            margin-bottom: 16px;
        }

        label{
            display: block;
            font-weight: 600;
            color: var(--bs-emphasis-color);
            margin-bottom: 6px;
        }

        input[type="text"],
        input[type="password"]{
            display: block;
            width: 100%;
            padding: .7rem .9rem;
            font-size: 1rem;
            border: 1px solid var(--border);
            border-radius: .75rem;
            background-color: #fff;
            color: var(--bs-body-color);
            outline: 0;
            transition: border-color .2s ease, box-shadow .2s ease;
        }
        input[type="text"]:focus,
        input[type="password"]:focus{
            border-color: var(--bs-primary);
            box-shadow: 0 0 0 .25rem rgba(13,110,253,.15);
        }

        button[type="submit"]{
            width: 100%;
            padding: .8rem 1rem;
            border: 0;
            border-radius: .8rem;
            background: var(--grad);
            color: #fff;
            font-weight: 700;
            font-size: 1rem;
            cursor: pointer;
            transition: transform .06s ease, filter .15s ease;
        }
        button[type="submit"]:hover{ filter: brightness(1.05); }
        button[type="submit"]:active{ transform: translateY(1px); }

        p[style]{
            text-align: center;
            margin: 12px 0 0;
            font-weight: 600;
        }

        body > p:last-of-type{
            text-align: center;
            margin-top: 14px;
            color: var(--bs-secondary-color);
        }
        body > p:last-of-type a{
            color: var(--bs-primary);
            font-weight: 600;
            text-decoration: none;
        }
        body > p:last-of-type a:hover{
            text-decoration: underline;
        }
    </style>
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

<p style="color: #dee2e6">
    Chưa có tài khoản?
    <a href="${pageContext.request.contextPath}/admin/register">Đăng ký</a>
</p>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
