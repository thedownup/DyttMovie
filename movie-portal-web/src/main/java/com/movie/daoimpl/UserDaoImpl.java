package com.movie.daoimpl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.movie.dao.UserDao;
import com.movie.pojo.User;

@Repository
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao{

	@Override
	public User login(User user) {
		String hql = "from User u where u.userName = :userName and u.passWord = :passWord";
		return (User) getSession().createQuery(hql).setString("userName", user.getUserName())
				.setString("passWord", user.getPassWord()).uniqueResult();
	}
	
	public User getByName(String username){
		
		Criteria criteria = getSession().createCriteria(User.class);
		User user = (User) criteria.add(Restrictions.eq("userName", username)).uniqueResult();
		
		return user;
		
	}

	@Override
	public String getUserTouXiangById(String id) {

		String sql = "select touXiangImg from movie_user where id = :id";
		
		String result = (String) getSession().createSQLQuery(sql).setParameter("id", id).uniqueResult();

		return result;
	}

	@Override
	public User getUserById(int uid) {
		
		String hql = "from User u where u.id = :uid";
		
		return (User) getSession().createQuery(hql).setParameter("uid", uid).uniqueResult();
	}

	@Override
	public boolean checkUserName(String userName) {
		
		String hql = "select count(1) from User u where u.userName = :userName";
		int executeUpdate = ((Number)getSession().createQuery(hql).setParameter("userName", userName).iterate().next()).intValue();
		if (executeUpdate >= 1) {
			return true;
		}
		return false;
	}
	
}
