package com.pmc.Activityroute.service;

import java.net.UnknownHostException;
import java.util.List;

import com.mongodb.*;
import com.mongodb.util.JSON;
import com.pmc.CommonDBManager.CommonDBManager;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.bson.types.ObjectId;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import test.SpringMongoConfig;

public class ActivityRouteInfoBasicService {
	public WriteResult saveActivityRoutetByJSON(String JSONStr) throws UnknownHostException {
		// TODO Auto-generated method stub
		
		DBCollection activityRouteInfoCollection = CommonDBManager.MongoDBConnect("ActivityRouteInfo");
		DBObject dbObecjt = (DBObject)JSON.parse(JSONStr);
		WriteResult result =  activityRouteInfoCollection.insert(dbObecjt);
		
		return result;
	}

	public JSONObject getAllActivityRoutesByAppId(String AppId){
		JSONObject ret = new JSONObject();
		ApplicationContext ac = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
		MongoOperations mo = (MongoOperations)ac.getBean("mongoTemplate");
		// mo.remove(new Query(Criteria.where("_id").is("5559bfe6e039fb36082da356")),"ActivityInfo");
		DBCollection collection =  mo.getCollection("ActivityInfo");
		DBObject obj = new BasicDBObject();
		obj.put("appId",AppId);
		DBCursor cursor =  collection.find(obj);
		List<DBObject> list = cursor.toArray();
		JSONArray retArray = new JSONArray();
		for(DBObject dbObject : list){
			JSONObject activityInfoObject = new JSONObject();
			//id
			ObjectId id = (ObjectId)dbObject.get("_id");
			String idString = id.toString();

			//appName
			String appName = (String)dbObject.get("appName");

			String osVersion = (String)dbObject.get("osVersion");

			//String osVersion = (String)dbObject.get("osVersion");

			String appVersion = (String)dbObject.get("appVersion");

			String manufacturer = (String)dbObject.get("manufacturer");

			String loadTime = (String)dbObject.get("");

		}
		return null;
	}

}
