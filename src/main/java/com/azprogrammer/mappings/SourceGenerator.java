package com.azprogrammer.mappings;

import java.util.*;

import com.azprogrammer.db.*;
import com.azprogrammer.db.web.WebUtil;

public class SourceGenerator {

  private BeanMetaData bean = null;
  private static final String r = "\r";

  public SourceGenerator(BeanMetaData beanMetaData) {
    super();
    setBean(beanMetaData);
  }

  public BeanMetaData getBean() {
    return bean;
  }

  public void setBean(BeanMetaData bean) {
    this.bean = bean;
  }

  public String getBeanName(){
    return getBean().getBeanName();
  }

  public Vector getProperties(){
    return getBean().getProperties();
  }

  public Vector getKeyProperties(){
    return getBean().getKeyProperties();
  }

  public Vector getQueryProperties(){
    return getBean().getQueryProperties();

  }
  
  public boolean hasAutoIncrement(){
      for (int i = 0; i < getProperties().size(); i++){
          PropertyMetaData prop = (PropertyMetaData) getProperties().elementAt(i);
          if(prop.isAutoIncrement()){
            return true;
          }
      }
      return false;
  }


  public String getBeanCode(){
                StringBuffer buf = new StringBuffer();
                Vector keys = getKeyProperties ();

                buf.append("/**\r");
                buf.append("* Creation Date: " + new java.util.Date().toString() + "\r");
                 buf.append("**/\r\r");
                buf.append("\r\r");
                buf.append(getBeanImports());
                buf.append("\r\r");

                 buf.append("public class " + getBeanName() +"  {\r\r");
                 buf.append("\r");

              
                //field variables
                for (int i = 0; i < getProperties().size(); i++){
                        PropertyMetaData prop = (PropertyMetaData) getProperties().elementAt(i);
                        buf.append("	protected " + prop.getColumnType() + " " + prop.getFieldVariable() + ";\r");
                }
                 buf.append("\r\r");

                 //constructor
                buf.append("	public " + getBeanName() +"() {\r");
                 buf.append("		super();\r");
                 buf.append("	}\r");
                 buf.append("\r");
                 
               

                 //getters & setters
                for (int i = 0; i < getProperties().size(); i++){
                        PropertyMetaData prop = (PropertyMetaData) getProperties().elementAt(i);
                        String type = prop.getColumnType();
                        buf.append("	public " + type + " " + prop.getGetter() + "(){\r");
                        buf.append("		return " + prop.getFieldVariable() + ";\r");
                        buf.append("	}\r\r");
                        buf.append("	public void " + prop.getSetter() + "(" + type + " " + prop.getPropertyName()+"){\r");
                        //buf.append("		" + type +" oldValue = " + prop.getFieldVariable() + ";\r");
                        buf.append("		" + prop.getFieldVariable() + " = " + prop.getPropertyName() + ";\r");
                         //buf.append("		" + prop.getFirePropChangeCode() + ";\r");
                        buf.append("	}\r\r");
                }

                  buf.append("}");

                return buf.toString();
        }

