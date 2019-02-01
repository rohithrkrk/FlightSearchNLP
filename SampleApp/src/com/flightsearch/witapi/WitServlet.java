package com.flightsearch.witapi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

/**
 * @author A-2574
 *
 */
@MultipartConfig
public class WitServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String searchString=req.getParameter("searchString");
		String languageofApp=req.getParameter("languageofApp");
		//languageofApp="en";
		 //String nonchromeFlag=req.getParameter("nonchromeFlag");
		InputStream input = req.getInputStream(); 
		byte[] bytes =IOUtils.toByteArray(input); 
		/*
		 * File targetFile = new
		 * File("C:\\Users\\A-2574\\Desktop\\Rohith\\targetFile.wav"); OutputStream
		 * outStream = new FileOutputStream(targetFile);  
		 * IOUtils.toByteArray(input); outStream.write(bytes); outStream.close();
		 */
		 if(bytes.length>0) {
			
			getApiTextResponsefromWav(bytes, languageofApp);
			 
		 }else {
			 getApiTextResponse(searchString,languageofApp);
		 }
		
		
		super.doPost(req, resp);
	}

	
	
	
	  public static void getApiTextResponse(String searchString,String
	  languageofApp) throws IOException { String url =
	  "https://api.wit.ai/message?v=20190130&q="+searchString; String key="";
	  if(languageofApp.equals("JA")){ key = "HYRE3CGXIV2JUBZLXQOUXOT4GFHPCREE";
	  }else{ key = "PVA4CHCJYCXCA2MRNFVGB5B7FZQEHXD4"; } URLConnection connection =
	  new URL(url).openConnection(); connection.setRequestProperty
	  ("Authorization","Bearer " + key); connection.setDoOutput(true);
	  BufferedReader response = new BufferedReader(new
	  InputStreamReader(connection.getInputStream())); String line; while((line =
	  response.readLine()) != null) { System.out.println(line); } }
	 
	    public static void getApiTextResponsefromWav(byte[] searchString,String languageofApp) throws IOException {
	    	String url = "https://api.wit.ai/speech";
	    	String key="";
		/*
		 * if(languageofApp.equals("JA")){ key = "HYRE3CGXIV2JUBZLXQOUXOT4GFHPCREE";
		 * }else{ key = "PVA4CHCJYCXCA2MRNFVGB5B7FZQEHXD4"; }
		 */
	    	 key = "PVA4CHCJYCXCA2MRNFVGB5B7FZQEHXD4";
	        String param1 = "20170203";
	        String param2 = "command";
	        String charset = "UTF-8";

	        String query = String.format("v=%s",
	                URLEncoder.encode(param1, charset));


	        URLConnection connection = new URL(url + "?" + query).openConnection();
	        connection.setRequestProperty ("Authorization","Bearer " + key);
	        connection.setRequestProperty("Content-Type", "audio/wav");
	        connection.setDoOutput(true);
	        OutputStream outputStream = connection.getOutputStream();
	        outputStream.write(searchString);
		/*
		 * OutputStream outputStream = connection.getOutputStream(); FileChannel
		 * fileChannel = new FileInputStreamÅipath to sample.wav).getChannel();
		 * ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
		 * 
		 * while((fileChannel.read(byteBuffer)) != -1) { byteBuffer.flip(); byte[] b =
		 * new byte[byteBuffer.remaining()]; byteBuffer.get(b); outputStream.write(b);
		 * byteBuffer.clear(); }
		 */

	        BufferedReader response = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	        String line;
	        while((line = response.readLine()) != null) {
	            System.out.println(line);
	        }
	}
	   
}
