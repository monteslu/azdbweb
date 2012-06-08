package com.azprogrammer.mappings;

/**
 * Insert the type's description here.
 * Creation date: (10/4/2001 8:42:23 AM)
 * @author: Administrator
 */
import java.sql.*;
import java.util.*;

public class BeanUtil {
/**
 * BeanUtil constructor comment.
 */
public BeanUtil() {
	super();
}
	public static String cleanString(String s){
		if(s == null){
			return "";
		}
		else{
			return s.trim();
		}
	}
	public static String getJavaType(int sqlType,String decimalDigits){
		int t = sqlType;
		if(t == Types.VARCHAR || t == Types.LONGVARCHAR || t == Types.CHAR){
			return "String";
		}
		if(t == Types.NUMERIC || t == Types.DECIMAL || t == Types.FLOAT ){
			if(decimalDigits == null || "0".equals(decimalDigits.trim())){
				return "int";
			}
			else{
				return "double";
			}
		}
		if(t == Types.INTEGER || t == Types.SMALLINT || t == Types.TINYINT){
		    return "int";
		}
		if(t == Types.BIGINT){
		    return "long";
		}
		if(t == Types.DATE || t == Types.TIMESTAMP){
			return "java.util.Date";
		}
		
		return "String";
	}
	public static String nameFromTableName(String table){
		if(table == null || "".equals(table.trim())){
			return "";
		}
		table = table.toLowerCase();
		char[] chars = table.toCharArray();
		chars[0] = Character.toUpperCase(chars[0]);

		
		for (int i = 0; i < chars.length; i++){
			if((chars[i] == '_') && (i < (chars.length + 1))){
				chars[i+1] = Character.toUpperCase(chars[i+1]);
			}
		}
		String retVal = new String(chars);
		retVal = replace(retVal,"_","");
		
		
		return retVal;
	
		
	}
	public static String propNameFromCol(String col){
		if(col == null || "".equals(col.trim())){
			return "";
		}
		col = col.toLowerCase();
		char[] chars = col.toCharArray();
		
		for (int i = 0; i < chars.length; i++){
			if((chars[i] == '_') && (i < (chars.length + 1))){
				chars[i+1] = Character.toUpperCase(chars[i+1]);
			}
		}
		String retVal = new String(chars);
		retVal = replace(retVal,"_","");
		
		
		return retVal;
	
		
	}
	public static String replace(String origStr, String toReplace, String replacement){
		if((origStr == null) || (toReplace == null) || (replacement == null)){
			return origStr;
		}

		while(origStr.indexOf(toReplace) >= 0){
			int start = origStr.indexOf(toReplace);
			
			if(start == 0){
				//replace start of string
				origStr = replacement + origStr.substring(toReplace.length());

			}
			else if((start + toReplace.length()) == origStr.length()){
				//replace end of string
				origStr = origStr.substring(0,start) + replacement;
			}
			else{
				//replace somewhere in middle of string
				String firstPart = origStr.substring(0,start);
				String lastPart = origStr.substring(firstPart.length() + toReplace.length());
				origStr = firstPart + replacement + lastPart;

			}
			

		}
		
		
		return origStr;
	}
}
