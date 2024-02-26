package classmanage.role.login.encrypt;

import java.security.SecureRandom;
import java.util.Base64;

public class GetSalt {
	// 무작위 난수(salt) 생성
	public static String getSalt() {
		
		SecureRandom rd = new SecureRandom();
		byte[] salt = new byte[16];
		
		// 난수 생성
		rd.nextBytes(salt);
		
		return  Base64.getEncoder().encodeToString(salt);
	}
}
