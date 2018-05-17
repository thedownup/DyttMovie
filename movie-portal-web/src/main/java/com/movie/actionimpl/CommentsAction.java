package com.movie.actionimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.movie.pojo.Comments;
import com.movie.pojo.NObject;
import com.movie.pojo.Reply;
import com.movie.pojo.ReplyInner;
import com.movie.pojo.User;
import com.movie.service.CommentsService;
import com.movie.service.FocusUserService;
import com.movie.untils.JsonUtils;
import com.opensymphony.xwork2.Action;

/**
 * @author zjt
 * @Description: 获取评论和发送评论
 */
@Controller
public class CommentsAction extends BaseAction<Comments>{

	private static final Logger logger = Logger.getLogger(CommentsAction.class);

	@Autowired
	private CommentsService commentsService;
	@Autowired
	private FocusUserService focusUserService;

	private String jsonData;
	private int num;
	private int page;
	private int movieInfoId;



	private String messages;
	private String checkCode;

	private int fid;
	private int uid;

	public int getFid() {
		return fid;
	}

	public int getUid() {
		return uid;
	}

	public void setFid(int fid) {
		this.fid = fid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}



	/**
	 * 获取评论
	 * @return
	 */
	public String getComments(){

		if (page == 0) {
			page++;
		}
		NObject nObject = commentsService.getComments(page, num, movieInfoId);
		@SuppressWarnings("unchecked")
		List<Comments> comment = (List<Comments>) nObject.getObjects();
		List<ReplyInner> replyInners = new ArrayList<ReplyInner>();
		for (Comments comments : comment) {
			ReplyInner replyInner = new ReplyInner();
			replyInner.setDate(comments.getSendDate());
			replyInner.setMessage(comments.getMessage());
			replyInner.setName(comments.getUser().getUserName());
			replyInner.setTouxiangUrl(comments.getUser().getTouXiangImg());
			replyInners.add(replyInner);
		}
		Reply reply = new Reply();
		reply.setNum(getPageNum(nObject.getNum(), 0, num));
		reply.setReplyInners(replyInners);
		String json = JsonUtils.objectToJson(reply);
		jsonData = json;
		return Action.SUCCESS;
	}

	/**
	 * 发送评论
	 */
	public String sendComments(){
		int uid = 0;

		if (checkCode == null) {
			checkCode = "";
			jsonData = "验证为空";
			return Action.SUCCESS;	
		}

		logger.debug(checkCode + " " + uid + " "+ movieInfoId + " "+ messages);

		if (session.get("user") != null) {

			User user = (User) session.get("user");
			uid = user.getId();

		}else {
			jsonData = "清先登录";
			return Action.SUCCESS;	
		}

		Set<Entry<String,Object>> entrySet = session.entrySet();

		for (Entry<String, Object> entry : entrySet) {
			logger.debug(session.get(entry.getKey()));
		}


		if (!checkCode.equals(session.get("checkCode"))) {
			jsonData = "验证码错误";
			return Action.SUCCESS;		
		}
		commentsService.sendComments(uid, movieInfoId, messages);
		jsonData = "评论成功";

		return Action.SUCCESS;	
	}

	/**
	 * 关注他人
	 * @return
	 */
	public String insertFocusUser(){
		try {
			focusUserService.insertFocusUser(uid, fid);
			jsonData = "200";
		} catch (Exception e) {
			jsonData = "-1";
		}
		return Action.SUCCESS;
	}

	/**
	 * 取消关注
	 * @return
	 */
	public String removeFocusUser(){
		try {
			focusUserService.removeFocusUser(uid, fid);
			jsonData = "200";
		} catch (Exception e) {
			jsonData = "-1";
		}
		return Action.SUCCESS;
	}

	/**
	 * 检查是否有关注
	 * @return
	 */
	public String checkFocusUser(){
		try {
			boolean focusUser = focusUserService.checkFocusUser(uid, fid);
			jsonData = String.valueOf(focusUser);
		} catch (Exception e) {
			jsonData = "-1";
		}
		return Action.SUCCESS;
	}


	public String getJsonData() {
		return jsonData;
	}

	public int getNum() {
		return num;
	}

	public int getPage() {
		return page;
	}


	public String getMessages() {
		return messages;
	}

	public String getCheckCode() {
		return checkCode;
	}

	public int getMovieInfoId() {
		return movieInfoId;
	}

	public void setMovieInfoId(int movieInfoId) {
		this.movieInfoId = movieInfoId;
	}

	public void setMessages(String messages) {
		this.messages = messages;
	}

	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public void setPage(int page) {
		this.page = page;
	}


	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}
}
