package ua.training.controller.filter;

import java.util.Locale;

public enum AppLocale {
	EN(new Locale("en", "US")), UK(new Locale("uk", "UA"));

	private final static Locale DEFAULT_LOCALE = EN.getLocale();

	private Locale locale;

	AppLocale(Locale locale) {
		this.locale = locale;
	}

	public Locale getLocale() {
		return locale;
	}

	public static Locale getDefault() {
		return DEFAULT_LOCALE;
	}

	/**
	 * Return Locale type for a given language value
	 * 
	 * @param language
	 * @return
	 */

	public static Locale forValue(String language) {
		for (final AppLocale locale : AppLocale.values()) {
			if (locale.getLocale().getLanguage().equalsIgnoreCase(language)) {
				return locale.getLocale();
			}
		}
		return getDefault();
	}

}