        public String buildSavePage() {
      try {

        StringBuffer str = new StringBuffer("");
      str.append(getPageHeader());
      str.append("<%" + r);
      str.append("try{" + r);
          str.append("	DBFinder finder = new DBFinder(con," + getBeanName() + "Director.getInstance());" + r);
          str.append("	" + getBeanName() + " dbObject = null;" + r);
          str.append("	if(\"new\".equals(request.getParameter(\"cmd\"))){" + r);
          str.append("		dbObject = new " + getBeanName() + "();" + r);

                  //use the sequence to setup the key
                  if(!"".equals(DBUtil.clearNull(getBean().getSequenceName()).trim())){
                          str.append("		int objectId = finder.findSequenceNumber();" + r);
                         Vector keyProps = getKeyProperties();
                          if(keyProps.size() == 1){ //there should be only one - the id from the sequence
                                  PropertyMetaData keyProp = ((PropertyMetaData) keyProps.elementAt(0));
                                  str.append("		dbObject." +  keyProp.getSetter() + "(objectId);" + r);
                          }

                  }

          str.append("	}else if(\"edit\".equals(request.getParameter(\"cmd\"))){" + r);

          str.append("		" + getBeanName() + "Key key = new " + getBeanName() + "Key();" + r);
          str.append("		WebUtil.setProperties(request,key);" + r);
          str.append("		dbObject = (" + getBeanName() + ") finder.retrieveObject(key);"+r);
          str.append("	}" +r +r);

          str.append("	WebUtil.setProperties(request,dbObject);" +r);

          str.append("	dbObject.save(con);"+r);
          str.append("%>" + r + r);
          str.append(getBeanName() + " saved.<BR>" + r);
          str.append("<a href=\"" + getBeanName() + "List.jsp\">Click here</a> to return to list." + r + r);
          str.append("<%" + r);
      str.append("}catch(Exception saveE){" + r);
      str.append("%>" + r);
      str.append("	An error occurred saving the " + getBeanName() + ":<BR>" + r);
          str.append("	<b>" + "<%=saveE.toString()%>" + "</b><BR><BR>" + r);
          str.append("	<a href=\"javascript:history.back(1)\">try again?</a><BR>" + r);
      str.append("<%}%>" + r + r);
          str.append(getPageFooter() + r);
          str.append("</body>" + r + "</html>" + r);


          return str.toString();

} catch (Exception e) {
          return e.toString();
}
}


/**
* Comment
*/
public String buildList() {
try {




      StringBuffer str = new StringBuffer("");
      str.append(getPageHeader());


      str.append("<%" + r);
          str.append(getBeanName() + "Query query = new " + getBeanName() + "Query();" + r);
           str.append("//set query properties:" + r);
          str.append("WebUtil.setProperties(request,query);" + r );
          str.append("%>" + r+ r + r);


          //query form
          str.append("<form action=\"" + getBeanName() + "List.jsp\" method=\"post\">" + r);
          str.append("	<input type=\"hidden\" name=\"cmd\" value=\"fromQuery\">" + r);


          Vector queryProps = getBean().getQueryProperties();
          for(int i = 0; i < queryProps.size(); i++){
                  PropertyMetaData queryProp = ((PropertyMetaData) queryProps.elementAt(i));
                  str.append("	" + queryProp.getPropertyName() + ": <input type=\"text\" name=\"" + queryProp.getPropertyName() +"\" value=\"<%=WebUtil.clearNull(query."+ queryProp.getGetter()+"())%>\"><BR>"+r);
          }
          str.append("	<input type=\"submit\" value=\"Search\"></td></form>" + r);
          str.append("</form><BR><BR>" +r +r +r);

          str.append("<%" + r);
          str.append("//un-comment this if you want to only search if a query is performed" + r);
          str.append("//if(\"fromQuery\".equals(WebUtil.getString(request,\"cmd\"))){" + r + r + r);


          str.append("	DBFinder finder = new DBFinder(con," + getBeanName() + "Director.getInstance());" + r);
          str.append("	finder.setQuery(query);" + r + r);
          str.append("	//un-comment this if you want to limit the results" + r);
          str.append("	//int MAX_RESULTS = 100;" + r);
          str.append("	//finder.setMaxRecordCount(MAX_RESULTS);" + r + r);
          str.append("	java.util.Vector dbObjects = finder.findDBObjects();" + r);
          str.append("	%>" + r);
          str.append("	<table>"+r);
          str.append("	<tr>"+r);

          //table column names
          for(int i = 0; i < getProperties().size(); i++){
                  str.append("<th>" + ((PropertyMetaData) getProperties().elementAt(i)).getPropertyName() + "</th>"+r);
          }
          str.append("	<th>&nbsp;</th>" + r);
          str.append("	<tr>"+r);
          str.append("	<%" + r);


          //rows
          str.append("	boolean toggle = false;"+r);
          str.append("	for (int i = 0; i < dbObjects.size(); i++){"+r);
          str.append("		"+ getBeanName() + " dbObject = (" + getBeanName() + ") dbObjects.elementAt(i);"+r);
          str.append("		"+ getBeanName() + "Key key = (" + getBeanName() + "Key) dbObject.createKey();"+r);
          str.append("		if(toggle){ %>"+r);
          str.append("			<tr class=\"toggleon\">"+r);
          str.append("		<%}else{%>"+r);
          str.append("			<tr>"+r);
          str.append("		<%}%>"+r);
          for(int i = 0; i < getProperties().size(); i++){
                  str.append("		<td><%=dbObject."+ ((PropertyMetaData) getProperties().elementAt(i)).getGetter()+"()%></td>"+r);
          }

          //code for edit button on each row.
          str.append("	<form action=\"edit" + getBeanName() + ".jsp\" method=\"post\"><td>" + r);

          Vector keyProps = getKeyProperties();
          for(int i = 0; i < keyProps.size(); i++){
                  PropertyMetaData keyProp = ((PropertyMetaData) keyProps.elementAt(i));
                  str.append("		<input type=\"hidden\" name=\"" + keyProp.getPropertyName() +"\" value=\"<%=key."+ keyProp.getGetter()+"()%>\">"+r);
          }
          str.append("	<input type=\"submit\" value=\"edit\"></td></form>" + r);
          str.append("		</tr>"+r);


          str.append("	<%" + r + "	toggle = !toggle;"+r);
          str.append("	}"+r + "%>"+r);
          str.append("	</table><BR><BR>"+r +r + r);

          str.append("<%" + r);
          str.append("//un-comment this if you want to only search if a query is performed" + r);
          str.append("//}" + r);
          str.append("%>" + r + r);

          //code for add new button
          str.append("<form action=\"addNew" + getBeanName() + ".jsp\" method=\"post\">" + r);
             str.append("<input type=\"submit\" value=\"Add New " + getBeanName() + "\"></form>" + r);
          str.append(getPageFooter() + r);
             str.append("</body>" +r + "</html>" + r);

          return str.toString();

} catch (Exception e) {
          return e.toString();
}
}



/**
* Comment
*/
public String buildForm(String beanVariable) {
try {

        boolean editPage = true;
        if("".equals(beanVariable) || (beanVariable == null)){
                    editPage = false;
        }


        StringBuffer str = new StringBuffer("");
        str.append(getPageHeader());

        str.append("<h2>");
        if(editPage){
                str.append("Edit ");
        }else{
                    str.append("Add New ");
        }
        str.append(getBeanName() + "</h2><BR>" + r +r);

        str.append("<FORM method=\"POST\" action=\"save" +  getBeanName()  + ".jsp\">");
        str.append(r + r);
    Vector properties = getProperties();


            //String objectName = getObjectNameTf().getText().trim();

            if(!editPage){
                    str.append("<input type=\"hidden\" name=\"cmd\" value=\"new\">" + r + r);
            }
            else{
                    str.append("<input type=\"hidden\" name=\"cmd\" value=\"edit\">" + r + r);
                    str.append("<%try{" + r);
                    str.append("//retrieve the object to edit" +r);
                      str.append("	DBFinder finder = new DBFinder(con," + getBeanName()  + "Director.getInstance());" +r);
                str.append("	" + getBeanName()  + "Key key= new " + getBeanName()  + "Key();" +r);
                str.append("	WebUtil.setProperties(request,key);" + r);
                str.append("	" + getBeanName()  + "  " + beanVariable + " = (" + getBeanName()  + ") finder.retrieveObject(key);" +r);
                    str.append("%>" + r);
            }

            for (int i = 0; i < properties.size(); i++){
                    str.append(getInputCode((PropertyMetaData)properties.elementAt(i),beanVariable));
            }
            str.append("<BR>"+r);
            if(editPage){
                    str.append("<input type=\"submit\" value=\"Save Changes\">" +r);
            }else{
                    str.append("<input type=\"submit\" value=\"Save\">" +r);
            }


            str.append("</FORM>" + r + r);

            if(editPage){
                    str.append("<%}catch(Exception e){" + r);
                    str.append("	//you may wish to handle this differently" +r);
                    str.append("	out.println(e.toString());");
                    str.append("}%>" +r);
            }
            str.append(getPageFooter() + r);
            str.append("</body>" +r + "</html>" + r);

            return str.toString();

} catch (Exception e) {
            return e.toString();
}
}



String getPageHeader(){
  StringBuffer str = new StringBuffer("");
str.append("<%@ include file=\"../conn.jsp\" %>" + r);
str.append("<%@ page import=\"com.lou.db.*\" %>" + r);
str.append("<%@ page import=\"com.lou.db.web.*\" %>" + r);
str.append("<%@ page import=\"" + getBean().getPackageName() + ".*\" %>" + r + r+ r);

str.append("<html>" + r + "<head>" + r + "<title>" + getBeanName() + "</title>" + r + r);
str.append("<link rel=\"StyleSheet\" href=\"../stylesheet.css\" type=\"text/css\" media=\"screen\">" + r + "</head>" + r + r + "<body>" + r + r);

return str.toString();
}


String getPageFooter(){
    StringBuffer str = new StringBuffer("");
str.append("<%@ include file=\"../conn_close.jsp\" %>" + r);
return str.toString();
}



private String getHTMLWidget(PropertyMetaData prop,String objectName){



  StringBuffer str = new StringBuffer("");

  //determine if property can be set with form
  String ptype = prop.getColumnType();
  if("boolean".equals(ptype)){
          str.append("<select name=\"" + prop.getPropertyName() + "\" size=\"1\">" + r);
          if(!"".equals(objectName)  && (objectName != null)){
                  str.append("<option value=\"Y\" <%if(" + objectName + "." + prop.getGetter() + "()){out.print(\"selected\");}%>>Y</option>" + r);
                  str.append("<option value=\"N\" <%if(!" + objectName + "." + prop.getGetter() + "()){out.print(\"selected\");}%>>N</option>" + r);
          }
          else{
                  str.append("<option value=\"Y\">Y</option>" + r);
                  str.append("<option value=\"N\">N</option>" + r);
          }
          str.append("</select>" + r);
  }
  else{
          str.append("<input type=\"text\" name=\"" + prop.getPropertyName() + "\" ");

          if(!"".equals(objectName)   && (objectName != null)){
                  str.append("value=\"<%=" + objectName + "." + prop.getGetter() + "()%>\" ");
          }
          if("char".equals(ptype)){
                  str.append("size=\"1\" maxlength=\"1\">");
          }
          else{
                  str.append("size=\"50\" maxlength=\"50\">");
          }
  }







  return str.toString();
}
private String getInputCode(PropertyMetaData prop,String objectName){
  StringBuffer str = new StringBuffer("");
  str.append("<p>" + r);
  String propName = prop.getPropertyName();

  String label = getBean().getPropertyLabel(prop);

  str.append("<font class=\"inputlabel\">" + label + ":</font>" + r);
  str.append("<br>" + r);
  str.append("<font class=\"normal\">" + r);
  str.append(getHTMLWidget(prop,objectName));
  str.append("</font>" + r);
  str.append("</p>" + r + r);
  return str.toString();
}


public String getQueryCode(){
                StringBuffer buf = new StringBuffer();
                Vector props = getQueryProperties();

                buf.append("/**\r");
                buf.append("* Creation Date: " + new java.util.Date().toString() + "\r");
                 buf.append("**/\r\r");
                 buf.append("\r\r");
                buf.append(getImports());
                buf.append("\r\r");

                 buf.append("public class " + getBeanName() +"Query extends DBQuery {\r\r");
                 buf.append("\r");


                //field variables
                for (int i = 0; i < props.size(); i++){
                        PropertyMetaData prop = (PropertyMetaData) props.elementAt(i);
                        buf.append("	private " + prop.getColumnType() + " " + prop.getFieldVariable() + ";\r");
                }
                 buf.append("\r\r");

                 //constructor
                buf.append("	public " + getBeanName() +"Query() {\r");
                 buf.append("		super();\r");
                 buf.append("	}\r");
                 buf.append("\r");


                 //getters & setters
                for (int i = 0; i < props.size(); i++){
                        PropertyMetaData prop = (PropertyMetaData) props.elementAt(i);
                        String type = prop.getColumnType();
                        buf.append("	public " + type + " " + prop.getGetter() + "(){\r");
                        buf.append("		return " + prop.getFieldVariable() + ";\r");
                        buf.append("	}\r\r");
                        buf.append("	public void " + prop.getSetter() + "(" + type + " " + prop.getPropertyName()+"){\r");
                        //buf.append("		" + type +" oldValue = " + prop.getFieldVariable() + ";\r");
                        buf.append("		" + prop.getFieldVariable() + " = " + prop.getPropertyName() + ";\r");
                        //buf.append("		" + prop.getFirePropChangeCode() + ";\r");
                        buf.append("	}\r\r");
                }


                //selectors
                buf.append("	public List<DBSelector> getSelectors() {\r");
                               
                
                buf.append("		List<DBSelector> selectors = new ArrayList<DBSelector>();\r\r");
                buf.append("        DBDirector dir = " + getBeanName () + "Director.getInstance ();\r");
                for (int i = 0; i < props.size(); i++){
                        PropertyMetaData prop = (PropertyMetaData) props.elementAt(i);
                        String type = prop.getColumnType ();
                        // we don't want the selectors to be added when the properties haven't been set
                        if("int".equals(type) || "long".equals(type) || "double".equals(type) || "float".equals(type)){
                            buf.append("       if(" + prop.getGetter() + "() > 0){\r   ");
                        }
                        buf.append("		selectors.add(new DBSelector(dir.getColumnForProperty (\"" + prop.getPropertyName () + "\"), " + prop.getSelectorParameter() + ", DBSelector." + prop.getQueryType() + "));\r");
                        if("int".equals(type) || "long".equals(type) || "double".equals(type) || "float".equals(type)){
                            buf.append("       }\r");
                        }
                }
                buf.append("\r		return selectors;\r");
                buf.append("	}\r\r");

                  buf.append("}");

                return buf.toString();
        }


