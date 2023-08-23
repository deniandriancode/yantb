package org.fam.blogger.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.fam.blogger.constant.BlogConstant;
import org.fam.blogger.dto.BlogPostDto;
import org.fam.blogger.dto.BlogUserDto;
import org.fam.blogger.entity.BlogPost;
import org.fam.blogger.entity.BlogRole;
import org.fam.blogger.entity.BlogUser;
import org.fam.blogger.repository.BlogPostRepository;
import org.fam.blogger.repository.BlogRoleRepository;
import org.fam.blogger.repository.BlogUserRepository;
import org.fam.blogger.util.Slug;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class BlogServiceImpl implements BlogService {

	@Autowired
	BlogUserRepository blogUserRepository;

	@Autowired
	BlogRoleRepository blogRoleRepository;

	@Autowired
	BlogPostRepository blogPostRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	Slug slug;

	@Override
	public Optional<BlogUser> findUserByEmail(String email) {
		return blogUserRepository.findByEmail(email);
	}

	@Override
	public Optional<BlogUser> findUserByUsername(String username) {
		return blogUserRepository.findByUsername(username);
	}

	@Override
	public void saveUser(BlogUserDto blogUserDto) {
		Optional<BlogRole> existingBlogRole = blogRoleRepository.findByRole(BlogConstant.Roles.USER);
		BlogRole retrievedBlogRole = null;
		if (existingBlogRole.isEmpty()) {
			retrievedBlogRole = blogRoleRepository.save(new BlogRole(BlogConstant.Roles.USER));
		} else {
			retrievedBlogRole = existingBlogRole.get();
		}
		blogUserRepository.save(new BlogUser(
				blogUserDto.getUsername(),
				blogUserDto.getEmail(),
				passwordEncoder.encode(blogUserDto.getPassword()),
				Arrays.asList(retrievedBlogRole)));

	}

	@Override
	public void saveBlogPost(BlogPostDto blogPostDto) {
		Optional<BlogUser> blogUser = getCurrentLoggedInUser();
		BlogPost newBlogPost = new BlogPost(
				blogPostDto.getTitle(),
				slug.slugify(blogPostDto.getTitle()),
				blogPostDto.getContent(),
				blogUser.get());
		blogPostRepository.save(newBlogPost);
	}

	@Override
	public List<BlogPost> getAllBlogPostWithEmail(String email) {
		BlogUser author = findUserByEmail(email).get();
		List<BlogPost> blogPostList = blogPostRepository.findByAuthor(author);
		return blogPostList;
	}

	@Override
	public BlogPost getBlogPostById(Long id) {
		Optional<BlogPost> postRetrieved = blogPostRepository.findById(id);
		return postRetrieved.orElseGet(() -> new BlogPost());
	}

	@Override
	public BlogPost getBlogPostBySlugTitle(String slugTitle) {
		return blogPostRepository.findBySlugTitle(slugTitle).get();
	}

	@Override
	public Optional<BlogUser> getCurrentLoggedInUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication instanceof AnonymousAuthenticationToken)
			return Optional.empty();

		String username = authentication.getName();
		Optional<BlogUser> currentLoggedInUser = findUserByUsername(username);
		return currentLoggedInUser;
	}

	@Override
	public boolean isLoggedIn() {
		return getCurrentLoggedInUser().isPresent();
	}

}
