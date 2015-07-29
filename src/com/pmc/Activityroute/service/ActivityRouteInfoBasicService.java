package com.pmc.Activityroute.service;

import com.mongodb.*;
import com.mongodb.util.JSON;
import com.pmc.Activityroute.Model.ActivityRoute;
import com.pmc.Activityroute.Model.Route;
import com.pmc.CommonDBManager.CommonDBManager;
import com.pmc.utils.MongoDBManage.SpringMongoConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-config.xml")

public class ActivityRouteInfoBasicService {



	public WriteResult saveActivityRoutetByJSON(String JSONStr) throws UnknownHostException {
		// TODO Auto-generated method stub
		
		DBCollection activityRouteInfoCollection = CommonDBManager.MongoDBConnect("ActivityRouteInfo");
		DBObject dbObecjt = (DBObject)JSON.parse(JSONStr);
		WriteResult result =  activityRouteInfoCollection.insert(dbObecjt);
		
		return result;
	}

	public List<ActivityRoute> findListByAppId(String appId){
        ApplicationContext ac = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
        MongoOperations  mo = (MongoOperations)ac.getBean("mongoTemplate");
        DBCollection collection =  mo.getCollection("ActivityRouteInfo");
        DBObject queryObj = new BasicDBObject();
        queryObj.put("appId", appId);
        DBCursor cursor =  collection.find(queryObj);
        List<DBObject> list = cursor.toArray();
        List<ActivityRoute> resultList = new ArrayList<>();
        for(DBObject dbObj : list){
            BasicDBList dbList = (BasicDBList)dbObj.get("route");
            Object[] objList = dbList.toArray();
            List<Route> routeList = new ArrayList<>();
           for (Object routeObj : objList ) {
                String name = (String)((DBObject)routeObj).get("name");
                Long createTime = (Long)((DBObject)routeObj).get("createTime");
               Route route = new Route(createTime,name);
               routeList.add(route);
            }

            String appIdStr = (String)dbObj.get("appId");
            String deviceIdStr = (String)dbObj.get("deviceId");
            ActivityRoute activityRoute = new ActivityRoute(deviceIdStr,appIdStr,routeList);
            resultList.add(activityRoute);
        }
		return resultList;
	}

    @Test
    public  void testDB(){
       List<ActivityRoute> result = findListByAppId("85d4a553-ee8d-4136-80ab-2469adcae44d");
        for (ActivityRoute activityRoute : result) {
            System.out.println(activityRoute.getAppId());
            List<Route> routList = activityRoute.getRoute();
            for (Route route : routList) {
                System.out.println(route.getName());
                System.out.println(route.getCreateTime());
            }
        }
    }

    @Test
    public void testFindDeviceId(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
        MongoOperations  mo = (MongoOperations)ac.getBean("mongoTemplate");
        DBCollection collection =  mo.getCollection("ActivityRouteInfo");
        DBObject queryObj = new BasicDBObject();
        queryObj.put("deviceId","ffffffff-8c6e-cb34-ffff-ffffd322cd58");
        DBCursor cursor =  collection.find(queryObj);
        List<DBObject> list = cursor.toArray();
        System.out.println(list.size());
    }

}
