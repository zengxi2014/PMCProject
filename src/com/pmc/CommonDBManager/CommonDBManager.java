package com.pmc.CommonDBManager;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;

public class CommonDBManager {
	public static DBCollection MongoDBConnect(String collectionName)
		    throws UnknownHostException
		  {
		    Mongo connection = new Mongo("125.216.242.117",27017);
		    DB db = connection.getDB("PMC");
		    DBCollection CrackReportCollection = db.getCollection(collectionName);
		    return CrackReportCollection;
		  }
}
