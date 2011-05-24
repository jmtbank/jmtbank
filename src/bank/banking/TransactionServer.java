package bank.banking;

import java.rmi.*;
import bank.Account;
import bank.access.DataAccess;
import bank.access.DataAccessException;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

public class TransactionServer {

	public static void main (String[] args) {
		
		Remote db;

		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}

		try {
			//B{DataAccess.lookup}
			Registry remoteregistry = LocateRegistry.getRegistry(args[0]);
			db = remoteregistry.lookup(args[1]);
			//E{DataAccess.lookup}
		} catch (Exception re) {
			System.err.println(
			"Registry exception in TransactionServer.main: " + re);
			return;
		}
		
		try {
        //B{Transaction.stub}
        	Transactor trans = new Transactor((DataAccess)db);
        	Transaction stub =
                (Transaction) UnicastRemoteObject.exportObject(trans, 0);
        //E{Transaction.stub}
        //B{Transaction.register}  
            String name = "Transaction";
            Registry localregistry = LocateRegistry.getRegistry();
            localregistry.rebind(name, stub);
        //E{Transaction.register}
            System.out.println("Transaction Server ready.");
        } catch (RemoteException re) {
            System.err.println("Exception in TransactionServer.main: " + re);
        }
	}
}