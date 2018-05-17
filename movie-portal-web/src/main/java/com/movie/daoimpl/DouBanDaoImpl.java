package com.movie.daoimpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.movie.dao.DouBanDao;
import com.movie.pojo.DouBan;

@Repository
public class DouBanDaoImpl extends BaseDaoImpl<DouBan> implements DouBanDao{
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DouBan> getDouBan(int page) {
		
		int num = 25;
		
		String hql = "from DouBan d group by d.rank+0";
		return getSession().createQuery(hql).setMaxResults(num).setFirstResult((page-1)*num).list();
	}

}
