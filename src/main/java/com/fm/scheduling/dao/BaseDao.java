package com.fm.scheduling.dao;

import com.fm.scheduling.domain.BaseRecord;
import com.fm.scheduling.domain.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class BaseDao<E extends BaseRecord> {


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
            //"createdBy = ?," +
            //"createDate = ?," +
            "lastUpdate = ?," +
            "lastUpdateBy = ? ";


    public BaseDao(Class clazz) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        this.url = "jdbc:mysql://localhost:3306/";
        this.dbName = "scheduling";
        this.user = "scheduling";
        this.password = "scheduling";
        Class.forName("com.mysql.jdbc.Driver").newInstance();
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
