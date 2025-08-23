<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Chi tiết đơn hàng #${order.orderId} - WebShop</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        .order-detail-card {
            border: 1px solid #dee2e6;
            border-radius: 8px;
            margin-bottom: 20px;
        }
        .order-header {
            background: #f8f9fa;
            padding: 20px;
            border-bottom: 1px solid #dee2e6;
            border-radius: 8px 8px 0 0;
        }
        .order-items {
            padding: 20px;
        }
        .order-item {
            border: 1px solid #dee2e6;
            border-radius: 4px;
            padding: 15px;
            margin-bottom: 10px;
        }
        .order-summary {
            background: #f8f9fa;
            padding: 20px;
            border-top: 1px solid #dee2e6;
            border-radius: 0 0 8px 8px;
        }
        .status-badge {
            font-size: 1rem;
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
        <!-- Breadcrumb -->
        <nav aria-label="breadcrumb">
            <ol class="breadcrumb">
                <li class="breadcrumb-item">
                    <a href="${pageContext.request.contextPath}/products">Trang chủ</a>
                </li>
                <li class="breadcrumb-item">
                    <a href="${pageContext.request.contextPath}/order">Đơn hàng</a>
                </li>
                <li class="breadcrumb-item active" aria-current="page">Chi tiết đơn hàng #${order.orderId}</li>
            </ol>
        </nav>

        <div class="order-detail-card">
            <!-- Order Header -->
            <div class="order-header">
                <div class="row align-items-center">
                    <div class="col-md-6">
                        <h3 class="mb-2">Đơn hàng #${order.orderId}</h3>
                        <p class="mb-1">
                            <i class="fas fa-calendar"></i> 
                            <strong>Ngày đặt:</strong> 
                            <fmt:formatDate value="${order.orderDate}" pattern="dd/MM/yyyy HH:mm"/>
                        </p>
                        <p class="mb-0">
                            <i class="fas fa-user"></i> 
                            <strong>Mã người dùng:</strong> ${order.userId}
                        </p>
                    </div>
                    <div class="col-md-6 text-end">
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
                </div>
            </div>

            <!-- Order Items -->
            <div class="order-items">
                <h5 class="mb-3">
                    <i class="fas fa-list"></i> Chi tiết sản phẩm
                </h5>
                
                <c:forEach var="orderDetail" items="${orderDetails}">
                    <div class="order-item">
                        <div class="row align-items-center">
                            <div class="col-md-2">
                                <img src="https://via.placeholder.com/60x60?text=Product" 
                                     alt="Product" class="img-fluid rounded">
                            </div>
                            <div class="col-md-4">
                                <h6 class="mb-1">Sản phẩm #${orderDetail.productId}</h6>
                                <p class="text-muted mb-0">Mã sản phẩm: ${orderDetail.productId}</p>
                            </div>
                            <div class="col-md-2 text-center">
                                <span class="badge bg-secondary">Số lượng: ${orderDetail.quantity}</span>
                            </div>
                            <div class="col-md-2 text-center">
                                <span class="text-muted">
                                    Đơn giá:<br>
                                    <fmt:formatNumber value="${orderDetail.price}" type="currency" currencySymbol="₫"/>
                                </span>
                            </div>
                            <div class="col-md-2 text-end">
                                <span class="fw-bold">
                                    <fmt:formatNumber value="${orderDetail.price * orderDetail.quantity}" 
                                                    type="currency" currencySymbol="₫"/>
                                </span>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>

            <!-- Order Summary -->
            <div class="order-summary">
                <div class="row">
                    <div class="col-md-8">
                        <c:if test="${order.discountId != null}">
                            <p class="mb-2 text-success">
                                <i class="fas fa-tag"></i> 
                                <strong>Khuyến mãi:</strong> Có áp dụng khuyến mãi (Mã: ${order.discountId})
                            </p>
                        </c:if>
                        <p class="mb-0">
                            <i class="fas fa-info-circle"></i> 
                            <strong>Ghi chú:</strong> Đơn hàng sẽ được xử lý trong thời gian sớm nhất
                        </p>
                    </div>
                    <div class="col-md-4 text-end">
                        <div class="mb-2">
                            <span class="text-muted">Tạm tính:</span>
                            <span class="ms-2">
                                <fmt:formatNumber value="${order.totalPrice}" type="currency" currencySymbol="₫"/>
                            </span>
                        </div>
                        <div class="mb-2">
                            <span class="text-muted">Phí vận chuyển:</span>
                            <span class="ms-2 text-success">Miễn phí</span>
                        </div>
                        <hr>
                        <div class="h4 text-danger mb-0">
                            <strong>Tổng cộng:</strong>
                            <span class="ms-2">
                                <fmt:formatNumber value="${order.totalPrice}" type="currency" currencySymbol="₫"/>
                            </span>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Action Buttons -->
        <div class="d-flex justify-content-between mt-4">
            <a href="${pageContext.request.contextPath}/order" class="btn btn-outline-primary">
                <i class="fas fa-arrow-left"></i> Quay lại danh sách đơn hàng
            </a>
            <a href="${pageContext.request.contextPath}/products" class="btn btn-primary">
                <i class="fas fa-shopping-bag"></i> Tiếp tục mua sắm
            </a>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
