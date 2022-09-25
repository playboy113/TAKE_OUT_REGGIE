package db;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;

public class MyDataSource {
    public static DataSource getDataSource(String connectURI){
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName("con.mysql.jdbc.Driver");
        ds.setUsername("root");
        ds.setPassword("moshoushijie1996");
        ds.setUrl(connectURI);
        return ds;
    }
}
