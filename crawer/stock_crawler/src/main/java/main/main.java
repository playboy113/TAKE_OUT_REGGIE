package main;

import Parse.Parse;
import db.MYSQLControl;
import model.newModel;
import util.HttpRequestUtil;

import java.util.List;

public class main {
    static HttpRequestUtil httpRequestUtil = new HttpRequestUtil();

    public static void main(String[] args) {
        for (int i=1;i<5;i++){
            int page = i;
            String everypageurl = "https://www.fnlondon.com/news/"+page;
            String html = httpRequestUtil.getContentByHttpGetMethod(everypageurl,"gbk");
            List<newModel> dataList = Parse.getData(html,page);
            MYSQLControl.executeInsert(dataList);
        }
    }
}
