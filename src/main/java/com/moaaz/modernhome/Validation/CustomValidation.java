package com.moaaz.modernhome.Validation;

public class CustomValidation {
	public static boolean nullChecker(Object object) {
		if (object == null)
			return false;
		if (object instanceof String string) {
			return !string.isEmpty() && !string.isBlank();
		}
		return true;
	}
}
