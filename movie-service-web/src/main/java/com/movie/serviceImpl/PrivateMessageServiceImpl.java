package com.movie.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movie.dao.PrivateMessageDao;
import com.movie.pojo.EasyUIDataGridResult;
import com.movie.service.PrivateMessageService;

@Service
public class PrivateMessageServiceImpl implements PrivateMessageService{

	@Autowired
	private PrivateMessageDao privateMessageDao;
	
	@Override
	public void sendSystemPrivateMessage(String message) {
		privateMessageDao.sendSystemPrivateMessage(message);
	}

	@Override
	public EasyUIDataGridResult getPrivateMessage(int page,int num) {
		
		return privateMessageDao.getPrivateMessage(page, num);
	}
	
	
}
