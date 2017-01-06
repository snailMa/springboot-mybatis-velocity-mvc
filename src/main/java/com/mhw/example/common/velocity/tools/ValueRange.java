package com.mhw.example.common.velocity.tools;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * 转换成配置文件中的值 .
 */
public class ValueRange {

	/**
	 * @Fields logger : 日志
	 */
	private Logger logger = Logger.getLogger(ValueRange.class);

	/**
	 * @Fields prop : 对象
	 */
	private Properties prop;

	/**
	 * . 构造函数
	 */
	public ValueRange() {
		try {
			// 将properties文件加载到输入字节流中
			InputStream is = ValueRange.class.getResourceAsStream("/im_valueRange.properties");
			InputStreamReader read = new InputStreamReader(is, "UTF-8");
			// 创建一个Properties容器
			prop = new Properties();
			// 从流中加载properties文件信息
			prop.load(read);
		} catch (IOException e) {
			logger.error("读取配置文件valueRange.properties异常!", e);
		}
	}

	/**
	 * 查询对应的值.
	 * 
	 * @param type
	 *            类别 （sex...）
	 * @param value
	 *            值（0,1...）
	 * @return 对应值
	 */
	public String value(String type, String value) {
		String retValue = "";
		retValue = prop.getProperty(type + "_" + value);
		return retValue;
	}

	/**
	 * 查询对应的值（找不到返回默认值）.
	 * 
	 * @param type
	 *            类别 （sex...）
	 * @param value
	 *            值（0,1...）
	 * @param def
	 *            默认值
	 * @return 对应值
	 */
	public String value(String type, String value, String def) {
		String str = this.value(type, value);
		if (StringUtils.isNotBlank(str)) {
			return str;
		} else {
			return def;
		}
	}

}
