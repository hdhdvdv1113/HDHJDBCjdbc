package day01.basic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCRun {

	public static void main(String[] args) {
		/*
		//자바랑 오라클이랑 연결하기위해서 드라이버 설치가 필요하다
		// 우리는 jar파일을사용해야한다
		/*
		 *  JDBC 코딩 절차
		 *  1. 드라이버 등록
		 *  2. DBMS 연결 생성
		 *  3. Statement 객체 생성(쿼리문 실행 준비)
		 *  4. SQL 전송(쿼리문 실행)
		 *  5. 결과 받기 (ResultSet으로 받음)
		 *  6. 자원해체(close())
		 *  
		 * 
		 */
		String url = "jdbc:oracle:thin:@localhost:1521:xe"; // 오라클 연결할주소
		String username = "KH";
		String password = "KH";
		String query = "SELECT * FROM DEPARTMENT";
		try {
			// 1. JDBC 드라이버 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// 2. Connection 객체 생성 DBMS 연결 생성 (sqldeveloper 접속 버튼 누름)
			Connection conn = DriverManager.getConnection(url, username, password);
			// 3. Statement 객체 생성 (쿼리문 실행 준비)
			Statement stmt = conn.createStatement();
			// 4. SQL 전송 (명령문 실행, 초록색 재생 버튼 누름)
			// 5. 결과받기(ResultSet)
			ResultSet rset = stmt.executeQuery(query);
			// 배열에 있는 값을 꺼내 쓸 때 함께 쓰는 것은? 3글자 반복문
			// 후처리 필요 (ResultSet에서 꺼내 써야함)
			while (rset.next()) {
				// System.out.println("직업명 : "+ rset.getString("EMP_NAME"));
				// System.out.println("부서명 : "+ rset.getString("DEPT_TITLE"));
				// System.out.println("부서명 : "+ rset.getString("DEPT_TITLE"));
				// System.out.println("부서명 : "+ rset.getString("DEPT_TITLE"));
				// System.out.println("부서명 : "+ rset.getString("DEPT_TITLE"));
				// System.out.println("부서명 : "+ rset.getString("DEPT_TITLE"));
				// System.out.println("부서명 : "+ rset.getString("DEPT_TITLE"));
				// System.out.println("부서명 : "+ rset.getString("DEPT_TITLE"));
				// =========> 14개의 칼럼을 다보려면 이렇게 14개써야한다.
				// 다음시간에 이 이런노가다를 안할수있는기술을씀

				System.out.println("부서코드 : " + rset.getString("DEPT_ID"));
				
			}
			// 6.자원 해제
			rset.close();
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

}