package Accounting.Saving;

import java.util.ArrayList;
import java.util.Scanner;

import Accounting.AccountDao;
import MiniProject.Bank.BankDao;
import MiniProject.Members.MembersDao;
import MiniProject.Members.MembersService;
import MiniProject.Record.RecordService;

public class SavingService {
	private SavingDao dao;
	public static String loginId;
	private RecordService rs;
	private MembersDao mdao;
	private BankDao bdao;
	private AccountDao adao;

	public SavingService() {
		dao = new SavingDao();
		rs = new RecordService();
		mdao = new MembersDao();
		bdao = new BankDao();
		adao = new AccountDao();
	}

	// 계좌 추가
	public void addAccount(Scanner sc) {
		System.out.println("===계좌추가===");
		int exp = 0;
		double per;
		System.out.print("입금 금액을 입력하세요:");
		int balance = sc.nextInt();
		do {
			System.out.print("가입 개월을 선택하세요.(3개월 0.2%, 6개월 0.6%, 12개월 0.9%): ");
			exp = sc.nextInt();
		} while (!(exp == 3 || exp == 6 || exp == 12));
		if (exp == 3) {
			per = 0.2;
		} else if (exp == 6) {
			per = 0.6;
		} else {
			per = 0.9;
		}
		;
		String account_num = bdao.insert(2);
		dao.insert(new Saving(account_num, MembersService.loginId, balance, null, null, per, 0), exp);
		System.out.println("===개설이 완료되었습니다===");
	}

	// 계좌 번호 조회
	public void getByNum(Scanner sc) {
		System.out.println("===계좌번호로 검색===");
		System.out.println("계좌번호:");
		String account_num = sc.next();
		// 계좌의 주인일 때도 조회.
		Saving s = dao.selectByNum(account_num);
		if (s == null) {
			System.out.println("없는 계좌번호");
			return;
		}
		// 조회시 날짜가 음수이면 > exprie 확인후 0이면 이자 지급
		if (dao.getExpDate(account_num) <= 0 && s.getExpiry() == 0) {
			dao.updateExp(s);
		}
		System.out.println("1.입금하기 2.출금하기 3.송금하기 4.계좌내역보기 5.계좌삭제하기");
		int x = sc.nextInt();
		switch (x) {
		case 1:
			// 만기일 지나면 입금 막기
			if (dao.getExpDate(account_num) > 0) {
				deposit(sc, s, account_num);
				break;
			}
			System.out.println("만기일이 지나 입금 할 수 없습니다.");
			break;
		case 2:
			if (dao.getExpDate(account_num) > 0) {
				System.out.println("만기 전 출금입니다.");
			}
			;
			withdraw(sc, s, account_num);
			break;
		case 3:
			if (dao.getExpDate(account_num) > 0) {
				System.out.println("만기 전 출금입니다.");
			}
			;
			remittance(sc, account_num);
			break;
		case 4:
			runRecord(sc, account_num);
			break;
		case 5:
			delSaving(account_num);
			break;
		}
	}

	// 적금 조회(전체 조회)
	public void printAll() {
		System.out.println("===전체 적금 계좌===");
		ArrayList<Saving> list = dao.SelectAll(MembersService.loginId);
		if (list.isEmpty()) {
			System.out.println("적금계좌가 존재하지 않습니다.");
		} else {
			for (Saving s : list) {
				System.out.println(s);
			}
		}
	}

	// 입출금
	public void deposit(Scanner sc, Saving s, String account_num) {
		System.out.println("==입금==");
		System.out.println("얼마를 입금하시겠습니까?");
		int money = sc.nextInt();
		while (money < 0) {
			System.out.println("다시 입력해 주십시오.");
			money = sc.nextInt();
		}
		;
		dao.update(money, account_num);
		System.out.println("입금이 완료되었습니다.");
		rs.addRecord(account_num, money, MembersService.name, dao.selectByNum(account_num).getBalance(), 1,
				MembersService.loginId);
	}

	// 출금
	public void withdraw(Scanner sc, Saving s, String account_num) {
		System.out.println("==출금==");
		System.out.println("얼마를 출금하시겠습니까?");
		int money = sc.nextInt();
		while (money < 0) {
			System.out.println("다시 입력해 주십시오.");
			money = sc.nextInt();
		}
		if (s.getBalance() < money) {
			System.out.println("계좌에 잔액이 부족합니다.");
		} else {
			dao.update(money * -1, account_num);
			System.out.println("출금이 완료되었습니다.");
			rs.addRecord(account_num, money * (-1), MembersService.name, dao.selectByNum(account_num).getBalance(), 2,
					MembersService.loginId);
		}
	}

