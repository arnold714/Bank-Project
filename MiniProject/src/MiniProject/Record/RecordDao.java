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
		String sql = "insert into Record values(seq.nextvalue,?,?,?,sysdate)";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, r.getNum());
			pstmt.setInt(2, r.getAccount_num());
			pstmt.setInt(3, r.getMoney());
			pstmt.setString(4, r.getName());
			pstmt.setDate(5, r.getNewDate());
			pstmt.executeUpdate();
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
	public ArrayList<Record> selectByDate(Date date) {

	}

//입금만 조회
	public ArrayList<Record> selectByDeposit() {

	}

//출금만 조회
	public ArrayList<Record> selectByWithdraw() {
		
	}

// 거래 내역 조회	
	public ArrayList<Record> selectAll(int account_num) {
		Connection conn = db.conn();
		String sql = "select * from Saving where ‎account_num = ?";
		ArrayList<Record> list = new ArrayList<Record>();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, account_num);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				list.add(new Record(rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getString(4),rs.getDate(5)));
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

	

	


