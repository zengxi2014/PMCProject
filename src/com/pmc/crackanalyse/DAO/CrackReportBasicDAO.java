package com.pmc.crackanalyse.DAO;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;

import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.pmc.crackanalyse.model.CrackDeviceInfo;
import com.pmc.crackanalyse.model.CrackReport;

public class CrackReportBasicDAO {
	public static List<CrackReport> load(DBCursor cursor) throws ParseException{
		List<DBObject> crackReportCursonList = cursor.toArray();
		if(crackReportCursonList.size()==0){
			return null;
		}
		List<CrackReport> crackReportList = new ArrayList<CrackReport>();
		for(DBObject dbObject : crackReportCursonList){
			CrackReport crackReport = new CrackReport();
			ObjectId objectId = (ObjectId)dbObject.get("_id");
			crackReport.setId(objectId.toString());
			
			
			 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			 String dateString = (String)dbObject.get("CrackTime");
			 Date date = formatter.parse(dateString);
			
			crackReport.setCrackTime(date);			
			crackReport.setCrackType((String)dbObject.get("CrackType"));
			crackReport.setCrackInfo((String)dbObject.get("CrackStackInfo"));
			
			DBObject deviceInfoObject = (DBObject)dbObject.get("Crack_Device_Info");
			CrackDeviceInfo deviceInfo = new CrackDeviceInfo();
			deviceInfo.setBoard((String)deviceInfoObject.get("Board"));
			deviceInfo.setBootLoader((String)deviceInfoObject.get("BootLoader"));
			deviceInfo.setBrand((String)deviceInfoObject.get("Brand"));
			deviceInfo.setBrandInfo((String)deviceInfoObject.get("BrandInfo"));
			deviceInfo.setCpu_abi((String)deviceInfoObject.get("CPU_ABI"));
			deviceInfo.setCpu_abi2((String)deviceInfoObject.get("CPU_ABI2"));
			deviceInfo.setDevice((String)deviceInfoObject.get("Device"));
			deviceInfo.setDisplay((String)deviceInfoObject.get("Display"));
			deviceInfo.setHardware((String)deviceInfoObject.get("HardWare"));
			deviceInfo.setManufacturer((String)deviceInfoObject.get("Manufacturer"));
			deviceInfo.setModel((String)deviceInfoObject.get("Model"));
			deviceInfo.setProduct((String)deviceInfoObject.get("Product"));
			deviceInfo.setSdkVersion((String)deviceInfoObject.get("SDKVersion"));
			deviceInfo.setVersionMain((String)deviceInfoObject.get("VersionMain"));
			deviceInfo.setVersionRelease((String)deviceInfoObject.get("VersionRelease"));
			
			crackReport.setCrackDeviceInfo(deviceInfo);
			crackReportList.add(crackReport);
		}
		return crackReportList;
		
	}
}
