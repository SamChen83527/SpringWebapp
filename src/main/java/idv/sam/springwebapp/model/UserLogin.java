package idv.sam.springwebapp.model;

public class UserLogin extends User{
	private String loginStatus;	
	
	public UserLogin() {
		
	}
	
	public UserLogin(String email, String password) {
		this.setEmail(email);
		this.setPassword(password);
	}
	
	public String getLoginStatus() {
		return loginStatus;
	}
	public void setLoginStatus(String loginStatus) {
		this.loginStatus = loginStatus;
	}
	
	public void setLogin(User user) {
		this.setEmail(user.getEmail());
		this.setPassword(user.getPassword());
		this.setUsername(user.getUsername());
		this.setFirstname(user.getFirstname());
		this.setLastname(user.getLastname());
	}
}