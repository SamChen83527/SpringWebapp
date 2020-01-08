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
	@RequestMapping(value = "/getuserdevice", method = RequestMethod.GET)
    public void getUserDevice() throws IOException {
		System.out.println("Get user device info");
		
    }
	
	@RequestMapping(value = "/getobservations", method = RequestMethod.GET)
	@ResponseBody
	public void getObservations() throws IOException {
		System.out.println("Get observations");
		
	}
	
	@RequestMapping(value = "/getlatestobservation", method = RequestMethod.GET)
	@ResponseBody
	public void getLatestObservation() throws IOException {
		System.out.println("Get latest observation");
		
	}
}