/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se1625.tblaccount;

import com.se1625.tblstudent.TblStudentDTO;
import com.se1625.utils.DBHelper;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;

/**
 *
 * @author Thai Quoc Toan <toantqse151272@fpt.edu.vn>
 */
public class TblAccountDAO implements Serializable {

    public boolean checkExistedAccount(String email) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT acc.username "
                        + "FROM tblAccount AS acc "
                        + "WHERE acc.username = ? and acc.isAdmin = 2 ";
                stm = con.prepareStatement(sql);
                stm.setString(1, email);

                rs = stm.executeQuery();
                if (rs.next()) {
                    String username = rs.getString("username");
                    if (username != null) {
                        return true;
                    }
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return false;
    }

    public TblAccountDTO getAccountSchool() throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        TblAccountDTO account = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT acc.username, acc.password, name "
                        + "FROM tblAccount AS acc "
                        + "WHERE acc.isAdmin = ? ";
                stm = con.prepareStatement(sql);
                stm.setInt(1, 1);

                rs = stm.executeQuery();
                if (rs.next()) {
                    String username = rs.getString("username");
                    String password = rs.getString("password");
                    String name = rs.getString("name");
                    account = new TblAccountDTO();
                    account.setEmail(username);
                    account.setPassword(password);
                    account.setName(name);

                    return account;
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return null;
    }

