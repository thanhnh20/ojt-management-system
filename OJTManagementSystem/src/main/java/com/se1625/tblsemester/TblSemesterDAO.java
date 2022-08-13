package com.se1625.tblsemester;


import com.se1625.utils.DBHelper;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Thai Quoc Toan <toantqse151272@fpt.edu.vn>
 */
public class TblSemesterDAO implements Serializable{
    
    public TblSemesterDTO getCurrentSemester() throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT Top 1 endDate, semesterName, semesterID, startDate "
                        + "FROM tblSemester "
                        + "ORDER BY endDate DESC ";
                stm = con.prepareStatement(sql);
                
                rs = stm.executeQuery();
                
                if (rs.next()) {
                    Date endDate = rs.getDate("endDate");
                    String semesterName = rs.getString("semesterName");
                    Date startDate = rs.getDate("startDate");
                    int semesterID = rs.getInt("semesterID");
                    
                    TblSemesterDTO semester = new TblSemesterDTO();
                    semester.setEndDate(endDate);
                    semester.setSemesterName(semesterName);
                    semester.setSemesterID(semesterID);
                    semester.setStartDate(startDate);
                    
                    return semester;
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
    
    public boolean addNextSemester(String semesterName, 
            Date semesterStartDate, Date semesterEndDate) 
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "INSERT INTO tblSemester (semesterName, startDate, endDate) "
                        + "VALUES (?, ?, ?) ";
                stm = con.prepareStatement(sql);
                stm.setString(1, semesterName);
                stm.setDate(2, semesterStartDate);
                stm.setDate(3, semesterEndDate);
                
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
    
    public List<TblSemesterDTO> getListSemester() throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        List<TblSemesterDTO> listSemester = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT endDate, semesterName, semesterID, startDate "
                        + "FROM tblSemester "
                        + "ORDER BY endDate ASC ";
                stm = con.prepareStatement(sql);
                
                rs = stm.executeQuery();
                
                while (rs.next()) {
                    Date endDate = rs.getDate("endDate");
                    String semesterName = rs.getString("semesterName");
                    Date startDate = rs.getDate("startDate");
                    int semesterID = rs.getInt("semesterID");
                    
                    TblSemesterDTO semester = new TblSemesterDTO();
                    semester.setEndDate(endDate);
                    semester.setSemesterName(semesterName);
                    semester.setSemesterID(semesterID);
                    semester.setStartDate(startDate);
                    
                    if (listSemester == null) {
                        listSemester = new ArrayList<>();
                    }
                    listSemester.add(semester);
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
        return listSemester;
    }
    
    public TblSemesterDTO getSemesterByID(int semesterID) 
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT endDate, semesterName, semesterID, startDate "
                        + "FROM tblSemester "
                        + "WHERE semesterID = ? ";
                stm = con.prepareStatement(sql);
                stm.setInt(1, semesterID);
                
                rs = stm.executeQuery();
                
                if (rs.next()) {
                    Date endDate = rs.getDate("endDate");
                    String semesterName = rs.getString("semesterName");
                    Date startDate = rs.getDate("startDate");
                    int semester_ID = rs.getInt("semesterID");
                    
                    TblSemesterDTO semester = new TblSemesterDTO();
                    semester.setEndDate(endDate);
                    semester.setSemesterName(semesterName);
                    semester.setSemesterID(semester_ID);
                    semester.setStartDate(startDate);
                    
                    return semester;
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
