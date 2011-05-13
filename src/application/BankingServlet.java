package application;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import banking.AuthenticatedClient;
import banking.Authentication;
import banking.AuthenticationException;
import banking.MockAuthenticator;
import banking.Client;
import banking.Account;
import presentation.*;

public class BankingServlet extends HttpServlet {
	private Authentication auth;
	private AuthenticatedClient authClient;
	
	public BankingServlet() {
		super();
		auth = new MockAuthenticator();

	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
			HttpSession session = request.getSession();
			if(session.isNew()) {
				session.setMaxInactiveInterval(60);
			}
			authClient = (AuthenticatedClient) session.getAttribute("authClient");
			if(authClient == null /* || authClient.getMethod() != USERNAME */) {
				String userid = request.getParameter("username");
				String password = request.getParameter("password");
				try {
					authClient = auth.authenticateClient(userid, password);
					session.setAttribute("authClient", authClient);
					handleRequest(request, response);
				}
				catch(AuthenticationException authex) {
					//return to login, error
					AuthenticationMessage msg = new AuthenticationMessage(authex);
					String address = "/WEB-INF/Login.jsp";
					request.setAttribute("authMsg", msg);
					RequestDispatcher dispatcher = request.getRequestDispatcher(address);
					dispatcher.forward(request, response);					
				}
			} else {
				handleRequest(request, response);
			}
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
			doGet(request, response);
	}
	
	public void handleRequest(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
			String address;
			String clientId = authClient.getClientId();
			String path = request.getPathInfo();
			String[] accountIds = new String[0];
			for(Client c : Client.getClients()) {
				if(clientId.equals(c.getClientId())) {
					accountIds = c.getAccounts();
				}
			}
			List<presentation.Account> accounts = new ArrayList<presentation.Account>();
			for(String s : accountIds) {
				accounts.add(banking.Account.getAccount(s));
			}
			Balances bean = new Balances(clientId, accounts);
			if("/checkbalance".equals(path)) {
				address = "/WEB-INF/banking/Checkbalance.jsp";
				request.setAttribute("balances", bean);
			} else if ("/transfer".equals(path)) {
				address = "/WEB-INF/banking/Transfer.jsp";					
			} else if("/logout".equals(path)) {
				// what to do
				address = "/WEB-INF/.jsp";
			} else {
				address = "/WEB-INF/banking/Menu.jsp";
			}
			RequestDispatcher dispatcher = request.getRequestDispatcher(address);
			dispatcher.forward(request, response);
	}
}
