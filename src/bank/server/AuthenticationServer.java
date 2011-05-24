package bank.server;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import bank.access.DataAccess;
import bank.authentication.Authentication;
import bank.authentication.Authenticator;

public class AuthenticationServer {
	public static final String RMI_NAME = "bank.authentication.Authentication";

	public static void main(String[] args) {
		if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
		String dbServer = "localhost";
		if(args.length == 1) {
			dbServer = args[0];
		}
		try {
	        Registry dbRegistry = LocateRegistry.getRegistry(dbServer);
			System.out.println("Getting the database from the registry.");	        
	        DataAccess db = (DataAccess) dbRegistry.lookup(DataAccessServer.RMI_NAME);
	        
	        Registry localRegistry = LocateRegistry.getRegistry();
	        Authenticator auth = new Authenticator(db);
	        System.out.println("Exporting the Authenticator.");
	        Authentication authStub = (Authentication) UnicastRemoteObject.exportObject(auth,0);
	        System.out.println("Registering the Authenticator in the registry.");	        
	        try {
				localRegistry.bind(RMI_NAME, authStub);
			} catch (AlreadyBoundException e) {
				System.err.println("WARNING: An other auth server is register in the registry, overwriting its binding.");
				localRegistry.rebind(RMI_NAME, authStub);				
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}

}
