<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All products</title>
</head>
<body>
    <%@include file="../header.jsp"%>
    <h1>All Products</h1>
    <table border="1">
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Price</th>
            <th>Add to shopping cart</th>
        </tr>
        <c:forEach var="product" items="${products}">
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
                        /shoppingCarts/addToShoppingCart?productID=${product.id}">add</a>
                </td>
            </tr>
        </c:forEach>
    </table>
    <br>
    <form method="get" action="${pageContext.request.contextPath}/shoppingCarts/shoppingCart">
        <button>Go to shopping cart</button>
    </form>
</body>
</html>
