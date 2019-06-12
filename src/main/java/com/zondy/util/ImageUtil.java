package com.zondy.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.MemoryCacheImageInputStream;

public class ImageUtil {
	/**
	* 判断图片文件格式
	* 
	* @param mapObj
	* @return
	* @throws IOException
	*/
	public static String getImageType(byte[] mapObj) throws IOException {
	// FileOutputStream fout = new FileOutputStream("d:\\test.jpg");
	// //将字节写入文件 
	// fout.write(mapObj);
	// fout.close();
	//支持的文件类型: ["gif", "jpeg", "jpg", "bmp", "png"],

	//	if (Base64.decode(requestDTO.getCreditImg()).length > StringUtil.sizeImge) {
	//	//大于1M
	//	throw new RestException(ErrorDetailEnum.FILE_SIZE_ERROR, WatchLogType.MERCHANTS_IN_CENTER);//文件过大
	//	}
		String type = "noPic";
		ByteArrayInputStream bais = null;
		MemoryCacheImageInputStream mcis = null;
		try {
			bais = new ByteArrayInputStream(mapObj);
			mcis = new MemoryCacheImageInputStream(bais);
			Iterator itr = ImageIO.getImageReaders(mcis);
			while (itr.hasNext()) {
				ImageReader reader = (ImageReader) itr.next();
				String imageName = reader.getClass().getSimpleName();
				if (imageName != null) {
					if ("GIFImageReader".equals(imageName)) {
						type = "gif";
					} else if ("JPEGImageReader".equals(imageName)) {
						type = "jpg";
					} else if ("PNGImageReader".equals(imageName)) {
						type = "png";
					} else if ("BMPImageReader".equals(imageName)) {
						type = "bmp";
					} else {
						type = "noPic";
					}
				}
			}
		} catch (Exception e) {
			type = "noPic";
		} finally {
			if (bais != null) {
				try {
					bais.close();
				} catch (IOException ioe) {
					type = "noPic";
				}
			}
			if (mcis != null) {
				try {
					mcis.close();
				} catch (IOException ioe) {
					type = "noPic";
				}
			}
		}
		return type;
	}
	
	/**
	  * 得到文件流
	  * @param url
	  * @return
	  */
	 public static byte[] getFileStream(String url){
	     try {
	         URL httpUrl = new URL(url);
	         HttpURLConnection conn = (HttpURLConnection)httpUrl.openConnection();
	         conn.setRequestMethod("GET");
	         conn.setConnectTimeout(5 * 1000);
	         InputStream inStream = conn.getInputStream();//通过输入流获取图片数据
	         byte[] btImg = readInputStream(inStream);//得到图片的二进制数据
	         return btImg;
	     } catch (Exception e) {
	        e.printStackTrace();
	     }
	     return null;
	 }
	
	/**
	* 从输入流中获取数据
	* @param inStream 输入流
	* @return
	* @throws Exception
	*/
	public static byte[] readInputStream(InputStream inStream) throws Exception{
	  ByteArrayOutputStream outStream = new ByteArrayOutputStream();
	  byte[] buffer = new byte[1024];
	  int len = 0;
	  while( (len=inStream.read(buffer)) != -1 ){
	      outStream.write(buffer, 0, len);
	  }
	  inStream.close();
	  return outStream.toByteArray();
	}
}
