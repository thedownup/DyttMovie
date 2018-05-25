package com.movie.daoimpl;



import java.math.BigInteger;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.movie.dao.MovieDao;
import com.movie.pojo.Movie;
import com.movie.pojo.NObject;
import com.movie.pojo.SingleMovie;

@Repository
public class MovieDaoImpl extends BaseDaoImpl<Movie> implements MovieDao{

	@Override
	public Movie getMovies(int id) {
		Criteria criteria = getSession().createCriteria(Movie.class);
		criteria.add(Restrictions.eq("id", id));
		return (Movie) criteria.uniqueResult();
	}

	@Override
	public NObject getMovies(int page,int num,String q) {
		

		NObject nObject = new NObject();

		String hql = "select m from Movie m  where m.movieName like :q or m.movieInfo.director like :q or "
				+ "m.movieInfo.starring like :q or m.movieInfo.writers like :q or m.movieInfo.alias like :q or m.movieInfo.language like :q";
		String numHql = "select count(*) from Movie m  where m.movieName like :q or m.movieInfo.director like :q or "
				+ "m.movieInfo.starring like :q or m.movieInfo.writers like :q or m.movieInfo.alias like :q or m.movieInfo.language like :q";

		nObject.setObjects(getSession().createQuery(hql).setString("q", "%"+q+"%")
				.setFirstResult((page-1)*num).setMaxResults(num).list());

		nObject.setNum(((Number)getSession().createQuery(numHql).setString("q", "%"+q+"%")
				.iterate().next()).intValue());

		return nObject;
	}

	@Override
	public NObject getLikeMovies(int uid,int page, int num) {

		NObject nObject = new NObject();
		/*String hql = "from Movie m  inner join fetch m.movieInfo.movieFlag.user as u where  u.id = :uid"
				+ " and m.movieInfo.movieFlag.movieInfo.likeSee = 1";

		String numHql = "select count(*) from Movie m  inner join fetch m.movieInfo.movieFlag.user as u where  u.id = :uid"
				+ " and m.movieInfo.movieFlag.movieInfo.likeSee = 1";*/

		String sql = "SELECT {movie.*} FROM movie  WHERE movie.id in (SELECT mid FROM movieinfo m WHERE m.id in (SELECT mid FROM movie_flag f WHERE f.uid = :uid and likeSee = 1))";

		String numSql = "SELECT COUNT(*) FROM movie  WHERE movie.id in (SELECT mid FROM movieinfo m WHERE m.id in (SELECT mid FROM movie_flag f WHERE f.uid = :uid and likeSee = 1))";

		nObject.setObjects(getSession().createSQLQuery(sql).addEntity("movie",SingleMovie.class).setParameter("uid", uid)
				.setMaxResults(num).setFirstResult((page-1)*num).list());

		Query query = getSession().createSQLQuery(numSql).setParameter("uid", uid);

		List<BigInteger> list = query.list();
		int count = list.get(0).intValue();
		nObject.setNum(count);

		return nObject;
	}


}
