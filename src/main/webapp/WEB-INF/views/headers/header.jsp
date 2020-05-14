<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<header>
    <nav>
        <a href="/">Internet Shop</a>
        | <a href="/products/all">All products</a>
        <c:if test="${sessionScope.userId == null}">
            | <a href="/users/authentication">Log In</a>
            | <a href="/users/registration">Sign Up</a>
        </c:if>

        <c:if test="${sessionScope.userId != null}">
            <%@include file="./user/userMenu.jsp"%>
            <%@include file="./admin/adminMenu.jsp"%>
            | <a href="/users/logout">Log Out</a>
        </c:if>
    </nav>
</header>
