package br.com.jopaulofood.util;

import java.util.Collection;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

public class StringUtils {

	public static boolean isEmpty(String str) {
		if (str == null) {
			return true;
		}		
		return str.trim().length() == 0;
	}
	
	public static String encrypt(String rawString) {
		if (isEmpty(rawString)) {
			return null;
		}		
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		return encoder.encode(rawString);
	}
	
	public static String concatenar(Collection<String> strings) {
		if (strings == null || strings.size() == 0) {
			return null;
		}
		
		StringBuilder builder = new StringBuilder();
		String delimiter = ", ";
		boolean first = true;
		
		for (String string : strings) {
			if (!first) {
				builder.append(delimiter);
			}
			builder.append(string);
			first = false;
		}
		
		return builder.toString();
	}
}
