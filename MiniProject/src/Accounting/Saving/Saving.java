package Accounting.Saving;

import java.sql.Date;

import Accounting.Account;

public class Saving extends Account {
	//적금:만기일,이자율
	private Date expDate;
	private double doublepercent;
	private int expiry;
	
	public Saving() {
		super();
	}
	public Saving(int account_num, String id, int balance, Date date,Date expDate,double doublepercent,int expiry) {
		super(account_num, id, balance, date);
		this.expDate = expDate;
		this.doublepercent = doublepercent;
		this.expiry = expiry;
	}
	public Date getExpDate() {
		return expDate;
	}
	public void setExpDate(Date expDate) {
		this.expDate = expDate;
	}
	public double getDoublepercent() {
		return doublepercent;
	}
	public void setDoublepercent(double doublepercent) {
		this.doublepercent = doublepercent;
	}

	public int getExpiry() {
		return expiry;
	}
	public void setExpiry(int expiry) {
		this.expiry = expiry;
	}
	@Override
	public String toString() {
		String exp ="만기 전";
		if(expiry==1) {
			exp = "만기 완료";
		}
		return "Saving [계좌번호=" + getAccount_num() + ", 계좌주=" + getId() + ", 금액=" + getBalance() + ", 가입일=" + getDate()+ "만기일=" + expDate + ", 이자율=" + doublepercent + "%, 만기 여부="+exp+"]";
	}
	
	
}
