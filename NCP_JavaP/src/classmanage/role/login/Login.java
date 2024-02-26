package classmanage.role.login;

import classmanage.db.dao.MemberDao;
import classmanage.dto.MemberDto;
import classmanage.role.login.interFace.CheckJob;
import classmanage.role.professor.ProfessorManagement;
import classmanage.role.student.StudentManagement;

public class Login {

	public void distinct() {
		MemberDao mDao = new MemberDao();
		MemberDto mDto = new MemberDto();
		CheckId ck = new CheckId();

		while (true) {
			mDto = ck.checkId();

			// 아이디가 존재하지 않는다면
			if (mDto == null) {
				return;
			}

			// 사용자에게 입력받은 id를 통해 학생인지 교수인지 조회 후 역할 페이지로 이동
			switch (mDto.getSelect()) {
			case CheckJob.STUDENT:
				System.out.println(mDao.getNamebyId(mDto.getUserId()) + " " + mDto.getSelect() + "님 로그인 성공!");
				StudentManagement st = new StudentManagement();
				st.run(mDto.getUserId());
				return;
			case CheckJob.PROFESSOR:
				System.out.println(mDao.getNamebyId(mDto.getUserId()) + " " + mDto.getSelect() + "님 로그인 성공!");
				ProfessorManagement pm = new ProfessorManagement();
				pm.run(mDto.getUserId());
				return;
			}

		}
	}
}
