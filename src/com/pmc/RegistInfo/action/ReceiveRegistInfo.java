package com.pmc.RegistInfo.action;

import com.mongodb.WriteResult;
import com.pmc.RegistInfo.service.RegistInfoBasicService;
import com.pmc.utils.PMCJSONUtils;
import org.apache.struts2.ServletActionContext;
import org.json.JSONObject;
import org.json.JSONException;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by luluteam on 2015/6/24.
 */
public class ReceiveRegistInfo{

    private RegistInfoBasicService registInfoBasicService = new RegistInfoBasicService();

    public void execute()throws JSONException, IOException {
        String jsonStr = PMCJSONUtils.readJSONStringFromHttpRequest(ServletActionContext.getRequest());
        if (jsonStr == null) return;
        JSONObject object = new JSONObject(jsonStr);
        PrintWriter out = ServletActionContext.getResponse().getWriter();
        if (object != null) {
            String deviceId = (String)object.get("deviceId");
            if ( registInfoBasicService.checkIfDeviceIdExist(deviceId) == false){
                registInfoBasicService.saveRegistInfoByJSON(jsonStr);
            }
        }
    }
}
