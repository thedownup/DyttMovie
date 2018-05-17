package com.movie.actionimpl;

import java.io.File;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.movie.pojo.User;
import com.movie.service.UserService;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 
 * @author zjt
 * @Description: 上传用的
 */
@Service
public class FileAction extends ActionSupport implements SessionAware {

	@Autowired
	private UserService userService;
	@Value("${PATHBACKIMG}")
	private String PATHBACKIMG;
	@Value("${PATHICONIMG}")
	private String PATHICONS;
	
	private Map<String, Object> session;
	public String jsonData;
	public File upload;// 上传文件
	public String uploadContentType;// 上传文件类型
	public String uploadFileName;// 上传文件名


	@Override
	public void validate() {
		super.validate();
	}

	/**
	 * 上传背景
	 * @return
	 */
	public String uploadBackground() {
		Lock lock = new ReentrantLock();
		lock.lock();
		try {
			//不存在就创建
			File file = new File(PATHBACKIMG);
			if (!file.exists()) {
				file.mkdir();
			}
			FileUtils.copyFile(upload, new File(file,uploadFileName));
			User user = (User) session.get("user");
			user.setBackImg(PATHBACKIMG+"/"+uploadFileName);
			userService.update(user);
			jsonData = "上传背景成功";
			return Action.SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			jsonData = "上传背景失败";
			return Action.SUCCESS;
		}finally{
			lock.unlock();
		}
	}
	

	public String getJsonData() {
		return jsonData;
	}

	public File getUpload() {
		return upload;
	}

	public String getUploadContentType() {
		return uploadContentType;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

}
