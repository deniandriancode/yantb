package org.fam.blogger.service;

import java.util.Optional;

import org.fam.blogger.dto.BlogUserDto;
import org.fam.blogger.entity.BlogUser;

public interface BlogService {

	Optional<BlogUser> findUserByUsername(String username);

	Optional<BlogUser> findUserByEmail(String email);

	void saveUser(BlogUserDto blogUserDto);

}
