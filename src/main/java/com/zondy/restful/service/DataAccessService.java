package com.zondy.restful.service;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.alibaba.fastjson.JSONObject;

/**
 * 数据访问更新服务
 * @author lemon
 *
 */
@Path("/data")
public interface DataAccessService {

	@POST
	@Path("/mapservice")
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public Object mapservice(@QueryParam("xml")String xml);
}
