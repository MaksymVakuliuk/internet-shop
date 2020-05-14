<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Order information</title>
</head>
<body>
    <%@include file="../headers/header.jsp"%>
    <h1>Order information</h1>

    <div>
        <h4>User info</h4>
        User id: <c:out value="${user.id}"/><br>
        User name: <c:out value="${user.name}"/><br>
        User login: <c:out value="${user.login}"/>
    </div>

    <br>
    <div>
        <h4>Products</h4>
        <table border="1">
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Price</th>
            </tr>
            <c:forEach var="product" items="${order.products}">
                <tr>
                    <td>
                        <c:out value="${product.id}"/>
                    </td>
                    <td>
                        <c:out value="${product.name}"/>
                    </td>
                    <td>
                        <c:out value="${product.price}"/>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
    <br>
</body>
</html>
