package application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import presentation.Balances;
import banking.AuthenticatedClient;
import banking.Authentication;
import banking.AuthenticationException;
import banking.AuthenticationMethod;
import banking.Client;
import banking.MockAuthenticator;
import banking.Transaction;
import banking.TransactionException;
import banking.TransactorFactory;
import banking.AtmLimit;

public class AtmServlet extends HttpServlet {

	private Authentication auth;
	private AuthenticatedClient authClient;
	private final float MAX_DEPOSIT = 1000;
	private final float MAX_WITHDRAW = 500;
	
	public AtmServlet() {
		auth = new MockAuthenticator();
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
			HttpSession session = request.getSession();
			if(session.isNew()) {
				session.setMaxInactiveInterval(60);
			}
			authClient = (AuthenticatedClient) session.getAttribute("authClient");
			if(authClient == null || authClient.getMethod() != AuthenticationMethod.CARD) {
				String cardId = request.getParameter("cardId");
				String pin = request.getParameter("pin");
				try {
					authClient = auth.authenticateCard(cardId, pin);
					session.setAttribute("authClient", authClient);
					session.setAttribute("atmLimit", new AtmLimit());
					handleRequest(request, response);
				}
				catch(AuthenticationException authex) {
					//return to login, error
					AuthenticationMessage msg = new AuthenticationMessage(authex);
					String address = "/WEB-INF/LoginCard.jsp";
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
			HttpSession session = request.getSession();

			Transaction trans = TransactorFactory.getTransaction(authClient);
			String address;
			String clientId = authClient.getClientId();
			String path = request.getPathInfo();
			String[] accountIds = new String[0];
			for(Client c : Client.getClients()) {
				if(clientId.equals(c.getClientId())) {
					accountIds = c.getAccounts();
				}
			}
			AtmLimit lim = (AtmLimit) request.getSession().getAttribute("atmLimit");

			if("/checkbalance".equals(path)) {
				address = "/WEB-INF/banking/Checkbalance.jsp";
				List<presentation.Account> accounts = new ArrayList<presentation.Account>();
				for(String s : accountIds) {
					try {
						float balance = trans.getBalance(s);
						accounts.add(new presentation.Account(s, balance));
					} catch (TransactionException tranex) { }
				}
				Balances bean = new Balances(clientId, accounts);
				request.setAttribute("balances", bean);
			} else if ("/withdraw".equals(path)) {
				address = "/WEB-INF/atm/Withdraw.jsp";
				request.setAttribute("accountIds",accountIds);
				String account = request.getParameter("account");
				String amountStr = request.getParameter("amount");
				String error = null;
				String message = null;
				if(account != null && amountStr != null) {
					try {
						if(lim.getWithdraw() < MAX_WITHDRAW) { 
							float amount = Float.parseFloat(amountStr);
							if(!((lim.getWithdraw() + amount) > MAX_WITHDRAW)) {
								message = trans.withdraw(account, amount);
								lim.addWithdraw(amount);
								request.getSession().setAttribute("atmLimit", lim);
							}
							else {
								message = "This withdrawal would exceed the maximum alotted withdrawal amount of this session";
							}
						} else {
							message = "Already at maximum alotted withdrawal amount of this session";
						}
					} catch (NumberFormatException e) {
						error = "Amount \"" + amountStr + "\"not a valid float";
					} catch (TransactionException e) {
						error = e.getMessage();
					}
					if(error != null) {
						request.setAttribute("error", error);
					} else {
						if(message != null) {
							request.setAttribute("message", message);
						}
						address = "/WEB-INF/atm/WithdrawDone.jsp";
					}
				}
			} else if ("/deposit".equals(path)) {
				address = "/WEB-INF/atm/Deposit.jsp";					
				request.setAttribute("accountIds",accountIds);
				String account = request.getParameter("account");
				String amountStr = request.getParameter("amount");
				String error = null;
				String message = null;
				if(account != null && amountStr != null) {
					try {
						if(lim.getDeposit() < MAX_DEPOSIT) { 
							float amount = Float.parseFloat(amountStr);
							if(!((lim.getDeposit() + amount) > MAX_DEPOSIT)) {
								message = trans.deposit(account, amount);
								lim.addDeposit(amount);
								request.getSession().setAttribute("atmLimit", lim);
							}
							else {
								message = "This deposit would exceed the maximum alotted deposit amount of this session";
							}
						} else {
							message = "Already at maximum alotted deposit amount of this session";
						}
					} catch (NumberFormatException e) {
						error = "Amount \"" + amountStr + "\"not a valid float";
					} catch (TransactionException e) {
						error = e.getMessage();
					}
					if(error != null) {
						request.setAttribute("error", error);
					} else {
						if(message != null) {
							request.setAttribute("message", message);
						}
						address = "/WEB-INF/atm/DepositDone.jsp";
					}
				}
			} else if("/logout".equals(path)) {
				session.removeAttribute("authClient");
				address = "/";
				//response.sendRedirect("/");
			} else {
				address = "/WEB-INF/atm/Menu.jsp";
			}
			RequestDispatcher dispatcher = request.getRequestDispatcher(address);
			dispatcher.forward(request, response);
	}
}
