<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@ page import="youtube.UserBean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>My YouTube</title>
</head>
<body>
<table width="80%">
<tr>
<td rowspan="4" style="width:40%"><img src ="./images/Eds.jpg" width ="250px" height = "100px" /></td>
<td rowspan="4"><b><font size="20">My YouTube</font></b></td>
<td align="right" valign="middle">
<%if(session.getAttribute("user") != null){
	UserBean user=(UserBean)session.getAttribute("user");
%>
Hi <%= user.getLastName() %> , <%= user.getFirstName() %> &nbsp;&nbsp;&nbsp; <a href="logout">Logout</a>	
<%}else{%>
<a href="./login.jsp">Login</a>
<%} %>
</td>
</tr>
<tr>
<td align="right" valign="middle"><a href="./uploadCheck.jsp">Upload</a></td>
</tr>
<tr>
<td align="right" valign="middle"><a href="allVideos">All Videos</a></td>
</tr>
<tr>
<td align="right" valign="middle">
<%if(session.getAttribute("user") != null){
%>
<a href="myVideoCheck">My Videos</a>	
<%}%>
</td>
</tr>
</table>
<br/>
<% 
String msg=(String)request.getAttribute("message");
if(msg!=null)
{
%>
<%= msg %>
<%
}
%>
<br/>
</body>
</html>