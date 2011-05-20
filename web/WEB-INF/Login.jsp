<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Login</TITLE>
</HEAD>
<BODY>
<jsp:useBean id="authMsg"
type="bank.application.AuthenticationMessage"
scope="request" />
<jsp:getProperty name="authMsg" property="message" /> <br />

<form method="post">
Username: <input type="text" name="identifier"><br />
Password: <input type="password" name="secret"><br />
<input type="submit" value="Login">
</form>
Back to <a href="../">menu</a>
</BODY></HTML>
