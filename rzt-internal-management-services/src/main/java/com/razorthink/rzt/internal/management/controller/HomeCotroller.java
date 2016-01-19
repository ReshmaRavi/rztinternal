package com.razorthink.rzt.internal.management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeCotroller {
	
	@RequestMapping("/")
	public ModelAndView home() {
		System.out.println("Welocme...");
		return new ModelAndView("role");
	}
	
	@RequestMapping("/role")
	public ModelAndView role() {
		System.out.println("role");
		return new ModelAndView("role"); 	
	}		
}
