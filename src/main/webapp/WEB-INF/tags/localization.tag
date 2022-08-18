<%@ taglib prefix="llt" uri="/WEB-INF/tags/LocaleListTag.tld" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<head>
    <link rel="stylesheet" href="css/my-css/menu-panel-style.css">
</head>
<div class="localization">
    <select name="lang" id="localization" value="${request.getLocale()}">
        <llt:forEnum name="locale" enumeration="<%=request.getLocales()%>">
            <option value="${locale}"><c:out value="${locale.toString().toUpperCase()}"/></option>
        </llt:forEnum>
    </select>
</div>
<script src="js/jquery-1.11.3.min.js"></script>
<script src="js/my-scripts/localization-ajax.js"></script>