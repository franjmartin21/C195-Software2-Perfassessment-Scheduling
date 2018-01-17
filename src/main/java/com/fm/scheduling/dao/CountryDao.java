package com.fm.scheduling.dao;

import com.fm.scheduling.domain.Country;
import com.fm.scheduling.domain.Customer;
import com.fm.scheduling.domain.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CountryDao extends BaseDao<Country> {

    private static final String GET_LIST_QUERY = " " +
            " SELECT    countryId," +
            "           country," +
            COMMON_COLUMS_QUERY +
            " FROM      country";

    private static final String GET_BY_ID_QUERY =" " +
                                    " SELECT    countryId," +
                                    "           country," +
                                                COMMON_COLUMS_QUERY +
                                    " FROM      country" +
                                    " WHERE     countryId = ?";

    private static final String INSERT_QUERY = "INSERT INTO country (countryId, country, " + COMMON_COLUMS_QUERY + ")" +
            "VALUES(" + "?,?," + COMMON_COLUMN_WILDCARDS + ")";


    private static final String UPDATE_QUERY = "" +
            "UPDATE country set country = ?," +
                                 COMMON_COLUMNS_UPDATE +
            "WHERE countryId = ?";

    private static final String DELETE_QUERY = "" +
            "DELETE FROM country WHERE countryId = ?";

    public CountryDao() {
        super(Country.class);
    }

    public List<Country> getList() throws SQLException{
        super.createConnectionDb();
        PreparedStatement ps = connection.prepareStatement(GET_LIST_QUERY);
        ResultSet rs = ps.executeQuery();
        List<Country> countryList = mapResultSetList(rs);
        super.closeConnectionDb();
        return countryList;
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
        int returnCode = getNextId();
        super.createConnectionDb();
        PreparedStatement ps = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, getNextId());
        ps.setString(2, country.getCountry());
        ps.setString(3, user.getUserName() );
        ps.setTimestamp(4, Timestamp.valueOf(java.time.LocalDateTime.now()));
        ps.setTimestamp(5, Timestamp.valueOf(java.time.LocalDateTime.now()));
        ps.setString(6, user.getUserName());
        ps.executeUpdate();
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

    public List<Country> mapResultSetList(ResultSet rs) throws SQLException{
        if(rs == null) return null;

        List<Country> countryList = new ArrayList<Country>();
        Country country = null;
        while (rs.next()) {
            country = super.mapResultSet(rs);
            country.setCountryId(rs.getInt("countryId"));
            country.setCountry(rs.getString("country"));
            countryList.add(country);
        }
        return countryList;
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
