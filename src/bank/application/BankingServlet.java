package bank.application;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;

import bank.authentication.AuthenticationException;
import bank.authentication.AuthenticationMethod;
import bank.banking.Transaction;
import bank.banking.TransactionException;
import bank.server.TransactionServer;
import bank.Client;

public class BankingServlet extends LoggedInServlet {
	private final float MAX_TRANSFER = 5000;

	public void handleRequest(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
			String address;
			String fullname = (authClient.getFirstName() + " " + authClient.getLastName());
			String path = request.getPathInfo();
			
			if("/checkbalance".equals(path)) {
				address = "/WEB-INF/banking/Checkbalance.jsp";
				String accountid = authClient.getAccountId();
				try {
					float balance = trans.getBalance(accountid);
					request.setAttribute("balance", balance);
				} catch (Exception tranex) { }
				request.setAttribute("clientname", fullname);
				request.setAttribute("accountid", accountid);
			} else if ("/transfer".equals(path)) {
				address = "/WEB-INF/banking/Transfer.jsp";
				String debAccountId = authClient.getAccountId();
				request.setAttribute("debaccount", debAccountId);
				request.setAttribute("clientname", fullname);
				String crdAccountId = request.getParameter("crdAccountId");
				String amount = request.getParameter("transferamount");
				if(debAccountId != null && crdAccountId != null && amount != null) {
					String reply;
					float currtransfer = ((Float) request.getSession().getAttribute("transferred")).floatValue();
					try {
						if(currtransfer < MAX_TRANSFER) {
							if(!((Float.parseFloat(amount) + currtransfer) > MAX_TRANSFER)) {
								reply = trans.transfer(debAccountId, crdAccountId, Float.parseFloat(amount));
								if(reply == null) {
									reply = "Transfer successful";
								}
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
				try {
					float balance = trans.getBalance(debAccountId);
					request.setAttribute("balance", balance);
				} catch (Exception tranex) { }
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
	protected Client authenticate(String identifier, String secret)
			throws AuthenticationException {
		session.setAttribute("transferred", new Float(0));
		try { 
			Client cl = auth.authenticateHBClient(identifier, secret);
			session.setAttribute("loginmethod", AuthenticationMethod.USERNAME);
			return cl;
		}
		catch(RemoteException re) { 
			throw new AuthenticationException("Unable to authenticate: "+re.getMessage());
		}
	}

	@Override
	protected String getLoginPagePath() {
		return "/WEB-INF/Login.jsp";
	}

	@Override
	protected boolean isAllowedAuth() {
		return session.getAttribute("loginmethod") == AuthenticationMethod.USERNAME;
	}
}