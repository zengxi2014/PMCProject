package com.pmc.crackanalyse.action;

import com.pmc.crackanalyse.service.CrackReportBasicService;
import com.pmc.utils.PMCJSONUtils;
import org.apache.struts2.ServletActionContext;
import org.json.JSONException;

import java.io.IOException;
import java.io.PrintWriter;



public class ReceiveCrackReport {
	private CrackReportBasicService crackReportBasicService = new CrackReportBasicService();
	
	 
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
		PrintWriter out =  ServletActionContext.getResponse().getWriter();
		//out.println("OK");
		crackReportBasicService.saveCrackReportByJSON(jsonStr);		
	}
}
