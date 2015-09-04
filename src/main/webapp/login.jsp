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
    <form action="LoginServlet" method="POST">
        UserName: <input type="text" name="UserName"> <br />
        Password: <input type="password" name="Password" /> <br /> <input
            type="submit" value="Submit" name="Submit" />
    </form>
    <c:if test="${loginFlag == false}">
        <h3>Wrong username or password</h3>
    </c:if>
</body>
</html>
