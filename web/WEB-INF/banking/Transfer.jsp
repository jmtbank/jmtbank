<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Login</TITLE>
</HEAD>
<BODY>

<% String clientname = (String) request.getAttribute("clientname");
	String debitaccount = (String) request.getAttribute("debaccount");
	Float balance = (Float) request.getAttribute("balance"); %>

Hello, <%= clientname %><br />

Transfer: <br />
<form method="post">
From account: <%= debitaccount %> <br />
To account: <input type="text" name="crdAccountId"><br />
Amount: <input type="text" name="transferamount"><br />
<input type="submit" value="transfer">
</form><br /><br />
Available amount: <%= balance %><br /><br />
Back to <a href="./">menu</a><br />
<% String s = (String) request.getAttribute("transresult"); if(s != null) { out.println(s); } %>

</BODY></HTML>
