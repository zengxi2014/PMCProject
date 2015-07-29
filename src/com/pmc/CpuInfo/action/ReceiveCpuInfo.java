package com.pmc.CpuInfo.action;

import com.mongodb.WriteResult;
import com.pmc.CpuInfo.service.CpuService;
import com.pmc.MemoryInfo.service.MemoryInfoService;
import com.pmc.utils.PMCJSONUtils;
import org.apache.struts2.ServletActionContext;
import org.json.JSONException;

import java.io.IOException;

/**
 * Created by luluteam on 2015/6/17.
 */
public class ReceiveCpuInfo {
   // private M = new MemoryInfoService();
    private CpuService cpuService = new CpuService();
    public void execute()throws JSONException, IOException {
        String jsonStr = PMCJSONUtils.readJSONStringFromHttpRequest(ServletActionContext.getRequest());
        WriteResult result = cpuService.savCPUInfoByJSON(jsonStr);
    }
}
