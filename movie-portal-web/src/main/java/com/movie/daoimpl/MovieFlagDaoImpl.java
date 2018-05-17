package com.movie.daoimpl;

import java.math.BigInteger;

import org.springframework.stereotype.Repository;

import com.movie.dao.MovieFlagDao;
import com.movie.pojo.MovieFlag;

@Repository
public class MovieFlagDaoImpl extends BaseDaoImpl<MovieFlag> implements MovieFlagDao{

	@Override
	public void save(int uid,int mid,MovieFlag movieFlag) {
		
		//判断有没有
		String haveSql = "SELECT COUNT(1) FROM movie_flag WHERE uid = :uid AND mid = :mid";
		BigInteger bigInteger = (BigInteger) getSession().createSQLQuery(haveSql).setParameter("uid", uid).setParameter("mid", mid)
			.uniqueResult();
		if (bigInteger.intValue() >= 1) {
			String sql = "update movie_flag set wantSee = ?,seeBefore = ?,likeSee = ? where uid = ? and mid = ?";
			getSession().createSQLQuery(sql).setParameter(3, uid).setParameter(4, mid)
			.setParameter(0, movieFlag.getWantSee()).setParameter(1, movieFlag.getSeeBefore()).setParameter(2, movieFlag.getLikeSee())
			.executeUpdate();
		} else {
			String sql = "INSERT INTO movie_flag (`uid`,`mid`,`wantSee`,`seeBefore`,`likeSee`) VALUE (?, ?, ?, ?,?)";
			getSession().createSQLQuery(sql).setParameter(0, uid).setParameter(1, mid)
				.setParameter(2, movieFlag.getWantSee()).setParameter(3, movieFlag.getSeeBefore())
				.setParameter(4, movieFlag.getLikeSee()).executeUpdate();
		}
	}

	@Override
	public MovieFlag get(int uid, int mid) {

		String flag = "select count(*) from movie_flag where uid = :uid and mid = :mid";
		
		int isExit = ((Number)getSession().createSQLQuery(flag).setParameter("uid", uid).setParameter("mid", mid).list().iterator().next()).intValue();
		if (isExit <= 0) {
			String create = "INSERT INTO movie_flag(`uid`,`mid`) VALUE (:uid,:mid)";
			getSession().createSQLQuery(create).setParameter("uid", uid).setParameter("mid", mid).executeUpdate();
		}
		
		String hql = "select new MovieFlag(id,wantSee,seeBefore,likeSee) from MovieFlag m where m.movieInfo.id = :mid and m.user.id = :uid";
		MovieFlag movieFlag = (MovieFlag) getSession().createQuery(hql).setParameter("mid", mid).setParameter("uid", uid).uniqueResult();
		return movieFlag;
	}

	@Override
	public void delete(int uid, int id) {

		String sql = "DELETE FROM movie_flag WHERE uid = :uid AND mid = :mid";
		getSession().createSQLQuery(sql).setParameter("uid", uid).setParameter("mid", id).executeUpdate();

	}

	@Override
	public int getLikeSee(int mid) {

		String hql = "select count(1) from MovieFlag m where m.likeSee = 1 and m.movieInfo.id = ?";


		return ((Number)getSession().createQuery(hql).setParameter(0, mid).iterate().next()).intValue();
	}

	@Override
	public int getWantSee(int mid) {
		
		String hql = "select count(1) from MovieFlag m where m.wantSee = 1 and m.movieInfo.id = ?";

		return ((Number)getSession().createQuery(hql).setParameter(0, mid).iterate().next()).intValue();

	}

	@Override
	public int getSeeBefore(int mid) {
		
		String hql = "select count(1) from MovieFlag m where m.seeBefore = 1 and m.movieInfo.id = ?";

		return ((Number)getSession().createQuery(hql).setParameter(0, mid).iterate().next()).intValue();

	}

}
