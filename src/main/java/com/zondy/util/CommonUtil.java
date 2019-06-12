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
/*import it.sauronsoftware.jave.AudioAttributes;
import it.sauronsoftware.jave.EncodingAttributes;*/
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.UUID;

/*import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;

import it.sauronsoftware.jave.*;*/



/**
 * 模块名称：通用方法类								<br>
 * 功能描述：该文件详细功能描述							<br>
 * 文档作者：李大伟									<br>
 * 创建时间：2016年2月24日 下午2:57:26					<br>
 * 初始版本：V1.0										<br>
 */
public class CommonUtil {
	/**
	 * 功能描述： 解决文件下载时中文名乱码问题<br>
	 * 创建时间：2016年2月24日 下午2:59:02<br>
	 * @param @param s
	 * @param @return
	 * @return String
	 */
	public static String toUtf8String(String s) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c >= 0 && c <= 255) {
				sb.append(c);
			} else {
				byte[] b;
				try {
					b = Character.toString(c).getBytes("utf-8");
				} catch (Exception e) {
					b = new byte[0];
				}
				for (int j = 0; j < b.length; j++) {
					int k = b[j];
					if (k < 0)k += 256;
					sb.append("%" + Integer.toHexString(k).toUpperCase());
				}
			}
		}
		return sb.toString();
	}
	
	
	/**
	 * 功能描述：空格处理<br>
	 * 创建时间：2016年2月24日 下午2:58:22<br>
	 * @param @param str
	 * @param @return
	 * @return String
	 */
	public static String trimStr(String str){
		if(str!=null&&!str.equals("")){
			str=str.trim();
			str=str.replaceAll("\r", "");
			str=str.replaceAll("\n", "");
		}
		return str;
	}
	
	/**
	 * 功能描述：判断字符串是否为空<br>
	 * 创建时间：2016年2月24日 下午2:58:40<br>
	 * @param @param s
	 * @param @return
	 * @return boolean
	 */
	public static boolean isNull(String s){
		if(s==null||s.equals("")||s.equals("null")){
			return true;
		}else{
			return false;
		}
	}
	
	
	
	public static String trims(String s){
		if(!CommonUtil.isNull(s)){
			return s.trim();
		}else{
			return "";
		}
	}
	
	public static String[] getXy(String x1,String y1,String x2,String y2,String x3,String y3){
		if(!CommonUtil.isNull(x1)&&!CommonUtil.isNull(y1)){
            return new String[]{x1,y1};			
		}else{
			if(!CommonUtil.isNull(x2)&&!CommonUtil.isNull(y2)){
				return new String[]{x2,y2};
			}else{
				if(!CommonUtil.isNull(x3)&&!CommonUtil.isNull(y3)){
					return new String[]{x3,y3};
				}else{
					 return new String[]{"",""};
				}
			}
		}
	}
	
	public static String formatDouble(Double d){
		if(d==null){
			return "";
		}else{
			DecimalFormat df=new DecimalFormat("#.00");
			return df.format(d);
		}
	}
	
	public static String getPrimaryKey(){
        String uid = UUID.randomUUID().toString();
        uid = uid.replaceAll("-", "");
        return uid;
    }
	
	public static File writeToFile(InputStream ins,String path){
		try{
			File file=new File(path);
			OutputStream out=new FileOutputStream(file);
			int read=0;
			byte[] bytes=new byte[1024];
			while((read=ins.read(bytes))!=-1){
				out.write(bytes,0,read);
			}
			out.flush();
			out.close();
			return file;
		}catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/*public static String upload(String url, File file, String filename, String contentType) {
		PostMethod method = new PostMethod(url);
		HttpClient client = new HttpClient();
		try {
			Part[] parts = {new MyFilePart("media", filename,file,contentType,"UTF-8") };

			method.setRequestEntity(new MultipartRequestEntity(parts, method
					.getParams()));
			client.getHttpConnectionManager().getParams()
					.setConnectionTimeout(5000);
			int status = client.executeMethod(method);
			if (status == HttpStatus.SC_OK) {
				System.out.println("上传成功");
			} else {
				System.out.println("上传失败");
			}
			byte[] b = method.getResponseBody();
			String res =new String(b,"utf-8");
			return res;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		finally {
			method.releaseConnection();
		}
		return "";
	}
	
	public static void changeToMp3(String sourcePath, String targetPath) {
		File source = new File(sourcePath);
		File target = new File(targetPath);
		AudioAttributes audio = new AudioAttributes();
		Encoder encoder = new Encoder();
		audio.setCodec("libmp3lame");
		EncodingAttributes attrs = new EncodingAttributes();
		attrs.setFormat("mp3");
		attrs.setAudioAttributes(audio);
		try {
			encoder.encode(source, target, attrs);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InputFormatException e) {
			e.printStackTrace();
		} catch (EncoderException e) {
			e.printStackTrace();
		}
	}*/
}
