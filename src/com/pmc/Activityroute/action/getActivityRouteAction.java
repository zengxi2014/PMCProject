package com.pmc.Activityroute.action;

import com.mongodb.WriteResult;
import com.pmc.Activityroute.service.ActivityRouteInfoBasicService;
import com.pmc.utils.PMCJSONUtils;
import org.apache.struts2.ServletActionContext;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by luluteam on 2015/5/25.
 */
public class getActivityRouteAction {

    @Autowired
    private  ActivityRouteInfoBasicService activityRouteInfoBasicService = new ActivityRouteInfoBasicService();

    private String appId;

    public void execute()throws JSONException, IOException {

//        System.out.println(jsonStr);
//        JSONObject object = new JSONObject(jsonStr);
//        if(jsonStr == null)
//            return;
//        PrintWriter out =  ServletActionContext.getResponse().getWriter();
//        //out.println("OK");
//        //crackReportBasicService.saveCrackReportByJSON(jsonStr);
//        WriteResult result =  activityRouteInfoService.saveActivityRoutetByJSON(jsonStr);
//        System.out.println(result.getLastError().toString());
    activityRouteInfoBasicService.findListByAppId("85d4a553-ee8d-4136-80ab-2469adcae44d");


    }
}
