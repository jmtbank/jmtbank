package banking;

public class MockTransactor implements Transaction {

	@Override
	public String deposit(String accountId, float amount)
			throws TransactionException {
		if(amount <= 0) throw new TransactionException("You can only deposit amounts larger than 0");
		Account a = Account.getAccount(accountId);
		if(a == null) throw new TransactionException("unknown account:" + accountId);
		return amount + " deposited to account: " + accountId;
	}

	@Override
	public float getBalance(String accountId) throws TransactionException {
		Account a = Account.getAccount(accountId);
		if(a == null) throw new TransactionException("unknown account");
		return a.getBalance();
	}

	@Override
	public String transfer(String debtAccountId, String creditAccountId,
			float amount) throws TransactionException {
		if(amount <= 0) throw new TransactionException("You can only transfer amounts larger than 0");
		Account debtAcc = Account.getAccount(debtAccountId);
		if(debtAcc == null) throw new TransactionException("unknown debit account:" + debtAccountId);
		Account creditAcc = Account.getAccount(creditAccountId);
		if(creditAcc == null) throw new TransactionException("unknown credit account:" + creditAccountId);
		return "Transferred " + amount + " from " + debtAccountId + " to " + creditAccountId;
	}

	@Override
	public String withdraw(String debitAccountId, float amount)
			throws TransactionException {
		// TODO Auto-generated method stub
		return null;
	}

}
