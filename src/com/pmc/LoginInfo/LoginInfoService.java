package com.pmc.LoginInfo;

import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;
import com.mongodb.util.JSON;
import com.pmc.CommonDBManager.CommonDBManager;

import java.net.UnknownHostException;

/**
 * Created by luluteam on 2015/7/8.
 */
public class LoginInfoService {
    public WriteResult saveLoginInfoByJSON(String JSONStr) throws UnknownHostException {
        // TODO Auto-generated method stub

        DBCollection activityRouteInfoCollection = CommonDBManager.MongoDBConnect("LoginInfo");
        DBObject dbObecjt = (DBObject) JSON.parse(JSONStr);
        WriteResult result =  activityRouteInfoCollection.insert(dbObecjt);

        return result;
    }
}
