package com.fm.scheduling.dao;

import com.fm.scheduling.domain.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao extends BaseDao<User> {

    private static final String GET_LIST_QUERY =" " +
            "SELECT     userId," +
            "           userName," +
            "           password," +
            "           active," +
            "           createBy," +
            "           createDate," +
            "           lastUpdate," +
            "           lastUpdatedBy" +
            " FROM      user";

    private static final String GET_BY_ID_QUERY =" " +
            "SELECT     userId," +
            "           userName," +
            "           password," +
            "           active," +
            "           createBy," +
            "           createDate," +
            "           lastUpdate," +
            "           lastUpdatedBy" +
            " FROM      user" +
            " WHERE     userId = ?";

    private static final String GET_BY_NAME_QUERY =" " +
            "SELECT     userId," +
            "           userName," +
            "           password," +
            "           active," +
            "           createBy," +
            "           createDate," +
            "           lastUpdate," +
            "           lastUpdatedBy" +
            " FROM      user" +
            " WHERE     userName = ?";


    private static final String INSERT_QUERY = "" +
            "INSERT INTO user (userId, userName, password, active," + COMMON_COLUMS_QUERY + ")" +
            "VALUES(" + "?,?,?,?," + COMMON_COLUMN_WILDCARDS + ")";


    private static final String UPDATE_QUERY = "" +
            "UPDATE user set UserName = ?," +
            "            password = ?," +
            "            active = ?," +
            "            createBy = ?," +
            "            createDate = ?," +
            "            lastUpdate = ?," +
            "            lastUpdatedBy = ?" +
            "WHERE userId = ?";

    private static final String DELETE_QUERY = "" +
            "DELETE FROM user WHERE userId = ?";


    public UserDao(){
        super(User.class);
    }

    public List<User> getList() throws SQLException{
        super.createConnectionDb();
        PreparedStatement ps = connection.prepareStatement(GET_LIST_QUERY);
        ResultSet rs = ps.executeQuery();
        List<User> userList = mapResultSetList(rs);
        super.closeConnectionDb();
        return userList;
    }

    @Override
    public User getById(int i) throws SQLException {
        super.createConnectionDb();
        PreparedStatement ps = connection.prepareStatement(GET_BY_ID_QUERY);
        ps.setInt(1, i);
        ResultSet rs = ps.executeQuery();
        User user = mapResultSet(rs);
        super.closeConnectionDb();
        return user;
    }

    public User getByName(String name) throws SQLException{
        super.createConnectionDb();
        PreparedStatement ps = connection.prepareStatement(GET_BY_NAME_QUERY);
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();
        User user = mapResultSet(rs);
        super.closeConnectionDb();
        return user;
    }

    @Override
    public int insert(User object, User user) throws SQLException {
        int returnCode = getNextId();
        super.createConnectionDb();
        PreparedStatement ps = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, returnCode);
        ps.setString(2, object.getUserName());
        ps.setString(3, object.getPassword());
        ps.setBoolean(4, object.isActive());
        ps.setString(5, user.getUserName() );
        ps.setTimestamp(6, Timestamp.valueOf(java.time.LocalDateTime.now()));
        ps.setTimestamp(7, Timestamp.valueOf(java.time.LocalDateTime.now()));
        ps.setString(8, user.getUserName());
        ps.executeUpdate();
        super.closeConnectionDb();
        return returnCode;
    }

    @Override
    public int update(User object, User user, int i) throws SQLException {
        int returnCode;
        super.createConnectionDb();
        PreparedStatement ps = connection.prepareStatement(UPDATE_QUERY);
        ps.setString(1, object.getUserName());
        ps.setString(2, object.getPassword());
        ps.setBoolean(3, object.isActive());
        //ps.setString(4, object.getCreatedBy() );
        //ps.setTimestamp(5, Timestamp.valueOf(java.time.LocalDateTime.now()));
        ps.setTimestamp(4, Timestamp.valueOf(java.time.LocalDateTime.now()));
        ps.setString(5, user.getUserName());
        ps.setInt(6, i);
        returnCode = ps.executeUpdate();
        super.closeConnectionDb();
        return returnCode;
    }

    @Override
    public int delete(int i) throws SQLException {
        int returnCode;
        super.createConnectionDb();
        PreparedStatement ps = connection.prepareStatement(DELETE_QUERY);
        ps.setInt(1, i);
        returnCode = ps.executeUpdate();
        super.closeConnectionDb();
        return returnCode;
    }

    public List<User> mapResultSetList(ResultSet rs) throws SQLException{
        if(rs == null) return null;

        List<User> userList = new ArrayList<>();
        User user = new User();
        while (rs.next()) {
            user.setCreateDate(rs.getTimestamp("createDate").toLocalDateTime());
            user.setCreatedBy(rs.getString("createBy"));
            user.setLastUpdate(rs.getTimestamp("lastUpdate").toLocalDateTime());
            user.setLastUpdateBy(rs.getString("lastUpdatedBy"));
            user.setUserId(rs.getInt("userId"));
            user.setUserName(rs.getString("userName"));
            user.setPassword(rs.getString("password"));
            user.setActive(rs.getBoolean("active"));
            userList.add(user);
        }
        return userList;
    }

    @Override
    protected User mapResultSet(ResultSet rs) throws SQLException{
        if(rs == null) return null;

        User user = null;
        while (rs.next()) {
            user = User.createInstance(User.class);
            user.setCreateDate(rs.getTimestamp("createDate").toLocalDateTime());
            user.setCreatedBy(rs.getString("createBy"));
            user.setLastUpdate(rs.getTimestamp("lastUpdate").toLocalDateTime());
            user.setLastUpdateBy(rs.getString("lastUpdatedBy"));
            user.setUserId(rs.getInt("userId"));
            user.setUserName(rs.getString("userName"));
            user.setPassword(rs.getString("password"));
            user.setActive(rs.getBoolean("active"));
        }
        return user;
    }
}
