package banking;

public class AtmLimit {

	private float withdrawValue;
	private float depositValue;

	public AtmLimit() {
		withdrawValue = 0;
		depositValue = 0;
	}
	
	public AtmLimit(float withdrawValue, float depositValue) {
		this.withdrawValue = withdrawValue;
		this.depositValue = depositValue;
	}
	
	public float getDeposit() { return depositValue; }
	
	public float getWithdraw() { return withdrawValue; }
	
	public void setDeposit(float depositValue) { this.depositValue = depositValue; }
	
	public void setWithdraw(float withdrawValue) { this.withdrawValue = withdrawValue; }
	
	public void addDeposit(float deposit) { depositValue = depositValue + deposit; }
	
	public void addWithdraw(float withdraw) { withdrawValue = withdrawValue + withdraw; }
}