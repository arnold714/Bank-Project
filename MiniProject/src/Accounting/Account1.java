package Accounting;

import java.sql.Date;

public class Account1 extends Account{
	private Boolean allow;
	
	public Boolean getAllow() {
		return allow;
	}

	public void setAllow(Boolean allow) {
		this.allow = allow;
	}

	public Account1() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Account1(int account_num, String id, int balance, Date date, Boolean allow) {
		super(account_num, id, balance, date);
		this.allow = allow;
		// TODO Auto-generated constructor stub
	}

}
