package idv.sam.springwebapp.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import idv.sam.springwebapp.model.User;
import idv.sam.springwebapp.service.UserManager;

@Controller
@RequestMapping("/example")
public class ExampleController {
	/* Service Bean */
	UserManager userManager;	
	/* Constructor Dependency Injection */
	public ExampleController(UserManager userManager) {
		this.userManager = userManager; 
	}
	
	@RequestMapping(value="/index", method=RequestMethod.GET)
	public ModelAndView examplePage() {
		System.out.println("Get example page");
		ModelAndView mv = new ModelAndView("example/example_index"); 			// target view
		return mv;
	}
	
	/* GET Example */	
	// GET with ModelAndView	
	@RequestMapping("/getalluser/ModelAndView")
	public ModelAndView getAllUserWithMV(
			@RequestParam(value = "name", required = false, defaultValue = "all") String name) {
		
		System.out.println("Get all users");
		
		List<User> users = userManager.getAllUser();
 
		ModelAndView mv = new ModelAndView("usergetall"); 			// target view
		mv.addObject("users", users);
		return mv;
	}

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
	
	// GET with Model	
	@RequestMapping(value="/getalluser/Model", method=RequestMethod.GET)
	public String getAllUserWithM(
			@RequestParam(value = "name", required = false) String name,
			Model model) {
		System.out.println("Get all users");
		
		List<User> users = userManager.getAllUser();
		
		model.addAttribute("users", users);
		return "usergetall";										// target view
	}
	
	/* POST */
	// Use IOUtils
	@RequestMapping(value = "/getuserbyfullname/IOUtils", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView getUserByFullName_POST1(HttpServletRequest request, Model model) throws IOException {
		System.out.println("Get user by full name with POST");
		
		@SuppressWarnings("deprecation")
		final String json = IOUtils.toString(request.getInputStream());
	    System.out.println("json = " + json);
	    
	    JSONObject jsonstring = new JSONObject(json);
	    User user = userManager.getUserByFullname(jsonstring.getString("firstname"), jsonstring.getString("lastname"));
	    
	    ModelAndView mv = new ModelAndView("usergetbyfullname"); 	// target view
		mv.addObject("user", user);
		return mv;
	}
	
	/* Use BufferedReader */
	@RequestMapping(value = "/getuserbyfullname/BufferedReader", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView getUserByFullName_POST2(HttpServletRequest request) throws IOException {
		System.out.println("Get user by full name with POST");
		
		BufferedReader reader = request.getReader();
	    StringBuilder builder = new StringBuilder();
	    String line = reader.readLine();
	    while (line != null) {
	        builder.append(line);
	        line = reader.readLine();
	    }
	    reader.close();
	    
	    String requestbody = builder.toString();
	    System.out.println("Urequestbodyser = " + requestbody);
	    
	    JSONObject ujson = new JSONObject(requestbody);	    
	    User user = userManager.getUserByFullname(ujson.getString("firstname"), ujson.getString("lastname"));
	    
	    ModelAndView mv = new ModelAndView("usergetbyfullname"); 	// target view
		mv.addObject("user", user);
		return mv;
	}
	
	
}
