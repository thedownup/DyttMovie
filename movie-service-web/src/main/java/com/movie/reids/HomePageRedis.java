package com.movie.reids;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.movie.pojo.Movie;

@Component
public class HomePageRedis extends BaseRedis{
	
	private static final String HOMEPAGE = "homePage";
	
	public List<Movie> getMovies(){
		
		List<Movie> movies = new ArrayList<Movie>();
		
		return movies;
		
	}
	
}
