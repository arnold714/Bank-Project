package MiniProject.Members;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import MiniProject.DBConnect;

//dao: db 작업
public class MembersDao {
	private DBConnect db;

	public MembersDao() {
		db = DBConnect.getInstance();
	}

	public void insert(Members m) {
		Connection conn = db.conn();
		// 실행할 쿼리문 작성
		String sql = "insert into Members values(?,?,?,?,0,default)";
		// 자바에서 sql을 실행할 수 있는 PreparedStatement 생성
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			// pstmt.set..(?순서, 값): ? 들어갈 값 설정
			// id, pwd, name, phone, auth, signdate
			pstmt.setString(1, m.getId());
			pstmt.setString(2, m.getPwd());
			pstmt.setString(3, m.getName());
			pstmt.setString(4, m.getPhone());
			
			
			// sql 실행
			int cnt = pstmt.executeUpdate();// insert, update, delete문장 실행.
			// pstmt.executeQuery()//select문 실행 => ResultSet 반환
			System.out.println(cnt + " 줄 추가됨");
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

	public int update(Members m) {// id로 찾아서 pwd수정
		Connection conn = db.conn();
		String sql = "update Members set pwd=? where id=?";
		int cnt = 0;
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, m.getPwd());
			pstmt.setString(2, m.getId());
			cnt = pstmt.executeUpdate();// 처리된 줄 수 반환
			System.out.println(cnt + " 줄이 수정됨");
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
		return cnt;
	}

	public int delete(String id) {
		Connection conn = db.conn();
		String sql = "delete from Members where id=?";
		int cnt = 0;
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			cnt = pstmt.executeUpdate();
			System.out.println(cnt + " 줄이 삭제됨");
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
		return cnt;
	}

	// pk기준 검색 메서드는 반환타입은 vo, 파라메터는 pk 컬럼. Members은 String id를 파람으로 받음
	public Members select(String id) {
		Connection conn = db.conn();
		String sql = "select * from Members where id=?";
		Members Members = null;
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			// select는 executeQuery()로 실행
			// executeQuery()는 select를 실행하고 검색 결과를 ResultSet에 담아서 반환
			ResultSet rs = pstmt.executeQuery();
			// rs.next(): 읽을 다음 줄로 이동. 읽을 것이 있으면 true, 없으면 false반환
			if (rs.next()) {
				Members = new Members();
				Members.setId(rs.getString(1));// 1번 컬럼의 문자열 값을 읽음
				Members.setPwd(rs.getString(2));// 2번 컬럼의 문자열 값을 읽음
				Members.setName(rs.getString(3));// 3번 컬럼의 문자열 값을 읽음
				Members.setPhone(rs.getString(4));// 4번 컬럼의 문자열 값을 읽음
				if(rs.getInt(5)==1) {
					Members.setAuth(true);
				}else {
					Members.setAuth(false);// 5번 컬럼의 문자열 값을 읽음
				}
				Members.setSigndate(rs.getDate(6));// 6번 컬럼의 문자열 값을 읽음

				// Members = new
				// Members(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4));
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
		return Members;
	}

	public ArrayList<Members> selectAll() {
		// db 연결
		Connection conn = db.conn();

		// sql문 작성
		String sql = "select * from Members";

		// ArrayList 생성
		ArrayList<Members> list = new ArrayList<Members>();

		try {
			// PreparedStatement 객체 생성
			PreparedStatement pstmt = conn.prepareStatement(sql);

			// sql 실행
			ResultSet rs = pstmt.executeQuery();

			// ResultSet 읽을 줄로 이동
			while (rs.next()) {
				Members Members = new Members();
				Members.setId(rs.getString(1));// 1번 컬럼의 문자열 값을 읽음
				Members.setPwd(rs.getString(2));// 2번 컬럼의 문자열 값을 읽음
				Members.setName(rs.getString(3));// 3번 컬럼의 문자열 값을 읽음
				Members.setPhone(rs.getString(4));// 4번 컬럼의 문자열 값을 읽음
				if(rs.getInt(5)==1) {
					Members.setAuth(true);
				}else {
					Members.setAuth(false);// 5번 컬럼의 문자열 값을 읽음
				}
				Members.setSigndate(rs.getDate(6));// 6번 컬럼의 문자열 값을 읽음
				list.add(Members);
				// list.add(new
				// Members(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4)));
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