package bank.server;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.ConnectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.security.AccessControlException;

import bank.access.DataAccess;
import bank.access.DataAccessException;
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
	        
	        // testing
	        System.out.print("Testing db connection: ");
	        db.getAccount("");
	        System.out.println("OK");
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
			
			System.out.println("AuthenticationServer running.. (Press enter to stop)");
			try {
				System.in.read();
			} catch (IOException e) {
				System.err.println("Error waiting for input.");
			}
			
			System.out.println("Unbinding from the registry.");
			try {
				localRegistry.unbind(RMI_NAME);
			} catch (NotBoundException e) {
				System.err.println("Error unbinding from registry.");
			}

			System.out.println("Unexporting object.");
			if(! UnicastRemoteObject.unexportObject(auth, false)) {
				System.err.println("Error unexporting object, there might be pending calls on it, forcing in 5 seconds");
				try { Thread.sleep(5*1000); } catch (InterruptedException e) {}
				UnicastRemoteObject.unexportObject(auth, true);
			}

		} catch (NotBoundException e) {
			System.err.println("DataAccess server not found in the registry at " + dbServer);
			System.err.println("Please make sure that it is running there.");
		} catch(AccessControlException e) {
			System.err.println("AccessControl error: " + e.getMessage());
			System.err.println("Please make sure you use a good policy file");
			System.err.println("Set it with the -Djava.security.policy= before the classname");
		} catch(ConnectException e) {
			System.err.println("Could not connect to the DataAccess server at " + dbServer);
			System.err.println("Please make sure the server is running.");
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
