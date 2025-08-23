<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>WebShop - Trang chủ</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        .hero-section {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 80px 0;
            text-align: center;
        }
        .feature-card {
            text-align: center;
            padding: 30px 20px;
            border: 1px solid #dee2e6;
            border-radius: 8px;
            margin-bottom: 20px;
            transition: transform 0.2s;
        }
        .feature-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
        }
        .feature-icon {
            font-size: 3rem;
            color: #667eea;
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
    <!-- Navigation -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/">
                <i class="fas fa-shopping-cart"></i> WebShop
            </a>
            <div class="navbar-nav ms-auto">
                <a class="nav-link active" href="${pageContext.request.contextPath}/">
                    <i class="fas fa-home"></i> Trang chủ
                </a>
                <a class="nav-link" href="${pageContext.request.contextPath}/products">
                    <i class="fas fa-shopping-bag"></i> Sản phẩm
                </a>
                <a class="nav-link" href="${pageContext.request.contextPath}/cart">
                    <i class="fas fa-shopping-cart"></i> Giỏ hàng
                </a>
                <a class="nav-link" href="${pageContext.request.contextPath}/order">
                    <i class="fas fa-list"></i> Đơn hàng
                </a>
            </div>
        </div>
    </nav>

    <!-- Hero Section -->
    <div class="hero-section">
        <div class="container">
            <h1 class="display-4 mb-4">Chào mừng đến với WebShop</h1>
            <p class="lead mb-4">Khám phá các sản phẩm chất lượng với giá cả hợp lý</p>
            <a href="${pageContext.request.contextPath}/products" class="btn btn-light btn-lg">
                <i class="fas fa-shopping-bag"></i> Bắt đầu mua sắm
            </a>
        </div>
    </div>

    <!-- Features Section -->
    <div class="container my-5">
        <div class="row">
            <div class="col-md-4">
                <div class="feature-card">
                    <div class="feature-icon">
                        <i class="fas fa-search"></i>
                    </div>
                    <h5>Tìm kiếm sản phẩm</h5>
                    <p class="text-muted">Dễ dàng tìm kiếm và lọc sản phẩm theo danh mục</p>
                </div>
            </div>
            <div class="col-md-4">
                <div class="feature-card">
                    <div class="feature-icon">
                        <i class="fas fa-shopping-cart"></i>
                    </div>
                    <h5>Giỏ hàng thông minh</h5>
                    <p class="text-muted">Quản lý giỏ hàng một cách thuận tiện</p>
                </div>
            </div>
            <div class="col-md-4">
                <div class="feature-card">
                    <div class="feature-icon">
                        <i class="fas fa-credit-card"></i>
                    </div>
                    <h5>Đặt hàng đơn giản</h5>
                    <p class="text-muted">Quy trình đặt hàng nhanh chóng và an toàn</p>
                </div>
            </div>
        </div>
    </div>

    <!-- Quick Actions -->
    <div class="container mb-5">
        <div class="row justify-content-center">
            <div class="col-md-8 text-center">
                <h3 class="mb-4">Bắt đầu mua sắm ngay hôm nay</h3>
                <div class="d-grid gap-2 d-md-block">
                    <a href="${pageContext.request.contextPath}/products" class="btn btn-primary btn-lg me-md-2">
                        <i class="fas fa-shopping-bag"></i> Xem tất cả sản phẩm
                    </a>
                    <a href="${pageContext.request.contextPath}/cart" class="btn btn-outline-primary btn-lg">
                        <i class="fas fa-shopping-cart"></i> Xem giỏ hàng
                    </a>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>