package com.movie.daoimpl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Repository;

import com.movie.dao.PrivateMessageDao;
import com.movie.pojo.EasyUIDataGridResult;
import com.movie.pojo.PrivateMessage;

@Repository
public class PrivateMessageDaoImpl extends BaseDaoImpl<PrivateMessage> implements PrivateMessageDao{

	@Override
	public void sendSystemPrivateMessage(String message) {
		
		PrivateMessage privateMessage = new PrivateMessage();
		privateMessage.setFid(-1);
		privateMessage.setRid(-1);
		privateMessage.setMessageType((byte)2);
		privateMessage.setUid(-1);
		privateMessage.setSid(-1);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		privateMessage.setSendDate(simpleDateFormat.format(new Date()));
		privateMessage.setMessage(message);
		
		getSession().save(privateMessage);
		
	}

	@Override
	public EasyUIDataGridResult getPrivateMessage(int page,int num) {

		EasyUIDataGridResult easyUIDataGridResult = new EasyUIDataGridResult();
		
		String count = "select count(1) from PrivateMessage p where p.messageType = 2";
		
		String hql = "from PrivateMessage p where p.messageType = 2";
		
/*		List<PrivateMessage> list = getSession().createQuery(hql).setMaxResults(num).setFirstResult((page-1)*num).list();
		if (list.size() > 0) {
			for (PrivateMessage privateMessage : list) {
				String sendDate = privateMessage.getSendDate();
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				privateMessage.setSendDate(sendDate.format(sendDate));
			}
		}
*/
		easyUIDataGridResult.setRows(getSession().createQuery(hql).setMaxResults(num).setFirstResult((page-1)*num).list());
		easyUIDataGridResult.setTotal(((Number)getSession().createQuery(count).iterate().next()).intValue());
	
		return easyUIDataGridResult;
	}

}
