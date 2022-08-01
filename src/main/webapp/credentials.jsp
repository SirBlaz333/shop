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
            <h1>Order</h1>
        </header>
        <div class="cart">
            <div class="header row product-info">
                <div class="product-icon"></div>
                <div class="brand">Brand</div>
                <div class="name">Name</div>
                <div class="price">Price</div>
                <div class="amount">Amount</div>
            </div>
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
                            <span>
                                <c:out value="${cart.get(product)}"/>
                            </span>
                        </div>
                    </div>
                </div>
            </c:forEach>
            <div class="billing row">
                <form action="">
                    <div class="row">
                        <div>
                            <div class="row">
                                <div class="small-col">
                                    <label for="fname"><i class="fa fa-user"></i> Full Name</label>
                                    <span>
                                        <c:out value="${user.firstname}"/>
                                        <c:out value="${user.lastname}"/>
                                    </span>
                                </div>
                                <div class="small-col">
                                    <label for="email"><i class="fa fa-envelope"></i> Email</label>
                                    <span>
                                        <c:out value="${user.email}"/>
                                    </span>
                                </div>
                            </div>
                            <h3>Billing Address</h3>
                            <label for="adr"><i class="fa fa-address-card"></i> Address</label>
                            <input type="text" id="adr" name="address" placeholder="542 W. 15th Street">
                        </div>

                        <div>
                            <h3>Payment</h3>
                            <label for="fname">Accepted Cards</label>
                            <div class="icon-container">
                                <i class="fa fa-cc-visa" style="color:navy;"></i>
                                <i class="fa fa-cc-mastercard" style="color:red;"></i>
                            </div>
                            <label for="cname">Name on Card</label>
                            <input type="text" id="cname" name="cardname" placeholder="John More Doe">
                            <label for="ccnum">Credit card number</label>
                            <input type="text" id="ccnum" name="cardnumber" placeholder="1111-2222-3333-4444">
                            <label for="expmonth">Expiration Month</label>
                            <input type="text" id="expmonth" name="expmonth" placeholder="September">

                            <div class="row">
                                <div class="small-col">
                                    <label for="expyear">Expiration Year</label>
                                    <input type="text" id="expyear" name="expyear" placeholder="2018">
                                </div>
                                <div class="small-col">
                                    <label for="cvv">CVV</label>
                                    <input type="text" id="cvv" name="cvv" placeholder="352">
                                </div>
                            </div>
                        </div>
                        <div class="confirmation">
                            <button type="submit">
                                Confirm
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <script src="js/jquery-1.11.3.min.js"></script>
    <script src="js/my-scripts/collapsible.js"></script>
    <script src="js/my-scripts/jquery.smooth-appearance.js"></script>
</body>
</html>