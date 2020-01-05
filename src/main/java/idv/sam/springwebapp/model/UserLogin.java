package idv.sam.springwebapp.model;

public class UserLogin extends User{
	private String loginStatus;	
	
	public String getLoginStatus() {
		return loginStatus;
	}
	public void setLoginStatus(String loginStatus) {
		this.loginStatus = loginStatus;
	}
	
	public void setLogin(User user) {
		this.setEmail(user.getEmail());
		this.setPassword(user.getPassword());
	}
}