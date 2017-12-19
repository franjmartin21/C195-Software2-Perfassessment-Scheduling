package com.fm.scheduling;

import java.sql.*;

public class MainClass {
    public static void main(String[] args) throws ClassNotFoundException,SQLException, IllegalAccessException, InstantiationException {
        Connection con = null;
        String sURL = "jdbc:mysql://localhost:3306/scheduling";
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        con = DriverManager.getConnection(sURL,"root","emer_ms_ITO21");
        PreparedStatement stmt = con.prepareStatement("SELECT * FROM User");
        ResultSet rs = stmt.executeQuery();
        while (rs.next())
            System.out.println (rs.getString("userName"));
    }
}