    public boolean addAccountCompany(TblAccountDTO accountCompany) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            if (accountCompany != null) {
                con = DBHelper.makeConnection();
                if (con != null) {
                    String sql = "INSERT INTO tblAccount (username, password, name, avatar, isAdmin) "
                            + "VALUES(?, ?, ?, ?, ?) ";
                    stm = con.prepareStatement(sql);
                    stm.setString(1, accountCompany.getEmail());
                    stm.setString(2, accountCompany.getPassword());
                    stm.setNString(3, accountCompany.getName());
                    stm.setString(4, accountCompany.getAvatar());
                    stm.setInt(5, accountCompany.getIs_Admin());
                    
                    int rows = stm.executeUpdate();
                    if (rows > 0) {
                        return true;
                    }
                }
            }
        } finally {
            if (con != null) {
                con.close();
            }
        }
        return false;
    }
    public boolean checkLoginForCompanyAccount(String username, String password) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        con = DBHelper.makeConnection();
        try {
            if (con != null) {
                String sql = "SELECT username "
                        + "FROM TblAccount "
                        + "WHERE username = ? "
                        + "AND password = ? "
                        + "AND isAdmin = 3 ";

                stm = con.prepareStatement(sql);
                stm.setString(1, username);
                stm.setString(2, password);
                rs = stm.executeQuery();
                if (rs.next()) {
                    return true;
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return false;
    }

    public TblAccountDTO getAccount(String username) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT username, password, name, avatar, isAdmin "
                        + "FROM tblAccount "
                        + "WHERE username = ? ";
                stm = con.prepareStatement(sql);
                stm.setString(1, username);
                rs = stm.executeQuery();
                TblAccountDTO dto = null;
                if (rs.next()) {
                    String accountname = rs.getString("username");
                    String password = rs.getString("password");
                    String name = rs.getString("name");
                    String avatar = rs.getString("avatar");
                    int role = rs.getInt("isAdmin");
                    dto = new TblAccountDTO(accountname, name, avatar, role);
                    dto.setPassword(password);
                }
                return dto;
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return null;
    }
    
    public boolean addStudentAccount(String username, String name, String avatar, int isAdmin, String studentCode) throws NamingException, SQLException{
        Connection con = null;
        PreparedStatement stm = null;
        try{
            con = DBHelper.makeConnection();
            if(con != null){
                con.setAutoCommit(false);
                String sql1 = "INSERT INTO tblAccount("
                        + "username, name, avatar, isAdmin) "
                        + "VALUES (?, ?, ?, ?)";
                stm = con.prepareStatement(sql1);
                stm.setString(1, username);
                stm.setNString(2, name);
                stm.setString(3, avatar);
                stm.setInt(4, isAdmin);               
                int effectRow1 = stm.executeUpdate();
                
                String sql2 = "INSERT INTO tblStudent("
                        + "studentCode, username) "
                        + "VALUES (?, ?) ";                     
                stm = con.prepareStatement(sql2);
                stm.setString(1, studentCode);
                stm.setString(2, username);                
                int effectRow2 = stm.executeUpdate();
                
                con.commit();
                
                if(effectRow1 > 0 && effectRow2 > 0){
                    return true;
                }
            }
        }finally{
            if (stm != null) {
                stm.close();
            }
            if(con != null){
                con.close();
            }
        }        
        
        return false;
    }
    
    public boolean addStudentAccount(TblStudentDTO student,int currentSemester) throws NamingException, SQLException{
        Connection con = null;
        PreparedStatement stm = null;
        try{
            con = DBHelper.makeConnection();
            if(con != null){
                con.setAutoCommit(false);
                String sql1 = "INSERT INTO tblAccount("
                        + "username, name, isAdmin) "
                        + "VALUES (?, ?, ?)";
                stm = con.prepareStatement(sql1);
                stm.setString(1, student.getAccount().getEmail());
                stm.setNString(2, student.getAccount().getName());              
                stm.setInt(3, 2);              
                int effectRow1 = stm.executeUpdate();
                
                String sql2 = "INSERT INTO tblStudent("
                        + "studentCode, username, major, phone, numberOfCredit, is_Intern, is_Disabled) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?) ";                     
                stm = con.prepareStatement(sql2);
                stm.setString(1, student.getStudentCode());
                stm.setString(2, student.getAccount().getEmail());                
                stm.setNString(3, student.getMajor());                
                stm.setString(4, student.getPhone());                
                stm.setInt(5, student.getNumberOfCredit()); 
                stm.setInt(6, 0);
                stm.setBoolean(7, false);
                int effectRow2 = stm.executeUpdate();
                
                
                String sql3 = "INSERT INTO tblSemester_Student (semesterID, studentCode) "
                        + "VALUES (? ,?) ";
                stm = con.prepareStatement(sql3);
                stm.setInt(1, currentSemester);
                stm.setString(2, student.getStudentCode());
                
                int effectRow3 = stm.executeUpdate();
                con.commit();
                
                if(effectRow1 > 0 && effectRow2 > 0 && effectRow3 > 0 ){
                    return true;
                }
            }
        }finally{
            if (stm != null) {
                stm.close();
            }
            if(con != null){
                con.setAutoCommit(true);
                con.close();
            }
        }        
        
        return false;
    }

    public boolean updateAccount(String username, String avatar) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "UPDATE tblAccount "
                        + "SET avatar = ? "
                        + "WHERE username = ? ";
                stm = con.prepareStatement(sql);
                stm.setString(1, avatar);
                stm.setString(2, username);
                
                int rows = stm.executeUpdate();
                if(rows > 0) {
                    return true;
                }
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return false;
    }

    public boolean deleteAccout(String username) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "DELETE FROM tblAccount "
                        + "WHERE username = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, username);
                int rows = stm.executeUpdate();
                if (rows > 0) {
                    return true;
                }
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return false;
    }
    
    public TblAccountDTO GetAccountByRole(int role) 
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT username, password, name, isAdmin "
                        + "FROM tblAccount "
                        + "WHERE isAdmin = ? ";
                stm = con.prepareStatement(sql);
                stm.setInt(1, role);
                rs = stm.executeQuery();
                if (rs.next()) {
                    String username = rs.getString("username");
                    String password = rs.getString("password");
                    String name = rs.getString("name");
                    int isAdmin = rs.getInt("isAdmin");
                    TblAccountDTO account = new TblAccountDTO();
                    account.setEmail(username);
                    account.setPassword(password);
                    account.setName(name);
                    return account;
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return null;
    }
}
