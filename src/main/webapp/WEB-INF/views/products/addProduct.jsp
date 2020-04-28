<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add product</title>
</head>
<body>
    <%@include file="../header.html"%>
    <h1>Add product form</h1>
<form method="post" action="${pageContext.request.contextPath}/products/add">
    <p><label>
        Name:
        <input type="text" name="name">
    </label></p>
    <p><label>
        Login:
        <input type="text" name="price">
    </label></p>
    <button type="submit">Add product</button>
</form>
</body>
</html>
