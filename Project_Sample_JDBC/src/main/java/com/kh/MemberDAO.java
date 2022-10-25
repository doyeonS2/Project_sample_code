package com.kh;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

// DB와 연결하는 부분 만들어주기
// 노션 DATABASE -> JDBC 구현 순서 참고

public class MemberDAO {
	private Connection conn = null; 
	private Statement stmt = null; // 표준 SQL문을 수행하기 위한 Statement 객체 얻기
	private ResultSet rs = null; // Statement의 수행 결과를 여러행으로 받음(다음에 읽을 게 없을때까지)
	// SQL문을 미리 컴파일해서 재사용하므로 Statement 인터페이스보다 훨씬 빠르게 데이터베이스 작업을 수행
	private PreparedStatement pstmt = null; 
	
	// 로그인 관련 메소드 만들기
	
	// 로그인 체크
	public boolean loginCheck(String id, String pwd) { // 아이디와 패스워드 입력받음
		try {
			conn = Common.getConnection();
			stmt = conn.createStatement(); // Statement 객체 얻기
			// 쿼리문 만들기(쿼리 날리기)
			String sql = "SELECT * FROM T_MEMBER WHERE ID = " + "'" + id + "'"; // 아이디 받아냄
			rs = stmt.executeQuery(sql); // 결과가 넘어옴
			
			// 결과 처리
			while(rs.next()) { // 읽을 데이터가 있으면 true
				String sqlId = rs.getString("ID"); // 쿼리 수행 결과에서 ID 값을 가져옴
				String sqlPwd = rs.getString("PWD"); 
				System.out.println("ID : " + sqlId);
				System.out.println("PWD : " + sqlPwd);
				// 조건 처리
				if(id.equals(sqlId) && pwd.equals(sqlPwd)) { // DB에 저장된 정보와 입력받은 아이디, 패스워드가 같은 지 물어보는 것
					Common.close(rs);
					Common.close(stmt);
					Common.close(conn);
					return true; 
				}
			}
			Common.close(rs);
			Common.close(stmt);
			Common.close(conn);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
}
