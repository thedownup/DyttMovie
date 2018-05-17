package com.movie.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import com.movie.pojo.MResult;
import com.movie.service.UserService;
import com.movie.until.FastDFSClient;

/**
 * @author zjt
 * @Description: 用fastdfs进行上传后的保存
 */
@Controller
@RequestMapping("/file")
public class UpLoadController {
	
	private static final Object LOCK = new Object();
	
	@Autowired
	private FastDFSClient fastDFSClient;
	@Autowired
	private UserService userService;
	@Value("${domain}")
	private String domain;
	
	@RequestMapping("/uploadimg")
	@ResponseBody
	public MResult upload(@RequestParam("file") MultipartFile file,Exception e){
		
		if(file == null){
			return MResult.build("不能上传空文件");
		}
		
		//限制上传大小
		if (e instanceof MaxUploadSizeExceededException) {
			return MResult.build("上传图片大小超过10M");
		}
		
		String fileName = file.getOriginalFilename();
		if (!getFileType(fileName).equals("null")) {
			try {
				return MResult.build(domain+fastDFSClient.uploadFile(file.getBytes(),getFileType(fileName)));
			} catch (Exception e1) {
				return MResult.build(e1.getMessage());
			} 
		} else {
			return MResult.build("不支持此类型");
		}
	}
	
	/**
	 * 用springmv+fastdfs来上传 struts2用不了
	 * @param id
	 * @param url
	 * @return
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value="/updateback",method=RequestMethod.POST)
	@ResponseBody
	public MResult updateBack(@RequestParam(name="userId")int id,@RequestParam("file")MultipartFile file){
		synchronized (LOCK) {
			try {
				String url = fastDFSClient.uploadFile(file.getBytes(), getFileType(file.getOriginalFilename()));
				userService.updateBackImg(id, domain+url);
				return MResult.build("修改成功");
			} catch (Exception e) {
				e.printStackTrace();
				return MResult.build("修改失败");
			}
		}
	}
	
	/**
	 * 用springmv+fastdfs来上传 struts2用不了
	 * @param id
	 * @param url
	 * @return
	 */
	@CrossOrigin(origins = "*", maxAge = 3600)
	@RequestMapping(value="/updatetouxian",method=RequestMethod.POST)
	@ResponseBody
	public MResult updateTouxian(@RequestParam(name="userId")int id,@RequestParam("file")MultipartFile file){
		synchronized (LOCK) {
			try {
				String url = fastDFSClient.uploadFile(file.getBytes(), getFileType(file.getOriginalFilename()));
				userService.updateTouxians(id, domain+url);
				return MResult.build("修改成功");
			} catch (Exception e) {
				e.printStackTrace();
				return MResult.build("修改失败");
			}
		}
	}
	
	
	/**
     * 判断是否为允许的上传文件类型,true表示允许
     */
    private String getFileType(String fileName) {
        //设置允许上传文件类型
        String suffixList = "jpg,gif,png,ico,bmp,jpeg";
        // 获取文件后缀
        String suffix = fileName.substring(fileName.lastIndexOf(".")
                + 1, fileName.length());
        if (suffixList.contains(suffix.trim().toLowerCase())) {
            return suffix;
        }
        return "null";
    }
}
