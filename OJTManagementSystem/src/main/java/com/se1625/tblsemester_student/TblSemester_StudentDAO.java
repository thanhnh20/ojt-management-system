/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se1625.tblsemester_student;

import com.se1625.tblsemester.TblSemesterDAO;
import com.se1625.tblsemester.TblSemesterDTO;
import com.se1625.utils.DBHelper;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author Thai Quoc Toan <toantqse151272@fpt.edu.vn>
 */
public class TblSemester_StudentDAO implements Serializable{
    
    public boolean addNewSemesterForStudent(int semesterId, String studentCode) 
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "INSERT INTO tblSemester_Student (semesterID, studentCode) "
                        + "VALUES (?, ?) ";
                stm = con.prepareStatement(sql);
                stm.setInt(1, semesterId);
                stm.setString(2, studentCode);
                
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
    
    public boolean deleteStudentSemester(String studentCode) 
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "DELETE FROM tblSemester_Student "
                        + "WHERE studentCode = ? ";
                stm = con.prepareStatement(sql);
                stm.setString(1, studentCode);
                
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
    
    public TblSemesterDTO getSemesterOfStudent(String studentCode) 
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT TOP 1 ss.semesterID "
                        + "FROM tblSemester_Student AS ss INNER JOIN TblSemester AS s ON (ss.semesterID = s.semesterID) "
                        + "WHERE ss.studentCode = ? "
                        + "ORDER BY s.endDate DESC ";
                stm = con.prepareStatement(sql);
                stm.setString(1, studentCode);
                
                rs = stm.executeQuery();
                
                if (rs.next()) {
                    int semesterID = rs.getInt("semesterID");
                    
                    TblSemesterDAO semesterDAO = new TblSemesterDAO();
                    TblSemesterDTO semester = semesterDAO.getSemesterByID(semesterID);
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
