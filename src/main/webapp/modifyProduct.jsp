<%@page import="java.util.ArrayList"%>
<%@page import="vn.kms.lp.model.ProductModel"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Update Product</title>
</head>
<body>
    <c:choose>    
    <c:when test="${sessionScope.userName != null}">
        <%
                String Id = request.getParameter("Id");
                String name = request.getParameter("Name");
                String category = request.getParameter("Category");
                String desc = request.getParameter("Desc");
                String price = request.getParameter("Price");
        %>
        <c:out value="${Id}"></c:out>
        <form action="UpdateDatabase" method="GET">
            Name: <input type="text" name="Name" value=<%=name%>>
            <br /> 
            Category: <input type="text" name="Category"
                value=<%=category%> /> <br /> 
            Description: <input type="text" name="Desc" value=<%=desc%>> 
            <br />
            Price: <input type="text" name="Price" value=<%=price%>>
            <br /> <input type="submit" value="Save" />                
                    <input type="hidden" name="Id" value=<%=Id%>>                   
        </form>
    </c:when>
    <c:otherwise>
           <h1 style="color:red">UnAuthorized</h1>
    </c:otherwise>
    </c:choose> 
</body>
</html>
