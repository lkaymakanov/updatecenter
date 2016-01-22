package update.center.security;

public class User {

	private String userName;
	private String userPass;
	private int logged = 1;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPass() {
		return userPass;
	}
	public void setUserPass(String userPass) {
		this.userPass = userPass;
	}
	public int getLogged() {
		return logged;
	}
	public void setLogged(int logged) {
		this.logged = logged;
	}
	
	
}
