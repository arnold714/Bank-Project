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
	public void addRecord(String account_num) {
		System.out.println("추가됨");
	}

	//번호, 계좌번호, 금액, 이름, 잔액, 날짜, 입출금
	public void addRecord(String account_num,int money, String name,int balnace,int isDeposit,String id) {
		dao.insert(new Record(0, account_num, money, name, balnace, null, isDeposit,id));
	}
	
//	getAll() : 모든 거래내역 출력	
	public void getAll(String account_num) {
		System.out.println("===  모든 거래내역 출력 ===");
		ArrayList<Record> list = dao.selectAll(account_num);
		if (list.isEmpty()) {
			System.out.println("검색된 결과가 없습니다");
		} else {
			for (Record r : list) {
				System.out.println(r);
			}
		}
	}
	
//	getByDate: 날짜에 해당하는 거래내역 출력
	public void getByDate(Scanner sc, String account_num) {
		System.out.println("=== 해당 날짜 거래내역 조회 ===");
		System.out.println("시작 거래일(년/월/일):");
		String date = sc.next();
		System.out.println("마지막 거래일(년/월/일):");
		String date1 = sc.next();
		ArrayList<Record> list = dao.selectByDate(date,date1, account_num);
		if (list.isEmpty()) {
			System.out.println("검색된 결과 없음");
		} else {
			for (Record r: list) {
				System.out.println(r);
			}
		}
		
	}
	
//	getByDeposit: 입금에 해당하는 거래내역 출력	
	public void getByDeposit(Scanner sc, String account_num) {
		System.out.println("=== 입/출금===");
		System.out.print("1.입금 2.출금");
		int type = sc.nextInt();
		String[] title = {"입금", "출금"};		
		if(type==1) {//입금
			ArrayList<Record> list = dao.selectByDeposit(type,account_num);	
			for (Record r : list) {
				System.out.println(r);
			}
		}else if(type==2) {//출금
			ArrayList<Record> list = dao.selectByDeposit(type,account_num);		
			for (Record r : list) {
				System.out.println(r);
			}
		}else {//
			System.out.println("잘못 입력됨.입출금 취소");
		}
	}
	
	
	
}