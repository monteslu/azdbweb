package com.azprogrammer.db.web;

import java.util.*;
import java.sql.*;

/**
 * JavascripGenerator is a utility class for developing JavaScript on the fly.<BR><BR>
 * Creation date: (10/16/2002 12:59:51 PM)
 * @author: Luis Montes
 */

public class JScriptGenerator {
	/**
	 * JScriptGenerator constructor.
	 */

	public JScriptGenerator() {
		super();
	}
	public static String createArray(ResultSet rs, String variableName) {
		StringBuffer sb =
			new StringBuffer("var " + variableName + " = new Array();\r\n");
		try {
			ResultSetMetaData md = rs.getMetaData();
			Vector colNames = new Vector();

			for (int i = 0; i < md.getColumnCount(); i++) {
				Object fieldValue;
				colNames.addElement(md.getColumnName(i + 1));
			}

			int arrayId = 0;

			while (rs.next()) {
				for (int i = 0; i < colNames.size(); i++) {
					sb.append(
						variableName
							+ "["
							+ arrayId
							+ "][0] = \""
							+ colNames.elementAt(i).toString()
							+ "\";\n\r");
					sb.append(
						variableName
							+ "["
							+ arrayId
							+ "][1] = \""
							+ WebUtil.clearNull(
								rs.getString(colNames.elementAt(i).toString()))
							+ "\";\n\r");

				}
				arrayId++;
			}

		} catch (Exception e) {
			sb.append("//Error creating array: " + e.toString() + "\r\n");
		}
		return sb.toString();
	}
}