    public String getImports(){
                String code = "";
                if(!"".equals(BeanUtil.cleanString(getBean().getPackageName()))){
                        code+="package " + getBean().getPackageName() + ".dao;";
                }
                code+="\r\rimport com.azprogrammer.db.*;\r";
                code+="import com.azprogrammer.db.spring.*;\r";
                code+="import java.util.*;\r";
                code+="import " + getBean().getPackageName() + "." + getBeanName () + ";\r\r";
                return code;
     }
    
    public String getBeanImports(){
        String code = "";
        if(!"".equals(BeanUtil.cleanString(getBean().getPackageName()))){
                code+="package " + getBean().getPackageName() + ";";
        }
        return code;
    }

        public String getKeyCode(){
                Vector keys = getKeyProperties();
                StringBuffer buf = new StringBuffer();

                buf.append("/**\r");
                buf.append("* Creation Date: " + new java.util.Date().toString() + "\r");
                 buf.append("**/\r\r");
                 buf.append("\r\r");
                buf.append(getImports());
                buf.append("\r\r");

                 buf.append("public class " + getBeanName() +"Key extends DBKey {\r\r");

                 //field variables
                for (int i = 0; i < keys.size(); i++){
                        PropertyMetaData prop = (PropertyMetaData) keys.elementAt(i);
                        buf.append("	private " + prop.getColumnType() + " " + prop.getFieldVariable() + ";\r");
                }
                 buf.append("\r\r");

                 //constructor
                buf.append("	public " + getBeanName() +"Key() {\r");
                 buf.append("		super();\r");
                 buf.append("	}\r");
                 buf.append("\r\r");
                 
                 
                 
                 //constructor with params
                 buf.append("    public " + getBeanName() +"Key(");
                 for (int i = 0; i < keys.size(); i++){
                     PropertyMetaData prop = (PropertyMetaData) keys.elementAt(i);
                     String type = prop.getColumnType();
                     buf.append(type + " " + prop.getPropertyName());
                     if(i < (keys.size () - 1)){
                         buf.append (", ");
                     }
                 }
                 buf.append (") {\r")
                 .append("       super();\r");
                 for (int i = 0; i < keys.size(); i++){
                     PropertyMetaData prop = (PropertyMetaData) keys.elementAt(i);
                     String type = prop.getColumnType();
                     buf.append("       " + prop.getSetter() + "(" + prop.getPropertyName()+");\r");
                 }
                  buf
                  .append("   }\r")
                  .append("\r\r");

                  
                  
                  
                 //get key columns
                 buf.append("	public DBRecord getKeyColumns(){\r");
                 for (int i = 0; i < keys.size(); i++){
                        PropertyMetaData prop = (PropertyMetaData) keys.elementAt(i);
                        buf.append("		getRecord().putField(" + getBeanName () + "Director.getInstance ().getColumnForProperty (\"" + prop.getPropertyName () + "\")," + prop.getGetter() + "());\r");
                }
                 buf.append("		return getRecord();\r");
                buf.append("	}\r");
                 buf.append("\r\r");

                 //getters & setters
                for (int i = 0; i < keys.size(); i++){
                        PropertyMetaData prop = (PropertyMetaData) keys.elementAt(i);
                        String type = prop.getColumnType();
                        buf.append("	public " + type + " " + prop.getGetter() + "(){\r");
                        buf.append("		return " + prop.getFieldVariable() + ";\r");
                        buf.append("	}\r\r");
                        buf.append("	public void " + prop.getSetter() + "(" + type + " " + prop.getPropertyName()+"){\r");
                        buf.append("		" + prop.getFieldVariable() + " = " + prop.getPropertyName() + ";\r");
                         buf.append("	}\r\r");
                }


                 buf.append("}");

                return buf.toString();
        }

