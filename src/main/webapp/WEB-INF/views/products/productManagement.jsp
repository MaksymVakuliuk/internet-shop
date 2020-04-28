<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add product</title>
</head>
<body>
    <%@include file="../header.html"%>

    <div id="addProductForm">
        <h1>Add product form</h1>
        <form method="post" action="${pageContext.request.contextPath}/products/add">
            <p><label>
                Name:
                <input type="text" name="name">
            </label></p>
            <p><label>
                Price:
                <input type="text" name="price">
            </label></p>
            <button type="submit">Add product</button>
        </form>
    </div>

    <div id="tableOfAllUser">
        <table border="1">
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Price</th>
                <th>Delete</th>
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
                        /products/delete?productID=${product.id}">delete</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>

</body>
</html>
