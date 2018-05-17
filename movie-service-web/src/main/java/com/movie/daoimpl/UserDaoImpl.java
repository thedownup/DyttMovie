package com.movie.daoimpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.movie.dao.UserDao;
import com.movie.pojo.EasyUIDataGridResult;
import com.movie.pojo.User;

@Repository
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao{

	@Override
	public EasyUIDataGridResult getUsers(int page, int num) {

		String hql = "from User";
		List<User> list = getSession().createQuery(hql).setMaxResults(num).setFirstResult((page-1)*num).list();

		EasyUIDataGridResult easyUIDataGridResult = new EasyUIDataGridResult();
		easyUIDataGridResult.setTotal(((Number)getSession().createQuery("select count(*) from User").iterate().next()).intValue());
		easyUIDataGridResult.setRows(list);
		return easyUIDataGridResult;
	}

	@Override
	public void deleteById(int id) {
		String hql = "delete * from movie_user  where id = :id";
		getSession().createSQLQuery(hql).setParameter("id", id).executeUpdate();
	}

	@Override
	public boolean haveUserName(String userName) {

		String hql = "select count(1) from User u where u.userName = :userName";

		int num = ((Number)getSession().createQuery(hql).setParameter("userName", userName).iterate().next()).intValue();
		if (num == 0) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public void updateBackImg(int userId,String url) {
		String hql = "update User u set u.backImg = :url where u.id = :userId";
		getSession().createQuery(hql).setParameter("url", url).setParameter("userId", userId).executeUpdate();
	}

	@Override
	public void updateTouxians(int userId,String url) {
		String hql = "update User u set u.touXiangImg = :url where u.id = :userId";
		getSession().createQuery(hql).setParameter("url", url).setParameter("userId", userId).executeUpdate();
	}
	
	

}
