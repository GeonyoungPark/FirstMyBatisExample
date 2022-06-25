package kr.or.ddit.member.dao;

import java.util.List;

import kr.or.ddit.member.vo.MemberVO;

/**
 * 실제 DB와 연결해서 SQL문을 수행하여 결과를 조회한 후
 * 서비스에 전달하는 DAO의 인터페이스
 * @author PC-09
 *
 */
public interface IMemberDAO {
	/**
	 * MemberVO에 담긴 데이터를 DB에 insert 하는 메서드
	 * @param mv DB에 insert할 데이터가 저장된 MemberVO 객체
	 * @return DB작업이 성공하면 1 이상의 값이 반환되고 실패하면 0이 반환된다.
	 */
	public int insertMember(MemberVO mv);
	
	/**
	 *  하나의 MemberVo 데이터를 이용하여 회원정보를 업데이트하는 메서드
	 *  update 할 회원정보가 들어있는 MemberVO 객체
	 *  작성성공 :1, 작업실패 :0
	 *  
	 */
	public int updateMember(MemberVO mv);
	
	/**
	 * 회원ID를 매개변수로 받아서 그 회원 정보를 삭제하는 메서드
	 * @param memId 삭제할 ID
	 * @return 작업성공 : 1, 작업실패 :0
	 */
	public int deleteMember(String memId);
	/**
	 * DB의 mymember테이블에 존재하는 전체 레코드를 가져와 List에 담아서
	 * 반환하는 메서드
	 * @return MemberVO객체를 담고잇는 List객체
	 */
	public List<MemberVO> getAllMemberList();
	/**
	 * 주어진 회원ID를 이용하여 회원정보가 존재하는지 체크하기 위한 메서드
	 * @param memId 체크할 회원 ID
	 * @return 해당 회원ID의 회원정보가 존재하면 true, 없으면 false
	 */
	public boolean checkMember(String memId);
	
	/**
	 * 주어진 회원정보를 이용하여 해당 회원정보를 검색하는 메소드  
	 * @param mv 검색에 사용할 회원 정보
	 * @return 검색된 회원 정보를 담은 List
	 */
	public List<MemberVO> searchMember(MemberVO mv);
	
}
