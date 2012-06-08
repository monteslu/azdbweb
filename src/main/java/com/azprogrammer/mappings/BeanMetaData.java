package com.azprogrammer.mappings;

/**
 * Insert the type's description here.
 * Creation date: (10/3/2001 3:14:16 PM)
 * @author: Administrator
 */
 import java.util.*;
 import java.sql.*;

import com.azprogrammer.db.*;

public class BeanMetaData {
	private java.util.Vector fieldProperties = new Vector();
	public  String packageName;
	private String fieldTableName = new String();
	private String fieldBeanName = new String();
	private String fieldSchemaName = new String();
	private String fieldSequenceName = new String();
	private String fieldViewName = "";
        private static final String r = "\r";
/**
 * BeanMetaData constructor comment.
 */
public BeanMetaData() {
	super();
}

/**
 * Gets the beanName property (java.lang.String) value.
 * @return The beanName property value.
 * @see #setBeanName
 */
public java.lang.String getBeanName() {
	return fieldBeanName;
}

	public String getFullyQualifiedSequenceName(){
		if("".equals(BeanUtil.cleanString(getSchemaName()))){
			return getSequenceName();
		}
		else{
			return getSchemaName() + "." + getSequenceName();
		}
	}
	public String getFullyQualifiedTableName(){
		if("".equals(BeanUtil.cleanString(getSchemaName()))){
			return getTableName();
		}
		else{
			return getSchemaName() + "." + getTableName();
		}
	}

/**
 * Gets the properties property (java.util.Vector) value.
 * @return The properties property value.
 * @see #setProperties
 */
public Vector getKeyProperties() {
	Vector keys = new Vector();
	for (int i = 0; i < getProperties().size(); i++){
		PropertyMetaData prop = (PropertyMetaData)getProperties().elementAt(i);
		if(prop.isKeyField()){
			keys.addElement(prop);
		}
	}
	return keys;
}
/**
 * Insert the method's description here.
 * Creation date: (10/5/2001 2:40:58 PM)
 * @return java.lang.String
 */
public java.lang.String getPackageName() {
	return packageName;
}
/**
 * Gets the properties property (java.util.Vector) value.
 * @return The properties property value.
 * @see #setProperties
 */
public Vector getProperties() {
	if(fieldProperties == null){
		fieldProperties = new Vector();
	}
	return fieldProperties;
}

/**
 * Gets the properties property (java.util.Vector) value.
 * @return The properties property value.
 * @see #setProperties
 */
public Vector getQueryProperties() {
	Vector props = new Vector();
	for (int i = 0; i < getProperties().size(); i++){
		PropertyMetaData prop = (PropertyMetaData)getProperties().elementAt(i);
		if(prop.isQueryField()){
			props.addElement(prop);
		}
	}
	return props;
}
/**
 * Gets the schemaName property (java.lang.String) value.
 * @return The schemaName property value.
 * @see #setSchemaName
 */
public java.lang.String getSchemaName() {
	return fieldSchemaName;
}
/**
 * Gets the sequenceName property (java.lang.String) value.
 * @return The sequenceName property value.
 * @see #setSequenceName
 */
public java.lang.String getSequenceName() {
	return fieldSequenceName;
}
/**
 * Gets the tableName property (java.lang.String) value.
 * @return The tableName property value.
 * @see #setTableName
 */
public java.lang.String getTableName() {
	return fieldTableName;
}

/**
 * Gets the viewName property (java.lang.String) value.
 * @return The viewName property value.
 * @see #setViewName
 */
public java.lang.String getViewName() {
    return fieldViewName;
}

/**
 * Sets the beanName property (java.lang.String) value.
 * @param beanName The new value for the property.
 * @see #getBeanName
 */
public void setBeanName(java.lang.String beanName) {
	fieldBeanName = beanName;
}
/**
 * Insert the method's description here.
 * Creation date: (10/5/2001 2:40:58 PM)
 * @param newPackageName java.lang.String
 */
public void setPackageName(java.lang.String newPackageName) {
	packageName = newPackageName;
}
/**
 * Sets the properties property (java.util.Vector) value.
 * @param properties The new value for the property.
 * @see #getProperties
 */
public void setProperties(java.util.Vector properties) {
	fieldProperties = properties;
}
/**
 * Sets the schemaName property (java.lang.String) value.
 * @param schemaName The new value for the property.
 * @see #getSchemaName
 */
public void setSchemaName(java.lang.String schemaName) {
	fieldSchemaName = schemaName;
}
/**
 * Sets the sequenceName property (java.lang.String) value.
 * @param sequenceName The new value for the property.
 * @see #getSequenceName
 */
public void setSequenceName(java.lang.String sequenceName) {
	fieldSequenceName = sequenceName;
}
/**
 * Sets the tableName property (java.lang.String) value.
 * @param tableName The new value for the property.
 * @see #getTableName
 */
public void setTableName(java.lang.String tableName) {
	fieldTableName = tableName;
}

/**
 * Sets the viewName property (java.lang.String) value.
 * @param viewName The new value for the property.
 * @see #getViewName
 */
public void setViewName(java.lang.String viewName) {
    fieldViewName = viewName;
}




      public String getPropertyLabel(PropertyMetaData prop) {

          try {
              String propName = prop.getPropertyName();

              //create label
              char[] chars = propName.toCharArray();
              String firstLetter = ("" + chars[0]).toUpperCase();
              chars[0] = firstLetter.toCharArray()[0];
              String label = new String(chars);
              return label;
          } catch (Exception e) {
              return "";
          }

      }


}
