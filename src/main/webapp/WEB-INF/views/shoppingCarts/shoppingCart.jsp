<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Shopping cart</title>
</head>
<body>
    <%@include file="../header.html"%>
    <h1>Shopping Cart</h1>
    User name: <c:out value="${shoppingCart.getUser().getName()}"/><br>

    <table border="1">
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Price</th>
        </tr>
        <c:forEach var="product" items="${shoppingCart.getProducts()}">
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
</body>
</html>
