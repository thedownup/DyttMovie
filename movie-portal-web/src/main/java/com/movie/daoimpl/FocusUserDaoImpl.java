package com.movie.daoimpl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.movie.dao.FocusUserDao;
import com.movie.pojo.FocusUser;

@Repository
public class FocusUserDaoImpl  implements FocusUserDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	public Session getSession(){
		return sessionFactory.getCurrentSession();
	} 	

	@Override
	public List<FocusUser> getFocusUser(int uid) {
		
		String sql = "SELECT movie_user.touxiangimg,movie_user.id FROM movie_user WHERE movie_user.id IN (SELECT fid FROM "+
				"focus_user WHERE uid = :uid)";
		
		List<Object[]> list = getSession().createSQLQuery(sql).setParameter("uid", uid).list();
		List<FocusUser> lists = new ArrayList<FocusUser>();
		for (Object[] objects : list) {
			FocusUser fUser = new FocusUser((int)objects[1],(String)objects[0]);
			lists.add(fUser);
		}
		return lists;
	}

	@Override
	public void insertFocusUser(int uid,int fid) {
		
		//先查询是否关注了
		if (checkFocusUser(uid, fid)) {
			return;
		}
		
		String sql = "INSERT INTO focus_user (`uid`,`fid`) VALUE (:uid,:fid);";
		getSession().createSQLQuery(sql).setParameter("uid", uid)
			.setParameter("fid", fid).executeUpdate();
	}

	@Override
	public void removeFocusUser(int uid,int fid) {
		String sql = "DELETE FROM focus_user WHERE uid = :uid AND fid = :fid";
		getSession().createSQLQuery(sql).setParameter("uid", uid).setParameter("fid", fid).executeUpdate();
	}

	@Override
	public boolean checkFocusUser(int uid,int fid) {
		
		String checkSql = "SELECT COUNT(*) FROM focus_user WHERE uid = :uid AND fid = :fid";
		BigInteger bigInteger = (BigInteger) getSession().createSQLQuery(checkSql).setParameter("uid", uid)
			.setParameter("fid", fid).uniqueResult();
		if (bigInteger.intValue() >= 1) {
			return true;
		}
		
		return false;
	}

}
