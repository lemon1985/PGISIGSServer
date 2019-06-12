package com.zondy.restful.serviceimpl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ws.rs.core.Response;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zondy.bean.GType;
import com.zondy.bean.GeometryType;
import com.zondy.bean.PGISRequest;
import com.zondy.bean.PGISResponse;
import com.zondy.bean.Relation;
import com.zondy.bean.Unit;
import com.zondy.property.PropertiesConfig;
import com.zondy.restful.service.DataAccessService;
import com.zondy.util.HttpRequest;
import com.zondy.util.MathUtil;
import com.zondy.util.StringUtil;

public class DataAccessServiceImpl implements DataAccessService{
	PropertiesConfig pc=new PropertiesConfig();

	@Override
	public Object mapservice(String xml) {
		JSONObject jo=new JSONObject();
		if(StringUtil.isXML(xml))
		{
			String str=mapseviceXML(xml);
			return Response.ok(str).header("Content-Type","text/xml;charset=UTF-8").build();
		}else if(StringUtil.isJson(xml))
		{
			jo=mapseviceJson(xml);
		}else
		{
			jo.put("code", "-1");
			jo.put("msg", "传入参数不是xml格式或json格式");
		}
		return jo.toString();
	}
	
	public String mapseviceXML(String xml)
	{
		//INSERT SPATIALQUERY   UPDATE DELETE
		if(StringUtil.ifnodeexist(xml, "INSERT"))
		{
			return INSERT_XML(xml);
		}else if(StringUtil.ifnodeexist(xml, "SPATIALQUERY")||StringUtil.ifnodeexist(xml, "BUFFERQUERY"))
		{
			return SPATIALQUERY_XML(xml);
		}else if(StringUtil.ifnodeexist(xml, "UPDATE"))
		{
			return UPDATE_XML(xml);
		}else if(StringUtil.ifnodeexist(xml, "DELETE"))
		{
			return DELETE_XML(xml);
		}else
		{
			JSONObject jo=new JSONObject();
			jo.put("code", "-1");
			jo.put("msg", "xml格式错误!");
			return jo.toString();
		}
	}
	
	public JSONObject mapseviceJson(String xml)
	{
		JSONObject o=JSONObject.parseObject(xml);
		if(xml.contains("\"INSERT\":"))
		{
			return INSERT_Json(xml);
		}else if(o.containsKey("SPATIALQUERY"))
		{
			return SPATIALQUERY_Json(xml);
		}else if(o.containsKey("UPDATE"))
		{
			return UPDATE_Json(xml);
		}else if(o.containsKey("DELETE"))
		{
			return DELETE_Json(xml);
		}else
		{
			JSONObject jo=new JSONObject();
			jo.put("code", "-1");
			jo.put("msg", "json格式错误!");
			return jo;
		}
	}
	
	public String INSERT_XML(String xml)
	{
		PGISRequest request=Analyze_INSERT_XML(xml);
		PGISResponse response=INSERT_IGS(request,"json");
		String jo=Package2XML_INSERT(response);
		//JSONObject jo=JSONObject.parseObject(JSONObject.toJSONString(request));
		return jo;
	}
	
	
	public PGISResponse INSERT_IGS(PGISRequest request,String f)
	{
		PGISResponse rp=new PGISResponse();
		rp.setVersion(request.getVersion());
		rp.setType(request.getGtype().toString());
		rp.setLayer(request.getName());
		String layername=request.getName();
		try {
			layername=URLEncoder.encode(layername, "UTF-8");
		} catch (UnsupportedEncodingException e) {
		}
		String resturl="http://"+pc.getInitParameter("IGSIP")+":"+pc.getInitParameter("WMTSPROT")+
				"/igs/rest/mrfs/layer/addFeatures?gdbp=GDBP://MapGisLocal/map/sfcls/"+layername+"&f="+f;
		String postjson=getpostJson_add(request);
		String result=HttpRequest.sendPost(resturl, postjson);
		rp.setMsg(result);
		return rp;
	}
	
