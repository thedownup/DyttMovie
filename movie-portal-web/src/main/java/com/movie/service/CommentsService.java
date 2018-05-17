package com.movie.service;

import com.movie.pojo.NObject;

public interface CommentsService{
	
	public NObject getComments(int page,int num,int mid);
	
	public void sendComments(int uid,int id,String message);
}
