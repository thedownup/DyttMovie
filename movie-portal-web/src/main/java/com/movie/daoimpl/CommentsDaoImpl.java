package com.movie.daoimpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.movie.dao.CommentsDao;
import com.movie.pojo.Comments;
import com.movie.pojo.NObject;

@Repository
public class CommentsDaoImpl extends BaseDaoImpl<Comments> implements CommentsDao{

	@Override
	public NObject getComments(int page,int num,int mid) {
		NObject nObject = new NObject();
		String hql = "from Comments c where c.movieInfo.id = :mid";
		@SuppressWarnings("unchecked")
		List<Comments> comments = getSession().createQuery(hql).setMaxResults(num)
				.setParameter("mid", mid).setFirstResult((page-1)*num).list();

		String numHql =  "select count(*) from Comments c where c.movieInfo.id = :mid";
		int nums = ((Number)getSession().createQuery(numHql).setParameter("mid", mid).iterate().next()).intValue();
		nObject.setObjects(comments);
		nObject.setNum(nums);
		return nObject;
	}
	
	
	@Override
	public void saveComments(int uid, int id, String message) {
		
		String sql = "INSERT INTO comments (`uid`,`mid`,`message`,`sendDate`) VALUE (:uid, :id, :message, :date)";
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
		String date = simpleDateFormat.format(new Date());
		
		getSession().createSQLQuery(sql).setParameter("uid", uid).setParameter("id", id)
			.setParameter("message", message).setParameter("date", date).executeUpdate();
		
	}

}