	public PGISResponse UPDATE_IGS(PGISRequest request,String f)
	{
		PGISResponse rp=new PGISResponse();
		rp.setVersion(request.getVersion());
		rp.setType(request.getGtype().toString());
		rp.setLayer(request.getName());
		String where =request.getWhere();
		String objectid_str="";String resturl="";String result="";
		String layername=request.getName();
		try {
			layername=URLEncoder.encode(layername, "UTF-8");
		} catch (UnsupportedEncodingException e) {
		}
		//IGS接口中如果条件中带有objectIds= 那么其他条件就不起作用了
		if(where.contains("objectIds="))
		{
			String[] where_arr=where.split("&");
			for(String s:where_arr)
			{
				if(s.contains("objectIds="))
				{
					objectid_str=s.replace("objectIds=", "");
				}
			}
			
		}else
		{
			try {
				where=URLEncoder.encode(where, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			
			//通过查询接口获取FID 也就是objectIds串
			resturl="http://"+pc.getInitParameter("IGSIP")+":"+pc.getInitParameter("WMTSPROT")+
					"/igs/rest/mrfs/layer/query?gdbp=GDBP://MapGisLocal/map/sfcls/"+layername+"&f=json&page=0&pageCount=10000000&where="+where;
			result=HttpRequest.startGet(resturl);
			if(StringUtil.ifnull(result))
			{
			JSONObject result_json = JSONObject.parseObject(result);
			JSONArray SFEleArray = result_json.getJSONArray("SFEleArray");
			if(SFEleArray!=null)
			{
				for(int i=0;i<SFEleArray.size();i++)
				{
					objectid_str+=SFEleArray.getJSONObject(i).getInteger("FID")+",";
				}
			}
			if(objectid_str.length()>1)objectid_str=objectid_str.substring(0, objectid_str.length()-1);
			}
		}
		
		resturl="http://"+pc.getInitParameter("IGSIP")+":"+pc.getInitParameter("WMTSPROT")+
				"/igs/rest/mrfs/layer/updateFeatures?gdbp=GDBP://MapGisLocal/map/sfcls/"+layername+"&f=json";
		
		String[] fid_arr= objectid_str.split(",");
		if(StringUtil.ifnull(objectid_str))
		{
		objectid_str="";String postjson="";
		for(String fid:fid_arr)
		{
			request.setFid(fid);
			postjson=getpostJson_update(request);
			result=HttpRequest.sendPost(resturl, postjson);
			if(result.equals("true"))
			{
				objectid_str+=fid+",";
			}
		}
		}
		if(objectid_str.length()>1)objectid_str=objectid_str.substring(0, objectid_str.length()-1);
		rp.setId(objectid_str);
		if(StringUtil.ifnull(objectid_str))
		{
			rp.setMsg("true");
			rp.setResult("id为"+objectid_str+"的要素修改成功!");
		}else
		{
			rp.setResult(result);
			rp.setMsg("error");
		}
		return rp;
	}
	
	public PGISResponse SPATIALQUERY_IGS(PGISRequest request,String f)
	{
		PGISResponse rp=new PGISResponse();
		rp.setVersion(request.getVersion());
		rp.setLayer(request.getName());
		String layername=request.getName();
		try {
			layername=URLEncoder.encode(layername, "UTF-8");
		} catch (UnsupportedEncodingException e) {
		}
		String resturl="http://"+pc.getInitParameter("IGSIP")+":"+pc.getInitParameter("WMTSPROT")+
				"/igs/rest/mrfs/layer/query?gdbp=GDBP://MapGisLocal/map/sfcls/"+layername+"&f="+f;
		if(request.getGeometryType()!=null)
			resturl+="&geometryType="+request.getGeometryType().toString();
		if(StringUtil.ifnull(request.getGeometry()))
			resturl+="&geometry="+StringUtil.isnull(request.getGeometry());
		
		int pageCount=request.getFeaturelimit();
		int startindex=request.getBeginrecord();
		resturl+="&pageCount="+request.getFeaturelimit();
		int nowpage=startindex/pageCount;
		if(startindex%pageCount>0)nowpage++;
		resturl+="&page="+nowpage;
		String where=request.getWhere();
		if(StringUtil.ifnull(where))
		{
			if(where.contains("objectIds"))
			{
				for(String s:where.split("and"))
				{
					if(s.contains("objectIds"))
					{
						resturl+=s;
					}
				}
			}else
			{
				try {
					where=URLEncoder.encode(where, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				resturl+="&where="+where;
			}
			
		}
		Relation r=request.getRelation();
		boolean MustInside=true;
		boolean Intersect=true;
		if(r.equals(Relation.joint)) 
		{
			MustInside=false;
		}else if(r.equals(Relation.disjoint)) 
		{
			Intersect=false;
			MustInside=false;
		}
		 resturl+="&rule={MustInside:"+MustInside+",Intersect:"+Intersect+"}";
		 resturl+="&structs={IncludeAttribute:true,IncludeGeometry:true}";
		System.out.println(resturl);
		String result=HttpRequest.startGet(resturl);
		
		JSONObject result_json = JSONObject.parseObject(result);
		JSONArray SFEleArray = result_json.getJSONArray("SFEleArray");
		if(SFEleArray!=null)
		{
			rp.setResult(result);
		}else
		{
			rp.setResult("Error");
		}
		return rp;
	}
	
	public PGISResponse DELETE_IGS(PGISRequest request,String f)
	{
		PGISResponse rp=new PGISResponse();
		rp.setVersion(request.getVersion());
		rp.setType(request.getGtype().toString());
		rp.setLayer(request.getName());
		String where =request.getWhere();
		String objectid_str="";String resturl="";String result="";
		//IGS接口中如果条件中带有objectIds= 那么其他条件就不起作用了
		if(where.contains("objectIds="))
		{
			String[] where_arr=where.split("&");
			for(String s:where_arr)
			{
				if(s.contains("objectIds="))
				{
					objectid_str=s.replace("objectIds=", "");
				}
			}
			
		}else
		{
			try {
				where=URLEncoder.encode(where, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			String layername=request.getName();
			try {
				layername=URLEncoder.encode(layername, "UTF-8");
			} catch (UnsupportedEncodingException e) {
			}
			//通过查询接口获取FID 也就是objectIds串
			resturl="http://"+pc.getInitParameter("IGSIP")+":"+pc.getInitParameter("WMTSPROT")+
					"/igs/rest/mrfs/layer/query?gdbp=GDBP://MapGisLocal/map/sfcls/"+layername+"&f=json&page=0&pageCount=10000000&where="+where;
			result=HttpRequest.startGet(resturl);
			if(StringUtil.ifnull(result))
			{
			JSONObject result_json = JSONObject.parseObject(result);
			JSONArray SFEleArray = result_json.getJSONArray("SFEleArray");
			if(SFEleArray!=null)
			{
				for(int i=0;i<SFEleArray.size();i++)
				{
					objectid_str+=SFEleArray.getJSONObject(i).getInteger("FID")+",";
				}
			}
			if(objectid_str.length()>1)objectid_str=objectid_str.substring(0, objectid_str.length()-1);
			}
		}
		rp.setId(objectid_str);
		String layername=request.getName();
		try {
			layername=URLEncoder.encode(layername, "UTF-8");
		} catch (UnsupportedEncodingException e) {
		}
		resturl="http://"+pc.getInitParameter("IGSIP")+":"+pc.getInitParameter("WMTSPROT")+
				"/igs/rest/mrfs/layer/deleteFeatures?gdbp=GDBP://MapGisLocal/map/sfcls/"+layername+"&f="+f+"&objectIds="+objectid_str;
		result=HttpRequest.sendPost(resturl,"");
		rp.setMsg(result);
		return rp;
	}
	
	public String getpostJson_add(PGISRequest request)
	{
		switch(request.getGtype())
		{
			case POINT:return PackagePOINTJsonForRequest(request);
			case POLYLINE:return PackageLINEJsonForRequest(request);
			case POLYGON:return PackageAreaJsonForRequest(request);
			case MULTIPOINT:return PackagePOINTJsonForRequest(request);
			case MULTIPOLYLINE:return PackageLINEJsonForRequest(request);
			case MULTIPOLYGON:return PackageAreaJsonForRequest(request);
			default:return "";
		}
	}
	
	public String getpostJson_update(PGISRequest request)
	{
		switch(request.getGtype())
		{
			case POINT:return PackagePOINTJsonForRequest(request);
			case POLYLINE:return PackageLINEJsonForRequest(request);
			case POLYGON:return PackageAreaJsonForRequest(request);
			case MULTIPOINT:return PackagePOINTJsonForRequest(request);
			case MULTIPOLYLINE:return PackageLINEJsonForRequest(request);
			case MULTIPOLYGON:return PackageAreaJsonForRequest(request);
			default:return "";
		}
	}
	
	public String PackagePOINTJsonForRequest(PGISRequest request)
	{
		StringBuffer FldName=new StringBuffer();
		int FldNumber=0;
		StringBuffer AttValue=new StringBuffer();
		StringBuffer PntGeom=new StringBuffer();
		String shape="";
		List<HashMap<String, Object>> list=request.getField();
		FldNumber=list.size()+1;
		for(HashMap<String, Object> m:list)
		{
			for (Entry<String, Object> entry : m.entrySet()) {
					if(entry.getKey().equals("shape"))
					{
						shape=entry.getValue().toString();
					}else
					{
						FldName.append("\""+entry.getKey()+"\",");
						AttValue.append("\""+entry.getValue()+"\",");
					}
				
				}
		}
		String[] point=shape.split(",");
		if(point.length%2==0)
		{
			for(int i=0;i<point.length/2;i++)
			{
				PntGeom.append("{\"Dot\": {");
				PntGeom.append("\"x\": "+point[i]+",");
				PntGeom.append("\"y\": "+point[i+1]);
                PntGeom.append("}},");
			}
		}else
		{
			return "";
		}
		if(FldName.length()>0)FldName.deleteCharAt(FldName.length() - 1);
		if(AttValue.length()>0)AttValue.deleteCharAt(AttValue.length() - 1);
		if(PntGeom.length()>0)PntGeom.deleteCharAt(PntGeom.length() - 1);
		
		String returninfo="{"+
							 "\"AttStruct\": {"+
							 "\"FldName\": ["+FldName.toString()+"],"+
							 "\"FldNumber\":"+FldNumber+
									"},"+
					"\"SFEleArray\":["+
						"{"+
							"\"fGeom\": {"+
								"\"PntGeom\": ["+PntGeom+"]"+
										"},"+
							"\"ftype\": 1,"+
							"\"AttValue\":["+AttValue+"]";
		if(StringUtil.ifnull(request.getFid()))
		{
				returninfo+=",\"FID\": \""+request.getFid()+"\"";
		}
		returninfo+="}]}";
		
		return returninfo;
	}
	
	
	public String PackageLINEJsonForRequest(PGISRequest request)
	{
		StringBuffer FldName=new StringBuffer();
		int FldNumber=0;
		StringBuffer AttValue=new StringBuffer();
		StringBuffer LinGeom=new StringBuffer();
		String shape="";
		List<HashMap<String, Object>> list=request.getField();
		FldNumber=list.size()+1;
		for(HashMap<String, Object> m:list)
		{
			for (Entry<String, Object> entry : m.entrySet()) {
					if(entry.getKey().equals("shape"))
					{
						shape=entry.getValue().toString();
					}else
					{
						FldName.append("\""+entry.getKey()+"\",");
						AttValue.append("\""+entry.getValue()+"\",");
					}
				
				}
		}
		String[] Lines=shape.split("\\|");
		if(Lines.length>0)
		{
			for(String line:Lines)
			{
				String[] Dots=line.split(",");
				LinGeom.append("{\"Line\": {\"Arcs\": [{\"Dots\": [");
				if(Dots.length%2==0)
				{
					for(int i=0;i<Dots.length/2;i++)
					{
						LinGeom.append("{");
						LinGeom.append("\"x\": "+Dots[i]+",");
						LinGeom.append("\"y\": "+Dots[i+1]);
						LinGeom.append("},");
					}
				}else
				{
					return "";
				}
				if(LinGeom.length()>0)LinGeom.deleteCharAt(LinGeom.length() - 1);
				LinGeom.append("]}]}},");
			}
		}else
		{
			return "";
		}
		if(FldName.length()>0)FldName.deleteCharAt(FldName.length() - 1);
		if(AttValue.length()>0)AttValue.deleteCharAt(AttValue.length() - 1);
		if(LinGeom.length()>0)LinGeom.deleteCharAt(LinGeom.length() - 1);
		
		
		String returninfo="{"+
								 "\"AttStruct\": {"+
								 "\"FldName\": ["+FldName.toString()+"],"+
								 "\"FldNumber\":"+FldNumber+
										"},"+
						"\"SFEleArray\":["+
							"{"+
								"\"fGeom\": {"+
									"\"LinGeom\": ["+LinGeom+"]"+
											"},"+
								"\"ftype\": 2,"+
								"\"AttValue\":["+AttValue+"]";
		if(StringUtil.ifnull(request.getFid()))
		{
				returninfo+=",\"FID\": \""+request.getFid()+"\"";
		}
		returninfo+="}]}";
		
		
		return returninfo;
	}
	
	
	public String PackageAreaJsonForRequest(PGISRequest request)
	{
		StringBuffer FldName=new StringBuffer();
		int FldNumber=0;
		StringBuffer AttValue=new StringBuffer();
		StringBuffer RegGeom=new StringBuffer();
		String shape="";
		List<HashMap<String, Object>> list=request.getField();
		FldNumber=list.size()+1;
		for(HashMap<String, Object> m:list)
		{
			for (Entry<String, Object> entry : m.entrySet()) {
					if(entry.getKey().equals("shape"))
					{
						shape=entry.getValue().toString();
					}else
					{
						FldName.append("\""+entry.getKey()+"\",");
						AttValue.append("\""+entry.getValue()+"\",");
					}
				
				}
		}
		String[] Areas=shape.split("\\|");
		if(Areas.length>0)
		{
			for(String Area:Areas)
			{
				String[] Dots=Area.split(",");
				RegGeom.append("{\"Rings\": [{\"Arcs\": [{\"Dots\": [");
				if(Dots.length%2==0)
				{
					for(int i=0;i<Dots.length/2;i++)
					{
						RegGeom.append("{");
						RegGeom.append("\"x\": "+Dots[i]+",");
						RegGeom.append("\"y\": "+Dots[i+1]);
						RegGeom.append("},");
					}
				}else
				{
					return "";
				}
				if(RegGeom.length()>0)RegGeom.deleteCharAt(RegGeom.length() - 1);
				RegGeom.append("]}]}]},");
			}
		}else
		{
			return "";
		}
		if(FldName.length()>0){FldName.deleteCharAt(FldName.length() - 1);}
		if(AttValue.length()>0){AttValue.deleteCharAt(AttValue.length() - 1);}
		if(RegGeom.length()>0){RegGeom.deleteCharAt(RegGeom.length() - 1);}
		
		String  returninfo="{"+
							 "\"AttStruct\": {"+
									 "\"FldName\": ["+FldName.toString()+"],"+
									 "\"FldNumber\":"+FldNumber+
											"},"+
							"\"SFEleArray\":["+
								"{"+
									"\"fGeom\": {"+
										"\"RegGeom\": ["+RegGeom+"]"+
												"},"+
									"\"ftype\": 3,"+
									"\"AttValue\":["+AttValue+"]";
		if(StringUtil.ifnull(request.getFid()))
		{
				returninfo+=",\"FID\": \""+request.getFid()+"\"";
		}
		returninfo+="}]}";
		
		
		return returninfo;
	}
	
	
	public String Package2XML_INSERT(PGISResponse response)
	{
		StringBuffer sb = new StringBuffer();
	    sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
	    sb.append("<EASYXML version=\""+response.getVersion()+"\">");
	    
	    if(response.getMsg().equals("true"))
	    {
	    	sb.append("<RESPONSE id=\""+StringUtil.isnull(response.getId())+"\" "
	    			+ "	layer=\""+StringUtil.isnull(response.getLayer())+"\" "
	    					+ " maxrecord=\""+StringUtil.isnull(response.getMaxrecord())+"\" result=\"OK\">");
	    	sb.append("<FEATURES>");
	    	sb.append("<FEATURE type=\""+response.getType()+"\">");
	    	sb.append("</FEATURE>");
	    	sb.append("</FEATURES>");
	    }else
	    {
	    	sb.append("<RESPONSE>");
	    	sb.append("<ERROR message=\""+response.getMsg()+"\"/>");
	    }
	    sb.append("</RESPONSE>");
	    sb.append("</EASYXML>");
	    return sb.toString();
	}
	
	public String Package2XML_UPDATE(PGISResponse response)
	{
		StringBuffer sb = new StringBuffer();
	    sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
	    sb.append("<EASYXML version=\""+response.getVersion()+"\">");
	    
	    if(response.getMsg().equals("true"))
	    {
	    	sb.append("<RESPONSE id=\""+StringUtil.isnull(response.getId())+"\" "
	    			+ "	layer=\""+StringUtil.isnull(response.getLayer())+"\" "
	    					+ " maxrecord=\""+StringUtil.isnull(response.getMaxrecord())+"\" result=\"OK\">");
	    	sb.append("<FEATURES>");
	    	sb.append("<FEATURE type=\""+response.getType()+"\">");
	    	sb.append(response.getResult());
	    	sb.append("</FEATURE>");
	    	sb.append("</FEATURES>");
	    }else
	    {
	    	sb.append("<RESPONSE>");
	    	sb.append("<ERROR message=\""+response.getResult()+"\"/>");
	    }
	    sb.append("</RESPONSE>");
	    sb.append("</EASYXML>");
	    return sb.toString();
	}
	
	public String Package2XML_DELETE(PGISResponse response)
	{
		StringBuffer sb = new StringBuffer();
	    sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
	    sb.append("<EASYXML version=\""+response.getVersion()+"\">");
	    
	    if(response.getMsg().equals("true"))
	    {
	    	sb.append("<RESPONSE id=\""+StringUtil.isnull(response.getId())+"\" "
	    			+ "	layer=\""+StringUtil.isnull(response.getLayer())+"\" "
	    					+ " maxrecord=\""+StringUtil.isnull(response.getMaxrecord())+"\" result=\"OK\">");
	    	sb.append("<FEATURES>");
	    	sb.append("<FEATURE type=\""+response.getType()+"\">");
	    	sb.append("</FEATURE>");
	    	sb.append("</FEATURES>");
	    }else
	    {
	    	sb.append("<RESPONSE>");
	    	sb.append("<ERROR message=\""+response.getMsg()+"\"/>");
	    }
	    sb.append("</RESPONSE>");
	    sb.append("</EASYXML>");
	    return sb.toString();
	}
	
	public String Package2XML_SPATIALQUERY(PGISResponse response,PGISRequest request)
	{
		StringBuffer sb = new StringBuffer();
	    sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
	    sb.append("<EASYXML version=\""+response.getVersion()+"\">");
	    
	    if(response.getResult().equals("Error"))
	    {
	    	sb.append("<RESPONSE>");
	    	sb.append("<ERROR message=\""+response.getMsg()+"\"/>");
	    	
	    }else
	    {
	    	JSONObject result=JSONObject.parseObject(response.getResult());
	    	int  TotalCount = result.getInteger("TotalCount");
	    	
	    	sb.append("<RESPONSE id=\""+StringUtil.isnull(response.getId())+"\" "
	    			+ "	layer=\""+StringUtil.isnull(request.getName())+"\" "
	    					+ " maxrecord=\""+TotalCount+"\" result=\"OK\">");
	    	
	    	JSONArray  FldNamelist = result.getJSONObject("AttStruct").getJSONArray("FldName");
//	    	JSONArray  FldTypelist = result.getJSONObject("AttStruct").getJSONArray("FldType");
	    	JSONArray  FldAliaslist= result.getJSONObject("AttStruct").getJSONArray("FldAlias");
	    	String FldName="";String FldAlias="";
	    	for(int i=0;i<FldNamelist.size();i++)
	    	{
	    		FldName+=FldNamelist.getString(i)+",";
	    	}
	    	for(int i=0;i<FldAliaslist.size();i++)
	    	{
	    		FldAlias+=FldAliaslist.getString(i)+",";
	    	}
	    	if(FldName.length()>1)FldName=FldName.substring(0, FldName.length()-1);
	    	if(FldAlias.length()>1)FldAlias=FldAlias.substring(0, FldAlias.length()-1);
	    	JSONArray  SFEleArray = result.getJSONArray("SFEleArray");
	    	double centerx=0.0,centery=0.0;
	    	String FID="";String shape="";GType type;
	    	double xmin=0.0;double ymin=0.0;double xmax=0.0; double ymax=0.0;
	    	sb.append("<FEATURES>");
	    	for (int i=0;i<SFEleArray.size();i++) {
	    		shape="";FID="";
	    		JSONObject temp=SFEleArray.getJSONObject(i);
				FID = temp.getInteger("FID").toString();
				xmin=temp.getJSONObject("bound").getDouble("xmin");
				ymin=temp.getJSONObject("bound").getDouble("ymin");
				xmax=temp.getJSONObject("bound").getDouble("xmax");
				ymax=temp.getJSONObject("bound").getDouble("ymax");
				centerx=(xmin+xmax)/2;centery=(ymin+ymax)/2;
				JSONArray AttValue=temp.getJSONArray("AttValue");
				int ftype=temp.getInteger("ftype");
				if(ftype==1)
				{
					//point or multipoint
					
					JSONArray PntGeom=temp.getJSONObject("fGeom").getJSONArray("PntGeom");
					if(PntGeom.size()>1)
					{
						type=GType.MULTIPOINT;
						for(int k=0;k<PntGeom.size();k++)
						{
							shape+=PntGeom.getJSONObject(k).getJSONObject("Dot").getDouble("x")+
									","+PntGeom.getJSONObject(k).getJSONObject("Dot").getDouble("y")+",";
						}
						if(shape.length()>1)shape=shape.substring(0, shape.length()-1);
					}else
					{
						type=GType.POINT;
						shape=PntGeom.getJSONObject(0).getJSONObject("Dot").getDouble("x")+
								","+PntGeom.getJSONObject(0).getJSONObject("Dot").getDouble("y");
					}
					
					sb.append("<FEATURE type=\""+type.toString()+"\" centerx=\""+centerx+"\" centery=\""+centery+"\" dispname=\""+FldAlias+"\">");
					
					sb.append("<FIELD name=\"objectid\" value=\""+FID+"\" />");
					if(AttValue.size()==FldNamelist.size())
					{
						for(int p=0;p<AttValue.size();p++)
						{
							sb.append("<FIELD name=\""+FldNamelist.getString(p)+"\" value=\""+AttValue.getString(p)+"\" />");
						}
					}
					sb.append("<FIELD name=\"shape\" value=\""+shape+"\" />");
					sb.append("</FEATURE>");
				}else if(ftype==2)
				{
					//polyline or multipolyline
					JSONArray LinGeom=temp.getJSONObject("fGeom").getJSONArray("LinGeom");
					if(LinGeom.size()>1)
					{
						type=GType.MULTIPOLYLINE;
						for(int k=0;k<LinGeom.size();k++)
						{
							JSONArray Dots=LinGeom.getJSONObject(k).getJSONObject("Line").getJSONArray("Arcs").getJSONObject(0).getJSONArray("Dots");
							for(int p=0;p<Dots.size();p++)
							{
								shape+=Dots.getJSONObject(p).getDouble("x")+
										","+Dots.getJSONObject(p).getDouble("y")+",";
							}
							if(shape.length()>1)shape=shape.substring(0, shape.length()-1);
							shape+="|";
						}
						if(shape.length()>1)shape=shape.substring(0, shape.length()-1);
					}else
					{
						type=GType.POLYLINE;
						JSONArray Dots=LinGeom.getJSONObject(0).getJSONObject("Line").getJSONArray("Arcs").getJSONObject(0).getJSONArray("Dots");
						for(int p=0;p<Dots.size();p++)
						{
							shape+=Dots.getJSONObject(p).getDouble("x")+
									","+Dots.getJSONObject(p).getDouble("y")+",";
						}
						if(shape.length()>1)shape=shape.substring(0, shape.length()-1);
					}
					
					sb.append("<FEATURE type=\""+type.toString()+"\" centerx=\""+centerx+"\" centery=\""+centery+"\" dispname=\""+FldAlias+"\">");
					
					sb.append("<FIELD name=\"objectid\" value=\""+FID+"\" />");
					if(AttValue.size()==FldNamelist.size())
					{
						for(int p=0;p<AttValue.size();p++)
						{
							sb.append("<FIELD name=\""+FldNamelist.getString(p)+"\" value=\""+AttValue.getString(p)+"\" />");
						}
					}
					sb.append("<FIELD name=\"shape\" value=\""+shape+"\" />");
					sb.append("</FEATURE>");
				}else if(ftype==3)
				{
					//polygon or multipolygon
					JSONArray RegGeom=temp.getJSONObject("fGeom").getJSONArray("RegGeom");
					if(RegGeom.size()>1)
					{
						type=GType.MULTIPOLYGON;
						for(int k=0;k<RegGeom.size();k++)
						{
							JSONArray Dots=RegGeom.getJSONObject(k).getJSONArray("Rings").getJSONObject(0).getJSONArray("Arcs").getJSONObject(0).getJSONArray("Dots");
							for(int p=0;p<Dots.size();p++)
							{
								shape+=Dots.getJSONObject(p).getDouble("x")+
										","+Dots.getJSONObject(p).getDouble("y")+",";
							}
							if(shape.length()>1)shape=shape.substring(0, shape.length()-1);
							shape+="|";
						}
						if(shape.length()>1)shape=shape.substring(0, shape.length()-1);
					}else
					{
						type=GType.POLYGON;
						JSONArray Dots=RegGeom.getJSONObject(0).getJSONArray("Rings").getJSONObject(0).getJSONArray("Arcs").getJSONObject(0).getJSONArray("Dots");
						for(int p=0;p<Dots.size();p++)
						{
							shape+=Dots.getJSONObject(p).getDouble("x")+
									","+Dots.getJSONObject(p).getDouble("y")+",";
						}
						if(shape.length()>1)shape=shape.substring(0, shape.length()-1);
					}
					
					sb.append("<FEATURE type=\""+type.toString()+"\" centerx=\""+centerx+"\" centery=\""+centery+"\" dispname=\""+FldAlias+"\">");
					
					sb.append("<FIELD name=\"objectid\" value=\""+FID+"\" />");
					if(AttValue.size()==FldNamelist.size())
					{
						for(int p=0;p<AttValue.size();p++)
						{
							sb.append("<FIELD name=\""+FldNamelist.getString(p)+"\" value=\""+AttValue.getString(p)+"\" />");
						}
					}
					sb.append("<FIELD name=\"shape\" value=\""+shape+"\" />");
					sb.append("</FEATURE>");
				}
			}
	    	
	    	sb.append("</FEATURES>");
	    }
	    sb.append("</RESPONSE>");
	    sb.append("</EASYXML>");
	    return sb.toString();
	}

	public JSONObject Package2Json_INSERT(PGISResponse response)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("{");
	    if(response.getMsg().equals("true"))
	    {
	    	sb.append("\"RESPONSE\": { "
	    				+ "\"@id\": \""+StringUtil.isnull(response.getId())+"\", "
	    				+ "\"@layer\": \""+StringUtil.isnull(response.getLayer())+"\", "
	    				+ "\"@maxrecord\": \""+StringUtil.isnull(response.getMaxrecord())+"\", "
	    				+ " \"@result\": \"OK\"},");
	    	sb.append("\"FEATURES\": [{");
	    	sb.append("\"@FEATURE\": \""+response.getType()+"\"");
	    	sb.append("}]");
	    }else
	    {
	    	sb.append("\"RESPONSE\": {");
	    	sb.append("\"ERROR\": {\"@message\": \""+response.getMsg()+"\"}}");
	    }
	    sb.append("}");
	    return JSONObject.parseObject(sb.toString());
	}
		
	
	@SuppressWarnings("unchecked")
	public PGISRequest Analyze_INSERT_XML(String xml)
	{
		PGISRequest request=new PGISRequest();
		Document document;
		try {
			document = DocumentHelper.parseText(xml);
			Element root = document.getRootElement();
			Element table=root.element("REQUEST").element("EDIT_ATTRS").element("TABLE");
			request.setVersion(root.attributeValue("version"));
			request.setName(table.attributeValue("name"));
			request.setGtype(Enum.valueOf(GType.class, table.attributeValue("gtype")));
			List<Element> fieldlist = table.element("INSERT").element("FIELDS").elements();
			List<HashMap<String, Object>> fieldMap=new ArrayList<HashMap<String, Object>>();
			String namestr="";String valuestr="";String[] name_arr,value_arr;
			HashMap<String, Object> map=new HashMap<String, Object>();
			for (Element field : fieldlist) {
				List<Attribute> attributeList = field.attributes();
	            for (Attribute attr : attributeList) {
	            	if(attr.getName().equals("name"))
	            	{
	            		namestr+=attr.getValue()+";";
	            	}else if(attr.getName().equals("value"))
	            	{
	            		valuestr+=attr.getValue()+";";
	            	}
	            	//map.put(attr.attribute("name")., attr.getValue());
	                System.out.println(attr.getName() + ": " + attr.getValue());
	            }
			}
			 if(namestr.length()>1)namestr=namestr.substring(0, namestr.length()-1);
	            if(valuestr.length()>1)valuestr=valuestr.substring(0, valuestr.length()-1);
	            name_arr=namestr.split(";");value_arr=valuestr.split(";");
	            if(name_arr.length==value_arr.length)
	            {
	            	for(int i=0;i<name_arr.length;i++)
		            {
		            	map.put(name_arr[i], value_arr[i]);
		            }
	            }
	            fieldMap.add(map);
			request.setField(fieldMap);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return request;
	}
	
	@SuppressWarnings("unchecked")
	public PGISRequest Analyze_UPDATE_XML(String xml)
	{
		PGISRequest request=new PGISRequest();
		Document document;
		try {
			document = DocumentHelper.parseText(xml);
			Element root = document.getRootElement();
			Element table=root.element("REQUEST").element("EDIT_ATTRS").element("TABLE");
			request.setVersion(root.attributeValue("version"));
			request.setName(table.attributeValue("name"));
			request.setGtype(Enum.valueOf(GType.class, table.attributeValue("gtype")));
			request.setWhere(table.element("UPDATE").attributeValue("where"));
			List<Element> fieldlist = table.element("UPDATE").element("FIELDS").elements();
			List<HashMap<String, Object>> fieldMap=new ArrayList<HashMap<String, Object>>();
			String namestr="";String valuestr="";String[] name_arr,value_arr;
			HashMap<String, Object> map=new HashMap<String, Object>();
			for (Element field : fieldlist) {
				List<Attribute> attributeList = field.attributes();
	            for (Attribute attr : attributeList) {
	            	if(attr.getName().equals("name"))
	            	{
	            		namestr+=attr.getValue()+";";
	            	}else if(attr.getName().equals("value"))
	            	{
	            		valuestr+=attr.getValue()+";";
	            	}
	            	//map.put(attr.attribute("name")., attr.getValue());
	                System.out.println(attr.getName() + ": " + attr.getValue());
	            }
			}
			 if(namestr.length()>1)namestr=namestr.substring(0, namestr.length()-1);
	            if(valuestr.length()>1)valuestr=valuestr.substring(0, valuestr.length()-1);
	            name_arr=namestr.split(";");value_arr=valuestr.split(";");
	            if(name_arr.length==value_arr.length)
	            {
	            	for(int i=0;i<name_arr.length;i++)
		            {
		            	map.put(name_arr[i], value_arr[i]);
		            }
	            }
	            fieldMap.add(map);
			request.setField(fieldMap);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return request;
	}
	
	
	public PGISRequest Analyze_DELETE_XML(String xml)
	{
		PGISRequest request=new PGISRequest();
		Document document;
		try {
			document = DocumentHelper.parseText(xml);
			Element root = document.getRootElement();
			Element table=root.element("REQUEST").element("EDIT_ATTRS").element("TABLE");
			request.setVersion(root.attributeValue("version"));
			request.setName(table.attributeValue("name"));
			request.setGtype(Enum.valueOf(GType.class, table.attributeValue("gtype")));
			request.setWhere(table.element("DELETE").attributeValue("where"));
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return request;
	}
	
	public String SPATIALQUERY_XML(String xml)
	{
		PGISRequest request=Analyze_SPATIALQUERY_XML(xml);
		PGISResponse response=SPATIALQUERY_IGS(request,"json");
		String jo=Package2XML_SPATIALQUERY(response,request);
		return jo.toString();
	}
	
	public String UPDATE_XML(String xml)
	{
		PGISRequest request=Analyze_UPDATE_XML(xml);
		PGISResponse response=UPDATE_IGS(request,"json");
		String jo=Package2XML_UPDATE(response);
		return jo.toString();
	}
	
	public String DELETE_XML(String xml)
	{
		PGISRequest request=Analyze_DELETE_XML(xml);
		PGISResponse response=DELETE_IGS(request,"json");
		String jo=Package2XML_DELETE(response);
		//JSONObject jo=JSONObject.parseObject(JSONObject.toJSONString(request));
		return jo.toString();
	}
	
	public JSONObject INSERT_Json(String xml)
	{
		PGISRequest request=Analyze_INSERT_JSON(xml);
		PGISResponse response=INSERT_IGS(request,"json");
		JSONObject jo=Package2Json_INSERT(response);
		//JSONObject jo=JSONObject.parseObject(JSONObject.toJSONString(request));
		return jo;
	}
	
	public PGISRequest Analyze_INSERT_JSON(String xml)
	{
		PGISRequest request=new PGISRequest();
		JSONObject jsonObject = JSONObject.parseObject(xml);
		JSONObject root = jsonObject.getJSONObject("JSON");
		request.setVersion(root.getString("-version"));
		JSONObject table=root.getJSONObject("REQUEST").getJSONObject("EDIT_ATTRS").getJSONObject("TABLE");
		request.setName(table.getString("-name"));
		request.setGtype(Enum.valueOf(GType.class, table.getString("-gtype")));
		//JSONObject FIELDS=table.getJSONObject("INSERT").getJSONObject("FIELDS");
		JSONArray  fieldlist = table.getJSONObject("INSERT").getJSONArray("FIELDS");
		List<HashMap<String, Object>> fieldMap=new ArrayList<HashMap<String, Object>>();
		
		String namestr="";String valuestr="";String[] name_arr,value_arr;
		HashMap<String, Object> map=new HashMap<String, Object>();
		for (int i=0;i<fieldlist.size();i++) {
			JSONObject job = fieldlist.getJSONObject(i).getJSONObject("FIELD"); 
			for (Map.Entry<String, Object> entry : job.entrySet()) {
				if(entry.getKey().equals("-name"))
            	{
            		namestr+=entry.getValue()+";";
            	}else if(entry.getKey().equals("-value"))
            	{
            		valuestr+=entry.getValue()+";";
            	}
			}
		}
		if(namestr.length()>1)namestr=namestr.substring(0, namestr.length()-1);
        if(valuestr.length()>1)valuestr=valuestr.substring(0, valuestr.length()-1);
        name_arr=namestr.split(";");value_arr=valuestr.split(";");
        if(name_arr.length==value_arr.length)
        {
        	for(int k=0;k<name_arr.length;k++)
            {
            	map.put(name_arr[k], value_arr[k]);
            }
        }
        fieldMap.add(map);
		request.setField(fieldMap);
		return request;
	}
	
	@SuppressWarnings("unchecked")
	public PGISRequest Analyze_SPATIALQUERY_XML(String xml)
	{
		PGISRequest request=new PGISRequest();
		Document document;
		try {
			document = DocumentHelper.parseText(xml);
			Element root = document.getRootElement();
			request.setVersion(root.attributeValue("version"));
			Element GET_FEATURES=root.element("REQUEST").element("GET_FEATURES");
			request.setFeaturelimit(Integer.parseInt(GET_FEATURES.attributeValue("featurelimit")));
			request.setBeginrecord(Integer.parseInt(GET_FEATURES.attributeValue("beginrecord")));
			//判断是哪种类型的查询
			if(StringUtil.ifnodeexist(xml, "SPATIALQUERY"))
			{
				Element LAYER=GET_FEATURES.element("LAYER");
				Element SPATIALQUERY=LAYER.element("SPATIALQUERY");
				Element SPATIALFILTER=SPATIALQUERY.element("SPATIALFILTER");
				request.setId(LAYER.attributeValue("id"));
				request.setName(LAYER.attributeValue("name"));
				request.setSubfields(SPATIALQUERY.attributeValue("subfields"));
				request.setDispfield(SPATIALQUERY.attributeValue("dispfield"));
				request.setWhere(SPATIALQUERY.attributeValue("where"));
				request.setRelation(Enum.valueOf(Relation.class, SPATIALFILTER.attributeValue("relation")));
				String distance=SPATIALFILTER.attributeValue("distance");
				if(StringUtil.ifnull(SPATIALFILTER.attributeValue("unit")))
						{
				Unit unit=Enum.valueOf(Unit.class, SPATIALFILTER.attributeValue("unit"));
				
				//方便IGS服务调用，统一转化degree to meter
				if(unit.equals(Unit.degree))
				{
					//如果距离参数为空，那么设置默认距离为100米
					if("".equals(StringUtil.isnull(distance))){distance="100";}
					distance=MathUtil.degree2meter(Double.parseDouble(distance))+"";
				}
				request.setDistance(distance);
				request.setUnit(Unit.meter);
						}
				//空间：点、拉框、多边形、圆
				StringBuffer geometry=new StringBuffer();
				if(StringUtil.ifnodeexist(xml, "MULTIPOINT"))
				{
					request.setGeometryType(GeometryType.point);
					//点
					List<Element> POINTList=SPATIALFILTER.element("MULTIPOINT").elements();
					for(Element POINT:POINTList)
					{
						geometry.append(POINT.attributeValue("x")+","+POINT.attributeValue("y")+",");
					}
					geometry.append(distance);
				}else if(StringUtil.ifnodeexist(xml, "CIRCLE"))
				{
					request.setGeometryType(GeometryType.circle);
					//圆查询
					Element CIRCLE =SPATIALFILTER.element("ENVELOPE").element("CIRCLE");
					Unit unit_=Enum.valueOf(Unit.class, CIRCLE.attributeValue("unit"));
					String radius=CIRCLE.attributeValue("radius");
					//方便IGS服务调用，统一转化degree to meter
					if(unit_.equals(Unit.degree))
					{
						radius=MathUtil.degree2meter(Double.parseDouble(radius))+"";
					}
					geometry.append(CIRCLE.attributeValue("xx")+","+
							CIRCLE.attributeValue("yy")+","+radius);
				}else if(StringUtil.ifnodeexist(xml, "ENVELOPE"))
				{
					request.setGeometryType(GeometryType.rect);
					//拉框查询
					Element ENVELOPE =SPATIALFILTER.element("ENVELOPE");
					geometry.append(ENVELOPE.attributeValue("minx")+","+
							ENVELOPE.attributeValue("miny")+","+
							ENVELOPE.attributeValue("maxx")+","+
							ENVELOPE.attributeValue("maxy"));
				}else if(StringUtil.ifnodeexist(xml, "POLYGON"))
				{
					request.setGeometryType(GeometryType.polygon);
					//多边形查询
					List<Element> POINTList=SPATIALFILTER.element("POLYGON").element("RING").elements();
					for(Element POINT:POINTList)
					{
						geometry.append(POINT.attributeValue("x")+","+POINT.attributeValue("y")+",");
					}
					if(geometry.length()>0)geometry.deleteCharAt(geometry.length()-1);
				}
				request.setGeometry(geometry.toString());
			}else if(StringUtil.ifnodeexist(xml, "BUFFERQUERY"))
			{
				//缓冲查询 目前api只看到点和线的缓冲
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return request;
	} 
	
	public JSONObject SPATIALQUERY_Json(String xml)
	{
		return null;
	}
	
	public JSONObject UPDATE_Json(String xml)
	{
		return null;
	}
	
	public JSONObject DELETE_Json(String xml)
	{
		return null;
	}
}
