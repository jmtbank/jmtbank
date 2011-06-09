package bank.application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;

import bank.authentication.AuthenticationException;
import bank.authentication.AuthenticationMethod;
import bank.Client;
import bank.banking.Transaction;
import bank.banking.TransactionException;

public class AtmServlet extends LoggedInServlet {

	private final float MAX_DEPOSIT = 1000;
	private final float MAX_WITHDRAW = 500;
	
	public void handleRequest(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
			String address;
			String fullname = (authClient.getFirstName() + " " + authClient.getLastName());
			String path = request.getPathInfo();
			AtmLimit lim = (AtmLimit) request.getSession().getAttribute("atmLimit");

			if("/checkbalance".equals(path)) {
				address = "/WEB-INF/banking/Checkbalance.jsp";
				String accountid = authClient.getAccountId();
				request.setAttribute("accountid", accountid);
				try {
					float balance = trans.getBalance(accountid);
					request.setAttribute("balance", balance);
				} catch (Exception tranex) { }
				request.setAttribute("clientname", fullname);
				request.setAttribute("accountid", accountid);
			} else if ("/withdraw".equals(path)) {
				address = "/WEB-INF/atm/Withdraw.jsp";
				String account = authClient.getAccountId();
				request.setAttribute("accountid", account);
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
						error = "Amount \"" + amountStr + "\" not a valid float";
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
				String account = authClient.getAccountId();
				request.setAttribute("accountid", account);
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
						error = "Amount \"" + amountStr + "\" not a valid float";
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
	@Override
	protected Client authenticate(String identifier, String secret)
			throws AuthenticationException {
		session.setAttribute("atmLimit", new AtmLimit());
		try { 
			Client cl = auth.authenticateCDClient(identifier, secret);
			session.setAttribute("loginmethod", AuthenticationMethod.CARD);
			return cl;
		}
		catch(RemoteException re) { 
			throw new AuthenticationException("Unable to authenticate: "+re.getMessage());
		}
	}
	@Override
	protected String getLoginPagePath() {
		return "/WEB-INF/LoginCard.jsp";
	}
	@Override
	protected boolean isAllowedAuth() {
		return session.getAttribute("loginmethod") == AuthenticationMethod.CARD;
	}

}
