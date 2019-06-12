package com.zondy.restful.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zondy.bean.ANALYSISBean;
import com.zondy.property.PropertiesConfig;
import com.zondy.restful.service.SpatialAnalysisService;
import com.zondy.util.HttpRequest;

public class SpatialAnalysisServiceImpl implements SpatialAnalysisService{
	PropertiesConfig pc=new PropertiesConfig();

	@Override
	public JSONObject overlay(String srcInfo1, String srcInfo2, String desInfo, String attOptType, String infoOptType,
			String overType, String isCleanNode, String isLabelPnt, String isValidReg, String isReCalculate,
			String radius) {
		List<ANALYSISBean> l=new ArrayList<ANALYSISBean>();
		ANALYSISBean tmp=new ANALYSISBean();
		tmp.setKey("srcInfo1");tmp.setValue(srcInfo1);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("srcInfo2");tmp.setValue(srcInfo2);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("desInfo");tmp.setValue(desInfo);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("attOptType");tmp.setValue(attOptType);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("infoOptType");tmp.setValue(infoOptType);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("overType");tmp.setValue(overType);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("isCleanNode");tmp.setValue(isCleanNode);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("isLabelPnt");tmp.setValue(isLabelPnt);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("isValidReg");tmp.setValue(isValidReg);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("isReCalculate");tmp.setValue(isReCalculate);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("radius");tmp.setValue(radius);l.add(tmp);
		System.out.println(JSON.toJSONString(l));
		String resturl="http://"+pc.getInitParameter("IGSIP")+":"+pc.getInitParameter("WMTSPROT")+
				"/igs/rest/mrfws/execute/600227?f=json";
		String result=HttpRequest.sendPost(resturl, JSON.toJSONString(l));
		return JSON.parseObject(result);
	}

	@Override
	public JSONObject polygonoverlay(String srcInfo1, String strGRegionXML, String desInfo, String attOptType,
			String infoOptType, String overType, String isCleanNode, String isLabelPnt, String isValidReg,
			String isReCalculate, String radius, String informat) {
			List<ANALYSISBean> l=new ArrayList<ANALYSISBean>();
			ANALYSISBean tmp=new ANALYSISBean();
			tmp.setKey("srcInfo1");tmp.setValue(srcInfo1);l.add(tmp);tmp=new ANALYSISBean();
			tmp.setKey("strGRegionXML");tmp.setValue(strGRegionXML);l.add(tmp);tmp=new ANALYSISBean();
			tmp.setKey("desInfo");tmp.setValue(desInfo);l.add(tmp);tmp=new ANALYSISBean();
			tmp.setKey("attOptType");tmp.setValue(attOptType);l.add(tmp);tmp=new ANALYSISBean();
			tmp.setKey("infoOptType");tmp.setValue(infoOptType);l.add(tmp);tmp=new ANALYSISBean();
			tmp.setKey("overType");tmp.setValue(overType);l.add(tmp);tmp=new ANALYSISBean();
			tmp.setKey("isCleanNode");tmp.setValue(isCleanNode);l.add(tmp);tmp=new ANALYSISBean();
			tmp.setKey("isLabelPnt");tmp.setValue(isLabelPnt);l.add(tmp);tmp=new ANALYSISBean();
			tmp.setKey("isValidReg");tmp.setValue(isValidReg);l.add(tmp);tmp=new ANALYSISBean();
			tmp.setKey("isReCalculate");tmp.setValue(isReCalculate);l.add(tmp);tmp=new ANALYSISBean();
			tmp.setKey("radius");tmp.setValue(radius);l.add(tmp);tmp=new ANALYSISBean();
			tmp.setKey("informat");tmp.setValue(informat);l.add(tmp);
			String resturl="http://"+pc.getInitParameter("IGSIP")+":"+pc.getInitParameter("WMTSPROT")+
					"/igs/rest/mrfws/execute/600237?f=json";
			String result=HttpRequest.sendPost(resturl, JSON.toJSONString(l));
			return JSON.parseObject(result);
	}