        public String getCompleteCode(){
              String code = getImports();


              code+="\r\r\r";
              code+=getDirectorCode();
              code+="\r\r\r";
              code+=getKeyCode();
              code+="\r\r\r";
              code+=getBeanCode();
              code+="\r\r\r";
              code+=getQueryCode();

              return code;
      }
        
      public String getDirectorSpringCode(){
          StringBuffer buf = new StringBuffer();
          buf.append ("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r");
          buf.append ("<beans xmlns=\"http://www.springframework.org/schema/beans\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd\">\r");
          buf.append ("    <bean id=\"" + getBeanName () + "Director\" class=\"" + getBean ().getPackageName () + ".dao." + getBeanName () + "Director\" factory-method=\"getInstance\">\r");
          buf.append ("        <property name=\"tableName\" value=\"" + getBean ().getTableName () + "\"/>\r");
          if(!getBean ().getTableName ().equals (getBean ().getViewName ())){
              buf.append ("        <property name=\"viewName\" value=\"" + getBean ().getViewName () + "\"/>\r");
          }
          if(!WebUtil.isEmpty (getBean ().getSequenceName ())){
              buf.append ("        <property name=\"sequenceName\" value=\"" + getBean ().getSequenceName () + "\"/>\r");
          }
          
          buf.append ("        <property name=\"propertiesToColumns\">\r");
          buf.append ("            <map>\r");
          for (int i = 0; i < getProperties().size(); i++){
              PropertyMetaData prop = (PropertyMetaData) getProperties().elementAt(i);
              buf.append("                <entry key=\"" + prop.getPropertyName () + "\" value=\"" + prop.getColumnName () + "\" />\r");
          }          
          buf.append ("            </map>\r");
          buf.append ("        </property>\r");
          
          List<String> ingorables = getIngorablePropsForInsert ();
          if(ingorables.size () > 0){
              buf.append ("        <property name=\"ingorablePropsForInsert\">\r");
              buf.append ("            <list>\r");
              
              for (String propName : ingorables)
              {
                  buf.append("                <value>" + propName + "</value>\r");
              }
        
              buf.append ("            </list>\r");
              buf.append ("        </property>\r"); 
          }
          
          buf.append ("    </bean>\r");
          buf.append ("</beans>\r");
          return buf.toString ();
      }

