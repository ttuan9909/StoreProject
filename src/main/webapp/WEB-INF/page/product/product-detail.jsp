<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>${product.productName} - WebShop</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        .product-image {
            max-width: 100%;
            height: auto;
            border-radius: 8px;
        }
        .product-info {
            background: #f8f9fa;
            padding: 20px;
            border-radius: 8px;
        }
        .price-tag {
            background: linear-gradient(45deg, #ff6b6b, #ee5a24);
            color: white;
            padding: 10px 20px;
            border-radius: 25px;
            font-size: 1.5rem;
            font-weight: bold;
        }
        .quantity-input {
            max-width: 100px;
        }
        .breadcrumb {
            background: transparent;
            padding: 0;
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
                <a class="nav-link" href="${pageContext.request.contextPath}/order">
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
                <li class="breadcrumb-item active" aria-current="page">${product.productName}</li>
            </ol>
        </nav>

        <div class="row">
            <!-- Product Image -->
            <div class="col-md-6">
                <div class="text-center">
                    <img src="${product.image}" alt="${product.productName}" class="product-image img-fluid">
                </div>
            </div>

            <!-- Product Information -->
            <div class="col-md-6">
                <div class="product-info">
                    <h1 class="mb-3">${product.productName}</h1>
                    
                    <div class="mb-4">
                        <span class="price-tag">
                            <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="₫"/>
                        </span>
                    </div>

                    <div class="mb-3">
                        <span class="badge bg-success fs-6">
                            <i class="fas fa-check-circle"></i> Còn hàng: ${product.quantity}
                        </span>
                    </div>

                    <div class="mb-4">
                        <h5>Mô tả sản phẩm:</h5>
                        <p class="text-muted">${product.description}</p>
                    </div>

                    <div class="mb-4">
                        <h5>Thông tin khác:</h5>
                        <ul class="list-unstyled">
                            <li><i class="fas fa-calendar"></i> Ngày tạo: 
                                <fmt:formatDate value="${product.dateCreated}" pattern="dd/MM/yyyy"/>
                            </li>
                            <li><i class="fas fa-tag"></i> Mã sản phẩm: #${product.productId}</li>
                        </ul>
                    </div>

                    <!-- Add to Cart Form -->
                    <form id="addToCartForm" class="mb-3">
                        <div class="row g-3">
                            <div class="col-md-4">
                                <label for="quantity" class="form-label">Số lượng:</label>
                                <input type="number" class="form-control quantity-input" 
                                       id="quantity" name="quantity" value="1" min="1" max="${product.quantity}">
                            </div>
                            <div class="col-md-8">
                                <label class="form-label">&nbsp;</label>
                                <div class="d-grid">
                                    <button type="submit" class="btn btn-success btn-lg">
                                        <i class="fas fa-cart-plus"></i> Thêm vào giỏ hàng
                                    </button>
                                </div>
                            </div>
                        </div>
                    </form>

                    <!-- Action Buttons -->
                    <div class="d-grid gap-2">
                        <a href="${pageContext.request.contextPath}/products" class="btn btn-outline-primary">
                            <i class="fas fa-arrow-left"></i> Quay lại danh sách
                        </a>
                    </div>
                </div>
            </div>
        </div>

        <!-- Related Products Section -->
        <div class="mt-5">
            <h3 class="mb-4">Sản phẩm liên quan</h3>
            <div class="row">
                <!-- You can add related products here -->
                <div class="col-12 text-center">
                    <p class="text-muted">Chức năng hiển thị sản phẩm liên quan sẽ được phát triển sau.</p>
                </div>
            </div>
        </div>
    </div>

    <!-- Add to Cart Modal -->
    <div class="modal fade" id="addToCartModal" tabindex="-1">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Thêm vào giỏ hàng</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <p>Bạn cần <a href="${pageContext.request.contextPath}/login">đăng nhập</a> để thêm sản phẩm vào giỏ hàng.</p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                    <a href="${pageContext.request.contextPath}/login" class="btn btn-primary">Đăng nhập</a>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        // Add to cart form submission
        document.getElementById('addToCartForm').addEventListener('submit', function(e) {
            e.preventDefault();
            
            // Check if user is logged in (you can implement this check)
            const isLoggedIn = false; // This should be set based on your authentication logic
            
            if (!isLoggedIn) {
                // Show login modal
                const modal = new bootstrap.Modal(document.getElementById('addToCartModal'));
                modal.show();
            } else {
                // Add to cart logic
                const quantity = document.getElementById('quantity').value;
                const productId = ${product.productId};
                const productName = '${product.productName}';
                const productPrice = ${product.price};
                
                // You can implement AJAX call to add to cart here
                console.log('Adding to cart:', {productId, productName, productPrice, quantity});
                
                // Show success message
                alert('Đã thêm sản phẩm vào giỏ hàng!');
            }
        });

        // Quantity validation
        document.getElementById('quantity').addEventListener('change', function() {
            const value = parseInt(this.value);
            const max = parseInt(this.max);
            
            if (value < 1) {
                this.value = 1;
            } else if (value > max) {
                this.value = max;
            }
        });
    </script>
</body>
</html>
