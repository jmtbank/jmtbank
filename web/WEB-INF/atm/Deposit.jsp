<%@ page import="bank.application.presentation.*" %>
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
	String accountid = (String)request.getAttribute("accountid");
%>
Account: <%= accountid %><br />
<form method="post">
Amount: <input type="text" name="amount"><br />
<input type="submit" value="Deposit">
</form>
<br />
Back to <a href="./">menu</a><br />
</BODY></HTML>
