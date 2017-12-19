package com.fm.scheduling.dao;

import com.fm.scheduling.domain.Country;
import com.fm.scheduling.domain.User;

import java.sql.*;

public class CountryDao extends BaseDao<Country> {

    private static final String GET_BY_ID_QUERY =" " +
                                    " SELECT    countryId," +
                                    "           country," +
                                                COMMON_COLUMS_QUERY +
                                    " FROM      Country" +
                                    " WHERE     countryId = ?";

    private static final String INSERT_QUERY = "INSERT INTO Country (country, " + COMMON_COLUMS_QUERY + ")" +
            "VALUES(" + "?," + COMMON_COLUMN_WILDCARDS + ")";


    private static final String UPDATE_QUERY = "" +
            "UPDATE Country set country = ?," +
                                 COMMON_COLUMNS_UPDATE +
            "WHERE countryId = ?";

    private static final String DELETE_QUERY = "" +
            "DELETE FROM Country WHERE countryId = ?";

    public CountryDao() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        super(Country.class);
    }

    @Override
    public Country getById(int i) throws SQLException {
        super.createConnectionDb();
        PreparedStatement ps = connection.prepareStatement(GET_BY_ID_QUERY);
        ps.setInt(1, i);
        ResultSet rs = ps.executeQuery();
        Country country= mapResultSet(rs);
        super.closeConnectionDb();
        return country;
    }

    @Override
    public int insert(Country country, User user) throws SQLException {
        int returnCode;
        super.createConnectionDb();
        PreparedStatement ps = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, country.getCountry());
        ps.setString(2, user.getUserName() );
        ps.setTimestamp(3, Timestamp.valueOf(java.time.LocalDateTime.now()));
        ps.setTimestamp(4, Timestamp.valueOf(java.time.LocalDateTime.now()));
        ps.setString(5, user.getUserName());
        returnCode = ps.executeUpdate();
        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()){
            returnCode=rs.getInt(1);
        }
        super.closeConnectionDb();
        return returnCode;
    }

    @Override
    public int update(Country object, User user, int i)  throws SQLException {
        int returnCode;
        super.createConnectionDb();
        PreparedStatement ps = connection.prepareStatement(UPDATE_QUERY);
        ps.setString(1, object.getCountry());
        ps.setTimestamp(2, Timestamp.valueOf(java.time.LocalDateTime.now()));
        ps.setString(3, user.getUserName());
        ps.setInt(4, i);
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
    protected Country mapResultSet(ResultSet rs) throws SQLException{
        if(rs == null) return null;

        Country country = null;
        while (rs.next()) {
            country = super.mapResultSet(rs);
            country.setCountryId(rs.getInt("countryId"));
            country.setCountry(rs.getString("country"));
        }
        return country;
    }

}