package Accounting;

import java.util.ArrayList;
import java.util.Scanner;

import Accounting.Saving.SavingDao;
import MiniProject.Bank.BankDao;
import MiniProject.Members.MembersDao;
import MiniProject.Members.MembersService;
import MiniProject.Record.RecordService;

public class AccountService {
	private AccountDao dao;
	private RecordService rs;
	private MembersDao mdao;
	private BankDao bdao;
	private SavingDao sdao;

	public AccountService() {
		dao = new AccountDao();
		rs = new RecordService();
		mdao = new MembersDao();
		bdao = new BankDao();
		sdao = new SavingDao();
	}

	public void addAccount(String id) {
		System.out.println("===계좌추가===");
		String account_num = bdao.insert(1);
		dao.insert(account_num);
		System.out.println("===개설이 완료되었습니다===");
		System.out.println("===사용전 관리자의 승인이 필요하니다===");
	}

	public void getByNum(Scanner sc) {
		System.out.println("===계좌번호로 검색===");
		System.out.println("계좌번호:");
		String account_num = sc.next();
		Account1 account = dao.selectByNum(account_num);
		if (account == null) {
			System.out.println("없는 계좌번호");
		} else {
			if (MembersService.loginId.equals(account.getId())) {//
				System.out.println(account);
				System.out.println("1.입금하기 2.출금하기 3.송금하기 4.계좌내역보기 5.계좌삭제하기");
				int x = sc.nextInt();
				switch (x) {
				case 1:
					if (account.getAllow() == false) {
						System.out.println("승인되지 않은 계좌입니다.");
						break;
					}
					deposit(sc, account_num);
					System.out.println(dao.selectByNum(account_num));
					break;
				case 2:
					if (account.getAllow() == false) {
						System.out.println("승인되지 않은 계좌입니다.");
						break;
					}
					withdraw(sc, account_num);
					System.out.println(dao.selectByNum(account_num));
					break;
				case 3:
					if (account.getAllow() == false) {
						System.out.println("승인되지 않은 계좌입니다.");
						break;
					}
					remittance(sc, account_num);
					System.out.println(dao.selectByNum(account_num));
					break;
				case 4:
					if (account.getAllow() == false) {
						System.out.println("승인되지 않은 계좌입니다.");
						break;
					}
					runRecord(sc, account_num);
					// 계좌내역보기
					break;
				case 5:
					delAccount(account_num);
					break;
				}
			} else {
				System.out.println("본인의 계좌가 아닙니다.");
			}
		}
	}

	public void getAll() {
		System.out.println("===전체 계좌===");
		ArrayList<Account1> list = dao.SelectById(MembersService.loginId);
		if (list.isEmpty()) {
			System.out.println("검색 결과가 없습니다");
		} else {
			for (Account1 a : list) {
				System.out.println(a);
			}
		}
	}

	public void getAllManager() {
		System.out.println("===전체 계좌===");
		ArrayList<Account1> list = dao.SelectAll();
		if (list.isEmpty()) {
			System.out.println("검색 결과가 없습니다");
		} else {
			for (Account1 a : list) {
				System.out.println(a);
			}
		}
	}

	public void deposit(Scanner sc, String account_num) {
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

	public void withdraw(Scanner sc, String account_num) {
		System.out.println("==출금==");
		System.out.println("얼마를 출금하시겠습니까?");
		int money = sc.nextInt();
		while (money < 0) {
			System.out.println("다시 입력해 주십시오.");
			money = sc.nextInt();
		}
		if (dao.selectByNum(account_num).getBalance() < money) {
			System.out.println("계좌에 잔액이 부족합니다.");
		} else {
			dao.update(money * -1, account_num);
			System.out.println("출금이 완료되었습니다.");
			rs.addRecord(account_num, money * (-1), MembersService.name, dao.selectByNum(account_num).getBalance(), 2,
					MembersService.loginId);
		}
	}

	public void remittance(Scanner sc, String account_num) {
		System.out.println("==송금==");
		System.out.println("송금받을 계좌를 입력해 주십시오.");
		String remit_num = sc.next();

		if (bdao.select(remit_num).getIsAccount() == 1) {
			if (dao.selectByNum(remit_num).getAllow() != false) {
				int money = 0;
				System.out
						.println(mdao.select(dao.selectByNum(remit_num).getId()).getName() + "님에게 송금하시겠습니까? 1.예 2.아니오");
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
						rs.addRecord(account_num, money * (-1),
								mdao.select(dao.selectByNum(remit_num).getId()).getName(),
								dao.selectByNum(account_num).getBalance(), 2, MembersService.loginId);
					}
				}
			} else {
				System.out.println("승인되지 않은 계좌입니다.");
			}
		} else if (bdao.select(remit_num).getIsAccount() == 2) {
			int money = 0;
			System.out.println(mdao.select(sdao.selectByNum(remit_num).getId()).getName() + "님에게 송금하시겠습니까? 1.예 2.아니오");
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
					sdao.update(money, remit_num);
					dao.update(money * -1, account_num);

					System.out.println("이체가 완료되었습니다.");
					// 입금 받을 사람(+//입금)
					// 계좌받기,금액 받기
					// 자동용>입금자의 이름,잔액,아이디
					rs.addRecord(remit_num, money, MembersService.name, sdao.selectByNum(remit_num).getBalance(), 1,
							sdao.selectByNum(remit_num).getId());
					// 입금 하는 사람(-//출금)
					rs.addRecord(account_num, money * (-1), mdao.select(sdao.selectByNum(remit_num).getId()).getName(),
							dao.selectByNum(account_num).getBalance(), 2, MembersService.loginId);
				}
			}
		}
	}

	public void delAccount(String num) {
		System.out.println("===삭제===");
		if (dao.selectByNum(num).getBalance() == 0) {
			dao.delete(num);
			bdao.delete(num);
			System.out.println("삭제가 완료되었습니다");
		} else {
			System.out.println("계좌에 잔액이 없어야 삭제할 수 있습니다.");
		}
	}

	public void getAccountApproval(Scanner sc) {
		Boolean flag = true;
		while (flag) {
			System.out.println("1.승인대기 계좌보기 2.계좌승인하기 3.종료");
			int m = sc.nextInt();
			switch (m) {
			case 1:
				for (Account1 a : dao.SelectAllAccountApproval()) {
					System.out.println(a);
				}
				;
				break;
			case 2:
				System.out.println("승인할 계좌를 입력하시오");
				dao.approval(sc.next());
				break;
			case 3:
				flag = false;
				break;
			}
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
