package javdb;

import db.MySqlControl;
import model.magnet_model;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

public class crawer_javdb {
    public ArrayList<magnet_model> javdb(String url) throws IOException, URISyntaxException {

        ArrayList<magnet_model> model_list = new ArrayList<>();


        //设置请求头
        Builder builder = new Builder();
        builder.host = "https://javdb.com/";
        Map<String, String> header = new HashMap<String, String>();
        header.put("Host", builder.host);
        header.put("User-Agent",
                builder.userAgentList.get(new Random().nextInt(builder.userAgentSize)));
        header.put("Accept", builder.accept);
        header.put("Referer", builder.refererList.get(new Random().nextInt(builder.refererSize)));
        header.put("Accept-Language", builder.acceptLanguage);
        header.put("Accept-Encoding", builder.acceptEncoding);

        Connection conn = Jsoup.connect(url);
        Connection conHeader = conn.headers(header);
        Document doc = conHeader.timeout(Integer.MAX_VALUE).get();
        Elements elements = doc.getElementsByClass("item");

        for (Element element : elements) {
            String inner_url = element.select("a").attr("href");
            Document inner_doc = Jsoup.connect("https://javdb.com/" + inner_url).timeout(Integer.MAX_VALUE).get();
            magnet_model model = new magnet_model();


            //获取番号
            model.setNum(inner_doc.getElementsByAttributeValue("class", "panel-block first-block").select("span").text());

            //获取标题
            String str = inner_doc.getElementsByTag("h2").text();
            if (str.length()==0){
                model.setTitle(inner_doc.getElementsByAttributeValue("class", "panel-block first-block").select("span").text());
            }else{
                model.setTitle(str.substring(str.indexOf(" "), str.length()-1));
            }

            //获取演员与类别
            Elements dess = inner_doc.getElementsByClass("panel-block");
            for (Element des : dess) {
                if (des.select("strong").text().contains("演員")) {
                    model.setActress(des.select("a").text());
                } else if (des.select("strong").text().contains("類別")) {
                    model.setTypes(des.select("a").text());
                }else if(des.select("strong").text().contains("日期")){
                    model.setDate(des.select("span").text());
                }
            }
            //获取画质与字幕、磁力链接
            Elements inner_elements = inner_doc.getElementsByAttributeValue("class", "magnet-name column is-four-fifths");
            for (Element inner_ele : inner_elements) {
                String inner_str = inner_ele.select("div").text();
                if (inner_str.contains("高清") && inner_str.contains("字幕")) {
                    String magenet = inner_ele.select("a").attr("href");
                    magenet = magenet.replace(".torrent", "");
                    model.setMagenet(magenet);
                    model.setSubline("中文字幕");
                    model.setHD("高清");
                    break;
                } else if (inner_str.contains("高清") && !inner_str.contains("字幕")) {
                    String magenet = inner_ele.select("a").first().attr("href");
                    magenet = magenet.replace(".torrent", "");
                    model.setMagenet(magenet);
                    model.setSubline("无");
                    model.setHD("高清");
                    break;
                } else if (!inner_str.contains("高清") && inner_str.contains("字幕")) {
                    String magenet = inner_ele.select("a").first().attr("href");
                    magenet = magenet.replace(".torrent", "");
                    model.setMagenet(magenet);
                    model.setSubline("中文字幕");
                    model.setHD("无");
                    break;
                } else {
                    String magenet = inner_ele.select("a").first().attr("href");
                    magenet = magenet.replace(".torrent", "");
                    model.setMagenet(magenet);
                    model.setSubline("无");
                    model.setHD("无");
                    break;
                }

            }
            model_list.add(model);

        }
        return model_list;
    }







    class Builder{
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
