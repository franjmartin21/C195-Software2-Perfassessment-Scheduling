package com.fm.scheduling.dao;

import com.fm.scheduling.domain.User;

import java.sql.*;

public class UserDao extends BaseDao<User> {

    private static final String GET_BY_ID_QUERY =" " +
            "SELECT     userId," +
            "           userName," +
            "           password," +
            "           active," +
                        COMMON_COLUMS_QUERY +
            " FROM      User" +
            " WHERE     userId = ?";

    private static final String INSERT_QUERY = "" +
            "INSERT INTO User (userName, password, active," + COMMON_COLUMS_QUERY + ")" +
            "VALUES(" + "?,?,?," + COMMON_COLUMN_WILDCARDS + ")";


    private static final String UPDATE_QUERY = "" +
            "UPDATE User set UserName = ?," +
            "            password = ?," +
            "            active = ?," +
                         COMMON_COLUMNS_UPDATE +
            "WHERE userId = ?";

    private static final String DELETE_QUERY = "" +
            "DELETE FROM User WHERE userId = ?";


    public UserDao() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        super(User.class);
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

    @Override
    public int insert(User object, User user) throws SQLException {
        int returnCode;
        super.createConnectionDb();
        PreparedStatement ps = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, object.getUserName());
        ps.setString(2, object.getPassword());
        ps.setBoolean(3, object.isActive());
        ps.setString(4, user.getUserName() );
        ps.setTimestamp(5, Timestamp.valueOf(java.time.LocalDateTime.now()));
        ps.setTimestamp(6, Timestamp.valueOf(java.time.LocalDateTime.now()));
        ps.setString(7, user.getUserName());
        returnCode = ps.executeUpdate();
        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()){
            returnCode=rs.getInt(1);
        }
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

    @Override
    protected User mapResultSet(ResultSet rs) throws SQLException{
        if(rs == null) return null;

        User user = null;
        while (rs.next()) {
            user = super.mapResultSet(rs);
            user.setUserId(rs.getInt("userId"));
            user.setUserName(rs.getString("userName"));
            user.setPassword(rs.getString("password"));
            user.setActive(rs.getBoolean("active"));
        }
        return user;
    }
}
