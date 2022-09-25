package db;

import model.magnet_model;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.SQLException;
import java.util.List;

public class MySqlControl {
    static javax.sql.DataSource ds =  MyDataSource.getDataSource("jdbc:mysql://127.0.0.1:3306/mysql?useUnicode=true&characterEncoding=UTF-8&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC&autoReconnect=true&failOverReadOnly=false");
    static QueryRunner qr = new QueryRunner(ds);

    public static void executeInsert(List<magnet_model> data){
        Object[][] params = new Object[data.size()][8];
        for (int i =0;i< params.length;i++){
            params[i][0] = data.get(i).getTitle();
            params[i][1] = data.get(i).getActress();
            params[i][2] = data.get(i).getSubline();
            params[i][3] = data.get(i).getHD();
            params[i][4] = data.get(i).getMagenet();
            params[i][5] = data.get(i).getNum();
            params[i][6] = data.get(i).getTypes();
            params[i][7] = data.get(i).getDate();
        }

        try {
            qr.batch("insert into magnet_db(title,actress,subline,HD,magnet,num,types,date) values (?,?,?,?,?,?,?,?)",params);
            qr.execute("delete from magnet_db where magnet in (\n" +
                    "    select\n" +
                    "       t.magnet\n" +
                    "    from (\n" +
                    "         select\n" +
                    "        magnet\n" +
                    "        from magnet_db\n" +
                    "        group by magnet\n" +
                    "        having count(1)>1\n" +
                    "             ) t\n" +
                    "    ) and magnet not in (\n" +
                    "select\n" +
                    "dt.minMagnet\n" +
                    "from\n" +
                    "(select min(num) as minMagnet from magnet_db\n" +
                    "group by magnet\n" +
                    "having count(1) >1) dt)");

            System.out.println("成功插入数据"+data.size()+"条");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}
