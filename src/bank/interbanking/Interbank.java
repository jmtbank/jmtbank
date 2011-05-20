package bank.interbanking;

/**
 * <p>Title:        Interface Interbank</p>
 * <p>Description:  Allows the transaction processing subsystem of one bank
 *                  to obtain the RMI URLs of the TransactionProcessor or
 *                  Authenticator of another bank.</p>
 * <p>Copyright:    Copyright (c) 2009</p>
 * <p>Company:      University of Twente</p>
 * @author Dick Quartel / adapted by L. Ferreira Pires
 * @version 2.0
 */

import java.rmi.*;

public interface Interbank extends Remote {

	/** Returns the RMI URL of the transaction processor of bank 'bankCode',
     * which can be used to obtain a remote reference to the transaction
     * processor by invoking Naming.lookup(url),
     * where url is the returned RMI URL.
     * @param bankCode bank code
     * @return an RMI URL, or 'null' in case the bank code does not exist.
	 */
	public String lookupTransactionProcessor(String bankCode)
		throws RemoteException;

	/** Returns the RMI URL of the authenticator of bank 'bankCode',
     * which can be used to obtain a remote reference to the authenticator
     * by invoking Naming.lookup(url),
     * where url is the returned RMI URL.
     * @param bankCode bank code
     * @return an RMI URL, or 'null' in case the bank code does not exist.
	 */
	public String lookupAuthenticator(String bankCode)
		throws RemoteException;

	/** Registers the RMI URL of the transaction processor of bank 'bankCode'.
     * @param bankCode bank code
     * @param host name of the host where the TransactionProcessor runs,
     *             and the rmiregistry where it is registered
     * @param name name of the transaction processor
	 */
	public void registerTransactionProcessor(String bankCode,
                                             String host,
                                             String name)
		throws RemoteException;

	/** Registers the RMI URL of the authenticator of bank 'bankCode'.
     * @param bankCode bank code
     * @param host name of the host where the Authenticator runs,
     *             and the rmiregistry where it is registered
     * @param name name of the authenticator
	 */
	public void registerAuthenticator(String bankCode,
                                      String host,
                                      String name)
		throws RemoteException;

	/** Registers the RMI URL of the transaction processor of bank 'bankCode'.
     * @param bankCode bank code
     * @param host name of the host where the TransactionProcessor runs,
     *             and the rmiregistry where it is registered
     * @param port port number of the rmi registry
     * @param name name of the transaction processor
	 */
	public void registerTransactionProcessor(String bankCode,
                                             String host,
                                             int port,
                                             String name)
		throws RemoteException;

	/** Registers the RMI URL of the authenticator of bank 'bankCode'.
     * @param bankCode bank code
     * @param host name of the host where the Authenticator runs,
     *             and the rmiregistry where it is registered
     * @param port port number of the rmi registry
     * @param name name of the authenticator
	 */
	public void registerAuthenticator(String bankCode,
                                      String host,
                                      int port,
                                      String name)
		throws RemoteException;


	/** Deregisters the RMI URL of the transaction processor of bank 'bankCode'.
     * @param bankCode bank code
	 */
	public void deregisterTransactionProcessor(String bankCode)
		throws RemoteException;

	/** Deregisters the RMI URL of the authenticator of bank 'bankCode'.
     * @param bankCode bank code
	 */
	public void deregisterAuthenticator(String bankCode)
		throws RemoteException;

    /** Returns a list of registered transaction processors.
     * @return array of strings formatted as: "bank code" + " - " + "url"
     */
    public String[] listTransactionProcessors()
        throws RemoteException;

    /** Returns a list of registered authenticators.
     * @return array of strings formatted as: "bank code" + " - " + "url"
     */
    public String[] listAuthenticators()
        throws RemoteException;

}