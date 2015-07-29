package com.pmc.ActivityInfo.action;

import com.mongodb.WriteResult;
import com.pmc.ActivityInfo.service.ActivityInfoBasicService;
import com.pmc.utils.PMCJSONUtils;
import org.apache.struts2.ServletActionContext;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by luluteam on 2015/7/25.
 */
public class ReceiveActivityInfoAction {
    private ActivityInfoBasicService activityInfoBasicService = new ActivityInfoBasicService();
    public void execute()throws JSONException, IOException {
        String jsonStr = PMCJSONUtils.readJSONStringFromHttpRequest(ServletActionContext.getRequest());
        System.out.println(jsonStr);
        JSONObject object = new JSONObject(jsonStr);
        if(jsonStr == null)
            return;
        WriteResult result = activityInfoBasicService.saveActivityInfoByJSON(jsonStr);
    }
}
