package com.pmc.crackanalyse.service;

import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.pmc.CommonDBManager.CommonDBManager;
import com.sun.org.apache.xpath.internal.operations.Bool;

import org.bson.types.ObjectId;
import org.json.JSONObject;
import org.json.JSONArray;
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
import org.junit.Test;

public class CrackReportBasicService {

    public JSONObject findCrashReportByHashCode(String hashCode) throws ParseException, UnknownHostException {
        DBCollection crashTypeReportCollection = CommonDBManager.MongoDBConnect("CrashTypeReport");
        DBObject obj = new BasicDBObject();
        obj.put("hashCode",hashCode);
        DBCursor cursor = crashTypeReportCollection.find(obj);
        List<DBObject> resultList = cursor.toArray();
        JSONObject retJSONObject = new JSONObject();
        JSONArray retJSONArray = new JSONArray();
        for (DBObject dbObject : resultList) {
            retJSONArray.put(dbObject);
        }
        retJSONObject.put("data", retJSONArray);
        return retJSONObject;
    }

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
		String hashCode = (String)dbObecjt.get("hashCode");
        if( _findCrashTypeReportByHashCode(hashCode) == false ) {
            String crashType = (String)dbObecjt.get("CrackType");
            DBObject obj = new BasicDBObject();
            obj.put("CrashType",crashType);
            obj.put("hashCode",hashCode);
            _saveCrashTypeReport(obj);
        }
		return result;
	}

    public JSONObject findAllCrashTypeReportByHashCode(String hashCode) throws UnknownHostException {
        DBCollection crashTypeReportCollection = CommonDBManager.MongoDBConnect("CrashTypeReport");
        DBObject obj = new BasicDBObject();
        obj.put("hashCode", hashCode);
        DBCursor cursor = crashTypeReportCollection.find(obj);
        List<DBObject>  retArray =  cursor.toArray();
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray   = new JSONArray();
        for (DBObject dbObject : retArray) {
            JSONObject dbJSONObj = new JSONObject();
            dbJSONObj.put("hashCode",dbObject.get("hashCode").toString());
            dbJSONObj.put("CrashType", dbObject.get("CrashType").toString());
            dbJSONObj.put("_id",dbObject.get("_id").toString());
            //jsonArray.add(dbJSONObj);
            jsonArray.put(dbJSONObj);
        }
        jsonObject.put("data",jsonArray);
        return  jsonObject;
    }

    @Test
    public void Test()  throws UnknownHostException {
        JSONObject obj =  findAllCrashTypeReportByHashCode("-834561363");
        System.out.println(obj.toString());
    }

    private Boolean _findCrashTypeReportByHashCode(String hashCode)  throws UnknownHostException {
        DBCollection crashTypeReportCollection = CommonDBManager.MongoDBConnect("CrashTypeReport");
        DBObject obj = new BasicDBObject();
        obj.put("hashCode", hashCode);
        DBCursor cursor = crashTypeReportCollection.find(obj);
        List<DBObject> list = cursor.toArray();
        if (cursor.size() == 0) {
            return false;
        }else {
            return true;
        }
    }



	private WriteResult _saveCrashTypeReport(DBObject dbObject)  throws UnknownHostException {
		DBCollection crashTypeReportCollection = CommonDBManager.MongoDBConnect("CrashTypeReport");
        WriteResult result =  crashTypeReportCollection.insert(dbObject);
		return result;
	}
}
