package com.pmc.MemoryInfo.service;

import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;
import com.mongodb.util.JSON;
import com.pmc.CommonDBManager.CommonDBManager;

import java.net.UnknownHostException;

/**
 * Created by luluteam on 2015/6/17.
 */
public class MemoryInfoService {
    public WriteResult saveMemoryInfoByJSON(String JSONStr) throws UnknownHostException {
        // TODO Auto-generated method stub

        DBCollection activityRouteInfoCollection = CommonDBManager.MongoDBConnect("MemoryInfo");
        DBObject dbObecjt = (DBObject) JSON.parse(JSONStr);
        WriteResult result =  activityRouteInfoCollection.insert(dbObecjt);

        return result;
    }
}
