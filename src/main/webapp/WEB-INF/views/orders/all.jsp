<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Oll orders</title>
</head>
<body>
    <%@include file="../header.jsp"%>
    <h1>All orders</h1>

    <div id="tableOfAllOrders">
        <table border="1">
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Order information</th>
                <th>Delete</th>
            </tr>
            <c:forEach var="order" items="${orders}">
                <tr>
                    <td>
                        <c:out value="${order.id}"/>
                    </td>
                    <td>
                        <c:out value="${order.user.name}"/>
                    </td>
                    <td>
                        <a href="${pageContext.request.contextPath}
                            /orders/orderInformation?orderID=${order.id}">Show</a>
                    </td>
                    <td>
                        <a href="${pageContext.request.contextPath}
                            /orders/delete?orderID=${order.id}">delete</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>

</body>
</html>
