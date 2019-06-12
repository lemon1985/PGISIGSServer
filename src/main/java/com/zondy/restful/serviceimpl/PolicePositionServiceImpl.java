package com.zondy.restful.serviceimpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;

import com.alibaba.fastjson.JSONObject;
import com.zondy.bean.ENPoint;
import com.zondy.restful.service.PolicePositionService;
import com.zondy.util.DateTimeUtil;
import com.zondy.util.PointUtil;
/**
* 功能描述: <br>
* @return:
* @since: 1.0.0
* @Author:lemon
* @Date: 2019/5/6 9:40
*/

public class PolicePositionServiceImpl implements PolicePositionService{
	@Override
	public JSONObject RegisterGPSList(String gpsid, String policetypeid, String detailpolicetype, String gpsstyleid,
			String detaildevtype, String loctype, String sourcetype, String djdz, String carno, String policeid,
			String policename, String callno, String orgid, String xnorgid, String neturl, String gjurl,
			String remark) {
		JSONObject data = new JSONObject();
//		String sql="insert into table values("+gpsid+","+policetypeid+","+detailpolicetype+",'"+gpsstyleid+"'"
//////				+ ",'"+detaildevtype+"','"+loctype+"','"+sourcetype+"','"+djdz+"','"+carno+"'"
//////						+ ",'"+policeid+"','"+policename+"','"+callno+"','"+orgid+"','"+xnorgid+"'"
//////								+ ",'"+neturl+"','"+gjurl+"','"+remark+"')";
//////		int i=this.jdbcTemplate.update(sql);
		int i=Math.random()>0.5?1:0;
		if(i>0)
		{
			data.put("result", "success");
			data.put("msg", "添加成功");
		}else
		{
			data.put("result", "fail");
			data.put("msg", "添加失败");
		}
		return data;
	}

