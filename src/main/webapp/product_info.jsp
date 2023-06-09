<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="mylib" tagdir="/WEB-INF/tags" %>
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
    <link rel="stylesheet" href="font-awesome-4.5.0/css/font-awesome.min.css">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="slick/slick.css" />
    <link rel="stylesheet" type="text/css" href="slick/slick-theme.css" />
    <link rel="stylesheet" href="css/tooplate-style.css">
    <!-- tooplate style -->
    <link rel="stylesheet" href="css/my-css/my-style.css">
    <link rel="stylesheet" href="css/my-css/product-info-style.css">
    <!-- my styles --->
</head>
<body>
    <mylib:menu-panel/>
    <div class="product-container">

        <div class="left-column">
            <img src="img/products/product-1.jpg" alt="">
        </div>

        <div class="right-column">
            <div class="product-description">
                <h1>Intel</h1>
                <h1>Core i7-1265UE</h1>
                <h3>Price: $429.99</h3>
                <form action="">
                    <button class="add-to-cart">
                        <img src="img/cart-icon.png">
                        add to cart
                    </button>
                </form>
                <button type="button" class="collapsible">Description</button>
                <div class="content">
                    <div class="description">
                        <p>The preferred choice of everyone. Fast, good, cold, 8th threads.
                            I need a big description, so there is a poem.
                            <br>
                            <br>
                            Do not go gentle into that good night,<br>
                            Old age should burn and rave at close of day;<br>
                            Rage, rage against the dying of the light.<br>
                            <br>
                            Though wise men at their end know dark is right,<br>
                            Because their words had forked no lightning they<br>
                            Do not go gentle into that good night.<br>
                            <br>
                            Good men, the last wave by, crying how bright<br>
                            Their frail deeds might have danced in a green bay,<br>
                            Rage, rage against the dying of the light.<br>
                        </p>
                    </div>
                </div>

                <button type="button" class="collapsible">Specification</button>
                <div class="content">
                    <div class="specification">
                        <table>
                            <tb>
                                <tr class="row">
                                    <td>Brand</td>
                                    <td>Intel</td>
                                </tr>
                                <tr class="row">
                                    <td>Name</td>
                                    <td>Core i7-1265UE</td>
                                </tr>
                                <tr class="row">
                                    <td>Amount of cores</td>
                                    <td>8 cores</td>
                                </tr>
                                <tr class="row">
                                    <td>Memory type</td>
                                    <td>DDR4</td>
                                </tr>
                                <tr class="row">
                                    <td>Voltage</td>
                                    <td>120w</td>
                                </tr>
                                <tr class="row">
                                    <td>Weight</td>
                                    <td>80g</td>
                                </tr>
                            </tb>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script src="js/jquery-1.11.3.min.js"></script>
    <script src="js/my-scripts/collapsible.js"></script>
    <script src="js/my-scripts/jquery.smooth-appearance.js"></script>
</body>
</html>