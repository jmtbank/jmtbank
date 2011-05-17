package application;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import banking.*;
import presentation.*;

public class BankingServlet extends LoggedInServlet {
	private final float MAX_TRANSFER = 5000;

	public void handleRequest(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
			Transaction trans = TransactorFactory.getTransaction(authClient);

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
					float currtransfer = ((Float) request.getSession().getAttribute("transferred")).floatValue();
					try {
						if(currtransfer < MAX_TRANSFER) {
							if(!((Float.parseFloat(amount) + currtransfer) > MAX_TRANSFER)) {
								reply = trans.transfer(debAccountId, crdAccountId, Float.parseFloat(amount));
								currtransfer = currtransfer + Float.parseFloat(amount);
								request.getSession().setAttribute("transferred", new Float(currtransfer));
							}
							else { reply = "This transfer would exceed the maximum alotted transfer amount per session"; }
						}
						else { reply = "Already at maximum alotted transfer amount for this session"; }
					} catch(TransactionException tranex) { reply = tranex.getMessage(); }
					catch(NumberFormatException e) {
						reply = "Amount is not a valid number";
					}
					request.setAttribute("transresult", reply);
				}
			} else if("/logout".equals(path)) {
				request.getSession().invalidate();
				request.setAttribute("authMsg", new AuthenticationMessage("Logout successful"));
				address = "";
			} else {
				address = "/WEB-INF/banking/Menu.jsp";
			}
			RequestDispatcher dispatcher = request.getRequestDispatcher(address);
			dispatcher.forward(request, response);
	}

	@Override
	protected AuthenticatedClient authenticate(String identifier, String secret)
			throws AuthenticationException {
		session.setAttribute("transferred", new Float(0));
		return auth.authenticateClient(identifier, secret);
	}

	@Override
	protected String getLoginPagePath() {
		return "/WEB-INF/Login.jsp";
	}

	@Override
	protected boolean isAllowedAuth(AuthenticatedClient authcl) {
		return authcl.getMethod() == AuthenticationMethod.USERNAME;
	}
}