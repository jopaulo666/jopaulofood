package br.com.jopaulofood.infrastructure.web.converter;

import java.math.BigDecimal;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import br.com.jopaulofood.util.FormatUtils;
import br.com.jopaulofood.util.StringUtils;

@Component
public class StringToBigDecimalConverter implements Converter<String, BigDecimal> {

	@Override
	public BigDecimal convert(String source) {
		if (StringUtils.isEmpty(source)) {
			return null;
		}
		
		source = source.replace(",", ".");
		return BigDecimal.valueOf(Double.valueOf(source));
	}

	

}
