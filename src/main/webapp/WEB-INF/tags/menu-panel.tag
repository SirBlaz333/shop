<%@ taglib prefix="mylib" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<head>
    <link rel="stylesheet" href="css/my-css/menu-panel-style.css">
</head>
<div class="panel">
    <form action="index.jsp">
        <button type="submit" class="menu-button">
            Home
        </button>
    </form>
    <form method="get" action="controller">
        <input type="hidden" name="command" value="products"/>
        <input type="hidden" name="pageSize" value="8"/>
        <input type="hidden" name="pageCount" value="1"/>
        <button type="submit" class="menu-button">
            Products
        </button>
    </form>
    <form id="cart-async" action="cart.jsp">
        <button type="submit" class="cart-button">
            <c:out value="${cart != null ? cart.getSize() : 0}"/>
            <img src="img/cart-icon.png">
        </button>
    </form>
    <mylib:login/>
</div>