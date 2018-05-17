package com.movie.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movie.dao.MovieInfoDao;
import com.movie.pojo.EasyUIDataGridResult;
import com.movie.pojo.MovieInfo;
import com.movie.service.MovieInfoService;

@Service
public class MovieInfoServiceImpl implements MovieInfoService{

	@Autowired
	private MovieInfoDao movieInfoDao;
	
	@Override
	public EasyUIDataGridResult getMovieInfos(int page, int num) {
		return movieInfoDao.getMovieInfos(page, num);
	}

	@Override
	public void deleteById(int... ids) {
		movieInfoDao.deleteById(ids);
	}

	@Override
	public void updateMovieInfo(MovieInfo movieInfo) {
		movieInfoDao.updateMovieInfo(movieInfo);
	}

	@Override
	public void saveMovieInfo(MovieInfo movieInfo) {
		movieInfoDao.save(movieInfo);
	}

	@Override
	public EasyUIDataGridResult getMovieInfoByMovieId(int mid) {
		EasyUIDataGridResult easyUIDataGridResult = movieInfoDao.getMovieInfoByMovieId(mid);
		return easyUIDataGridResult;
	}

	@Override
	public MovieInfo getMovieInfoById(int id) {
		return movieInfoDao.getMovieInfoById(id);
	}

}
