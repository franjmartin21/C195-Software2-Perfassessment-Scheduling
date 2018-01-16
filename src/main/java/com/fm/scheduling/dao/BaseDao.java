package com.fm.scheduling.dao;

import com.fm.scheduling.domain.BaseRecord;
import com.fm.scheduling.domain.User;
import com.fm.scheduling.util.UtilMessages;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public abstract class BaseDao<E extends BaseRecord> {

    private static final String CONFIG_FILE = "config";

    private String url;

    private String dbName;

    private String user;

    private String password;

    private Class clazz;

    protected Connection connection;

    protected static final String COMMON_COLUMS_QUERY = "" +
            "createdBy," +
            "createDate," +
            "lastUpdate," +
            "lastUpdateBy";
    protected static final String COMMON_COLUMN_WILDCARDS = "?,?,?,?";

    protected static final String COMMON_COLUMNS_UPDATE = "" +
            "lastUpdate = ?," +
            "lastUpdateBy = ? ";


    public BaseDao(Class clazz){
        ResourceBundle resourceBundle = ResourceBundle.getBundle(CONFIG_FILE);

        this.url = resourceBundle.getString("mysql.url");
        this.dbName = resourceBundle.getString("mysql.dbName");
        this.user = resourceBundle.getString("mysql.user");
        this.password = resourceBundle.getString("mysql.password");
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.clazz = clazz;
    }

    public void createConnectionDb() throws SQLException{
        connection = DriverManager.getConnection(this.url + this.dbName , this.user, this.password);
    }

    public void closeConnectionDb(){
        try{
            connection.close();
        } catch (Exception e){}
    }

    public abstract E getById(int i) throws SQLException;

    public abstract int insert(E object, User user) throws SQLException;

    public abstract int update(E object, User user, int i) throws SQLException;

    public abstract int delete(int i) throws SQLException ;

    protected E mapResultSet(ResultSet rs) throws SQLException {
        E baseRecord = E.createInstance(clazz);
        baseRecord.setCreateDate(rs.getTimestamp("createDate").toLocalDateTime());
        baseRecord.setCreatedBy(rs.getString("createdBy"));
        baseRecord.setLastUpdate(rs.getTimestamp("lastUpdate").toLocalDateTime());
        baseRecord.setLastUpdateBy(rs.getString("lastUpdateBy"));
        return baseRecord;
    }
}
