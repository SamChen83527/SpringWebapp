package idv.sam.springwebapp.model;

import org.json.JSONObject;

public class User {	
	private long rid;
	private String firstname;
    private String lastname;
    private String email;
    private String username;
    private String password;
    
    public User(){}
    
    public User(String user_string){
    	JSONObject user_jsonobject = new JSONObject(user_string);
    	
        this.firstname = user_jsonobject.getString("firstname");
        this.lastname = user_jsonobject.getString("lastname");
        this.email = user_jsonobject.getString("email");
        this.username = user_jsonobject.getString("username");
        this.password = user_jsonobject.getString("password");
    }
    
    public User(String firstname, String lastname, String email, String username, String password){
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.username = username;
        this.password = password;
    }
    
    public User(String firstname, String lastname, String email, String username){
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.username = username;
    }
    
    public long getRid() {
		return rid;
	}

	public void setRid(long rid) {
		this.rid = rid;
	}
	
    public String getUsername() {
        return username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    @Override
	public String toString(){
    	JSONObject user_jsonobject = new JSONObject();
    	user_jsonobject
    		.put("firstname", getFirstname())
    		.put("lastname", getLastname())
    		.put("email", getEmail())
    		.put("username", getUsername())
    		.put("password", getPassword());
    	
		return user_jsonobject.toString();
	}

}
