package bank.server;

import java.rmi.*;
import bank.Account;
import bank.access.DataAccess;
import bank.access.DataAccessException;
import bank.banking.Transaction;
import bank.banking.Transactor;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

public class TransactionServer {
	public static final String RMI_NAME = "bank.banking.Transaction";

	public static void main (String[] args) {
		Remote db;

		if (System.getSecurityManager() == null) {
			System.out.println("SecurityManager null, creating new SecurityManager");
			System.setSecurityManager(new SecurityManager());
		}
		String loc = "localhost";
		if(args.length == 1) {
			loc = args[0];
		}
		try {
			Registry remoteregistry = LocateRegistry.getRegistry(loc);
			db = remoteregistry.lookup(DataAccessServer.RMI_NAME);
		} catch (Exception re) {
			System.err.println("Registry exception in TransactionServer.main: " + re);
			return;
		}
		
		try {
        	Transactor trans = new Transactor((DataAccess)db);
        	Transaction stub =
                (Transaction) UnicastRemoteObject.exportObject(trans, 0);  
            Registry localregistry = LocateRegistry.getRegistry();
            localregistry.rebind(RMI_NAME, stub);
            System.out.println("Transaction Server ready.");
        } catch (RemoteException re) {
            System.err.println("Exception in TransactionServer.main: " + re);
        }
	}
}