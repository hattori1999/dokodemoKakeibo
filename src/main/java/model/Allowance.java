package model;

public class Allowance {
	private int userId;
	private int amount;
	
	public Allowance(int userId,int amount) {
		this.userId = userId;
		this.amount = amount;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

}
