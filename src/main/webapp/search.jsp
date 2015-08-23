<%@page import="java.util.ArrayList"%>
<%@page import="vn.kms.lp.model.ProductModel"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Search page</title>
</head>
<body>
    <form action="DataFetching" method="GET">
        Name: <input type="text" name="Name"> <br /> Category:
        <input type="text" name="Category" /> <br /> Price: <input
            type="text" name="From"> To <input type="text"
            name="To" /> <br /> Order By <select name="Order"
            id="Order">
            <option value="name">Name</option>
            <option value="price">Price</option>
            <option selected="selected"></option>
        </select> <input type="submit" value="Search" />
    </form>
    <c:choose>
        <c:when test="${sessionScope.userName!=null}">
            <table border="1" style="width: 100%">
                <tr>
                    <td>Id</td>
                    <td>Name</td>
                    <td>Category</td>
                    <td>Desc</td>
                    <td>Price</td>
                </tr>
                <c:if test="${products != null}">
                    <c:forEach items="${products}" var="item">
                        <tr>
                            <td><c:out value="${item.getId()}"></c:out></td>
                            <td><a href="modifyProduct.jsp"> <c:out
                                        value="${item.getName()}"></c:out></a></td>
                            <td><c:out
                                    value="${item.getCategory()}"></c:out></td>
                            <td><c:out value="${item.getDesc()}"></c:out></td>
                            <td><c:out value="${item.getPrice()}"></c:out></td>
                        </tr>
                    </c:forEach>
                </c:if>
            </table>
            <h2 align="right">Welcome"${sessionScope.userName}"</h2>
            <form action="modifyProduct.jsp" method="POST">
                <input type="submit" value="Add New">
            </form>
        </c:when>
        <c:otherwise>
            <table border="1" style="width: 100%">
                <tr>
                    <td>Id</td>
                    <td>Name</td>
                    <td>Category</td>
                    <td>Desc</td>
                    <td>Price</td>
                </tr>
                <c:if test="${products != null}">
                    <c:forEach items="${products}" var="item">
                        <tr>
                            <td><c:out value="${item.getId()}"></c:out></td>
                            <td><c:out value="${item.getName()}"></c:out></td>
                            <td><c:out
                                    value="${item.getCategory()}"></c:out></td>
                            <td><c:out value="${item.getDesc()}"></c:out></td>
                            <td><c:out value="${item.getPrice()}"></c:out></td>
                        </tr>
                    </c:forEach>
                </c:if>
            </table>
        </c:otherwise>
    </c:choose>
</body>
</html>
