package com.example.crawer_begin.utils;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import sun.misc.IOUtils;


import java.io.*;
import java.net.*;

public class HtmlParseUtil {

    public static void main(String[] args) throws IOException {

        //请求头
        String url = "https://www-us.apache.org/dist//httpd/httpd-2.4.37.tar.gz";
        Response response =Jsoup.connect(url).validateTLSCertificates(false).timeout(10*60*1000).maxBodySize(Integer.MAX_VALUE)
                .method(Connection.Method.GET).ignoreContentType(true).execute();
        if (response.statusCode() == 200){
            BufferedInputStream bufferedInputStream = response.bodyStream();
            saveFile(bufferedInputStream,"E:\\java\\crawer\\crawer_begin\\src\\main\\java\\com\\example\\crawer_begin\\utils\\img\\httpd-2.4.37.tar.gz");
        }

    }
    static void saveFile(BufferedInputStream bufferedInputStream,String path) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;

        FileOutputStream fileOutputStream = new FileOutputStream(new File(path));
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);

        while ((len=bufferedInputStream.read(buffer,0,1024)) != -1){
            bufferedOutputStream.write(buffer,0,len);
        }
        bufferedOutputStream.flush();
        bufferedOutputStream.close();


    }
}
