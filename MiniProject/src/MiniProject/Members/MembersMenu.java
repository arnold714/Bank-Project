package MiniProject.Members;

import java.util.Scanner;

public class MembersMenu {
	private MembersService mservice;
	
	public MembersMenu() {
		mservice = new MembersService();
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
			System.out.println("1.내정보확인 2.내정보수정 3.로그아웃 4.탈퇴 5.게시판 6.종료");
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
				//
				break;
			case 6:
				flag = false;
				break;
			}
		}
	}
}
