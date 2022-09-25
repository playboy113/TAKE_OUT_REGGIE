import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class crawer_main {
    public static void main(String[] args) throws URISyntaxException, IOException {
        String proxyHost = "127.0.0.1";
        String proxyPort = "7890";
        System.setProperty("http.proxyHost", proxyHost);
        System.setProperty("http.proxyPort", proxyPort);

// 对https也开启代理
        System.setProperty("https.proxyHost", proxyHost);
        System.setProperty("https.proxyPort", proxyPort);


        ArrayList<String> magnet_list = new ArrayList<>();


        for (int i = 0; i <= 9; i++) {
            Document doc = Jsoup.connect("https://www.sehuatang.net/forum-2-" + i + ".html").timeout(Integer.MAX_VALUE).get();
            Elements elements = doc.getElementsByAttributeValueStarting("id", "normalthread_");
            for (Element ele : elements) {
                String title = ele.getElementsByClass("s xst").select("a").text();

                String inner_url = ele.select("a").attr("href");
                //System.out.println(title);
                Document inner_doc = Jsoup.connect("https://www.sehuatang.net/" + inner_url).timeout(Integer.MAX_VALUE).get();
                //图片下载
                Element inner_pic = inner_doc.getElementsByTag("ignore_js_op").select("p").first().nextElementSibling();
                String inner_pic_magnet = inner_pic.select("a").attr("href");
                Connection pic_conn = Jsoup.connect(inner_pic_magnet);
                Connection.Response response = pic_conn.method(Connection.Method.GET).ignoreContentType(true).execute();
                BufferedInputStream bufferedInputStream = response.bodyStream();
                //savePic(bufferedInputStream,"E:\\java\\crawer\\crawer_0915\\src\\main\\java\\img\\"+title+".jpg");
                //System.out.println(inner_pic_magnet);

                //链接下载
                Element inner_element = inner_doc.getElementsByClass("blockcode").first();

                String magnet = inner_element.select("li").text();
                magnet_list.add(magnet);


            }

        }
        //将链接写入文本
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("E:\\java\\crawer\\crawer_0915\\src\\main\\java\\crawer_data\\" + "magnetList.txt")), "gbk"));
        for (int i = 0; i < magnet_list.size(); i++) {
            writer.write(magnet_list.get(i)+" "+"\n");
        }
        writer.close();


    }

    static void savePic(BufferedInputStream bufferInputStream, String path) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        //创建缓冲流
        FileOutputStream fileOutStream = new FileOutputStream(new
                File(path));
        BufferedOutputStream bufferedOut = new BufferedOutputStream
                (fileOutStream);
        //图片写入
        while ((len = bufferInputStream.read(buffer, 0, 1024)) != -1) {
            bufferedOut.write(buffer, 0, len);
        }
        //缓冲流释放与关闭
        bufferedOut.flush();
        bufferedOut.close();

    }

}
