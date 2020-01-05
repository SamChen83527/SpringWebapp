package idv.sam.springwebapp.model;

public class UserRegistation extends User{	
	private String registationStatus;	

	public String getRegistationStatus() {
		return registationStatus;
	}

	public void setRegistationStatus(String registationStatus) {
		this.registationStatus = registationStatus;
	}
}
