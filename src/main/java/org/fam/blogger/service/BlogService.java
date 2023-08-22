package org.fam.blogger.service;

import java.util.List;
import java.util.Optional;

import org.fam.blogger.dto.BlogPostDto;
import org.fam.blogger.dto.BlogUserDto;
import org.fam.blogger.entity.BlogPost;
import org.fam.blogger.entity.BlogUser;

// TODO create a unique slugifier function

public interface BlogService {

	Optional<BlogUser> findUserByUsername(String username);

	Optional<BlogUser> findUserByEmail(String email);

	void saveUser(BlogUserDto blogUserDto);

	void saveBlogPost(BlogPostDto blogPostDto);

	List<BlogPost> getAllBlogPostWithEmail(String email);

	BlogPost getBlogPostById(Long id);

	Optional<BlogUser> getCurrentLoggedInUser();

	boolean isLoggedIn();

}
