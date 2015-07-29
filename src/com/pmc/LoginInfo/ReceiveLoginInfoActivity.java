package com.pmc.LoginInfo;

import com.pmc.RegistInfo.service.RegistInfoBasicService;
import com.pmc.utils.PMCJSONUtils;
import org.apache.struts2.ServletActionContext;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by luluteam on 2015/7/8.
 */
public class ReceiveLoginInfoActivity {
    private LoginInfoService loginInfoService = new LoginInfoService();

    public void execute()throws JSONException, IOException {
        String jsonStr = PMCJSONUtils.readJSONStringFromHttpRequest(ServletActionContext.getRequest());
        if (jsonStr == null) return;
        loginInfoService.saveLoginInfoByJSON(jsonStr);
    }
}
