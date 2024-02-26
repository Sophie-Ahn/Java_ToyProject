package classmanage.main;

import java.util.Scanner;

import classmanage.db.dao.MemberDao;
import classmanage.role.login.Login;
import classmanage.role.login.SignUp;
import classmanage.role.login.Update;
import classmanage.role.login.interFace.MainSel;

public class ClassMangement {
	//시작화면
	public void run() {
		Scanner sc = new Scanner(System.in);
		boolean isRun = true;
		
		while(isRun) {
			System.out.println("1. 로그인");
			System.out.println("2. 회원 가입");
			System.out.println("3. 회원정보 수정");
			System.out.println("4. 프로그램 종료");
			System.out.print("번호 입력 >> ");
			String sel = sc.nextLine();
			
			switch (sel) {
			case MainSel.LOGIN:
				// 로그인 -> 아이디 입력 -> 교수,학생 구분 -> 해당 입력창
				System.out.println("\n(๑>◡<๑)로그인창(๑>◡<๑)");
				Login login = new Login();
				login.distinct();
				break;
			case MainSel.SIGNUP:
				// 회원가입 -> 이름, 아이디, 구분(학생, 입력) -> DB저장, 알림 -> 다시 메인창
				System.out.println("\n(๑>◡<๑)회원가입 창(๑>◡<๑)");
				SignUp su = new SignUp();
				su.member();
				break;
			case MainSel.UPDATE:
				// 회원정보수정 -> 아이디 입력 -> 로그인 성공 -> 수정페이지
				System.out.println("\n(๑>◡<๑)회원정보 수정창(๑>◡<๑)");
				Update ud = new Update();
				ud.updateMember();
				break;
			case MainSel.EXIT:
				System.out.println("\n(๑>◡<๑)프로그램을 종료합니다.(๑>◡<๑)");
				isRun = false;
				sc.close();
				break;
			default:
				System.out.println("Warning : 올바른 번호를 입력해주세요.");
				break;
			}
			
		}
		
	}
	
}
