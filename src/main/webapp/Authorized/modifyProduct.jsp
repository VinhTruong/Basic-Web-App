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
        <%
                String id = request.getParameter("Id");
                String name = request.getParameter("Name");
                String category = request.getParameter("Category");
                String desc = request.getParameter("Desc");
                String price = request.getParameter("Price");
                System.out.print("Iddddd:"+id);
        %>
        
        <form action="UpdateServlet" method="GET">
            Name: <input type="text" name="Name" value="<%=name%>">
            <br /> 
            Category: <input type="text" name="Category"
                value="<%=category%>" /> <br /> 
            Description: <input type="text" name="Desc" value="<%=desc%>"> 
            <br />
            Price: <input type="text" name="Price" value="<%=price%>">
            <br /> <input type="submit" value="Save" />      
                   <input type="hidden" name="Action" value="Save">             
                   <input type="hidden" name="Id" value="<%=id%>">                   
        </form>
        
        <form action="UpdateServlet">
            <input type="hidden" name="Action" value="Delete">
            <input type="hidden" name="Id" value="<%=id%>">
            <input type="submit" value="Delete" />   
        </form>
</body>
</html>
