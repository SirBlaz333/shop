<%@ page pageEncoding="utf-8"%>
<%@ taglib prefix="mylib" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
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
    <script src="https://kit.fontawesome.com/9beb8ac82e.js" crossorigin="anonymous"></script>
    <!-- Font Awesome -->
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="slick/slick.css" />
    <link rel="stylesheet" type="text/css" href="slick/slick-theme.css" />
    <link rel="stylesheet" href="css/tooplate-style.css">
    <!-- tooplate style -->
    <link rel="stylesheet" href="css/my-css/my-style.css">
    <link rel="stylesheet" href="css/my-css/registration-style.css">
    <!-- my styles --->
</head>
<body>
    <mylib:menu-panel/>
    <div class="container">
        <header id="header" class="text-center tm-text-gray">
            <c:if test="${register != null}">
                <h1>Registration</h1>
            </c:if>
            <c:if test="${register == null}">
                <h1>Login</h1>
            </c:if>
        </header>
        <div class = "registration">
            <form action="controller" method="post" name="registration" id="registration">
                <div class = "text-center" style="color: red">
                    ${errorMessage}
                </div>
                <div class="rows">
                    <c:if test="${register != null}">
                        <div class = "input-row">
                            <label for="firstname"><i class="fa fa-user"></i> First Name</label>
                            <div class="error" id="firstname-error">Please enter your firstname</div>
                            <input type="text" name="firstname" id="firstname" placeholder="Max" value="${firstname}"/>
                        </div>

                        <div class="input-row">
                            <label for="lastname"><i class="fa fa-user"></i> Last Name</label>
                            <div class="error" id="lastname-error">Please enter your lastname</div>
                            <input type="text" name="lastname" id="lastname" placeholder="Smith" value="${lastname}"/>
                        </div>
                    </c:if>

                    <div class="input-row">
                        <label for="email"><i class="fa fa-envelope"></i> Email</label>
                        <div class="error" id="email-error">Enter correct email</div>
                        <input type="email" name="email" id="email" placeholder="max@smith.com"  value="${email}"/>
                    </div>

                    <div class="input-row">
                        <label for="password"><i class="fa-solid fa-lock"></i> Password</label>
                        <div class="error" id="password-error">Please provide a password</div>
                        <div class="error" id="password-length-error">Your password must be at least 5 characters long</div>
                        <input type="password" name="password" id="password" placeholder="&#9679;&#9679;&#9679;&#9679;&#9679;"/>
                    </div>

                    <c:if test="${register != null}">
                        <div class="newsletter">
                            <input type="checkbox" id="newsletter" name="newsletter">
                            <label for="newsletter">Add me to the newsletter</label>
                        </div>
                    </c:if>

                    <div class="input-row">
                        <mylib:captcha/>
                    </div>
                    <c:if test="${register != null}">
                        <div class="uploadForm input-row">
                            <input type="file" id="avatar" name="avatar" accept="image/png, image/jpeg" form="uploadAvatar">
                            <input type="submit" form="uploadAvatar" name="Upload avatar" value="Upload avatar"/>
                            <span id="result"></span>
                        </div>

                        <input type="hidden" name="command" value="registration">
                        <button type="submit">Register</button>
                    </c:if>
                    <c:if test="${register == null}">
                        <input type="hidden" name="command" value="login">
                        <button type="submit">Login</button>
                    </c:if>
                </div>
            </form>
            <c:if test="${register == null}">
                <form action="controller" method="post">
                    <input type="hidden" name="command" value="showLoginPage"/>
                    <input type="hidden" name="register" value="true"/>
                    <button type="submit">Sign up</button>
                </form>
            </c:if>
            <c:if test="${register != null}">
                <form action="controller" id="uploadAvatar" method="post" enctype="multipart/form-data">
                </form>
            </c:if>
        </div>
    </div>
    <script src="js/jquery-1.11.3.min.js"></script>
<!--    <script src="js/my-scripts/jquery.form-validation.js"></script>-->
    <script src="js/my-scripts/form-validation.js"></script>
    <script src="js/my-scripts/jquery.submit-form-ajax.js"></script>
    <script src="js/my-scripts/jquery.smooth-appearance.js"></script>
</body>
</html>