package classmanage.role.login;

import java.util.Scanner;

import classmanage.db.dao.MemberDao;
import classmanage.dto.MemberDto;
import classmanage.role.login.encrypt.GetHashPw;
import classmanage.role.login.encrypt.GetSalt;

public class SignUp {

	public void member() {
		Scanner sc = new Scanner(System.in);
		boolean isSave = true;
		String id = null;
		String inputPw = null;
		String salt = null;
		String hashedPw = null;
		String name = null;
		String select = null;
		
		while (isSave) {

			MemberDao mDao = new MemberDao();

			if (id == null) {
				System.out.print("ID(학번/교수번호)입력 (0 입력시 뒤로가기) >> ");
				id = sc.nextLine();
				if ("0".equals(id)) {
					id = null;
					break;
				}
				// ID 입력시 10자를 초과하거나, 숫자가 아닌 경우
				if (id.length() > 10 || !id.matches("\\d+") || id.split(" ").length > 1) {
					System.out.println("잘못된 입력입니다. ID는 10자 이하 숫자로만 입력해주세요.");
					id = null;
					continue;
				}
				// ID가 이미 존재하는 경우
				if (mDao.isMemberExists(id)) {
					System.out.println("이미 존재하는 회원입니다.");
					id = null;
					continue;
				}
			}

			if (inputPw == null) {
				// 사용자 입력 비밀번호
				System.out.print("비밀번호 입력 (0 입력시 뒤로가기) >> ");
				inputPw = sc.nextLine();
				if ("0".equals(inputPw)) {
					id = null;
					inputPw = null;
					continue;
				}
				salt = GetSalt.getSalt();

				hashedPw = GetHashPw.getHashPw(inputPw, salt);
			}

			if (name == null) {
				System.out.print("이름 입력 (0 입력시 뒤로가기) >> ");
				name = sc.nextLine();
				if ("0".equals(name)) {
					inputPw = null;
					name = null;
					continue;
				}
				// 이름 입력시 숫자나 특수기호를 입력하는 경우
				if (name.length() > 10 || name.matches(".*\\d.*") || !name.matches("[a-zA-Z가-힣]+") || name.split(" ").length > 1) {
					System.out.println("잘못된 입력입니다. 이름은 숫자나 특수기호를 포함하지 않는 10자 이내 문자로 입력해주세요.");
					name = null;
					continue;
				}
			}

			if (select == null) {
				System.out.print("학생/교수 선택 (0 입력시 뒤로가기) >> ");
				select = sc.nextLine();
				if ("0".equals(select)) {
					name = null;
					select = null;
					continue;
				}

				if (!select.equals("학생") && !select.equals("교수") || select.length() > 2) {
					System.out.println("잘못된 입력입니다. 학생/교수 중에 선택하여 입력해주세요.");
					select = null;
					continue;
				}
			}

			MemberDto mDto = new MemberDto(id, salt, hashedPw, name, select);

			mDao.saveToDatabase(mDto);
			
			isSave = false;

		}

	}
}