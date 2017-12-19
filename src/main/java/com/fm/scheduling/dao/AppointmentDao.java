package com.fm.scheduling.dao;

import com.fm.scheduling.domain.Appointment;
import com.fm.scheduling.domain.User;

import java.sql.*;

public class AppointmentDao extends BaseDao<Appointment> {

    private static final String GET_BY_ID_QUERY =" " +
            " SELECT    appointmentId," +
            "           customerId," +
            "           title," +
            "           description," +
            "           location," +
            "           contact," +
            "           url," +
            "           start," +
            "           end," +
                        COMMON_COLUMS_QUERY +
            " FROM      Appointment" +
            " WHERE     appointmentId= ?";

    private static final String INSERT_QUERY = "INSERT INTO Appointment (customerId, title, description, location, contact, url, start, end, " + COMMON_COLUMS_QUERY + ")" +
            "VALUES(" + "?,?,?,?,?,?,?,?," + COMMON_COLUMN_WILDCARDS + ")";


    private static final String UPDATE_QUERY = "" +
            "UPDATE Appointment set customerId = ?," +
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
            "DELETE FROM Appointment WHERE appointmentId = ?";

    public AppointmentDao() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        super(Appointment.class);
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
        int returnCode;
        super.createConnectionDb();
        PreparedStatement ps = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, appointment.getCustomerId());
        ps.setString(2, appointment.getTitle());
        ps.setString(3, appointment.getDescription());
        ps.setString(4, appointment.getLocation());
        ps.setString(5, appointment.getContact());
        ps.setString(6, appointment.getUrl());
        ps.setTimestamp(7, Timestamp.valueOf(java.time.LocalDateTime.now()));
        ps.setTimestamp(8, Timestamp.valueOf(java.time.LocalDateTime.now()));
        ps.setString(9, user.getUserName() );
        ps.setTimestamp(10, Timestamp.valueOf(java.time.LocalDateTime.now()));
        ps.setTimestamp(11, Timestamp.valueOf(java.time.LocalDateTime.now()));
        ps.setString(12, user.getUserName());
        returnCode = ps.executeUpdate();
        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()){
            returnCode=rs.getInt(1);
        }
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
        ps.setString(3, object.getDescription());
        ps.setString(4, object.getLocation());
        ps.setString(5, object.getContact());
        ps.setString(6, object.getUrl());
        ps.setTimestamp(7, Timestamp.valueOf(java.time.LocalDateTime.now()));
        ps.setTimestamp(8, Timestamp.valueOf(java.time.LocalDateTime.now()));
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


    @Override
    protected Appointment mapResultSet(ResultSet rs) throws SQLException{
        if(rs == null) return null;

        Appointment appointment = null;
        while (rs.next()) {
            appointment = super.mapResultSet(rs);
            appointment.setAppointmentId(rs.getInt("appointmentId"));
            appointment.setCustomerId(rs.getInt("customerId"));
            appointment.setTitle(rs.getString("title"));
            appointment.setDescription(rs.getString("description"));
            appointment.setLocation(rs.getString("location"));
            appointment.setContact(rs.getString("contact"));
            appointment.setUrl(rs.getString("url"));
            appointment.setCreateDate(rs.getTimestamp("start").toLocalDateTime());
            appointment.setCreateDate(rs.getTimestamp("end").toLocalDateTime());
        }
        return appointment;
    }

}
