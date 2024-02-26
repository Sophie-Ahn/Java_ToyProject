package classmanage.role.student;

import java.util.Scanner;

import classmanage.db.dao.MemberDao;
import classmanage.dto.MemberDto;
import classmanage.role.student.interFace.StudentMainSel;


public class StudentManagement {
	public void run(String userId) {
		Scanner sc = new Scanner(System.in);
		ViewAllClass vac = new ViewAllClass();
		ViewRegistClass vrc = new ViewRegistClass();
		ChatStudent chat = null;
		while(true) {
			System.out.println();
			System.out.println("1. 과목 조회");
			System.out.println("2. 수강 과목 조회");
			System.out.println("3. 채팅");
			System.out.println("4. 로그아웃");
			System.out.print("번호 입력 >> ");
			String sel = sc.nextLine();
			switch (sel) {
				case StudentMainSel.VIEW_ALLCLASS: {
					// 전체 과목 조회 -> 수강신청
					vac.viewAllClass(userId);
					break;
				}
				case StudentMainSel.VIEW_REGISTCALSS: {
					// 현재 수강중인 과목 조회 -> 성적조회
					vrc.viewRegistClass(userId);
					break;
				}
				case StudentMainSel.CHAT: {
					MemberDao mDao = new MemberDao();
					MemberDto mDto = new MemberDto();
					mDto = mDao.memberInfo(userId);
					chat = new ChatStudent(mDto);
					break;
				}
				case StudentMainSel.LOGOUT: {
					// 로그아웃. 초기 화면으로 이동
					System.out.println();
					return;
				}
				default: {
					System.out.println("잘못된 입력입니다.");
				}
			}
		}
			
	}
}
