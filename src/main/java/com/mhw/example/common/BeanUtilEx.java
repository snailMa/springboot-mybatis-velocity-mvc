package com.mhw.example.common;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.apache.commons.beanutils.converters.SqlDateConverter;

public class BeanUtilEx extends BeanUtils {

	private static final BigDecimal BIGDECIMAL_ZERO = new BigDecimal("0");

	static {
		// 注册sql.date的转换器，即允许BeanUtils.copyProperties时的源目标的sql类型的值允许为空
		ConvertUtils.register(new SqlDateConverter(), java.util.Date.class);
		// ConvertUtils.register(new SqlTimestampConverter(),
		// java.sql.Timestamp.class);
		// 注册util.date的转换器，转化到date的时候将调用这个转化器
		ConvertUtils.register(new UtilDateConverter(), java.util.Date.class);
		// 这里一定要注册默认值，使用null也可以
		BigDecimalConverter bd = new BigDecimalConverter(BIGDECIMAL_ZERO);
		ConvertUtils.register(bd, java.math.BigDecimal.class);
	}

	public static void copyProperties(Object target, Object source) {
		// 支持对日期copy
		try {
			BeanUtils.copyProperties(target, source);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 用一句话描述这个方法的作用.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
	}

}

class UtilDateConverter implements Converter {

	@SuppressWarnings("unchecked")
	public <T> T convert(Class<T> type, Object value) {
		// String p = (String) value;
		if (value == null) {
			return null;
		}
		if (value instanceof Date) {
			return (T) value;
		}
		if (value instanceof BigDecimal) {
			return (T) value;
		}
		if (value instanceof String) {
			String p = (String) value;
			try {
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				return (T) df.parse(p.trim());
			} catch (Exception e) {
				try {
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
					return (T) df.parse(p.trim());
				} catch (Exception ex) {
					return null;
				}
			}
		}
		return null;
	}

}
