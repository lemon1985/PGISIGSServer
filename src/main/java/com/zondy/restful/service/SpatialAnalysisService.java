package com.zondy.restful.service;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.alibaba.fastjson.JSONObject;

/**
 * 空间分析服务 
 * @author lemon
 *
 */
@Path("/analysis")
public interface SpatialAnalysisService {
	/**
	 * 图层叠加
	 * @param srcinfo1	被叠加简单要素类的信息：
	 * 					格式"GDBP://user:password @ serverName/gdbName /ds/ds06/sfcls/www.wl"
	 * @param srcinfo2	叠加简单要素类的信息,
	 * 					格式"GDBP://user:password @ serverName/gdbName /ds/ds06/sfcls/www.wp"
	 * @param desinfo	结果简单要素类信息
	 * @param attopttype 是否进行属性操作
	 * @param infoopttype 共有部分的图形参数操作
	 * @param overtype		叠加类型
	 * @param isCleanNode	是否节点平差
	 * @param isLabelPnt	是否label点方式
	 * @param isValidReg	是否检查区的合法性
	 * @param isReCalculate	是否重算面积
	 * @param radius		容差半径
	 * @return		分析结果要素名称
	 */
	@POST
	@Path("/overlay")
	@Produces({MediaType.APPLICATION_JSON})
	public JSONObject overlay(@QueryParam("srcinfo1")String srcinfo1,
			@QueryParam("srcinfo2")String srcinfo2,
			@QueryParam("desinfo")String desinfo,
			@QueryParam("attopttype")String attopttype,
			@QueryParam("infoopttype")String infoopttype,
			@QueryParam("overtype")String overtype,
			@QueryParam("iscleannode")String iscleannode,
			@QueryParam("islabelpnt")String islabelpnt,
			@QueryParam("isvalidreg")String isvalidreg,
			@QueryParam("isrecalculate")String isrecalculate,
			@QueryParam("radius")String radius);
	
	/**
	 * 多边形叠加
	 * @param srcinfo	被叠加简单要素类的信息：
	 * 					格式"GDBP://user:password @ serverName/gdbName /ds/ds06/sfcls/www.wl"
	 * @param strgregionxml	序列化的多边形字符串
	 * @param desinfo	结果简单要素类信息
	 * @param attopttype 是否进行属性操作
	 * @param infoopttype 共有部分的图形参数操作
	 * @param overtype		叠加类型
	 * @param isCleanNode	是否节点平差
	 * @param isLabelPnt	是否label点方式
	 * @param isValidReg	是否检查区的合法性
	 * @param isReCalculate	是否重算面积
	 * @param radius		容差半径
	 * @parma informat
	 * @return		分析结果要素名称
	 */
	@POST
	@Path("/polygonoverlay")
	@Produces({MediaType.APPLICATION_JSON})
	public JSONObject polygonoverlay(@QueryParam("srcinfo1")String srcinfo,
			@QueryParam("strgregionxml")String strgregionxml,
			@QueryParam("desinfo")String desinfo,
			@QueryParam("attopttype")String attopttype,
			@QueryParam("infoopttype")String infoopttype,
			@QueryParam("overtype")String overtype,
			@QueryParam("iscleannode")String iscleannode,
			@QueryParam("islabelpnt")String islabelpnt,
			@QueryParam("isvalidreg")String isvalidreg,
			@QueryParam("isrecalculate")String isrecalculate,
			@QueryParam("radius")String radius,
			@QueryParam("informat")String informat);
	
