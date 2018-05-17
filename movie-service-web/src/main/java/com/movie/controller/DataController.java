package com.movie.controller;


import org.apache.http.HttpHost;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.movie.dao.PageFlagDao;
import com.movie.http.HttpManager;
import com.movie.ipfilter.IpFilter;
import com.movie.pojo.IPMessage;
import com.movie.pojo.MResult;
import com.movie.reids.RedisHelper;
import com.movie.service.DbService;
import com.movie.service.DouBanService;
import com.movie.service.MovieService;
import com.movie.service.ProxyIpPoolService;
import com.movie.until.JsonUtils;
import com.movie.wangye.BaseDyttOrigin;
import com.movie.wangye.JiuqiOrigin;

/**
 * @author zjt
 * @Description: 控制数据的爬取和保存
 */
@Controller
@RequestMapping("/data")
public class DataController {	
	
	private static final Logger logger = Logger.getLogger(DataController.class);
	
	@Autowired
	private DbService dbService;
	@Autowired
	private DouBanService douBanService;
	@Autowired
	private BaseDyttOrigin baseDyttOrigin;
	@Autowired
	private JiuqiOrigin jiuqiOrigin;
	@Autowired
	private RedisHelper redisHelper;
	@Autowired
	private HttpManager httpManager;
	@Autowired
	private PageFlagDao pageFlagDao;
	@Autowired
	private ProxyIpPoolService proxyIpPoolService;
	@Autowired
	private MovieService movieService;
	@Autowired
	private IpFilter ipFilter;
	
	
	private static final String NEW_MOVIE_URL = "http://www.ygdy8.net/html/gndy/dyzz/index.html";
	private static final String OUMEI_MOVIE_URL = "http://www.ygdy8.net/html/gndy/oumei/index.html";
	private static final String CHINA_MOVIE_URL = "http://www.ygdy8.net/html/gndy/china/index.html";
	private static final String JIUQI_MOVIE_URL = "JIUQI_MOVIE_TYPE";
	
	
	@RequestMapping(value="/douban",method=RequestMethod.POST)
	@ResponseBody
	public MResult updateDouban(){
		try {
			douBanService.updateDouBan();
			return MResult.build("更新豆瓣成功");
		} catch (Exception e) {
			return MResult.build(e.getMessage());
		}
	}

	
	public void saveDyttMovieToDb1(){
		/*setProxyIp();*/
		dbService.saveDyttMovieToDb(BaseDyttOrigin.OUMEIMOVIETYPE,1,191, BaseDyttOrigin.BASECHINAMOVIE);
	}
	
	@RequestMapping(value="/updatedyttnewmovie",method=RequestMethod.POST)
	@ResponseBody
	public MResult updateDyttNewMovie(){
		setProxyIp();
		try {
			//获取总页数
			int page = baseDyttOrigin.getPage(NEW_MOVIE_URL);
			int updatePage = dbService.updateMovieFromDytt(BaseDyttOrigin.NEWMOVIETYPE, page, BaseDyttOrigin.BASENEWMOVIE);
			if (updatePage == 0) return MResult.build("本次没有可以更新的");
			return MResult.build("本次更新了"+updatePage+"页");
		} catch (Exception e) {
			return MResult.build(e.getMessage());
		}
	}
	
	@RequestMapping(value="/updatedyttoumeimovie",method=RequestMethod.POST)
	@ResponseBody
	public MResult updateDyttOouMeiMovie(){
		setProxyIp();
		try {
			//获取总页数
			int page = baseDyttOrigin.getPage(OUMEI_MOVIE_URL);
			int updatePage = dbService.updateMovieFromDytt(BaseDyttOrigin.OUMEIMOVIETYPE, page, BaseDyttOrigin.BASEOUMEIMOVIE);
			if (updatePage == 0) return MResult.build("本次没有可以更新的");
			return MResult.build("本次更新了"+updatePage+"页");
		} catch (Exception e) {
			return MResult.build(e.getMessage());
		}
	}
	
