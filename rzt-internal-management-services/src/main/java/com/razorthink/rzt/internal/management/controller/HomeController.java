/**
 * 
 */
package com.razorthink.rzt.internal.management.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

	@RequestMapping(value = "/")
	public String home() {
		System.out.println("\n in home controller");
		return "index";
	}
	
	@RequestMapping(value = "/admin")
	public ModelAndView admin(@RequestParam String name) {
		System.out.println("controller name=="+name);
		ModelAndView mv = new ModelAndView("adminhome");
		mv.addObject("username",name);
		System.out.println("controller after name=="+name);
		return mv;
	}
	
	@RequestMapping(value = "/noSession")
	public String noSession() {
		return "index";
	}

	@RequestMapping(value = "/loginHome")
	public String login() {
		return "home";
	}
	@RequestMapping(value = "/userHome")
	public String userhome() {
		System.out.println("inside home controller userhome");
		return "userhome";
	}

	@RequestMapping(value = "/errorLogin")
	public ModelAndView errorLogin(@RequestParam String Status) {
		ModelAndView mv = new ModelAndView("index");
		if (Status.equals("error")) {
			mv.addObject("Status", "error");
		}
		return mv;

	}

	@RequestMapping(value = "/client")
	public String client() {
		return "client";
	}
	@RequestMapping(value = "/project")
	public String project() {
		return "project";
	}

	@RequestMapping(value = "/employee")
	public String employee() {
		return "employee";
	}

	@RequestMapping(value = "/designation")
	public String designation() {
		return "designation";
	}

	@RequestMapping(value = "/role")
	public String role() {
		return "role";
	}

	@RequestMapping(value = "/users")
	public String users() {
		return "users";
	}

	
	@RequestMapping(value = "/test")
	public String test() {
		return "test";
	}

}