	@Override
	public JSONObject elementsoverlayelements(String strGeometry1XML, String strGeometry2XML, String desInfo,
			String attOptType, String infoOptType, String overType, String isCleanNode, String isLabelPnt,
			String isValidReg, String isReCalculate, String radius, String informat) {
		List<ANALYSISBean> l=new ArrayList<ANALYSISBean>();
		ANALYSISBean tmp=new ANALYSISBean();
		tmp.setKey("strGeometry1XML");tmp.setValue(strGeometry1XML);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("strGeometry2XML");tmp.setValue(strGeometry2XML);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("desInfo");tmp.setValue(desInfo);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("attOptType");tmp.setValue(attOptType);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("infoOptType");tmp.setValue(infoOptType);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("overType");tmp.setValue(overType);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("isCleanNode");tmp.setValue(isCleanNode);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("isLabelPnt");tmp.setValue(isLabelPnt);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("isValidReg");tmp.setValue(isValidReg);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("isReCalculate");tmp.setValue(isReCalculate);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("radius");tmp.setValue(radius);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("informat");tmp.setValue(informat);l.add(tmp);
		String resturl="http://"+pc.getInitParameter("IGSIP")+":"+pc.getInitParameter("WMTSPROT")+
				"/igs/rest/mrfws/execute/600307?f=json";
		String result=HttpRequest.sendPost(resturl, JSON.toJSONString(l));
		return JSON.parseObject(result);
	}

	@Override
	public JSONObject elementsoverlaylayers(String srcInfo1, String strGeometryXML, String desInfo, String attOptType,
			String infoOptType, String overType, String isCleanNode, String isLabelPnt, String isValidReg,
			String isReCalculate, String radius, String informat) {
		List<ANALYSISBean> l=new ArrayList<ANALYSISBean>();
		ANALYSISBean tmp=new ANALYSISBean();
		tmp.setKey("srcInfo1");tmp.setValue(srcInfo1);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("strGeometryXML");tmp.setValue(strGeometryXML);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("desInfo");tmp.setValue(desInfo);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("attOptType");tmp.setValue(attOptType);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("infoOptType");tmp.setValue(infoOptType);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("overType");tmp.setValue(overType);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("isCleanNode");tmp.setValue(isCleanNode);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("isLabelPnt");tmp.setValue(isLabelPnt);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("isValidReg");tmp.setValue(isValidReg);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("isReCalculate");tmp.setValue(isReCalculate);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("radius");tmp.setValue(radius);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("informat");tmp.setValue(informat);l.add(tmp);
		String resturl="http://"+pc.getInitParameter("IGSIP")+":"+pc.getInitParameter("WMTSPROT")+
				"/igs/rest/mrfws/execute/600308?f=json";
		String result=HttpRequest.sendPost(resturl, JSON.toJSONString(l));
		return JSON.parseObject(result);
	}

	@Override
	public JSONObject elementsoverlayByIds(String srcInfo1, String srcInfo2, String FIDs1, String FIDs2, String desInfo,
			String attOptType, String infoOptType, String overType, String isCleanNode, String isLabelPnt,
			String isValidReg, String isReCalculate, String radius) {
		List<ANALYSISBean> l=new ArrayList<ANALYSISBean>();
		ANALYSISBean tmp=new ANALYSISBean();
		tmp.setKey("srcInfo1");tmp.setValue(srcInfo1);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("srcInfo2");tmp.setValue(srcInfo2);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("FIDs1");tmp.setValue(FIDs1);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("FIDs2");tmp.setValue(FIDs2);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("desInfo");tmp.setValue(desInfo);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("attOptType");tmp.setValue(attOptType);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("infoOptType");tmp.setValue(infoOptType);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("overType");tmp.setValue(overType);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("isCleanNode");tmp.setValue(isCleanNode);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("isLabelPnt");tmp.setValue(isLabelPnt);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("isValidReg");tmp.setValue(isValidReg);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("isReCalculate");tmp.setValue(isReCalculate);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("radius");tmp.setValue(radius);l.add(tmp);
		String resturl="http://"+pc.getInitParameter("IGSIP")+":"+pc.getInitParameter("WMTSPROT")+
				"/igs/rest/mrfws/execute/600309?f=json";
		String result=HttpRequest.sendPost(resturl, JSON.toJSONString(l));
		return JSON.parseObject(result);
	}

