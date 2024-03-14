package Accounting;

import java.util.ArrayList;
import MiniProject.DBConnect;

public class AccountDao {
	private DBConnect db;
	
	public AccountDao() {
		db = DBConnect.getInstance();
	}
	//insert
	//계좌 추가(상품 가입)
	 public void insert(Account a) {
	 } 
	 
	 //select
	 //전체 계좌 조회
	 public ArrayList<Account> SelectAll(){
		  return null;
	 }
	 //계좌번호로 조회
	  public Account selectByNum(int num) {
		   return null;
	 }
	   
	  //update
	  //입금(입금 금액 추가)
	  public void update(Account a) {
	  }
	  //예금:출금/승인or거절 추가 
	  
	  //적금:만기 추가
	  
	   //delete
	   //계좌 삭제(탈회)(금액이 0일 경우)
	   public void delete(int num){
	      //계좌번호에 맞는거 탈퇴하기
	   } 
}