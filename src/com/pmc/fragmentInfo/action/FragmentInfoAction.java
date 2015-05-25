package com.pmc.fragmentInfo.action;

import java.io.IOException;
import java.io.PrintWriter;

import org.apache.struts2.ServletActionContext;
import org.json.JSONException;

import com.mongodb.WriteResult;
import com.pmc.fragmentInfo.service.FragmentInfoBasicService;
import com.pmc.utils.PMCJSONUtils;

public class FragmentInfoAction {
	  private FragmentInfoBasicService fragmentInfoBasicService = new FragmentInfoBasicService();
	  
	  public void execute()throws JSONException, IOException{
			String jsonStr = PMCJSONUtils.readJSONStringFromHttpRequest(ServletActionContext.getRequest());	
			System.out.println(jsonStr);
			if(jsonStr == null)
				return;
			PrintWriter out =  ServletActionContext.getResponse().getWriter();	
			WriteResult result =  fragmentInfoBasicService.saveFragmentInfoByJSON(jsonStr);
			System.out.println(result.toString());
			
		}
}
