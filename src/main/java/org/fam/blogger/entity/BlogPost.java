package org.fam.blogger.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "blog_posts")
public class BlogPost {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	private String title;

	@NotEmpty
	private String slugTitle;

	@NotEmpty
	@Column(columnDefinition = "TEXT")
	private String content;

	@ManyToOne
	@JoinColumn(name = "author_id")
	private BlogUser author;

	public BlogPost() {

	}

	public BlogPost(String title, String slugTitle, String content, BlogUser author) {
		this.slugTitle = slugTitle;
		this.title = title;
		this.content = content;
		this.author = author;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public BlogUser getAuthor() {
		return author;
	}

	public void setAuthor(BlogUser author) {
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSlugTitle() {
		return slugTitle;
	}

	public void setSlugTitle(String slugTitle) {
		this.slugTitle = slugTitle;
	}

}
