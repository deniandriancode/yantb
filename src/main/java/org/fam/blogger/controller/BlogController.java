package org.fam.blogger.controller;

import java.util.List;
import java.util.Optional;

import org.fam.blogger.dto.BlogPostDto;
import org.fam.blogger.dto.BlogUserDto;
import org.fam.blogger.entity.BlogPost;
import org.fam.blogger.entity.BlogUser;
import org.fam.blogger.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;

@Controller
public class BlogController {

	@Autowired
	BlogService blogService;

	@GetMapping("/")
	public String homePage() {
		return blogService.isLoggedIn() ? "home-auth" : "home";
	}

	@GetMapping("/login")
	public String loginFormGet(BlogUserDto blogUserDto) {
		return blogService.isLoggedIn() ? "redirect:/dashboard" : "login";
	}

	@GetMapping("/register")
	public String registerFormGet(BlogUserDto blogUserDto) {
		return blogService.isLoggedIn() ? "redirect:/dashboard" : "register";
	}

	@GetMapping("/dashboard")
	public String dashboardGet(Model model) {
		BlogUser blogUser = blogService.getCurrentLoggedInUser().get();
		List<BlogPost> blogPostList = blogUser.getBlogPosts();
		model.addAttribute("blogs", blogPostList);
		model.addAttribute("username", blogUser.getUsername());
		return "dashboard";
	}

	@GetMapping("/blog/new")
	public String userNewBlogForm(BlogPostDto blogPostDto) {
		return "user-blog-new";
	}

	@GetMapping("/blog/{username}/post/{slug}")
	public String getBlogPostDetail(@PathVariable("username") String username, @PathVariable("slug") String slug,
			Model model) {
		if (blogService.isLoggedIn())
			model.addAttribute("loggedIn", true);

		Optional<BlogUser> blogAuthor = blogService.findUserByUsername(username);
		List<BlogPost> blogPostList = blogAuthor.get().getBlogPosts();
		BlogPost blogPost = new BlogPost();
		for (BlogPost b : blogPostList) {
			if (b.getSlugTitle().equals(slug)) {
				blogPost = b;
				break;
			}
		}
		model.addAttribute("blog", blogPost);
		return "blog-post";
	}

	@GetMapping("/blog/update/{id}")
	public String getBlogUpdateForm(@PathVariable("id") Long id, Model model) {
		return "user-blog-update";
	}

	@PostMapping("/register")
	public String registerProcess(@Valid @ModelAttribute("blogUserDto") BlogUserDto blogUserDto,
			BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "register";
		}

		Optional<BlogUser> existingUser = blogService.findUserByUsername(blogUserDto.getUsername());
		if (existingUser.isPresent()) {
			model.addAttribute("userExistsError", true);
			return "register";
		}

		blogService.saveUser(blogUserDto);

		return "redirect:/login";
	}

	@PostMapping("/blog/new")
	public String postNewBlogpost(@Valid @ModelAttribute("blogPostDto") BlogPostDto blogPostDto,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "user-blog-new";
		}

		blogService.saveBlogPost(blogPostDto);

		return "redirect:/dashboard";
	}

}
