package bank.authentication;

import java.rmi.Remote;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;

import bank.Client;
import bank.access.DataAccess;
import bank.access.DataAccessMock;
import bank.server.AuthenticationServer;

public class AuthenticatorTest {
	
	public static void main(String[] args) {
		Authentication auth;
		Client cl = null;
		if(args.length == 1) {
			if(args[0].equals("l")) {
				DataAccess db = new DataAccessMock();
				auth = new Authenticator(db);
			}
			else if(args[0].equals("r")) {
				Remote remoteauth;
				try {
					Registry remoteregistry = LocateRegistry.getRegistry("localhost");
					System.out.println("Located registry");
					remoteauth = remoteregistry.lookup(AuthenticationServer.RMI_NAME);
					System.out.println("Received remote object");
				} catch (Exception re) {
					System.out.println("Received exception while trying to retrieve remote object");
					return;
				}
				auth = (Authentication) remoteauth;
			} else { System.out.println("Incorrect parameter, use l or r"); return;}
		} else { System.out.println("Incorrect parameter, use l or r"); return;}
		try {
			System.out.println("CARD AUTHENTICATION");
			System.out.println("Correct login of client 1");
			try { cl = auth.authenticateCDClient("0010000001", "00001");
				if(cl == null) System.out.println("Authentication failed, client = null");
				else System.out.println("Authentication successfull, client = "+cl.getUserName());
			} catch(AuthenticationException ex) { System.out.println(ex.getMessage()); }
			
			System.out.println("\nLogging in with wrong card ID");
			try { cl = auth.authenticateCDClient("1111111111", "00001");
				if(cl == null) System.out.println("Authentication failed, client = null");
				else System.out.println("Authentication successfull, client = "+cl.getUserName());
			} catch(AuthenticationException ex) { System.out.println(ex.getMessage()); }
			
			System.out.println("\nLogging in with wrong password");
			try { cl = auth.authenticateCDClient("0010000001", "00009");
				if(cl == null) System.out.println("Authentication failed, client = null");
				else System.out.println("Authentication successfull, client = "+cl.getUserName());
			} catch(AuthenticationException ex) { System.out.println(ex.getMessage()); }
			
			System.out.println("\nBANKING AUTHENTICATION");
			System.out.println("Correct login of client 1");
			try { cl = auth.authenticateHBClient("user1", "password1");
				if(cl == null) System.out.println("Authentication failed, client = null");
				else System.out.println("Authentication successfull, client = "+cl.getUserName());
			} catch(AuthenticationException ex) { System.out.println(ex.getMessage()); }
			
			System.out.println("\nLogging in with wrong username");
			try { cl = auth.authenticateHBClient("1111111111", "password1");
				if(cl == null) System.out.println("Authentication failed, client = null");
				else System.out.println("Authentication successfull, client = "+cl.getUserName());
			} catch(AuthenticationException ex) { System.out.println(ex.getMessage()); }
			
			System.out.println("\nLogging in with wrong password");
			try { cl = auth.authenticateHBClient("user1", "00009");
				if(cl == null) System.out.println("Authentication failed, client = null");
				else System.out.println("Authentication successfull, client = "+cl.getUserName());
			} catch(AuthenticationException ex) { System.out.println(ex.getMessage()); }
		}
		catch(Exception e) { System.out.println("error: "); }
	}
}