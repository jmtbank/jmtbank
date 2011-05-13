<%@ page import="presentation.Account" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Login</TITLE>
</HEAD>
<BODY>
<jsp:useBean id="balances"
type="presentation.Balances"
scope="request" />

Hello <jsp:getProperty name="balances" property="clientId" />, <br />
Your account(<jsp:expression>balances.getAccounts().size()</jsp:expression>):<br />
<jsp:scriptlet>
for(Account a : balances.getAccounts()) {
</jsp:scriptlet>
	Account: <jsp:expression>a.getAccountId()</jsp:expression>
	<jsp:expression>a.getBalance()</jsp:expression> <br />
<jsp:scriptlet>
}
</jsp:scriptlet>

</BODY></HTML>
