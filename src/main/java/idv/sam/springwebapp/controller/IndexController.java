package idv.sam.springwebapp.controller;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import idv.sam.springwebapp.model.User;
import idv.sam.springwebapp.model.UserLogin;

// Index page (Login)
@Controller
public class IndexController {
	@RequestMapping(value = "/staticpage", method = RequestMethod.GET)	
    public String staticrss() throws IOException {
		
		System.out.println("Welcome page");
		return "layout.htm";
    }
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index(
    		@ModelAttribute("message") String message,
    		@CookieValue(value = "loginUserEmail", defaultValue = "defaultCookieValue") String loginUserEmail,
    		@CookieValue(value = "loginUserPassword", defaultValue = "defaultCookieValue") String loginUserPassword) throws IOException {
		
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
		
		System.out.println("loginUserEmail: "+loginUserEmail);
		System.out.println("loginUserPassword: "+loginUserPassword);
		
		/* Validate GET Request*/
		if (userLoginInfo.getLoginStatus() == "VALID") {
			/* Return home page view */
			response.addCookie(new Cookie("loginUserEmail",userLoginInfo.getEmail()));
			response.addCookie(new Cookie("loginUserPassword",userLoginInfo.getPassword()));
			
			ModelAndView mv = new ModelAndView("userhomepage");
			mv.addObject("userLoginInfo", userLoginInfo);			
			return mv;
		} else {
			ModelAndView mv = new ModelAndView("index"); // target view
			mv.addObject("login", new UserLogin());
			mv.addObject("message", "Welcome back!!");
			return mv;
		}
	}
}