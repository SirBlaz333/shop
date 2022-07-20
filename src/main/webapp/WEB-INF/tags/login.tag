<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<head>
    <link rel="stylesheet" href="css/my-css/menu-panel-style.css">
</head>
<c:choose>
    <c:when test="${user==null}">
        <form action="controller" method="post">
            <button type="submit" class="right-button">
                <input type="hidden" name="command" value="showLoginPage">
                Login
            </button>
        </form>
    </c:when>
    <c:otherwise>
        <form action="controller" method="post">
            <input type="hidden" name="command" value="logout">
            <button type="submit" class="right-button">Logout</button>
        </form>
        <form action="controller" method="post">
            <button class="right-button">
                <c:out value="${user.firstname}"/>
                <img src="controller?command=displayAvatar" class="avatar" alt=""/>
            </button>
        </form>
    </c:otherwise>
</c:choose>
