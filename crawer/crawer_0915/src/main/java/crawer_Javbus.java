import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.seimicrawler.xpath.JXDocument;

import javax.print.Doc;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

public class crawer_Javbus {
    public static void main(String[] args) throws IOException, URISyntaxException {
        String proxyHost = "127.0.0.1";
        String proxyPort = "7890";
        System.setProperty("http.proxyHost", proxyHost);
        System.setProperty("http.proxyPort", proxyPort);

// 对https也开启代理
        System.setProperty("https.proxyHost", proxyHost);
        System.setProperty("https.proxyPort", proxyPort);

        String url = "https://www.javbus.com/star/2jv";
        //设置请求头
        Builder builder = new Builder();
        //请求网页不同添加host,也可以不设置
        builder.host = "www.javbus.com";
        //builder中的信息添加到Map集合中
        Map<String, String> header = new HashMap<String, String>();
        header.put("Host", builder.host);
        header.put("User-Agent",
                builder.userAgentList.get(new Random().nextInt(builder.userAgentSize)) );
        header.put("Accept", builder.accept);
        header.put("Referer", builder.refererList.get(new Random().nextInt(builder.refererSize)));
        header.put("Accept-Language", builder.acceptLanguage);
        header.put("Accept-Encoding", builder.acceptEncoding);
        //设置头



        Connection connect = Jsoup.connect(url);
        Connection conHeader = connect.headers(header);
        Document doc = conHeader.get();
        ArrayList<String> htmlList = new ArrayList<>();//总体url
        ArrayList<String> htmlList_sub = new ArrayList<>();//带字幕的url
        ArrayList<String> tagList = new ArrayList<>();
        Elements elements = doc.getElementsByClass("movie-box");
        for (Element ele:elements){
            String inner_html = ele.select("a").attr("href");
            String inner_tag = ele.getElementsByClass("item-tag").text();
            htmlList.add(inner_html);
            tagList.add(inner_tag);
      }
        //判断有字幕的url
        for (int i =0;i<htmlList.size();i++){
            if (tagList.get(i).contains("字幕")){
                htmlList_sub.add(htmlList.get(i));

//                Document inner_doc = Jsoup.connect(htmlList.get(i)).timeout(50000).get();
//                Elements inner_html = inner_doc.getElementsByClass("btn btn-mini-new btn-primary disabled");
//                System.out.println(inner_html.html());
                Document inner_doc = Jsoup.connect(htmlList.get(i)).timeout(500000).get();
                String inner_html = inner_doc.html();
                HtmlCleaner cleaner = new HtmlCleaner();
                TagNode node  = cleaner.clean(inner_html);



//                for (Element inner_ele:inner_eles){
//                    String href =inner_ele.text();
//                    System.out.println(href);
//                }
            }
        }



    }
    static class Builder{
        //设置userAgent库;读者根据需求添加更多userAgent
        String[] userAgentStrs = {"Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_8; en-us) AppleWebKit/534.50 (KHTML, like Gecko) Version/5.1 Safari/534.50",
                "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-us) AppleWebKit/534.50 (KHTML, like Gecko) Version/5.1 Safari/534.50",
                "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:38.0) Gecko/20100101 Firefox/38.0",
                "Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; .NET4.0C; .NET4.0E; .NET CLR 2.0.50727; .NET CLR 3.0.30729; .NET CLR 3.5.30729; InfoPath.3; rv:11.0) like Gecko",
                "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)",
                "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0; Trident/4.0)",
                "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.0)"};
        List<String> userAgentList = Arrays.asList(userAgentStrs);
        int userAgentSize = userAgentList.size();
        //设置referer库;读者根据需求添加更多referer
        String[] refererStrs = {"https://www.baidu.com/",
                "https://www.sogou.com/",
                "http://www.bing.com",
                "https://www.so.com/"};
        List<String> refererList = Arrays.asList(refererStrs);
        int refererSize = refererList.size();
        //设置Accept、Accept-Language以及Accept-Encoding
        String accept = "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8";
        String acceptLanguage = "zh-cn,zh;q=0.5";
        String acceptEncoding = "gzip, deflate";
        String host;
    }
}
