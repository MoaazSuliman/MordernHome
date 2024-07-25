package com.moaaz.modernhome.repository;

public class CriteriaService {
	private static final String SEARCH = "%";

	public static String getSearchText(String string) {
		return SEARCH + string.toLowerCase() + SEARCH;
	}
}
