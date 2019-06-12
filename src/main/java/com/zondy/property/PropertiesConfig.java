package com.zondy.property;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class PropertiesConfig {
	private String propFileName;
	private Properties properties = null;

	public PropertiesConfig(String paramString) {
		this.propFileName = paramString;
		getPropInputStream();
	}

	public PropertiesConfig() {
		this.propFileName = "system.properties";
		getPropInputStream();
	}

	private void getPropInputStream() {
		try {
			Resource resource = new ClassPathResource(this.propFileName);
			this.properties = new Properties();
			InputStream localInputStream = resource.getInputStream();
			this.properties.load(localInputStream);
			localInputStream.close();
		} catch (FileNotFoundException localFileNotFoundException) {
			localFileNotFoundException.getMessage();
		} catch (IOException localIOException) {
			localIOException.getMessage();
		}
	}

	public String getInitParameter(String paramString) {
		String str1 = "";
		byte[] arrayOfByte = null;
		try {
			String str2 = this.properties.getProperty(paramString);
			if (str2 != null) {
				arrayOfByte = str2.getBytes("ISO-8859-1");
				str1 = new String(arrayOfByte, "UTF-8");
			}
		} catch (UnsupportedEncodingException localUnsupportedEncodingException) {
			localUnsupportedEncodingException.printStackTrace();
		}
		return str1;
	}

	public void setInitParameter(String paramString1, String paramString2) {
		this.properties.setProperty(paramString1, paramString2);
	}

	public boolean storeInitParameter() {
		try {
			File localFile = new File(this.propFileName);
			FileOutputStream localFileOutputStream = new FileOutputStream(
					localFile.getAbsolutePath());
			this.properties.store(localFileOutputStream,
					"## webconfig file for PGIS site ###");
		} catch (IOException localIOException) {
			localIOException.getMessage();
		}
		return true;
	}

	public void removeInitParameter(String paramString) {
		this.properties.remove(paramString);
	}
}