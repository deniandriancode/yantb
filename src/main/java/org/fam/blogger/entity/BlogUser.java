package org.fam.blogger.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;

@Entity
@Table(name = "blog_users")
public class BlogUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    @Column(nullable = false, length = 50, unique = true)
	private String username;

	@Column(nullable = false, length = 50, unique = true)
	@Email
	private String email;

	@Column(nullable = false, length = 300)
	private String password;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "blog_users_roles_tbl", joinColumns = {
			@JoinColumn(name = "blog_user_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "blog_role_id", referencedColumnName = "id") })
	private List<BlogRole> blogRoles = new ArrayList<>();

	@OneToMany(mappedBy = "author")
	private List<BlogPost> blogPosts = new ArrayList<>();

	public BlogUser() {

	}

	public BlogUser(String username, String email, String password, List<BlogRole> blogRoles) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.blogRoles = blogRoles;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<BlogRole> getBlogRoles() {
		return blogRoles;
	}

	public void setBlogRoles(List<BlogRole> blogRoles) {
		this.blogRoles = blogRoles;
	}

	public List<BlogPost> getBlogPosts() {
		return blogPosts;
	}

	public void setBlogPosts(List<BlogPost> blogPosts) {
		this.blogPosts = blogPosts;
	}    

}
