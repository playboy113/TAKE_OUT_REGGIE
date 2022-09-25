package Parse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import model.CollectionModel;
import model.newModel;

import java.util.ArrayList;
import java.util.List;

public class Parse {
    public static List<newModel> getData(String html,int page){
        List<newModel> dataList = new ArrayList<>();
        String getScriptJson = html.split(" window.__STATE__ = ")[1].split("</script>")[0].trim();
        String standardJson = getScriptJson.substring(0,getScriptJson.length()-1);
        JSONObject jsonfile = JSONObject.parseObject(standardJson, Feature.OrderedField);
        String search_collection = "search_collection_"
                + "{\"db\":\"fnonline\",\"query"
                + "\":\"news\",\"queryType\""
                + ":\"keyword\",\"page\":" + page + "}" ;
        List<CollectionModel> idCollection = parseArray(
                JSONObject.parseObject(JSONObject.parseObject(
                                        JSONObject.parseObject(jsonfile.get("data")
                                                        .toString()).get(search_collection)
                                                .toString()).get("data")
                                .toString()).get("collection")
                        .toString(),CollectionModel.class);
        for (CollectionModel id : idCollection){
            String article = "article_"+id.getId();
            String oneNewsString = JSONObject.parseObject(
                            JSONObject.parseObject(
                                            JSONObject.parseObject(jsonfile.get("data")
                                                            .toString()).get(article)
                                                    .toString()).get("data")
                                    .toString()).get("data")
                    .toString();
            newModel model = JSON.parseObject(oneNewsString,newModel.class);
            dataList.add(model);
        }
        return dataList;
    }
    public static <T> List<T> parseArray(String jsonString,Class<T> cls){
        List<T> list = new ArrayList<T>();
        try {
            list = JSON.parseArray(jsonString, cls);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;

    }
}
