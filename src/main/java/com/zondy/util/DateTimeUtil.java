/**   
 * 特别声明：本技术材料受《中华人民共和国着作权法》、《计算机软件保护条例》
 * 等法律、法规、行政规章以及有关国际条约的保护，武汉中地数码科技有限公
 * 司享有知识产权、保留一切权利并视其为技术秘密。未经本公司书面许可，任何人
 * 不得擅自（包括但不限于：以非法的方式复制、传播、展示、镜像、上载、下载）使
 * 用，不得向第三方泄露、透露、披露。否则，本公司将依法追究侵权者的法律责任。
 * 特此声明！
 * 
   Copyright (c) 2016,武汉中地数码科技有限公司
 */

package com.zondy.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.zondy.property.PropertiesConfig;

/**
 * 模块名称：时间转化类								<br>
 * 功能描述：该文件详细功能描述							<br>
 * 文档作者：李大伟									<br>
 * 创建时间：2016年2月24日 下午2:56:21					<br>
 * 初始版本：V1.0										<br>
 */
public class DateTimeUtil {
	
	private static PropertiesConfig property=new PropertiesConfig();

	
	/**
	 * 功能描述：获取指定日期的后一天<br>
	 * 创建时间：2016年2月24日 下午2:56:49<br>
	 * @param @param specifiedDay
	 * @param @return
	 * @return String
	 */
	public static String getSpecifiedDayAfter(String specifiedDay){ 
		Calendar c = Calendar.getInstance(); 
		Date date=null; 
		try { 
		    date = new SimpleDateFormat("yyyy-MM-dd").parse(specifiedDay); 
		} catch (ParseException e) { 
		    e.printStackTrace(); 
		} 
		c.setTime(date); 
		int day=c.get(Calendar.DATE); 
		c.set(Calendar.DATE,day+1); 
		String dayAfter=new SimpleDateFormat("yyyy-MM-dd").format(c.getTime()); 
		return dayAfter; 
	}
	
	/**
	 * 功能描述：将字符串日期转为时间戳<br>
	 * 创建时间：2016年2月24日 下午2:54:23<br>
	 * @param @param time
	 * @param @param pattern
	 * @param @return
	 * @return Timestamp
	 */
	public static Timestamp parseStringToTimestamp(String time,int pattern){
		   SimpleDateFormat format=null;
		   Date d=null;
		   try {
			   if(pattern==1){
				      format =new SimpleDateFormat("yyyy-MM-dd");
			   }else{
				      format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			   }
			   d = format.parse(time);
			}catch (ParseException e) {
				e.printStackTrace();
			}
		   return new Timestamp(d.getTime());
	}
	/**
	 * 功能描述：日期转String<br>
	 * 创建时间：2016年2月24日 下午3:11:49<br>
	 * @param @param time
	 * @param @param type
	 * @param @return
	 * @return String
	 */
	public static String parseDate2String(Date time,int type){
		  SimpleDateFormat format=null;
		  String str="";
		  if(type==1){
			  format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			  str =format.format(time);
		  }else{
			  format=new SimpleDateFormat("yyyy-MM-dd");
			  str=format.format(time);
		  }
		  return str;
	}
	   /**
	    * 功能描述：字符转日期<br>
	    * 创建时间：2016年2月24日 下午3:13:45<br>
	    * @param @param time
	    * @param @return
	    * @return Date
	    */
	   public static Date FormatString2Date(String time,int type){
		      SimpleDateFormat format=null;
		      Date d=null;
		      try {
		    	  if(type==1){
		    		  format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    	  }else{
		    		  format=new SimpleDateFormat("yyyy-MM-dd");
		    	  }
			   	  d= format.parse(time);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return d;
	   }
	   
	   public static Timestamp getDayTime(Date begintime, int i) {
			Timestamp ts = null;
			try {
				Calendar c = Calendar.getInstance();
				c.setTime(begintime);
				c.add(Calendar.DATE, i);
				Date d = c.getTime();
				ts = new Timestamp(d.getTime());
			} catch (Exception e) {
				e.printStackTrace();
			}
			return ts;
		}
	   
	   public static String getFullTime(String time,String zxrq){
			String str="";
			String jlbbTime = property.getInitParameter("jlbbTime");
			String arr[]=jlbbTime.split("\\:");
			String arr2[]=time.split("\\:");
			int hour=Integer.parseInt(arr[0]);
			int hour2=Integer.parseInt(arr2[0]);
			int mins=Integer.parseInt(arr[1]);
			int mins2=Integer.parseInt(arr2[1]);
			if(hour2>hour){
				str=zxrq+" "+time;
			}else if(hour2==hour){
				if(mins2>=mins){
					str=zxrq+" "+time;
				}else{
					Date d=FormatString2Date(zxrq,2);
					Timestamp t=getDayTime(d, 1);
					zxrq=parseDate2String(t, 2);
					str=zxrq+" "+time;
				}
			}else{
				Date d=FormatString2Date(zxrq,2);
				Timestamp t=getDayTime(d, 1);
				zxrq=parseDate2String(t, 2);
				str=zxrq+" "+time;
			}
			str=str+":00";
			return str;
		}
	   
	   public static Date randomDate(Date beginDate,Date endDate ){
		   if(beginDate.getTime() >= endDate.getTime()){
		     return new Date();
		   }
		   long date = random(beginDate.getTime(),endDate.getTime());
		   return new Date(date);
		 }
		 public static long random(long begin,long end){
		   long rtn = begin + (long)(Math.random() * (end - begin));
		   if(rtn == begin || rtn == end){
		     return random(begin,end);
		   }
		   return rtn;
		 }
	   
	   public static String TimeStamp2Date(String timestampString,String format)
	   {
		   Long timestamp=Long.parseLong(timestampString)*1000;
		   String date=new SimpleDateFormat(format).format(new Date(timestamp));
		   return date;
	   }
	   
	   public static void main(String[] args){
		   //DateTimeUtil.getFullTime("01:00","2016-04-27");
		   Timestamp t=DateTimeUtil.parseStringToTimestamp("2018-09-17 16:20:41",0);
		   long s=(System.currentTimeMillis()-t.getTime())/1000;
		   System.out.println(s);
		 //  System.out.println(DateTimeUtil.TimeStamp2Date("1533548529", "yyyy-MM-dd HH:mm:ss"));
	   }
}
