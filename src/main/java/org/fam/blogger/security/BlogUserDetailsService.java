package org.fam.blogger.security;

import java.util.Optional;
import java.util.stream.Collectors;

import org.fam.blogger.entity.BlogUser;
import org.fam.blogger.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class BlogUserDetailsService implements UserDetailsService {

	@Autowired
	BlogService blogService;

	@Override
	public UserDetails loadUserByUsername(String usernameOrEmail)
			throws UsernameNotFoundException { // here I use email actually
		Optional<BlogUser> checkBlogUser = blogService.findUserByEmail(usernameOrEmail);
		if (checkBlogUser.isEmpty()) {
			throw new UsernameNotFoundException("Invalid username or password");
		}

		BlogUser blogUser = checkBlogUser.get();

		return new User(
				blogUser.getEmail(),
				blogUser.getPassword(),
				blogUser.getBlogRoles().stream()
						.map((role) -> new SimpleGrantedAuthority(role.getRole()))
						.collect(Collectors.toList()));
	}

}
