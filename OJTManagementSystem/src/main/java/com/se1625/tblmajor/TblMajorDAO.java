/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se1625.tblmajor;

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
public class TblMajorDAO implements Serializable{
    private List<TblMajorDTO> listNameMajor;

    public List<TblMajorDTO> getListNameMajor() {
        return listNameMajor;
    }
    
    
    public void getNameMajor() throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT major.majorID, major.majorName "
                        + "FROM tblMajor AS major ";
                stm = con.prepareCall(sql);
                
                rs = stm.executeQuery();
                while (rs.next()) {
                    int majorID = rs.getInt("majorID");
                    String majorName = rs.getNString("majorName");
                    
                    TblMajorDTO dto = new TblMajorDTO(majorID, majorName);
                    
                    if (listNameMajor == null) {
                        listNameMajor = new ArrayList<>();
                    }
                    listNameMajor.add(dto);
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
    }
    

    public TblMajorDTO getMajor(int majorID) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        TblMajorDTO major = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT major.majorName "
                        + "FROM tblMajor AS major "
                        + "WHERE majorID = ?";
                stm = con.prepareCall(sql);
                stm.setInt(1, majorID);
                
                rs = stm.executeQuery();
                if (rs.next()) {
                    String majorName = rs.getNString("majorName");
                    
                    major = new TblMajorDTO(majorID, majorName);

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
        return major;
    }

    
    // hàm tìm majorID bằng majorName
    // nơi dùng: HomeShowCompanyDetailServlet
    public int getMajorIDByMajorName(String majorName) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT majorID "
                        + "FROM tblMajor  "
                        + "WHERE majorName = ?";
                stm = con.prepareCall(sql);
                stm.setString(1, majorName);
                
                rs = stm.executeQuery();
                if (rs.next()) {
                    int majorID = rs.getInt("majorID");
                    return majorID;
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
        return 0;
    }
    
    public boolean checkExistedMajor(String majorName) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT majorID "
                        + "FROM tblMajor  "
                        + "WHERE majorName = ?";
                stm = con.prepareCall(sql);
                stm.setString(1, majorName);
                
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
}
