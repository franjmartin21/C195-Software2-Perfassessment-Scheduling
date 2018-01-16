package com.fm.scheduling.dao;

import com.fm.scheduling.domain.Customer;
import com.fm.scheduling.domain.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDao extends BaseDao<Customer> {

    private static final String GET_LIST_QUERY = " " +
            " SELECT    customerId," +
            "           customerName," +
            "           addressId," +
            "           active," +
            COMMON_COLUMS_QUERY +
            " FROM      Customer";

    private static final String GET_BY_ID_QUERY =" " +
                                    " SELECT    customerId," +
                                    "           customerName," +
                                    "           addressId," +
                                    "           active," +
                                                COMMON_COLUMS_QUERY +
                                    " FROM      Customer" +
                                    " WHERE     customerId = ?";

    private static final String INSERT_QUERY = "INSERT INTO Customer (customerName, addressId, active, " + COMMON_COLUMS_QUERY + ")" +
            "VALUES(" + "?,?,?," + COMMON_COLUMN_WILDCARDS + ")";


    private static final String UPDATE_QUERY = "" +
            "UPDATE Customer set customerName = ?," +
            "                    addressId = ?," +
            "                    active = ?," +
                                 COMMON_COLUMNS_UPDATE +
            "where customerId = ?";

    private static final String DELETE_QUERY = "" +
            "DELETE FROM Customer WHERE customerId = ?";

    public CustomerDao() {
        super(Customer.class);
    }

    public List<Customer> getList() throws SQLException{
        super.createConnectionDb();
        PreparedStatement ps = connection.prepareStatement(GET_LIST_QUERY);
        ResultSet rs = ps.executeQuery();
        List<Customer> customerList = mapResultSetList(rs);
        super.closeConnectionDb();
        return customerList;
    }

    @Override
    public Customer getById(int i) throws SQLException {
        super.createConnectionDb();
        PreparedStatement ps = connection.prepareStatement(GET_BY_ID_QUERY);
        ps.setInt(1, i);
        ResultSet rs = ps.executeQuery();
        List<Customer> customerList = mapResultSetList(rs);
        super.closeConnectionDb();
        return customerList.isEmpty()? null: customerList.get(0);
    }

    @Override
    public int insert(Customer customer, User user) throws SQLException {
        int returnCode;
        super.createConnectionDb();
        PreparedStatement ps = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, customer.getCustomerName());
        ps.setInt(2, customer.getAddressId());
        ps.setBoolean(3, customer.isActive());
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
    public int update(Customer object, User user, int i)  throws SQLException {
        int returnCode;
        super.createConnectionDb();
        PreparedStatement ps = connection.prepareStatement(UPDATE_QUERY);
        ps.setString(1, object.getCustomerName());
        ps.setInt(2, object.getAddressId());
        ps.setBoolean(3, object.isActive());
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


    public List<Customer> mapResultSetList(ResultSet rs) throws SQLException{
        if(rs == null) return null;

        List<Customer> customerList = new ArrayList<Customer>();
        Customer customer = null;
        while (rs.next()) {
            customer = super.mapResultSet(rs);
            customer.setCustomerId(rs.getInt("customerId"));
            customer.setCustomerName(rs.getString("customerName"));
            customer.setAddressId(rs.getInt("addressId"));
            customer.setActive(rs.getBoolean("active"));
            customerList.add(customer);
        }
        return customerList;
    }

}
