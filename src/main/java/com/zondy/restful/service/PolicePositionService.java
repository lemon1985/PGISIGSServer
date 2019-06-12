package com.zondy.restful.service;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.alibaba.fastjson.JSONObject;

/**
 * 警力定位服务
 * @author lemon
 *
 */
@Path("/gps")
public interface PolicePositionService {
	
	/**
	 * 定位设备信息注册
	 * @param gpsid				设备编号     		必须
	 * @param policetypeid		警种类型大类        必须
	 * @param detailpolicetype  警种类型小类        否
	 * @param gpsstyleid		装备类型大类        是
	 * @param detaildevtype		装备类型小类        否
	 * @param loctype			定位类型	          是
	 * @param sourcetype		设备来源		否
	 * @param djdz				直属单位		是
	 * @param carno				车牌号码		否
	 * @param policeid			警员编号		否
	 * @param policename		警员名称		否
	 * @param callno			呼号			否
	 * @param orgid				三级节点名称 	是
	 * @param xnorgid			虚拟节点		否
	 * @param neturl			警力定位信息接入系统地址  	否
	 * @param gjurl				历史定位信息联网服务地址	否
	 * @param remark			备注
	 * @return
	 */
	@POST
	@Path("/registerGPSList")
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public JSONObject RegisterGPSList(@QueryParam("gpsid")String gpsid,
			@QueryParam("policetypeid")String policetypeid,
			@QueryParam("detailpolicetype")String detailpolicetype,
			@QueryParam("gpsstyleid")String gpsstyleid,
			@QueryParam("detaildevtype")String detaildevtype,
			@QueryParam("loctype")String loctype,
			@QueryParam("sourcetype")String sourcetype,
			@QueryParam("djdz")String djdz,
			@QueryParam("carno")String carno,
			@QueryParam("policeid")String policeid,
			@QueryParam("policename")String policename,
			@QueryParam("callno")String callno,
			@QueryParam("orgid")String orgid,
			@QueryParam("xnorgid")String xnorgid,
			@QueryParam("neturl")String neturl,
			@QueryParam("gjurl")String gjurl,
			@QueryParam("remark")String remark
			);
	
	/**
	 * 联网轨迹查询接口
	 * @param gpsid			设备唯一编码   							是
	 * @param begintime		轨迹起始时间（yyyy-MM-dd HH:mm:ss）		是
	 * @param endtime		轨迹结束时间（yyyy-MM-dd HH:mm:ss）		是
	 * @param level			可选 抽稀级别，0为不抽稀，1为抽稀一半，
	 * 						2为大比例抽稀默认为0						否
	 * 						
	 * @param type			返回类型，points[原始点集]，simplepoints=[抽稀点]，info[轨迹详情] 默认为points
	 * @param isfiltererror	是否过滤停留点、异常点 1-是 0-否                             该字段不明所指，暂未启用
	 * @return
	 */
	@GET
	@Path("/getNetTrack")
	@Produces({MediaType.APPLICATION_JSON})
	public JSONObject getNetTrack(@QueryParam("gpsid")String gpsid,
			@QueryParam("begintime")String begintime,
			@QueryParam("endtime")String endtime,
			@QueryParam("level")String level,
			@QueryParam("type")String type,
			@QueryParam("isfiltererror")String isfiltererror);
	
	/**
	 * 联网设备查询
	 * @param username			目录节点用户名
	 * @param password			目录节点用户密码
	 * @param policetypeid		警种类型id
	 * @param loctypeid			定位类型id
	 * @param gpsstyleid		设备类型id
	 * @return
	 */
	@GET
	@Path("/queryGPSList")
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public JSONObject QueryGPSList(@QueryParam("username")String username,
			@QueryParam("password")String password,
			@QueryParam("policetypeid")int policetypeid,
			@QueryParam("loctypeid")int loctypeid,
			@QueryParam("gpsstyleid")int gpsstyleid);
}