      public String getDirectorCode(){
          Vector keys = getKeyProperties ();
        StringBuffer buf = new StringBuffer();

        buf.append("/**\r");
        buf.append("* Creation Date: " + new java.util.Date().toString() + "\r");
         buf.append("**/\r\r");
        buf.append("\r\r");
        buf.append(getImports());
        buf.append("\r\r");
         buf.append("public class " + getBeanName() +"Director extends DBDirector {\r\r");
         buf.append("	private static " + getBeanName() +"Director singleton = null;\r");
         buf.append("\r");
         buf.append("	protected " + getBeanName() +"Director() {\r");
         buf.append("		super();\r");
         buf.append("	}\r");
         buf.append("\r");

       
         buf.append("	public static " + getBeanName() +"Director getInstance(){\r");
         buf.append("		if (singleton == null){\r");
         buf.append("			singleton = new " + getBeanName() + "Director();\r");
         buf.append("		}\r");
         buf.append("		return singleton;\r");
         buf.append("	}\r");
         buf.append("\r");
         
         
         
       //convenience retrieval function
         buf.append("    public static " + getBeanName() +" get" + getBeanName () +  "(");
         for (int i = 0; i < keys.size(); i++){
             PropertyMetaData prop = (PropertyMetaData) keys.elementAt(i);
             String type = prop.getColumnType();
             buf.append(type + " " + prop.getPropertyName());
             if(i < (keys.size () - 1)){
                 buf.append (", ");
             }
         }
         buf.append(", SpringDAO dao");
         buf.append("){\r");
          buf.append("       return (" + getBeanName () + ") dao.findObject(new " + getBeanName () + "Key(");
          for (int i = 0; i < keys.size(); i++){
              PropertyMetaData prop = (PropertyMetaData) keys.elementAt(i);
              buf.append( prop.getPropertyName());
              if(i < (keys.size () - 1)){
                  buf.append (", ");
              }
          }
          
          buf.append ("), " + getBeanName () + "Director.getInstance());\r");
          buf.append("   }\r");
          buf.append("\r");
         
         
          
          //key
          buf.append("    public DBKey createKey(Object bean) {\r");
          buf.append ("        " + getBeanName()  + " dbObj = (" + getBeanName() +") bean;\r" );
          buf.append("        " + getBeanName() + "Key key = new " + getBeanName() + "Key();\r");
          for (int i = 0; i < keys.size(); i++){
                  PropertyMetaData prop = (PropertyMetaData) keys.elementAt(i);
                  buf.append("        key." + prop.getSetter() + "(dbObj." + prop.getGetter() + "());\r");
          }
           buf.append("       return key;\r");
           buf.append("   }\r");
           buf.append("\r");

         

         buf.append("	public Object newObject() {\r");
         buf.append("		return new " + getBeanName() + "();\r");
         buf.append("	}\r");
         buf.append("\r");
         buf.append("}");

        return buf.toString();
}

public List<String> getIngorablePropsForInsert(){
    List <String> retVal = new ArrayList <String> ();
    Map <String, String> map = new HashMap <String, String>();
    Vector props = getProperties ();
    for (Object object : props)
    {
        PropertyMetaData prop = (PropertyMetaData)object;
        if(prop.isAutoIncrement () || prop.isIgnoreOnUpdate ()){
            map.put (prop.getPropertyName (), prop.getPropertyName ());
        }
    }
    
    Set <String> keySet =  map.keySet ();
    for (String string : keySet)
    {
        retVal.add (string);
    }
    return retVal;
}
}
