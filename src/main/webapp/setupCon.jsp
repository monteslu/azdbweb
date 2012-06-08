<%@ page import="com.azprogrammer.db.web.*" %><%@ page import="java.sql.*" %>
<html>
<head>
<title>
setupCon
</title>
<link rel="StyleSheet" href="stylsheet.css" type="text/css" media="screen">
</head>
<body>
  <h1>
connection info
</h1><BR>
<%
String driver = WebUtil.getString(request,"driver");
if(!"".equals(driver)){
  try{
    	Class.forName(driver);
    	String dbURL = "";
        Connection con = null;

        if("oracle.jdbc.driver.OracleDriver".equals(driver)){
          dbURL = "jdbc:oracle:thin:" + WebUtil.getString(request,"dbUser") + "/" + WebUtil.getString(request,"dbPassword") + "@" + WebUtil.getString(request,"host") + ":";
          if("".equals(WebUtil.getString(request,"port"))){
            dbURL += "1521";
          }else{
            dbURL += WebUtil.getString(request,"port");
          }
          dbURL += ":" + WebUtil.getString(request,"sid");
          con = DriverManager.getConnection(dbURL);

        }
        else if("com.mysql.jdbc.Driver".equals(driver)){
        	dbURL = "jdbc:mysql://" + WebUtil.getString(request,"host") + "/" + WebUtil.getString(request,"sid") + "?user=" + WebUtil.getString(request,"dbUser") + "&password=" + WebUtil.getString(request,"dbPassword");

                con = DriverManager.getConnection(dbURL);

        }
        else if("org.postgresql.Driver".equals(driver)){
        	dbURL = "jdbc:postgresql://" + WebUtil.getString(request,"host");
                if(!"".equals(WebUtil.getString(request,"port"))){
           		dbURL += ":" + WebUtil.getString(request,"port");
          	}
                dbURL += "/" + WebUtil.getString(request,"sid");
                out.print(dbURL);
                String dbPassToUse = WebUtil.getString(request,"dbPassword");
                if("".equals(dbPassToUse)){
                  dbPassToUse = null;
                }
                con = DriverManager.getConnection(dbURL,WebUtil.getString(request,"dbUser"),dbPassToUse);

        }else if("com.microsoft.sqlserver.jdbc.SQLServerDriver".equals(driver)){
        	dbURL = "jdbc:sqlserver://" + WebUtil.getString(request,"host");
            if(!"".equals(WebUtil.getString(request,"port"))){
       			dbURL += ":" + WebUtil.getString(request,"port");
      		}else{
      			dbURL += ":1433";
      		}
            dbURL += ";DatabaseName=" + WebUtil.getString(request,"sid") + ";";
            out.print(dbURL);
            String dbPassToUse = WebUtil.getString(request,"dbPassword");
            if("".equals(dbPassToUse)){
              dbPassToUse = null;
            }
            con = DriverManager.getConnection(dbURL,WebUtil.getString(request,"dbUser"),dbPassToUse);

    	}else{
          throw new Exception("no supported JDBC driver selected");
        }

       //establish the connection



        DatabaseMetaData md = con.getMetaData();
		out.println("Database URL :" + md.getURL() + "<BR>");
		out.println("Database Name :" + md.getDatabaseProductName() + "<BR>");
		out.println("Version :" + md.getDatabaseProductVersion() + "<BR>");
		out.println("Driver Version :" + md.getDriverVersion() + "<BR><BR><BR>");

        session.setAttribute("CONNECTION",con);

        %>
        <a href="index.jsp">Continue</a> to schema browser.<BR><BR>
        <%

  }catch(Exception conE){
      %>
      <b>error establishing connection: <%=conE.getMessage()%> </b><br>
      <%
    }
}
%>

<form method="post">
<table>

  <tr>
    <td> driver:</td>
    <td>
      <select name="driver">
      	<option value="com.mysql.jdbc.Driver" <% if("com.mysql.jdbc.Driver".equals(driver)){out.print("selected");} %>>com.mysql.jdbc.Driver</option>
      	<option value="oracle.jdbc.driver.OracleDriver" <% if("oracle.jdbc.driver.OracleDriver".equals(driver)){out.print("selected");} %>>oracle.jdbc.driver.OracleDriver</option>
      	<option value="org.postgresql.Driver" <% if("org.postgresql.Driver".equals(driver)){out.print("selected");} %>>org.postgresql.Driver</option>
      	<option value="com.microsoft.sqlserver.jdbc.SQLServerDriver" <% if("com.microsoft.sqlserver.jdbc.SQLServerDriver".equals(driver)){out.print("selected");} %>>com.microsoft.sqlserver.jdbc.SQLServerDriver</option>
      </select>
    </td>
   </tr>

    <tr>
      <td>Host:</td>
      <td><input type="text" size="50" name="host" value="<%=WebUtil.getString(request,"host")%>"></td>
    </tr>

    <tr>
       <td>DB User:</td>
    	<td><input type="text" size="50" name="dbUser" value="<%=WebUtil.getString(request,"dbUser")%>"></td>
    </tr>

    <tr>
        <td>DB Password:</td>
    	<td><input type="password" size="50" name="dbPassword" value="<%=WebUtil.getString(request,"dbPassword")%>"></td>
    </tr>

    <tr>
    	<td>database/SID:</td>
    	<td><input type="text" size="50" name="sid" value="<%=WebUtil.getString(request,"sid")%>"></td>
    </tr>

    <tr>
    	<td>port:(optional)</td>
    	<td><input type="text" size="5" name="port" maxlength="5" value="<%=WebUtil.getString(request,"port")%>"></td>
    </tr>

     <tr>
    	<td colspan="2"><input type="submit" value="connect"></td>
    </tr>

  </table>
    </form>
</body>
</html>
