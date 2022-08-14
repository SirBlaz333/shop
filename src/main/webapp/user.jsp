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
    <link rel="stylesheet" href="css/my-css/user-style.css">
    <!-- my styles --->
</head>
<body>
    <mylib:menu-panel/>
    <div class="container">
        <header id="header" class="text-center tm-text-gray">
            <h1>Your profile</h1>
        </header>
        <div class="user">
            <div class="row">
                <div class="small-col">
                    <div class="header">
                        <label for="fname"><i class="fa fa-user"></i> Full Name</label>
                    </div>
                    <div>
                        <span>
                            <c:out value="${user.firstname}"/>
                            <c:out value="${user.lastname}"/>
                        </span>
                    </div>
                </div>
                <div class="small-col">
                    <div class="header>
                        <label for="email"><i class="fa fa-envelope"></i> Email</label>
                    </div>
                    <div>
                        <span>
                            <c:out value="${user.email}"/>
                        </span>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="small-col">
                    <div>
                        <label><i class="fa fa-user"></i> Avatar</label>
                    </div>
                    <div class="avatar">
                        <img src="controller?command=displayAvatar"/>
                    </div>
                </div>
            </div>
            <div class="logout">
                <form action="controller" method="post">
                    <input type="hidden" name="command" value="logout">
                    <button type="submit" class="right-button">
                        Logout
                    </button>
                </form>
            </div>
        </div>
    </div>
    <script src="js/jquery-1.11.3.min.js"></script>
    <script src="js/my-scripts/jquery.smooth-appearance.js"></script>
</body>
</html>