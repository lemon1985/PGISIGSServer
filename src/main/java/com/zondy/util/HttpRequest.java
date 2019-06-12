/**   
 * 特别声明：本技术材料受《中华人民共和国着作权法》、《计算机软件保护条例》
 * 等法律、法规、行政规章以及有关国际条约的保护，武汉中地数码科技有限公
 * 司享有知识产权、保留一切权利并视其为技术秘密。未经本公司书面许可，任何人
 * 不得擅自（包括但不限于：以非法的方式复制、传播、展示、镜像、上载、下载）使
 * 用，不得向第三方泄露、透露、披露。否则，本公司将依法追究侵权者的法律责任。
 * 特此声明！
 * 
   Copyright (c) 2013,武汉中地数码科技有限公司
 */

package com.zondy.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;

/**
 * 模块名称：HttpRequest									<br>
 * 功能描述：该文件详细功能描述							<br>
 * 文档作者：雷志强									<br>
 * 创建时间：Dec 21, 2016 2:32:28 PM					<br>
 * 初始版本：V1.0	最初版本号							<br>
 * 修改记录：											<br>
 * *************************************************<br>
 * 修改人：雷志强										<br>
 * 修改时间：Dec 21, 2016 2:32:28 PM					<br>
 * 修改内容：											<br>
 * *************************************************<br>
 */
public class HttpRequest {
	private static Logger log = Logger.getLogger(HttpRequest.class);
	
