package kr.or.ddit.member;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import kr.or.ddit.member.service.IMemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.member.vo.MemberVO;

/*
	회원정보를 관리하는 프로그램을 작성하는데 
	아래의 메뉴를 모두 구현하시오. (CRUD기능 구현하기)
	(DB의 MYMEMBER테이블을 이용하여 작업한다.)
	
	* 자료 삭제는 회원ID를 입력 받아서 삭제한다.
	
	예시메뉴)
	----------------------
		== 작업 선택 ==
		1. 자료 입력			---> insert
		2. 자료 삭제			---> delete
		3. 자료 수정			---> update
		4. 전체 자료 출력		---> select
		5. 작업 끝.
	----------------------
	 
	   
// 회원관리 프로그램 테이블 생성 스크립트 
create table mymember(
    mem_id varchar2(8) not null,  -- 회원ID
    mem_name varchar2(100) not null, -- 이름
    mem_tel varchar2(50) not null, -- 전화번호
    mem_addr varchar2(128),    -- 주소
    reg_dt DATE DEFAULT sysdate, -- 등록일
    CONSTRAINT MYMEMBER_PK PRIMARY KEY (mem_id)
);

*/
public class MemberInfoMain {

	private IMemberService memService;
	
	private Scanner scan = new Scanner(System.in);

	
	public MemberInfoMain() {  //getInstance 서비스를 싱글톤으로 만들었다는걸 알수있음
		memService = MemberServiceImpl.getInsatnce();
	}

	

	/**
	 * 메뉴를 출력하는 메서드
	 */
	public void displayMenu() {
		System.out.println();
		System.out.println("----------------------");
		System.out.println("  === 작 업 선 택 ===");
		System.out.println("  1. 자료 입력");
		System.out.println("  2. 자료 삭제");
		System.out.println("  3. 자료 수정");
		System.out.println("  4. 전체 자료 출력");
		System.out.println("  5. 회원정보 검색");
		System.out.println("  6. 작업 끝.");
		System.out.println("----------------------");
		System.out.print("원하는 작업 선택 >> ");
	}

	/**
	 * 프로그램 시작메서드
	 */
	public void start() {
		int choice = 0;
		do {
			displayMenu(); // 메뉴 출력
			choice = scan.nextInt(); // 메뉴번호 입력받기
			switch (choice) {
			case 1: // 자료 입력
				insertMember();
				break;
			case 2: // 자료 삭제
				deleteMember();
				break;
			case 3: // 자료 수정
				updateMember();
				break;
			case 4: // 전체 자료 출력
				displayMemberAll();
				break;
			case 5: // 회원정보 검색
				searchMember();
				break;
			case 6: // 작업 끝
				System.out.println("작업을 마칩니다.");
				break;
			default:
				System.out.println("번호를 잘못 입력했습니다. 다시입력하세요");
			}
		} while (choice != 6);
	}

	/**
	 * 회원정보를 검색하는 메서드
	 */
	private void searchMember() {
		/*
		 	검색할 회원 ID, 회원이름, 전화번호, 주소등을 입력하면
		 	입력한 정보만 사용하여 검색하는 기능을 구현
		 	주소는 입력한 값만 포함되어도 검색 되도록 한다.
		 	입력을 하지 않은 자료는 엔터키로 다음 입력으로 넘긴다.
		 */
		scan.nextLine(); //입력버퍼 비우기
		System.out.println();
		System.out.println("검색할 정보를 입력하세요");
		System.out.print("회원ID>>> ");
		String memId = scan.nextLine().trim();
		
		System.out.print("회원이름>>> ");
		String memName = scan.nextLine().trim();
		
		System.out.print("회원전화번호>>> ");
		String memTel = scan.nextLine().trim();
		
		System.out.print("회원주소>>> ");
		String memAddr = scan.nextLine().trim();
		
		MemberVO mv = new MemberVO();
		mv.setMemAddr(memAddr);
		mv.setMemTel(memTel);
		mv.setMemId(memId);
		mv.setMemName(memName);
		
		//검색 기능 호출...
		List<MemberVO> memList = memService.searchMember(mv);
		
		System.out.println("-------------------------------------------------------");
		System.out.println("ID\t이 름\t전화번호\t\t주  소");
		System.out.println("-------------------------------------------------------");

		if (memList.size() == 0) {
			System.out.println("검색된 회원정보가 없습니다.");
		}else {
			for (MemberVO mv2 : memList) {
				System.out.println(mv2.getMemId() + "\t" 
								 + mv2.getMemName()+ "\t" 
								 + mv2.getMemTel() + "\t" 
								 + mv2.getMemAddr());
			}
		}
		
		System.out.println("-------------------------------------------------------");
		System.out.println("검색작업 끝...");
	}



