package kr.or.ddit.basic;

import org.apache.ibatis.session.SqlSession;

import kr.or.ddit.member.vo.MemberVO2;
import kr.or.ddit.util.MybatisUtil;

public class MybatisInsertTest {
	public static void main(String[] args) {
		
		SqlSession sqlSession = MybatisUtil.getInstance(true);
		MemberVO2 mv = new MemberVO2();
		mv.setMemName("채희진");
		mv.setMemTel("3333-3333");
		mv.setMemAddr("대구시 수성구");
		
		int cnt = sqlSession.insert("member2.insertMember",mv);
		
		if (cnt > 0) {
			System.out.println("등록성공");
			
			//아이디를 oracle에서 nextVal로 셋팅하고 자바에선 셋팅안했으니 null값이 나옴
			//member2.xml에서 selectKey를 설정하고 sql문 바꿈. 
			System.out.println("memId => " + mv.getMemId()); 
			
			//메일발송
			
		}else {
			System.out.println("실패");
		}
		
	}
}
