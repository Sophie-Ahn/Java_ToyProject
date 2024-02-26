package classmanage.role.student;

import java.util.List;
import java.util.Scanner;

import classmanage.db.dao.OpenClassDao;
import classmanage.dto.OpenClassDto;
import classmanage.role.student.interFace.ViewAllClassSel;
import classmanage.role.student.interFace.ViewRegistClassSel;

public class ViewAllClass {
	public void viewAllClass(String userId) {
		RegistClass rc = new RegistClass();
		Scanner sc = new Scanner(System.in);
		while(true) {
			System.out.println();
			System.out.println("====개설한 과목====");
			OpenClassDao dao = new OpenClassDao();
			List<OpenClassDto> dtoList = dao.allSubject();
			
			for(OpenClassDto dto : dtoList) {
				System.out.print(dto.getOpenSubjectIdx() + " ");
	        	System.out.print(dto.getUserId() + " ");
	        	System.out.println(dto.getSubject());
			}
			System.out.println("===============");
			System.out.println();
			System.out.println("1.수강신청  2.뒤로가기 3.종료");
			System.out.print("번호 입력 >> ");
			String num = sc.nextLine();
			switch(num) {
			case ViewAllClassSel.REGIST_CALSS: {
				rc.registClass(dtoList, userId);
				break;
			}
			case ViewAllClassSel.BACK: {
				return;
			}
			case ViewAllClassSel.EXIT:{
				System.exit(0);
			}
			default: {
				System.out.println("잘못된 입력입니다.");
			}
			}
		}
	}
}
