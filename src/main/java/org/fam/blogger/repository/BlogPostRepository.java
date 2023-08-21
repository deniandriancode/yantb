package org.fam.blogger.repository;

import java.util.List;

import org.fam.blogger.entity.BlogPost;
import org.fam.blogger.entity.BlogUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {

	List<BlogPost> findByAuthor(BlogUser author);
    
}
