package com.botkoda.sendMail.interfacesClass;

import com.botkoda.sendMail.interfaces.Connectuble;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnecterKNS implements Connectuble {
    String CONNECT_STR="your_str";
    Connection con=null;

    @Override
    public Connection connect() {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            con = DriverManager.getConnection(CONNECT_STR);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return con;
    }
}



