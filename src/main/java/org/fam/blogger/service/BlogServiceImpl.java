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
import org.springframework.beans.factory.annotation.Autowired;
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
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		// if (!(authentication instanceof AnonymousAuthenticationToken)) {
		String currentUsername = authentication.getName();
		Optional<BlogUser> blogUser = findUserByEmail(currentUsername);
		BlogPost newBlogPost = new BlogPost(
				blogPostDto.getTitle(),
				blogPostDto.getContent(),
				blogUser.get());
		blogPostRepository.save(newBlogPost);
		// }
	}

	@Override
	public List<BlogPost> getAllBlogPostWithEmail(String email) {
		BlogUser author = findUserByEmail(email).get();
		List<BlogPost> blogPostList = blogPostRepository.findByAuthor(author);
		return blogPostList;
	}

}
