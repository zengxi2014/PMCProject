package com.pmc.crackanalyse.service;

import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.WriteResult;
import com.mongodb.util.JSON;
import com.pmc.crackanalyse.DAO.CrackReportBasicDAO;
import com.pmc.crackanalyse.DAO.CrackReportDBManager;
import com.pmc.crackanalyse.model.CrackReport;

public class CrackReportBasicService {
	
	public CrackReport getCrackReportById(String id) throws ParseException, UnknownHostException{
		DBCollection crackReportCollection =  CrackReportDBManager.MongoDBConnect();
		DBObject obj = new BasicDBObject();
	
		ObjectId objid = new ObjectId(id);
		obj.put("_id", objid);
		DBCursor cursor = crackReportCollection.find(obj);
		List<CrackReport>  resultList = CrackReportBasicDAO.load(cursor);
		if(resultList==null)
			return null;
		return resultList.get(0);
	}
	
	public List<CrackReport> loadCrackReport() throws UnknownHostException, ParseException{
		DBCollection crackReportCollection =  CrackReportDBManager.MongoDBConnect();
		DBCursor cursor = crackReportCollection.find();
		List<CrackReport> resultList = CrackReportBasicDAO.load(cursor);
	
		
		return resultList;
	}

	public WriteResult saveCrackReportByJSON(String JSONStr) throws UnknownHostException {
		// TODO Auto-generated method stub
		
		DBCollection crackReportCollection =  CrackReportDBManager.MongoDBConnect();
		DBObject dbObecjt = (DBObject)JSON.parse(JSONStr);
		WriteResult result =  crackReportCollection.insert(dbObecjt);
		
		return result;
	}
	
	public WriteResult saveActivityInfoByJSON(String JSONStr) throws UnknownHostException {
		// TODO Auto-generated method stub
		Mongo connection = new Mongo();
		DB db = connection.getDB("PMC");
		DBCollection actvityInfoCollection = db.getCollection("ActivityInfo");
		DBObject dbObecjt = (DBObject)JSON.parse(JSONStr);
		WriteResult result = actvityInfoCollection.insert(dbObecjt);		
		return result;
	}
	
	
	
	
}
