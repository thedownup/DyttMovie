package com.movie.daoimpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.movie.dao.MovieDao;
import com.movie.pojo.EasyUIDataGridResult;
import com.movie.pojo.Link;
import com.movie.pojo.Movie;
import com.movie.pojo.MovieInfo;
import com.movie.pojo.PageFlag;
import com.movie.until.JsonUtils;

/**
 * 
 * @author zjt
 * @Description: 有关电源表的一些操作
 */
@Repository
public class MovieDaoImpl extends BaseDaoImpl<Movie> implements MovieDao{

	private static final Logger logger = Logger.getLogger(MovieDaoImpl.class);

	@Override
	public EasyUIDataGridResult getMovies(int page, int num) {

		EasyUIDataGridResult easyUIDataGridResult = new EasyUIDataGridResult();

		String hql = "from Movie m";
		String numHql = "select count(1) from Movie";

		@SuppressWarnings("unchecked")
		List<Movie> movies = getSession().createQuery(hql).setMaxResults(num).setFirstResult((page-1)*num).list();

		int mNum = ((Number)getSession().createQuery(numHql).iterate().next()).intValue();

		easyUIDataGridResult.setTotal(mNum);
		easyUIDataGridResult.setRows(movies);

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

		String hql = "delete Movie m where m.id in("+param+")";
		getSession().createQuery(hql).executeUpdate();
	}

	@Override
	public void updateMovie(Movie movie) {
		update(movie);
	}

	@Override
	public Movie getMovieById(int id) {

		String hql = "from Movie m where m.id = :id";

		return (Movie) getSession().createQuery(hql).setParameter("id", id).uniqueResult();
	}

	/**
	 * 简易去重
	 */
	@Override
	public void merge() {
		//1.去掉无效的电影
		String sql = "DELETE  FROM movie WHERE moviename IS NULL OR moviename = ''";
		getSession().createSQLQuery(sql).executeUpdate();

		//去掉名字相同的并合并
		String sql2 = "SELECT moviename FROM movie GROUP BY moviename HAVING moviename IN  "
				+ "(SELECT moviename FROM movie GROUP BY moviename HAVING  COUNT(moviename) > 1)";
		List<String> list = getSession().createSQLQuery(sql2).list();
		//找出了不同类型的相同电影名字
		for (String moviename : list) {
			//找出相同名字的电影			
			String hql = "from Movie m where m.movieName =:movieName";
			List<Movie> movies = getSession().createQuery(hql).setParameter("movieName", moviename).list();
			//保存最后的不同的链接值
			logger.info(moviename+"有"+movies.size()+"电影相同");
			Map<String, String> hashmap = new ConcurrentHashMap<String, String>();
			for (int i = 0; i < movies.size(); i++) {
				if (movies.get(i).getMovieInfos() != null && movies.get(i).getMovieInfos().size() >= 1) {
					String jsonLink = movies.get(i).getMovieInfos().iterator().next().getLink();
					List<Link> links = JsonUtils.jsonToList(jsonLink, Link.class);
					//取出link
					for (Link link : links) {
						hashmap.put(link.getLinkName(), link.getLinkUrl());
					}
				}

				if (i != 0) {
					//删除
					getSession().delete(movies.get(i));
				}
			}

			List<Link> links = new ArrayList<Link>();
			Set<Entry<String,String>> entrySet = hashmap.entrySet();
			for (Entry<String, String> entry : entrySet) {
				Link link = new Link();
				link.setLinkName(entry.getKey());
				link.setLinkUrl(entry.getValue());
				links.add(link);
			}
			//回显设置
			Movie movie = movies.get(0);
			movie.getMovieInfos().iterator().next().setLink(JsonUtils.objectToJson(links));
			getSession().update(movie);
			logger.info("合并了一个" + movie.toString() );
		}
	}

	/**
	 * 去除相同链接
	 */
	@Override
	public void clearSameLinks(){

		//查询从多少开始
		String hqlc = "from PageFlag p where p.movieType = 'CLEAR_TYPE' ";
		PageFlag pageFlag = (PageFlag) getSession().createQuery(hqlc).uniqueResult();
		
		String hql = "from MovieInfo where id > :id";
		List<MovieInfo> list = getSession().createQuery(hql).setParameter("id", pageFlag.getPage()).list();
		for (MovieInfo movieInfo : list) {
			HashMap<String, String>  hashMap = new HashMap<String, String>();
			String jsonlinks = movieInfo.getLink();
			List<Link> jsonToList = JsonUtils.jsonToList(jsonlinks, Link.class);
			for (Link link : jsonToList) {
				hashMap.put(link.getLinkName(), link.getLinkUrl());
			}

			List<Link> links = new ArrayList<Link>();
			Set<Entry<String,String>> entrySet = hashMap.entrySet();
			for (Entry<String, String> entry : entrySet) {
				Link link = new Link();
				link.setLinkName(entry.getKey());
				link.setLinkUrl(entry.getValue());
				links.add(link);
			}
			
			movieInfo.setLink(JsonUtils.objectToJson(links));
			getSession().update(movieInfo);
			logger.info("完成了一个"+movieInfo.toString());
		}
		
		//回显
		int maxId = (int) getSession().createQuery("select max(m.id) from MovieInfo m ").uniqueResult();
		pageFlag.setPage(maxId);
		getSession().update(pageFlag);
		
	}

}
