/**
 * 
 */
package com.razorthink.rzt.internal.management.controller;

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
	public String admin() {
		return "adminhome";
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

	
	@RequestMapping(value = "/header")
	public String header() {
		System.out.println("inside home controller userhome");
		return "header";
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

	@RequestMapping(value = "/employees")
	public String employee() {
		return "employees";
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
