package day03.pstmt.member.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import day03.pstmt.member.model.vo.Member;
import day04.pstmt.member.common.JDBCTemplate;

public class MemberDAO {

	private JDBCTemplate jdbcTemplate;
	
	public MemberDAO() {
		jdbcTemplate = JDBCTemplate.getInstance();
		
	}
	
	
	
	final String DRIVER_NAME = "oracle.jdbc.driver.OracleDriver";
	final String URL = "jdbc:oracle:thin:@localhost:1521:xe"; // 오라클 호스트 이름 : 포트 : SID
	final String USERNAME = "STUDENT"; // 오라클 계정명
	final String PASSWORD = "STUDENT"; // 계정 비밀번호
	// JDBC를 이용하여
	// Oracle DB에 접속하는 클래스
	// JDBC 코딩 절차

	/*
	 * 1. 드라이버 등록 2. DB 연결 생성 3. 쿼리문 실행 준비 4. 쿼리문 실행 및 5. 결과 받기 6. 자원해제(close())
	 */
	public void updateMember(Member member) {
		String query = "UPDATE MEMBER_TBL SET MEMBER_PWD = '" + member.getMemberPw() + "'" + ", EMAIL = '"
				+ member.getEmail() + "', PHONE = '" + member.getPhone() + "', ADDRESS = '" + member.getAddress()
				+ "', HOBBY = '" + member.getHobby() + "' WHERE MEMBER_ID = '" + member.getMemberId() + "'";
		JDBCTemplate jdbcTemplate = new JDBCTemplate();

		try {
			Class.forName(DRIVER_NAME);
			Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			Statement stmt = conn.createStatement();
			int result = stmt.executeUpdate(query);
			if (result > 0) {
				// 성공 후 커밋
			} else {
				// 실패면 롤백
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void deleteMember(String memberId) {
		String query = "DELETE FROM MEMBER_TBL WHERE MEMBER_ID = '" + memberId + "'";
		JDBCTemplate jdbcTemplate = new JDBCTemplate();

		try {
			Class.forName(DRIVER_NAME);
			Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			Statement stmt = conn.createStatement();
			int result = stmt.executeUpdate(query); // DML의 경우 INT로 받음
			if (result > 0) {
				// 성공 후 커밋
			} else {
				// 실패면 롤백
			}
			stmt.close();
			conn.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Member selectOneById(String memberId) {
		String query = "SELECT * FROM MEMBER_TBL WHERE MEMBER_ID = '" + memberId + "'";
		Member member = null;
		JDBCTemplate jdbcTemplate = new JDBCTemplate();

		try {
			Class.forName(DRIVER_NAME);
			Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery(query);
			if (rset.next()) {
				member = new Member();
				member.setMemberId(rset.getString("MEMBER_ID"));
				member.setMemberPw(rset.getString("MEMBER_PWD"));
				member.setMemberName(rset.getString("MEMBER_NAME"));
				member.setGender(rset.getString("GENDER").charAt(0)); // GENDER는 한글자라서 문자 처리
				member.setAge(rset.getInt("AGE"));
				member.setEmail(rset.getString("EMAIL"));
				member.setPhone(rset.getString("PHONE"));
				member.setAddress(rset.getString("ADDRESS"));
				member.setHobby(rset.getString("HOBBY"));
				member.setEnrollDate(rset.getDate("ENROLL_DATE")); // getDate 사용
			}
			rset.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return member;
	}

	public void insertMember(Member member) {
		String query = "INSERT INTO MEMBER_TBL" + " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, sysdate)";
		JDBCTemplate jdbcTemplate = new JDBCTemplate();

		try {
			Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
//			Statement stmt = conn.createStatement();
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, member.getMemberId());
			pstmt.setString(2, member.getMemberPw());
			pstmt.setString(3, member.getMemberName());
			pstmt.setString(4, String.valueOf(getGender()));
			pstmt.setInt(5, member.getAge());
			pstmt.setString(6, member.getEmail());
			pstmt.setString(7, member.getPhone());
			pstmt.setString(8, member.getAddress());
			pstmt.setString(9, member.getHobby());
			pstmt.setString(1, member.getMemberId());
//				ResultSet rset = stmt.executeQuery(query);
			int result = pstmt.executeUpdate(query); // DML의 경우 호출하는 메소드
			if (result > 0) { // autocommit이 되기 때문에 안해도 됨
				// insert 성공
			} else {
				// insert 실패
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private char[] getGender() {
		// TODO Auto-generated method stub
		return null;
	}

	public Member selectLoginInfo(Member mOne) {
		
//		String query = "SELECT * FROM MEMBER_TBL WHERE MEMBER_ID = '" + mOne.getMemberId() + "' AND MEMBER_PWD = '"
//		+ mOne.getMemberPw() + "'";
		String query = "SELECT * FROM MEMBER_TBL WHERE MEMBER_ID = ?"
					    +"AND MEMBER_PWD = ?"; ///////////////////////////// 바뀌는 부분 1번째
		Member member = null;
		Connection conn = null;
//		Statement stmt = null;
		PreparedStatement pstmt = null; /////////////////////////////// 바뀌는 부분 2번째
		ResultSet rset = null;
		//싱글톤 미적용
		JDBCTemplate jdbcTemplate = new JDBCTemplate();
		try {
			Class.forName(DRIVER_NAME);
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			//////////////////////// 바뀌는 부분2
			pstmt = conn.prepareStatement(query); // 쿼리문을 미리 컴파일
			//////////////////////// 바뀌는 부분3
			pstmt.setString(1, mOne.getMemberId()); // ?(위치홀더)에 값을을 넣는 코드
			pstmt.setString(2, mOne.getMemberPw()); // 시작은 1로 하고 마지막 수는 물음표의 갯수와 같다.(물음표 = 위치홀더)
			//////////////////////// 바뀌는 부분4
			rset = pstmt.executeQuery(); // 쿼리문을 미리 컴파일하고 위치홀더 값을 셋팅하고 쿼리문 실행 및 결과 받기
			
//			stmt = conn.createStatement();
//			rset = stmt.executeQuery(query);
			if (rset.next()) {
				member = new Member();
				member.setMemberId(rset.getString("MEMBER_ID"));
				member.setMemberPw(rset.getString("MEMBER_PWD"));
				member.setMemberName(rset.getString("MEMBER_NAME"));
				member.setGender(rset.getString("GENDER").charAt(0)); // GENDER는 한글자라서 문자처리
				member.setAge(rset.getInt("AGE"));	// AGE는 getINT로 사용
				member.setEmail(rset.getString("EMAIL"));
				member.setPhone(rset.getString("PHONE"));
				member.setAddress(rset.getString("ADDRESS"));
				member.setHobby(rset.getString("HOBBY"));
				member.setEnrollDate(rset.getDate("ENROLL_DATE")); // getDATE 사용
			}
			rset.close();
//			stmt.close();
			pstmt.close();
			conn.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				rset.close();
//				stmt.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return member;
	}


	// 클래스 안에 코드 못 씀
	// 이 메소드 안에 코드를 적어야 함.
	public List<Member> selectAll() {

		String query = "SELECT * FROM MEMBER_TBL";
		List<Member> mList = null;
		// 메소드 안에 코드를 씀
		JDBCTemplate jdbcTemplate = new JDBCTemplate();

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			try {
				Connection conn = (Connection) DriverManager.getConnection(URL, USERNAME, PASSWORD);
				Statement stmt = conn.createStatement();
				ResultSet rset = stmt.executeQuery(query); // 초록색 재생버튼 누름
				// rset 전부다 담겨있기 때문에 한 행씩 꺼내서 출력해줘야 함
				mList = new ArrayList<Member>();
				while (rset.next()) {
					Member member = new Member();
					String memberId = rset.getString("MEMBER_ID");
					String memberPwd = rset.getString("MEMBER_PWD");
					String memberName = rset.getString("MEMBER_NAME");

					member.setMemberId(memberId);
					member.setMemberPw(memberPwd);
					member.setMemberName(memberName);
					member.setGender(rset.getString("GENDER").charAt(0)); // GENDER는 한글자라서 문자 처리
					member.setAge(rset.getInt("AGE"));
					member.setEmail(rset.getString("EMAIL"));
					member.setPhone(rset.getString("PHONE"));
					member.setAddress(rset.getString("ADDRESS"));
					member.setHobby(rset.getString("HOBBY"));
					member.setEnrollDate(rset.getDate("ENROLL_DATE")); // getDate 사용
					mList.add(member); // 누락주의! 하나의 행 데이터를 List에 반복적으로 저장
					// 후처리 : SELECT한 결과값 자바영역인 List에다가 옮기는 작업
					System.out.println("이름 : " + rset.getString("MEMBER_NAME"));
				}
				rset.close();
				stmt.close();
				rset.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mList;
	}
	
	private Connection getConnection() throws Exception {
		Class.forName(DRIVER_NAME);
		Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		return conn;
	}
	
	
	
	
	
	
	
	
}
