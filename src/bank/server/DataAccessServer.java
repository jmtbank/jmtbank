package bank.server;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import bank.access.DataAccess;
import bank.access.DataAccessMock;

public class DataAccessServer {
	public static final String RMI_NAME = "bank.access.DataAccess";

	public static void main(String[] args) {
        if (System.getSecurityManager() == null) {
			System.out.println("SecurityManager null, creating a new one.");
			System.setSecurityManager(new SecurityManager());
        }

        DataAccess db = new DataAccessMock();
		try {
			System.out.println("Exporting DataAccessMock object");
			DataAccess dbstub = (DataAccess) UnicastRemoteObject.exportObject(db,0);
			Registry registry = LocateRegistry.getRegistry();
			System.out.println("Registering with the registry");
	        try {
				registry.bind(RMI_NAME, dbstub);
			} catch (AlreadyBoundException e) {
				System.err.println("WARNING: An other auth server is register in the registry, overwriting its binding.");
				registry.rebind(RMI_NAME, dbstub);
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("End of main method.");
	}

}
