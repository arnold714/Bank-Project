package MiniProject.Record;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import MiniProject.DBConnect;

public class RecordDao {
	private DBConnect db;

	public RecordDao() {
		db = DBConnect.getInstance();
	}

	// 거래 내역 추가
	public void insert(Record r) {
		Connection conn = db.conn();
		//번호, 계좌번호, 금액, 이름, 잔액, 날짜, 입출금
		String sql = "insert into Record values(seq.NEXTVAL,?,?,?,?,sysdate,?,?)";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, r.getAccount_num());
			pstmt.setInt(2, r.getMoney());
			pstmt.setString(3, r.getName());
			pstmt.setInt(4, r.getBalnace());
			pstmt.setInt(5, r.getIsDeposit());
			pstmt.setString(6, r.getId());
			int cnt = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}


//날짜별로 조회
	public ArrayList<Record> selectByDate(String newDate,String newDate1, String account_num) {

		Connection conn = db.conn();
		String sql = "select * from Record where to_char(newDate, 'yy/mm/dd') between ? and ?  and account_num =? order by num";
		ArrayList<Record> list = new ArrayList<Record>();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, newDate);
			pstmt.setString(2, newDate1);
			pstmt.setString(3, account_num);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				list.add(new Record(rs.getInt(1),rs.getString(2),rs.getInt(3),rs.getString(4),rs.getInt(5),rs.getDate(6),rs.getInt(7),rs.getString(8)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		return list;
	}

//입출금 조회
	public ArrayList<Record> selectByDeposit(int type,String account_num) {//입금:1, 출금:2
		Connection conn = db.conn();
		String sql = "select * from record where isDeposit = ? and account_num = ? order by num"; //입금
		ArrayList<Record> list = new ArrayList<Record>();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, type);
			pstmt.setString(2, account_num);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				list.add(new Record(rs.getInt(1),rs.getString(2),rs.getInt(3),rs.getString(4),rs.getInt(5),rs.getDate(6),rs.getInt(7),rs.getString(8)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO: handle exception
			}
	} 
		return list;
	}

// 거래 내역 조회	
	public ArrayList<Record> selectAll(String account_num) {
		Connection conn = db.conn();
		String sql = "select * from record where account_num =? order by num";
		ArrayList<Record> list = new ArrayList<Record>();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,account_num);
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				list.add(new Record(rs.getInt(1),rs.getString(2),rs.getInt(3),rs.getString(4),rs.getInt(5),rs.getDate(6),rs.getInt(7),rs.getString(8)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
}
	

	

