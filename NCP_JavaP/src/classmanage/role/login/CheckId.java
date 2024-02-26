package classmanage.role.login;

import java.util.Scanner;

import classmanage.db.dao.MemberDao;
import classmanage.dto.MemberDto;
import classmanage.role.login.encrypt.GetHashPw;

public class CheckId {
	public MemberDto checkId() {
		MemberDao mDao = new MemberDao();
		MemberDto mDto = new MemberDto();
		Scanner sc = new Scanner(System.in);

		// 입력한 ID로 salt값을 조회
		System.out.print("아이디 입력 >> ");
		String id = sc.nextLine();
		String role = mDao.getRoleById(id);
		String savedSalt = mDao.getSaltbyId(id);
		String savedPw = mDao.getHashPwbyID(id);

		// 아이디가 존재하지 않는다면
		if (role == null) {
			System.out.println("존재하지 않는 회원입니다.");
			return null;
		}

		// 입력한 비밀번호와 salt 값을 다시 암호화하여 조회한 salt값과 비교
		System.out.print("비밀번호 입력 >> ");
		String inputPw = sc.nextLine();
		String chekPw = GetHashPw.getHashPw(inputPw, savedSalt);

		// 비밀번호가 맞으면
		if (chekPw.equals(savedPw)) {
			mDto.setUserId(id);
			mDto.setHashedPw(savedPw);
			mDto.setSelect(role);

			return mDto;
		} else {
			System.out.println("비밀번호가 틀렸습니다.\n");
			return null;
		}

	}
}
