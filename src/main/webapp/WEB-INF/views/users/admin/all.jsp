<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All users</title>
</head>
<body>
    <%@include file="../../headers/header.jsp"%>

    <h1>All Users</h1>
    <table border="1">
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Login</th>
            <th>Show orders</th>
            <th>Delete</th>
        </tr>
        <c:forEach var="user" items="${users}">
            <tr>
                <td>
                    <c:out value="${user.id}"/>
                </td>
                <td>
                    <c:out value="${user.name}"/>
                </td>
                <td>
                    <c:out value="${user.login}"/>
                </td>
                <td>
                    <a href="${pageContext.request.contextPath}
                        /orders/admin/allOrdersOfUser?userID=${user.id}">show orders</a>
                </td>
                <td>
                    <a href="${pageContext.request.contextPath}
                        /users/admin/deleteUser?userID=${user.id}">Delete</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
