<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Login</TITLE>
</HEAD>
<BODY>

Transfer: <br />
<form method="post">
<input type="text" name="debAccountId"><br />
<input type="text" name="crdAccountId"><br />
<input type="text" name="transferamount"><br />
<input type="submit" value="transfer">
</form><br />
<% String s = (String) request.getAttribute("transresult"); if(s != null) { out.println(s); } %>

</BODY></HTML>
