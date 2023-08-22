package org.fam.blogger.util;

import com.github.slugify.Slugify;

import org.springframework.stereotype.Component;

@Component
public class Slug {

	private Slugify slg;

	public Slug() {
		slg = Slugify.builder().build();
	}

	public String slugify(String title) {
	    return slg.slugify(title);
	}

	public Slugify getSlg() {
		return slg;
	}

	public void setSlg(Slugify slg) {
		this.slg = slg;
	}

}
