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
<form method="post" action="uploadVideo" enctype="multipart/form-data">
<input type="file" name = "filepath"> &nbsp;&nbsp;&nbsp;
<input type="submit" value="Upload">
</form>
</td> 
</tr>
<tr><td><br/><br/></td></tr>
<tr>
<td align="center"><jsp:include page="list.jsp" /></td> 
</tr>
</table>
</body>
</html>