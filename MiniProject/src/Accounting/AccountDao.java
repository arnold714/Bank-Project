package Accounting;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import MiniProject.DBConnect;
import MiniProject.Members.Members;

public class AccountDao {
	private DBConnect db;

	public AccountDao() {
		db = DBConnect.getInstance();
	}

	// insert
	// 계좌 추가(상품 가입)
	public void insert(Account1 a) {
		Connection conn = db.conn();
		// account_num, id, balance, signdate, approval
		String sql = "insert into account values(?,?,0,default,0)";
		PreparedStatement pstmt;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, a.getAccount_num());
			pstmt.setString(2, a.getId());

			int cnt = pstmt.executeUpdate();
			System.out.println(cnt + "줄 추가됨");
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
	public Account1 selectByNum(int num) {
		Connection conn = db.conn();
		String sql = "select * from account where account_num=?";
		Account1 account1;
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				account1 = new Account1();
				account1.setAccount_num(rs.getInt(1));
				account1.setId(rs.getString(2));
				account1.setBalance(rs.getInt(3));
				account1.setDate(rs.getDate(4));
				if (rs.getInt(5) == 1) {
					account1.setAllow(true);
				} else {
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
		return null;
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
				account1.setAccount_num(rs.getInt(1));
				account1.setId(rs.getString(2));
				account1.setBalance(rs.getInt(3));
				account1.setDate(rs.getDate(4));
				if (rs.getInt(5) == 1) {
					account1.setAllow(true);
				} else {
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
	public void update(Account a) {
		Connection conn = db.conn();
		String sql = "update account set balance = balance + ? where num=?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, a.getBalance());
			int cnt = pstmt.executeUpdate();
			System.out.println(cnt + "줄 수정됨");

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
	public void delete(int num) {
		// 계좌번호에 맞는거 탈퇴하기
		Connection conn = db.conn();
		String sql = "delete account where num=?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);

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

}