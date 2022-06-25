package kr.or.ddit.member.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import kr.or.ddit.member.vo.MemberVO;
import kr.or.ddit.util.JDBCUtil3;
//dao jdbc 코딩하는 곳
public class MemberDaoImplForJDBC implements IMemberDAO {

	private Connection conn;
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private static IMemberDAO memDao;
	
	
	private MemberDaoImplForJDBC() {
		
	}
	
	public static IMemberDAO getInstance() {
		if (memDao == null) {
			memDao = new MemberDaoImplForJDBC();
		}
		return memDao;
	}

	@Override
	public int insertMember(MemberVO mv) {
		int cnt = 0;
		try {
			conn = JDBCUtil3.getConnection();
			String sql = "INSERT INTO mymember ( mem_id, mem_name, mem_tel, mem_addr,reg_dt)"
					+ " VALUES (?,?,?,?,sysdate)"; // values앞에 스페이스 하나 주기
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mv.getMemId());
			pstmt.setString(2, mv.getMemName());
			pstmt.setString(3, mv.getMemTel());
			pstmt.setString(4, mv.getMemAddr());

			cnt = pstmt.executeUpdate(); // 인트값 반환

		} catch (SQLException e) {
		} finally {
			JDBCUtil3.close(conn, stmt, pstmt, rs);
		}
		return cnt;
	}

	@Override
	public int updateMember(MemberVO mv) {
		int cnt = 0;
		try {
			conn = JDBCUtil3.getConnection();
			String sql = "update mymember set mem_name = ?, mem_tel =?, mem_addr = ? where mem_id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mv.getMemName());
			pstmt.setString(2, mv.getMemTel());
			pstmt.setString(3, mv.getMemAddr());
			pstmt.setString(4, mv.getMemId());

			cnt = pstmt.executeUpdate();

		} catch (SQLException e) {
		} finally {
			JDBCUtil3.close(conn, stmt, pstmt, rs);
		}
		return cnt;
	}

	@Override
	public int deleteMember(String memId) {
		int cnt = 0;
		try {
			conn = JDBCUtil3.getConnection();
			String sql = "delete from mymember where mem_id= ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memId);

			cnt = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil3.close(conn, stmt, pstmt, rs);
		}
		return cnt;
	}

	@Override
	public List<MemberVO> getAllMemberList() {
		List<MemberVO> memList = new ArrayList<MemberVO>();
		
		try {
			conn = JDBCUtil3.getConnection();

			String sql = "select * from mymember";

			stmt = conn.createStatement();

			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				//꺼내서
				String memId = rs.getString(1);
				String memName = rs.getString(2);
				String memTel = rs.getString(3);
				String memAddr = rs.getString(4);
				
				//VO에 set
				MemberVO mv = new MemberVO();
				mv.setMemAddr(memAddr);
				mv.setMemId(memId);
				mv.setMemName(memName);
				mv.setMemTel(memTel);
				
				//ArrayList에 저장
				memList.add(mv);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil3.close(conn, stmt, pstmt, rs);
		}

		return memList;
	}

	@Override
	public boolean checkMember(String memId) {
		boolean isExist = false;

		try {
			conn = JDBCUtil3.getConnection();

			String sql = "select count(*) as cnt" + " from mymember where mem_id = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memId);

			rs = pstmt.executeQuery();

			int cnt = 0;
			if (rs.next()) {
				cnt = rs.getInt("cnt");
			}

			if (cnt > 0) {
				isExist = true;
			} else {
				isExist = false;
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			JDBCUtil3.close(conn, stmt, pstmt, rs);
		}

		return isExist;

	}

	@Override
	public List<MemberVO> searchMember(MemberVO mv) {
		List<MemberVO> memList = new ArrayList<MemberVO>();
		//dynamic Query 사용해야함
		try {
			conn = JDBCUtil3.getConnection();
			String sql = "select * from mymember where 1=1";
			if (mv.getMemId() !=null && !mv.getMemId().equals("")) {
				sql += " and mem_id = ?";
			}
			if (mv.getMemName() !=null && !mv.getMemName().equals("")) {
				sql += " and mem_name = ?";
			}
			if (mv.getMemAddr() !=null && !mv.getMemAddr().equals("")) {
				sql += " and mem_addr like '%' || ? || '%' ";
			}
			if (mv.getMemTel() !=null && !mv.getMemTel().equals("")) {
				sql += " and mem_tel = ?";
			}
			
			pstmt = conn.prepareStatement(sql);
			int index = 1;
			if (mv.getMemId() !=null && !mv.getMemId().equals("")) {
				pstmt.setString(index++, mv.getMemId()); 
			}
			if (mv.getMemName() !=null && !mv.getMemName().equals("")) {
				pstmt.setString(index++, mv.getMemName()); 
			}
			if (mv.getMemAddr() !=null && !mv.getMemAddr().equals("")) {
				pstmt.setString(index++, mv.getMemAddr()); 
			}
			if (mv.getMemTel() !=null && !mv.getMemTel().equals("")) {
				pstmt.setString(index++, mv.getMemTel()); 
			}
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				//꺼내서
				String memId = rs.getString(1);
				String memName = rs.getString(2);
				String memTel = rs.getString(3);
				String memAddr = rs.getString(4);
				
				//VO에 set
				MemberVO mv2 = new MemberVO();
				mv2.setMemAddr(memAddr);
				mv2.setMemId(memId);
				mv2.setMemName(memName);
				mv2.setMemTel(memTel);
				
				//ArrayList에 저장
				memList.add(mv2);
			}
			
		} catch (SQLException e) {
			// TODO: handle exception
		}finally {
			JDBCUtil3.close(conn, stmt, pstmt, rs);
		}
		
		return memList;
	}

}
