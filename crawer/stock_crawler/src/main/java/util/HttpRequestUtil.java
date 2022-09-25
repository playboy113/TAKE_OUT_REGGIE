package util;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HttpRequestUtil {
    private HttpClient httpClient;
    private List<Header> headerList  = new ArrayList<>();
    public HttpEntity getEntityByHttpGetMethod(String url){
        initDefaultHeaders(); //头信息
        httpClient = HttpClients.custom()
                .setRetryHandler(new DefaultHttpRequestRetryHandler()) //默认重试次数
                .setDefaultHeaders(headerList)  //添加头信息
                .build();
        //请求中的cookie策略,如果不添加容易出现Invalid cookie header警告
        RequestConfig defaultConfig = RequestConfig.custom()
                .setCookieSpec(CookieSpecs.STANDARD).build();
        HttpGet httpget = new HttpGet(url); //使用的请求方法
        httpget.setConfig(defaultConfig);
        HttpResponse response = null;
        try {
            response = httpClient.execute(httpget);
        } catch (IOException e) {
            e.printStackTrace();
        }
        HttpEntity httpEntity = response.getEntity();
        return httpEntity;

    }
    public String getContentByHttpGetMethod(String url,String code){
        try {
            return EntityUtils.toString(getEntityByHttpGetMethod(url),code);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    private List<Header> initDefaultHeaders() {
        headerList.add(new BasicHeader(HttpHeaders.ACCEPT,
                "text/html,application/xhtml+xml,application/xml;q=0.9," +
                        "image/webp,image/apng,*/*;q=0.8"));
        headerList.add(new BasicHeader(HttpHeaders.USER_AGENT,
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
                        "AppleWebKit/537.36 (KHTML, like Gecko)"
                        + " Chrome/60.0.3112.113 Safari/537.36"));
        headerList.add(new BasicHeader(HttpHeaders.ACCEPT_ENCODING,
                "gzip, deflate"));
        headerList.add(new BasicHeader(HttpHeaders.CACHE_CONTROL,
                "max-age=0"));
        headerList.add(new BasicHeader(HttpHeaders.CONNECTION,
                "keep-alive"));
        headerList.add(new BasicHeader(HttpHeaders.ACCEPT_LANGUAGE,
                "zh-CN,zh;q=0.8,en;q=0.6,zh-TW;q=0.4,ja;q=0.2," +
                        "de;q=0.2"));
        return headerList;
    }
}
