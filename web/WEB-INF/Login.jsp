<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Login</TITLE>
</HEAD>
<BODY>
<jsp:useBean id="authMsg"
type="application.AuthenticationMessage"
scope="request" />
<jsp:getProperty name="authMsg" property="message" /> <br />

<form method="post">
Username: <input type="text" name="username"><br />
Password: <input type="password" name="password"><br />
<input type="submit" value="Login">
</form>
Back to <a href="../">menu</a>
</BODY></HTML>
