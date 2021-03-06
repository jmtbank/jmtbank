package bank.application;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;

import bank.authentication.Authentication;
import bank.authentication.AuthenticationException;
import bank.Client;
import bank.server.AuthenticationServer;
import bank.banking.Transaction;
import bank.server.TransactionServer;

public abstract class LoggedInServlet extends HttpServlet {
	protected Authentication auth;
	protected Transaction trans;
	protected Client authClient;
	protected HttpSession session;
	
	/**
	 * Clear our RMI connections, so we can try to reconnect on the next request.
	 */
	public void clear() {
		auth = null;
		trans = null;
	}
	public void init() {
		String authloc = getInitParameter("authenticationserver");
		if(authloc == null)
			authloc = "localhost";
		Remote remoteauth;
		try {
			Registry remoteregistry = LocateRegistry.getRegistry(authloc);
			remoteauth = remoteregistry.lookup(AuthenticationServer.RMI_NAME);
		} catch (RemoteException e) {
			//todo: wat hier?
			//return;
			throw new RuntimeException("Connection to authenticationserver failed",e);
		} catch (NotBoundException e) {
			throw new RuntimeException("Connection to authenticationserver failed",e);
		}
			auth = (Authentication) remoteauth;
		
		String transloc = getInitParameter("transactionserver");
		if(transloc == null)
			transloc = "localhost";
		Remote remotetrans;
		try {
			Registry remoteregistry = LocateRegistry.getRegistry(transloc);
			remotetrans = remoteregistry.lookup(TransactionServer.RMI_TRANSACTION_NAME);
		} catch (Exception e) {
			//todo: wat hier?
			e.printStackTrace();
			throw new RuntimeException("Connection to transactionserver failed",e);
		}
		trans = (Transaction) remotetrans;
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		if(auth == null) {
			//(re)connect to rmi objects
			init();
		}
		session = request.getSession();
		if(session.isNew()) {
			session.setMaxInactiveInterval(60);
		}
		authClient = (Client) session.getAttribute("authClient");
		if(authClient == null || ! isAllowedAuth()) {
			String identifier = request.getParameter("identifier");
			String secret = request.getParameter("secret");
			try {
				authClient = authenticate(identifier, secret);
				session.setAttribute("authClient", authClient);
				if(authClient != null) { handleRequest(request, response); }
				else { throw new AuthenticationException("authclient null"); }
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
	protected abstract Client authenticate(String identifier, String secret) throws AuthenticationException;
	protected abstract boolean isAllowedAuth();
}