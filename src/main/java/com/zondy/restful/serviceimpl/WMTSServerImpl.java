package com.zondy.restful.serviceimpl;

import java.io.IOException;

import javax.ws.rs.core.Response;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.zondy.property.PropertiesConfig;
import com.zondy.restful.service.WMTSService;
import com.zondy.util.ImageUtil;

public class WMTSServerImpl implements WMTSService{
	PropertiesConfig pc=new PropertiesConfig();
	
	private String ip=pc.getInitParameter("IGSIP");
	private String wmtsprot=pc.getInitParameter("WMTSPROT");
	private RestTemplate restTemplate;
	
	public RestTemplate getRestTemplate() {
		return restTemplate;
	}

	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@Override
	public String GetCapabilities(String service, String request, String version) {
		if("".equals(version))
		{
			version="1.0.0";
		}
		String url="http://"+ip+":"+wmtsprot+"/igs/rest/ogc/WMTSServer?SERVICE=WMTS&VERSION={1}&REQUEST=GetCapabilities";
		ResponseEntity<String> re = restTemplate.getForEntity(url, String.class,version);
		return re.getBody();
	}
	
	@Override
	public Response GetTile(String service, String request, String layer, String tilematrix, String tilecol,
			String tilerow, String version, String format, int level) {
		if("".equals(version))
		{
			version="1.0.0";
		}
		if("".equals(format))
		{
			format="image/png";
		}
		if(level==0)
		{
			level=1;
		}
		String url="http://"+ip+":"+wmtsprot+"/igs/rest/ogc/WMTSServer?service=WMTS&request=GetTile&version={1}"
		+"&layer={2}&format={3}&tilematrixset={3}&tilematrix={4}&tilerow={5}&tilecol={6}";
		ResponseEntity<byte[]> re = restTemplate.getForEntity(url, byte[].class,version,layer,format,tilematrix,level,tilerow,tilecol);
		byte[] bytes=re.getBody();
		//test
		//String pic = "http://pic39.nipic.com/20140226/18071023_152956865000_2.jpg";
	   // bytes = ImageUtil.getFileStream(pic);
		try {
			if(ImageUtil.getImageType(bytes).equals("noPic"))
			{
				ResponseEntity<String> re_ = restTemplate.getForEntity(url, String.class,version,layer,format,tilematrix,level,tilerow,tilecol);
				return Response.ok(re_.getBody()).header("Content-Type","text/xml;charset=UTF-8").build();
			}else
			{
				System.out.println(ImageUtil.getImageType(bytes));
				return Response.ok(bytes).header("Content-Type","image/jpeg;charset=UTF-8").build();
			}
		} catch (IOException e) {
			return Response.ok(e.getMessage()).header("Content-Type","text/xml;charset=UTF-8").build();
		}
		
	}
	
}
