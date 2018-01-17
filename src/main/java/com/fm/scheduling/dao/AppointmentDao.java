package com.fm.scheduling.dao;

import com.fm.scheduling.domain.Appointment;
import com.fm.scheduling.domain.Customer;
import com.fm.scheduling.domain.User;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppointmentDao extends BaseDao<Appointment> {

    private static final String SELECT_ALL_FIELDS =
            " SELECT    appointmentId," +
            "           customerId," +
            "           title," +
            "           description," +
            "           location," +
            "           contact," +
            "           url," +
            "           start," +
            "           end,";

    private static final String GET_BY_CUSTOMER =
            SELECT_ALL_FIELDS +
            COMMON_COLUMS_QUERY +
            " FROM appointment" +
            " WHERE customerId= ?";

    private static final String GET_BY_CONSULTANT =
            SELECT_ALL_FIELDS +
            COMMON_COLUMS_QUERY +
            " FROM      appointment" +
            " WHERE     createdBy= ?" +
            " ORDER BY  start ";

    private static final String GET_BY_LOCATION =
            SELECT_ALL_FIELDS +
            COMMON_COLUMS_QUERY +
            " FROM      appointment" +
            " WHERE     location= ?" +
            " ORDER BY  start ";

    private static final String GET_GROUPED_BY_DESCRIPTION_BY_DATE_RANGE = " " +
            " SELECT description, " +
            "        count(1) numAppointments " +
            " FROM appointment " +
            " WHERE start BETWEEN ? AND ? " +
            " GROUP BY description";

    private static final String GET_LIST_QUERY = " " +
            SELECT_ALL_FIELDS +
            COMMON_COLUMS_QUERY +
            " FROM      appointment" +
            " ORDER BY  start";

    private static final String GET_BY_ID_QUERY =" " +
            SELECT_ALL_FIELDS +
            COMMON_COLUMS_QUERY +
            " FROM      appointment" +
            " WHERE     appointmentId= ?";

    private static final String INSERT_QUERY = "INSERT INTO appointment (appointmentId, customerId, title, description, location, contact, url, start, end, " + COMMON_COLUMS_QUERY + ")" +
            "VALUES(" + "?,?,?,?,?,?,?,?,?," + COMMON_COLUMN_WILDCARDS + ")";

    private static final String UPDATE_QUERY = "" +
            "UPDATE appointment set customerId = ?," +
            "                       title = ?," +
            "                       description = ?," +
            "                       location = ?," +
            "                       contact = ?," +
            "                       url = ?," +
            "                       start = ?," +
            "                       end = ?," +
                                    COMMON_COLUMNS_UPDATE +
            "where appointmentId = ?";

    private static final String DELETE_QUERY = "" +
            "DELETE FROM appointment WHERE appointmentId = ?";

    public AppointmentDao() {
        super(Appointment.class);
    }

    public Map<String, Integer> getAppointmentsGroupedByDescriptionByDateRange(LocalDate startDate, LocalDate endDate) throws SQLException{
        Map<String, Integer> typeAppointmentMap = new HashMap<>();
        super.createConnectionDb();
        PreparedStatement ps = connection.prepareStatement(GET_GROUPED_BY_DESCRIPTION_BY_DATE_RANGE);
        ps.setTimestamp(1, Timestamp.valueOf(startDate.atStartOfDay()));
        ps.setTimestamp(2, Timestamp.valueOf(endDate.atTime(LocalTime.MAX)));
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            String key = rs.getString("description");
            Integer value = rs.getInt("numAppointments");
            typeAppointmentMap.put(key, value);
        }
        super.closeConnectionDb();
        return typeAppointmentMap;
    }

    public List<Appointment> getByConsultant(String consultant) throws SQLException{
        super.createConnectionDb();
        PreparedStatement ps = connection.prepareStatement(GET_BY_CONSULTANT);
        ps.setString(1, consultant);
        ResultSet rs = ps.executeQuery();
        List<Appointment> appointmentList = mapResultSetList(rs);
        super.closeConnectionDb();
        return appointmentList;
    }

    public List<Appointment> getByCustomer(int customerId) throws SQLException{
        super.createConnectionDb();
        PreparedStatement ps = connection.prepareStatement(GET_BY_CUSTOMER);
        ps.setInt(1, customerId);
        ResultSet rs = ps.executeQuery();
        List<Appointment> appointmentList = mapResultSetList(rs);
        super.closeConnectionDb();
        return appointmentList;
    }

    public List<Appointment> getByLocation(Appointment.LocationEnum location) throws SQLException{
        super.createConnectionDb();
        PreparedStatement ps = connection.prepareStatement(GET_BY_LOCATION);
        ps.setString(1, location.name());
        ResultSet rs = ps.executeQuery();
        List<Appointment> appointmentList = mapResultSetList(rs);
        super.closeConnectionDb();
        return appointmentList;
    }

    public List<Appointment> getList() throws SQLException{
        super.createConnectionDb();
        PreparedStatement ps = connection.prepareStatement(GET_LIST_QUERY);
        ResultSet rs = ps.executeQuery();
        List<Appointment> appointmentList = mapResultSetList(rs);
        super.closeConnectionDb();
        return appointmentList;
    }

    @Override
    public Appointment getById(int i) throws SQLException {
        super.createConnectionDb();
        PreparedStatement ps = connection.prepareStatement(GET_BY_ID_QUERY);
        ps.setInt(1, i);
        ResultSet rs = ps.executeQuery();
        Appointment appointment= mapResultSet(rs);
        super.closeConnectionDb();
        return appointment;
    }

    @Override
    public int insert(Appointment appointment, User user) throws SQLException {
        int returnCode = getNextId();
        super.createConnectionDb();
        PreparedStatement ps = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, returnCode);
        ps.setInt(2, appointment.getCustomerId());
        ps.setString(3, appointment.getTitle());
        ps.setString(4, appointment.getDescription().name());
        ps.setString(5, appointment.getLocation().name());
        ps.setString(6, appointment.getContact());
        ps.setString(7, appointment.getUrl());
        ps.setTimestamp(8, Timestamp.valueOf(appointment.getStart()));
        ps.setTimestamp(9, Timestamp.valueOf(appointment.getEnd()));
        ps.setString(10, user.getUserName() );
        ps.setTimestamp(11, Timestamp.valueOf(java.time.LocalDateTime.now()));
        ps.setTimestamp(12, Timestamp.valueOf(java.time.LocalDateTime.now()));
        ps.setString(13, user.getUserName());
        ps.executeUpdate();
        super.closeConnectionDb();
        return returnCode;
    }

    @Override
    public int update(Appointment object, User user, int i)  throws SQLException {
        int returnCode;
        super.createConnectionDb();
        PreparedStatement ps = connection.prepareStatement(UPDATE_QUERY);
        ps.setInt(1, object.getCustomerId());
        ps.setString(2, object.getTitle());
        ps.setString(3, object.getDescription().name());
        ps.setString(4, object.getLocation().name());
        ps.setString(5, object.getContact());
        ps.setString(6, object.getUrl());
        ps.setTimestamp(7, Timestamp.valueOf(object.getStart()));
        ps.setTimestamp(8, Timestamp.valueOf(object.getEnd()));
        ps.setTimestamp(9, Timestamp.valueOf(java.time.LocalDateTime.now()));
        ps.setString(10, user.getUserName());
        ps.setInt(11, object.getAppointmentId());
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

    public List<Appointment> mapResultSetList(ResultSet rs) throws SQLException{
        if(rs == null) return null;

        List<Appointment> appointmentList = new ArrayList<>();
        Appointment appointment = null;
        while (rs.next()) {
            appointment = super.mapResultSet(rs);
            appointment.setAppointmentId(rs.getInt("appointmentId"));
            appointment.setCustomerId(rs.getInt("customerId"));
            appointment.setTitle(rs.getString("title"));
            appointment.setDescription(Appointment.DescriptionEnum.valueOf(rs.getString("description")));
            appointment.setLocation(Appointment.LocationEnum.valueOf(rs.getString("location")));
            appointment.setContact(rs.getString("contact"));
            appointment.setUrl(rs.getString("url"));
            appointment.setStart(rs.getTimestamp("start").toLocalDateTime());
            appointment.setEnd(rs.getTimestamp("end").toLocalDateTime());
            appointmentList.add(appointment);
        }
        return appointmentList;
    }

    @Override
    protected Appointment mapResultSet(ResultSet rs) throws SQLException{
        if(rs == null) return null;

        Appointment appointment = null;
        while (rs.next()) {
            appointment = super.mapResultSet(rs);
            appointment.setAppointmentId(rs.getInt("appointmentId"));
            appointment.setCustomerId(rs.getInt("customerId"));
            appointment.setTitle(rs.getString("title"));
            appointment.setDescription(Appointment.DescriptionEnum.valueOf(rs.getString("description")));
            appointment.setLocation(Appointment.LocationEnum.valueOf(rs.getString("location")));
            appointment.setContact(rs.getString("contact"));
            appointment.setUrl(rs.getString("url"));
            appointment.setCreateDate(rs.getTimestamp("start").toLocalDateTime());
            appointment.setCreateDate(rs.getTimestamp("end").toLocalDateTime());
        }
        return appointment;
    }

}
