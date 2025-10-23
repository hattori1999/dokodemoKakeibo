package model;

import java.time.LocalDate;

public class Entry {
	private int userId;
	private int categoryId;
	private String name;
	private int price;
	private LocalDate date;
	private boolean creditFlag;
	private boolean purposeFlag;
	
	public Entry() {}
	
	public Entry(int userId, int categoryId, String name, int price, LocalDate date,
			boolean creditFlag, boolean purposeFlag) {
		this.userId = userId;
		this.categoryId = categoryId;
		this.name = name;
		this.price = price;
		this.date = date;
		this.creditFlag = creditFlag;
		this.purposeFlag = purposeFlag;
		
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public boolean isCreditFlag() {
		return creditFlag;
	}

	public void setCreditFlag(boolean creditFlag) {
		this.creditFlag = creditFlag;
	}

	public boolean isPurposeFlag() {
		return purposeFlag;
	}

	public void setPurposeFlag(boolean purposeFlag) {
		this.purposeFlag = purposeFlag;
	}
	
	
	
}
