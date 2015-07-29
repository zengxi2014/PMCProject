package com.pmc.MemoryInfo.action;

import com.mongodb.WriteResult;
import com.pmc.Activityroute.service.ActivityRouteInfoBasicService;
import com.pmc.MemoryInfo.service.MemoryInfoService;
import com.pmc.utils.PMCJSONUtils;
import org.apache.struts2.ServletActionContext;
import org.json.JSONException;
import java.io.IOException;

/**
 * Created by luluteam on 2015/6/17.
 */
public class ReceiveMemoryInfo {
    private MemoryInfoService memoryInfoService = new MemoryInfoService();
    public void execute()throws JSONException, IOException {
        String jsonStr = PMCJSONUtils.readJSONStringFromHttpRequest(ServletActionContext.getRequest());
        WriteResult result = memoryInfoService.saveMemoryInfoByJSON(jsonStr);
    }
}
