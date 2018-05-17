package com.movie.test;



import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import org.junit.Test;

import com.movie.until.URLEncoders;

public class GeneralTest {

	public static void main(String[] args) {
		String ipAndPort = "101.81.141.175:9999";
		String ip = ipAndPort.substring(0,ipAndPort.indexOf(":"));
		int port = Integer.valueOf(ipAndPort.substring(ipAndPort.indexOf(":")+1));
		System.out.println(ip);
		System.out.println(port);
	}

	@Test
	public void testScore(){
		String type = "豆瓣 7.6";
		System.out.println(type.substring(type.indexOf("瓣")+1,type.length()).trim());
	}

	@Test
	public void findTslVersion() throws Exception{

		SSLContext context = SSLContext.getInstance("TLS");  
		context.init(null, null, null);  

		SSLSocketFactory factory = (SSLSocketFactory) context.getSocketFactory();  
		SSLSocket socket = (SSLSocket) factory.createSocket();  

		String[] protocols = socket.getSupportedProtocols();  

		System.out.println("Supported Protocols: " + protocols.length);  
		for (int i = 0; i < protocols.length; i++) {  
			System.out.println(" " + protocols[i]);  
		}  

		protocols = socket.getEnabledProtocols();  

		System.out.println("Enabled Protocols: " + protocols.length);  
		for (int i = 0; i < protocols.length; i++) {  
			System.out.println(" " + protocols[i]);  
		}  
	}
	
	@Test
	public void parseDyttUrl(){
		String encode = URLEncoders.encode("ftp://dygod1:dygod1@d122.dygod.cn:1383/窃听风云.[国粤双语中字.1024分辨率]/[电影天堂www.dygod.com].窃听风云.rmvb");
		System.out.println(encode);
	}
	
	@Test
	public void testString(){
		String info = "IMDB评分";
		String type = info.substring(0, 4).replaceAll("　　", "");
		String value = info.substring(5);
		System.out.println(value);
	}

}
