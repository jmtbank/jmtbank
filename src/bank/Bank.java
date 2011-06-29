package bank;

public class Bank {
	/*
	 * Als dit iets dynamischer moet, dan zouden we dit bijvoorbeeld
	 * met een static initializer uit een file kunnen lezen en hier kunnen setten. 
	 */
	private static final String bankcode = "001";

	/**
	 * Get our own bankcode
	 */
	public static String getBankCode() {
		return bankcode;
	}
}
