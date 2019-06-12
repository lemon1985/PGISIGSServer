package com.zondy.bean;

import java.util.HashMap;

public class PGISResponse {
	private String version;	//版本号
	private String id;		//唯一标识
	private String layer;	//操作表名称
	private String maxrecord;//最大记录数
	private String result;	//操作结果   OK/Error
	private String type;    //特征类型
	private String msg;		//错误消息
	private double centerx;	//中心经度
	private double centery;	//中心纬度
	private String dispname;//展示字段
	private String linestr;	//几何字段文本值
	private HashMap<String, Object> field;//表中字段
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLayer() {
		return layer;
	}
	public void setLayer(String layer) {
		this.layer = layer;
	}
	public String getMaxrecord() {
		return maxrecord;
	}
	public void setMaxrecord(String maxrecord) {
		this.maxrecord = maxrecord;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public double getCenterx() {
		return centerx;
	}
	public void setCenterx(double centerx) {
		this.centerx = centerx;
	}
	public double getCentery() {
		return centery;
	}
	public void setCentery(double centery) {
		this.centery = centery;
	}
	public String getDispname() {
		return dispname;
	}
	public void setDispname(String dispname) {
		this.dispname = dispname;
	}
	public String getLinestr() {
		return linestr;
	}
	public void setLinestr(String linestr) {
		this.linestr = linestr;
	}
	public HashMap<String, Object> getField() {
		return field;
	}
	public void setField(HashMap<String, Object> field) {
		this.field = field;
	}
	
	
}
