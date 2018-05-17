package com.movie.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movie.dao.MovieDao;
import com.movie.pojo.EasyUIDataGridResult;
import com.movie.pojo.Movie;
import com.movie.service.MovieService;

@Service
public class MovieServiceImpl implements MovieService{

	@Autowired
	private MovieDao movieDao;
	
	@Override
	public EasyUIDataGridResult getMovies(int page, int num) {
		return movieDao.getMovies(page, num);
	}

	@Override
	public void deleteById(int... ids) {
		movieDao.deleteById(ids);
	}

	@Override
	public void updateMovie(Movie movie) {
		movieDao.updateMovie(movie);
	}

	@Override
	public void saveMovie(Movie movie) {
		movieDao.save(movie);
	}

	@Override
	public Movie getMovieById(int id) {
		return movieDao.getMovieById(id);
	}

	@Override
	public void merge() {
		movieDao.merge();
	}

	@Override
	public void clearSameLinks() {
		movieDao.clearSameLinks();
	}
	

}
