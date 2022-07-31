<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="mylib" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>

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
    <link rel="stylesheet" href="font-awesome-4.5.0/css/font-awesome.min.css">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="slick/slick.css" />
    <link rel="stylesheet" type="text/css" href="slick/slick-theme.css" />
    <link rel="stylesheet" href="css/tooplate-style.css">
    <!-- tooplate style -->
    <link rel="stylesheet" href="css/my-css/my-style.css">
    <link rel="stylesheet" href="css/my-css/products-style.css">
    <!-- my styles --->
</head>
<body>
    <mylib:menu-panel/>
    <div class="container">
        <header id="header" class="text-center tm-text-gray">
            <h1>Products</h1>
        </header>
        <div class="filter-menu">
            <div class="block">
                <div class="filtering-panel">
                    <div class="filter-content">
                        <form action="controller" method="get" id="filtering">
                            <div>
                                <input type="text" name="productName" placeholder="Name"/>
                                <input type="number" min=0 name="originPrice" placeholder="Origin price"/>
                                <input type="number" min=0 name="boundPrice" placeholder="Bound price"/>
                            </div>
                            <div>
                                <input type="checkbox" name="manufacturer" id="Intel" value="1"/>
                                <label for="Intel">Intel</label>
                                <input type="checkbox" name="manufacturer" id="AMD" value="2"/>
                                <label for="AMD">AMD</label>
                            </div>
                            <div>
                                <input type="checkbox" name="memoryType" id="DDR3" value="1"/>
                                <label for="DDR3">DDR3</label>
                                <input type="checkbox" name="memoryType" id="DDR4" value="2"/>
                                <label for="DDR4">DDR4</label>
                                <input type="checkbox" name="memoryType" id="DDR5" value="3"/>
                                <label for="DDR5">DDR5</label>
                            </div>
                            <div>
                                <button type="submit" class="link-button">Confirm</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <div class="block">
                <div class="sorting filtering-panel">
                    <div class="filter-content">
                        <form id="sorting">
                            <label for="sortingList">Sort by:</label>
                            <select name="sortingCriteria" id="sortingCriteria">
                              <option value="">None</option>
                              <option value="price">Price</option>
                              <option value="name">Name</option>
                            </select>
                            <select name="sortingOrder" id="sortingOrder">
                              <option value="ASC">ASC</option>
                              <option value="DESC">DESC</option>
                            </select>
                        </form>
                    </div>
                </div>
                <div class="page-size filtering-panel">
                    <div class="filter-content">
                        <form id="pagination">
                            <div id="pageInfo">
                                <span>Page size:</span>
                                <input type="number" min=1 id="pageSize" name="pageSize" value="${pageSize}" placeholder="Page size"/>
                                <span>Current page:</span>
                                <input type="number" id="pageCount" name="pageCount" value="${pageCount}" placeholder="Current page"/>
                            </div>
                            <button type="submit" class="link-button">Confirm</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <div id="products">
            <input type="hidden" id="maxPages" value="${maxPages}"/>
            <c:if test="${productList.size() == 0}">
                <c:out value="Products not found"/>
            </c:if>
            <ul class="products clearfix">
                <c:forEach var="product" items="${productList}">
                    <li class="product-wrapper">
                        <div class="product">
                            <div class="product-photo">
                                <img src="img/products/product-1.jpg" alt="">
                            </div>
                            <div class="product-name">
                                <c:out value="${product.manufacturer}"/>
                                <c:out value="${product.name}"/>
                                <c:out value="${product.price}"/>
                            </div>
                            <form method="post" action="controller">
                                <input type="hidden" name="command" value="processCart"/>
                                <input type="hidden" name="productId" value="${product.id}"/>
                                <input type="hidden" name="action" value="put"/>
                                <input type="hidden" name="amount" value="1"/>
                                <input type="button" onclick="doAsyncRequest(this)" value="Check" class="link-button"/>
                            </form>
                        </div>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </div>
    <div class="pagination">
        <a class="pageLink" id="previousPage" href="">❮</a>
        <a class="pageLink" id="nextPage" href="">❯</a>
    </div>
    <script src="js/jquery-1.11.3.min.js"></script>
    <script src="js/my-scripts/jquery.smooth-appearance.js"></script>
    <script src="js/my-scripts/jquery.products-form.js"></script>
    <script src="js/my-scripts/jquery.ajax-request.js"></script>
</body>
</html>