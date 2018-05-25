package com.movie.daoimpl;

import java.net.URL;

import org.hibernate.Transaction;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Repository;

import com.movie.dao.DouBanDao;
import com.movie.pojo.DouBan;

@Repository
public class DouBanDaoImpl extends BaseDaoImpl<DouBan> implements DouBanDao {

	@Override
	public void updatxDouBan() {

		Transaction beginTransaction = getSession().beginTransaction();
		//先清空数据库
		getSession().createSQLQuery("TRUNCATE TABLE douban").executeUpdate();
		try {
			for (int i = 1; i <= 10; i++) {
				String url = "http://www.id97.com/movie/top250_douban?page="+i;
				Document document;
				document = Jsoup.parse(new URL(url).openStream(),"UTF-8",url);
				Elements children = document.getElementsByClass("container-fluid").get(0).children();
				for (Element row : children) {
					String rank = row.getElementsByClass("col-xs-2").get(0).getElementsByTag("a").text();
					String name = row.getElementsByClass("col-xs-8").get(0).getElementsByClass("col-xs-9").get(0)
							.getElementsByTag("h4").get(0).getElementsByTag("a").text();
					String imgUrl = row.getElementsByClass("col-xs-8").get(0).getElementsByClass("col-xs-2").get(0)
							.getElementsByTag("img").attr("src");
					String introduce = row.getElementsByClass("col-xs-8").get(0).getElementsByClass("col-xs-9").get(0)
							.getElementsByTag("p").text();
					String score;
					if (row.getElementsByClass("col-xs-2").text().equals("") || row.getElementsByClass("col-xs-2").text().split("\\s+").length < 2) {
						score = "";
					}else {
						score = row.getElementsByClass("col-xs-2").text().split("\\s+")[1];
					}

					DouBan douBan = new DouBan();
					douBan.setMovieIntroduce(introduce);
					douBan.setScore(score);
					douBan.setRank(rank);
					douBan.setMovieUrl(imgUrl);
					douBan.setMovieName(name);
					getSession().save(douBan);
				}
			}
		} catch (Exception e) {
			updatxDouBan();
		} 

		beginTransaction.commit();
	}

}
