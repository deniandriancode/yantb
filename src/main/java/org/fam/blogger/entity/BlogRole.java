package org.fam.blogger.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "blog_roles")
public class BlogRole {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String role;

	@ManyToMany(mappedBy = "blogRoles")
	List<BlogUser> blogUsers = new ArrayList<>();

	public BlogRole() {

	}

	public BlogRole(String role) {
		this.role = role;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public List<BlogUser> getBlogUsers() {
		return blogUsers;
	}

	public void setBlogUsers(List<BlogUser> blogUsers) {
		this.blogUsers = blogUsers;
	}

}
