package com.pmc.utils;

import java.io.BufferedReader;

import javax.servlet.http.HttpServletRequest;

public class PMCJSONUtils {
	
	public static String readJSONStringFromHttpRequest(HttpServletRequest request){
		StringBuffer json = new StringBuffer();
        String line = null;
        try {
            BufferedReader reader = request.getReader();
            while((line = reader.readLine()) != null) {
                json.append(line);
            }
        }
        catch(Exception e) {
            System.out.println(e.toString());
        }
        return json.toString();
	}
	
}
