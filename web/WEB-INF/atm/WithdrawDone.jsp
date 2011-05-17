<%@ page import="presentation.*" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Withdraw money</TITLE>
</HEAD>
<BODY>
<%
	if(request.getAttribute("message") != null) {
		String message = (String) request.getAttribute("message");
		out.println("<div class=message>" + message + "</div><br />");
	}
%>
Withdraw done. <br />
Please take your money from the money dispencing slot.

<br /><br />
<a href=".">Continue</a>
</BODY></HTML>
