<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="locale"/>
<fmt:setLocale value="${request.getLocale()}"/>
<head>
    <link rel="stylesheet" href="css/my-css/menu-panel-style.css">
</head>
<c:choose>
    <c:when test="${user==null}">
        <form action="controller" method="post">
            <button type="submit" class="right-button">
                <input type="hidden" name="command" value="showLoginPage">
                <fmt:message key="login"/>
            </button>
        </form>
    </c:when>
    <c:otherwise>
        <form action="user.jsp" method="post">
            <button class="right-button">
                <c:out value="${user.firstname}"/>
                <img src="controller?command=displayAvatar" class="avatar" alt=""/>
            </button>
        </form>
    </c:otherwise>
</c:choose>
