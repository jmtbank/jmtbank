<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Login</TITLE>
</HEAD>
<BODY>
<jsp:useBean id="authMsg"
type="application.AuthenticationMessage"
scope="request" />
message: <jsp:getProperty name="authMsg" property="message" /> <br />

Please login: <br />
<form method="post">
CardId: <input type="text" name="cardId"><br />
Pin: <input type="password" name="pin"><br />
<input type="submit" value="Login">
</form>
</BODY></HTML>
