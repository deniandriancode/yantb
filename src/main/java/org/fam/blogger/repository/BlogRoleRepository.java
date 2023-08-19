package org.fam.blogger.repository;

import java.util.Optional;

import org.fam.blogger.entity.BlogRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogRoleRepository extends JpaRepository<BlogRole, Long> {

	Optional<BlogRole> findByRole(String role);

}
