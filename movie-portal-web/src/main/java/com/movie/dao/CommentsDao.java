package com.movie.dao;

import com.movie.pojo.Comments;
import com.movie.pojo.NObject;

public interface CommentsDao extends BaseDao<Comments>{
	
	public NObject getComments(int page,int num,int mid);
	
	public void saveComments(int uid,int id,String message) ;
	
}