	/**
	 * 两个要素叠加分析
	 * @param strgregion1xml	被叠加要素对象序列化后字符串
	 * @param strgregion2xml	叠加要素对象序列化后字符串
	 * @param desinfo	结果简单要素类信息
	 * @param attopttype 是否进行属性操作
	 * @param infoopttype 共有部分的图形参数操作
	 * @param overtype		叠加类型
	 * @param isCleanNode	是否节点平差
	 * @param isLabelPnt	是否label点方式
	 * @param isValidReg	是否检查区的合法性
	 * @param isReCalculate	是否重算面积
	 * @param radius		容差半径
	 * @parma informat		字符串格式
	 * @return		分析结果要素名称
	 */
	@POST
	@Path("/elementsoverlayelements")
	@Produces({MediaType.APPLICATION_JSON})
	public JSONObject elementsoverlayelements(@QueryParam("strgeometry1xml")String strgeometry1xml,
			@QueryParam("strgeometry2xml")String strgeometry2xml,
			@QueryParam("desinfo")String desinfo,
			@QueryParam("attopttype")String attopttype,
			@QueryParam("infoopttype")String infoopttype,
			@QueryParam("overtype")String overtype,
			@QueryParam("iscleannode")String iscleannode,
			@QueryParam("islabelpnt")String islabelpnt,
			@QueryParam("isvalidreg")String isvalidreg,
			@QueryParam("isrecalculate")String isrecalculate,
			@QueryParam("radius")String radius,
			@QueryParam("informat")String informat);
	
	/**
	 * 要素与图层叠加
	 * @param srcinfo1	叠加简单要素类的信息
	 * @param strgeometryxml 被叠加要素对象序列化后字符串
	 * @param desinfo	结果简单要素类信息
	 * @param attopttype 是否进行属性操作
	 * @param infoopttype 共有部分的图形参数操作
	 * @param overtype		叠加类型
	 * @param isCleanNode	是否节点平差
	 * @param isLabelPnt	是否label点方式
	 * @param isValidReg	是否检查区的合法性
	 * @param isReCalculate	是否重算面积
	 * @param radius		容差半径
	 * @parma informat		字符串格式
	 * @return		分析结果要素名称
	 */
	@POST
	@Path("/elementsoverlaylayers")
	@Produces({MediaType.APPLICATION_JSON})
	public JSONObject elementsoverlaylayers(@QueryParam("srcinfo1")String srcinfo1,
			@QueryParam("strgeometryxml")String strgeometryxml,
			@QueryParam("desinfo")String desinfo,
			@QueryParam("attopttype")String attopttype,
			@QueryParam("infoopttype")String infoopttype,
			@QueryParam("overtype")String overtype,
			@QueryParam("iscleannode")String iscleannode,
			@QueryParam("islabelpnt")String islabelpnt,
			@QueryParam("isvalidreg")String isvalidreg,
			@QueryParam("isrecalculate")String isrecalculate,
			@QueryParam("radius")String radius,
			@QueryParam("informat")String informat);
	
