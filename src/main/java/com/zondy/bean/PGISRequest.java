package com.zondy.bean;

import java.util.HashMap;
import java.util.List;

public class PGISRequest {
	private String version;				//版本
	private String name;				//操作表名称
	private String id;					//操作表在空间库中的id
	private GType gtype;				//几何类型 	POINT,POLYLINE,POLYGON,MULTIPOINT,MULTIPOLYLINE,MULTIPOLYGON
	private List<HashMap<String, Object>> field;//表中字段
	private int featurelimit;			//分页查询的最大条数
	private int beginrecord;			//分页查询的开始数
	private String subfields;			//操作表中的地址字段 固定MC
	private String dispfield;			//操作表中显示地址字段 固定	MC
	private String where;				//执行sql语句的条件判断
	private Relation relation;			//空间过滤关系 	固定 contain、joint、disjoint
	private String distance;			//与查询点之间的距离
	private Unit unit;					//单位
	private Unit bufferunits;			//缓冲区单位
	private Unit srcunit;				//源单位
	private Point point; 
	private List<Point> envelope;
	private List<Point> polygon;
	private double radius;
	private String coords;				//经纬度坐标中间以逗号隔开\
	private String geometry;
	private GeometryType geometryType;
	private String fid;
	
	
	
	public String getFid() {
		return fid;
	}
	public void setFid(String fid) {
		this.fid = fid;
	}
	public GeometryType getGeometryType() {
		return geometryType;
	}
	public void setGeometryType(GeometryType geometryType) {
		this.geometryType = geometryType;
	}
	public String getGeometry() {
		return geometry;
	}
	public void setGeometry(String geometry) {
		this.geometry = geometry;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public GType getGtype() {
		return gtype;
	}
	public void setGtype(GType gtype) {
		this.gtype = gtype;
	}
	
	public List<HashMap<String, Object>> getField() {
		return field;
	}
	public void setField(List<HashMap<String, Object>> field) {
		this.field = field;
	}
	public int getFeaturelimit() {
		return featurelimit;
	}
	public void setFeaturelimit(int featurelimit) {
		this.featurelimit = featurelimit;
	}
	public int getBeginrecord() {
		return beginrecord;
	}
	public void setBeginrecord(int beginrecord) {
		this.beginrecord = beginrecord;
	}
	public String getSubfields() {
		return subfields;
	}
	public void setSubfields(String subfields) {
		this.subfields = subfields;
	}
	public String getDispfield() {
		return dispfield;
	}
	public void setDispfield(String dispfield) {
		this.dispfield = dispfield;
	}
	public String getWhere() {
		return where;
	}
	public void setWhere(String where) {
		this.where = where;
	}
	public Relation getRelation() {
		return relation;
	}
	public void setRelation(Relation relation) {
		this.relation = relation;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	public Unit getUnit() {
		return unit;
	}
	public void setUnit(Unit unit) {
		this.unit = unit;
	}
	public Unit getBufferunits() {
		return bufferunits;
	}
	public void setBufferunits(Unit bufferunits) {
		this.bufferunits = bufferunits;
	}
	public Unit getSrcunit() {
		return srcunit;
	}
	public void setSrcunit(Unit srcunit) {
		this.srcunit = srcunit;
	}
	public Point getPoint() {
		return point;
	}
	public void setPoint(Point point) {
		this.point = point;
	}
	public List<Point> getEnvelope() {
		return envelope;
	}
	public void setEnvelope(List<Point> envelope) {
		this.envelope = envelope;
	}
	public List<Point> getPolygon() {
		return polygon;
	}
	public void setPolygon(List<Point> polygon) {
		this.polygon = polygon;
	}
	public double getRadius() {
		return radius;
	}
	public void setRadius(double radius) {
		this.radius = radius;
	}
	public String getCoords() {
		return coords;
	}
	public void setCoords(String coords) {
		this.coords = coords;
	}
	
	
}