	@Override
	public JSONObject overlaynew(String srcInfo1, String srcInfo2, String desInfo, String attOptType,
			String infoOptType, String overType, String isCleanNode, String radius, String src1AttFilter,
			String isCreateDesCls) {
		List<ANALYSISBean> l=new ArrayList<ANALYSISBean>();
		ANALYSISBean tmp=new ANALYSISBean();
		tmp.setKey("srcInfo1");tmp.setValue(srcInfo1);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("srcInfo2");tmp.setValue(srcInfo2);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("desInfo");tmp.setValue(desInfo);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("attOptType");tmp.setValue(attOptType);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("infoOptType");tmp.setValue(infoOptType);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("overType");tmp.setValue(overType);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("isCleanNode");tmp.setValue(isCleanNode);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("radius");tmp.setValue(radius);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("src1AttFilter");tmp.setValue(src1AttFilter);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("isCreateDesCls");tmp.setValue(isCreateDesCls);l.add(tmp);
		String resturl="http://"+pc.getInitParameter("IGSIP")+":"+pc.getInitParameter("WMTSPROT")+
				"/igs/rest/mrfws/execute/600334?f=json";
		String result=HttpRequest.sendPost(resturl, JSON.toJSONString(l));
		return JSON.parseObject(result);
	}

	@Override
	public JSONObject classbufferanalysis(String srcInfo, String desInfo, String idstr, String leftRad, String rightRad,
			String isByAtt, String fldName, String angleType, String isDynPrj, String dynPrjRad, String isDissolve,
			String color, String isMultiFeatureOpr) {
		List<ANALYSISBean> l=new ArrayList<ANALYSISBean>();
		ANALYSISBean tmp=new ANALYSISBean();
		tmp.setKey("srcInfo");tmp.setValue(srcInfo);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("desInfo");tmp.setValue(desInfo);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("idstr");tmp.setValue(idstr);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("leftRad");tmp.setValue(leftRad);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("rightRad");tmp.setValue(rightRad);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("isByAtt");tmp.setValue(isByAtt);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("fldName");tmp.setValue(fldName);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("angleType");tmp.setValue(angleType);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("isDynPrj");tmp.setValue(isDynPrj);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("dynPrjRad");tmp.setValue(dynPrjRad);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("isDissolve");tmp.setValue(isDissolve);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("color");tmp.setValue(color);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("isMultiFeatureOpr");tmp.setValue(isMultiFeatureOpr);l.add(tmp);
			String resturl="http://"+pc.getInitParameter("IGSIP")+":"+pc.getInitParameter("WMTSPROT")+
					"/igs/rest/mrfws/execute/600231?f=json";
			String result=HttpRequest.sendPost(resturl,  JSON.toJSONString(l));
			return JSON.parseObject(result);
	}

	@Override
	public JSONObject classbufferanalysismultiturn(String srcInfo, String desInfo, String idstr, String radiusStr,
			String angleType, String isDissolve, String isDynPrj, String color, String isMultiFeatureOpr) {
		List<ANALYSISBean> l=new ArrayList<ANALYSISBean>();
		ANALYSISBean tmp=new ANALYSISBean();
		tmp.setKey("srcInfo");tmp.setValue(srcInfo);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("desInfo");tmp.setValue(desInfo);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("idstr");tmp.setValue(idstr);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("radiusStr");tmp.setValue(radiusStr);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("angleType");tmp.setValue(angleType);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("isDynPrj");tmp.setValue(isDynPrj);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("isDissolve");tmp.setValue(isDissolve);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("color");tmp.setValue(color);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("isMultiFeatureOpr");tmp.setValue(isMultiFeatureOpr);l.add(tmp);
		System.out.println(JSON.toJSONString(l));
			String resturl="http://"+pc.getInitParameter("IGSIP")+":"+pc.getInitParameter("WMTSPROT")+
					"/igs/rest/mrfws/execute/600232?f=json";
			String result=HttpRequest.sendPost(resturl, JSON.toJSONString(l));
			return JSON.parseObject(result);
	}

