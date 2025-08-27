<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Đăng ký</title>
    <style>
        @import url('https://fonts.googleapis.com/css2?family=Inter:wght@400;600;700&display=swap');

        :root{
            --grad: linear-gradient(135deg, #0ea5e9 0%, #6366f1 100%);
            --border: #e5e7eb;
            --shadow: 0 12px 32px rgba(2,132,199,.15);
            --text: #0f172a;
        }

        html, body { height: 100%; }
        body{
            margin: 0;
            font-family: 'Inter', system-ui, -apple-system, Segoe UI, Roboto, Arial, sans-serif;
            background:
                    radial-gradient(1200px 600px at 80% -10%, rgba(99,102,241,.15), transparent 60%),
                    radial-gradient(900px 500px at -10% 110%, rgba(14,165,233,.18), transparent 60%),
                    #0b1220;
            display: grid;
            place-items: center;
            padding: 24px;
            color: var(--text);
        }

        h2{
            margin: 0 0 12px;
            text-align: center;
            font-size: 26px;
            font-weight: 800;
            color: #ffffff;
            text-shadow: 0 2px 6px rgba(0,0,0,.35);
        }

        body > p:first-of-type{
            margin: 0 0 16px;
            text-align: center;
            color: #e2e8f0;
            font-size: 14px;
        }
        body > p:first-of-type strong{ color:#ffffff; }

        form{
            width: min(480px, 92vw);
            background: #fff;
            padding: 32px 28px;
            border-radius: 18px;
            border: 1px solid var(--border);
            box-shadow: var(--shadow);
        }

        form > div{ margin-bottom: 16px; }

        label{
            display:block;
            font-size:14px;
            font-weight:600;
            color: var(--text);
            margin-bottom:6px;
        }
        label input, label select{ margin-top:8px; }

        input[type="text"],
        input[type="email"],
        select{
            width: 100%;
            box-sizing: border-box;
            padding: 14px 16px;
            font-size: 15px;
            border: 1px solid var(--border);
            border-radius: 12px;
            outline: none;
            transition: border-color .2s ease, box-shadow .2s ease;
            background: #fff;
        }
        input:focus, select:focus{
            border-color: #0ea5e9;
            box-shadow: 0 0 0 4px rgba(14,165,233,.15);
        }

        button[type="submit"]{
            width: 100%;
            padding: 12px 14px;
            margin-top: 6px;
            border: none;
            border-radius: 12px;
            background: var(--grad);
            color: #fff;
            font-weight: 700;
            font-size: 15px;
            cursor: pointer;
            transition: transform .06s ease, filter .15s ease;
        }
        button[type="submit"]:hover{ filter: brightness(1.05); }
        button[type="submit"]:active{ transform: translateY(1px); }

        p[style]{
            text-align: center;
            margin: 12px 0 0;
            font-weight: 700;
        }

        body > p:last-of-type{
            text-align: center;
            margin-top: 14px;
            color: #f1f5f9;
            font-weight: 500;
        }
        body > p:last-of-type a{
            color: #38bdf8;
            text-decoration: none;
            font-weight: 700;
        }
        body > p:last-of-type a:hover{ text-decoration: underline; }
    </style>
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
<%--                <option value="admin">admin</option>--%>
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
