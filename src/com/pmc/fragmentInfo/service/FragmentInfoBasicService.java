package com.pmc.fragmentInfo.service;

import java.net.UnknownHostException;

import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;
import com.mongodb.util.JSON;
import com.pmc.CommonDBManager.CommonDBManager;

public class FragmentInfoBasicService {
	public WriteResult saveFragmentInfoByJSON(String JSONStr) throws UnknownHostException {
		// TODO Auto-generated method stub
		
		DBCollection fragmentInfoCollection = CommonDBManager.MongoDBConnect("FragmentInfo");
		DBObject dbObecjt = (DBObject)JSON.parse(JSONStr);
		WriteResult result =   fragmentInfoCollection.insert(dbObecjt);
		
		return result;
	}
}
