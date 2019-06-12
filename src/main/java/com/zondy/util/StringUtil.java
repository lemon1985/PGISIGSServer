package com.zondy.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
public class StringUtil {
	public static String getType(String string) {
        if (isNumber(string))
            return "Number";
        else if (isJson(string))
            return "Json";
        else if (isXML(string))
            return "xml";
        else
            return "String";
    }
	
	public static boolean ifnodeexist(String xml,String nodename)
	{
		Document document;
		try {
			document = DocumentHelper.parseText(xml);
		
		Element elementTemplate = document.getRootElement();
		Element node = (Element)elementTemplate.selectSingleNode("//"+nodename);
		if(node==null)
		{
			return false;
		}else
		{
			return true;
		}
		} catch (DocumentException e) {
			e.printStackTrace();
			return false;
		} 
	}

    /**
     * 判断字符串是否是数字
     */
    public static boolean isNumber(String value) {
        return isInteger(value) || isDouble(value);
    }

    /**
     * 判断字符串是否是整数
     */
    public static boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 判断字符串是否是浮点数
     */
    public static boolean isDouble(String value) {
        try {
            Double.parseDouble(value);
            if (value.contains("."))
                return true;
            return false;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 判断是否是json结构
     */
    public static boolean isJson(String value) {
    	try {
    	Object object = JSON.parse(value);
    	if (object instanceof JSONObject) {
    		 return true;
    	} else if (object instanceof JSONArray) {
    		 return true;
    	} else {
    		 return false;
    	}
        } catch (JSONException e) {
            return false;
        }
    }
    
    public static String isnull(String s)
    {
    	return s==null?"":s;
    }
    
    public static boolean ifnull(String s)
    {
    	return !"".equals(s==null?"":s);
    }

    /**
     * 判断是否是xml结构
     */
    public static boolean isXML(String value) {
        try {
            DocumentHelper.parseText(value);
        } catch (DocumentException e) {
        	//e.printStackTrace();
            return false;
        }
        return true;
    }
}
