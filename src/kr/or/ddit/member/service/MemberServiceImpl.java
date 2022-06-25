package kr.or.ddit.member.service;

import java.util.List;

import kr.or.ddit.member.dao.IMemberDAO;
import kr.or.ddit.member.dao.MemberDAOImpl;
import kr.or.ddit.member.dao.MemberDaoImplForJDBC;
import kr.or.ddit.member.vo.MemberVO;
import kr.or.ddit.util.MybatisUtil;

public class MemberServiceImpl implements IMemberService {
	private IMemberDAO memDao;

	private static IMemberService memService;
	
	private  MemberServiceImpl() {
		memDao = MemberDAOImpl.getInstance();
	}

	public static IMemberService getInsatnce() {
		if (memService == null) {
			memService = new MemberServiceImpl();
		}
		return memService;
		
	}
	//서비스가 기능 구현의 단위가 됨. (Transaction)
	@Override
	public int registMember(MemberVO mv) {
		//여기에 여러가지 기능을 추가할 수 있음.
		
		//주민등록번호가 암호화 처리하기
		
		int cnt = memDao.insertMember(mv);
		
		//해당 사용자에 회원정보 등록 완료 메일 발송하기
		
		return cnt;
	}

	@Override
	public int updateMember(MemberVO mv) {
		int cnt = memDao.updateMember(mv);
		return cnt;
	}

	@Override
	public int deleteMember(String memId) {
		int cnt = memDao.deleteMember(memId);
		return cnt;
	}

	@Override
	public List<MemberVO> getAllMemberList() {
		List<MemberVO> memList = memDao.getAllMemberList();
		return memList;
	}

	@Override
	public boolean checkMember(String memId) {
		boolean isExist = memDao.checkMember(memId);
		return isExist;
	}
	
	@Override
	public List<MemberVO> searchMember(MemberVO mv) {
		
		List<MemberVO> memList = memDao.searchMember(mv);
		return memList;
	}

}