	@RequestMapping(value="/updatedyttchinamovie",method=RequestMethod.POST)
	@ResponseBody
	public MResult updateDyttChinaMovie(){
		setProxyIp();
		try {
			//获取总页数
			int page = baseDyttOrigin.getPage(CHINA_MOVIE_URL);
			int updatePage = dbService.updateMovieFromDytt(BaseDyttOrigin.CHINAMOVIETYPE, page, BaseDyttOrigin.BASECHINAMOVIE);
			if (updatePage == 0) return MResult.build("本次没有可以更新的");
			return MResult.build("本次更新了"+updatePage+"页");
		} catch (Exception e) {
			return MResult.build(e.getMessage());
		}
	}
	
	@RequestMapping(value="/updatejiuqimovie",method=RequestMethod.POST)
	@ResponseBody
	public MResult updateJiuQiMovie(){
		setProxyIp();
		try {
			//新的页数
			int page = jiuqiOrigin.getPage();
			//上次的页数
			int beg = pageFlagDao.getLastPage("JIUQI_MOVIE_URL", JIUQI_MOVIE_URL);
			int updatePage = page - beg;
			if (page > pageFlagDao.getLastPage("JIUQI_MOVIE_URL", JIUQI_MOVIE_URL)) {
				dbService.update97MovieToDb(1, updatePage);
				//设置新的值
				pageFlagDao.setPage(JIUQI_MOVIE_URL, page);
			}
			if (updatePage == 0) {
				return MResult.build("本次没有可以更新的");
			}
			return MResult.build("本次更新了"+updatePage+"页");
		} catch (Exception e) {
			e.printStackTrace();
			return MResult.build(e.getMessage());
		}
	}
	
	/**
	 * 查看代理ip池的数量
	 * @return
	 */
	@RequestMapping("/updateproxyip")
	@ResponseBody
	public MResult updateProxyIp(){
		try {
			int num = proxyIpPoolService.insertProxyIp();
			return MResult.build("向redis添加了"+num+"个");
		} catch (Exception e) {
			return MResult.build(e.getMessage());
		}
	}
	
	@RequestMapping("/getproxynum")
	@ResponseBody
	public MResult getNum(){
		try {
			long num = redisHelper.getIpMessageNum();
			return MResult.build("剩余proxyip数量为"+num);
		} catch (Exception e) {
			return MResult.build(e.getMessage());
		}
	}
	
	@RequestMapping("/deleteproxyid")
	@ResponseBody
	public MResult deleteProxyIp(){
		try {
			long bengNum = redisHelper.getIpMessageNum();
			redisHelper.checkProxyIp();
			long num = redisHelper.getIpMessageNum();
			return MResult.build("去除了无效的ip数量"+(bengNum-num)+"  "+"剩余数量"+num);
		} catch (Exception e) {
			return MResult.build(e.getMessage());
		}
	}
	
	@RequestMapping("/merge")
	@ResponseBody
	public MResult merge(){
		try {
			movieService.merge();
			return MResult.build("合并完毕");
		} catch (Exception e) {
			return MResult.build("合并失败"+e.getMessage());
		}
	}
	
	/**
	 * 设置代理ip如果没有则不进行代理
	 */
	private void setProxyIp(){
		boolean flag = true;
		while (flag) {
			IPMessage ipMessage = redisHelper.getIpMessage();
			if (ipMessage == null) {
				break;
			}
			if (ipFilter.isTable(ipMessage)) {
				logger.info("找到了可以代理的ip"+ipMessage.toString());
				httpManager.setHttpHost(new HttpHost(ipMessage.getIpAdress(), ipMessage.getPort()), 200000);
				break;
			} else {
				redisHelper.removeProxyIp(JsonUtils.objectToJson(ipMessage));
			}
		}
	}
	
}
