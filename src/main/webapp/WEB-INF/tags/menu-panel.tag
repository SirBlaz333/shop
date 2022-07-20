<%@ taglib prefix="mylib" tagdir="/WEB-INF/tags" %>
<head>
    <link rel="stylesheet" href="css/my-css/menu-panel-style.css">
</head>
<div class="panel">
    <form action="index.jsp">
        <button type="submit" class="menu-button">
            Home
        </button>
    </form>
    <form action="products.jsp">
        <button type="submit" class="menu-button">
            Products
        </button>
    </form>
    <form action="cart.jsp">
        <button type="submit" class="cart-button">
            <img src="img/cart-icon.png">
        </button>
    </form>
    <mylib:login/>
</div>