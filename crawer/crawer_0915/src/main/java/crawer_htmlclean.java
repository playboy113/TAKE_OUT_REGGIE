import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class crawer_htmlclean {
    public static void main(String[] args) throws IOException, XPatherException {
        String proxyHost = "127.0.0.1";
        String proxyPort = "7890";
        System.setProperty("http.proxyHost", proxyHost);
        System.setProperty("http.proxyPort", proxyPort);

// 对https也开启代理
        System.setProperty("https.proxyHost", proxyHost);
        System.setProperty("https.proxyPort", proxyPort);


        Document doc = Jsoup.connect("https://www.sehuatang.net/").timeout(50000).get();
        String html = doc.html();
        HtmlCleaner cleaner = new HtmlCleaner();
        TagNode node = cleaner.clean(html);
        Object[] ns = node.evaluateXPath("//div[@id='qmenu_menu']//a");
        System.out.println(((TagNode)ns[0]).getText());
    }
}
