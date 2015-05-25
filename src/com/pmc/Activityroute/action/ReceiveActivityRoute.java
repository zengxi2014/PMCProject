package com.pmc.Activityroute.action;

import java.io.IOException;
import java.io.PrintWriter;

import org.apache.struts2.ServletActionContext;
import org.json.JSONException;

import com.mongodb.WriteResult;
import com.opensymphony.xwork2.ActionSupport;
import com.pmc.Activityroute.service.ActivityRouteInfoBasicService;
import com.pmc.crackanalyse.service.CrackReportBasicService;
import com.pmc.utils.PMCJSONUtils;
import org.json.JSONObject;

public class ReceiveActivityRoute  {
	
	private ActivityRouteInfoBasicService activityRouteInfoService = new ActivityRouteInfoBasicService(); 
	 
    public void execute()throws JSONException, IOException{
		String jsonStr = PMCJSONUtils.readJSONStringFromHttpRequest(ServletActionContext.getRequest());	
		/*
		System.out.println(jsonStr);
		JSONObject object = new JSONObject(jsonStr);
		System.out.println((String)object.get("ret"));
		JSONArray array = (JSONArray) object.get("serviceParam");
		for(int i=0;i<array.length();i++){
			String str = array.getString(i);
			System.out.println(str);
		}
		*/
		System.out.println(jsonStr);
		JSONObject object = new JSONObject(jsonStr);
		if(jsonStr == null)
			return;
		PrintWriter out =  ServletActionContext.getResponse().getWriter();
		//out.println("OK");
		//crackReportBasicService.saveCrackReportByJSON(jsonStr);	
		WriteResult result =  activityRouteInfoService.saveActivityRoutetByJSON(jsonStr);
		System.out.println(result.getLastError().toString());
		
	}
}
