import db.MySqlControl;
import javdb.crawer_javdb;
import model.magnet_model;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class javdb_main {
    public static void main(String[] args) throws IOException, URISyntaxException {
        String proxyHost = "127.0.0.1";
        String proxyPort = "7890";
        System.setProperty("http.proxyHost", proxyHost);
        System.setProperty("http.proxyPort", proxyPort);

// 对https也开启代理
        System.setProperty("https.proxyHost", proxyHost);
        System.setProperty("https.proxyPort", proxyPort);

        for (int i =1;i<25;i++){
            String url = "https://javdb.com/search?f=download&q=blk&sb="+i;
            crawer_javdb crawer_javdb = new crawer_javdb();
            ArrayList<magnet_model> javdb = crawer_javdb.javdb(url);
            MySqlControl.executeInsert(javdb);
        }


    }
}
