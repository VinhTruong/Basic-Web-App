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
    <form action="UpdateDatabase" method="POST">
        Id: <input type="text" name="Id"> <br/>
        Name: <input type="text" name="Name"> <br /> 
        Category: <input type="text" name="Category" /> <br />
        Description: <input type="text" name="Desc"> <br />
        Price: <input type="text" name="Price"/> <br />  
               <input type="submit" value="Save"/> 
    </form>   
</body>
</html>
