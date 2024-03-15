package Accounting;

import java.sql.Date;

public class Account {
	//계좌번호
	private String account_num;
	//계좌주 id
	private String id;
	//금액(잔액)
	private int balance;
	//가입일
	private Date date;
	//예금:승인여부
	//적금:만기일,이자율
	
	public Account() {}
	
	public Account(String account_num, String id, int balance, Date date) {
		this.account_num = account_num;
		this.id = id;
		this.balance = balance;
		this.date = date;
	}
	
	public String getAccount_num() {
		return account_num;
	}

	public void setAccount_num(String account_num) {
		this.account_num = account_num;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getBalance() {
		return balance;
	}
	public void setBalance(int balance) {
		this.balance = balance;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Account [account_num=" + account_num + ", id=" + id + ", balance=" + balance + ", date=" + date + "]";
	}
}
