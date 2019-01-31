package com.flightsearch.witapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@MultipartConfig
public class WitServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String searchString=req.getParameter("searchString");
		String languageofApp=req.getParameter("languageofApp");
		getApiResponse(searchString,languageofApp);
		
		super.doPost(req, resp);
	}

	
	
	public static void getApiResponse(String searchString,String languageofApp) throws IOException {
	    String url = "https://api.wit.ai/message?v=20190130&q="+searchString;
	    String key="";
	    if(languageofApp.equals("JA")){
	    key = "HYRE3CGXIV2JUBZLXQOUXOT4GFHPCREE";
	    }else{
	    	 key = "CO25MYLYBZ6RA5PU2TSALWXHNKTNOLJ3";	
	    }
	    URLConnection connection = new URL(url).openConnection();
	    connection.setRequestProperty ("Authorization","Bearer " + key);
	    connection.setDoOutput(true);
	    BufferedReader response = new BufferedReader(new InputStreamReader(connection.getInputStream(),StandardCharsets.UTF_8));
	    String line;
	    while((line = response.readLine()) != null) {
	        System.out.println(line);
	    }
	}
}
