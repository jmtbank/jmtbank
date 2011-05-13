package banking;

public interface Transaction {
    public String transfer(String debtAccountId, String creditAccountId, float amount) throws TransactionException;
    public String deposit(String creditAccountId, float amount) throws TransactionException;
    public String withdraw(String debitAccountId, float amount) throws TransactionException;
    public float getBalance(String accountId) throws TransactionException;
}
