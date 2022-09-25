package db;

import model.newModel;
import org.apache.commons.dbutils.QueryRunner;
import util.TimeUtil;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

public class MYSQLControl {
    static DataSource ds = MyDataSource.getDataSource("jdbc:mysql://127.0.0.1:3306/"
            + "mysql?useUnicode=true&characterEncoding=UTF8");
    static QueryRunner qr = new QueryRunner(ds);
    public static void executeInsert(List<newModel> data){
        Object[][] params = new Object[data.size()][6];
        for (int i=0;i< params.length;i++){
            params[i][0] = data.get(i).getId();
            params[i][1] = data.get(i).getHeadline();
            params[i][2] = data.get(i).getUrl();
            params[i][3] = TimeUtil.TimeStampToDate(
                    data.get(i).getTimstamp(),
                    "yyyy-MM-dd HH:mm:ss"); //时间标准化
            params[i][4] = data.get(i).getTags();
            params[i][5] = data.get(i).getSummary();

        }

        try {
            qr.batch("insert into fnlondonnews(id,headline,url,"+"time,tags,summary)"+"values (?,?,?,?,?,?)",params);
            System.out.println("数据库执行完毕！"+"成功插入数据"+data.size()+"条");
        } catch (SQLException e) {
            e.printStackTrace();
        }



    }



}
