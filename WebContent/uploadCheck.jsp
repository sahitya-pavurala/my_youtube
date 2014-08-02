<%
if(session.getAttribute("user") == null){
	RequestDispatcher rd=request.getRequestDispatcher("login.jsp");
	rd.forward(request,response);
}else{
	RequestDispatcher rd=request.getRequestDispatcher("upload.jsp");
	rd.forward(request,response);
}
%>