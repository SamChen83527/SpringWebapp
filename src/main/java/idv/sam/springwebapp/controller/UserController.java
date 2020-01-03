package idv.sam.springwebapp.controller;

import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import idv.sam.springwebapp.model.Login;
import idv.sam.springwebapp.model.User;
import idv.sam.springwebapp.service.UserManager;

@Controller
@RequestMapping("/user")
public class UserController {
	/* Service Bean */
	UserManager userManager;
	
	/* Constructor Dependency Injection */
	public UserController(UserManager userManager) {
		this.userManager = userManager; 
	}
	
	/* GET */
	
	/* POST */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView userLogin(HttpServletRequest request, RedirectAttributes redirectAttributes, @ModelAttribute("login") Login login) throws IOException {
		System.out.println("User Login");
		
		/* User validation */
		User userInfo = userManager.userLogin(login.getEmail(), login.getPassword());
		if (userInfo.getUserStatus() == "valid") {
			System.out.println("Login successful");
			redirectAttributes.addFlashAttribute("userInfo", userInfo);
			ModelAndView mv = new ModelAndView("redirect:/homepage");
			return mv;
		} else {
			String message = "";
			if (userInfo.getUserStatus() == "RegistrationNotExist") {
				System.out.println("Please register first!");
				message = "Please register first!";
				redirectAttributes.addFlashAttribute("message", message);
			}
			else if (userInfo.getUserStatus() == "PasswordError") {
				System.out.println("Password is wrong!");
				message = "Password is wrong!";
				redirectAttributes.addFlashAttribute("message", message);
			}
			ModelAndView mv = new ModelAndView("redirect:/"); // use RedirectAttributes to redirect to controller '/'.
			return mv;
		}
	}
	
	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	@ResponseBody
	public String userRegistration(HttpServletRequest request) throws IOException {
		System.out.println("User Registration");
		
		/* Expect user registration request 
		 *
		 * 	{
		 * 		"firstname":"firstname",
		 * 		"lastname":"lastname",
		 * 		"email":"email",
		 * 		"username":"username",
		 * 		"password":"password"
		 * 	}
		 *
		 * */
		
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
	    // Parse request info
	    String username = requestbody_json.getString("username");
	    String email = requestbody_json.getString("email");
	    String firstname = requestbody_json.getString("firstname");
	    String lastname = requestbody_json.getString("lastname");
	    String password = requestbody_json.getString("password");
	    
	    User user = new User(firstname, lastname, email, username, password);
	    
	    Boolean registerResult;	    
	    Boolean emailIsExists = userManager.emailIsExists(email);
	    Boolean usernameIsExists = userManager.usernameIsExists(username);
	    // Check if username has been used or not
	    if (!emailIsExists) {
	    	if (!usernameIsExists) {
	    		// register user to the database	    	
		    	registerResult = userManager.registerUser(user);
	    	} else {
	    		registerResult = false;
	    	}	    	
	    } else {
	    	registerResult = false;
	    }
	    
	    // Return response result
	    JSONObject response_json = new JSONObject();
	    if (registerResult) {
	    	User userLoginInfo = userManager.userLogin(requestbody_json.getString("email"), requestbody_json.getString("password") );		    
		    if (userLoginInfo != null) {
		    	response_json
		    		.put("validation", "valid")
		    		.put("firstname", userLoginInfo.getFirstname())
		    		.put("lastname", userLoginInfo.getLastname())
		    		.put("email", userLoginInfo.getEmail())
		    		.put("username", userLoginInfo.getUsername());
		    } else {
		    	response_json.put("validation", "invalid");
		    }
	    } else {
	    	if (emailIsExists) {
	    			response_json
	    				.put("validation", "registration false")
	    				.put("response", "Email has been registered.");
			}
	    	else if (usernameIsExists) {
    			response_json
    				.put("validation", "registration false")
    				.put("response", "User name has been registered.");
	    	}
	    }	    
	    
	    return response_json.toString();
	}
	
}
