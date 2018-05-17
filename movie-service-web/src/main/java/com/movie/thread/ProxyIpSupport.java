package com.movie.thread;

import java.util.concurrent.Callable;

import com.movie.ipfilter.IpFilter;
import com.movie.pojo.IPMessage;

public class ProxyIpSupport implements Callable<Boolean>{

	private IPMessage ipMessage;
	private IpFilter ipFilter;

	public ProxyIpSupport(IPMessage ipMessage,IpFilter ipFilter){
		this.ipMessage = ipMessage;
		this.ipFilter = ipFilter;
	}

	@Override
	public Boolean call() throws Exception {
		//进行二次筛选
		boolean flage = ipFilter.isTable(ipMessage);
		return flage;
	}

}
