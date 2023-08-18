package org.fam.blogger.controller;

import org.fam.blogger.dto.BlogUserDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;

@Controller
public class BlogController {

	@GetMapping("/")
	public String homePage() {
		return "home";
	}

	@GetMapping("/login")
	public String loginFormGet(BlogUserDto blogUserDto, Model model) {
		model.addAttribute("error", false);
		return "login";
	}

	@GetMapping("/register")
	public String registerFormGet(BlogUserDto blogUserDto) {
		return "register";
	}

	@GetMapping("/dashboard")
	public String dashboardGet() {
		return "dashboard";
	}

	@PostMapping("/login")
	public String loginProcess(@Valid @ModelAttribute("blogUserDto") BlogUserDto blogUserDto,
			BindingResult bindingResult, Model model) {
		if (bindingResult.hasFieldErrors("username") || bindingResult.hasFieldErrors("password")) {
			model.addAttribute("error", true);
			return "login";
		}
		return "redirect:/";
	}

	@PostMapping("/register")
	public String registerProcess(@Valid @ModelAttribute("blogUserDto") BlogUserDto blogUserDto,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "register";
		}
		return "redirect:/";
	}

}
