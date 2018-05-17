package com.movie.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movie.pojo.Rolling;
import com.movie.reids.RedisHelper;
import com.movie.service.RollingService;

/**
 * @author zjt
 * @Description: 首页滚动屏相关方法
 */
@Service
public class RollingServiceImpl implements RollingService{

	@Autowired
	private RedisHelper redisHelper;
	
	@Override
	public List<Rolling> getRolling(int page, int num) {
		
		
		return null;
	}

	@Override
	public void updateRolling() {
		
	}

	@Override
	public void saveRolling() {
		
	}

}
