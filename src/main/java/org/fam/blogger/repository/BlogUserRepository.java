package org.fam.blogger.repository;

import java.util.Optional;

import org.fam.blogger.entity.BlogUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogUserRepository extends JpaRepository<BlogUser, Long> {

	Optional<BlogUser> findByEmail(String email);

	Optional<BlogUser> findByUsername(String username);

}
