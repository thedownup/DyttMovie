package com.movie.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movie.dao.PrivateMessageDao;
import com.movie.pojo.PrivateMessage;
import com.movie.pojo.User;
import com.movie.service.PrivateMessageService;

@Service
public class PrivateMessageServiceImpl implements PrivateMessageService {

	@Autowired
	private PrivateMessageDao privateMessageDao;
	
	
	@Override
	public List<PrivateMessage> getPrivateMessage(int uid, int fid) {
		return privateMessageDao.getPrivateMessage(uid, fid);
	}

	@Override
	public void sendMessage(int uid, int fid, String message) {
		privateMessageDao.sendMessage(uid, fid, message);
	}

	@Override
	public List<User> getRecentlyUser(int uid) {
		return privateMessageDao.getRecentlyUser(uid);
	}

	@Override
	public List<PrivateMessage> getSystemMessage() {

		return privateMessageDao.getSystemMessage();
	}

}
