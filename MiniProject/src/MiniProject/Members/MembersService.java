package MiniProject.Members;

import java.util.ArrayList;
import java.util.Scanner;

//service
public class MembersService {
	private MembersDao dao;
	public static String loginId;
	public static boolean auth;
	public static String name;

	public MembersService() {
		dao = new MembersDao();
	}

	// 회원가입(추가)
	public void addMembers(Scanner sc) {
		System.out.println("=== 회원가입 ===");
		String id = "";
		do {
			System.out.print("id:");
			id = sc.next();
		} while (dao.select(id) != null);
		System.out.print("pwd:");
		String pwd = sc.next();
		System.out.print("name:");
		String name = sc.next();
		System.out.print("phone:");
		String phone = sc.next();
		Members m = new Members(id, pwd, name, phone, null, null );
		dao.insert(m);
	}

	public boolean login(Scanner sc) {
		System.out.println("=== 로그인 ===");
		System.out.print("id:");
		String id = sc.next();
		System.out.print("pwd:");
		String pwd = sc.next();
		Members m = dao.select(id);
		boolean flag = false;
		if (m == null) {
			System.out.println("없는 아이디");
		} else {
			if (pwd.equals(m.getPwd())) {
				System.out.println("로그인 성공");
				loginId = id;
				auth = dao.select(id).getAuth();
				name = dao.select(id).getName();
				flag = true;
			} else {
				System.out.println("패스워드 실패");
			}
		}
		return flag;
	}
	
	public void logout() {
		System.out.println("=== 로그아웃됨 ===");
		loginId = null;
	}

	// pwd수정
	public void editMembers(Scanner sc) {
		System.out.println("=== pwd 수정 ===");
		
		System.out.print("new pwd:");
		String pwd = sc.next();
		int cnt = dao.update(new Members(loginId, pwd, "", "",null,null));
		if (cnt > 0) {
			System.out.println("수정 완료");
		} else {
			System.out.println("수정이 완료되지 않았음");
		}
	}

	// 탈퇴
	public void delMembers() {
		System.out.println("=== 탈퇴 ===");
		
		int cnt = dao.delete(loginId);
		if (cnt > 0) {
			System.out.println("탈퇴 완료");
			logout();
		} else {
			System.out.println("탈퇴가 완료되지 않았음");
		}
	}

	// 내정보확인
	public void printMembers(Scanner sc) {
		System.out.println("=== 내정보확인 ===");
		
		Members m = dao.select(loginId);
		if (m == null) {
			System.out.println("없는 아이디");
		} else {
			System.out.println(m);
		}
	}

//	// 회원목록
//	public void printAll() {
//		ArrayList<Members> list = dao.selectAll();
//		System.out.println("=== 회원목록 ===");
//		for (Members m : list) {
//			System.out.println(m);
//		}
//	}
}