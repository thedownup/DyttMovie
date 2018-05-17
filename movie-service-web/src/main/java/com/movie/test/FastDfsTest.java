package com.movie.test;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.movie.until.FastDFSClient;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring/*.xml"})
public class FastDfsTest {

	@Value("${tracker_server}")
	String track ;
	@Autowired
	private FastDFSClient fastDFSClient;


	@Test
	public void testFastClient(){
		try {
			File file = new File("F:\\爬的图片\\A片三贱客 Les Kaïra (2012).jpg");
			String url = fastDFSClient.uploadFile(FileUtils.readFileToByteArray(file),"jpg");
			System.out.println(url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testValue(){
		System.out.println(track);
	}
}
