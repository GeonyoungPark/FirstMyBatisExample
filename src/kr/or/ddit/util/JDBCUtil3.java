package kr.or.ddit.util;
/*
 * db.properties파일의 내용으로 DB정보를 설정하는 법
 * 
 * 방법1) Properties객체 이용하기
 */

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.ResourceBundle;
/*
 * 방법2) resourcebundle 객체 이용하기
 */
public class JDBCUtil3 {
	static ResourceBundle bundle; // peoperties객체 변수 선언
	
	static {
		//1.드라이버 로딩(선택사항)
		try {
			
			bundle =  ResourceBundle.getBundle("db"); //객체 생성
			
			
			Class.forName(bundle.getString("driver"));
			System.out.println("드라이버 로딩 완료!");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패!!!");
			e.printStackTrace();
		} 
	}
public static Connection getConnection() {
		
		try {
			return DriverManager.getConnection(
					bundle.getString("url"),
					bundle.getString("user"),
					bundle.getString("password"));
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	public static void close(Connection conn,
			Statement stmt,
			PreparedStatement pstmt,
			ResultSet rs) {
		if (rs != null)try {rs.close();} catch (SQLException ex) {}
		if (stmt != null)try {stmt.close();} catch (SQLException ex) {}
		if (conn != null)try {conn.close();} catch (SQLException ex) {}
		if (pstmt != null)try {pstmt.close();}catch (SQLException ex){}
	}
	
}
