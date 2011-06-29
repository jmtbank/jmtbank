package bank.banking;

import java.rmi.*;
import bank.Account;
import bank.access.DataAccess;
import bank.access.DataAccessMock;
import bank.access.DataAccessException;

public class TransactorLocalTest {
public static void main(String[] args) {

	DataAccess db = new DataAccessMock();
	Transaction trans = new TransactionProcessor(db);
	try {
		System.out.println("balance acc1: "+trans.getBalance("0010000001")+"\n balance acc2: "+trans.getBalance("0010000002"));
		System.out.println("Transferring 500 from acc1 to acc2");
		trans.transfer("0010000001","0010000002",500);
		System.out.println(" balance acc1: "+trans.getBalance("0010000001")+"\n balance acc2: "+trans.getBalance("0010000002"));
		System.out.println("withdrawing 2000 from acc3\n Old balance: "+trans.getBalance("0010000003"));
		trans.withdraw("0010000003", 2000);
		System.out.println(" New balance: "+trans.getBalance("0010000003"));
		System.out.println("depositing 750 to acc4\n Old balance: "+trans.getBalance("0010000004"));
		trans.deposit("0010000004", 750);
		System.out.println(" New balance: "+trans.getBalance("0010000004"));
	}
	catch(Exception e) {
		System.out.println("error: "+e);
	}
}
}