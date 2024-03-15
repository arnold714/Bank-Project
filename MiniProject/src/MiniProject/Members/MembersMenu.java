package MiniProject.Members;

import java.util.Scanner;

import Accounting.AccountService;

public class MembersMenu {
	private MembersService mservice;
	private AccountService aservice;
	
	public MembersMenu() {
		mservice = new MembersService();
		aservice = new AccountService();
	}
	
	public void run(Scanner sc) {
		while (true) {
			System.out.println("종료는 0");
			int m = sc.nextInt();
			if (m == 0) {
				break;
			}
			if (MembersService.loginId == null) {
				runMemLogout(sc);
			} else {
				runMemLogin(sc);
			}
		}
	}

	// 로그인 안했을때 메뉴
	public void runMemLogout(Scanner sc) {
		boolean flag = true;
		while(flag) {
			System.out.println("1.로그인 2.회원가입 3.종료");
			int m = sc.nextInt();
			switch(m) {
			case 1:
				flag = !mservice.login(sc);
				break;
			case 2:
				mservice.addMembers(sc);
				break;
			case 3:
				flag = false;
				break;
			}
		}
	}

	// 로그인 했을때 메뉴
	public void runMemLogin(Scanner sc) {
		boolean flag = true;
		while(flag) {
			System.out.println("1.내정보확인 2.내정보수정 3.로그아웃 4.탈퇴 5.나의 계좌 6.관리자메뉴 7.종료");
			int m = sc.nextInt();
			switch(m) {
			case 1:
				mservice.printMembers(sc);
				break;
			case 2:
				mservice.editMembers(sc);
				break;
			case 3:
				mservice.logout();
				return;
			case 4:
				mservice.delMembers();
				return;
			case 5:
				runAccount(sc);
				break;
			case 6:
				if(MembersService.auth==true) {
					runManager(sc);
				}else {
					System.out.println("관리자가 아닙니다.");
				}
				break;
			case 7:
				flag = false;
				break;
			}
		}
	}
	
	public void runAccount(Scanner sc) {
		boolean flag = true;
		while(flag) {
			System.out.println("1.계좌선택 2.계좌추가 3.전체계좌 4.종료");
			int m = sc.nextInt();
			switch(m) {
			case 1:
				aservice.getByNum(sc);
				break;
			case 2:
				aservice.addAccount(MembersService.loginId);
				break;
			case 3:
				aservice.getAll();
				break;
			case 4:
				flag = false;
				break;
			}
		}
	}
	
	public void runManager(Scanner sc) {
		boolean flag = true;
		while(flag) {
			System.out.println("1.전회원 보기 2.전계좌 보기 3.모든거래내역 보기 4.승인업무처리 5.종료");
			int m = sc.nextInt();
			switch(m) {
			case 1:
				mservice.printAll();
				break;
			case 2:
				aservice.getAll();
				break;
			case 3:
				//거래내역보기
				break;
			case 4:
				runApproval(sc);
				break;
			case 5:
				flag = false;
				break;
			}
		}
	}
	
	public void runApproval(Scanner sc) {
		boolean flag = true;
		while(flag) {
			System.out.println("1.계좌승인업무 2.관리자승인업무 3.종료");
			int m = sc.nextInt();
			switch(m) {
			case 1:
				aservice.getAccountApproval(sc);
				break;
			case 2:
				mservice.membersApproval(sc);
				break;
			case 3:
				flag = false;
				break;
			}
		}
	}
}
