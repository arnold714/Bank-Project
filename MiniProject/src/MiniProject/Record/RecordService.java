package MiniProject.Record;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class RecordService {
	private RecordDao dao;
	
	public RecordService() {
		dao = new RecordDao();
	}

//	addRecord: 거래내역 추가
	public void addRecord(Scanner sc) {
		System.out.println("=== 거래내역 추가 ===");
		System.out.print("계좌 번호:");
		int account_num = sc.nextInt();
		System.out.print("금액:");
		int money =sc.nextInt();
		System.out.print("이름:");
		String name = sc.next();
		System.out.print("잔액:");
		int balance = sc.nextInt();
		System.out.println("거래 구분(입금/출금)");
		int isDeposit = sc.nextInt();
		dao.insert(new Record(0, account_num, money, name, balance, null, isDeposit));
	}
	
//	getAll() : 모든 거래내역 출력	
	public void getAll(Scanner sc) {
		System.out.println("===  모든 거래내역 출력 ===");
		ArrayList<Record> list = dao.selectAll();
		if (list.isEmpty()) {
			System.out.println("검색된 결과가 없습니다");
		} else {
			for (Record r : list) {
				System.out.println(r);
			}
		}
	}
//	getByDate: 날짜에 해당하는 거래내역 출력
	public void getByDate(Scanner sc) {
		System.out.println("=== 해당 날짜 거래내역 조회 ===");
		System.out.println("거래일(년/월/일):");
		String date = sc.next();
		ArrayList<Record> list = dao.selectByDate(date);
		if (list.isEmpty()) {
			System.out.println("검색된 결과 없음");
		} else {
			for (Record r: list) {
				System.out.println(r);
			}
		}
		
	}
	
//	getByDeposit: 입금에 해당하는 거래내역 출력	
	public void getByDeposit(Scanner sc, int num) {
		System.out.println("=== 입/출금===");
		System.out.print("1.입금 2.출금");
		int type = sc.nextInt();
		String[] title = {"입금", "출금"};		
		if(type==1) {//입금
			ArrayList<Record> list = dao.selectByDeposit(type);	
			for (Record r : list) {
				System.out.println(r);
			}
		}else if(type==2) {//출금
			ArrayList<Record> list = dao.selectByWithdraw(type);	
			for (Record r : list) {
				System.out.println(r);
			}
		}else {//
			System.out.println("잘못 입력됨.입출금 취소");
		}
	}
	
//	getByWithdraw: 출금에 해당하는 거래내역 출력	
	
	
	
}