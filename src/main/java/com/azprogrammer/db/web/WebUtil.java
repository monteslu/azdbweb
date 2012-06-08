package com.azprogrammer.db.web;

import javax.servlet.http.*;
import java.sql.*;
import java.util.*;
import java.lang.reflect.*;
import java.beans.*;

/**
 * WebUtil various helper methods for servlet/jsp development.<BR><BR>
 * Creation date: (10/3/2002 9:48:48 AM)
 * @author: Luis Montes
 */

public class WebUtil {
	/**
	 * WebUtil constructor .
	 */
	public WebUtil() {
		super();
	}

	/**
	 *
	 * Simply replaces any null strings with an empty string.
	 *
	 */
	public static String clearNull(String s) {
		if (s == null) {
			return "";
		} else {
			return s;
		}
	}

	/**
	 * Used to convert an HTML Form field on an HTTPRequest to a boolean.<BR>
	 * This can be helpful in mapping a checkbox to a boolean property.<BR>
	 *
	 */
	public static boolean getBoolean(HttpServletRequest request, String parm) {
		String str = getString(request, parm);
		if ("on".equalsIgnoreCase(str)
			|| "checked".equalsIgnoreCase(str)
			|| "y".equalsIgnoreCase(str)
			|| "yes".equalsIgnoreCase(str)
			|| "t".equalsIgnoreCase(str)
			|| "true".equalsIgnoreCase(str)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Used to convert a date from an HTML Form field on an HTTPRequest into a date object.<BR>
	 * The com.lou.db.DBSpecificLayer determines the date format being used.<BR>
	 *
	 */
	public static java.util.Date getDate(
		HttpServletRequest request,
		String parm) {
		String dateString = getString(request, parm);

		return com.azprogrammer.db.DBUtil.getDate(dateString);

	}

	/**
	 * Used to convert an HTML Form field on an HTTPRequest to a double.<BR>
	 *
	 */
	public static double getDouble(HttpServletRequest request, String parm)
		throws NumberFormatException {
		double retVal;
		try {
			return (double) getInt(request, parm);

		} catch (NumberFormatException e) {
			return Double.parseDouble(getString(request, parm));

		}

	}

	/**
	 * Used to convert an HTML Form field on an HTTPRequest to an int.<BR>
	 *
	 */
	public static int getInt(HttpServletRequest request, String parm)
		throws NumberFormatException {
		return Integer.parseInt(getString(request, parm));

	}
	
	/**
     * Used to convert an HTML Form field on an HTTPRequest to an int.<BR>
     *
     */
    public static long getLong(HttpServletRequest request, String parm)
        throws NumberFormatException {
        return Long.parseLong(getString(request, parm));

    }

	/**
	 * Used to convert an HTML Form field on an HTTPRequest to a String.<BR>
	 * This method also clears out any nulls and trims the String.
	 *
	 */
	public static String getString(HttpServletRequest request, String parm) {
		return clearNull(request.getParameter(parm)).trim();

	}
	
	/**
     * Used to convert an HTML Form field on an HTTPRequest to a String.<BR>
     * This method also clears out any nulls and trims the String.
     * Anything chars longer than the max will be trimmed off.
     */
    public static String getString(HttpServletRequest request, String parm, int maxLength) {
        String rawStr = getString(request, parm);
        
        if((rawStr.length () > maxLength) && (maxLength > 0)) {
            rawStr = rawStr.substring (0, maxLength);
        }
        return rawStr;

    }

	/**
	 * Checks to see if a given parameter is on the request.
	 *
	 */
	public static boolean hasParameter(HttpServletRequest in, String sName) {
		return (in.getParameterValues(sName) != null);
	}

	/**
	 * Checks for both null Objects and empty Strings
	 *
	 */
	public static boolean isEmpty(Object obj) {
		if (obj == null) {
			return true;
		} else if (obj instanceof String) {
			if (obj.toString().trim().equals("")) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}


	/**
	 *  Very simple method - does not do dns lookup or anything like that<BR>
	 * Just checks if the email address is well-formed.
	 *
	 */
	public static boolean isValidEmail(String email) {
		if (email == null) {
			return false;
		}
		email = email.trim();
		//"*@*.**" == 6 characters
		if (email.length() < 6) {
			return false;
		}
		if (email.indexOf("@") < 1) {
			return false;
		}

		//make sure there's a dot after the @
		if (email.lastIndexOf(".") < email.indexOf("@")) {
			return false;
		}

		//criteria is met, this just might be a real email
		return true;
	}
	public static String listBox(String sSql, String sName, Connection con) {

		return listBox(sSql, sName, con, "", 1, "");
	}
	public static String listBox(
		String sSql,
		String sName,
		Connection con,
		String sDefault) {

		return listBox(sSql, sName, con, sDefault, 1, "");
	}
	public static String listBox(
		String sSql,
		String sName,
		Connection con,
		String sDefault,
		int size) {
		return listBox(sSql, sName, con, sDefault, 1, "");

	}
	public static String listBox(
		String sSql,
		String sName,
		Connection con,
		String sDefault,
		int size,
		String script) {
		StringBuffer result =
			new StringBuffer(
				"<select name=\""
					+ sName
					+ "\" size=\""
					+ size
					+ "\" "
					+ script
					+ ">\r\n");
		try {
			ResultSet rs = con.createStatement().executeQuery(sSql);

			while (rs.next()) {
				String selected = "";
				String value = rs.getString(2);
				if (value.equals(sDefault)) {
					selected = " selected";
				}
				String option =
					"<option"
						+ selected
						+ " value=\""
						+ value
						+ "\">"
						+ rs.getString(1)
						+ "</option>\r\n";
				result.append(option);
			}
			rs.getStatement().close();
		} catch (Exception e) {
			//System.out.println("error building list box : " + e.toString());
			result.append("<!-- " + e.toString() + " -->\r\n");
		}

		result.append("</select>\r\n");

		return result.toString();
	}
	public static String replace(
		String origStr,
		String toReplace,
		String replacement) {
		if ((origStr == null)
			|| (toReplace == null)
			|| (replacement == null)) {
			return origStr;
		}

		while (origStr.indexOf(toReplace) >= 0) {
			int start = origStr.indexOf(toReplace);

			if (start == 0) {
				//replace start of string
				origStr = replacement + origStr.substring(toReplace.length());

			} else if ((start + toReplace.length()) == origStr.length()) {
				//replace end of string
				origStr = origStr.substring(0, start) + replacement;
			} else {
				//replace somewhere in middle of string
				String firstPart = origStr.substring(0, start);
				String lastPart =
					origStr.substring(firstPart.length() + toReplace.length());
				origStr = firstPart + replacement + lastPart;

			}

		}

		return origStr;
	}
	public static void setProperties(HttpServletRequest req, Object dbObj) {
		if ((req == null) || (dbObj == null)) {
			return;
		}
		try {
			Enumeration names = req.getParameterNames();
			PropertyDescriptor[] props =
				Introspector
					.getBeanInfo(dbObj.getClass())
					.getPropertyDescriptors();

			//cycle through all the request parms
			while (names.hasMoreElements()) {
				String name = names.nextElement().toString();
				String strVal = getString(req, name);

				//check if there is a matching bean property to the request parm name
				for (int i = 0; i < props.length; i++) {
					if (name.equals(props[i].getName())) {
						try {
							Method setter = props[i].getWriteMethod();
							Method getter = props[i].getReadMethod();
							if ((getter != null) && (setter != null)) {

								Object[] objArray = new Object[1];

								//determine the type of property, and what its value should be
								Class aClass = getter.getReturnType();
								if (aClass == String.class) {
									objArray[0] = strVal;
								} else if (aClass == Integer.TYPE) {
									objArray[0] =
										new Integer(getInt(req, name));
								} else if (aClass == Long.TYPE) {
                                    objArray[0] =
                                        new Long(getLong(req, name));
                                }else if (aClass == Double.TYPE) {
									objArray[0] =
										new Double(getDouble(req, name));
								} else if (aClass == Boolean.TYPE) {
									objArray[0] =
										new Boolean(getBoolean(req, name));
								} else if (aClass == java.util.Date.class) {
									objArray[0] = getDate(req, name);
								}

                                                                //set the property

                                                                if((aClass == java.util.Date.class) || (aClass == java.sql.Date.class) || (aClass == java.sql.Timestamp.class)){
                                                                  setter.invoke(dbObj, objArray); //null dates ok
                                                                }else{
                                                                  if (objArray[0] != null) {
                                                                    setter.invoke(dbObj, objArray);
                                                                  }
                                                                }

							}
						} catch (Exception setExp) {
						}

					}
				}

			}
		} catch (Exception e) {

		}
	}
}
