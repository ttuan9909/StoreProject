<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Lịch sử đơn hàng - WebShop</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        .order-card {
            border: 1px solid #dee2e6;
            border-radius: 8px;
            margin-bottom: 20px;
            transition: box-shadow 0.2s;
        }
        .order-card:hover {
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
        }
        .order-header {
            background: #f8f9fa;
            padding: 15px;
            border-bottom: 1px solid #dee2e6;
            border-radius: 8px 8px 0 0;
        }
        .order-body {
            padding: 15px;
        }
        .status-badge {
            font-size: 0.875rem;
        }
        .empty-orders {
            text-align: center;
            padding: 60px 20px;
        }
    </style>
</head>
<body>
    <!-- Navigation -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/products">
                <i class="fas fa-shopping-cart"></i> WebShop
            </a>
            <div class="navbar-nav ms-auto">
                <a class="nav-link" href="${pageContext.request.contextPath}/products">
                    <i class="fas fa-home"></i> Trang chủ
                </a>
                <a class="nav-link" href="${pageContext.request.contextPath}/cart">
                    <i class="fas fa-shopping-cart"></i> Giỏ hàng
                </a>
                <a class="nav-link active" href="${pageContext.request.contextPath}/order">
                    <i class="fas fa-list"></i> Đơn hàng
                </a>
            </div>
        </div>
    </nav>

    <div class="container mt-4">
        <h2 class="mb-4">
            <i class="fas fa-list"></i> Lịch sử đơn hàng
        </h2>

        <c:choose>
            <c:when test="${empty orders}">
                <!-- Empty Orders -->
                <div class="empty-orders">
                    <i class="fas fa-shopping-bag fa-4x text-muted mb-3"></i>
                    <h4 class="text-muted">Chưa có đơn hàng nào</h4>
                    <p class="text-muted">Bạn chưa đặt đơn hàng nào. Hãy bắt đầu mua sắm!</p>
                    <a href="${pageContext.request.contextPath}/products" class="btn btn-primary">
                        <i class="fas fa-shopping-bag"></i> Mua sắm ngay
                    </a>
                </div>
            </c:when>
            <c:otherwise>
                <!-- Orders List -->
                <c:forEach var="order" items="${orders}">
                    <div class="order-card">
                        <div class="order-header">
                            <div class="row align-items-center">
                                <div class="col-md-6">
                                    <h6 class="mb-1">Đơn hàng #${order.orderId}</h6>
                                    <small class="text-muted">
                                        <i class="fas fa-calendar"></i> 
                                        <fmt:formatDate value="${order.orderDate}" pattern="dd/MM/yyyy HH:mm"/>
                                    </small>
                                </div>
                                <div class="col-md-3 text-center">
                                    <span class="badge 
                                        ${order.orderStatus == 'cho_xu_ly' ? 'bg-warning' : 
                                          order.orderStatus == 'dang_xu_ly' ? 'bg-info' : 
                                          order.orderStatus == 'hoan_thanh' ? 'bg-success' : 'bg-danger'} 
                                        status-badge">
                                        <c:choose>
                                            <c:when test="${order.orderStatus == 'cho_xu_ly'}">
                                                <i class="fas fa-clock"></i> Chờ xử lý
                                            </c:when>
                                            <c:when test="${order.orderStatus == 'dang_xu_ly'}">
                                                <i class="fas fa-cog fa-spin"></i> Đang xử lý
                                            </c:when>
                                            <c:when test="${order.orderStatus == 'hoan_thanh'}">
                                                <i class="fas fa-check-circle"></i> Hoàn thành
                                            </c:when>
                                            <c:when test="${order.orderStatus == 'huy'}">
                                                <i class="fas fa-times-circle"></i> Đã hủy
                                            </c:when>
                                            <c:otherwise>
                                                ${order.orderStatus}
                                            </c:otherwise>
                                        </c:choose>
                                    </span>
                                </div>
                                <div class="col-md-3 text-end">
                                    <span class="h5 text-danger mb-0">
                                        <fmt:formatNumber value="${order.totalPrice}" type="currency" currencySymbol="₫"/>
                                    </span>
                                </div>
                            </div>
                        </div>
                        
                        <div class="order-body">
                            <div class="row align-items-center">
                                <div class="col-md-8">
                                    <p class="mb-1">
                                        <strong>Tổng tiền:</strong> 
                                        <fmt:formatNumber value="${order.totalPrice}" type="currency" currencySymbol="₫"/>
                                    </p>
                                    <c:if test="${order.discountId != null}">
                                        <p class="mb-1 text-success">
                                            <i class="fas fa-tag"></i> Có áp dụng khuyến mãi
                                        </p>
                                    </c:if>
                                </div>
                                <div class="col-md-4 text-end">
                                    <a href="${pageContext.request.contextPath}/order/detail/${order.orderId}" 
                                       class="btn btn-outline-primary btn-sm">
                                        <i class="fas fa-eye"></i> Xem chi tiết
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