	// 송금
	public void remittance(Scanner sc, String account_num) {
		System.out.println("==송금==");
		System.out.println("송금받을 계좌를 입력해 주십시오.");
		String remit_num = sc.next();
		if (bdao.select(remit_num).getIsAccount() == 1) {//송금보내는 계좌가 예금계좌
			if (adao.selectByNum(remit_num).getAllow() != false) {
				int money = 0;
				System.out.println(mdao.select(adao.selectByNum(remit_num).getId()).getName() + "님에게 송금하시겠습니까? 1.예 2.아니오");
				if (sc.nextInt() == 1) {
					System.out.println("얼마를 출금하시겠습니까?");
					money = sc.nextInt();
					while (money < 0) {
						System.out.println("다시 입력해 주십시오.");
						money = sc.nextInt();
					}
					if (dao.selectByNum(account_num).getBalance() < money) {
						System.out.println("계좌에 잔액이 부족합니다.");
					} else {
						dao.update(money * -1, account_num);
						adao.update(money, remit_num);
						System.out.println("이체가 완료되었습니다.");
						// 입금 받을 사람(+//입금)
						// 계좌받기,금액 받기
						// 자동용>입금자의 이름,잔액,아이디
						rs.addRecord(remit_num, money, mdao.select(adao.selectByNum(remit_num).getId()).getName(),
								adao.selectByNum(remit_num).getBalance(), 1, adao.selectByNum(remit_num).getId());
						// 입금 하는 사람(-//출금)
						rs.addRecord(account_num, money * (-1), MembersService.name,
								dao.selectByNum(account_num).getBalance(), 2, MembersService.loginId);
					}
				}
			}else {
				System.out.println("승인되지 않은 계좌입니다.");
			}
		}else if (bdao.select(remit_num).getIsAccount() == 2) {//적금계좌에서 적금계좌로 송금
			int money = 0;
			System.out.println(mdao.select(dao.selectByNum(remit_num).getId()).getName() + "님에게 송금하시겠습니까? 1.예 2.아니오");
			if (sc.nextInt() == 1) {
				System.out.println("얼마를 출금하시겠습니까?");
				money = sc.nextInt();
				while (money < 0) {
					System.out.println("다시 입력해 주십시오.");
					money = sc.nextInt();
				}
				if (dao.selectByNum(account_num).getBalance() < money) {
					System.out.println("계좌에 잔액이 부족합니다.");
				} else {
					dao.update(money, remit_num);
					dao.update(money * -1, account_num);

					System.out.println("이체가 완료되었습니다.");
					// 입금 받을 사람(+//입금)
					// 계좌받기,금액 받기
					// 자동용>입금자의 이름,잔액,아이디
					rs.addRecord(remit_num, money, MembersService.name, dao.selectByNum(remit_num).getBalance(), 1,
							dao.selectByNum(remit_num).getId());
					// 입금 하는 사람(-//출금)
					rs.addRecord(account_num, money * (-1), mdao.select(dao.selectByNum(remit_num).getId()).getName(),
							dao.selectByNum(account_num).getBalance(), 2, MembersService.loginId);
				}
			}
		}
	}

	// 계좌 번호 조회
	public void selectSaving(Scanner sc) {
		System.out.println("계좌 확인");
		System.out.print("조회할 계좌 번호를 입력하세요:");
		String account_num = sc.next();
		// 계좌의 주인일 때도 조회.
		Saving s = dao.selectByNum(account_num);
		if (s.equals(null) || !s.getId().equals(MembersService.loginId)) {
			System.out.println("조회 할 수 없는 계좌입니다.");
			return;
		}
		System.out.println(s);
	}

	// 계좌 삭제
	public void delSaving(String num) {
		// 계좌 번호 조회 후 금액이 0이면 삭제, 금액이 남으면 삭제 불가능
		System.out.println("===삭제===");
		if (dao.selectByNum(num).getBalance() == 0) {
			dao.delete(num);
			System.out.println("삭제가 완료되었습니다");
		} else {
			System.out.println("계좌에 잔액이 없어야 삭제할 수 있습니다.");
		}
	}

	public void runRecord(Scanner sc, String account_num) {
		boolean flag = true;
		while (flag) {
			System.out.println("1.전체 조회 2.입출금으로 조회 3.날짜로 조회 4.종료");
			int m = sc.nextInt();
			switch (m) {
			case 1:
				rs.getAll(account_num);
				break;
			case 2:
				rs.getByDeposit(sc, account_num);
				break;
			case 3:
				rs.getByDate(sc, account_num);
				break;
			case 4:
				flag = false;
				break;
			}
		}
	}
}
