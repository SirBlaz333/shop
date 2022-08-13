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
    <link rel="stylesheet" href="css/my-css/my-style.css"/>
    <link rel="stylesheet" href="css/my-css/error.css"/>
    <!-- my styles --->
</head>
<body>
    <div class = "text-center" style="color: red">
        <div class="message">
            <c:out value="Oops! Something went wrong..."/>
        </div class="message">
        <div class="message">
            <c:out value="${errorMessage}"/>
            <c:out value="${requestScope['javax.servlet.error.message']}"/>
        </div>
        <div class="message">
            <a href="index.jsp">Go to main page</a>
        </div>
    </div>

    <script src="js/jquery-1.11.3.min.js"></script>
    <script src="js/my-scripts/jquery.smooth-appearance.js"></script>
    <script src="js/my-scripts/jquery.products-form.js"></script>
    <script src="js/my-scripts/jquery.ajax-request.js"></script>
</body>
</html>