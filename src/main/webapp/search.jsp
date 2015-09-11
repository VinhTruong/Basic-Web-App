<%@page import="vn.kms.lp.web.listener.SessionListener"%>
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
	<form action="SearchServlet" method="GET">
		Name: <input type="text" name="Name"> <br /> Category: <input
			type="text" name="Category" /> <br /> Price: <input type="text"
			name="From"> To <input type="text" name="To" /> <br />
		Order By <select name="Order" id="Order">
			<option value="name">Name</option>
			<option value="price">Price</option>
			<option selected="selected"></option>
		</select> <input type="submit" value="Search" />
	</form>
	
	<table border="1" style="width: 100%">
		<tr>
			<td>Name</td>
			<td>Category</td>
			<td>Desc</td>
			<td>Price</td>
		</tr>
		<c:if test="${products != null}">
			<c:forEach items="${products}" var="item">
				<tr>
					<td><c:out value="${item.getName()}"></c:out></td>
					<td><c:out value="${item.getCategory()}"></c:out></td>
					<td><c:out value="${item.getDesc()}"></c:out></td>
					<td><c:out value="${item.getPrice()}"></c:out></td>
				</tr>
			</c:forEach>
		</c:if>
	</table>
	
	<form action="login.jsp">
		<input align="right" type="submit" value="Login">
	</form>
	
	<h2 align="Right" style="color: blue">
		Online:
		<c:out value="<%=SessionListener.getOnlineNumber()%>"></c:out>
	</h2>
</body>
</html>
