/**
 * This class reads the global data from config.propertiesfile
 * 
 *  For eg:
 *  Read the application URL as below,
 *  getContext().getStringProperty(propertyname)
 */
package planITutils.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.Logger;
import org.testng.Assert;

public class TestContext {
	
	private static Logger log;
	public static Properties prop;
	InputStream inp;
	
	public TestContext(Logger log, String file){	
		this.log=log;
		loadPropertiesFile(file);
	}
	
	public void loadPropertiesFile(String file){
		try{
			prop = new Properties();
			inp = new FileInputStream(file);
			prop.load(inp);
		}catch(IOException e){
			Assert.fail("Unable to load properties file ", e);
		}finally{
			if(inp!=null){
				try {
					inp.close();
				} catch (IOException e) {
					log.error("Error in closing the stream " , e);
				}
			}
		}
	}
	
	
	public static String getStringProperty(String propertyname){
		String value = null;
		if(prop.containsKey(propertyname)){
			 value = prop.getProperty(propertyname);
		}else
			log.warn("Property: " + propertyname + " is not present in the config.properties file");
		return value;
	}
	
}
