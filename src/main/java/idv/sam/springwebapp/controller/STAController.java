package idv.sam.springwebapp.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import idv.sam.springwebapp.model.User;
import idv.sam.springwebapp.model.UserLogin;

// Index page (Login)
@Controller
@RequestMapping("/data")
public class STAController {
	@RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index(@ModelAttribute("message") String message) throws IOException {
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
	public ModelAndView userHomePage(@ModelAttribute("userInfo") UserLogin userLoginInfo) throws IOException {
		System.out.println("User Home Page");
		
		/* Validate GET Request*/
		if (userLoginInfo.getLoginStatus() == "VALID") {
			/* Return home page view */
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