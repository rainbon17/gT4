package com.pca.pdc.util;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;
import com.pca.corefrmwk.util.StringUtil;


public class ObjectUtils {

	public static void null2Default(Object obj) {
		Field[] fArr = obj.getClass().getDeclaredFields();
		for (Field f : fArr) {
			f.setAccessible(true);
			try {
				setValue(f, f.get(obj), obj);
			} catch (Exception e) {

			}
		}
	}
	
	
	public static void null2DefaultWithTrim(Object obj) {
		Field[] fArr = obj.getClass().getDeclaredFields();
		for (Field f : fArr) {
			f.setAccessible(true);
			try {
				setTrimedValue(f, f.get(obj), obj);
			} catch (Exception e) {

			}
		}
	}

	
	public static void setValue(Field f, Object value, Object obj) throws Exception {
		if (f.getType().equals(String.class)) {
			value = com.pca.pdc.util.StringUtils.getString(value , "");
		} else if (f.getType().equals(Integer.class)
				|| f.getType().equals(int.class)) {
			value = Integer.parseInt(com.pca.pdc.util.StringUtils
					.getString(value, "0"));
		} else if (f.getType().equals(BigDecimal.class)) {
			value = new BigDecimal(com.pca.pdc.util.StringUtils.getString(
					value, "0"));
		} else if (f.getType().equals(Double.class)) {
			value = Double.valueOf(com.pca.pdc.util.StringUtils.getString(
					value, "0.0"));
		}
		
		f.set(obj, value);
	}
	
	
	public static void setTrimedValue(Field f, Object value, Object obj) throws Exception {
		if (f.getType().equals(String.class)) {
			value = com.pca.pdc.util.StringUtils.getString(value , "");
			value=StringUtils.trim((String) value);
		} else if (f.getType().equals(Integer.class)
				|| f.getType().equals(int.class)) {
			value = Integer.parseInt(com.pca.pdc.util.StringUtils
					.getString(value, "0"));
		} else if (f.getType().equals(BigDecimal.class)) {
			value = new BigDecimal(com.pca.pdc.util.StringUtils.getString(
					value, "0"));
		} else if (f.getType().equals(Double.class)) {
			value = Double.valueOf(com.pca.pdc.util.StringUtils.getString(
					value, "0.0"));
		}
		
		f.set(obj, value);
	}

	
	public static String getExceptionCauseMsg(Throwable t) {
		if (t.getCause() == null) {
			return t.getMessage();
		}
		return getExceptionCauseMsg(t.getCause());
	}

	
	/**
	 * 轉換表單欄位型態
	 * @param criteriaMap 
	 * @param queryUsed 是否查詢用
	 */
	public static void formConverter(Map<String, Object> criteriaMap, boolean queryUsed) {
		for(String key:criteriaMap.keySet()){
			Object o = criteriaMap.get(key);
			
			if(queryUsed){
				/*DATE TO INTEGER*/
				if(o instanceof Date){
					criteriaMap.put(key,   objToInt(o) );
				}
			}else{
				/*INTEGER TO DATE*/
				if(o instanceof Integer && StringUtils.lowerCase(key).contains("date")){
					try {
						Date date= NBADateUtil.parseStringObjToDate(o);
						criteriaMap.put(key,date);
					} catch (ParseException e) {
						criteriaMap.put(key,null);
					}
				}
			}			
		}		
	}
	
	
	public static boolean isNotEmptyBlank(Object o){
		return StringUtils.isNotEmpty(StringUtils.trim(StringUtil.blankWhenNull(o)));
	}

	
	/**
	 * 畫面顯示
	 * 
	 * @param o
	 * @return
	 */
	private static Date objToDate(Object o) {
		if (o == null) {
			return null;
		}
		
		if (o instanceof Date) {
			return (Date) o;
		}
		
		try {
			return NBADateUtil.parseStringObjToDate(o);
		} catch (ParseException e) {
			return null;
		}
	}

	
	/**
	 * 查詢
	 */
	private static Integer objToInt(Object o) {
		if (o == null) {
			return null;
		}
		
		if (o instanceof Date) {
			return NBADateUtil.parseDateToInt((Date) o);
		}
		
		return Integer.parseInt("".equals(o.toString()) ? "0" : o.toString());
	}
	
	private static String objToString(Object o) {
		if (o == null) {
			return StringUtils.EMPTY;
		}
		return o.toString() ;
	}


}
