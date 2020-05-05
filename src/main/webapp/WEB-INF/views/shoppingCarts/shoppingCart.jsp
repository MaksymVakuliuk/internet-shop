<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Shopping cart</title>
</head>
<body>
    <%@include file="../headers/header.jsp"%>
    <h1>Shopping Cart</h1>
    <div>
        User name: <c:out value="${shoppingCart.getUser().getName()}"/><br>
    </div>
    <br>
    <table border="1">
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Price</th>
            <th>Delete</th>
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
                <td>
                    <a href="${pageContext.request.contextPath}
                        /shoppingCarts/removeProduct?productID=${product.id}">delete</a>
                </td>
            </tr>
        </c:forEach>
    </table>

    <br>

    <form action="${pageContext.request.contextPath}/orders/create">
        <button>Place an order</button>
    </form>


</body>
</html>
