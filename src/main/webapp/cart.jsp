<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="mylib" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/html">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Shop</title>
    <!--

    Template 2103 Central
    http://www.tooplate.com/view/2103-central

    -->
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Open+Sans:300,400">
    <!-- Google web font "Open Sans" -->
    <script src="https://kit.fontawesome.com/9beb8ac82e.js" crossorigin="anonymous"></script>
    <!-- Font Awesome -->
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="slick/slick.css" />
    <link rel="stylesheet" type="text/css" href="slick/slick-theme.css" />
    <link rel="stylesheet" href="css/tooplate-style.css">
    <!-- tooplate style -->
    <link rel="stylesheet" href="css/my-css/my-style.css">
    <link rel="stylesheet" href="css/my-css/cart-style.css">
    <!-- my styles --->
</head>
<body>
    <mylib:menu-panel/>
    <div class="container">
        <header id="header" class="text-center tm-text-gray">
            <h1>Cart</h1>
        </header>
        <div class = "text-center" style="color: red">
            <c:out value="${param.errorMessage}"/>
        </div>
        <div class="cart">
            <div class="header row product-info">
                <div class="product-icon"></div>
                <div class="brand">Brand</div>
                <div class="name">Name</div>
                <div class="price">Price</div>
                <div class="amount">Amount</div>
                <div class="delete">
                    <form method="post" action="controller">
                        <input type="hidden" name="command" value="processCart"/>
                        <input type="hidden" name="action" value="clear"/>
                        <button type="button" onclick="doAsyncRequest(this)">
                            <img src="img/bin-icon.png"/>
                        </button>
                    </form>
                </div>
            </div>
            <div id="async">
                <c:forEach var="product" items="${cart.getMap().keySet()}">
                    <div class="row">
                        <div class="product-info">
                            <div class="product-icon">
                                <img src="img/products/product-1.jpg">
                            </div>
                            <div class="brand">
                                <c:out value="${product.manufacturer}"/>
                            </div>
                            <div class="name">
                                <c:out value="${product.name}"/>
                            </div>
                            <div class="price">
                                $<c:out value="${product.price}"/>
                            </div>
                            <div class="amount">
                                <div class="minus">
                                    <form method="post" action="controller">
                                        <input type="hidden" name="command" value="processCart"/>
                                        <input type="hidden" name="productId" value="${product.id}"/>
                                        <input type="hidden" name="action" value="remove"/>
                                        <input type="hidden" name="amount" value="1"/>
                                        <button type="button" onclick="doAsyncRequest(this)">
                                            <i class="fa-solid fa-minus"></i>
                                        </button>
                                    </form>
                                </div>
                                <span>
                                    <c:out value="${cart.get(product)}"/>
                                </span>
                                <div class="plus">
                                    <form method="post" action="controller">
                                        <input type="hidden" name="command" value="processCart"/>
                                        <input type="hidden" name="productId" value="${product.id}"/>
                                        <input type="hidden" name="action" value="put"/>
                                        <input type="hidden" name="amount" value="1"/>
                                        <button type="button" onclick="doAsyncRequest(this)">
                                            <i class="fa-solid fa-plus"></i>
                                        </button>
                                    </form>
                                </div>
                            </div>
                            <div class="delete">
                                <form method="post" action="controller">
                                    <input type="hidden" name="command" value="processCart"/>
                                    <input type="hidden" name="productId" value="${product.id}"/>
                                    <input type="hidden" name="action" value="removeAll"/>
                                    <button type="button" onclick="doAsyncRequest(this)">
                                        <img src="img/bin-icon.png"/>
                                    </button>
                                </form>
                            </div>
                        </div>
                    </div>
                </c:forEach>
                <c:if test="${cart.getMap().size() > 0}">
                    <div class="confirmation">
                        <form method="post" action="credentials.jsp">
                            <button type="submit">
                                Confirm
                            </button>
                        </form>
                    </div>
                </c:if>
                <c:if test="${cart == null || cart.getMap().size() == 0}">
                    <div class="empty">
                        Your cart is empty
                    <div>
                </c:if>
            </div>
        </div>
    </div>
    <script src="js/jquery-1.11.3.min.js"></script>
    <script src="js/my-scripts/collapsible.js"></script>
    <script src="js/my-scripts/jquery.smooth-appearance.js"></script>
    <script src="js/my-scripts/jquery.ajax-request.js"></script>
</body>
</html>