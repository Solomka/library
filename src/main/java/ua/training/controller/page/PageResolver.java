package ua.training.controller.page;

public class PageResolver {
	
	private final String suffix;
	private final String prefix;

	public PageResolver(String prefix, String suffix) {
		this.suffix = suffix;
		this.prefix = prefix;
	}

	public String resolve(String middlePartOfPageName) {
		return prefix + middlePartOfPageName + suffix;
	}
}
