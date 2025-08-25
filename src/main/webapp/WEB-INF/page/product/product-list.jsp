<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Danh sách sản phẩm - WebShop</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        .product-card {
            transition: transform 0.2s;
            height: 100%;
        }
        .product-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
        }
        .product-image {
            height: 200px;
            object-fit: cover;
        }
        .category-filter {
            background: #f8f9fa;
            padding: 20px;
            border-radius: 8px;
            margin-bottom: 20px;
        }
        .search-box {
            background: white;
            padding: 20px;
            border-radius: 8px;
            margin-bottom: 20px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
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
        <!-- Search Box -->
        <div class="search-box">
            <form action="${pageContext.request.contextPath}/search" method="GET" class="row g-3">
                <div class="col-md-8">
                    <input type="text" class="form-control" name="keyword" 
                           placeholder="Tìm kiếm sản phẩm..." 
                           value="${searchKeyword != null ? searchKeyword : ''}">
                </div>
                <div class="col-md-4">
                    <button type="submit" class="btn btn-primary w-100">
                        <i class="fas fa-search"></i> Tìm kiếm
                    </button>
                </div>
            </form>
        </div>

        <!-- Category Filter -->
        <div class="category-filter">
            <h5><i class="fas fa-filter"></i> Lọc theo danh mục</h5>
            <div class="row">
                <div class="col-md-2">
                    <a href="${pageContext.request.contextPath}/products" 
                       class="btn btn-outline-primary w-100 mb-2 ${selectedCategory == null ? 'active' : ''}">
                        Tất cả
                    </a>
                </div>
                <c:forEach var="category" items="${categories}">
                    <div class="col-md-2">
                        <a href="${pageContext.request.contextPath}/product/category/${category.categoryId}" 
                           class="btn btn-outline-primary w-100 mb-2 ${selectedCategory != null && selectedCategory.categoryId == category.categoryId ? 'active' : ''}">
                            ${category.categoryName}
                        </a>
                    </div>
                </c:forEach>
            </div>
        </div>

        <!-- Products Grid -->
        <div class="row">
            <c:forEach var="product" items="${products}">
                <div class="col-md-3 mb-4">
                    <div class="card product-card h-100">
                        <img src="${product.image}" class="card-img-top product-image" alt="${product.productName}">
                        <div class="card-body d-flex flex-column">
                            <h6 class="card-title">${product.productName}</h6>
                            <p class="card-text text-muted small">
                                ${product.description.length() > 100 ? product.description.substring(0, 100).concat('...') : product.description}
                            </p>
                            <div class="mt-auto">
                                <div class="d-flex justify-content-between align-items-center mb-2">
                                    <span class="h5 text-danger mb-0">
                                        <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="₫"/>
                                    </span>
                                    <span class="badge bg-success">Còn ${product.quantity}</span>
                                </div>
                                <div class="d-grid gap-2">
                                    <a href="${pageContext.request.contextPath}/product/detail/${product.productId}" 
                                       class="btn btn-outline-primary btn-sm">
                                        <i class="fas fa-eye"></i> Xem chi tiết
                                    </a>
                                    <button class="btn btn-success btn-sm add-to-cart-btn" 
                                            data-product-id="${product.productId}" 
                                            data-product-name="${product.productName}"
                                            data-product-price="${product.price}">
                                        <i class="fas fa-cart-plus"></i> Thêm vào giỏ
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>

        <!-- Pagination -->
        <c:if test="${totalPages > 1}">
            <nav aria-label="Product pagination">
                <ul class="pagination justify-content-center">
                    <c:if test="${currentPage > 1}">
                        <li class="page-item">
                            <a class="page-link" href="?page=${currentPage - 1}">Trước</a>
                        </li>
                    </c:if>
                    
                    <c:forEach begin="1" end="${totalPages}" var="pageNum">
                        <li class="page-item ${pageNum == currentPage ? 'active' : ''}">
                            <a class="page-link" href="?page=${pageNum}">${pageNum}</a>
                        </li>
                    </c:forEach>
                    
                    <c:if test="${currentPage < totalPages}">
                        <li class="page-item">
                            <a class="page-link" href="?page=${currentPage + 1}">Sau</a>
                        </li>
                    </c:if>
                </ul>
            </nav>
        </c:if>

        <!-- No Products Message -->
        <c:if test="${empty products}">
            <div class="text-center py-5">
                <i class="fas fa-search fa-3x text-muted mb-3"></i>
                <h4 class="text-muted">Không tìm thấy sản phẩm nào</h4>
                <p class="text-muted">Hãy thử tìm kiếm với từ khóa khác hoặc chọn danh mục khác</p>
            </div>
        </c:if>
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
        // Add to cart functionality
        document.querySelectorAll('.add-to-cart-btn').forEach(button => {
            button.addEventListener('click', function() {
                // Check if user is logged in (you can implement this check)
                const isLoggedIn = false; // This should be set based on your authentication logic
                
                if (!isLoggedIn) {
                    // Show login modal
                    const modal = new bootstrap.Modal(document.getElementById('addToCartModal'));
                    modal.show();
                } else {
                    // Add to cart logic
                    const productId = this.dataset.productId;
                    const productName = this.dataset.productName;
                    const productPrice = this.dataset.productPrice;
                    
                    // You can implement AJAX call to add to cart here
                    console.log('Adding to cart:', {productId, productName, productPrice});
                }
            });
        });
    </script>
</body>
</html>
