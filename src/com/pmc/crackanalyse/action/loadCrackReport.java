package com.pmc.crackanalyse.action;

import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.List;

import com.pmc.crackanalyse.model.CrackReport;
import com.pmc.crackanalyse.service.CrackReportBasicService;

public class loadCrackReport {
	private CrackReportBasicService crackReportBasicService = new CrackReportBasicService();
	public void loadCrackReportById() throws UnknownHostException, ParseException{
		/*
		List<CrackReport> resultlist = crackReportBasicService.loadCrackReport();
		for(CrackReport report : resultlist){
			System.out.println(report.getId());
			System.out.println(report.getCrackTime().toString());
			System.out.println(report.getCrackType());
			System.out.println(report.getCrackInfo());
		}
		*/
		CrackReport report =  crackReportBasicService.getCrackReportById("549a35ac9e48edc06770f6a3");
		if(report==null){
			System.out.println("找不到该数据");
		}else{
		System.out.println(report.getCrackInfo());
		}
	}
	
	public void loadAllCrackReport()throws UnknownHostException, ParseException{
		List<CrackReport> resultlist = crackReportBasicService.loadCrackReport();
		for(CrackReport report : resultlist){
			System.out.println("-------------------------------------");
			System.out.println(report.getId());
			System.out.println(report.getCrackTime().toString());
			System.out.println(report.getCrackType());
			System.out.println(report.getCrackInfo());
			System.out.println("-------------------------------------");
		}
	}
}
