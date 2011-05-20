<%@ page import="bank.application.presentation.Account" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Login</TITLE>
</HEAD>
<BODY>
<jsp:useBean id="balances"
type="bank.application.presentation.Balances"
scope="request" />

Hello <jsp:getProperty name="balances" property="clientId" />, <br />
Your account(s) - (<%= balances.getAccounts().size() %>):<br /><br />
<%
for(Account a : balances.getAccounts()) {
%>
	Account: <%= a.getAccountId() %>
	<%= a.getBalance() %> <br />
<%
}
%>
Back to <a href="./">menu</a><br />
</BODY></HTML>
