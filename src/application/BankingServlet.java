package application;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import banking.*;
import presentation.*;

public class BankingServlet extends HttpServlet {
	private Authentication auth;
	private Transaction trans;
	private AuthenticatedClient authClient;
	
	public BankingServlet() {
		super();
		auth = new MockAuthenticator();
		trans = new MockTransactor();
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
						AuthenticationMessage msg;
						if(userid != null && password != null) {
							msg = new AuthenticationMessage(authex);
						} else {
							if (request.getAttribute("authMsg") != null) {
								msg = (AuthenticationMessage) request.getAttribute("authMsg");
								}
								else {
								msg = new AuthenticationMessage("Please log in");
								}
						}
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
			
			if("/checkbalance".equals(path)) {
				address = "/WEB-INF/banking/Checkbalance.jsp";
				String[] accountIds = new String[0];
				for(Client c : Client.getClients()) {
					if(clientId.equals(c.getClientId())) {
						accountIds = c.getAccounts();
					}
				}
				List<presentation.Account> accounts = new ArrayList<presentation.Account>();
				for(String s : accountIds) {
					try {
						float balance = trans.getBalance(s);
						accounts.add(new presentation.Account(s, balance));
					} catch (TransactionException tranex) { }
				}
				Balances bean = new Balances(clientId, accounts);
				request.setAttribute("balances", bean);
			} else if ("/transfer".equals(path)) {
				address = "/WEB-INF/banking/Transfer.jsp";
				String debAccountId = request.getParameter("debAccountId");
				String crdAccountId = request.getParameter("crdAccountId");
				String amount = request.getParameter("transferamount");
				if(debAccountId != null && crdAccountId != null && amount != null) {
					String reply;
					try {
						reply = trans.transfer(debAccountId, crdAccountId, Float.parseFloat(amount));
					} catch(TransactionException tranex) { reply = tranex.getMessage(); }
					request.setAttribute("transresult", reply);
				}
			} else if("/logout".equals(path)) {
				//HttpSession session = request.getSession();
				request.getSession().invalidate();
				//session.removeAttribute("authClient");
				request.setAttribute("authMsg", new AuthenticationMessage("Logout successful"));
				address = "";
			} else {
				address = "/WEB-INF/banking/Menu.jsp";
			}
			RequestDispatcher dispatcher = request.getRequestDispatcher(address);
			dispatcher.forward(request, response);
	}
}