<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
    <%@include file="../headers/header.jsp"%>
    <h1>Registration form</h1>
    <br>
    <div id="SignUp">
        <form method="post" action="${pageContext.request.contextPath}/users/registration">
            <p><label>
                Name:
                <input type="text" name="name">
            </label></p>
            <p><label>
                Login:
                <input type="text" name="login">
            </label></p>
            <p><label>
                Password:
                <input type="password" name="pwd">
            </label></p>
            <p><label>
                Confirm password:
                <input type="password" name="pwd-confirm">
            </label></p>
            <button type="submit">Sign Up</button>
        </form>
    </div>
</body>
</html>
