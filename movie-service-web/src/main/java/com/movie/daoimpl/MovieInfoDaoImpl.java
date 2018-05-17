package com.movie.daoimpl;


import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.movie.dao.MovieDao;
import com.movie.dao.MovieInfoDao;
import com.movie.pojo.EasyUIDataGridResult;
import com.movie.pojo.Movie;
import com.movie.pojo.MovieInfo;

@Repository
public class MovieInfoDaoImpl extends BaseDaoImpl<MovieInfo> implements MovieInfoDao{

	@Autowired
	private MovieDao movieDao;
	
	@Override
	public EasyUIDataGridResult getMovieInfos(int page, int num) {

		EasyUIDataGridResult easyUIDataGridResult = new EasyUIDataGridResult();
		
		String numHql = "select count(1) from MovieInfo";
		int Mium = ((Number)getSession().createQuery(numHql).iterate().next()).intValue();

		String hql = "from MovieInfo";
		List list = getSession().createQuery(hql).setMaxResults(num).setFirstResult((page-1)*num).list();

		easyUIDataGridResult.setRows(list);
		easyUIDataGridResult.setTotal(Mium);
		
		return easyUIDataGridResult;
	}

	@Override
	public void deleteById(int... ids) {
		String param = "";

		for (int i = 0; i < ids.length; i++) {
			if ((i+1) == ids.length) {
				param = param + ids[i];
			}else {
				param = param + ids[i] + ",";
			}
		}
		
		String sql = "DELETE FROM movie WHERE movie.id IN (SELECT mid FROM movieinfo WHERE movieinfo.id IN ("+param+"))";
		getSession().createSQLQuery(sql).executeUpdate();
	}

	@Override
	public void updateMovieInfo(MovieInfo movieInfo) {
		Movie movie = movieDao.getMovieById(movieInfo.getId());
		movieInfo.setMovie(movie);
		update(movieInfo);
	}

	@Override
	public EasyUIDataGridResult getMovieInfoByMovieId(int mid) {
		EasyUIDataGridResult easyUIDataGridResult = new EasyUIDataGridResult();
		String numHql = "select count(1) from movie m where m.id = :mid";
		Query query = getSession().createSQLQuery(numHql).setParameter("mid", mid);
		List<BigInteger> nlist = query.list();
		int count = 0;
		if (nlist.get(0).intValue() > 0) {
			count = nlist.get(0).intValue();
			String hql = "from MovieInfo m where m.movie.id = :mid";
			MovieInfo movieInfo = (MovieInfo) getSession().createQuery(hql).setParameter("mid", mid).list().get(0);
			
			easyUIDataGridResult.setTotal(count);
			
			List<MovieInfo> list = new ArrayList<MovieInfo>();
			list.add(movieInfo);
			easyUIDataGridResult.setRows(list);
			return easyUIDataGridResult;
		}else {
			easyUIDataGridResult.setTotal(0);
			easyUIDataGridResult.setRows(null);
			return easyUIDataGridResult;
		}
	}

	@Override
	public MovieInfo getMovieInfoById(int id) {
		
		String hql = "from MovieInfo mi where mi.id = :id";
		
		return (MovieInfo) getSession().createQuery(hql).setParameter("id", id).uniqueResult();
	}

}
