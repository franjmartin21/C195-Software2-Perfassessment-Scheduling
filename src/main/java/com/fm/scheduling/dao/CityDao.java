package com.fm.scheduling.dao;

import com.fm.scheduling.domain.City;
import com.fm.scheduling.domain.Country;
import com.fm.scheduling.domain.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CityDao extends BaseDao<City> {

    private static final String GET_LIST_BY_COUNTRY_ID_QUERY = " " +
                                    " SELECT    cityId, " +
                                    "           city, " +
                                    "           countryId," +
                                                COMMON_COLUMS_QUERY +
                                    " FROM      city" +
                                    " WHERE     countryId = ?";

    private static final String GET_BY_ID_QUERY =" " +
                                    " SELECT    cityId," +
                                    "           city," +
                                    "           countryId," +
                                                COMMON_COLUMS_QUERY +
                                    " FROM      city" +
                                    " WHERE     cityId = ?";

    private static final String INSERT_QUERY = "INSERT INTO city (cityId, city, countryId, " + COMMON_COLUMS_QUERY + ")" +
            "VALUES(" + "?,?,?," + COMMON_COLUMN_WILDCARDS + ")";


    private static final String UPDATE_QUERY = "" +
            "UPDATE city set city = ?," +
            "                countryId = ?," +
                                 COMMON_COLUMNS_UPDATE +
            "WHERE cityId = ?";

    private static final String DELETE_QUERY = "" +
            "DELETE FROM city WHERE cityId = ?";

    public CityDao() {
        super(City.class);
    }

    public List<City> getListByCountryId(int countryId) throws SQLException{
        super.createConnectionDb();
        PreparedStatement ps = connection.prepareStatement(GET_LIST_BY_COUNTRY_ID_QUERY);
        ps.setInt(1, countryId);
        ResultSet rs = ps.executeQuery();
        List<City> cityList = mapResultSetList(rs);
        super.closeConnectionDb();
        return cityList;
    }

    @Override
    public City getById(int i) throws SQLException {
        super.createConnectionDb();
        PreparedStatement ps = connection.prepareStatement(GET_BY_ID_QUERY);
        ps.setInt(1, i);
        ResultSet rs = ps.executeQuery();
        City city= mapResultSet(rs);
        super.closeConnectionDb();
        return city;
    }

    @Override
    public int insert(City city, User user) throws SQLException {
        int returnCode = getNextId();
        super.createConnectionDb();
        PreparedStatement ps = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, returnCode);
        ps.setString(2, city.getCity());
        ps.setInt(3, city.getCountryId() );
        ps.setString(4, user.getUserName() );
        ps.setTimestamp(5, Timestamp.valueOf(java.time.LocalDateTime.now()));
        ps.setTimestamp(6, Timestamp.valueOf(java.time.LocalDateTime.now()));
        ps.setString(7, user.getUserName());
        ps.executeUpdate();
        super.closeConnectionDb();
        return returnCode;
    }

    @Override
    public int update(City city, User user, int i)  throws SQLException {
        int returnCode;
        super.createConnectionDb();
        PreparedStatement ps = connection.prepareStatement(UPDATE_QUERY);
        ps.setString(1, city.getCity());
        ps.setInt(2, city.getCountryId());
        ps.setTimestamp(3, Timestamp.valueOf(java.time.LocalDateTime.now()));
        ps.setString(4, user.getUserName());
        ps.setInt(5, i);
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

    public List<City> mapResultSetList(ResultSet rs) throws SQLException{
        if(rs == null) return null;

        List<City> cityList = new ArrayList<City>();
        City city = null;
        while (rs.next()) {
            city = super.mapResultSet(rs);
            city.setCityId(rs.getInt("cityId"));
            city.setCity(rs.getString("city"));
            city.setCountryId(rs.getInt("countryId"));
            cityList.add(city);
        }
        return cityList;
    }

    @Override
    protected City mapResultSet(ResultSet rs) throws SQLException{
        if(rs == null) return null;

        City city = null;
        while (rs.next()) {
            city = super.mapResultSet(rs);
            city.setCityId(rs.getInt("cityId"));
            city.setCity(rs.getString("city"));
            city.setCountryId(rs.getInt("countryId"));
        }
        return city;
    }

}
