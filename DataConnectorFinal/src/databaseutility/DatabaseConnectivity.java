package databaseutility;

import java.sql.*;
import javax.swing.*;

public class DatabaseConnectivity 
{
    private static Connection conn = null;
    private static Statement stmt = null;

    public static Statement connectToDB()
    {
        final String jdbcDriver = "com.mysql.jdbc.Driver";
        final String dbURL = "jdbc:mysql://localhost:3306/Univ";
            
        try
        {
            Class.forName(jdbcDriver);
            conn = DriverManager.getConnection(dbURL, "root", "anujk1998");
            stmt = conn.createStatement();
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        return stmt;
    }
}