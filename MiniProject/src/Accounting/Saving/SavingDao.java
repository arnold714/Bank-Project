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
	public void insert(Saving s) {
		Connection conn = db.conn();
		//Account_num(계좌번호),String id(계좌주),int balance(잔액),Date date(가입일),
		//Date expDate(만기일),double doublePercent(이자율)
		String sql = "insert into Saving values(seq.nextvalue,?,?,sysdate,0,?)";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, s.getId());
			pstmt.setInt(2, s.getBalance());
			pstmt.setDate(3, s.getExpDate());
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
	public ArrayList<Saving> SelectAll(int id) {
		Connection conn = db.conn();
		String sql = "select * from Saving where id =?";
		ArrayList<Saving> list = new ArrayList<Saving>();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				list.add(new Saving(rs.getInt(1), rs.getString(2), rs.getInt(3),rs.getDate(4), rs.getDate(5), rs.getDouble(5)));
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
	public Saving selectByNum(int num) {
		Connection conn = db.conn();
		String sql = "select * from Saving where Account_num=?";
		Saving s = null;
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				s = new Saving();
				s.setAccount_num(rs.getInt(1));
				s.setId(rs.getString(2));
				s.setBalance(rs.getInt(3));
				s.setDate(rs.getDate(4));
				s.setExpDate(rs.getDate(5));
				s.setDoublepercent(rs.getDouble(6));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {conn.close();} 
			catch (SQLException e) {e.printStackTrace();}
		}
		return s;
	}
	//입금 추가
	public int update(Saving s) {
		Connection conn = db.conn();
		String sql = "update saving set blance=blance+? where account_num=?";
		int cnt = 0;
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, s.getBalance());
			pstmt.setInt(2, s.getAccount_num());
			cnt = pstmt.executeUpdate();
			System.out.println(cnt + " 줄이 수정됨");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {conn.close();} 
			catch (SQLException e) {e.printStackTrace();}
		}
		return cnt;
	}

	public void delete(int num) {
		Connection conn = db.conn();
		String sql = "delete from saving where account_num=?";
		int cnt = 0;
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			cnt = pstmt.executeUpdate();
			System.out.println(num+ "계좌 탈회");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {conn.close();} 
			catch (SQLException e) {e.printStackTrace();}
		}
	}
	

}
