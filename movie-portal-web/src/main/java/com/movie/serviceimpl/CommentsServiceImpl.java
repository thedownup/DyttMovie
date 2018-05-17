package com.movie.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movie.dao.CommentsDao;
import com.movie.pojo.NObject;
import com.movie.service.CommentsService;

@Service
public class CommentsServiceImpl implements CommentsService{

	@Autowired
	private CommentsDao commentsDao;
	
	@Override
	public NObject getComments(int page, int num, int mid) {

		NObject nObject = commentsDao.getComments(page, num, mid);
		
		return nObject;
	}

	@Override
	public void sendComments(int uid, int id, String message) {
		commentsDao.saveComments(uid, id, message);
	}


}
