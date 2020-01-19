package idv.sam.springwebapp.controller;

import java.io.IOException;
import java.util.Map;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

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
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index_ctrl(
    		HttpServletRequest request,
    		HttpServletResponse response,
    		RedirectAttributes redirectAttributes,
    		@CookieValue(value = "loginJWT", defaultValue = "") String clientJWTCookie) throws IOException {
		
		System.out.println("Welcome page");
		/*
		 * Check cookie to determine return login view or homepage view
		 * * If JWT is invalid	--> index(login) view
		 * * If JWT is valid	--> hompage view
		 */		
		Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
		
		// from direct link
		if (inputFlashMap == null) {
	    	// Client cookie is valid.
			if (clientJWTCookie!=""  && userManager.validateJWT(clientJWTCookie)) {
				System.out.println("Client cookie is valid.");
				
				// Get user info with JWT
				UserLogin userLoginInfo = userManager.getUserByJWT(clientJWTCookie);			
				
				/* Redirect to home view */
				redirectAttributes.addFlashAttribute("userInfo", userLoginInfo);
				ModelAndView mv = new ModelAndView("redirect:/home");
				return mv;
			}
			
			// Client cookie is invalid, login manually first.
			else {
				System.out.println("Client cookie is invalid, login manually first.");
				ModelAndView mv = new ModelAndView("redirect:/user/login");
				return mv;
			}
		}
		
		// from login-failed ctrl redirect
		else {
			System.out.println("Client access direct link.");
			
			String message = (String) inputFlashMap.get("message");
			ModelAndView mv = new ModelAndView("redirect:/user/login");
			redirectAttributes.addFlashAttribute("message", message);
			return mv;
		}
    }
	
	/* return page */
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView userHomePage(
			HttpServletResponse response, 
			@ModelAttribute("userInfo") UserLogin userLoginInfo,
			@CookieValue(value = "loginUserEmail", defaultValue = "") String clientEmailCookie,
    		@CookieValue(value = "loginUsername", defaultValue = "") String clientUsernameCookie,
    		@CookieValue(value = "loginJWT", defaultValue = "") String clientJWTCookie) throws IOException {
		System.out.println("User Home Page");

		// from login page, create jwt cookie
		if (clientJWTCookie.isEmpty()) {
			System.out.println("Assign JWT cookie");
			
			// generate jwt
			String jwt = userManager.createJWT(userLoginInfo.getUsername(), userLoginInfo.getEmail());
			
			// add cookie
			Cookie userEmailCookie = new Cookie("loginUserEmail", userLoginInfo.getEmail()); // bake cookie
			userEmailCookie.setMaxAge(1000); // set expire time to 1000 sec
			response.addCookie(userEmailCookie); // put cookie in response
			
			Cookie usernameCookie = new Cookie("loginUsername",  userLoginInfo.getUsername()); // bake cookie
			usernameCookie.setMaxAge(1000); // set expire time to 1000 sec
			response.addCookie(usernameCookie);
			
			Cookie jwtCookie = new Cookie("loginJWT", jwt); // bake cookie
			usernameCookie.setMaxAge(1000); // set expire time to 1000 sec
			response.addCookie(jwtCookie);
			
			/* Return home page view */
			ModelAndView mv = new ModelAndView("home_page");
			mv.addObject("userLoginInfo", userLoginInfo);
			return mv;
		} 
		
		// from direct link or refresh, validate cookie
		else {
			// refresh page - login with JWT
			if (clientJWTCookie!="") {
				// validate JWT
				Boolean validation = userManager.validateJWT(clientJWTCookie);
				
				// Client cookie is valid.
				if (validation) {
					System.out.println("Client cookie is valid.");
					
					// TO-DO: get user
					
					/* Return home page view */
					ModelAndView mv = new ModelAndView("home_page");
					mv.addObject("userLoginInfo", userLoginInfo);
					return mv;
				} 
				
				// Client cookie is invalid.
				else {					
					System.out.println("Client cookie is invalid.");
					ModelAndView mv = new ModelAndView("redirect:/user/login");
					return mv;
				}
			} 
			
			// has no cookie, login manually first.
			else {
				System.out.println("No cookie, login manually first.");
				ModelAndView mv = new ModelAndView("redirect:/user/login");
				return mv;
			}
			
		}
	}
}