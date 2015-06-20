package models;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
	private String CONFIG_FILE_PATH = "/Users/samchien/config/config.properties";
	private int MIN_GRAM;
	private int MAX_GRAM;
	private int KEYWORDS_NUM;
	private double EPSILON;
	private String EXCEL_PATH;
	private String DB_NAME;
	private String DB_USER;
	private String DB_PASS;

	public int getMIN_GRAM() {
		return MIN_GRAM;
	}

	public double getEPSILON() {
		return EPSILON;
	}

	public String getEXCEL_PATH() {
		return EXCEL_PATH;
	}

	public int getMAX_GRAM() {
		return MAX_GRAM;
	}

	public int getKEYWORDS_NUM() {
		return KEYWORDS_NUM;
	}

	public String getDB_NAME() {
		return DB_NAME;
	}

	public String getDB_USER() {
		return DB_USER;
	}

	public String getDB_PASS() {
		return DB_PASS;
	}

	public Config() {
		Properties prop = new Properties();
		InputStream input = null;
		
		try {
			input = new FileInputStream(CONFIG_FILE_PATH);
			prop.load(input);
			
			// get the property value and print it out
			MIN_GRAM = Integer.parseInt(prop.getProperty("MIN_GRAM"));
			MAX_GRAM = Integer.parseInt(prop.getProperty("MAX_GRAM"));
			KEYWORDS_NUM = Integer.parseInt(prop.getProperty("KEYWORDS_NUM"));
			EPSILON = Double.parseDouble(prop.getProperty("EPSILON"));
			EXCEL_PATH = prop.getProperty("EXCEL_PATH");
			DB_NAME = prop.getProperty("DB_NAME");
			DB_USER = prop.getProperty("DB_USER");
			DB_PASS = prop.getProperty("DB_PASS");
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
