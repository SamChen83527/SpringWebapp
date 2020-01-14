package idv.sam.springwebapp.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import idv.sam.springwebapp.model.UserLogin;
import idv.sam.springwebapp.service.UserManager;

// Index page (Login)
@Controller
public class IndexController {	
	/* Service Bean */
	UserManager userManager;
	
	/* Constructor Dependency Injection */
	public IndexController(UserManager userManager) {
		this.userManager = userManager; 
	}
	
	/* return page */
	@RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index(
    		@ModelAttribute("message") String message) throws IOException {
		
		System.out.println("Welcome page");
		
        ModelAndView mv = new ModelAndView("index"); // target view
        mv.addObject("login", new UserLogin());
        if (message.isEmpty()) {
        	mv.addObject("message", "Welcome back!");
        } else {
        	mv.addObject("message", message);
        }
		return mv;
    }
	
	/* return page */
	@RequestMapping(value = "/homepage", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView userHomePage(
			HttpServletResponse response, 
			@ModelAttribute("userInfo") UserLogin userLoginInfo,
			@CookieValue(value = "loginUserEmail", defaultValue = "") String clientEmailCookie,
    		@CookieValue(value = "loginUsername", defaultValue = "") String clientUsernameCookie,
    		@CookieValue(value = "loginJWT", defaultValue = "") String clientJWTCookie) throws IOException {
		System.out.println("User Home Page");

		System.out.println("User name from login info: " + userLoginInfo.getUsername());

		System.out.println("User email cookie: " + clientEmailCookie);
		System.out.println("User name cookie: " + clientUsernameCookie);
		System.out.println("User jwt cookie: " + clientJWTCookie);
		
		/* Validate GET Request*/
		// from login page
		if (userLoginInfo.getLoginStatus() == "VALID") {
			System.out.println("VALID");
			
			// re-login
			if (clientUsernameCookie == userLoginInfo.getUsername()) {
				/* Return home page view */
				ModelAndView mv = new ModelAndView("userhomepage");
				mv.addObject("userLoginInfo", userLoginInfo);
				return mv;
			}
			
			else if (clientUsernameCookie == "" || clientUsernameCookie != userLoginInfo.getUsername()) {
				// generate jwt
				String jwt = userManager.createJWT(userLoginInfo.getUsername(), userLoginInfo.getEmail());
				
				// add cookie
				Cookie userEmailCookie = new Cookie("loginUserEmail", userLoginInfo.getEmail()); // bake cookie
				userEmailCookie.setMaxAge(10000); // set expire time to 1000 sec
				response.addCookie(userEmailCookie); // put cookie in response
				
				Cookie usernameCookie = new Cookie("loginUsername",  userLoginInfo.getUsername()); // bake cookie
				usernameCookie.setMaxAge(10000); // set expire time to 1000 sec
				response.addCookie(usernameCookie);
				
				Cookie jwtCookie = new Cookie("loginJWT", jwt); // bake cookie
				usernameCookie.setMaxAge(10000); // set expire time to 1000 sec
				response.addCookie(jwtCookie);
				
				/* Return home page view */
				ModelAndView mv = new ModelAndView("userhomepage");
				mv.addObject("userLoginInfo", userLoginInfo);
				return mv;
			} else {
				System.out.println("UNEXPECTED_INVALID");
				ModelAndView mv = new ModelAndView("index"); // target view
				mv.addObject("login", new UserLogin());
				mv.addObject("message", "Welcome back!!");
				return mv;
			}
		} 
		// from refresh-page or unexpected access
		else {
			// refresh page - login with JWT			
			if (clientJWTCookie!="") {
				// validate JWT
				Boolean validation = userManager.validateJWT(clientJWTCookie);
				if (validation) {
					// Client cookie is valid.
					System.out.println("Client cookie is valid.");
					/* Return home page view */
					ModelAndView mv = new ModelAndView("userhomepage");
					mv.addObject("userLoginInfo", userLoginInfo);
					return mv;
				} else {
					// Client cookie is invalid.
					System.out.println("Client cookie is invalid.");
					ModelAndView mv = new ModelAndView("index"); // target view
					mv.addObject("login", new UserLogin());
					mv.addObject("message", "Welcome back!!");
					return mv;
				}
			} else {
				// No cookie, login manually first.
				System.out.println("No cookie, login manually first.");
				ModelAndView mv = new ModelAndView("index"); // target view
				mv.addObject("login", new UserLogin());
				mv.addObject("message", "Welcome back!!");
				return mv;
			}
		}
	}
}