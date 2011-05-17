package application;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import banking.AuthenticatedClient;
import banking.Authentication;
import banking.AuthenticationException;
import banking.AuthenticationMethod;
import banking.MockAuthenticator;

public abstract class LoggedInServlet extends HttpServlet {
	protected Authentication auth;
	protected AuthenticatedClient authClient;
	protected HttpSession session;
	
	public LoggedInServlet() {
		super();
		auth = new MockAuthenticator();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		session = request.getSession();
		if(session.isNew()) {
			session.setMaxInactiveInterval(60);
		}
		authClient = (AuthenticatedClient) session.getAttribute("authClient");
		if(authClient == null || ! isAllowedAuth(authClient)) {
			String identifier = request.getParameter("identifier");
			String secret = request.getParameter("secret");
			try {
				authClient = authenticate(identifier, secret);
				session.setAttribute("authClient", authClient);
				handleRequest(request, response);
			}
			catch(AuthenticationException authex) {
				//return to login, error
				AuthenticationMessage msg;
				if(identifier != null && secret != null) {
					msg = new AuthenticationMessage(authex);
				} else {
					if (request.getAttribute("authMsg") != null) {
						msg = (AuthenticationMessage) request.getAttribute("authMsg");
					}
					else {
						msg = new AuthenticationMessage("Please log in");
					}
				}
				String address = getLoginPagePath();
				request.setAttribute("authMsg", msg);
				RequestDispatcher dispatcher = request.getRequestDispatcher(address);
				dispatcher.forward(request, response);					
			}
		} else {
			handleRequest(request, response);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	public abstract void handleRequest(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException;

	protected abstract String getLoginPagePath();
	protected abstract AuthenticatedClient authenticate(String identifier, String secret) throws AuthenticationException;
	protected abstract boolean isAllowedAuth(AuthenticatedClient authcl);
}