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

import java.util.LinkedList;

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
    public List<List<String>> getRoutes(String appId){
        List<ActivityRoute> activityRoutes=findListByAppId(appId);
        if(activityRoutes==null)return null;
        else{
            List<List<String>> routes= new LinkedList<>();
            for(int i=0;i<activityRoutes.size();i++) {
                ActivityRoute activityRoute = activityRoutes.get(i);
                List<Route>record=activityRoute.getRoute();
                List<String> route = new LinkedList<>();
                for (int j=0;j<record.size()-1;j++){
                    //过滤掉停留时间少于2秒的页面，其中包含0.5秒的默认加载时间，视为无效节点
                    if(record.get(j+1).getCreateTime()-record.get(j).getCreateTime()>=2000) {
                        route.add(record.get(j).getName().substring(record.get(j).getName().lastIndexOf(".") + 1));
                    }
                }
                route.add(record.get(record.size()-1).getName().substring(record.get(record.size()-1).getName().lastIndexOf(".") + 1));
                //过滤加载页面较多或者会话时间超过30min的异常记录
                if(route.size()<=21&&record.get(record.size()-1).getCreateTime()-record.get(0).getCreateTime()<=1800000)
                    routes.add(route);
            }
            // System.out.println(routes);
            return routes;
        }
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