	// 회원정보를 삭제하기 위한 메소드
	private void deleteMember() {
		System.out.println();
		System.out.println("삭제할 회원 정보를 입력하세요");
		System.out.print("회원ID >>");

		String memId = scan.next();
		
		int cnt = memService.deleteMember(memId);
		
		if (cnt > 0) {
			System.out.println(memId + "회원정보 삭제 성공");
		}else {
			System.out.println(memId + "회원정보 삭제 실패!!!");
			
		}
	}

	// 회원정보를 수정하기 위한
	private void updateMember() {
		String memId = "";
		boolean isExist = false; // 중복체크용
		do {
			System.out.println();
			System.out.println("업데이트할 회원 정보를 입력하세요");
			System.out.print("회원ID >>");

			memId = scan.next();

			isExist = checkMember(memId);

			if (!isExist) {
				System.out.println("회원ID가 " + memId + "인 회원은 이미 존재하지 않습니다.");
				System.out.println("다시 입력하세요");
			}
		} while (!isExist);

		System.out.print("회원 이름>>");
		String memName = scan.next();

		System.out.print("회원 전화번호>>");
		String memTel = scan.next();

		scan.nextLine(); // 입력 버퍼 비우기

		System.out.print("회원 주소>>");
		String memAddr = scan.nextLine();

		MemberVO mv = new MemberVO();
		mv.setMemAddr(memAddr);
		mv.setMemId(memId);
		mv.setMemName(memName);
		mv.setMemTel(memTel);
		
		int cnt = memService.updateMember(mv);
		
		if (cnt > 0) {
			System.out.println(memId  + "업데이트 성공");
		}else {
			
			System.out.println(memId  + "업데이트 실패!!!");
		}
	}

	/*
	 * 전체 회원 정보를 출력하는 메서드
	 */
	private void displayMemberAll() {
		System.out.println("-------------------------------------------------------");
		System.out.println("ID\t이 름\t전화번호\t\t주  소");
		System.out.println("-------------------------------------------------------");

		List<MemberVO> memList = memService.getAllMemberList();
		if (memList.size() == 0) {
			System.out.println("회원정보가 없습니다.");
		}else {
			for (MemberVO mv : memList) {
				System.out.println(mv.getMemId() + "\t" 
								 + mv.getMemName()+ "\t" 
								 + mv.getMemTel() + "\t" 
								 + mv.getMemAddr());
			
			}
		}
		
		System.out.println("-------------------------------------------------------");
		System.out.println("출력작업 끝...");
		
	}

	private void insertMember() {
		String memId = "";
		boolean isExist = false; // 중복체크용
		do {
			System.out.println();
			System.out.println("추가할 회원 정보를 입력하세요");
			System.out.print("회원ID >>");

			memId = scan.next();

			isExist = checkMember(memId);

			if (isExist) {
				System.out.println("회원ID가 " + memId + "인 회원은 이미 존재합니다.");
				System.out.println("다시 입력하세요");
			}
		} while (isExist);

		System.out.print("회원 이름>>");
		String memName = scan.next();

		System.out.print("회원 전화번호>>");
		String memTel = scan.next();

		scan.nextLine(); // 입력 버퍼 비우기

		System.out.print("회원 주소>>");
		String memAddr = scan.nextLine();
		MemberVO mv =  new MemberVO();
		mv.setMemAddr(memAddr);
		mv.setMemId(memId);
		mv.setMemName(memName);
		mv.setMemTel(memTel);
		
		int cnt = memService.registMember(mv);
		
		if (cnt > 0) {
			System.out.println(mv.getMemId() + "회원정보 등록 성공!");
		}else {
			System.out.println(mv.getMemId() + "회원정보 등록 실패!!!");
		}
		return;
	}

	// 회원이 ID 중복체크 메소드 존재하면 true 존재하지않으면 false 리턴
	private boolean checkMember(String memId) {

		boolean isExist = memService.checkMember(memId);
		return isExist;
	}

	public static void main(String[] args) {
		MemberInfoMain memObj = new MemberInfoMain();
		memObj.start();
	}

}