	/**
	 * 根据FID进行要素叠加分析
	 * @param srcinfo1	被叠加的要素集合所在的简单要素类
	 * @param srcinfo2  叠加的要素集合所在的简单要素类
	 * @param fids1	   被叠加的要素集合ID字符串,以逗号分隔，如：1,2,3,4
	 * @param fids2     叠加的要素集合ID字符串,以逗号分隔，如：1,2,3,4
	 * @param desinfo	结果简单要素类信息
	 * @param attopttype 是否进行属性操作
	 * @param infoopttype 共有部分的图形参数操作
	 * @param overtype		叠加类型
	 * @param isCleanNode	是否节点平差
	 * @param isLabelPnt	是否label点方式
	 * @param isValidReg	是否检查区的合法性
	 * @param isReCalculate	是否重算面积
	 * @param radius		容差半径
	 * @return		分析结果要素名称
	 */
	@POST
	@Path("/elementsoverlaybyids")
	@Produces({MediaType.APPLICATION_JSON})
	public JSONObject elementsoverlayByIds(@QueryParam("srcinfo1")String srcinfo1,
			@QueryParam("srcinfo2")String srcinfo2,
			@QueryParam("fids1")String fids1,
			@QueryParam("fids2")String fids2,
			@QueryParam("desinfo")String desinfo,
			@QueryParam("attopttype")String attopttype,
			@QueryParam("infoopttype")String infoopttype,
			@QueryParam("overtype")String overtype,
			@QueryParam("iscleannode")String iscleannode,
			@QueryParam("islabelpnt")String islabelpnt,
			@QueryParam("isvalidreg")String isvalidreg,
			@QueryParam("isrecalculate")String isrecalculate,
			@QueryParam("radius")String radius);
	
	
	/**
	 * 图层叠加new
	 * @param srcinfo1	被叠加的要素集合所在的简单要素类
	 * @param srcinfo2  叠加的要素集合所在的简单要素类
	 * @param desinfo	结果简单要素类信息
	 * @param attopttype 是否进行属性操作
	 * @param infoopttype 共有部分的图形参数操作
	 * @param overtype		叠加类型
	 * @param isrecalculate	是否重算面积
	 * @param radius	容差半径,参考系单位为度时，参考值为10e-9，参考系单位为米时，参考值为10e-5，因为一度约为100公里
	 * @param src1attfilter		srcInfo1属性过滤条件 mpArea>20000000
	 * @param iscreatedescls	是否创建目标简单要素类
	 * @return		分析结果要素名称
	 */
	@POST
	@Path("/overlaynew")
	@Produces({MediaType.APPLICATION_JSON})
	public JSONObject overlaynew(@QueryParam("srcinfo1")String srcinfo1,
			@QueryParam("srcinfo2")String srcinfo2,
			@QueryParam("desinfo")String desinfo,
			@QueryParam("attopttype")String attopttype,
			@QueryParam("infoopttype")String infoopttype,
			@QueryParam("overtype")String overtype,
			@QueryParam("isrecalculate")String isrecalculate,
			@QueryParam("radius")String radius,
			@QueryParam("src1attfilter")String src1attfilter,
			@QueryParam("iscreatedescls")String iscreatedescls
			);
	
	
	/**
	 * 简单要素类缓冲分析（单圈），支持对部分要素
	 * @param srcinfo				源简单要素类的URL
	 * @param desinfo				目的简单要素类的URL
	 * @param idstr					需要缓冲的要素ID的字符串，以','分隔
	 * @param leftrad				缓冲选项：左半径
	 * @param rightrad				缓冲选项：右半径
	 * @param isbyatt				缓冲选项：是否根据属性字段设置缓冲区半径,当为true时,LeftRad以及RightRad无效
	 * @param fldname				缓冲选项：可变半径属性字段名称
	 * @param angleType				缓冲选项：拐角类型：圆头/尖头:0/1
	 * @param isdynprj				缓冲选项：是否动态投影
	 * @param dynprjrad				缓冲选项：动态投影半径
	 * @param isdissolve			缓冲选项：缓冲区是否合并
	 * @param color					颜色(新增)
	 * @param ismultifeatureopr		复合要素操作(新增)
	 * @return			分析结果要素名称
	 */
	@POST
	@Path("/classbufferanalysis")
	@Produces({MediaType.APPLICATION_JSON})
	public JSONObject classbufferanalysis(@QueryParam("srcinfo")String srcinfo,
			@QueryParam("desinfo")String desinfo,
			@QueryParam("idstr")String idstr,
			@QueryParam("leftrad")String leftrad,
			@QueryParam("rightrad")String rightrad,
			@QueryParam("isbyatt")String isbyatt,
			@QueryParam("fldname")String fldname,
			@QueryParam("angletype")String angletype,
			@QueryParam("isdynprj")String isdynprj,
			@QueryParam("dynprjrad")String dynprjrad,
			@QueryParam("isdissolve")String isdissolve,
			@QueryParam("color")String color,
			@QueryParam("ismultifeatureopr")String ismultifeatureopr
			);
	
	/**
	 * 简单要素类缓冲分析（多圈），支持对部分要素
	 * @param srcinfo			源简单要素类的URL
	 * @param desinfo			目的简单要素类的URL
	 * @param idstr				需要缓冲的要素ID的字符串，以','分隔
	 * @param radiusStr			缓冲半径的字符串，以','分隔
	 * @param angleType			拐角类型：圆头/尖头:0/1
	 * @param isdissolve		缓冲区是否合并
	 * @param isdynprj			是否动态投影
	 * @param color				颜色(新增)
	 * @param ismultifeatureopr	复合要素操作(新增)
	 * @return					分析结果要素名称
	 */
	@POST
	@Path("/classbufferanalysismultiturn")
	@Produces({MediaType.APPLICATION_JSON})
	public JSONObject classbufferanalysismultiturn(@QueryParam("srcinfo")String srcinfo,
			@QueryParam("desinfo")String desinfo,
			@QueryParam("idstr")String idstr,
			@QueryParam("radiusstr")String radiusstr,
			@QueryParam("angletype")String angletype,
			@QueryParam("isdissolve")String isdissolve,
			@QueryParam("isdynprj")String isdynprj,
			@QueryParam("color")String color,
			@QueryParam("ismultifeatureopr")String ismultifeatureopr
			);
	
