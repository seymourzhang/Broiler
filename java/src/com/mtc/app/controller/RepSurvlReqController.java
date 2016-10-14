/**
 *
 * MTC-上海农汇信息科技有限公司
 * Copyright © 2015 农汇 版权所有
 */
package com.mtc.app.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mtc.app.service.BaseQueryService;
import com.mtc.common.util.DealSuccOrFail;
import com.mtc.common.util.PubFun;
import com.mtc.common.util.constants.Constants;

/**
 * @ClassName: RepSurvlReqController
 * @Description: 
 * @Date 2016-1-21 下午6:03:11
 * @Author Shao Yao Yu
 * 成活率曲线
 */
@Controller
@RequestMapping("/rep/SVRate")
public class RepSurvlReqController {
	
	private static Logger mLogger =Logger.getLogger(RepSurvlReqController.class); 
		
	@Autowired
	private BaseQueryService mBaseQueryService;
	 
	 @RequestMapping("/SurvlRateReq")
		public void SurvlRateReq(HttpServletRequest request,HttpServletResponse response){
		   mLogger.info("=======Now start executing RepSurvlReqController.SurvlRateReq");
		   JSONObject resJson = new JSONObject();
		   String dealRes = null;
		   long startReqTime = System.currentTimeMillis();
		   try {
		     String paraStr = PubFun.getRequestPara(request);
		     mLogger.info("updateFarm.para="+paraStr);
			 JSONObject jsonobject = new JSONObject(paraStr);
			 mLogger.info("jsonObject=" + jsonobject.toString());
			 /** 业务处理开始，查询、增加、修改、或删除 **/
			 JSONObject params = jsonobject.optJSONObject("params");
			 int userid = jsonobject.optInt("id_spa");
			 String CompareType = params.optString("CompareType");
			 JSONArray DCDatas = new JSONArray();
			 if(CompareType == null || CompareType.equals("")){
				 CompareType = "01";
			 }
			 // 栋舍对比
			 if(CompareType.equals("01")){
				 int FarmBreedId = params.optInt("FarmBreedId");
			     String sql = "SELECT (case when bd.growth_date > curdate() then 'N' else 'Y' end) as showFlag,  bd.age , hb.house_id,s_f_getHouseName(hb.house_id) AS housename, ROUND(bd.cur_amount/(bd.cur_amount+bd.acc_cd)*100 ,1) AS atu_cd_rate  "
			     		+ "FROM s_b_house_breed hb "
			     		+ "LEFT JOIN s_b_breed_detail bd ON bd.house_breed_id = hb.id "
			     		+ "WHERE hb.farm_breed_id = "+FarmBreedId+" AND (CASE WHEN hb.market_date IS NULL THEN 1=1 ELSE  bd.growth_date <= hb.market_date END) GROUP BY bd.age,hb.house_id "
			     				+ " UNION ALL  "
			     				+ " SELECT ( CASE WHEN bd.growth_date > CURDATE() THEN 'N'  ELSE 'Y'  END ) AS showFlag,"
			     				+ " bd.age ,IFNULL(NULL,'01') AS house_id ,IFNULL(NULL,'全场平均') AS housename,"
			     				+ " ROUND( SUM(bd.cur_amount) / ((SUM(bd.cur_amount) + SUM(bd.acc_cd))) * 100,1) AS atu_cd_rate "
			     				+ " FROM s_b_house_breed hb  LEFT JOIN s_b_breed_detail bd  ON bd.house_breed_id = hb.id "
			     				+ "	WHERE hb.farm_breed_id ="+FarmBreedId+" AND (CASE WHEN hb.market_date IS NULL THEN 1=1 ELSE  bd.growth_date <= hb.market_date END) GROUP BY age ORDER BY house_id, age "; 
			     mLogger.info("===========RepSurvlReqController.SurvlRateReq.SQL=" + sql);// ROUND( SUM(bd.cur_amount) / ((SUM(bd.ytd_amount) + SUM(bd.cur_amount)) / 2) * 100,1)
			     List<HashMap<String,Object>> toutcome = mBaseQueryService.selectMapByAny(sql);
				 if(toutcome.size()!=0){
					 Object house_id = toutcome.get(0).get("house_id");
					 int i=0;
					 Object housename = null;
					 ArrayList<Object> HouseDa =new ArrayList<Object>();
					 for (HashMap<String, Object> outcome : toutcome){
						 if(!house_id.equals(toutcome.get(i).get("house_id"))||i+1==toutcome.size()){
							 JSONObject tJSONObject = new JSONObject();
							 tJSONObject.put("HouseId", house_id);
							 tJSONObject.put("housename", housename);
							 tJSONObject.put("HouseDatas", HouseDa);
							 DCDatas.put(tJSONObject);
							 HouseDa = new ArrayList<Object>();
						 }
						 house_id =  outcome.get("house_id");					
						 housename =  outcome.get("housename");
						 Object atu_cd_rate =  outcome.get("atu_cd_rate");
						 String showFlag =  outcome.get("showFlag").toString();	
						 if(showFlag.equals("Y")){
							 HouseDa.add(atu_cd_rate);
						 }
						 i++;
					 }
					 resJson.put("DCDatas", DCDatas);
					 resJson.put("FarmBreedId", FarmBreedId);			
				 }
			 // 批次对比	 
			 }else if(CompareType.equals("02")){
				 String HouseId = params.optString("HouseId");
				 String  sql ="SELECT farm_id FROM s_b_user_farm WHERE user_id ="+userid;
				 mLogger.info("==========RepSurvlReqController.SurvlRateReq.SQL=" + sql);
				 Integer  farm_id = mBaseQueryService.selectIntergerByAny(sql);
				 String SQL =null;
				 if(HouseId.equals("01")){
					  SQL = "SELECT  (CASE WHEN a.growth_date > CURDATE() THEN 'N' ELSE 'Y' END) AS showFlag , b.farm_breed_id,a.age,"
					  		+ " (SELECT batch_code FROM s_b_farm_breed WHERE id = b.farm_breed_id) AS batch_code,"
					  		+ " ROUND(SUM(a.cur_amount) / ((SUM(a.ytd_amount) + SUM(a.cur_amount)) / 2) * 100, 1) AS atu_cd_rate"
					  		+ " FROM s_b_breed_detail a LEFT JOIN s_b_house_breed b ON a.house_breed_id = b.id"
					  		+ " WHERE b.farm_id = "+farm_id+" AND a.age <= 50 AND  (CASE WHEN b.market_date IS NULL THEN 1=1 ELSE  a.growth_date <= b.market_date END) GROUP BY a.age, b.farm_breed_id ORDER BY b.farm_breed_id,a.age"; 
				 }else{
					  SQL = "SELECT (case when bd.growth_date > curdate() then 'N' else 'Y' end) as showFlag, hb.farm_breed_id,(SELECT batch_code from s_b_farm_breed where id = hb.farm_breed_id) AS batch_code,"
					     		+ "ROUND(bd.cur_amount/(bd.cur_amount+bd.acc_cd)*100 ,1) AS atu_cd_rate  "
					     		+ "FROM s_b_house_breed hb "
					     		+ "LEFT JOIN s_b_breed_detail bd ON bd.house_breed_id = hb.id "
					     		+ "WHERE hb.house_id = "+HouseId+" AND (CASE WHEN hb.market_date IS NULL THEN 1=1 ELSE  bd.growth_date <= hb.market_date END) GROUP BY bd.age,hb.farm_breed_id ORDER BY hb.farm_breed_id,bd.age"; 
				 }
			     mLogger.info("==========RepSurvlReqController.SurvlRateReq.SQL=" + SQL);
			     List<HashMap<String,Object>> toutcome = mBaseQueryService.selectMapByAny(SQL);
				 if(toutcome.size()!=0){
					 Object farm_breed_id = toutcome.get(0).get("farm_breed_id");
					 int i=0;
					 Object batch_code = null;
					 ArrayList<Object> HouseDa =new ArrayList<Object>();
					 for (HashMap<String, Object> outcome : toutcome){
						 if(!farm_breed_id.equals(toutcome.get(i).get("farm_breed_id"))||i+1==toutcome.size()){
							 JSONObject tJSONObject = new JSONObject();
							 tJSONObject.put("FarmBreedId", farm_breed_id);
							 tJSONObject.put("FBBatchCode", batch_code);
							 tJSONObject.put("HouseDatas", HouseDa);
							 DCDatas.put(tJSONObject);
							 HouseDa = new ArrayList<Object>();
						 }
						 farm_breed_id =  outcome.get("farm_breed_id");					
						 batch_code =  outcome.get("batch_code");
						 Object atu_cd_rate =  outcome.get("atu_cd_rate");	
						 String showFlag =  outcome.get("showFlag").toString();	
						 if(showFlag.equals("Y")){
							 HouseDa.add(atu_cd_rate);
						 }
						 
						 i++;
					 }
					 resJson.put("HouseId", HouseId);			
				  }
			 }
			 resJson.put("DCDatas", DCDatas);
			 resJson.put("Result", "Success");
			 /** 业务处理结束 **/
			 dealRes = Constants.RESULT_SUCCESS ;
		   } catch (Exception e) {
				e.printStackTrace();
				try {
					resJson = new JSONObject();
					resJson.put("Exception", e.getMessage());
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
				dealRes = Constants.RESULT_FAIL ;
			}
		    
			long endReqTime = System.currentTimeMillis();
			if(endReqTime - startReqTime < 1500){
				try {
					Thread.sleep(1500 - endReqTime + startReqTime);
				}catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
			DealSuccOrFail.dealApp(request,response,dealRes,resJson);
			mLogger.info("=====Now end executing RepSurvlReqController.SurvlRateReq");
		}
	
}