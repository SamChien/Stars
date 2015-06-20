package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

public class MysqlDB {
	Connection conn;
	
	public MysqlDB() {
		Config config = new Config();
		
		try {
			Class.forName("com.mysql.jdbc.Driver"); 
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + config.getDB_NAME() + "?useUnicode=true&characterEncoding=UTF8", config.getDB_USER(), config.getDB_PASS());
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void insert(String tableName, String[] cols, Object[] values, boolean isIgnore) {
		try {
			String sql;
			Statement stmt = (Statement) conn.createStatement();
			
			if (isIgnore) {
				sql = "INSERT IGNORE INTO " + tableName + " SET ";
				for (int index = 0; index < cols.length; index++) {
					if (values[index] == null) {
						sql += cols[index] + " = NULL,";
					} else if (values[index] instanceof String) {
						sql += cols[index] + " = '" + ((String)values[index]).replaceAll("'", "''") + "',";
					} else if (values[index] instanceof Integer || values[index] instanceof Double) {
						sql += cols[index] + " = " + values[index] + ",";
					}
				}
				sql = sql.substring(0, sql.length() - 1);
				stmt.execute(sql);
			} else {
				sql = "INSERT INTO " + tableName + " (";
				for (String colName : cols) {
					sql += colName + ",";
				}
				sql = sql.substring(0, sql.length() - 1) + ") VALUES (";
				for (Object theValue : values) {
					if (theValue == null) {
						sql += "NULL,";
					} else if (theValue instanceof String) {
						sql += "'" + ((String)theValue).replaceAll("'", "''") + "',";
					} else if (theValue instanceof Integer
							|| theValue instanceof Double) {
						sql += theValue + ",";
					}
				}
				sql = sql.substring(0, sql.length() - 1) + ")";
				stmt.execute(sql);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void delete(String tableName, String where) {
		try {
			Statement stmt = (Statement) conn.createStatement();
			String sql = "DELETE FROM " + tableName +" WHERE " + where;
			
			stmt.execute(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ResultSet select(String[] cols, String tableName, boolean isDistinct, String where, String orderBy) {
		ResultSet result = null;
		
		try {
			Statement stmt = (Statement) conn.createStatement();
			String sql = "SELECT ";
			
			if (isDistinct) {
				sql += "DISTINCT ";
			}
			for (String colName: cols) {
				sql += colName + ",";
			}
			sql = sql.substring(0, sql.length() - 1) + " FROM " + tableName;
			if (where != null) {
				sql += " WHERE " + where;
			}
			if (orderBy != null) {
				sql += " ORDER BY " + orderBy;
			}
			result = stmt.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public void close() {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}