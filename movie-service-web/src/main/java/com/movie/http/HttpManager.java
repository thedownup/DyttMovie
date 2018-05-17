package com.movie.http;

import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.net.ssl.SSLContext;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;


@Component
public class HttpManager {

	private static final Logger logger = Logger.getLogger(HttpManager.class);

	//全局连接对象
	private static PoolingHttpClientConnectionManager  connectionManager;
	LayeredConnectionSocketFactory sslsf = null;

	private HttpHost host;
	private int contextTimeOut;

	@PostConstruct
	public void init(){
		try {
			sslsf = new SSLConnectionSocketFactory(SSLContext.getDefault());
		} catch (NoSuchAlgorithmException e) {
			logger.error(e.getMessage()+"------"+"SSLConnectionSocketFactory异常");
		}

		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory> create()
				.register("https", sslsf)
				.register("http", new PlainConnectionSocketFactory())
				.build();
		connectionManager =new PoolingHttpClientConnectionManager(socketFactoryRegistry);
		connectionManager.setMaxTotal(500);
		connectionManager.setDefaultMaxPerRoute(connectionManager.getMaxTotal());
	}

	/**
	 * 设置全局的代理ip
	 * @param host
	 */
	public void setHttpHost(HttpHost host,int contextTimeOut){
		this.contextTimeOut = contextTimeOut;
		this.host = host;
	}

	/**
	 * 创建全局的httpclient
	 * @param timeOut
	 * @return
	 */
	public CloseableHttpClient createContextClient(){
		if (host == null) {
			return createHttpClient();
		}
		return createHttpClient(contextTimeOut, host);
	}

	/**
	 * 创建全局的response
	 * @param request
	 * @return
	 */
	public CloseableHttpResponse getContextResponse(HttpRequestBase request){
		CloseableHttpResponse response = getResponse(request, host);
		return response;
	}

	public CloseableHttpClient createHttpClient(int timeOut,HttpHost proxy){
		// 创建Http请求配置参数
		RequestConfig.Builder builder = RequestConfig.custom()
				// 获取连接超时时间
				.setConnectionRequestTimeout(timeOut)
				// 请求超时时间
				.setConnectTimeout(timeOut)
				// 响应超时时间
				.setSocketTimeout(timeOut);
		if (proxy != null) {
			builder.setProxy(proxy);
		}

		RequestConfig requestConfig = builder.build();

		// 创建httpClient
		HttpClientBuilder httpClientBuilder = HttpClients.custom();

		// 把请求重试设置到连接客户端
		httpClientBuilder.setDefaultRequestConfig(requestConfig)
		// 配置连接池管理对象
		.setConnectionManager(connectionManager).setSSLSocketFactory(sslsf);

		return httpClientBuilder.build();
	}

	/**
	 * 创建新的httpclient
	 * @return
	 */
	public CloseableHttpClient createHttpClient(){
		CloseableHttpClient httpClient = HttpClients.createDefault();
		return httpClient;
	}

	public CloseableHttpResponse getResponse(String url){
		return getResponse(url,null);
	}

	public CloseableHttpResponse getResponse(String url,HttpHost proxy){
		HttpGet request = new HttpGet(url);
		return getResponse(request,proxy);
	}

	/**
	 * 获得响应
	 * @param request
	 * @param proxy
	 * @return
	 */
	public CloseableHttpResponse getResponse(HttpRequestBase request,HttpHost proxy){
		//随机设置请求头
		request.setHeader("User-Agent", Constant.userAgentArray[new Random().nextInt(Constant.userAgentArray.length)]);
		/*HttpClientContext httpClientContext = HttpClientContext.create();*/
		CloseableHttpResponse response = null;

		try {

			if (proxy == null) {
				response = createHttpClient().execute(request);
			} else {

				response = createHttpClient(300000, proxy).execute(request);
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("getResponse"+"失败"+e.getMessage());
		}
		return response;
	}
}
