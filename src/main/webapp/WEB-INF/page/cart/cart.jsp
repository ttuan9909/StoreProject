<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Giỏ hàng - WebShop</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        .cart-item {
            border: 1px solid #dee2e6;
            border-radius: 8px;
            padding: 15px;
            margin-bottom: 15px;
        }
        .cart-item-image {
            width: 80px;
            height: 80px;
            object-fit: cover;
            border-radius: 4px;
        }
        .quantity-input {
            width: 70px;
        }
        .cart-summary {
            background: #f8f9fa;
            padding: 20px;
            border-radius: 8px;
            position: sticky;
            top: 20px;
        }
        .empty-cart {
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
                <a class="nav-link active" href="${pageContext.request.contextPath}/cart">
                    <i class="fas fa-shopping-cart"></i> Giỏ hàng
                </a>
                <a class="nav-link" href="${pageContext.request.contextPath}/order">
                    <i class="fas fa-list"></i> Đơn hàng
                </a>
            </div>
        </div>
    </nav>

    <div class="container mt-4">
        <h2 class="mb-4">
            <i class="fas fa-shopping-cart"></i> Giỏ hàng của bạn
        </h2>

        <c:choose>
            <c:when test="${empty cartItems}">
                <!-- Empty Cart -->
                <div class="empty-cart">
                    <i class="fas fa-shopping-cart fa-4x text-muted mb-3"></i>
                    <h4 class="text-muted">Giỏ hàng trống</h4>
                    <p class="text-muted">Bạn chưa có sản phẩm nào trong giỏ hàng.</p>
                    <a href="${pageContext.request.contextPath}/products" class="btn btn-primary">
                        <i class="fas fa-shopping-bag"></i> Mua sắm ngay
                    </a>
                </div>
            </c:when>
            <c:otherwise>
                <div class="row">
                    <!-- Cart Items -->
                    <div class="col-lg-8">
                        <c:forEach var="cartItem" items="${cartItems}">
                            <div class="cart-item">
                                <div class="row align-items-center">
                                    <div class="col-md-2">
                                        <img src="https://via.placeholder.com/80x80?text=Product" 
                                             alt="Product" class="cart-item-image">
                                    </div>
                                    <div class="col-md-4">
                                        <h6 class="mb-1">Sản phẩm #${cartItem.productId}</h6>
                                        <p class="text-muted mb-0">Đơn giá: 
                                            <fmt:formatNumber value="${cartItem.price}" type="currency" currencySymbol="₫"/>
                                        </p>
                                    </div>
                                    <div class="col-md-3">
                                        <label class="form-label">Số lượng:</label>
                                        <input type="number" class="form-control quantity-input" 
                                               value="${cartItem.quantity}" min="1" 
                                               data-product-id="${cartItem.productId}">
                                    </div>
                                    <div class="col-md-2">
                                        <p class="mb-0 fw-bold">
                                            <fmt:formatNumber value="${cartItem.price * cartItem.quantity}" 
                                                            type="currency" currencySymbol="₫"/>
                                        </p>
                                    </div>
                                    <div class="col-md-1">
                                        <button class="btn btn-outline-danger btn-sm remove-item-btn" 
                                                data-product-id="${cartItem.productId}">
                                            <i class="fas fa-trash"></i>
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>

                        <!-- Cart Actions -->
                        <div class="d-flex justify-content-between mt-3">
                            <button class="btn btn-outline-secondary" id="clearCartBtn">
                                <i class="fas fa-trash"></i> Xóa toàn bộ giỏ hàng
                            </button>
                            <a href="${pageContext.request.contextPath}/products" class="btn btn-outline-primary">
                                <i class="fas fa-plus"></i> Thêm sản phẩm
                            </a>
                        </div>
                    </div>

                    <!-- Cart Summary -->
                    <div class="col-lg-4">
                        <div class="cart-summary">
                            <h5 class="mb-3">Tóm tắt đơn hàng</h5>
                            
                            <div class="d-flex justify-content-between mb-2">
                                <span>Tạm tính:</span>
                                <span><fmt:formatNumber value="${cartTotal}" type="currency" currencySymbol="₫"/></span>
                            </div>
                            
                            <div class="d-flex justify-content-between mb-2">
                                <span>Phí vận chuyển:</span>
                                <span class="text-success">Miễn phí</span>
                            </div>
                            
                            <hr>
                            
                            <div class="d-flex justify-content-between mb-3">
                                <span class="fw-bold">Tổng cộng:</span>
                                <span class="fw-bold text-danger fs-5">
                                    <fmt:formatNumber value="${cartTotal}" type="currency" currencySymbol="₫"/>
                                </span>
                            </div>
                            
                            <div class="d-grid gap-2">
                                <button class="btn btn-success btn-lg" id="checkoutBtn">
                                    <i class="fas fa-credit-card"></i> Tiến hành đặt hàng
                                </button>
                                <a href="${pageContext.request.contextPath}/products" class="btn btn-outline-primary">
                                    <i class="fas fa-arrow-left"></i> Tiếp tục mua sắm
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </c:otherwise>
        </c:choose>
    </div>

    <!-- Confirmation Modal -->
    <div class="modal fade" id="confirmationModal" tabindex="-1">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Xác nhận</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <p id="confirmationMessage"></p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                    <button type="button" class="btn btn-danger" id="confirmActionBtn">Xác nhận</button>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        // Update quantity
        document.querySelectorAll('.quantity-input').forEach(input => {
            input.addEventListener('change', function() {
                const productId = this.dataset.productId;
                const quantity = parseInt(this.value);
                
                if (quantity < 1) {
                    this.value = 1;
                    return;
                }
                
                updateCartItem(productId, quantity);
            });
        });

        // Remove item
        document.querySelectorAll('.remove-item-btn').forEach(button => {
            button.addEventListener('click', function() {
                const productId = this.dataset.productId;
                showConfirmation('Bạn có chắc muốn xóa sản phẩm này khỏi giỏ hàng?', () => {
                    removeFromCart(productId);
                });
            });
        });

        // Clear cart
        document.getElementById('clearCartBtn').addEventListener('click', function() {
            showConfirmation('Bạn có chắc muốn xóa toàn bộ giỏ hàng?', () => {
                clearCart();
            });
        });

        // Checkout
        document.getElementById('checkoutBtn').addEventListener('click', function() {
            showConfirmation('Bạn có chắc muốn tiến hành đặt hàng?', () => {
                createOrder();
            });
        });

        function showConfirmation(message, callback) {
            document.getElementById('confirmationMessage').textContent = message;
            const modal = new bootstrap.Modal(document.getElementById('confirmationModal'));
            modal.show();
            
            document.getElementById('confirmActionBtn').onclick = function() {
                callback();
                modal.hide();
            };
        }

        function updateCartItem(productId, quantity) {
            // Implement AJAX call to update cart item
            console.log('Updating cart item:', {productId, quantity});
            
            // You can implement the actual AJAX call here
            // For now, just show a message
            alert('Cập nhật giỏ hàng thành công!');
        }

        function removeFromCart(productId) {
            // Implement AJAX call to remove item from cart
            console.log('Removing from cart:', productId);
            
            // You can implement the actual AJAX call here
            // For now, just show a message and reload the page
            alert('Đã xóa sản phẩm khỏi giỏ hàng!');
            location.reload();
        }

        function clearCart() {
            // Implement AJAX call to clear cart
            console.log('Clearing cart');
            
            // You can implement the actual AJAX call here
            // For now, just show a message and reload the page
            alert('Đã xóa toàn bộ giỏ hàng!');
            location.reload();
        }

        function createOrder() {
            // Implement AJAX call to create order
            console.log('Creating order');
            
            // You can implement the actual AJAX call here
            // For now, just redirect to order page
            window.location.href = '${pageContext.request.contextPath}/order';
        }
    </script>
</body>
</html>
