package com.movie.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.junit.Test;

import com.movie.pojo.Reply;
import com.movie.pojo.ReplyInner;
import com.movie.untils.JsonUtils;

public class GeneralTest {
	
	@Test
	public void test(){
		Properties prop = new Properties(); 
		try {
			prop.load(this.getClass().getResourceAsStream("/conf/client.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(prop.get("tracker_server"));
	}
	
	@Test
	public void jsonTest(){
		String jsonData = "{\"num\":1,\"replyInners\":[{\"date\":\"2018-04-25 18:03:21\",\"name\":\"2\",\"message\":\"然而我\"},{\"date\":\"2018-04-25 18:03:21\",\"name\":\"2\",\"message\":\"然而我\"}]}";
		Reply reply = JsonUtils.jsonToPojo(jsonData, Reply.class);
	
		List<ReplyInner> replyInners = reply.getReplyInners();
		for (ReplyInner replyInner : replyInners) {
			System.out.println(replyInner.toString());
		}
	}
	
	@Test
	public void testStrng(){
		String temp = "克里斯·海姆斯沃斯 Chris Hemsworth 　　　　　　迈克尔·珊农 Michael Shannon 　　　　　　迈克尔·佩纳 Michael Peña 　　　　　　纳维德·内加班 Navid Negahban 　　　　　　崔凡特·罗兹 Trevante Rhodes 　　　　　　吉欧夫·斯图兹 Geoff Stults 　　　　　　萨德·拉金比尔 Thad Luckinbill";
		String[] split = temp.split(" +");
		StringBuffer stringBuffer = new StringBuffer();
		for (String string : split) {
			stringBuffer.append(string);
			System.out.println(string);
		}
		
		System.out.println(stringBuffer.toString().replaceAll(" +",""));
	}
	
	@Test
	public void testHashMap(){
		HashMap<String,String> hashMap = new HashMap<String,String>();
		hashMap.put("1", "2");
		hashMap.put("2", "3");
		hashMap.putIfAbsent("2", "4");//相同时不替换
		Set<String> keySet = hashMap.keySet();
		for (String string : keySet) {
			System.out.println(string+"="+hashMap.get(string));
		}
	}
	
	@Test
	public void testNull(){
		int a = '　';
		System.out.println(a);
		String temp = " 	帕拉巴斯Prabhas　　　　　　拉纳·达格巴提RanaDaggubati　　　　　　安努舒卡·谢蒂AnushkaShetty　　　　　　特曼娜·芭蒂亚TamannaahBhatia　　　　　　萨伯拉杰Subbaraju　　　　　　拉姆亚·克里希南RamyaKrishnan　　　　　　纳赛尔Nasser　　　　　　挲塞亚拉杰Satyaraj　　　　　　";
		System.out.println(temp.replaceAll((char)12288 + "", " ").replaceAll("\\s+", " "));
	}
	
}
