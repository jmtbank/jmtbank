<%@ page import="presentation.*" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Deposit money</TITLE>
</HEAD>
<BODY>
<%
	if(request.getAttribute("error") != null) {
		String error = (String) request.getAttribute("error");
		out.println("<div class=error>" + error + "</div>");
	}
%>
<form method="post">
<select name="account">
<%
	for(String a : (String[])request.getAttribute("accountIds")) {
		out.println("<option>" + a + "</option>");
	}
%>
</select>
Amount: <input type="text" name="amount"><br />
<input type="submit" value="Deposit">
</form>
</BODY></HTML>