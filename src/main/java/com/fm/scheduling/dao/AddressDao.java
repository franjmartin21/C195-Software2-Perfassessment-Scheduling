package com.fm.scheduling.dao;

import com.fm.scheduling.domain.Address;
import com.fm.scheduling.domain.User;

import java.sql.*;

public class AddressDao extends BaseDao<Address> {

    private static final String GET_BY_ID_QUERY =" " +
                                    " SELECT    addressId," +
                                    "           address," +
                                    "           address2," +
                                    "           cityId," +
                                    "           postalCode," +
                                    "           phone," +
                                                COMMON_COLUMS_QUERY +
                                    " FROM      Address" +
                                    " WHERE     addressId = ?";

    private static final String INSERT_QUERY = "INSERT INTO Address(address, address2, cityId, postalCode, phone, " + COMMON_COLUMS_QUERY + ")" +
            "VALUES(" + "?,?,?,?,?," + COMMON_COLUMN_WILDCARDS + ")";


    private static final String UPDATE_QUERY = "" +
            "UPDATE Address set address = ?," +
            "                address2 = ?," +
            "                cityId = ?," +
            "                postalCode = ?," +
            "                phone = ?," +
                             COMMON_COLUMNS_UPDATE +
            "WHERE addressId = ?";

    private static final String DELETE_QUERY = "" +
            "DELETE FROM Address WHERE addressId = ?";

    public AddressDao() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        super(Address.class);
    }

    @Override
    public Address getById(int i) throws SQLException {
        super.createConnectionDb();
        PreparedStatement ps = connection.prepareStatement(GET_BY_ID_QUERY);
        ps.setInt(1, i);
        ResultSet rs = ps.executeQuery();
        Address address= mapResultSet(rs);
        super.closeConnectionDb();
        return address;
    }

    @Override
    public int insert(Address address, User user) throws SQLException {
        int returnCode;
        super.createConnectionDb();
        PreparedStatement ps = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, address.getAddress());
        ps.setString(2, address.getAddress2() );
        ps.setInt(3, address.getCityId() );
        ps.setString(4, address.getPostalCode());
        ps.setString(5, address.getPhone());
        ps.setString(6, user.getUserName());
        ps.setTimestamp(7, Timestamp.valueOf(java.time.LocalDateTime.now()));
        ps.setTimestamp(8, Timestamp.valueOf(java.time.LocalDateTime.now()));
        ps.setString(9, user.getUserName());
        returnCode = ps.executeUpdate();
        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()){
            returnCode=rs.getInt(1);
        }
        super.closeConnectionDb();
        return returnCode;
    }

    @Override
    public int update(Address address, User user, int i)  throws SQLException {
        int returnCode;
        super.createConnectionDb();
        PreparedStatement ps = connection.prepareStatement(UPDATE_QUERY);
        ps.setString(1, address.getAddress());
        ps.setString(2, address.getAddress2());
        ps.setInt(3, address.getCityId());
        ps.setString(4, address.getPostalCode());
        ps.setString(5, address.getPhone());
        ps.setTimestamp(6, Timestamp.valueOf(java.time.LocalDateTime.now()));
        ps.setString(7, user.getUserName());
        ps.setInt(8, i);
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
    protected Address mapResultSet(ResultSet rs) throws SQLException{
        if(rs == null) return null;

        Address address = null;
        while (rs.next()) {
            address = super.mapResultSet(rs);
            address.setAddress(rs.getString("address"));
            address.setAddress2(rs.getString("address2"));
            address.setCityId(rs.getInt("cityId"));
            address.setPostalCode(rs.getString("postalCode"));
            address.setPhone(rs.getString("phone"));
        }
        return address;
    }

}
