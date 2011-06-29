<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Login</TITLE>
</HEAD>
<BODY>
<% String clientname = (String) request.getAttribute("clientname"); 
	Float balance = (Float) request.getAttribute("balance");
	String balstr = balance.toString();
	String accountid = (String) request.getAttribute("accountid"); %>

Hello <%= clientname %>, <br />
Your account - (<%= accountid %>)<br /><br />
Balance: <%= balance %><br /><br />
Back to <a href="./">menu</a><br />
</BODY></HTML>
