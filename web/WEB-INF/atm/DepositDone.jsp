<%@ page import="presentation.*" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Deposit money</TITLE>
</HEAD>
<BODY>
<%
	if(request.getAttribute("message") != null) {
		String message = (String) request.getAttribute("message");
		out.println("<div class=message>" + message + "</div><br />");
	}
%>
Deposit done. <br />

<br /><br />
<a href=".">Continue</a>
</BODY></HTML>
