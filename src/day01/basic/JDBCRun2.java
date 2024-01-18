package day01.basic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCRun2 {
	
public static void main(String[] args) {
	
	/*
	 * 자바랑 오라클이랑 연결하기 위해서는 드라이버 설치가 필요하다.
	 * 우리는 jar파일을 사용해야 한다.
	 * 
	 * JDBC 코딩 절차
	 * 1.드라이버 등록
	 * 2. DBMS연결생성
	 * 3.Statement 객체 생성(쿼리문 실행준비)
	 * 4.SQL 전송 (쿼리문 실행)
	 * 5.결과받기(ResultSet으로받음)
	 * 6.자원해체(close())
	 */
	String url = "jdbc:oracle:thin:@localhost:1521:xe";
	String username = "KH";
	String password = "KH";
	String query = "SELECT * FROM DEPARTMENT";

	try {
		// 1.드라이버 등록
		Class.forName("oracle.jdbc.driver.OracleDriver");
		// 2. DBMS 연결 생성 (sqldeveloper 접속버튼누름)
		Connection conn1 = DriverManager.getConnection(url, username, password);
		// 3.Statement 객체 생성(쿼리문 실행준비)
		Statement stmt1 = conn1.createStatement();
		// 4.SQL 전송 (명령문 실행, 초록색 재생 버튼 누름)
		// 5.결과받기(ResultSet)
		ResultSet rset1 = stmt1.executeQuery(query);
		// 배열에 있는 값을 꺼내 쓸 때 함께 쓰는 것은? 3글자 반복문
		// 후처리 필요 (ResultSet에서 꺼내 써야함)
		while (rset1.next()) {
			System.out.println("부서명 : " + rset1.getString("DEPT_TITLE"));
		}
		// 6.자원 해제
		rset1.close();
		stmt1.close();
		conn1.close();
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
}