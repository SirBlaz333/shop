<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<head>
    <link rel="stylesheet" href="css/my-css/captcha-style.css">
</head>
<div class="captcha">
    <img src="controller?command=showRegistration" />
    <input type="captcha" name="captcha" id="captcha"
     placeholder="Enter numbers from image" required="true" maxlength="6">
    <input type="hidden" name="captchaKey" value="${captchaKey}"/>
</div>