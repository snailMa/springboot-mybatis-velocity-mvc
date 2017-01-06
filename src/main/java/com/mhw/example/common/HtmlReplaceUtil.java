package com.mhw.example.common;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HtmlReplaceUtil {
	public Object replaceHtml(Object object) throws NoSuchMethodException, SecurityException, Exception {
		// 不需要的自己去掉即可
		if (object != null) {
			if (object instanceof String) {
				// 如果输入对象直接为String类型，则直接替换。
				object = HtmlRegexpUtil.replaceTag(object.toString()).toString();
				return object;
			}

			// 列表中对象替换
			if (object instanceof ArrayList || object instanceof List) {
				List val = (List) object;

				if (val != null) {
					for (int i = 0; i < val.size(); i++) {
						this.replaceHtml(val.get(i));
					}
				}
				return null;
			}

			//
			if (object instanceof String[]) {
				String[] strs = (String[]) object;
				if (strs != null && strs.length > 0) {
					for (int i = 0; i < strs.length; i++) {
						strs[i] = this.replaceHtml(strs[i]).toString();
					}
				}
				return null;
			}

			// 拿到该类
			Class<?> clz = object.getClass();
			// 获取实体类的所有属性，返回Field数组
			// Field[] fields = clz.getDeclaredFields();
			// clz.getSuperclass()
			List fields = getFields(clz);

			for (int j = 0; j < fields.size(); j++) {
				Field field = (Field) fields.get(j);
				try {
					// 如果类型是String
					if (field.getGenericType().toString().equals("class java.lang.String")) { // 如果type是类类型，则前面包含"class
																								// "，后面跟类名

						// 拿到该属性的get方法
						Method getM = (Method) object.getClass().getMethod("get" + getMethodName(field.getName()));
						// 调用getter方法获取属性值
						String val = (String) getM.invoke(object);

						val = HtmlRegexpUtil.replaceTag(val);

						// 拿到该属性的set方法
						Method setM = (Method) object.getClass().getMethod("set" + getMethodName(field.getName()),
								java.lang.String.class);
						setM.invoke(object, val);
					}

					// 属性也是一个JAVABEAN 原本是indexOf(com.hundsun.internet.ism)
					if (field.getGenericType().toString().indexOf("com.hs.zt.ism.modules.im") > -1) {
						// 拿到该属性的get方法
						Method getM = (Method) object.getClass().getMethod("get" + getMethodName(field.getName()));
						// 调用getter方法获取属性值
						Object val = getM.invoke(object);

						this.replaceHtml(val);
					}

					// 列表中对象替换
					if (field.getGenericType().toString().indexOf("java.util.List") > -1
							|| field.getGenericType().toString().indexOf("java.util.ArrayList") > -1) {
						// 拿到该属性的get方法
						Method getM = (Method) object.getClass().getMethod("get" + getMethodName(field.getName()));
						// 调用getter方法获取属性值
						List val = (List) getM.invoke(object);

						if (val != null) {
							for (int i = 0; i < val.size(); i++) {
								this.replaceHtml(val.get(i));
							}
						}
					}

					// Map中对象替换
					if (field.getGenericType().toString().indexOf("java.util.HashMap") > -1
							|| field.getGenericType().toString().indexOf("java.util.Hashtable") > -1) {
						// 拿到该属性的get方法
						Method getM = (Method) object.getClass().getMethod("get" + getMethodName(field.getName()));
						// 调用getter方法获取属性值
						Map val = (Map) getM.invoke(object);

						Iterator ite = val.keySet().iterator();
						while (ite.hasNext()) {
							String key = (String) ite.next();
							Object value = val.get(key);
							this.replaceHtml(value);
						}
					}

				} catch (Exception ex) {
					// ex.printStackTrace();
				}
			}
		}
		return null;
	}

	/**
	 * 获取属性
	 * 
	 * @param clz
	 * @return
	 */
	private List getFields(Class<?> clz) {
		List list = new ArrayList();

		Field[] fields = clz.getDeclaredFields();
		for (Field field : fields) {
			list.add(field);
		}

		if (clz.getSuperclass() != null) {
			list.addAll(this.getFields(clz.getSuperclass()));
		}

		return list;
	}

	/**
	 * 把一个字符串的第一个字母大写、效率是最高的.
	 * 
	 * @param fildeName
	 *            传递过来的方法名
	 * @return String
	 * @throws Exception
	 *             抛出的异常
	 */
	private static String getMethodName(String fildeName) throws Exception {
		byte[] items = fildeName.getBytes();
		items[0] = (byte) ((char) items[0] - 'a' + 'A');
		return new String(items);
	}
}
