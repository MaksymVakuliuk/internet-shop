<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sign in</title>
</head>
<body>
    <%@include file="../header.jsp"%>
    <h1>Sign in</h1>
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
            <button type="submit">Sign in</button>
        </form>
    </div>
</body>
</html>