	/**
	 * 要素缓冲分析：支持多个要素
	 * @param sfgeometryxml		序列化的要素的xml字符串
	 * @param attStrctXML		属性结构xml字符串
	 * @param attrowsxml		属性记录xml字符串
	 * @param leftrad			左半径
	 * @param rightrad			右半径
	 * @param traceradius		跟踪半径
	 * @param resultname		结果图层的URL
	 * @param color				颜色（新增）
	 * @param ismultifeatureopr	复合要素操作(新增)
	 * @return					分析结果要素名称
	 */
	@POST
	@Path("/elementbufferanalysis")
	@Produces({MediaType.APPLICATION_JSON})
	public JSONObject elementbufferanalysis(@QueryParam("sfgeometryxml")String sfgeometryxml,
			@QueryParam("attstrctxml")String attstrctxml,
			@QueryParam("attrowsxml")String attrowsxml,
			@QueryParam("leftrad")String leftrad,
			@QueryParam("rightrad")String rightrad,
			@QueryParam("traceradius")String traceradius,
			@QueryParam("resultname")String resultname,
			@QueryParam("color")String color,
			@QueryParam("ismultifeatureopr")String ismultifeatureopr
			);

	
	/**
	 * 要素多重缓冲分析
	 * @param sfgeometryxml		序列化的要素的xml字符串
	 * @param attStrctXML		属性结构xml字符串
	 * @param attrowsxml		属性记录xml字符串
	 * @param radiusstr			多重缓冲半径序列字符串
	 * @param traceradius		跟踪半径
	 * @param resultname		结果图层的URL
	 * @param color				颜色（新增）
	 * @param ismultifeatureopr	复合要素操作(新增)
	 * @return					分析结果要素名称
	 */
	@POST
	@Path("/elementmultiplebufferanalysis")
	@Produces({MediaType.APPLICATION_JSON})
	public JSONObject classmultiplebufferanalysis(@QueryParam("sfgeometryxml")String sfgeometryxml,
			@QueryParam("attstrctxml")String attstrctxml,
			@QueryParam("attrowsxml")String attrowsxml,
			@QueryParam("radiusstr")String radiusstr,
			@QueryParam("traceradius")String traceradius,
			@QueryParam("resultname")String resultname,
			@QueryParam("color")String color,
			@QueryParam("ismultifeatureopr")String ismultifeatureopr
			);
	
	
	/**
	 * 路径分析
	 * @param netclsurl			网络类URL：GDBP://MapGISLocal/Sample/ds/sample/ncls/wuhan
	 * @param flagposstr		网标坐标序列：以','分隔
	 * @param elementtype		网络类型：1/2:节点网标/线网标
	 * @param barrierposstr		障碍点坐标序列：以','分隔
	 * @param neardis			搜索半径
	 * @param analytp			分析类型：默认（"UserMode"）
	 * @param weight			权值字段名
	 * @param pathstr			生成的CPathAnalyzeResult序列化的字符串
	 * @return			路径分析的结果
	 */
	@POST
	@Path("/roadanalysis")
	@Produces({MediaType.APPLICATION_JSON})
	public JSONObject roadanalysis(@QueryParam("netclsurl")String netclsurl,
			@QueryParam("flagposstr")String flagposstr,
			@QueryParam("elementtype")String elementtype,
			@QueryParam("barrierposstr")String barrierposstr,
			@QueryParam("neardis")String neardis,
			@QueryParam("analytp")String analytp,
			@QueryParam("weight")String weight,
			@QueryParam("pathstr")String pathstr
			);
	
}
