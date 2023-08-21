package org.fam.blogger.controller;

import java.util.Optional;

import org.fam.blogger.dto.BlogUserDto;
import org.fam.blogger.entity.BlogUser;
import org.fam.blogger.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;

@Controller
public class BlogController {

	@Autowired
	BlogService blogService;

	@GetMapping("/")
	public String homePage() {
		return "home";
	}

	@GetMapping("/login")
	public String loginFormGet(BlogUserDto blogUserDto) {
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

	@PostMapping("/register")
	public String registerProcess(@Valid @ModelAttribute("blogUserDto") BlogUserDto blogUserDto,
			BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "register";
		}

		Optional<BlogUser> existingUser = blogService.findUserByEmail(blogUserDto.getEmail());
		if (existingUser.isPresent()) {
			model.addAttribute("userExistsError", true);
			return "register";
		}

		blogService.saveUser(blogUserDto);

		return "redirect:/";
	}

}
