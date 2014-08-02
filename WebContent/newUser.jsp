<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>My YouTube</title>
<script type="text/javascript">
	function validate()
	{
		
		var iChars = "!@#$%^&*()+=-[]\\\';,./{}|\"_:<>?0123456789";
		var iChars2 = "!@#$%^&*()+=-[]\\\';,./{}|\" :<>?";
		var firstName=document.getElementById("firstName").value;
		var lastName=document.getElementById("lastName").value;
		var userName=document.getElementById("userName").value;
		var password=document.getElementById("pwd").value;
		var cpassword=document.getElementById("cpwd").value;
		for (var i = 0; i < firstName.length; i++) 
		  {
		  	if (iChars.indexOf(firstName.charAt(i)) != -1) {
		  	alert ("Your username has special characters/numericals. \n Please remove them and try again.");
		  	return false;
		  	}
		  }
		if((firstName.length>=15)||firstName.length==0)
		{
			alert("Please enter name with maximum of 15 characters and minimum 1 character");
			return false;
		}
		for (var i = 0; i < lastName.length; i++) 
		  {
		  	if (iChars.indexOf(lastName.charAt(i)) != -1) {
		  	alert ("Your username has special characters/numericals. \n Please remove them and try again.");
		  	return false;
		  	}
		  }
		if((lastName.length>=15)||lastName.length==0)
		{
			alert("Please enter name with maximum of 15 characters and minimum 1 character");
			return false;
		}
		for(var j=0;j<userName.length;j++)
		{
			if(iChars2.indexOf(userName.charAt(j))!=-1)
			{
				alert("Username can contain only alphabets,numbers and '_'");
				return false;
			}
		}
		if((userName.length>=15)||userName.length==0)
		{
			alert("Please enter username with maximum of 15 characters and minimum 1 character");
			return false;
		}
		if(password.length==0)
		{
			alert("Please enter password.");
			return false;
		}	
		for(var j=0;j<password.length;j++)
		{
			if(" ".indexOf(password.charAt(j))!=-1)
			{
				alert("Please avoid white spaces in password.");
				return false;
			}
		}
		if(cpassword!=password)
		{
			alert("Passwords dont match.Please check them.");
			return false;
		}	
        return true;
		
	}
</script>
</head>
<body>
<table width="100%">
<tr>
<td align="center"><jsp:include page="header.jsp" /></td> 
</tr>
<tr>
<td align="center">
<form name="loginForm" action="createNewUser" method="post" onsubmit='return validate()'>
	<table border="0" align="center">
	<tr><td>
	First Name:</td><td><input name="firstName" type="text" id="firstName"  /></td></tr>
	<tr><td>
	Last Name:</td><td><input name="lastName" type="text" id="lastName" /></td></tr>
	<tr><td>
	User Name:</td><td><input name="userName" type="text" id="userName" /></td></tr>
	<tr><td>
	Password:</td><td><input name="pwd" type="password" id="pwd" /></td></tr>
	<tr><td>
	Confirm Password: </td><td><input name="cpwd" type="password" id="cpwd" /></td></tr>
	<tr><td></td><td>
	<input name="submit" type="submit" value="GO" /></td></tr>
	</table>
</form>
</td> 
</tr>
</table>
</body>
</html>