package Accounting;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import MiniProject.DBConnect;
import MiniProject.Members.MembersService;

public class AccountDao {
	private DBConnect db;

	public AccountDao() {
		db = DBConnect.getInstance();
	}

	// insert
	// 계좌 추가(상품 가입)
	public void insert(String account_num) {
		Connection conn = db.conn();
		// account_num, id, balance, signdate, approval
		String sql = "insert into account values(?,?,0,default,0)";
		PreparedStatement pstmt;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, account_num);
			pstmt.setString(2, MembersService.loginId);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// 계좌번호로 조회
	public Account1 selectByNum(String num) {
		Connection conn = db.conn();
		String sql = "select * from account where account_num=?";
		Account1 account1 = null;
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, num);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
					account1 = new Account1();
					account1.setAccount_num(rs.getString(1));
					account1.setId(rs.getString(2));
					account1.setBalance(rs.getInt(3));
					account1.setDate(rs.getDate(4));
					if(rs.getInt(5)==1) {
						account1.setAllow(true);
					}else {
						account1.setAllow(false);
					}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return account1;
	}

	// select
	// 전체 계좌 조회
	public ArrayList<Account1> SelectAll() {
		Connection conn = db.conn();
		ArrayList<Account1> list = new ArrayList<Account1>();
		String sql = "select * from account";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Account1 account1 = new Account1();
				account1.setAccount_num(rs.getString(1));
				account1.setId(rs.getString(2));
				account1.setBalance(rs.getInt(3));
				account1.setDate(rs.getDate(4));
				if(rs.getInt(5)==1) {
					account1.setAllow(true);
				}else {
					account1.setAllow(false);
				}
				list.add(account1);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}
	public ArrayList<Account1> SelectById(String id) {
		Connection conn = db.conn();
		ArrayList<Account1> list = new ArrayList<Account1>();
		String sql = "select * from account where id = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Account1 account1 = new Account1();
				account1.setAccount_num(rs.getString(1));
				account1.setId(rs.getString(2));
				account1.setBalance(rs.getInt(3));
				account1.setDate(rs.getDate(4));
				if(rs.getInt(5)==1) {
					account1.setAllow(true);
				}else {
					account1.setAllow(false);
				}
				list.add(account1);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}

	// update
	// 입금(입금 금액 추가)
	public void update(int money, String num) {
		Connection conn = db.conn();
		String sql = "update account set balance = balance + ? where account_num=?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, money);
			pstmt.setString(2, num);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// delete
	// 계좌 삭제(탈회)(금액이 0일 경우)
	public void delete(String account_num) {
		// 계좌번호에 맞는거 탈퇴하기
		Connection conn = db.conn();
		String sql = "delete account where account_num=?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, account_num);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void approval(String account_num) {//승인
		// 계좌번호에 맞는거 탈퇴하기
		Connection conn = db.conn();
		String sql = "update account set approval = 1 where account_num=?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, account_num);
			pstmt.executeUpdate();
			System.out.println("승인되었습니다.");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public ArrayList<Account1> SelectAllAccountApproval() {
		Connection conn = db.conn();
		ArrayList<Account1> list = new ArrayList<Account1>();
		String sql = "select * from account where approval = 0";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Account1 account1 = new Account1();
				account1.setAccount_num(rs.getString(1));
				account1.setId(rs.getString(2));
				account1.setBalance(rs.getInt(3));
				account1.setDate(rs.getDate(4));
				if(rs.getInt(5)==1) {
					account1.setAllow(true);
				}else {
					account1.setAllow(false);
				}
				list.add(account1);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}

}