package com.pmc.crackanalyse.DAO;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;

public class CrackReportDBManager {
	public static DBCollection MongoDBConnect()
		    throws UnknownHostException
		  {
		    Mongo connection = new Mongo();
		    DB db = connection.getDB("PMC");
		    DBCollection CrackReportCollection = db.getCollection("CrashReport");
		    return CrackReportCollection;
		  }
}
