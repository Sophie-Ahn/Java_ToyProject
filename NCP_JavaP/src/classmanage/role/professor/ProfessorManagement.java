package classmanage.role.professor;

import java.util.Scanner;

import classmanage.db.dao.MemberDao;
import classmanage.dto.MemberDto;
import classmanage.main.ClassMangement;
import classmanage.role.professor.interFace.ProfessorMainSel;

public class ProfessorManagement {

	public void run(String id) {
		Scanner sc = new Scanner(System.in);
		boolean isRun = true;

		while (isRun) {
			System.out.println("======교수 페이지======");
			System.out.println("1. 과목 개설");
			System.out.println("2. 개설 강좌 조회");
			System.out.println("3. 과목 삭제");
			System.out.println("4. 채팅");
			System.out.println("5. 로그아웃");
			System.out.println("번호 입력 >> ");

			String sel = sc.next();
			switch (sel) {

			case ProfessorMainSel.OPEN_CLASS: {
				CreateClass cc = new CreateClass();
				cc.createClass(id);
				break;
			}
			case ProfessorMainSel.MY_CLASS: {
				ViewMyClass vmc = new ViewMyClass();
				vmc.myClass(id);
				break;
			}
			case ProfessorMainSel.DELETE_CLASS: {
				DeleteClass dc = new DeleteClass();
				dc.deleteClass(id);
				break;
			}case ProfessorMainSel.CHAT: {
				MemberDao dao = new MemberDao();
				MemberDto user = new MemberDto();
				user = dao.memberInfo(id);
				ChatProfessor cp = new ChatProfessor(user);
				break;
			}
			
			case ProfessorMainSel.LOGOUT: {
				isRun = false;
				return;
			}
			
			default: {
				System.out.println("잘못된 입력입니다.");
			}
			}

		}
	}

}
