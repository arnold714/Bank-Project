package MiniProject.Bank;

import java.util.ArrayList;

public class BankService {
	private BankDao dao;
	
	public BankService() {
		dao = new BankDao();
	}
	
	public void addBank(int isAccount) {
		dao.insert(isAccount);
	}
	
	public void delBank(String account_num) {
		dao.delete(account_num);
	}
	
	public void getByNum(String account_num) {
			Bank b = dao.select(account_num);
			System.out.println(b);
	}
}
