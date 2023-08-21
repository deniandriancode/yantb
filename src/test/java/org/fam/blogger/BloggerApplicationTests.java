package org.fam.blogger;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.fam.blogger.util.Markdown;
import org.junit.jupiter.api.Test;

class BloggerApplicationTests {

	@Test
	void renderParagraph_Test() {
		Markdown markdown = new Markdown();
		String result = markdown.render("hello");
		assertEquals("<p>hello</p>\n", result);
	}

	@Test
	void renderParagraphWithoutNewLine_Test() {
		Markdown markdown = new Markdown();
		String result = markdown.renderNoNewline("hello");
		assertEquals("<p>hello</p>", result);
	}

	@Test
	void renderLink_Test() {
		Markdown markdown = new Markdown();
		String result = markdown.render("[Display Text](/reflink)");
		assertEquals("<p><a href=\"/reflink\">Display Text</a></p>\n", result);
	}

	@Test
	void renderImage_Test() {
		Markdown markdown = new Markdown();
		String result = markdown.render("![alt text](/img-url.png)");
		assertEquals("<p><img src=\"/img-url.png\" alt=\"alt text\" /></p>\n", result);
	}

}
