package MiniProject.Bank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import MiniProject.DBConnect;
import MiniProject.Members.Members;

public class BankDao {
	private DBConnect db;

	public BankDao() {
		db = DBConnect.getInstance();
	}
	public String insert(int isAccount) {
		Connection conn = db.conn();
		String sql = "insert into Bank values(seq_account.nextval, ?)";
		String sql2 = "select seq_account.currval from bank";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, isAccount);
			pstmt.executeUpdate();
			pstmt = conn.prepareStatement(sql2);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getString(1);
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
	
	public String current_num() {
		Connection conn = db.conn();
		String sql = "select seq_account.currval from bank";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getString(1);
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
	
	
	public Bank select(String account_num) {
		Connection conn = db.conn();
		String sql = "select * from bank where account_num=?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, account_num);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				return new Bank(rs.getString(1), rs.getInt(2));
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


	public ArrayList<Bank> selectAll() {
		Connection conn = db.conn();
		String sql = "select * from bank order by isAccount";
		ArrayList<Bank> list = new ArrayList<Bank>();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {// rs.next():읽을 줄로 이동을 해서 읽을 값이 있으면 true, 없으면 false
				list.add(new Bank(rs.getString(1), rs.getInt(2)));
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

//	public void update(Board b) {
//		Connection conn = db.conn();
//		String sql = "update board set title=?, content=? where num=?";
//		try {
//			PreparedStatement pstmt = conn.prepareStatement(sql);
//			pstmt.setString(1, b.getTitle());
//			pstmt.setString(2, b.getContent());
//			pstmt.setInt(3, b.getNum());
//
//			int cnt = pstmt.executeUpdate();
//			System.out.println(cnt + " 줄 수정됨");
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} finally {
//			try {
//				conn.close();
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//	}

	public void delete(String account_num) {
		Connection conn = db.conn();
		String sql = "delete bank where account_num=?";
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
}
