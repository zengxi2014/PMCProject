package com.pmc.JSONTest;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;

import org.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.json.JSONException;

import com.pmc.utils.PMCJSONUtils;

public class JSONTest {
	
	public void execute()throws JSONException, IOException{
		String jsonStr = PMCJSONUtils.readJSONStringFromHttpRequest(ServletActionContext.getRequest());	
		System.out.println(jsonStr);
		JSONObject obj = new JSONObject();
		obj.put("ret", "OK");
		PrintStream out = new PrintStream(ServletActionContext.getResponse().getOutputStream());
		System.out.println(obj.toString());
		out.println(obj.toString());
//		System.out.println(obj.toString());
	}

}
