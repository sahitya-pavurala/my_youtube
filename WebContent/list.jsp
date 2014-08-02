<%@page import="youtube.UserBean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.util.*,youtube.VideoBean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>My YouTube</title>
<script type="text/javascript">
function updateRating(selectedId){
	document.getElementById("newRating").value = document.getElementById(selectedId).value;
	document.getElementById("videoId").value = selectedId;
	document.getElementById("ratingForm").submit();
}
</script>
</head>
<body>
<form action="updateRating" method="post" id="ratingForm">
<table>
<%ArrayList<VideoBean> videos = (ArrayList<VideoBean>)session.getAttribute("videos");
if(videos != null){
for(VideoBean vid : videos){
%>
<tr>
<td valign="middle"><h3><b><%=vid.getName() %></b></h3></td>
<td valign="middle">
<video width="356" height="200" controls>
<source src="<%=vid.getUrl() %>" />
  <em>Sorry, your browser doesn't support HTML5 video.</em>
</video>
</td>
<td valign="middle">
<table>
<tr>
<td align="left"><b>Avg. Rating : &nbsp;</b></td>
<td align="left"><b><%= vid.getAvgRating() %></b></td>
</tr>
<tr>
<td align="left"><b>Current User. Rating : &nbsp;</b></td>
<td align="left"><b>
<% if(session.getAttribute("user") != null){%>
<%=vid.getCurrentUserRating() %>
<%}else{%>
N/A
<% }%>
</b></td>
</tr>
<tr>
<td align="left"><b>Rating : &nbsp;</b></td>
<td align="left">
<% if(session.getAttribute("user") != null){%>
<select name = "rating" id="<%= vid.getId()%>" onchange="updateRating(this.id)" >
               <option value = "1">one</option>
               <option value = "2">two</option>
               <option value = "3">three</option>
               <option value = "4">four</option>
               <option value = "5">five</option>
</select>
<%}else{%>
N/A
<% }%>
</td>
</tr>
<tr>
<td align="left" colspan="2">
<% if(session.getAttribute("user") != null && vid.getAuthUser() != null && vid.getAuthUser().equals(((UserBean)session.getAttribute("user")).getUserName())){%>
<a href="deleteVideo?vid=<%=vid.getId() %>">Delete</a>
<%}%></td>
</tr>
</table>
</td>
</tr>
<%}
}
%>
</table>
<input type = "hidden" id="videoId" name="videoId">
<input type = "hidden" id="newRating" name="newRating">
</form>
</body>
</html>