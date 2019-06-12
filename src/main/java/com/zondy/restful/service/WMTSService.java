package com.zondy.restful.service;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
/**
 * 栅格瓦片地图服务
 * @author lemon
 *
 */
@Path("/wmts")
public interface WMTSService {
	/**
	 * 元数据接口用于获取栅格瓦片地图服务的元数据信息
	 * @param service(必选) 服务类型，栅格瓦片地图服务类型的标识  必须为WMTS     
	 * @param request(必选) 请求操作类型，接口的请求标识         必须为GetCapabilities
	 * @param version 请求的版本号，支持1.0.0版本
	 * @return 
	 */
	@GET
	@Path("/getCapabilities")
	@Produces(value="text/xml")
	public String GetCapabilities(@QueryParam("service")String service,
			@QueryParam("request")String request,
			@QueryParam("version")String version);
	
	/**
	 * 地图数据接口用于获取指定位置的地图瓦片
	 * @param service(必选)    服务类型    必须为WMTS
	 * @param request(必选)    请求的类型 必须为GetTile
	 * @param layer(必选)      瓦片数据名称，根据发布的WMTS服务信息设置 
	 * 					  格式为“服务名称:瓦片名称”，例如“TileTest1:WhMap”
	 * @param tilematrix(必选) 块阵集，即瓦片矩阵名称，主要由坐标系唯一确定
	 *                   瓦片参考系，例如“EPSG:4326_WhMap”
	 * @param tilecol(必选)    列号
	 * @param tilerow(必选)    行号
	 * @param version       请求的版本号，支持1.0.0版本
	 * @param format		返回数据格式,返回数据的存储格式，包括栅格和矢量格式，缺省为栅格格式
	 * 						image/png  image/jpeg
	 * @param level         级数
	 * @return
	 */
	@GET
	@Path("/getTile")
	@Produces(MediaType.MEDIA_TYPE_WILDCARD)
	public Response GetTile(@QueryParam("service")String service,
			@QueryParam("request")String request,
			@QueryParam("layer")String layer,
			@QueryParam("tilematrix")String tilematrix,
			@QueryParam("tilecol")String tilecol,
			@QueryParam("tilerow")String tilerow,
			@QueryParam("version")String version,
			@QueryParam("format")String format,
			@QueryParam("level")int level);
}
