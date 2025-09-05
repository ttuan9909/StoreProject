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
                                        <img src="${cartItem.productImage}"
                                             alt="${cartItem.productName}" class="cart-item-image"
                                             onerror="this.src='https://via.placeholder.com/80x80?text=No+Image'">
                                    </div>
                                    <div class="col-md-4">
                                        <h6 class="mb-1">${cartItem.productName}</h6>
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
                                        <p class="mb-0 fw-bold item-total">
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
                                <span class="total-amount"><fmt:formatNumber value="${cartTotal}" type="currency" currencySymbol="₫"/></span>
                            </div>
                            <div class="d-flex justify-content-between mb-2">
                                <span>Phí vận chuyển:</span>
                                <span class="text-success">Miễn phí</span>
                            </div>
                            <hr>
                            <div class="d-flex justify-content-between mb-3">
                                <span class="fw-bold">Tổng cộng:</span>
                                <span class="fw-bold text-danger fs-5 total-amount">
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
        // Utility function để format tiền tệ thống nhất
        function formatCurrency(amount) {
            return new Intl.NumberFormat('vi-VN', {
                style: 'currency',
                currency: 'VND',
                minimumFractionDigits: 0,
                maximumFractionDigits: 0
            }).format(amount);
        }

        // Show loading state
        function showLoading(element) {
            if (element) {
                element.style.opacity = '0.6';
                element.style.pointerEvents = 'none';
            }
        }

        // Hide loading state
        function hideLoading(element) {
            if (element) {
                element.style.opacity = '1';
                element.style.pointerEvents = 'auto';
            }
        }

        // Show toast notification instead of alert
        function showToast(message, type = 'success') {
            // Create toast element
            const toastHtml = `
                <div class="toast align-items-center text-white bg-${type === 'success' ? 'success' : 'danger'} border-0" role="alert" aria-live="assertive" aria-atomic="true">
                    <div class="d-flex">
                        <div class="toast-body">
                            ${message}
                        </div>
                        <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast" aria-label="Close"></button>
                    </div>
                </div>
            `;
            
            // Add to toast container or create one
            let toastContainer = document.querySelector('.toast-container');
            if (!toastContainer) {
                toastContainer = document.createElement('div');
                toastContainer.className = 'toast-container position-fixed top-0 end-0 p-3';
                document.body.appendChild(toastContainer);
            }
            
            toastContainer.insertAdjacentHTML('beforeend', toastHtml);
            const toastElement = toastContainer.lastElementChild;
            const toast = new bootstrap.Toast(toastElement);
            toast.show();
            
            // Remove toast element after it's hidden
            toastElement.addEventListener('hidden.bs.toast', () => {
                toastElement.remove();
            });
        }
        // Update quantity
        document.querySelectorAll('.quantity-input').forEach(input => {
            input.addEventListener('change', function() {
                const productId = this.dataset.productId;
                const quantity = parseInt(this.value);

                if (!productId) {
                    console.error('No data-product-id found for input:', this);
                    alert('Lỗi: Không tìm thấy ID sản phẩm');
                    return;
                }
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
            console.log('Updating cart item:', {productId, quantity});

            if (quantity < 1) {
                showToast('Số lượng phải lớn hơn 0', 'error');
                document.querySelector(`.quantity-input[data-product-id="${productId}"]`).value = 1;
                return;
            }

            // Tìm phần tử cart-item để show loading
            const inputElement = document.querySelector(`.quantity-input[data-product-id="${productId}"]`);
            const itemRow = inputElement ? inputElement.closest('.cart-item') : null;
            
            if (itemRow) {
                showLoading(itemRow);
            }

            const formData = new URLSearchParams();
            formData.append('action', 'update');
            formData.append('productId', productId);
            formData.append('quantity', quantity);

            fetch('${pageContext.request.contextPath}/cart', {
                method: 'POST',
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                body: formData
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error('HTTP error! status: ' + response.status);
                    }
                    return response.json();
                })
                .then(data => {
                    if (itemRow) {
                        hideLoading(itemRow);
                    }
                    
                    if (data.success) {
                        if (!inputElement) {
                            console.error(`No input element found for productId=${productId}`);
                            showToast('Lỗi: Không tìm thấy sản phẩm trong giỏ hàng', 'error');
                            return;
                        }

                        if (!itemRow) {
                            console.error(`No cart-item found for productId=${productId}`);
                            showToast('Lỗi: Không tìm thấy phần tử giỏ hàng', 'error');
                            return;
                        }

                        // Cập nhật giá tổng của sản phẩm (item-total)
                        const itemTotalElement = itemRow.querySelector('.item-total');
                        if (itemTotalElement) {
                            const price = parseFloat(data.price);
                            const newItemTotal = price * quantity;
                            itemTotalElement.textContent = formatCurrency(newItemTotal);
                        } else {
                            console.error(`No item-total element found for productId=${productId}`);
                        }

                        // Cập nhật tổng giá trị giỏ hàng (cartTotal)
                        const cartTotalElements = document.querySelectorAll('.cart-summary .total-amount');
                        if (cartTotalElements.length > 0) {
                            cartTotalElements.forEach(element => {
                                element.textContent = formatCurrency(parseFloat(data.cartTotal));
                            });
                        } else {
                            console.error('No total-amount elements found');
                        }

                        showToast(data.message, 'success');
                    } else {
                        showToast(data.message, 'error');
                    }
                })
                .catch(error => {
                    if (itemRow) {
                        hideLoading(itemRow);
                    }
                    console.error('Error:', error);
                    showToast('Lỗi kết nối: ' + error.message, 'error');
                });
        }

        function removeFromCart(productId) {
            console.log('Removing from cart:', productId);

            // Find and show loading for the specific item
            const inputElement = document.querySelector(`.quantity-input[data-product-id="${productId}"]`);
            const itemRow = inputElement ? inputElement.closest('.cart-item') : null;
            
            if (itemRow) {
                showLoading(itemRow);
            }

            const formData = new URLSearchParams();
            formData.append('action', 'remove');
            formData.append('productId', productId);

            fetch('${pageContext.request.contextPath}/cart', {
                method: 'POST',
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                body: formData
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error('HTTP error! status: ' + response.status);
                    }
                    return response.json();
                })
                .then(data => {
                    if (data.success) {
                        showToast(data.message, 'success');
                        // Smooth remove animation
                        if (itemRow) {
                            itemRow.style.transition = 'opacity 0.3s ease';
                            itemRow.style.opacity = '0';
                            setTimeout(() => {
                                location.reload();
                            }, 300);
                        } else {
                            location.reload();
                        }
                    } else {
                        if (itemRow) {
                            hideLoading(itemRow);
                        }
                        showToast(data.message, 'error');
                    }
                })
                .catch(error => {
                    if (itemRow) {
                        hideLoading(itemRow);
                    }
                    console.error('Error:', error);
                    showToast('Lỗi kết nối: ' + error.message, 'error');
                });
        }

        function clearCart() {
            console.log('Clearing cart');
            
            // Show loading for entire cart
            const cartContainer = document.querySelector('.col-lg-8');
            if (cartContainer) {
                showLoading(cartContainer);
            }
            
            const formData = new URLSearchParams();
            formData.append('action', 'clear');

            fetch('${pageContext.request.contextPath}/cart', {
                method: 'POST',
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                body: formData
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error('HTTP error! status: ' + response.status);
                    }
                    return response.json();
                })
                .then(data => {
                    if (data.success) {
                        showToast(data.message, 'success');
                        setTimeout(() => {
                            location.reload();
                        }, 1000);
                    } else {
                        if (cartContainer) {
                            hideLoading(cartContainer);
                        }
                        showToast(data.message, 'error');
                    }
                })
                .catch(error => {
                    if (cartContainer) {
                        hideLoading(cartContainer);
                    }
                    console.error('Error:', error);
                    showToast('Lỗi kết nối: ' + error.message, 'error');
                });
        }

        function createOrder() {
            console.log('Creating order');
            const cartItems = document.querySelectorAll('.cart-item');
            if (cartItems.length === 0) {
                showToast('Giỏ hàng trống, vui lòng thêm sản phẩm!', 'error');
                return;
            }
            
            // Show loading for checkout button
            const checkoutBtn = document.getElementById('checkoutBtn');
            if (checkoutBtn) {
                checkoutBtn.disabled = true;
                checkoutBtn.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Đang xử lý...';
            }
            
            const formData = new URLSearchParams();
            formData.append('action', 'create');
            fetch('${pageContext.request.contextPath}/order', {
                method: 'POST',
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                body: formData
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error('HTTP error! status: ' + response.status);
                    }
                    return response.json();
                })
                .then(data => {
                    if (data.success) {
                        showToast(data.message, 'success');
                        setTimeout(() => {
                            window.location.href = '${pageContext.request.contextPath}/order/detail/' + data.orderId;
                        }, 1500);
                    } else {
                        if (checkoutBtn) {
                            checkoutBtn.disabled = false;
                            checkoutBtn.innerHTML = '<i class="fas fa-credit-card"></i> Tiến hành đặt hàng';
                        }
                        showToast(data.message, 'error');
                    }
                })
                .catch(error => {
                    if (checkoutBtn) {
                        checkoutBtn.disabled = false;
                        checkoutBtn.innerHTML = '<i class="fas fa-credit-card"></i> Tiến hành đặt hàng';
                    }
                    console.error('Error:', error);
                    showToast('Lỗi kết nối: ' + error.message, 'error');
                });
        }
        // Example function tính total từ UI (tùy chỉnh theo cart items)
        function calculateTotal() {
            let total = 0;
            document.querySelectorAll('.cart-item').forEach(item => {
                const quantity = parseInt(item.querySelector('.quantity-input').value);
                const priceText = item.querySelector('.text-muted').textContent;
                const price = parseFloat(priceText.replace(/[^\d.]/g, ''));
                total += price * quantity;
            });
            return total;
        }
    </script>
</body>
</html>
