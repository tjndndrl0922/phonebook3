	package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.PersonVo;

public class PhoneDao {

	// 필드
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String id = "phonedb";
	private String pw = "phonedb";

	// 생성자
	// 메소드g.s
	// 메소드일반
	// DB접속
	private void getConnection() {

		try {
			// JDBC 드라이버 (Oracle) 로딩
			Class.forName(driver);

			// Connection 얻어오기
			conn = DriverManager.getConnection(url, id, pw);
		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}

	// 자원 정리
	private void close() {
		try {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}
	
	//사람 1명 가져오기
	public PersonVo getPerson(int personId) {
		getConnection();
		PersonVo personVo = null;
		try {
			// SQL문 준비/ 바인딩 / 실행
			/*
			SELECT person_id,
			        name,
			        hp,
			        company
			FROM person
			where person_id = 6;
			 */
			String query = "";
			query += " SELECT person_id, ";
			query += "		  name,  ";
			query += "  	  hp, ";
			query += " 		  company ";
			query += " FROM person ";
			query += " where person_id = ? ";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, personId);
			
			rs = pstmt.executeQuery();
			
			
			//결과처리
			while(rs.next()) {
				int personID = rs.getInt("person_id");
				String name = rs.getString("name");
				String hp = rs.getString("hp");
				String company = rs.getString("company");
				
				personVo = new PersonVo(personID, name, hp, company);
			}
		}catch (Exception e) {
			System.out.println("error:" + e);
		}
		
		
		
		close();
		return personVo;
		
	}
	
	//phone 검색
	public List<PersonVo> phonesearchList(String search) {
		getConnection();
		
		List<PersonVo> phonesearchList = new ArrayList<PersonVo>();
		
		try {
			// SQL문 준비/ 바인딩 / 실행
			String query = "";
			query += " SELECT person_id, " ;			
			query += "  	  name, ";
			query += " 		  hp, ";
			query += " 	  	  company ";
			query += " FROM person ";
			query += " where name like ? ";
			query += " or hp like  ? " ;
			query += " or company like  ? " ;
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, '%' + search + '%');
			pstmt.setString(2, '%' + search + '%');
			pstmt.setString(3, '%' + search + '%');
			rs = pstmt.executeQuery();
			
			// 결과처리
			while(rs.next()) {
				int personId = rs.getInt("person_id");
				String name = rs.getString("name");
				String hp = rs.getString("hp");
				String company = rs.getString("company");
				PersonVo vo = new PersonVo(personId, name, hp, company);
				phonesearchList.add(vo);
				
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		// 자원정리
		close();

		
		return phonesearchList;
	}

	// phone 수정
	public int personUpdate(PersonVo personVo) {
		getConnection();
		int count = 0;
		try {
			// SQL문 준비/ 바인딩 / 실행
			String query = "";
			query += " update person ";
			query += " set name = ?, ";
			query += " 	   hp = ?, ";
			query += " 	   company = ? ";
			query += " where person_id = ? ";
		
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, personVo.getName());
			pstmt.setString(2, personVo.getHp());
			pstmt.setString(3, personVo.getCompany());
			pstmt.setInt(4, personVo.getPerson_id());
			
			count = pstmt.executeUpdate();
			// 결과처리
			System.out.println("(dao)" + count + "건이 수정");
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		// 자원정리
		close();

		return count;
	}

	// phone 삭제
	public int personDelete(int personId) {
		getConnection();
		int count = 0;
		try {
			// SQL문 준비/ 바인딩 / 실행
			String query = "";
			query += " delete from person ";
			query += " where person_id = ? ";

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, personId);

			count = pstmt.executeUpdate();

			// 결과처리
			System.out.println("(dao)" + count + "건이 삭제");
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		// 자원정리
		close();

		return count;
	}
	
	// phone 리스트
	public List<PersonVo> getPersonList() {
		getConnection();
		List<PersonVo> phoneList = new ArrayList<PersonVo>();

		try {
			// SQL문 준비/ 바인딩 / 실행
			String query = "";
			query += " SELECT person_id, ";
			query += " 		  name, ";
			query += " 		  hp, ";
			query += " 		  company ";
			query += " FROM person ";

			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			// 결과처리
			while (rs.next()) {
				int person_id = rs.getInt("person_id");
				String name = rs.getString("name");
				String hp = rs.getString("hp");
				String company = rs.getString("company");

				PersonVo vo = new PersonVo(person_id, name, hp, company);
				phoneList.add(vo);
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		// 자원정리
		close();

		return phoneList;
	}

	// phone 저장
	public int personInsert(PersonVo personVo) {
		getConnection();
		int count = 0;

		try {
			// SQL문 준비/ 바인딩 / 실행
			String query = "";
			query += " insert into person ";
			query += " values(seq_person_id.nextval, ?, ?, ?) ";

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, personVo.getName());
			pstmt.setString(2, personVo.getHp());
			pstmt.setString(3, personVo.getCompany());

			count = pstmt.executeUpdate();

			// 결과처리
			System.out.println("(dao)" + count + "건이 저장");
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		// 자원정리
		close();

		return count;
	}
}
