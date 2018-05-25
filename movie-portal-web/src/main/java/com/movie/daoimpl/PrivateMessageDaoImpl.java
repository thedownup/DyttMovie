package com.movie.daoimpl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.movie.dao.PrivateMessageDao;
import com.movie.pojo.PrivateMessage;
import com.movie.pojo.User;

@Repository
public class PrivateMessageDaoImpl implements PrivateMessageDao{

	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession(){
		return sessionFactory.getCurrentSession();
	}

	@Override
	public List<PrivateMessage> getPrivateMessage(int uid, int fid) {
		
		String sql = "SELECT * FROM private_message WHERE (uid =:uid and fid = :fid ) or (uid =:fid and fid =:uid)";
		List<Object[]> list = getSession().createSQLQuery(sql).setParameter("uid", uid)
			.setParameter("fid", fid).list();
		
		List<PrivateMessage> pList = new ArrayList<PrivateMessage>();
		
		for (Object[] objects : list) {
			Timestamp timestamp = (Timestamp) objects[7];
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date = simpleDateFormat.format(timestamp);
			PrivateMessage privateMessage = new PrivateMessage((int)objects[0],(int)objects[1],(int)objects[2],
					(int)objects[3],(int)objects[4],(byte)objects[5],(String)objects[6],date);
			pList.add(privateMessage);
		}
		
		return pList;
	}

	@Override
	public void sendMessage(int uid, int fid,String message) {
		
		String sql = "INSERT INTO private_message (`uid`,`fid`,`sid`,`rid`,`message_type`,`message_content`,`send_time`) "+
					"VALUE (?,?,?,?,?,?,?)";
		
		SimpleDateFormat simpleFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = simpleFormatter.format(new Date());
		
		getSession().createSQLQuery(sql).setParameter(0, uid).setParameter(1, fid).setParameter(2, uid)
			.setParameter(3, fid).setParameter(4, 1).setParameter(5, message).setParameter(6, date).executeUpdate();
	}

	@Override
	public List<User> getRecentlyUser(int uid) {
		
		String sql = "SELECT m.username,m.touxiangimg,m.id FROM movie_user m WHERE m.id IN(SELECT uid FROM private_message WHERE fid = :uid)";
		List<Object[]> list = getSession().createSQLQuery(sql).setParameter("uid", uid).list();
		
		List<User> lUsers = new ArrayList<User>();
		
		for (Object[] objects : list) {
			User user = new User();
			user.setUserName((String)objects[0]);
			user.setTouXiangImg((String)objects[1]);
			user.setId((int)objects[2]);
			lUsers.add(user);
		}
		
		return lUsers;
	}

	@Override
	public List<PrivateMessage> getSystemMessage() {
		
		String sql = "SELECT message_content,send_time FROM private_message WHERE message_type = 2";
		List<Object[]> list = getSession().createSQLQuery(sql).list();

		List<PrivateMessage> lMessages = new ArrayList<PrivateMessage>();
		
		for (Object[] objects : list) {
			PrivateMessage privateMessage = new PrivateMessage();
			privateMessage.setMessage((String)objects[0]);
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date = simpleDateFormat.format((Date)objects[1]);
			privateMessage.setSendDate(date);
			lMessages.add(privateMessage);
		}
		
		return lMessages;
	}

}
