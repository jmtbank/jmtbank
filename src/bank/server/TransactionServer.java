package bank.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.*;

import bank.Bank;
import bank.access.DataAccess;
import bank.access.DataAccessException;
import bank.banking.Transaction;
import bank.banking.TransactionProcessing;
import bank.banking.TransactionProcessor;
import bank.banking.Transactor;
import bank.interbanking.Interbank;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.security.AccessControlException;

public class TransactionServer {
	public static final String RMI_TRANSACTIONPROCESSING_NAME = "bank.banking.TransactionProcessing";
	public static final String RMI_TRANSACTION_NAME = "bank.banking.Transaction";

	public static void main (String[] args) {
		if (System.getSecurityManager() == null) {
			System.out.println("SecurityManager null, creating new SecurityManager");
			System.setSecurityManager(new SecurityManager());
		}
		String dbServer = "localhost";
		if(args.length >= 1) {
			dbServer = args[0];
		}
		
		String hostname = null;
		if(args.length == 2) {
			hostname = args[1];
		} else {
			try {
				hostname = InetAddress.getLocalHost().getHostAddress();
			} catch (UnknownHostException e) {
				e.printStackTrace();
				System.err.println("Can't find a name for this host");
			}
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
	        TransactionProcessor transProc = new TransactionProcessor(db);
	        System.out.println("Exporting the TransactionProcessor.");
	        TransactionProcessing transProcStub = (TransactionProcessing) UnicastRemoteObject.exportObject(transProc,0);
	        System.out.println("Registering the TransactionProcessor in the registry.");	        
	        try {
				localRegistry.bind(RMI_TRANSACTIONPROCESSING_NAME, transProcStub);
			} catch (AlreadyBoundException e) {
				System.err.println("WARNING: An other transaction server is register in the registry, overwriting its binding.");
				localRegistry.rebind(RMI_TRANSACTIONPROCESSING_NAME, transProcStub);				
			}
			
			Interbank interbank = null;
			try {
				interbank = (Interbank) Naming.lookup("rmi://ewi887.ewi.utwente.nl/InterBank");
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				System.err.println("ERROR: Can't connect to the interbanking registry.");
			} 
			Transaction trans = new Transactor(transProc, interbank);

	        System.out.println("Exporting the Transactor.");
	        Transaction transStub = (Transaction) UnicastRemoteObject.exportObject(trans,0);
	        System.out.println("Registering the Transactor in the registry.");	        
	        try {
				localRegistry.bind(RMI_TRANSACTION_NAME, transStub);
			} catch (AlreadyBoundException e) {
				System.err.println("WARNING: An other transaction server is registered in the registry, overwriting its binding.");
				localRegistry.rebind(RMI_TRANSACTION_NAME, transStub);				
			}

			if(interbank != null) {
				System.out.println("Registering with the Interbanking registy as bank " +Bank.getBankCode() + " at rmi://"+hostname + "/" +RMI_TRANSACTIONPROCESSING_NAME);
				interbank.registerTransactionProcessor(Bank.getBankCode(), hostname, RMI_TRANSACTIONPROCESSING_NAME);
			}
			
			System.out.println("TransactionServer running.. (Press enter to stop)");
			try {
				System.in.read();
			} catch (IOException e) {
				System.err.println("Error waiting for input.");
			}
			
			if(interbank != null) {
				System.out.println("Deregistering with the Interbanking registy.");
				interbank.deregisterTransactionProcessor(Bank.getBankCode());
			}
			
			System.out.println("Unbinding from the registry.");
			try {
				localRegistry.unbind(RMI_TRANSACTIONPROCESSING_NAME);
			} catch (NotBoundException e) {
				System.err.println("Error unbinding from registry.");
			}

			System.out.println("Unexporting object.");
			if(! UnicastRemoteObject.unexportObject(trans, false)) {
				System.err.println("Error unexporting object, there might be pending calls on it, forcing in 5 seconds");
				try { Thread.sleep(5*1000); } catch (InterruptedException e) {}
				UnicastRemoteObject.unexportObject(trans, true);
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
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
}