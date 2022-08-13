/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se1625.utils;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author Thai Quoc Toan <toantqse151272@fpt.edu.vn>
 */
public class DBHelper {
    public static Connection makeConnection() 
            throws NamingException, SQLException {
        //1. get current context
        Context context = new InitialContext();
        //2. get server context
        Context tomcatContext = (Context) context.lookup("java:comp/env");
        //3. user datasource
        DataSource ds = (DataSource) tomcatContext.lookup("DBCONNECTION");
        //4. open connection 
        Connection con = ds.getConnection();
        
        return con;
    }
}
