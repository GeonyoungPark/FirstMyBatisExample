package kr.or.ddit.basic;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import kr.or.ddit.member.vo.MemberVO;

public class MemberMybatisTest {
	// mybatis를 이용하여 DB자료를 처리하는 작업 순서
	// 1. mybatis의 환경설정파일을 읽어와 실행시킨다.
	public static void main(String[] args) {
		SqlSession sqlSession = null;
		try {
			// 1-1. xml문서 읽어오기
			// 설정파일의 인코딩 설정(한글처리를 위해서...)
			Charset charset = Charset.forName("UTF-8");
			Resources.setCharset(charset);
			Reader rd = Resources.getResourceAsReader("mybatis-config.xml");

			// 1-2. 위에서 읽어온 Reader객체를 이용하여 실제 작업을
			// 진행할 객체를 생성하기
			SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(rd);

			// auto commit false 설정
			sqlSession = sessionFactory.openSession(false);
			rd.close();// Reader 객체 닫기
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 2. 실행할 SQL문에 맞는 쿼리문을 호출해서 원하는 작업을
		// 수행한다.
		// 2-1 insert작업 연습
		System.out.println("insert 작업 시작...");

		// 1) 저장할 데이터를 VO에 담는다.
		MemberVO mv = new MemberVO();
		mv.setMemId("d001");
		mv.setMemName("강감찬");
		mv.setMemTel("010-1111-1111");
		mv.setMemAddr("경남 사천시");

		// 2) Sqlsession 객체를 이용하여 해당 쿼리문을 실행한다.
		// 형식) mybatis.insert("namespace값.id값", 파라미터객체);
		// 반환값 : 성공하면 int값이 반환
		try {

			int cnt = sqlSession.insert("memberTest.insertMember", mv);

			if (cnt > 0) {
				System.out.println("insert작업 성공");
				sqlSession.commit();

			} else {
				System.out.println("insert작업 실패!!!");
			}

		} catch (PersistenceException e) {
			sqlSession.rollback();
			e.printStackTrace();
		}
		System.out.println("---------------------------------------------------");

		// 2-2. update작업 연습
		System.out.println("update 작업 시작...");
		mv = new MemberVO();
		mv.setMemId("d001");
		mv.setMemName("한재웅");
		mv.setMemTel("2222-2222");
		mv.setMemAddr("서울시 영등포구");

		// update 반환값 : 성공한 레코드 수
		int cnt = sqlSession.update("memberTest.updateMember", mv);

		if (cnt > 0) {
			System.out.println("업데이트 성공..");
			sqlSession.commit();

		} else {
			System.out.println("업데이트 실패!!!");
			sqlSession.rollback();
		}
		/*
		 * //2-3 delete작업 연습 System.out.println("delete 작업 시작...");
		 * 
		 * //delete 반환값 : 성공한 레코드 수 cnt = sqlSession.delete("memberTest.deleteMember",
		 * "d001");
		 * 
		 * if (cnt > 0) { System.out.println("삭제 성공.."); sqlSession.commit();
		 * 
		 * }else { System.out.println("삭제 실패!!!"); sqlSession.rollback(); }
		 * 
		 * System.out.println(
		 * "-------------------------------------------------------------------");
		 * 
		 */
		// 2-4 select 연습
		// 1) 응답의 결과가 여러개 일 경우...
		System.out.println("Select연습 시작(결과값이 여러개일 경우 List타입 리턴)");

		List<MemberVO> memList = null;

		/*
		 * 응답결과가 여러개 일 경우에는 selectList()메서드를 이용한다 이 메서드는 여러개의 레코드를 VO에 담은 후 이 VO데이터를
		 * List에 추가해 주는 작업을 자동으로 수행한다.
		 */
		memList = sqlSession.selectList("memberTest.getMemberAll3");

		for (MemberVO mv2 : memList) {
			System.out.println("ID : " + mv2.getMemId());
			System.out.println("이름 : " + mv2.getMemName());
			System.out.println("전화 : " + mv2.getMemTel());
			System.out.println("주소 : " + mv2.getMemAddr());
			System.out.println("==============================================");
		}
		System.out.println("-----------------------------------------------------------");
		System.out.println("출력 끝...");

		// 2) 응답결과가 1인 경우
		System.out.println("select 연습 시작(결과가 1개인 경우)");

		// 응답 결과가 1개가 확실한 경우에는 selectOne() 메소드를 이용한다
		
		MemberVO mv3 = (MemberVO) sqlSession.selectOne("memberTest.getMember","d001");
		
		System.out.println("ID : " + mv3.getMemId());
		System.out.println("이름 : " + mv3.getMemName());
		System.out.println("전화 : " + mv3.getMemTel());
		System.out.println("주소 : " + mv3.getMemAddr());
		System.out.println("==============================================");
	}

}
