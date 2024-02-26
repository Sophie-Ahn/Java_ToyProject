package classmanage.role.professor;

import java.util.List;
import java.util.Scanner;

import classmanage.db.dao.OpenClassDao;
import classmanage.dto.OpenClassDto;
import classmanage.role.professor.interFace.ViewMyClassSel;

public class ViewMyClass {
	public void myClass(String id) {
		Scanner sc = new Scanner(System.in);
		boolean isRun = true;
		while (isRun) {
			System.out.println("====개설한 과목====");

			OpenClassDao dao = new OpenClassDao();
			List<OpenClassDto> dto = dao.viewMyClass(id);
			for (OpenClassDto openSubjectDto : dto) {
				System.out.println(openSubjectDto.getSubject());
			}

			System.out.println("===============");
			System.out.println("1.수강 학생 보기");
			System.out.println("2.과목 학점 보기");
			System.out.println("3.뒤로가기");
			System.out.println("4.종료");
			System.out.println("번호 입력 >> ");

			String sel = sc.next();
			switch (sel) {

			case ViewMyClassSel.VIEW_CLASS_STUDENT: {
				System.out.println("과목 입력 >> ");
				String subject = sc.next();
				ViewClassStudent vcs = new ViewClassStudent();
				vcs.viewRegistClassStudent(id, subject);
				break;
			}
			case ViewMyClassSel.VIEW_STUDENT_SCORE: {
				System.out.println("입력된 학점 볼 과목 >>");
				String subject = sc.next();
				ViewStudentScore vs = new ViewStudentScore();
				vs.viewStudentScore(id, subject);
				break;
			}
			case ViewMyClassSel.BACK: {
				return;
			}
			case ViewMyClassSel.EXIT: {
				System.out.println("프로그램 종료.");
				System.exit(0);
				break;
			}
			default: {
				System.out.println("잘못된 입력입니다.");
			}
			}
		}
	}
}
