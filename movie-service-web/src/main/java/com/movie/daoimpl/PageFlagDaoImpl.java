package com.movie.daoimpl;

import java.util.Date;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.movie.dao.PageFlagDao;
import com.movie.pojo.PageFlag;
import com.movie.wangye.BaseDyttOrigin;

@Repository
public class PageFlagDaoImpl extends BaseDaoImpl<PageFlag> implements PageFlagDao {

	@Autowired
	private BaseDyttOrigin baseDyttOrigin;

	/**
	 * 获取上次栏目的全部页数
	 */
	@Override
	public int getLastPage(String movieType,String url) {

		String hql = "from PageFlag p where p.movieType= :movieType";

		Query query = getSession().createQuery(hql).setParameter("movieType", movieType);

		PageFlag pageFlag = (PageFlag) query.uniqueResult();
		//如果没有
		if (pageFlag == null) {
			PageFlag pageFlag2 = new PageFlag();
			pageFlag2.setTime(new Date());
			pageFlag2.setPage(baseDyttOrigin.getPage(url));
			pageFlag2.setMovieType(movieType);
			save(pageFlag2);
			return pageFlag2.getPage();
		}

		return pageFlag.getPage();
	}

	@Override
	public void setPage(String movieType, int page) {
		String hql = "update PageFlag p set p.page = :page where p.movieType = :movieType";
		getSession().createQuery(hql).setParameter("page", page)
			.setParameter("movieType", movieType).executeUpdate();
	}

}
