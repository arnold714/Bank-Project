package Accounting.Saving;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Accounting.Account;
import MiniProject.DBConnect;
import MiniProject.Members.Members;

public class SavingDao{
	private DBConnect db;
	
	public SavingDao() {
		db = DBConnect.getInstance();
	}
	
	//계좌 생성
	public void insert(Saving s,int num) {
		Connection conn = db.conn();
		//Account_num(계좌번호),String id(계좌주),int balance(잔액),Date date(가입일),
		//Date expDate(만기일),double doublePercent(이자율)
		String sql = "insert into Saving values(seq_account.nextval,?,?,sysdate,add_months(sysdate,?),?,0)";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, s.getId());
			pstmt.setInt(2, s.getBalance());
			pstmt.setInt(3, num);
			pstmt.setDouble(4, s.getDoublepercent());
			int cnt = pstmt.executeUpdate();
			System.out.println(s.getBalance() + "개설 되었습니다.");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {conn.close();}
			catch (SQLException e) {e.printStackTrace();}
		}
	}
	//내 적금 계좌 전체 조회
	public ArrayList<Saving> SelectAll(String id) {
		Connection conn = db.conn();
		String sql = "select * from Saving where id =?";
		ArrayList<Saving> list = new ArrayList<Saving>();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				list.add(new Saving(rs.getString(1), rs.getString(2), rs.getInt(3),rs.getDate(4), rs.getDate(5), rs.getDouble(6),rs.getInt(7)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {conn.close();} 
			catch (SQLException e) {e.printStackTrace();}
		}
		return list;
	}
	//계좌 번호로 조회
	public Saving selectByNum(String num) {
		Connection conn = db.conn();
		String sql = "select * from Saving where Account_num=?";
		Saving s = null;
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, num);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				s = new Saving();
				s.setAccount_num(rs.getString(1));
				s.setId(rs.getString(2));
				s.setBalance(rs.getInt(3));
				s.setDate(rs.getDate(4));
				s.setExpDate(rs.getDate(5));
				s.setDoublepercent(rs.getDouble(6));
				s.setExpiry(rs.getInt(7));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {conn.close();} 
			catch (SQLException e) {e.printStackTrace();}
		}
		return s;
	}
	//만기일 조회
	//만기 전에는 1, 만기 당일 or 만기 날에는 음수 반환
	public int getDate(String num) {
		Connection conn = db.conn();
		String sql = "select round(expdate - sysdate,0) from Saving where account_num=?;";
		int x = 1;
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, num);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {conn.close();} 
			catch (SQLException e) {e.printStackTrace();}
		}
		return x;
	}
	//입금 추가
	public void update(int money, String num) {
		Connection conn = db.conn();
		String sql = "update saving set balance=balance+? where account_num=?";
		int cnt = 0;
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, money);
			pstmt.setString(2, num);
			cnt = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {conn.close();} 
			catch (SQLException e) {e.printStackTrace();}
		}
	}
	//만기 이자 추가
	public void updateExp(Saving s) {
		Connection conn = db.conn();
		String sql = "update saving set balance=balance+balance*Round(percent*(MONTHS_BETWEEN(expDate , newdate))/12,2), expiry=1 where account_num=?";
		int cnt = 0;
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, s.getAccount_num());
			cnt = pstmt.executeUpdate();
		} catch (SQLException e) {e.printStackTrace();
		} finally {
			try {conn.close();} 
			catch (SQLException e) {e.printStackTrace();}
		}
	}
	//계좌 삭제
	public void delete(String num) {
		Connection conn = db.conn();
		String sql = "delete from saving where account_num=?";
		int cnt = 0;
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, num);
			cnt = pstmt.executeUpdate();
			System.out.println(num+ "계좌 탈회가 완료되었습니다.");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {conn.close();} 
			catch (SQLException e) {e.printStackTrace();}
		}
	}
}
