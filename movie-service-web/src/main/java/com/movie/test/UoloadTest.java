package com.movie.test;

import java.io.File;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.movie.controller.UpLoadController;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring/*.xml"})
public class UoloadTest {
	
	@Autowired
	private UpLoadController upLoadController;
	
	@Test
	public void uploadfile(){
		File file = new File("F:\\爬的图片\\A片三贱客 Les Kaïra (2012).jpg");
	}
	
}
