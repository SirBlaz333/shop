<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<head>
    <link rel="stylesheet" href="css/my-css/menu-panel-style.css">
</head>
<form action="controller" method="post">
    <c:choose>
        <c:when test="${user==null}">
            <button type="submit" class="right-button">
                <input type="hidden" name="command" value="showLoginPage">
                Login
            </button>
        </c:when>
        <c:otherwise>
            <button type="submit" class="right-button">
                <input type="hidden" name="command" value="logout">
                Logout
            </button>
            <button type="submit" class="right-button">
                <c:out value="${user.firstname}"/>
            </button>
        </c:otherwise>
    </c:choose>
</form>
