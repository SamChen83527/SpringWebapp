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

// Index page (Login)
@Controller
public class IndexController {
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
	
	@RequestMapping(value = "/homepage", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView userHomePage(
			HttpServletResponse response, 
			@ModelAttribute("userInfo") UserLogin userLoginInfo,
			@CookieValue(value = "loginUserEmail", defaultValue = "defaultCookieValue") String loginUserEmail,
    		@CookieValue(value = "loginUserPassword", defaultValue = "defaultCookieValue") String loginUserPassword) throws IOException {
		System.out.println("User Home Page");
		
		/* Validate GET Request*/
		if (userLoginInfo.getLoginStatus() == "VALID") {
			System.out.println("VALID");
			/* Return home page view */
			response.addCookie(new Cookie("loginUserEmail",userLoginInfo.getEmail()));
			response.addCookie(new Cookie("loginUserPassword",userLoginInfo.getPassword()));
			
			ModelAndView mv = new ModelAndView("userhomepage");
			mv.addObject("userLoginInfo", userLoginInfo);
			return mv;
		} else {
			System.out.println("INVALID");
			ModelAndView mv = new ModelAndView("index"); // target view
			mv.addObject("login", new UserLogin());
			mv.addObject("message", "Welcome back!!");
			return mv;
		}
	}
}