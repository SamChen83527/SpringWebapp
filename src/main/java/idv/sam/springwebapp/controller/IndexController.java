package idv.sam.springwebapp.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import idv.sam.springwebapp.model.Login;
import idv.sam.springwebapp.model.User;

// Index page (Login)
@Controller
public class IndexController {
	@RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index(@ModelAttribute("message") String message) throws IOException {
		System.out.println("Welcome page");
		System.out.println("message:" + message.getClass() + ".");
        ModelAndView mv = new ModelAndView("index"); // target view
        mv.addObject("login", new Login());
        if (message.isEmpty()) {
        	mv.addObject("message", "Welcome back!");
        } else {
        	mv.addObject("message", message);
        }        
		return mv;
    }
	
	@RequestMapping(value = "/homepage", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView userHomePage(@ModelAttribute("userInfo") User userInfo) throws IOException {
		System.out.println("User Home Page");
		
		/* Validate GET Request*/
		if (userInfo.getUserStatus() == "valid") {
			/* Return home page view */		
			ModelAndView mv = new ModelAndView("userhomepage");
			mv.addObject("userInfo", userInfo);
			return mv;
		} else {
			ModelAndView mv = new ModelAndView("index"); // target view
			mv.addObject("message", "Welcome back!");
			return mv;
		}
	}
}