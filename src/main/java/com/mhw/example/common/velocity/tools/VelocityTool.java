package com.mhw.example.common.velocity.tools;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * velocity工具类 .
 */
public class VelocityTool {
	/**
	 * @Fields logger : 日志
	 */
	private Logger logger = Logger.getLogger(ValueRange.class);

	public VelocityTool() {
		// TODO Auto-generated constructor stub
		logger.debug("==========================我加载了VelocityTool");

	}

	/**
	 * 金钱格式化$!{tool.formatMoney("123423.6757","")}.
	 * 
	 * @param value
	 *            值
	 * @param format
	 *            格式化的形式
	 * @return 格式化结果
	 * @history:
	 */
	public static String formatMoney(String value, String format) {
		/**
		 * 空值判断
		 */
		if (value == null || value.equals("")) {
			return "0.00";
		}
		String f = "";
		if (StringUtils.isBlank(format)) {
			f = "###,##0.00";
		} else {
			f = format;
		}
		String res = "";
		try {

			DecimalFormat df = new DecimalFormat(f);
			res = df.format(Double.valueOf(value));

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return res;

	}

	/**
	 * 获得当前时间戳.
	 * 
	 * @return 时间戳
	 */
	public String getTimeMilli() {
		String now = String.valueOf(Calendar.getInstance().getTimeInMillis());
		return now;
	}

	/**
	 * 替换标记以正常显示.
	 * 
	 * @param input
	 *            input
	 * @return 结果
	 */
	public String replaceTag(String input) {
		if (!hasSpecialChars(input)) {
			return input;
		}
		StringBuffer filtered = new StringBuffer(input.length());
		char c;
		for (int i = 0; i <= input.length() - 1; i++) {
			c = input.charAt(i);
			switch (c) {
			case '<':
				filtered.append("&lt;");
				break;
			case '>':
				filtered.append("&gt;");
				break;
			case '"':
				filtered.append("&quot;");
				break;
			case '&':
				filtered.append("&amp;");
				break;
			default:
				filtered.append(c);
			}

		}
		return (filtered.toString());
	}

	/**
	 * 是否包含特殊字符.
	 * 
	 * @param input
	 *            input
	 * @return 是否
	 */
	public boolean hasSpecialChars(String input) {
		boolean flag = false;
		if ((input != null) && (input.length() > 0)) {
			char c;
			for (int i = 0; i <= input.length() - 1; i++) {
				c = input.charAt(i);
				switch (c) {
				case '>':
					flag = true;
					break;
				case '<':
					flag = true;
					break;
				case '"':
					flag = true;
					break;
				case '&':
					flag = true;
					break;
				default:
					break;
				}
			}
		}
		return flag;
	}

	/**
	 * 时间格式转化.
	 * 
	 * @param source
	 *            源
	 * @param format
	 *            格式化之后的型式
	 * @param date
	 *            时间字符串
	 * @return 格式化结果字符串
	 */
	public String dateSource2Format(String source, String format, String date) {
		SimpleDateFormat sdf = new SimpleDateFormat(source);
		try {
			Date d = sdf.parse(date);
			SimpleDateFormat sdfBack = new SimpleDateFormat(format);
			String back = sdfBack.format(d);
			return back;
		} catch (ParseException e) {
			SimpleDateFormat sdf2 = new SimpleDateFormat(source, Locale.US);
			try {
				Date d = sdf2.parse(date);
				SimpleDateFormat sdfBack = new SimpleDateFormat(format);
				String back = sdfBack.format(d);
				return back;
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
		}
		return date;
	}

	/**
	 * 判断是否在限制时间内.
	 * 
	 * @param startTime开始时间（精确到秒）
	 * @param endTime结束时间（精确到秒）
	 * @return 在时间内 返回 1 不在返回0
	 * @throws ParseException
	 */
	public int betweenTime(String startTime, String endTime) throws ParseException {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String a = sdf.format(date);
		Date d = sdf.parse(a);
		Date sTime = sdf.parse(startTime);
		Date eTime = sdf.parse(endTime);

		boolean flag = d.before(sTime);
		boolean flag2 = d.after(eTime);
		// 不在范围内
		if (flag || flag2) {
			return 0;
		}
		return 1;
	}

	/**
	 * 通过反射获得属性值.
	 * 
	 * @param obj
	 *            对象
	 * @param key
	 *            属性名
	 * @return 属性值
	 */
	public Object getProperty(Object obj, String key) {
		Class clazz = null;
		try {
			clazz = obj.getClass();
		} catch (Exception e) {
			logger.error("VelocityTool getProperty obj不是一个类", e);
		}
		Method method = null;
		try {
			method = clazz.getMethod("get" + firstLetterUp(key));
		} catch (Exception e) {
			e.printStackTrace();
		}
		Object o = null;
		try {
			o = method.invoke(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return o;
	}

	/**
	 * 首字母大写.
	 * 
	 * @param str
	 *            字符串
	 * @return 字符串
	 */
	public static String firstLetterUp(String str) {
		if (str != null && !"".equals(str)) {
			return str.substring(0, 1).toUpperCase() + str.substring(1, str.length());
		} else {
			return str;
		}
	}

	/**
	 * 将str 转化成 指定 编码输出
	 * 
	 * @param str
	 * @param type
	 * @return
	 */
	public static String urlencode(String str, String type) {

		if (str != null && !"".equals(str) && type != null && !"".equals(type)) {
			try {
				return URLEncoder.encode(str, type);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return "参数传错了";
	}

	public int getDoubleToInt(Double num) {
		return num.intValue();
	}

	public String decimalFormat(String fomartStr, String number) {
		if (StringUtils.isBlank(number))
			return null;
		DecimalFormat decimalFormat = new DecimalFormat(fomartStr);
		return decimalFormat.format(Double.parseDouble(number));
	}

	public String decimalFormat2(String number) {
		if (StringUtils.isBlank(number))
			return "--";
		return new BigDecimal(Double.parseDouble(number) * 100).setScale(2, BigDecimal.ROUND_HALF_UP) + "%";
	}

	public String decimalFormat3(String fomartStr, String number) {
		DecimalFormat decimalFormat = new DecimalFormat(fomartStr);
		if (StringUtils.isBlank(number))
			return decimalFormat.format(Double.parseDouble("0.00"));
		return new BigDecimal(Double.parseDouble(number) * 100).setScale(2, BigDecimal.ROUND_HALF_UP) + "";
	}

	public String ifInputStringIsBlankShow__(String number) {
		if (StringUtils.isBlank(number))
			return "--";
		else
			return number;
	}

	public int indexOf(String longStr, String partStr) {
		if (longStr != null && partStr != null) {
			return longStr.indexOf(partStr);
		} else {
			return -1;
		}
	}

	public boolean endWith(String longStr, String partStr) {
		if (longStr != null && partStr != null) {
			return longStr.endsWith(partStr);
		} else {
			return false;
		}
	}

	public boolean isBlank(String str) {
		return StringUtils.isBlank(str);
	}

	public boolean isNotBlank(String str) {
		return StringUtils.isNotBlank(str);
	}

	/**
	 * 判断 数值是否包含- 定义rise与fall做输入原因 样式有2套 表示同一东西
	 * 
	 * @param value
	 * @param rise
	 * @param fall
	 * @return
	 */
	public String showFallOrRise(String value, String rise, String fall) {
		if (StringUtils.isEmpty(value))
			return "black";
		value = value.replace("%", "");
		if (value.indexOf("--") > -1 || Double.parseDouble(value) == 0) // 不增加样式显示
			return "black";
		else if (value.indexOf("-") > -1)
			return fall;
		else
			return rise;
	}

	/**
	 * 获取UUID
	 * 
	 * @return
	 */
	public String newUUID() {
		return java.util.UUID.randomUUID().toString();
	}

	public String interestColor() {
		int random = (int) (Math.random() * 100);
		String retValue = "red-btn";

		switch (random % 3) {
		case 0:
			retValue = "red-btn";
			break;
		case 1:
			retValue = "yellow-btn";
			break;
		case 2:
			retValue = "blue-btn";
			break;
		default:
			return "red-btn";
		}

		return retValue;
	}

	public String stringConvertHtml(String string) {
		if (StringUtils.isBlank(string))
			return "";
		else {
			return string.replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("&quot;", "\"")
					.replaceAll("&amp;ldquo;", "“").replaceAll("&amp;rdquo;", "”");
		}
	}

	public String allHtmlUnescape(String string) {
		return StringEscapeUtils.unescapeHtml(string);
	}

	public String jiequString(String string) {
		if (string != null && string.length() > 25) {
			string = string.substring(0, 25) + "......";
		}
		return string;
	}

	public String subStringStr(String src, Integer firstIndex, Integer offset) {
		if (StringUtils.isEmpty(src))
			return null;
		if (src.length() <= offset) {
			return src;
		}
		return src.substring(firstIndex, offset) + "...";
	}

	public String decimalFormat4_cut4(String number) {
		if (StringUtils.isBlank(number))
			return "--";
		return new BigDecimal(Double.parseDouble(number)).setScale(4, BigDecimal.ROUND_HALF_UP) + "";
	}

	public String javaConvertHtmlTranSign(String inputStr) {
		if (!org.apache.commons.lang3.StringUtils.isEmpty(inputStr)) {
			String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
			inputStr = StringEscapeUtils.unescapeHtml(inputStr);
			java.util.regex.Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
			java.util.regex.Matcher m_script = p_script.matcher(inputStr);
			inputStr = m_script.replaceAll(""); // 过滤script标签
			return inputStr;
		}
		return "";
		// return inputStr.replaceAll("\r\n", "</br>");
	}

	public String javaConvertRNToBr(String inputStr) {
		if (!org.apache.commons.lang3.StringUtils.isEmpty(inputStr)) {
			return inputStr.replaceAll("\r\n", "</br>");
		}
		return "--";
	}

	public String decimalFormatNoPercent(String number) {
		if (StringUtils.isBlank(number))
			return "--";
		return new BigDecimal(Double.parseDouble(number) * 100).setScale(2, BigDecimal.ROUND_HALF_UP) + "";
	}

	public Boolean showProdCenterCSS(String pathurl, String staticPath) {
		if (StringUtils.isEmpty(pathurl) || org.apache.commons.lang3.StringUtils.isEmpty(staticPath)) {
			return false;
		}
		if (staticPath.equals(pathurl))
			return true;
		return false;
	}

	public String decimalFormat4_cut0(String number) {
		if (StringUtils.isBlank(number))
			return "--";
		return new BigDecimal(Double.parseDouble(number)).setScale(2, BigDecimal.ROUND_HALF_UP) + "";
	}

	public String getArrayValue(String[] array, Integer num) {
		if (array != null && array.length > 0 && num > -1 && array.length >= num) {
			return array[num - 1];
		}
		return null;
	}

	public String showSystemCheckMsg(String refusalType, String refusalReasons) {
		// 1基础信息缺失/不正确
		// 2净值缺失/不正确
		// 3其他
		String strT = "拒绝理由:%s" + (!org.apache.commons.lang3.StringUtils.isEmpty(refusalReasons) ? "," : "") + "%s";
		if ("1".equals(refusalType))
			return String.format(strT, "基础信息缺失/不正确", refusalReasons);
		else if ("2".equals(refusalType))
			return String.format(strT, "净值缺失/不正确", refusalReasons);
		else
			return String.format(strT, "其他", refusalReasons);
	}

	public String getResearchReportStatusDesc(String reportStatusDesc) {
		if (StringUtils.isEmpty(reportStatusDesc))
			return null;
		else if ("1".equals(reportStatusDesc)) {
			return "已展示";
		} else if ("2".equals(reportStatusDesc)) {
			return "被隐藏";
		}
		return null;
	}

	public String replaceString(String str, String oldChar, String newChar) {
		return str.replaceAll(oldChar, newChar);
	}

	/**
	 * 计算两个日期之间相差的天数
	 * 
	 * @param smdate
	 *            较小的时间
	 * @param bdate
	 *            较大的时间 (当前之间)
	 * @return 相差天数
	 * @throws ParseException
	 */
	public String daysBetween(Date smdate) throws ParseException {
		if (smdate == null) {
			return "--";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		smdate = sdf.parse(sdf.format(smdate));
		Date bdate = sdf.parse(sdf.format(new Date()));
		Calendar cal = Calendar.getInstance();
		cal.setTime(smdate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(bdate);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return between_days + "";
	}

	/**
	 * 判断 数值展示颜色
	 * 
	 * @param value
	 * @param warning
	 * @param danger
	 * @return
	 */
	public String showWarningOrDanger(Date value) {
		String daysClass = null;
		try {
			daysClass = daysBetween(value);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (!StringUtils.isEmpty(daysClass) && !daysClass.equals("--")) {
			int d = Integer.parseInt(daysClass);
			if (d >= 10 && d < 20) {
				return "warning";
			}
			if (d >= 20) {
				return "danger";
			}
		}
		return "";
	}

	public String subStringStrWithOutPoint(String src, Integer firstIndex, Integer offset) {
		if (StringUtils.isEmpty(src))
			return null;
		if (src.length() <= offset) {
			return src;
		}
		return src.substring(firstIndex, offset);
	}
}
