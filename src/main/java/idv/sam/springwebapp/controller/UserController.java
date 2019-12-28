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
	@RequestMapping(value="/getuserbyfullname", method=RequestMethod.GET)
	public ModelAndView getByFullname(
			@RequestParam(value = "firstname", required = true) String fname,
			@RequestParam(value = "lastname", required = true) String lname) {
		System.out.println("Get user by full name");
		
		User user = userManager.getUserByFullname(fname, lname);
		
		ModelAndView mv = new ModelAndView("usergetbyfullname"); // target view
		mv.addObject("user", user);
		return mv;
	}
	
	/* POST */	
	/* Use BufferedReader */
	@RequestMapping(value = "/userlogin", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView userLogin(HttpServletRequest request) throws IOException {
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
	    Boolean validation = userManager.validateUser(requestbody_json.getString("username"), requestbody_json.getString("password") );
	    String resp;
	    if (validation == true) {
	    	resp = "Welcome back.";
	    } else {
	    	resp = "Sorry! Please register first.";
	    }
	    ModelAndView mv = new ModelAndView("usergetbyfullname"); 	// target view
		mv.addObject(resp);
		return mv;
	}
	
	
}
