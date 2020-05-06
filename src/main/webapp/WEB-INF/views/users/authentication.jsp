<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Log in</title>
</head>
<body>
    <%@include file="../headers/header.jsp"%>
    <h1>Log In</h1>
    <br>
    <h4 style="color: red"><c:out value="${errorMessage}"/></h4>
    <div id="SignIn">
        <form method="post" action="${pageContext.request.contextPath}/users/authentication">
            <p><label>
                Login:
                <input type="text" name="login">
            </label></p>
            <p><label>
                Password:
                <input type="password" name="pwd">
            </label></p>
            <button type="submit">Log In</button>
        </form>
    </div>
</body>
</html>
