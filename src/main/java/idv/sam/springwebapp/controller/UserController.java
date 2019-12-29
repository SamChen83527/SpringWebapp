package idv.sam.springwebapp.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import idv.sam.springwebapp.model.User;
import idv.sam.springwebapp.service.UserManager;

@Controller
@RequestMapping("/users")
public class UserController {
	/* Service Bean */
	UserManager userManager;
	
	/* Constructor Dependency Injection */
	public UserController(UserManager userManager) {
		this.userManager = userManager; 
	}
	
	/* GET */
	
	/* POST */
	@RequestMapping(value = "/userlogin", method = RequestMethod.POST)
	@ResponseBody
	public String userLogin(HttpServletRequest request) throws IOException {
		System.out.println("User Login");
		
		/* Parse request body*/
		BufferedReader reader = request.getReader();
	    StringBuilder builder = new StringBuilder();
	    String line = reader.readLine();
	    while (line != null) {
	        builder.append(line);
	        line = reader.readLine();
	    }
	    reader.close();
	    
	    String requestbody = builder.toString();
	    System.out.println("Request body: " + requestbody);	    
	    JSONObject requestbody_json = new JSONObject(requestbody);
	    
	    /* User validation */
	    User userLoginInfo = userManager.userLogin(requestbody_json.getString("username"), requestbody_json.getString("password") );
	    JSONObject response_json = new JSONObject(); 
	    if (userLoginInfo != null) {
	    	response_json
	    		.put("validation", "valid")
	    		.put("user_firstname", userLoginInfo.getFirstname())
	    		.put("user_lastname", userLoginInfo.getLastname())
	    		.put("user_email", userLoginInfo.getEmail())
	    		.put("user_username", userLoginInfo.getUsername());
	    } else {
	    	response_json.put("validation", "invalid");
	    }
	    
	    return response_json.toString();
	}
	
}
