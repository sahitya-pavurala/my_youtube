<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>My YouTube</title>
</head>
<body>
<table width="100%">
<tr>
<td align="center"><jsp:include page="header.jsp" /></td> 
</tr>
<tr>
<td align="center">
<form action="loginServlet" method="post">
<table border="1">
<tr>
<td align="left">UserName:</td>
<td align="right"><input type="text" name="username"></td>
</tr>
<tr>
<td align="left">Password:</td>
<td align="right"><input type="password" name="password"></td>
</tr>
<tr>
<td colspan="2" align="center"><input type="submit" value="Login"></td>
</tr>
<tr>
<td colspan="2" align="center">
New Users: Click here to <a href="./newUser.jsp">Sign In</a>
</td>
</tr>
</table>
</form>
</td> 
</tr>
</table>
</body>
</html>