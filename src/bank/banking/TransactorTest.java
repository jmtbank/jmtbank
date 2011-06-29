package bank.banking;

import java.rmi.Remote;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;

import bank.access.DataAccess;
import bank.access.DataAccessMock;
import bank.server.TransactionServer;

public class TransactorTest {
public static void main(String[] args) {
	Transaction trans;
	
	if(args.length == 1) {
		if(args[0].equals("l")) {
			DataAccess db = new DataAccessMock();
			trans = new TransactionProcessor(db);
		}
		else if(args[0].equals("r")) {
			Remote remotetrans;
			try {
				Registry remoteregistry = LocateRegistry.getRegistry("localhost");
				System.out.println("Located registry");
				remotetrans = remoteregistry.lookup(TransactionServer.RMI_TRANSACTION_NAME);
				System.out.println("Received remote object");
			} catch (Exception re) {
				System.out.println("Received exception while trying to retrieve remote object");
				return;
			}
			trans = (Transaction) remotetrans;
		} else { System.out.println("Incorrect parameter, use l or r"); return;}
	} else { System.out.println("Incorrect parameter, use l or r"); return;}
	try {
		System.out.println("balance acc1: "+trans.getBalance("0010000001")+"\n balance acc2: "+trans.getBalance("0010000002"));
		System.out.println("Transferring 500 from acc1 to acc2");
		trans.transfer("0010000001","0010000002",500);
		System.out.println(" balance acc1: "+trans.getBalance("0010000001")+"\n balance acc2: "+trans.getBalance("0010000002")+"\n");
		
		System.out.println("withdrawing 2000 from acc3\n Old balance: "+trans.getBalance("0010000003"));
		trans.withdraw("0010000003", 2000);
		System.out.println(" New balance: "+trans.getBalance("0010000003")+"\n");
		
		System.out.println("depositing 750 to acc4\n Old balance: "+trans.getBalance("0010000004"));
		trans.deposit("0010000004", 750);
		System.out.println(" New balance: "+trans.getBalance("0010000004")+"\n");
		
		System.out.println("Transferring from a non-existing account");
		System.out.println(trans.transfer("111111111", "222222222", 300)+"\n");
		
		System.out.println("Transferring too much from acc1 to acc2 (5000)");
		System.out.println(trans.transfer("0010000001", "0010000002", 5000)); 
		System.out.println(" balance acc1: "+trans.getBalance("0010000001")+"\n balance acc2: "+trans.getBalance("0010000002")+"\n");
		
		System.out.println("Withdrawing from non-existing account");
		System.out.println(trans.withdraw("11111111", 50)+"\n");
		
		System.out.println("Withdrawing too much from acc1 (5000)");
		System.out.println(trans.withdraw("0010000001", 5000)+"\n");
		
		System.out.println("Depositing to non-existing account");
		System.out.println(trans.deposit("111111111", 50)+"\n");
		
	}
	catch(Exception e) {
		System.out.println("error: "+e);
	}
}
}