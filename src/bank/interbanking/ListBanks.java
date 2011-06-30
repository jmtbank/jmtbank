package bank.interbanking;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Arrays;

public class ListBanks {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String host = "ewi887.ewi.utwente.nl";
		if(args.length == 1) {
			host = args[0];
		}
		try {
			String url = "rmi://"+host+"/InterBank";
			System.out.println("Connecting to " + url);
			Interbank interbank = (Interbank) Naming.lookup(url);
			System.out.println("Listing registered transaction processors:");
			list(interbank.listTransactionProcessors());
			System.out.println();
			System.out.println("Listing registered authenticators:");
			list(interbank.listAuthenticators());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void list(String[] as) {
		Arrays.sort(as);
		for(String a : as) {
			System.out.println(a);
		}
	}

}
