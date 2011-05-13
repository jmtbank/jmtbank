<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Withdraw money</TITLE>
</HEAD>
<BODY>
<form method="post">
<select name="account">
<%
	for(String a : (String[])request.getAttribute("accountIds")) {
		out.println("<option>" + a + "</option>");
	}
%>
</select>
Amount: <input type="text" name="amount"><br />
<input type="submit" value="Withdraw">
</form>
</BODY></HTML>
