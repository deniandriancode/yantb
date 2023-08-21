package org.fam.blogger.util;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.stereotype.Component;

@Component
public class Markdown {

	Parser parser;
	HtmlRenderer htmlRenderer;

	public Markdown() {
		parser = Parser.builder().build();
		htmlRenderer = HtmlRenderer.builder().build();
	}

	public String render(String rawContent) {
		Node document = parser.parse(rawContent);
		return htmlRenderer.render(document);
	}

	public String renderNoNewline(String rawContent) {
		Node document = parser.parse(rawContent);
		return htmlRenderer.render(document).trim();
	}

}
