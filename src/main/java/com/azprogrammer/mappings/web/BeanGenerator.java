package com.azprogrammer.mappings.web;

/**
 * Insert the type's description here.
 * Creation date: (10/5/2001 10:25:28 AM)
 * @author: Administrator
 */
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import com.azprogrammer.mappings.*;

public class BeanGenerator {
/**
 * BeanGenerator constructor comment.
 */
public BeanGenerator() {
	super();
}
	public static BeanMetaData createBean(HttpServletRequest req){
		BeanMetaData bean = new BeanMetaData();
		bean.setBeanName(BeanUtil.cleanString(req.getParameter("beanName")));
		bean.setPackageName(BeanUtil.cleanString(req.getParameter("packageName")));
		bean.setSchemaName(BeanUtil.cleanString(req.getParameter("schemaName")));
		bean.setTableName(BeanUtil.cleanString(req.getParameter("tableName")));
		bean.setViewName(BeanUtil.cleanString(req.getParameter("viewName")));
		bean.setSequenceName(BeanUtil.cleanString(req.getParameter("sequenceName")));
		bean.setProperties(createProperties(req));

		return bean;
	}
	public static Vector createProperties(HttpServletRequest req){
		Vector props = new Vector();
		String[] colNames = req.getParameterValues("colName");
		String[] keyCols = req.getParameterValues("keyCol");
		String[] useFlags = req.getParameterValues("useFlag");
        String[] aiFlags = req.getParameterValues("aiFlag");
        String[] iuFlags = req.getParameterValues("iuFlag");
		String[] names = req.getParameterValues("propName");
		String[] colVars = req.getParameterValues("colVariable");
		String[] types = req.getParameterValues("javaType");
		String[] queryFields = req.getParameterValues("queryField");
		String[] queryTypes = req.getParameterValues("queryType");
		int size = colNames.length;

		for (int i = 0; i < size; i++){
			if(isChecked(keyCols[i]) || isChecked(useFlags[i])){
				PropertyMetaData prop = new PropertyMetaData();
				prop.setKeyField(isChecked(keyCols[i]));
				prop.setPropertyName(BeanUtil.cleanString(names[i]));
				prop.setColumnType(BeanUtil.cleanString(types[i]));
				prop.setColumnName(BeanUtil.cleanString(colNames[i]));
				prop.setColumnVariable(BeanUtil.cleanString(colVars[i]));
				prop.setQueryField(isChecked(queryFields[i]));
				prop.setQueryType(BeanUtil.cleanString(queryTypes[i]));
                prop.setAutoIncrement(isChecked(aiFlags[i]));
                prop.setIgnoreOnUpdate (isChecked(iuFlags[i]));

				props.addElement(prop);
			}
		}



		return props;
	}
	static boolean isChecked(String val){
		if("ON".equalsIgnoreCase(val) || "Y".equalsIgnoreCase(val)){
			return true;
		}
		else{
			return false;
		}
	}
}