	@Override
	public JSONObject elementbufferanalysis(String sfGeometryXML, String attStrctXML, String attRowsXML, String leftRad,
			String rightRad, String traceRadius, String resultName, String color, String isMultiFeatureOpr) {
		List<ANALYSISBean> l=new ArrayList<ANALYSISBean>();
		ANALYSISBean tmp=new ANALYSISBean();
		tmp.setKey("sfGeometryXML");tmp.setValue(sfGeometryXML);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("attStrctXML");tmp.setValue(attStrctXML);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("attRowsXML");tmp.setValue(attRowsXML);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("leftRad");tmp.setValue(leftRad);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("rightRad");tmp.setValue(rightRad);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("traceRadius");tmp.setValue(traceRadius);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("resultName");tmp.setValue(resultName);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("color");tmp.setValue(color);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("isMultiFeatureOpr");tmp.setValue(isMultiFeatureOpr);l.add(tmp);
			String resturl="http://"+pc.getInitParameter("IGSIP")+":"+pc.getInitParameter("WMTSPROT")+
					"/igs/rest/mrfws/execute/600238?f=json";
			String result=HttpRequest.sendPost(resturl, JSON.toJSONString(l));
			return JSON.parseObject(result);
	}

	@Override
	public JSONObject classmultiplebufferanalysis(String sfGeometryXML, String attStrctXML, String attRowsXML,
			String radiusStr, String traceRadius, String resultName, String color, String isMultiFeatureOpr) {
		List<ANALYSISBean> l=new ArrayList<ANALYSISBean>();
		ANALYSISBean tmp=new ANALYSISBean();
		tmp.setKey("sfGeometryXML");tmp.setValue(sfGeometryXML);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("attStrctXML");tmp.setValue(attStrctXML);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("attRowsXML");tmp.setValue(attRowsXML);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("radiusStr");tmp.setValue(radiusStr);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("traceRadius");tmp.setValue(traceRadius);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("resultName");tmp.setValue(resultName);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("color");tmp.setValue(color);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("isMultiFeatureOpr");tmp.setValue(isMultiFeatureOpr);l.add(tmp);
			String resturl="http://"+pc.getInitParameter("IGSIP")+":"+pc.getInitParameter("WMTSPROT")+
					"/igs/rest/mrfws/execute/600239?f=json";
			String result=HttpRequest.sendPost(resturl, JSON.toJSONString(l));
			return JSON.parseObject(result);
	}

	@Override
	public JSONObject roadanalysis(String netClsUrl, String flagPosStr, String elementType, String barrierPosStr,
			String nearDis, String analyTp, String weight, String pathStr) {
		List<ANALYSISBean> l=new ArrayList<ANALYSISBean>();
		ANALYSISBean tmp=new ANALYSISBean();
		tmp.setKey("netClsUrl");tmp.setValue(netClsUrl);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("flagPosStr");tmp.setValue(flagPosStr);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("elementType");tmp.setValue(elementType);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("barrierPosStr");tmp.setValue(barrierPosStr);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("nearDis");tmp.setValue(nearDis);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("analyTp");tmp.setValue(analyTp);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("weight");tmp.setValue(weight);l.add(tmp);tmp=new ANALYSISBean();
		tmp.setKey("pathStr");tmp.setValue(pathStr);l.add(tmp);
			String resturl="http://"+pc.getInitParameter("IGSIP")+":"+pc.getInitParameter("WMTSPROT")+
					"/igs/rest/mrfws/execute/600233?f=json";
			String result=HttpRequest.sendPost(resturl, JSON.toJSONString(l));
			return JSON.parseObject(result);
	}

	
}
