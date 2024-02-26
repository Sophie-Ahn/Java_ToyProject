package classmanage.dto;

public class MemberDto {
	private String userId;
	private String salt;
	private String hashedPw;
	private String name;
	private String select;
	
	public MemberDto(String userId, String salt, String hashedPw, String name, String select) {
		this.userId = userId;
		this.salt = salt;
		this.hashedPw = hashedPw;
		this.name = name;
		this.select = select;
	}

	public MemberDto() {
		
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getSalt() {
		return salt;
	}
	
	public String getHashedPw() {
		return hashedPw;
	}

	public void setHashedPw(String hashedPw) {
		this.hashedPw = hashedPw;
	}

	public String getSelect() {
		return select;
	}

	public void setSelect(String select) {
		this.select = select;
	}

//	@Override
//	public String toString() {
//		return "MemberDto [userId=" + userId + ", name=" + name + ", select=" + select + "]";
//	}

}
