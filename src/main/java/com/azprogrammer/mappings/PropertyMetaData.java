package com.azprogrammer.mappings;

/**
 * Insert the type's description here.
 * Creation date: (10/3/2001 3:17:29 PM)
 * @author: Administrator
 */
public class PropertyMetaData {
	private java.lang.String fieldPropertyName = new String();
	private java.lang.String fieldColumnName = new String();
	private boolean fieldKeyField = false;
	private java.lang.String fieldColumnType = new String();
	private java.lang.String fieldColumnVariable = new String();
	private boolean fieldQueryField = false;
	private java.lang.String fieldQueryType = new String();
    private boolean autoIncrement = false;
    private boolean ignoreOnUpdate = false;
/**
 * PropertyMetaData constructor comment.
 */
public PropertyMetaData() {
	super();
}
/**
 * Gets the columnName property (java.lang.String) value.
 * @return The columnName property value.
 * @see #setColumnName
 */
public java.lang.String getColumnName() {
	return fieldColumnName;
}
/**
 * Gets the columnType property (java.lang.String) value.
 * @return The columnType property value.
 * @see #setColumnType
 */
public java.lang.String getColumnType() {
	return fieldColumnType;
}
/**
 * Gets the columnVariable property (java.lang.String) value.
 * @return The columnVariable property value.
 * @see #setColumnVariable
 */
public java.lang.String getColumnVariable() {
	return fieldColumnVariable;
}
	public String getDBGetter(){
		String s = "get";
		if("char".equals(getColumnType())){
			s+= "Character";
		}
		else if("int".equals(getColumnType())){
			s+= "Int";
		}
		else if("long".equals(getColumnType())){
			s+= "Long";
		}
		else if("double".equals(getColumnType())){
			s+= "Double";
		}
		else if("float".equals(getColumnType())){
			s+= "Float";
		}
		else if("boolean".equals(getColumnType())){
			s+= "Boolean";
		}
		else if("java.util.Date".equals(getColumnType())){
			s+= "Date";
		}
		else if("java.sql.Date".equals(getColumnType())){
			s+= "SQLDate";
                }
                else if("java.sql.Timestamp".equals(getColumnType())){
                     s+= "Timestamp";
                }



		s += "Field";
		return s;
	}
	public String getFieldVariable(){
		if("".equals(BeanUtil.cleanString(getPropertyName()))){
			return "";
		}
		else{
			return "m_" + getPropertyName();
		}
	}
	public String getFirePropChangeCode(){
		String s = "firePropertyChange(\"" + getPropertyName()+ "\",";
		if("char".equals(getColumnType())){
			s+= "new Character(oldValue), new Character(" + getFieldVariable() + ")";
		}
		else if("int".equals(getColumnType())){
			s+= "new Integer(oldValue), new Integer(" + getFieldVariable() + ")";
		}
		else if("double".equals(getColumnType())){
			s+= "new Double(oldValue), new Double(" + getFieldVariable() + ")";
		}
		else if("float".equals(getColumnType())){
			s+= "new Float(oldValue), new Float(" + getFieldVariable() + ")";
		}
		else if("boolean".equals(getColumnType())){
			s+= "new Boolean(oldValue), new Boolean(" + getFieldVariable() + ")";
		}
		else if("long".equals(getColumnType())){
			s+= "new Long(oldValue), new Long(" + getFieldVariable() + ")";
		}
		else{
			s+= "oldValue, " + getFieldVariable();
		}

		s += ")";
		return s;
	}
	public String getGetter(){
		if("".equals(BeanUtil.cleanString(getPropertyName()))){
			return "";
		}
		else{
			char[] chars = getPropertyName().toCharArray();
			chars[0] = Character.toUpperCase(chars[0]);
			return "get" + new String(chars);
		}
	}
/**
 * Gets the propertyName property (java.lang.String) value.
 * @return The propertyName property value.
 * @see #setPropertyName
 */
public java.lang.String getPropertyName() {
	return fieldPropertyName;
}
/**
 * Gets the queryType property (java.lang.String) value.
 * @return The queryType property value.
 * @see #setQueryType
 */
public java.lang.String getQueryType() {
	return fieldQueryType;
}
	public String getSelectorParameter(){
		String type = getColumnType();
		if("char".equals(type) || "int".equals(type) || "long".equals(type) || "double".equals(type) || "float".equals(type)){
			return  "\"\" + " + getGetter() + "()";
		}
		else if("boolean".equals(type)){
			return "DBUtil.getDBBooleanString(" + getGetter() + "())";
		}
		else if("java.util.Date".equals(type) || "java.sql.Date".equals(type)){
			return "DBUtil.getDBDateString(" + getGetter() + "())";
		}
                else if("java.sql.Timestamp".equals(type)){
                      return "DBUtil.getDBLayer().getDateTimeInsertString(" + getGetter() + "())";
                }

		else{
			return getGetter() + "()";
		}


	}
	public String getSetter(){
		if("".equals(BeanUtil.cleanString(getPropertyName()))){
			return "";
		}
		else{
			char[] chars = getPropertyName().toCharArray();
			chars[0] = Character.toUpperCase(chars[0]);
			return "set" + new String(chars);
		}
	}
/**
 * Gets the keyField property (boolean) value.
 * @return The keyField property value.
 * @see #setKeyField
 */
public boolean isKeyField() {
	return fieldKeyField;
}
/**
 * Gets the queryField property (boolean) value.
 * @return The queryField property value.
 * @see #setQueryField
 */
public boolean isQueryField() {
	return fieldQueryField;
}
/**
 * Sets the columnName property (java.lang.String) value.
 * @param columnName The new value for the property.
 * @see #getColumnName
 */
public void setColumnName(java.lang.String columnName) {
	fieldColumnName = columnName;
}
/**
 * Sets the columnType property (java.lang.String) value.
 * @param columnType The new value for the property.
 * @see #getColumnType
 */
public void setColumnType(java.lang.String columnType) {
	fieldColumnType = columnType;
}
/**
 * Sets the columnVariable property (java.lang.String) value.
 * @param columnVariable The new value for the property.
 * @see #getColumnVariable
 */
public void setColumnVariable(java.lang.String columnVariable) {
	fieldColumnVariable = columnVariable;
}
/**
 * Sets the keyField property (boolean) value.
 * @param keyField The new value for the property.
 * @see #getKeyField
 */
public void setKeyField(boolean keyField) {
	fieldKeyField = keyField;
}
/**
 * Sets the propertyName property (java.lang.String) value.
 * @param propertyName The new value for the property.
 * @see #getPropertyName
 */
public void setPropertyName(java.lang.String propertyName) {
	fieldPropertyName = propertyName;
}
/**
 * Sets the queryField property (boolean) value.
 * @param queryField The new value for the property.
 * @see #getQueryField
 */
public void setQueryField(boolean queryField) {
	fieldQueryField = queryField;
}
/**
 * Sets the queryType property (java.lang.String) value.
 * @param queryType The new value for the property.
 * @see #getQueryType
 */
public void setQueryType(java.lang.String queryType) {
	fieldQueryType = queryType;
}
  public boolean isAutoIncrement() {
    return autoIncrement;
  }
  public void setAutoIncrement(boolean autoIncrement) {
    this.autoIncrement = autoIncrement;
  }
  
  public boolean isIgnoreOnUpdate() {
      return ignoreOnUpdate;
    }
    public void setIgnoreOnUpdate(boolean ignoreOnUpdate) {
      this.ignoreOnUpdate = ignoreOnUpdate;
    }
}
