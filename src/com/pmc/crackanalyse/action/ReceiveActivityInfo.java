package com.pmc.crackanalyse.action;

import java.io.IOException;
import java.io.PrintWriter;

import org.apache.struts2.ServletActionContext;
import org.json.JSONException;

import com.mongodb.WriteResult;
import com.pmc.crackanalyse.service.CrackReportBasicService;
import com.pmc.utils.PMCJSONUtils;

public class ReceiveActivityInfo {
	private CrackReportBasicService crackReportBasicService = new CrackReportBasicService();
	
	public void execute()throws JSONException, IOException{
		String jsonStr = PMCJSONUtils.readJSONStringFromHttpRequest(ServletActionContext.getRequest());	
		
		System.out.println(jsonStr);
		PrintWriter out =  ServletActionContext.getResponse().getWriter();
		out.println("OK");
		WriteResult result = crackReportBasicService.saveActivityInfoByJSON(jsonStr);
		System.out.println(result.getError());
	}
}
