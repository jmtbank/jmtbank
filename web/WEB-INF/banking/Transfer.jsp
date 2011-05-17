<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Login</TITLE>
</HEAD>
<BODY>

Transfer: <br />
<form method="post">
From account: <input type="text" name="debAccountId" value="ac1"><br />
To account: <input type="text" name="crdAccountId" value="ac2"><br />
Amount: <input type="text" name="transferamount"><br />
<input type="submit" value="transfer">
</form><br />
Back to <a href="./">menu</a><br />
<% String s = (String) request.getAttribute("transresult"); if(s != null) { out.println(s); } %>

</BODY></HTML>