	/**
     * 发送HttpPost请求
     * @param url
     * @param
     * @param
     * @return
     */
//    public static String sendHttpPostRequest(String url , JSONObject paramJo , JSONObject headerJo){
//        @SuppressWarnings("resource")
//        DefaultHttpClient httpclient = new DefaultHttpClient();
//        String body = null;
//        List<NameValuePair> params2=new ArrayList<NameValuePair>();
//        try {
//            Iterator<String> it = paramJo.keySet().iterator() ;
//            String paramKey ;
//            while(it.hasNext()){
//                paramKey = it.next() ;
//                params2.add(new BasicNameValuePair(paramKey,paramJo.getString(paramKey)));
//            }
//            StringEntity e = new UrlEncodedFormEntity(params2, HTTP.UTF_8);
//            e.setContentType("application/x-www-form-urlencoded");
//            HttpPost httpost = new HttpPost(url);
//            if(headerJo != null){
//                Iterator<String> headIt = headerJo.keySet().iterator() ;
//                String headKey ;
//                while(headIt.hasNext()){
//                    headKey = headIt.next() ;
//                    httpost.addHeader(headKey, URLEncoder.encode(headerJo.getString(headKey),"UTF-8"));
//                }
//            }
//
//            httpost.setEntity(e);
//            HttpResponse response = httpclient.execute(httpost);
//            if(response==null)return null;
//            body = EntityUtils.toString(response.getEntity());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }finally{
//            httpclient.getConnectionManager().shutdown();
//        }
//        return body ;
//    }
	/**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                //System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            log.error("发送GET请求出现异常！",new Throwable(e));
        	//System.out.println("发送GET请求出现异常！" + e);
            //e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception ex) {
            	log.error("Exception异常！",new Throwable(ex));
            }
        }
        return result;
    }
    
    public static String startGet(String path){
        BufferedReader in = null;    
        InputStream is = null;
        StringBuilder result = new StringBuilder(); 
        try {
            //GET请求直接在链接后面拼上请求参数
//            String mPath = path + "?";
//            for(String key:mData.keySet()){
//                mPath += key + "=" + mData.get(key) + "&";
//            }
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            //Get请求不需要DoOutPut
            conn.setDoOutput(false);
            conn.setDoInput(true);
            //设置连接超时时间和读取超时时间
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            //连接服务器  
            conn.connect();  
            
          //if连接请求码成功
         	if (conn.getResponseCode() == conn.HTTP_OK){
            	is = conn.getInputStream();
            	byte[] bytes = new byte[1024];
            	int i = 0;
            	while ((i = is.read(bytes)) != -1){
            		result.append(new String(bytes,0,i,"utf-8"));
            	}
            	is.close();
           	}
            
            // 取得输入流，并使用Reader读取  
//            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
//            String line;
//            while ((line = in.readLine()) != null) {
//                result.append(line);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //关闭输入流
        finally{
            try{
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result.toString();
    }
    
    /**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url) {
        String result = "";
        BufferedReader in = null;
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.setRequestProperty("content-type", "x-www-form-urlencoded");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                //System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            log.error("发送GET请求出现异常！",new Throwable(e));
        	//System.out.println("发送GET请求出现异常！" + e);
            //e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception ex) {
            	log.error("Exception异常！",new Throwable(ex));
            }
        }
        return result;
    }
 
    
    /**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("content-type", "x-www-form-urlencoded");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
        	log.error("发送POST请求出现异常！",new Throwable(e));
            //System.out.println("发送 POST 请求出现异常！"+e);
            //e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
            	log.error("IOException异常！",new Throwable(ex));
                //ex.printStackTrace();
            }
        }
        return result;
    }
    public static String sendPost2(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
//            conn.setRequestProperty("accept", "*/*");
//            conn.setRequestProperty("connection", "Keep-Alive");
//            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
          //  conn.setRequestProperty("content-type", "x-www-form-urlencoded");
            conn.setRequestProperty("content-type", "application/json");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
        	log.error("发送POST请求出现异常！",new Throwable(e));
            System.out.println("发送 POST 请求出现异常！"+e);
            //e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
            	log.error("IOException异常！",new Throwable(ex));
                //ex.printStackTrace();
            }
        }
        return result;
    }
    
    public static String sendPut(String url,String param){
    	String result = "";
    	PrintWriter out = null;
        BufferedReader in = null;
        HttpURLConnection httpURLConnection = null;
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            httpURLConnection = (HttpURLConnection)realUrl.openConnection();
            // 设置通用的请求属性
            httpURLConnection.addRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpURLConnection.setRequestProperty("accept", "*/*");
            httpURLConnection.setRequestProperty("connection", "Keep-Alive");
            httpURLConnection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            httpURLConnection.setRequestMethod("PUT");
            // 发送POST请求必须设置如下两行
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(httpURLConnection.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(),"UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
        	log.error("发送PUT请求出现异常！",new Throwable(e));
            //System.out.println("发送 POST 请求出现异常！"+e);
            //e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
            	log.error("IOException异常！",new Throwable(ex));
            }
        }
    	
    	return result;
    }
    
    //发送DELETE请求
    public static String sendDelete(String url){
    	String result = "";
        BufferedReader in = null;
        HttpURLConnection httpURLConnection = null;
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            httpURLConnection = (HttpURLConnection)realUrl.openConnection();
            // 设置通用的请求属性
            httpURLConnection.addRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpURLConnection.setRequestProperty("accept", "*/*");
            httpURLConnection.setRequestProperty("connection", "Keep-Alive");
            httpURLConnection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            httpURLConnection.setRequestMethod("DELETE");
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(),"UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
        	log.error("发送DELETE请求出现异常！",new Throwable(e));
        }
        //使用finally块来关闭输出流、输入流
        finally{
        	try{
                if(in!=null){
                    in.close();
                }
                if(httpURLConnection!=null){
                	httpURLConnection.disconnect();
                }
            }
            catch(IOException ex){
            	log.error("IOException异常！",new Throwable(ex));
            }
        }
    	return result;
    }
    public static JSONObject paramToJson(Map<String, Object> pMap){
		JSONObject paraJson = new JSONObject();
		Set<?> set = pMap.entrySet();
		Iterator<?> iterator =  set.iterator();
		Entry<String, Object> entry = null;
		while(iterator.hasNext()){
			entry = (Entry<String, Object>)iterator.next();
			String key = entry.getKey().toString();
			String[] value = (String[])entry.getValue();
			paraJson.put(key, value[0]);
		}
		return paraJson;
	}
	/**
	 * 功能描述：请用一句话描述这个方法实现的功能<br>
	 * 创建作者：雷志强<br>
	 * 创建时间：Dec 21, 2016 2:32:28 PM<br>
	 * @param args
	 * @return void
	 */
	public static void main(String[] args) {
		//testSendGet();
		testSendPost();
		System.out.println("OK");
	}
	
	public static void testSendGet(){
		String url = "http://localhost:8080/freemeso/service/rest/test/t5?sex=1&a=2&c=3&token=cc9d54c6dd8b41cda4bb93f7c296abbc";
		String result = sendGet(url);
		System.out.println("result="+result);
	}
	
	public static void testSendPost(){
//		String url = "http://192.168.84.158:8080/freemeso/service/app/location/query";
//		JSONObject json = new JSONObject();
//		json.put("keyword", "武汉中地数码科技园");
//		//json.put("userpwd", "2341141");
//		json.put("token", "cc9d54c6dd8b41cda4bb93f7c296abbc");
//		String param = json.toJSONString();
//		System.out.println(param);
//		String apiurl = "http://10.32.220.70:8080/TokenWechat/webservice.do?action=httpRequestByForm";
//		//参数
//		String token = "ir1qIFejPDSEnBBuwcevcafuz8XO6617xYFfqy9cgYn5WDddruP_pNn6VSUZaIjz";
//		String userid = "IDCardTest";
//		String deviceid = "";
//		String content = "{\"code\":\"admin123\"}";
//		content = WebConfig.encodeString(content, "UTF-8");
//		String url = "http://10.32.3.91:8080/LAPPServer/json/Login!getUserInfo";
//		String postbody = "url="+url+"&token="+token+"&userid="+userid+"&deviceid="+deviceid+"&content="+content;
		String apiurl = "http://localhost:8080/qwbb/servlet/TestServlet";
		String postbody = "action=aaaaaaa";
		String result = sendPost(apiurl,postbody);
		System.out.println("result="+result);
	}
	
	public static void testSendPut(){
		String url = "http://192.168.84.158:8195/bds/rest/ds/dataset.json";
		JSONObject json = new JSONObject();
		json.put("key", "9E698781AC764BBB9558E942F87E2ECD");
		json.put("description", "添加数据集");
		json.put("charset", "UTF-8");
		json.put("datasetName", "GDBP://MapGISLocal/武汉市01/ds/武汉市/sfcls/new_point");
		json.put("fldnames", "[\"LayerID\"]");
		json.put("fldtypes", "[\"String\"]");
		String param = json.toJSONString();
		String result = sendPut(url,param);
		System.out.println("result="+result);
	}
	
	public static void testSendDelete(){
		String url = "http://192.168.84.158:8195/bds/rest/ds/dataset.json?key=9E698781AC764BBB9558E942F87E2ECD&datasetName=gdbp://MapGisLocal/IMSWebGISGDB/sfcls/buffer110240&datasetType=Point";
		url="http://192.168.84.158:8195/bds/rest/ds/feature/1.json?gdbpUrl=gdbp://MapGisLocal/IMSWebGISGDB/sfcls/buffer11957&key=9E698781AC764BBB9558E942F87E2ECD";
		String result = sendDelete(url);
		System.out.println("result="+result);
	}
	
	
	public static InputStream sendGet_in(String url) {
        InputStream in = null;
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            // 定义 BufferedReader输入流来读取URL的响应
            in = (InputStream)(connection.getInputStream());
        } catch (Exception e) {
            log.error("发送GET请求出现异常！",new Throwable(e));
        	//System.out.println("发送GET请求出现异常！" + e);
            //e.printStackTrace();
        }
        return in;
    }
}
