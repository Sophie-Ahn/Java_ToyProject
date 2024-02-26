package classmanage.role.login.encrypt;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class GetHashPw {
	
	// SHA-256 알고리즘 사용하여 해시화된 비밀번호 생성
	public static String getHashPw(String inputPw, String salt) {
		String hashedPw = null;
		
		MessageDigest md;
		try {
			md = MessageDigest .getInstance("SHA-256");
			
			//System.out.println("암호화전 : " +  inputPw);
			
			// UTF-8로 인코딩된 바이트 배열 얻기
			byte[] pwdsalt = (inputPw + salt).getBytes(Charset.forName("UTF-8"));
			
			md.update(pwdsalt);
		
			// 해시화된 비밀번호를 Base64로 변환
			hashedPw = Base64.getEncoder().encodeToString(md.digest());
			
			//System.out.println("암호화후 : " + hashedPw);
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return hashedPw;
	}
}
