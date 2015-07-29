package com.pmc.RegistInfo.service;

import com.mongodb.*;
import com.mongodb.util.JSON;
import com.pmc.CommonDBManager.CommonDBManager;
import com.pmc.utils.MongoDBManage.SpringMongoConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;

import java.net.UnknownHostException;
import java.util.List;

/**
 * Created by luluteam on 2015/6/24.
 */
public class RegistInfoBasicService {
   public boolean checkIfDeviceIdExist(String deviceId){
        ApplicationContext ac = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
        MongoOperations mo = (MongoOperations)ac.getBean("mongoTemplate");
        DBCollection collection =  mo.getCollection("RegistInfo");
        DBObject queryObj = new BasicDBObject();
        queryObj.put("deviceId",deviceId);
        DBCursor cursor =  collection.find(queryObj);
        List<DBObject> list = cursor.toArray();
       if (list.size() > 0 ){
           return true;
       }else
           return false;
    }

    public WriteResult saveRegistInfoByJSON(String JSONStr) throws UnknownHostException {
        // TODO Auto-generated method stub

        DBCollection activityRouteInfoCollection = CommonDBManager.MongoDBConnect("RegistInfo");
        DBObject dbObecjt = (DBObject) JSON.parse(JSONStr);
        WriteResult result =  activityRouteInfoCollection.insert(dbObecjt);
        return result;
    }
}
