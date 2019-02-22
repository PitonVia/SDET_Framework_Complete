package com.inetBanking.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

// Methods of this class will be accessing the 'config.properties' file inside Configuration folder
// and will be returning all the property values

public class ReadConfig {
	
	private static Properties prop = new Properties();
	
	// Static initialization block:
	static {
		
		// Specify the path of the 'config.properties' file
		File src = new File("./Configuration/config.properties");
		
		FileInputStream fis = null;
		
		try {
			fis = new FileInputStream(src);
			prop.load(fis);			
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
	
	// Creating generic getter for all property values:
	public String getPropertyValue(String key) {
		return prop.getProperty(key);
	}
	
}