	@Override
	public JSONObject getNetTrack(String gpsid, String begintime, String endtime, String level, String type,
			String isfiltererror) {
		//原纪录经纬度坐标数组
		ArrayList<ENPoint> pGPSArrayInit = new ArrayList<ENPoint>();
		//过滤后的经纬度坐标数组
        ArrayList<ENPoint> pGPSArrayFilter = new ArrayList<ENPoint>();
		//过滤并排序后的经纬度坐标数组
        ArrayList<ENPoint> pGPSArrayFilterSort = new ArrayList<ENPoint>();
		//设置日期格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date begin_time=null,end_time=null;
		try {
			begin_time = df.parse(begintime);
			end_time=df.parse(endtime);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//生成随机点
		for(int i=0;i<200;i++)
		{
			ENPoint e=new ENPoint();
			e=PointUtil.randomENPoint(118.8888, 119, 31.888, 32);
			e.setXh(i);
			
	        String time=df.format(DateTimeUtil.randomDate(begin_time, end_time));
	        e.setTime(time);
			pGPSArrayInit.add(e);
		}
		//--------------进行轨迹压缩----------------------------------
		//设定最大距离误差阈值
		double DMax = 30.0;
		//获取第一个原始经纬度点坐标并添加到过滤后的数组中
        pGPSArrayFilter.add(pGPSArrayInit.get(0));
		//获取最后一个原始经纬度点坐标并添加到过滤后的数组中
        pGPSArrayFilter.add(pGPSArrayInit.get(pGPSArrayInit.size()-1));
		//使用一个点数组接收所有的点坐标，用于后面的压缩
        ENPoint[] enpInit = new ENPoint[pGPSArrayInit.size()];
        Iterator<ENPoint> iInit = pGPSArrayInit.iterator();
        int jj=0;
        while(iInit.hasNext()){
            enpInit[jj] = iInit.next();
            jj++;
        }
        //将ArrayList中的点坐标拷贝到点数组中
		//起始下标
       int start = 0;
		//结束下标
       int end = pGPSArrayInit.size()-1;
		//DP压缩算法
        pGPSArrayFilter=PointUtil.TrajCompressC(enpInit,pGPSArrayFilter,start,end,DMax);
      //  pGPSArrayFilter=PointUtil.DouglasPeucker(pGPSArrayInit,DMax);
		//输出压缩后的点数
        System.out.println(pGPSArrayFilter.size());
        
        //-------------------------对压缩后的经纬度点坐标数据按照ID从小到大排序---------------------------------------------//
		//使用一个点数组接收过滤后的点坐标，用于后面的排序
		ENPoint[] enpFilter = new ENPoint[pGPSArrayFilter.size()];
        Iterator<ENPoint> iF = pGPSArrayFilter.iterator();
        int i = 0;
        while(iF.hasNext()){
            enpFilter[i] = iF.next();
            i++;
        }
        //将ArrayList中的点坐标拷贝到点数组中
		//进行排序
        Arrays.sort(enpFilter);
        for(int j=0;j<enpFilter.length;j++){
			//将排序后的点坐标写到一个新的ArrayList数组中
            pGPSArrayFilterSort.add(enpFilter[j]);
        }
        //-------------------------求平均误差-------------------------------------------------------------------------//
		//求平均误差
		double mDError = PointUtil.getMeanDistError(pGPSArrayInit,pGPSArrayFilterSort);
        System.out.println(mDError);
        //-------------------------求压缩率--------------------------------------------------------------------------//
		//求压缩率
		double cRate = (double)pGPSArrayFilter.size()/pGPSArrayInit.size()*100;
        System.out.println(cRate);
        
        StringBuffer sb=new StringBuffer();
        for(ENPoint e:pGPSArrayFilter)
        {
        	sb.append(e.toString()+",");
        }
        String infos=sb.substring(0, sb.length()-1);
        
        JSONObject data = new JSONObject();
        data.put("gpsid", gpsid);
        data.put("begintime", begintime);
        data.put("endtime", endtime);
        data.put("level", level);
        data.put("infos", infos);
        data.put("result", 0);
        data.put("startPoint", pGPSArrayFilter.get(0).toString());
        data.put("endPoint", pGPSArrayFilter.get(pGPSArrayFilter.size()-1).toString());
		return data;
	}
	
	@Override
	public JSONObject QueryGPSList(String username, String password, int policetypeid, int loctypeid, int gpsstyleid) {
		JSONObject returninfo = new JSONObject();
		JSONObject data = new JSONObject();
		int i=Math.random()>0.5?1:0;
		if(i>0)
		{
			data.put("gpsid","bh12345678");
			data.put("devid","dh12345678");
			data.put("policetypeid","pt1101");
			data.put("policetypename","交警");
			data.put("detailpolicetype","巡逻警察");
			data.put("loctype","gps");
			data.put("localtypename","GPS");
			data.put("gpsstyleid","gp1101");
			data.put("gpsstylename","执法记录仪");
			data.put("detaildevtype","海康101型");
			data.put("callno","834211");
			data.put("carno","苏H12345");
			data.put("createtime","2018-01-01 11:11:11");
			data.put("x","111.11");
			data.put("y","33.33");
			data.put("policeid","081411");
			data.put("policename","王大壮");
			data.put("orgid","320800001010");
			data.put("orgname","交巡警大队");
			data.put("parentorg","市局");
			data.put("xnorgid","101");
			data.put("xnorgname","");
			data.put("djdz","");
			data.put("sourcetype","警用");
			data.put("province","江苏");
			data.put("city","淮安");
			data.put("infocreatetime","2018-01-01 11:11:11");
			data.put("infoupdatetime","2018-01-01 11:11:11");
			data.put("neturl","");
			data.put("parentneturl","");
			data.put("gjurl","");
			returninfo.put("result", "success");
			returninfo.put("data", data);
		}else
		{
			returninfo.put("result", "fail");
			returninfo.put("msg", "error message");
		}
		return returninfo;
	}
}
