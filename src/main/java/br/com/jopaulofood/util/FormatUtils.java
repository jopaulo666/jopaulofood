package br.com.jopaulofood.util;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class FormatUtils {
	
	private static final Locale LOCALE_BRAZIL = new Locale("pt", "BR");

	public static NumberFormat newCurrencyFormat() {
		NumberFormat format = NumberFormat.getNumberInstance(LOCALE_BRAZIL);
		format.setMaximumFractionDigits(2);
		format.setMinimumFractionDigits(2);
		format.setGroupingUsed(false);
		return format;
	}
	
	public static String formatCurrency(BigDecimal number) {
		if (number == null) {
			return null;
		}
		return newCurrencyFormat().format(number);
	}
}
