package com.movie.daoimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.movie.dao.SingleMovieDao;
import com.movie.pojo.FilterTag;
import com.movie.pojo.Movie;
import com.movie.pojo.NObject;
import com.movie.pojo.SingleMovie;

@Repository
public class SingleMovieDaoImpl extends BaseDaoImpl<SingleMovie> implements SingleMovieDao{

	@SuppressWarnings("unchecked")
	@Override
	public NObject getMovies(int page, int num,FilterTag filterTag) {

		String score = "";
		String type = "";
		String year = "";
		String area = "";
		//时间  类型 分数 地区
		Pattern pattern = Pattern.compile("[0-9]+-[0-9]+");
		Matcher matcher = pattern.matcher(filterTag.getScore());
		if (matcher.matches() != true) {
			score = "0-9";
		}else {
			score = filterTag.getScore();
		}

		if (filterTag.getArea().equals("全部")) 
			area = "";
		else 
			area = filterTag.getArea();

		if (filterTag.getType().equals("全部")) 
			type = "";
		else
			type = filterTag.getType();

		if (filterTag.getYear().equals("全部")) 
			year = "";
		else
			year = filterTag.getYear();


		String hql = "from SingleMovie where year like :year and area like :area "
				+ "and score between :li and :hi and type like :type and isMovie = 0";

		Query query = getSession().createQuery(hql).setParameter("year", "%"+year+"%")
				.setParameter("area", "%"+area+"%").setParameter("li", score.split("-")[0])
				.setParameter("hi", score.split("-")[1]).setParameter("type", "%"+type+"%");

		//获得条数
		String numHql = "select count(*) from SingleMovie where year like :year and area like :area "
				+ "and score between :li and :hi and type like :type and isMovie = 0";

		Query numQuery = getSession().createQuery(numHql).setParameter("year", "%"+year+"%")
				.setParameter("area", "%"+area+"%").setParameter("li", score.split("-")[0])
				.setParameter("hi", score.split("-")[1]).setParameter("type", "%"+type+"%");

		NObject nObject = new NObject();
		nObject.setObjects(query.setMaxResults(num).setFirstResult((page-1)*num).list());
		nObject.setNum(((Number)numQuery.iterate().next()).intValue());
		return nObject;

	}


	/**
	 * 获得总的数量
	 */
	@Override
	public int getCount() {
		String hql = "select count(*) from SingleMovie";
		return ((Number)getSession().createQuery(hql).uniqueResult()).intValue();
	}


	/**
	 *  推荐的电影
	 */
	@Override
	public List<Movie> getWonderfulMovies(String type) {


		//路过有给推荐评分高的电影
		String hql = " FROM Movie m  where m.type "+type+" AND m.score != 'N/A' AND m.score != ''  ORDER BY m.score DESC";
		@SuppressWarnings("unchecked")
		List<Movie> list = getSession().createQuery(hql).setMaxResults(5)
			.setFirstResult((new Random().nextInt(5))*5).list();
		//没有的话推荐其他类型评分高的电影
		if (list == null || list.size() == 0) {
			String hql2 = "FROM Movie m WHERE m.score != 'N/A' AND m.score != '' ORDER BY m.score DESC";
			@SuppressWarnings("unchecked")
			List<Movie> list2 = getSession().createQuery(hql2).setMaxResults(6)
				.setFirstResult((new Random().nextInt(5))*5).list();
			return list2;
		}

		return list;
	}


	@Override
	public Map<Integer, String> getRecentlyMovie() {

		Map<Integer, String> maps = new HashMap<Integer, String>();

		String hql = "select m.movieName , m.id from Movie m ORDER BY m.date DESC";
		List<Object[]> links = getSession().createQuery(hql).setMaxResults(10).list();
		for(Object[] link : links){  
			int id = (Integer) link[1];  
			String name = (String) link[0];  
			maps.put(id, name);
		}  
		return maps;
	}
}
