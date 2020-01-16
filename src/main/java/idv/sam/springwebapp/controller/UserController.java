package idv.sam.springwebapp.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import idv.sam.springwebapp.model.UserLogin;
import idv.sam.springwebapp.model.UserRegistation;
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
	/* return page */
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView enterRegistration() throws IOException {
		System.out.println("User Registration");
		
		ModelAndView mv = new ModelAndView("registration");
		mv.addObject("user_registration", new UserRegistation());
		return mv;
	}
	
	/* return page */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView userLogout(
			HttpServletRequest request, 
			HttpServletResponse response,
			RedirectAttributes redirectAttributes) throws IOException {
		System.out.println("User Logout");

		// login
		redirectAttributes.addFlashAttribute("message", "Welcome back!");
		redirectAttributes.addFlashAttribute("login", false);
		ModelAndView mv = new ModelAndView("redirect:/");
		return mv;
	}
	
	/* POST */
	@RequestMapping(value = "/login", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public ModelAndView userLogin(
			HttpServletRequest request, 
			HttpServletResponse response, 
			RedirectAttributes redirectAttributes, 
			@ModelAttribute("login") UserLogin login) throws IOException {
		System.out.println("User Login");
		
		/* User validation */
		UserLogin userLoginInfo = userManager.userLogin(login.getEmail(), login.getPassword());
		
		// Login valid
		if (userLoginInfo.getLoginStatus() == "VALID") {
			System.out.println("Login successful");
			
			/* Redirect to home view */
			redirectAttributes.addFlashAttribute("userInfo", userLoginInfo);
			ModelAndView mv = new ModelAndView("redirect:/home");
			return mv;
		}
		
		// Login invalid
		else {
			String message = "";
			if (userLoginInfo.getLoginStatus() == "INVALID_REGISTRATION_NOTEXIST") {
				System.out.println("Sorry! Please register first!");
				message = "Please register first!";
				redirectAttributes.addFlashAttribute("message", message);
			}
			else if (userLoginInfo.getLoginStatus() == "INVALID_PASSWORD_ERROR") {
				System.out.println("Sorry! The password is wrong!");
				message = "Password is wrong!";
				redirectAttributes.addFlashAttribute("message", message);
			}
			ModelAndView mv = new ModelAndView("redirect:/"); // use RedirectAttributes to redirect to controller '/'.
			return mv;
		}
	}
	
	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView userRegistration(
			HttpServletRequest request, 
			RedirectAttributes redirectAttributes,
			@ModelAttribute("user_registration") UserRegistation user_registration) throws IOException {
		System.out.println("User Registration");
		
	    String username = user_registration.getUsername();
	    String email = user_registration.getEmail();
	    String password = user_registration.getPassword();
	    String firstname = user_registration.getFirstname();
	    String lastname = user_registration.getLastname();	    
	    
	    Boolean emailIsExists = userManager.emailIsExists(email);
	    Boolean usernameIsExists = userManager.usernameIsExists(username);
	    // Check if user name has been used or not
	    if (!emailIsExists) {
	    	System.out.println("emailIsExists: False");
	    	if (!usernameIsExists) {
	    		// register user to the database
	    		User user = new User(firstname, lastname, email, username, password);
		    	userManager.registerUser(user);
		    	user_registration.setRegistationStatus("VALID");
		    	System.out.println("Register successful");
		    	
		    	// login
		    	UserLogin userLoginInfo = new UserLogin(email, password);
				redirectAttributes.addFlashAttribute("login", userLoginInfo);
				ModelAndView mv = new ModelAndView("redirect:/user/login");
				return mv;
	    	} else {
	    		System.out.println("User name has been used!");
	    		user_registration.setRegistationStatus("INVALID_USERNAME_EXISTS");
	    		
	    		// redirect	
				ModelAndView mv = new ModelAndView("redirect:/user/register");
				return mv;
	    	}
	    } else {
	    	System.out.println("emailIsExists: True");
	    	user_registration.setRegistationStatus("INVALID_EMAIL_EXISTS");
	    	
	    	// redirect	
			ModelAndView mv = new ModelAndView("redirect:/user/register");
			return mv;
	    }
	}
	
}
