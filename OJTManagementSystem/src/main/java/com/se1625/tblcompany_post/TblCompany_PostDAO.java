/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se1625.tblcompany_post;

import com.se1625.tblaccount.TblAccountDTO;
import com.se1625.tblcompany.TblCompanyDAO;
import com.se1625.tblcompany.TblCompanyDTO;
import com.se1625.tblmajor.TblMajorDAO;
import com.se1625.tblmajor.TblMajorDTO;
import com.se1625.tblsemester.TblSemesterDAO;
import com.se1625.tblsemester.TblSemesterDTO;
import com.se1625.tblstudent.TblStudentDAO;
import com.se1625.tblstudent.TblStudentDTO;
import com.se1625.utils.DBHelper;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author Thai Quoc Toan <toantqse151272@fpt.edu.vn>
 */
public class TblCompany_PostDAO implements Serializable {

    private List<TblCompany_PostDTO> companyPostListHome;
    private List<TblCompany_PostDTO> companyPostListAdminPage;
    private List<TblCompany_PostDTO> companyPostByFilter;

    public List<TblCompany_PostDTO> getCompanyPostListHome() {
        return companyPostListHome;
    }

    //lay list post company as ADMIN
    public List<TblCompany_PostDTO> getCompanyPostListAdminPage() {
        return companyPostListAdminPage;
    }

    public List<TblCompany_PostDTO> getCompanyPostByFilter() {
        return companyPostByFilter;
    }

    public void getListRecomendPost(String majorName) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT cp.postID, cp.title_Post, "
                        + " cp.postingDate, cp.quantityInterns, "
                        + "cp.expirationDate, cp.school_confirm, cp.statusPost, cp.workLocation, cp.vacancy, "
                        + "m.majorID, m.majorName, ac.name, ac.avatar "
                        + "FROM tblCompany_Post AS cp INNER JOIN tblMajor AS m ON (cp.majorID = m.majorID) "
                        + " INNER JOIN tblCompany AS com ON (cp.companyID = com.companyID) "
                        + "INNER JOIN tblAccount AS ac ON (com.username = ac.username) "
                        + " WHERE m.majorName = ? and com.is_Signed = ? "
                        + "ORDER BY cp.expirationDate DESC ";
                stm = con.prepareCall(sql);
                stm.setNString(1, majorName);
                stm.setBoolean(2, true);
                rs = stm.executeQuery();
                while (rs.next()) {
                    int postID = rs.getInt("postID");
                    String title_Post = rs.getNString("title_Post");
                    Date postingDate = rs.getDate("postingDate");
                    Date expirationDate = rs.getDate("expirationDate");
                    LocalDate timeDay = LocalDate.now();
                    DateTimeFormatter dayFormat
                            = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    // convert String to date type
                    java.util.Date currentDate = Date.valueOf(timeDay.format(dayFormat));
                    if (expirationDate.before(currentDate)) {
                        continue;
                    }
                    int quantityInterns = rs.getInt("quantityInterns");
                    if (quantityInterns == 0) {
                        continue;
                    }
                    boolean school_confirm = rs.getBoolean("school_confirm");
                    int statusPost = rs.getInt("statusPost");
                    String workLocation = rs.getNString("workLocation");
                    int majorID = rs.getInt("majorID");
                    String nameMajor = rs.getNString("majorName");
                    String companyName = rs.getNString("name");
                    String avatar = rs.getString("avatar");
                    String vacancy = rs.getNString("vacancy");

                    if (school_confirm == true && statusPost == 2) {
                        TblCompany_PostDTO dto = new TblCompany_PostDTO();
                        dto.setPostID(postID);
                        dto.setTitle_Post(title_Post);
                        dto.setPostingDate(postingDate);
                        dto.setExpirationDate(expirationDate);
                        dto.setQuantityIterns(quantityInterns);
                        dto.setWorkLocation(workLocation);

                        TblMajorDTO major = new TblMajorDTO();
                        major.setMajorID(majorID);
                        major.setMajorName(nameMajor);
                        dto.setMajor(major);

                        TblAccountDTO account = new TblAccountDTO();
                        account.setName(companyName);
                        account.setAvatar(avatar);

                        TblCompanyDTO company = new TblCompanyDTO();
                        company.setAccount(account);
                        dto.setCompany(company);
                        dto.setVacancy(vacancy);

                        if (companyPostListHome == null) {
                            companyPostListHome = new ArrayList<>();
                        }
                        companyPostListHome.add(dto);
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
    }

    public void getListPostHome() throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT cp.postID, cp.title_Post, "
                        + " cp.postingDate, cp.quantityInterns, "
                        + "cp.expirationDate, cp.school_confirm, cp.statusPost, cp.workLocation, "
                        + "m.majorName, ac.name, ac.avatar,cp.vacancy \n"
                        + "FROM tblCompany_Post AS cp INNER JOIN tblMajor AS m ON (cp.majorID = m.majorID) \n"
                        + " INNER JOIN tblCompany AS com ON (cp.companyID = com.companyID) "
                        + "INNER JOIN tblAccount AS ac ON (com.username = ac.username)\n"
                        + "ORDER BY cp.expirationDate DESC";
                stm = con.prepareCall(sql);
                rs = stm.executeQuery();
                while (rs.next()) {
                    int postID = rs.getInt("postID");
                    String title_Post = rs.getNString("title_Post");
                    Date postingDate = rs.getDate("postingDate");
                    Date expirationDate = rs.getDate("expirationDate");
                    LocalDate timeDay = LocalDate.now();
                    DateTimeFormatter dayFormat
                            = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    // convert String to date type
                    java.util.Date currentDate = Date.valueOf(timeDay.format(dayFormat));
                    if (expirationDate.before(currentDate)) {
                        continue;
                    }
                    int quantityInterns = rs.getInt("quantityInterns");
                    if (quantityInterns == 0) {
                        continue;
                    }
                    boolean school_confirm = rs.getBoolean("school_confirm");
                    int statusPost = rs.getInt("statusPost");
                    String workLocation = rs.getNString("workLocation");
                    String majorName = rs.getNString("majorName");
                    String companyName = rs.getNString("name");
                    String avatar = rs.getString("avatar");
                    String vacancy = rs.getString("vacancy");

                    if (school_confirm == true && statusPost == 2) {
                        TblCompany_PostDTO dto = new TblCompany_PostDTO();
                        dto.setPostID(postID);
                        dto.setTitle_Post(title_Post);
                        dto.setPostingDate(postingDate);
                        dto.setExpirationDate(expirationDate);
                        dto.setQuantityIterns(quantityInterns);
                        dto.setWorkLocation(workLocation);
                        dto.setMajorName(majorName);
                        dto.setVacancy(vacancy);
                                
                        TblAccountDTO account = new TblAccountDTO();
                        account.setName(companyName);
                        account.setAvatar(avatar);

                        TblCompanyDTO company = new TblCompanyDTO();
                        company.setAccount(account);

                        dto.setCompany(company);
                        if (companyPostListHome == null) {
                            companyPostListHome = new ArrayList<>();
                        }
                        companyPostListHome.add(dto);
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
    }

    public void searchPostByFilterAsCompanyRole(String companyID, String title_Post,
            int majorID, String nameStatus) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT post.postID, post.title_Post, post.quantityInterns, "
                        + "post.remuneration, post.postingDate, post.job_Description, post.job_Requirement, "
                        + "post.expirationDate, post.workLocation, major.majorName, major.majorID, acc.name, acc.avatar, "
                        + "post.school_confirm, post.statusPost, post.vacancy, cm.companyID "
                        + "FROM tblCompany_Post AS post INNER JOIN tblCompany AS cm ON (post.companyID = cm.companyID) "
                        + "INNER JOIN tblAccount AS acc ON (cm.username = acc.username) INNER JOIN tblMajor AS major "
                        + "ON (post.majorID = major.majorID) ";
                if (title_Post.trim().isEmpty() == true && majorID == 0 && nameStatus.trim().isEmpty() == true) {
                    sql += "WHERE post.companyID = ? ORDER BY statusPost ";
                    stm = con.prepareStatement(sql);
                    stm.setString(1, companyID);
                }
                if (title_Post.trim().isEmpty() == false && majorID != 0 && nameStatus.trim().isEmpty() == false) {
                    sql += "WHERE post.companyID = ? and post.title_Post LIKE ? and post.majorID = ? ";
                    if ("Inactive".equals(nameStatus)) {
                        sql += " and post.statusPost = ?  ORDER BY post.statusPost";
                        stm = con.prepareStatement(sql);
                        stm.setString(1, companyID);
                        stm.setNString(2, "%" + title_Post + "%");
                        stm.setInt(3, majorID);
                        stm.setInt(4, 0);
                    } else if ("Pending".equals(nameStatus)) {
                        sql += " and post.statusPost = ?  ORDER BY post.statusPost";
                        stm = con.prepareStatement(sql);
                        stm.setString(1, companyID);
                        stm.setNString(2, "%" + title_Post + "%");
                        stm.setInt(3, majorID);
                        stm.setInt(4, 1);
                    } else if ("Active".equals(nameStatus)) {
                        sql += " and post.statusPost = ?  ORDER BY post.statusPost";
                        stm = con.prepareStatement(sql);
                        stm.setString(1, companyID);
                        stm.setNString(2, "%" + title_Post + "%");
                        stm.setInt(3, majorID);
                        stm.setInt(4, 2);
                    } else if ("Expired".equals(nameStatus)) {
                        sql += " and post.statusPost = ?  ORDER BY post.statusPost";
                        stm = con.prepareStatement(sql);
                        stm.setString(1, companyID);
                        stm.setNString(2, "%" + title_Post + "%");
                        stm.setInt(3, majorID);
                        stm.setInt(4, 3);
                    }
                }
                if (title_Post.trim().isEmpty() == false && majorID == 0 && nameStatus.trim().isEmpty() == true) {
                    sql += "WHERE post.companyID = ? and post.title_Post LIKE ? ORDER BY post.statusPost";
                    stm = con.prepareStatement(sql);
                    stm.setString(1, companyID);
                    stm.setNString(2, "%" + title_Post + "%");
                }
                if (title_Post.trim().isEmpty() == false && majorID != 0 && nameStatus.trim().isEmpty() == true) {
                    sql += "WHERE post.companyID = ? and post.title_Post LIKE ? and post.majorID = ? ORDER BY post.statusPost";
                    stm = con.prepareStatement(sql);
                    stm.setString(1, companyID);
                    stm.setNString(2, "%" + title_Post + "%");
                    stm.setInt(3, majorID);
                }
                if (title_Post.trim().isEmpty() == false && majorID == 0 && nameStatus.trim().isEmpty() == false) {
                    sql += "WHERE post.companyID = ? and post.title_Post LIKE ? ";
                    if ("Inactive".equals(nameStatus)) {
                        sql += " and post.statusPost = ?  ORDER BY post.statusPost";
                        stm = con.prepareStatement(sql);
                        stm.setString(1, companyID);
                        stm.setNString(2, "%" + title_Post + "%");
                        stm.setInt(3, 0);
                    } else if ("Pending".equals(nameStatus)) {
                        sql += " and post.statusPost = ?  ORDER BY post.statusPost";
                        stm = con.prepareStatement(sql);
                        stm.setString(1, companyID);
                        stm.setNString(2, "%" + title_Post + "%");
                        stm.setInt(3, 1);
                    } else if ("Active".equals(nameStatus)) {
                        sql += " and post.statusPost = ?  ORDER BY post.statusPost";
                        stm = con.prepareStatement(sql);
                        stm.setString(1, companyID);
                        stm.setNString(2, "%" + title_Post + "%");
                        stm.setInt(3, 2);
                    } else if ("Expired".equals(nameStatus)) {
                        sql += " and post.statusPost = ?  ORDER BY post.statusPost";
                        stm = con.prepareStatement(sql);
                        stm.setString(1, companyID);
                        stm.setNString(2, "%" + title_Post + "%");
                        stm.setInt(3, 3);
                    }
                }
                if (title_Post.trim().isEmpty() == true && majorID != 0 && nameStatus.trim().isEmpty() == false) {
                    sql += "WHERE post.companyID = ? and post.majorID = ? ";
                    if ("Inactive".equals(nameStatus)) {
                        sql += " and post.statusPost = ?  ORDER BY post.statusPost";
                        stm = con.prepareStatement(sql);
                        stm.setString(1, companyID);
                        stm.setInt(2, majorID);
                        stm.setInt(3, 0);
                    } else if ("Pending".equals(nameStatus)) {
                        sql += " and post.statusPost = ?  ORDER BY post.statusPost";
                        stm = con.prepareStatement(sql);
                        stm.setString(1, companyID);
                        stm.setInt(2, majorID);
                        stm.setInt(3, 1);
                    } else if ("Active".equals(nameStatus)) {
                        sql += " and post.statusPost = ?  ORDER BY post.statusPost";
                        stm = con.prepareStatement(sql);
                        stm.setString(1, companyID);
                        stm.setInt(2, majorID);
                        stm.setInt(3, 2);
                    } else if ("Expired".equals(nameStatus)) {
                        sql += " and post.statusPost = ?  ORDER BY post.statusPost";
                        stm = con.prepareStatement(sql);
                        stm.setString(1, companyID);
                        stm.setInt(2, majorID);
                        stm.setInt(3, 3);
                    }
                }
                if (title_Post.trim().isEmpty() == true && majorID != 0 && nameStatus.trim().isEmpty() == true) {
                    sql += "WHERE post.companyID = ? and post.majorID = ? ORDER BY post.statusPost";
                    stm = con.prepareStatement(sql);
                    stm.setString(1, companyID);
                    stm.setInt(2, majorID);
                }
                if (title_Post.trim().isEmpty() == true && majorID == 0 && nameStatus.trim().isEmpty() == false) {
                    sql += "Where post.companyID = ? ";
                    if ("Inactive".equals(nameStatus)) {
                        sql += " and post.statusPost = ?  ORDER BY post.statusPost";
                        stm = con.prepareStatement(sql);
                        stm.setString(1, companyID);
                        stm.setInt(2, 0);
                    } else if ("Pending".equals(nameStatus)) {
                        sql += " and post.statusPost = ?  ORDER BY post.statusPost";
                        stm = con.prepareStatement(sql);
                        stm.setString(1, companyID);
                        stm.setInt(2, 1);
                    } else if ("Active".equals(nameStatus)) {
                        sql += " and post.statusPost = ?  ORDER BY post.statusPost";
                        stm = con.prepareStatement(sql);
                        stm.setString(1, companyID);
                        stm.setInt(2, 2);
                    } else if ("Expired".equals(nameStatus)) {
                        sql += " and post.statusPost = ?  ORDER BY post.statusPost";
                        stm = con.prepareStatement(sql);
                        stm.setString(1, companyID);
                        stm.setInt(2, 3);
                    }
                }
                rs = stm.executeQuery();
                while (rs.next()) {
                    int postID = rs.getInt("postID");
                    title_Post = rs.getNString("title_Post");
                    String workLocation = rs.getNString("workLocation");
                    String vacancy = rs.getNString("vacancy");
                    String job_Description = rs.getNString("job_Description");
                    String job_Requirement = rs.getNString("job_Requirement");
                    String remuneration = rs.getNString("remuneration");

                    boolean school_confirm = rs.getBoolean("school_confirm");
                    int statusPost = rs.getInt("statusPost");
                    majorID = rs.getInt("majorID");

                    String majorName = rs.getNString("majorName");
                    String companyName = rs.getNString("name");
                    String avatar = rs.getString("avatar");

                    int quantityInterns = rs.getInt("quantityInterns");
                    Date postingDate = rs.getDate("postingDate");
                    Date expirationDate = rs.getDate("expirationDate");
                    LocalDate timeDay = LocalDate.now();
                    DateTimeFormatter dayFormat
                            = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    // convert String to date type
                    java.util.Date currentDate = Date.valueOf(timeDay.format(dayFormat));

                    TblCompany_PostDTO dto = new TblCompany_PostDTO();
                    dto.setPostID(postID);
                    dto.setTitle_Post(title_Post);
                    dto.setPostingDate(postingDate);
                    dto.setExpirationDate(expirationDate);
                    dto.setJob_Description(job_Description);
                    dto.setJob_Requirement(job_Requirement);
                    dto.setRemuneration(remuneration);
                    dto.setQuantityIterns(quantityInterns);
                    dto.setWorkLocation(workLocation);
                    dto.setMajorName(majorName);
                    dto.setVacancy(vacancy);
                    if (quantityInterns == 0 || expirationDate.before(currentDate)) {
                        dto.setStatusPost(3);
                        dto.setSchool_confirm(false);
                    } else {
                        dto.setStatusPost(statusPost);
                        dto.setSchool_confirm(school_confirm);
                    }

                    TblAccountDTO account = new TblAccountDTO();
                    account.setName(companyName);
                    account.setAvatar(avatar);

                    TblCompanyDTO company = new TblCompanyDTO();
                    company.setAccount(account);
                    dto.setCompany(company);

                    TblMajorDTO major = new TblMajorDTO();
                    major.setMajorID(majorID);
                    major.setMajorName(majorName);
                    dto.setMajor(major);

                    if (companyPostByFilter == null) {
                        companyPostByFilter = new ArrayList<>();
                    }

                    companyPostByFilter.add(dto);

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

    public void searchPostByFilter(String companyID,
            int MajorID, String nameLocation) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT post.postID, post.title_Post, post.quantityInterns, post.postingDate, "
                        + "post.expirationDate, post.workLocation, major.majorName, acc.name, acc.avatar, "
                        + "post.school_confirm, post.statusPost,post.vacancy "
                        + "FROM tblCompany_Post AS post INNER JOIN tblCompany AS cm ON (post.companyID = cm.companyID) "
                        + "INNER JOIN tblAccount AS acc ON (cm.username = acc.username) INNER JOIN tblMajor AS major "
                        + "ON (post.majorID = major.majorID) ";
                if (companyID.isEmpty() == false && MajorID != 0
                        && nameLocation.isEmpty() == false) {
                    sql += " WHERE post.companyID = ? and post.majorID = ? "
                            + "and post.workLocation LIKE ?";
                    stm = con.prepareCall(sql);
                    stm.setString(1, companyID);
                    stm.setInt(2, MajorID);
                    stm.setNString(3, "%" + nameLocation + "%");
                }
                if (companyID.isEmpty() == false && MajorID == 0
                        && nameLocation.isEmpty() == false) {
                    sql += "WHERE post.companyID = ? and post.workLocation LIKE ?";
                    stm = con.prepareCall(sql);
                    stm.setString(1, companyID);
                    stm.setNString(2, "%" + nameLocation + "%");
                }
                if (companyID.isEmpty() == false && MajorID != 0
                        && nameLocation.isEmpty() == true) {
                    sql += "WHERE post.companyID = ? and post.majorID = ? ";
                    stm = con.prepareCall(sql);
                    stm.setString(1, companyID);
                    stm.setInt(2, MajorID);
                }
                if (companyID.isEmpty() == false && MajorID == 0
                        && nameLocation.isEmpty() == true) {
                    sql += "WHERE post.companyID = ?  ";
                    stm = con.prepareCall(sql);
                    stm.setString(1, companyID);
                }
                if (companyID.isEmpty() == true && MajorID != 0
                        && nameLocation.isEmpty() == false) {
                    sql += "WHERE post.majorID = ? and post.workLocation LIKE ?";
                    stm = con.prepareCall(sql);
                    stm.setInt(1, MajorID);
                    stm.setNString(2, "%" + nameLocation + "%");
                }
                if (companyID.isEmpty() == true && MajorID != 0
                        && nameLocation.isEmpty() == true) {
                    sql += "WHERE post.majorID = ? ";
                    stm = con.prepareCall(sql);
                    stm.setInt(1, MajorID);
                }
                if (companyID.isEmpty() == true && MajorID == 0
                        && nameLocation.isEmpty() == false) {
                    sql += "WHERE post.workLocation LIKE ? ";
                    stm = con.prepareCall(sql);
                    stm.setNString(1, "%" + nameLocation + "%");
                }

                if (companyID.isEmpty() == true && MajorID == 0
                        && nameLocation.isEmpty() == true) {
                    stm = con.prepareCall(sql);
                }
                rs = stm.executeQuery();
                while (rs.next()) {
                    int postID = rs.getInt("postID");
                    String title_Post = rs.getNString("title_Post");
                    String vacancy = rs.getNString("vacancy");
                    int quanityItens = rs.getInt("quantityInterns");
                    if (quanityItens == 0) {
                        continue;
                    }
                    Date postingDate = rs.getDate("postingDate");
                    Date expirationDate = rs.getDate("expirationDate");
                    LocalDate timeDay = LocalDate.now();
                    DateTimeFormatter dayFormat
                            = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    // convert String to date type
                    java.util.Date currentDate = Date.valueOf(timeDay.format(dayFormat));
                    if (expirationDate.before(currentDate)) {
                        continue;
                    }
                    boolean school_confirm = rs.getBoolean("school_confirm");
                    int status_Post = rs.getInt("statusPost");
                    String workLocation = rs.getNString("workLocation");
                    String majorName = rs.getNString("majorName");
                    String companyName = rs.getNString("name");
                    String avatar = rs.getString("avatar");
                    if (school_confirm == true && status_Post == 2 && quanityItens > 0) {
                        TblAccountDTO account = new TblAccountDTO();
                        account.setName(companyName);
                        account.setAvatar(avatar);

                        TblCompanyDTO company = new TblCompanyDTO();
                        company.setAccount(account);

                        TblCompany_PostDTO post = new TblCompany_PostDTO();
                        post.setCompany(company);
                        post.setMajorName(majorName);
                        post.setExpirationDate(expirationDate);
                        post.setPostID(postID);
                        post.setPostingDate(postingDate);
                        post.setWorkLocation(workLocation);
                        post.setQuantityIterns(quanityItens);
                        post.setTitle_Post(title_Post);
                        post.setVacancy(vacancy);

                        if (companyPostByFilter == null) {
                            companyPostByFilter = new ArrayList<>();
                        }

                        companyPostByFilter.add(post);
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
    }

    //SEARCH POST AS ADMIN 
    public void searchPostByFilterAsAdminRole(String titlePost,
            String companyName, String nameStatus, int semesterID) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {

                String sql = "SELECT post.postID, post.title_Post, post.quantityInterns, post.postingDate, "
                        + "post.job_Description, post.job_Requirement, post.remuneration, post.vacancy, "
                        + "post.expirationDate, post.workLocation, major.majorName, major.majorID, acc.name, acc.avatar, "
                        + "post.school_confirm, post.statusPost "
                        + "FROM tblCompany_Post AS post INNER JOIN tblCompany AS cm ON (post.companyID = cm.companyID) "
                        + "INNER JOIN tblAccount AS acc ON (cm.username = acc.username) INNER JOIN tblMajor AS major "
                        + "ON (post.majorID = major.majorID) ";
                TblSemesterDAO semesterDAO = new TblSemesterDAO();
                TblSemesterDTO semesterDTO = semesterDAO.getSemesterByID(semesterID);
                Date starDate = semesterDTO.getStartDate();
                Date endDate = semesterDTO.getEndDate();
                if (titlePost.isEmpty() == true && nameStatus.isEmpty() == true
                        && companyName.isEmpty() == true) {

                    sql += "WHERE (post.postingDate between ? and ?) order by post.statusPost ";
                    stm = con.prepareStatement(sql);
                    stm.setDate(1, starDate);
                    stm.setDate(2, endDate);
                }
                /////// /////// /////// ///////
                if (titlePost.isEmpty() == false && nameStatus.isEmpty() == true
                        && companyName.isEmpty() == false && semesterID != 0) {

                    sql += " WHERE post.title_Post LIKE ? and acc.name LIKE ? and (post.postingDate between  ?  and  ?  ) "
                            + "order by post.statusPost";
                    stm = con.prepareStatement(sql);
                    stm.setNString(1, "%" + titlePost + "%");
                    stm.setNString(2, "%" + companyName + "%");
                    stm.setDate(3, starDate);
                    stm.setDate(4, endDate);
                }
                if (titlePost.isEmpty() == true && nameStatus.isEmpty() == true
                        && companyName.isEmpty() == false) {
                    sql += "WHERE acc.name LIKE ? and (post.postingDate between ? and ?) order by post.statusPost ";
                    stm = con.prepareStatement(sql);
                    stm.setNString(1, "%" + companyName + "%");
                    stm.setDate(2, starDate);
                    stm.setDate(3, endDate);
                }
                if (titlePost.isEmpty() == false && nameStatus.isEmpty() == true
                        && companyName.isEmpty() == true) {
                    sql += "WHERE post.title_Post LIKE ? and (post.postingDate between ? and ?) order by post.statusPost ";
                    stm = con.prepareStatement(sql);
                    stm.setNString(1, "%" + titlePost + "%");
                    stm.setDate(2, starDate);
                    stm.setDate(3, endDate);
                }

                if (titlePost.isEmpty() == false && nameStatus.isEmpty() == false
                        && companyName.isEmpty() == false) {
                    sql += "WHERE post.title_Post LIKE ? and acc.name LIKE ? and (post.postingDate between ? and ?) ";
                    if (nameStatus.equals("Accept")) {
                        sql += " and post.statusPost = ? order by post.statusPost ";
                        stm = con.prepareStatement(sql);
                        stm.setNString(1, "%" + titlePost + "%");
                        stm.setNString(2, "%" + companyName + "%");
                        stm.setDate(3, starDate);
                        stm.setDate(4, endDate);
                        stm.setInt(5, 2);

                    } else if (nameStatus.equals("Denied")) {
                        sql += " and (post.statusPost = ? OR post.statusPost = ?) order by post.statusPost ";
                        stm = con.prepareStatement(sql);
                        stm.setNString(1, "%" + titlePost + "%");
                        stm.setNString(2, "%" + companyName + "%");
                        stm.setDate(3, starDate);
                        stm.setDate(4, endDate);
                        stm.setInt(5, 0);
                        stm.setInt (6, 3);

                    } else if (nameStatus.equals("Waiting")) {
                        sql += " and post.statusPost = ? order by post.statusPost ";
                        stm = con.prepareStatement(sql);
                        stm.setNString(1, "%" + titlePost + "%");
                        stm.setNString(2, "%" + companyName + "%");
                        stm.setDate(3, starDate);
                        stm.setDate(4, endDate);
                        stm.setInt(5, 1);

                    }
                }
                if (titlePost.isEmpty() == true && nameStatus.isEmpty() == false
                        && companyName.isEmpty() == false) {
                    sql += "WHERE acc.name LIKE ? and (post.postingDate between ? and ?)";
                    if (nameStatus.equals("Accept")) {
                        sql += " and post.statusPost = ? order by post.statusPost ";
                        stm = con.prepareStatement(sql);
                        stm.setNString(1, "%" + companyName + "%");
                        stm.setDate(2, starDate);
                        stm.setDate(3, endDate);
                        stm.setInt(4, 2);
                    } else if (nameStatus.equals("Denied")) {
                        sql += " and (post.statusPost = ? OR post.statusPost = ?) order by post.statusPost ";
                        stm = con.prepareStatement(sql);
                        stm.setNString(1, "%" + companyName + "%");
                        stm.setDate(2, starDate);
                        stm.setDate(3, endDate);
                        stm.setInt(4, 0);
                        stm.setInt(5, 3);
                    } else if (nameStatus.equals("Waiting")) {
                        sql += " and post.statusPost = ? order by post.statusPost ";
                        stm = con.prepareStatement(sql);
                        stm.setNString(1, "%" + companyName + "%");
                        stm.setDate(2, starDate);
                        stm.setDate(3, endDate);
                        stm.setInt(4, 1);
                    }
                }
                if (titlePost.isEmpty() == false && nameStatus.isEmpty() == false
                        && companyName.isEmpty() == true) {
                    sql += "WHERE post.title_Post LIKE ? and (post.postingDate between ? and ?) ";
                    if (nameStatus.equals("Accept")) {
                        sql += " and post.statusPost = ? order by post.statusPost ";
                        stm = con.prepareStatement(sql);
                        stm.setNString(1, "%" + titlePost + "%");
                        stm.setDate(2, starDate);
                        stm.setDate(3, endDate);
                        stm.setInt(4, 2);
                    } else if (nameStatus.equals("Denied")) {
                        sql += " and (post.statusPost = ? OR post.statusPost = ?) order by post.statusPost ";
                        stm = con.prepareStatement(sql);
                        stm.setNString(1, "%" + titlePost + "%");
                        stm.setDate(2, starDate);
                        stm.setDate(3, endDate);
                        stm.setInt(4, 0);
                        stm.setInt(5, 3);
                    } else if (nameStatus.equals("Waiting")) {
                        sql += " and post.statusPost = ? order by post.statusPost ";
                        stm = con.prepareStatement(sql);
                        stm.setNString(1, "%" + titlePost + "%");
                        stm.setDate(2, starDate);
                        stm.setDate(3, endDate);
                        stm.setInt(4, 1);
                    }
                }

                if (titlePost.isEmpty() == true && nameStatus.isEmpty() == false
                        && companyName.isEmpty() == true) {
                    sql += "WHERE (post.postingDate between ? and ?) and";
                    if (nameStatus.equals("Accept")) {
                        sql += " post.statusPost = ? order by post.statusPost ";
                        stm = con.prepareStatement(sql);
                        stm.setDate(1, starDate);
                        stm.setDate(2, endDate);
                        stm.setInt(3, 2);
                    } else if (nameStatus.equals("Denied")) {
                        sql += " (post.statusPost = ? OR post.statusPost = ?) order by post.statusPost ";
                        stm = con.prepareStatement(sql);
                        stm.setDate(1, starDate);
                        stm.setDate(2, endDate);
                        stm.setInt(3, 0);
                        stm.setInt(4, 3);
                    } else if (nameStatus.equals("Waiting")) {
                        sql += " post.statusPost = ? order by post.statusPost ";
                        stm = con.prepareStatement(sql);
                        stm.setDate(1, starDate);
                        stm.setDate(2, endDate);
                        stm.setInt(3, 1);
                    }
                }

                rs = stm.executeQuery();
                while (rs.next()) {
                    int postID = rs.getInt("postID");
                    int majorID = rs.getInt("majorID");
                    String title_Post = rs.getNString("title_Post");
                    String job_Requirement = rs.getNString("job_Requirement");
                    String job_Description = rs.getNString("job_Description");
                    String remuneration = rs.getNString("remuneration");
                    String workLocation = rs.getNString("workLocation");
                    String vacancy = rs.getNString("vacancy");
                    boolean school_confirm = rs.getBoolean("school_confirm");
                    int statusPost = rs.getInt("statusPost");

                    String majorName = rs.getNString("majorName");
                    companyName = rs.getNString("name");
                    String avatar = rs.getString("avatar");

                    int quantityInterns = rs.getInt("quantityInterns");
//                    if (quanityItens == 0) {
//                        continue;
//                    }
                    Date postingDate = rs.getDate("postingDate");
                    Date expirationDate = rs.getDate("expirationDate");
                    LocalDate timeDay = LocalDate.now();
                    DateTimeFormatter dayFormat
                            = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    // convert String to date type
                    java.util.Date currentDate = Date.valueOf(timeDay.format(dayFormat));
//                    if (expirationDate.before(currentDate)) {
//                        continue;
//                    }

                    TblCompany_PostDTO dto = new TblCompany_PostDTO();
                    dto.setPostID(postID);
                    dto.setTitle_Post(title_Post);
                    dto.setJob_Description(job_Description);
                    dto.setJob_Requirement(job_Requirement);
                    dto.setRemuneration(remuneration);
                    dto.setPostingDate(postingDate);
                    dto.setExpirationDate(expirationDate);
                    dto.setQuantityIterns(quantityInterns);
                    dto.setWorkLocation(workLocation);
                    dto.setMajorName(majorName);
                    dto.setVacancy(vacancy);
                    if (quantityInterns == 0 || expirationDate.before(currentDate)) {
                        dto.setStatusPost(3);
                        dto.setSchool_confirm(false);
                    } else {
                        dto.setStatusPost(statusPost);
                        dto.setSchool_confirm(school_confirm);
                    }

                    TblAccountDTO account = new TblAccountDTO();
                    account.setName(companyName);
                    account.setAvatar(avatar);

                    TblCompanyDTO company = new TblCompanyDTO();
                    company.setAccount(account);
                    dto.setCompany(company);

                    TblMajorDTO major = new TblMajorDTO();
                    major.setMajorID(majorID);
                    major.setMajorName(majorName);
                    dto.setMajor(major);

                    if (companyPostListAdminPage == null) {
                        companyPostListAdminPage = new ArrayList<>();
                    }

                    companyPostListAdminPage.add(dto);

                }
                //return companyPostListAdminPage;
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

    public List<TblCompany_PostDTO> getListByPage(List<TblCompany_PostDTO> list, int start, int end) {
        List<TblCompany_PostDTO> listPage = new ArrayList<>();
        for (int i = start; i < end; i++) {
            listPage.add(list.get(i));
        }
        return listPage;
    }

    // hàm tìm bài post bằng postID, trả ra tất cả fields của tblCompanyPost 
    // (postID, title_Post, job_Description, job_Requirement, remuneration,workLoaction, 
    // quantityInterns, postingDate, expirationDate, school_confirm, statusPost, 
    // tblCompany(companyID), tblMajor(majorID).
    // nơi dùng: HomeShowCompanyDetailServlet
    public TblCompany_PostDTO searchPostByPostID(int postID) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT post.title_Post, post.job_Description, post.job_Requirement, post.remuneration,"
                        + "post.workLocation, post.quantityInterns, post.postingDate, post.expirationDate,"
                        + "post.school_confirm, post.statusPost, post.companyID, major.majorName, com.companyID,post.vacancy "
                        + "FROM tblCompany_Post post JOIN tblMajor major on post.majorID = major.majorID "
                        + "JOIN tblCompany com on com.companyID = post.companyID "
                        + "WHERE post.postID = ?";
                stm = con.prepareCall(sql);
                stm.setInt(1, postID);
                rs = stm.executeQuery();
                if (rs.next()) {
                    String title_Post = rs.getNString("title_Post");
                    String job_Description = rs.getNString("job_Description");
                    String job_Requirement = rs.getNString("job_Requirement");
                    String remuneration = rs.getNString("remuneration");
                    int quanityIntens = rs.getInt("quantityInterns");
                    String vacancy = rs.getString("vacancy");

                    Date postingDate = rs.getDate("postingDate");
                    Date expirationDate = rs.getDate("expirationDate");
                    boolean school_confirm = rs.getBoolean("school_confirm");
                    int status_Post = rs.getInt("statusPost");
                    String workLocation = rs.getNString("workLocation");
                    String majorName = rs.getNString("majorName");
                    String companyID = rs.getString("companyID");

                    TblCompanyDTO company = new TblCompanyDTO();
                    company.setCompanyID(companyID);

                    TblCompany_PostDTO post = new TblCompany_PostDTO(postID, title_Post, job_Description, job_Requirement, remuneration,
                            workLocation, quanityIntens, postingDate, expirationDate, school_confirm, status_Post, company, majorName);
                    post.setVacancy(vacancy);
                    return post;
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

    public TblCompany_PostDTO getCompanyPost(int postID) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        TblCompany_PostDTO companyPost = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT postID, title_Post, job_Description, "
                        + "job_Requirement, remuneration, workLocation, "
                        + "quantityInterns, postingDate, expirationDate, "
                        + "school_Confirm, statusPost, companyID, majorID, vacancy "
                        + "FROM tblCompany_Post "
                        + "WHERE postID = ? ";
                stm = con.prepareStatement(sql);
                stm.setInt(1, postID);

                rs = stm.executeQuery();

                if (rs.next()) {

                    String title_Post = rs.getNString("title_Post");
                    String job_Description = rs.getNString("job_Description");
                    String job_Requirement = rs.getNString("job_Requirement");
                    String remuneration = rs.getNString("remuneration");

                    String workLocation = rs.getNString("workLocation");
                    int quantityInterns = rs.getInt("quantityInterns");
                    Date postingDate = rs.getDate("postingDate");
                    Date expirationDate = rs.getDate("expirationDate");
                    boolean schoolConfirm = rs.getBoolean("school_Confirm");
                    int statusPost = rs.getInt("statusPost");
                    String companyID = rs.getString("companyID");
                    int majorID = rs.getInt("majorID");
                    String vacancy = rs.getNString("vacancy");

                    TblCompanyDAO companyDAO = new TblCompanyDAO();
                    TblCompanyDTO company = companyDAO.getCompany(companyID);

                    TblMajorDAO majorDAO = new TblMajorDAO();
                    TblMajorDTO major = majorDAO.getMajor(majorID);

                    companyPost = new TblCompany_PostDTO();
                    companyPost.setPostID(postID);
                    companyPost.setTitle_Post(title_Post);
                    companyPost.setJob_Description(job_Description);
                    companyPost.setJob_Requirement(job_Requirement);
                    companyPost.setRemuneration(remuneration);
                    companyPost.setQuantityIterns(quantityInterns);
                    companyPost.setWorkLocation(workLocation);
                    companyPost.setPostingDate(postingDate);
                    companyPost.setExpirationDate(expirationDate);
                    LocalDate timeDay = LocalDate.now();
                    DateTimeFormatter dayFormat
                            = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    // convert String to date type
                    java.util.Date currentDate = Date.valueOf(timeDay.format(dayFormat));
                    if (quantityInterns == 0 || expirationDate.before(currentDate)) {
                        companyPost.setStatusPost(3);
                        companyPost.setSchool_confirm(false);
                    } else {
                        companyPost.setStatusPost(statusPost);
                        companyPost.setSchool_confirm(schoolConfirm);
                    }

                    companyPost.setCompany(company);
                    companyPost.setMajor(major);
                    companyPost.setVacancy(vacancy);
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
        return companyPost;
    }

    //lay cac bai post theo company ID
    public void getCompanyPostByCompanyID(String companyID) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        TblCompany_PostDTO companyPost = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT postID, title_Post, job_Description, "
                        + "job_Requirement, remuneration, workLocation, "
                        + "quantityInterns, postingDate, expirationDate, "
                        + "school_Confirm, statusPost, companyID, majorID, vacancy "
                        + "FROM tblCompany_Post "
                        + "WHERE companyID = ? "
                        + "ORDER BY statusPost ";
                stm = con.prepareStatement(sql);
                stm.setString(1, companyID);

                rs = stm.executeQuery();

                while (rs.next()) {
                    int postID = rs.getInt("postID");
                    String title_Post = rs.getNString("title_Post");
                    String job_Description = rs.getNString("job_Description");
                    String job_Requirement = rs.getNString("job_Requirement");
                    String remuneration = rs.getNString("remuneration");

                    String workLocation = rs.getNString("workLocation");
                    int quantityInterns = rs.getInt("quantityInterns");
                    Date postingDate = rs.getDate("postingDate");
                    Date expirationDate = rs.getDate("expirationDate");
                    boolean schoolConfirm = rs.getBoolean("school_Confirm");
                    int statusPost = rs.getInt("statusPost");
                    companyID = rs.getString("companyID");
                    int majorID = rs.getInt("majorID");
                    String vacancy = rs.getNString("vacancy");

                    TblCompanyDAO companyDAO = new TblCompanyDAO();
                    TblCompanyDTO company = companyDAO.getCompany(companyID);

                    TblMajorDAO majorDAO = new TblMajorDAO();
                    TblMajorDTO major = majorDAO.getMajor(majorID);

                    companyPost = new TblCompany_PostDTO();
                    companyPost.setPostID(postID);
                    companyPost.setTitle_Post(title_Post);
                    companyPost.setJob_Description(job_Description);
                    companyPost.setJob_Requirement(job_Requirement);
                    companyPost.setRemuneration(remuneration);
                    companyPost.setQuantityIterns(quantityInterns);
                    companyPost.setWorkLocation(workLocation);
                    companyPost.setPostingDate(postingDate);
                    companyPost.setExpirationDate(expirationDate);
                    LocalDate timeDay = LocalDate.now();
                    DateTimeFormatter dayFormat
                            = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    // convert String to date type
                    java.util.Date currentDate = Date.valueOf(timeDay.format(dayFormat));
                    if (quantityInterns == 0 || expirationDate.before(currentDate)) {
                        boolean check = updateStatusCompanyPostAsAdmin(postID, "0", 3);
                        if (check) {
                            companyPost.setStatusPost(statusPost);
                            companyPost.setSchool_confirm(schoolConfirm);
//                        }
//                        companyPost.setStatusPost(3);
//                        companyPost.setSchool_confirm(false);
                        }
                    } else {
                        companyPost.setStatusPost(statusPost);
                        companyPost.setSchool_confirm(schoolConfirm);
                    }

                    companyPost.setCompany(company);
                    companyPost.setMajor(major);
                    companyPost.setVacancy(vacancy);
                    if (companyPostByFilter == null) {
                        companyPostByFilter = new ArrayList<>();
                    }
                    companyPostByFilter.add(companyPost);
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

    //LAY TAT CA CAC BAI POST AS ADMIN 
    public void getListPost(int semesterID) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT cp.postID, cp.title_Post, "
                        + " cp.postingDate, cp.quantityInterns, cp.job_Description, cp.job_Requirement, cp.remuneration, "
                        + "cp.expirationDate, cp.school_confirm, cp.statusPost, cp.workLocation, cp.vacancy, "
                        + "m.majorName, ac.name, ac.avatar \n"
                        + "FROM tblCompany_Post AS cp INNER JOIN tblMajor AS m ON (cp.majorID = m.majorID) \n"
                        + " INNER JOIN tblCompany AS com ON (cp.companyID = com.companyID) "
                        + "INNER JOIN tblAccount AS ac ON (com.username = ac.username) "
                        + "WHERE cp.postingDate between ? and ? "
                        + "ORDER BY cp.statusPost ";
                TblSemesterDAO semesterDAO = new TblSemesterDAO();
                TblSemesterDTO semesterDTO = semesterDAO.getSemesterByID(semesterID);
                Date starDate = semesterDTO.getStartDate();
                Date endDate = semesterDTO.getEndDate();

                stm = con.prepareCall(sql);
                stm.setDate(1, starDate);
                stm.setDate(2, endDate);
                rs = stm.executeQuery();
                while (rs.next()) {
                    int postID = rs.getInt("postID");
                    String title_Post = rs.getNString("title_Post");
                    Date postingDate = rs.getDate("postingDate");
                    Date expirationDate = rs.getDate("expirationDate");
                    LocalDate timeDay = LocalDate.now();
                    DateTimeFormatter dayFormat
                            = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    // convert String to date type
                    java.util.Date currentDate = Date.valueOf(timeDay.format(dayFormat));
//                    if (expirationDate.before(currentDate)) {
//                        continue;
//                    }
                    int quantityInterns = rs.getInt("quantityInterns");
//                    if (quantityInterns == 0) {
//                        continue;
//                    }
                    boolean school_confirm = rs.getBoolean("school_confirm");
                    int statusPost = rs.getInt("statusPost");
                    String job_Requirement = rs.getNString("job_Requirement");
                    String job_Description = rs.getNString("job_Description");
                    String remuneration = rs.getNString("remuneration");
                    String vacancy = rs.getNString("vacancy");

                    String workLocation = rs.getNString("workLocation");
                    String majorName = rs.getNString("majorName");
                    String companyName = rs.getNString("name");
                    String avatar = rs.getString("avatar");

                    TblCompany_PostDTO dto = new TblCompany_PostDTO();
                    dto.setPostID(postID);
                    dto.setTitle_Post(title_Post);
                    dto.setJob_Description(job_Description);
                    dto.setJob_Requirement(job_Requirement);
                    dto.setRemuneration(remuneration);
                    dto.setPostingDate(postingDate);
                    dto.setWorkLocation(workLocation);
                    dto.setMajorName(majorName);
                    dto.setVacancy(vacancy);
                    if (quantityInterns == 0 || expirationDate.before(currentDate)) {
                        dto.setStatusPost(3);
                        dto.setSchool_confirm(false);
                    } else {
                        dto.setStatusPost(statusPost);
                        dto.setSchool_confirm(school_confirm);
                    }

                    TblAccountDTO account = new TblAccountDTO();
                    account.setName(companyName);
                    account.setAvatar(avatar);

                    TblCompanyDTO company = new TblCompanyDTO();
                    company.setAccount(account);

                    dto.setCompany(company);
                    if (companyPostListAdminPage == null) {
                        companyPostListAdminPage = new ArrayList<>();
                    }
                    companyPostListAdminPage.add(dto);

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

    public boolean updateCompanyPostAsCompany(int postID, String tilte_Post, int majorID,
            int quantityInterns, Date expirationDate, String workLocation, String job_Description,
            String job_Requirement, String remuneration, String vacancy, boolean school_confirm, int statusPost) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "UPDATE tblCompany_Post "
                        + "set title_Post = ?, majorID = ?, quantityInterns = ?, expirationDate = ?, workLocation = ?, "
                        + "job_Description = ?, job_Requirement = ?, remuneration = ? , vacancy = ?, school_confirm = ? , statusPost = ? "
                        + "WHERE postID = ?";
                stm = con.prepareStatement(sql);
                stm.setNString(1, tilte_Post);
                stm.setInt(2, majorID);
                stm.setInt(3, quantityInterns);
                stm.setDate(4, expirationDate);
                stm.setNString(5, workLocation);
                stm.setNString(6, job_Description);
                stm.setNString(7, job_Requirement);
                stm.setNString(8, remuneration);
                stm.setNString(9, vacancy);
                LocalDate timeDay = LocalDate.now();
                DateTimeFormatter dayFormat
                        = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                // convert String to date type
                java.util.Date currentDate = Date.valueOf(timeDay.format(dayFormat));
                if (quantityInterns == 0 || expirationDate.before(currentDate)) {
                    stm.setBoolean(10, false);
                    stm.setInt(11, 3);
                } else {
                    stm.setBoolean(10, false);
                    stm.setInt(11, 1);
                }

                stm.setInt(12, postID);

                int effectRows = stm.executeUpdate();

                if (effectRows > 0) {
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

    public boolean updateStatusCompanyPostAsAdmin(int postID, String school_confirm,
            int statusPost)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "update tblCompany_Post "
                        + "set school_confirm = ? , statusPost = ? "
                        + "where postID = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, school_confirm);
                stm.setInt(2, statusPost);
                stm.setInt(3, postID);

                int rows = stm.executeUpdate();
                if (rows > 0) {
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

    public List<TblCompany_PostDTO> getTypesPost(String statusPost, String companyID) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        List<TblCompany_PostDTO> listPosts = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT postID, title_Post, job_Description, "
                        + "job_Requirement, remuneration, workLocation, "
                        + "quantityInterns, postingDate, expirationDate, "
                        + "school_Confirm, statusPost, companyID, majorID, vacancy "
                        + "FROM tblCompany_Post "
                        + "WHERE statusPost = ? and companyID = ? ";
                stm = con.prepareStatement(sql);
                if ("Active".equals(statusPost.trim())) {
                    stm.setInt(1, 2);
                } else if ("Inactive".equals(statusPost.trim())) {
                    stm.setInt(1, 0);
                } else if ("Pending".equals(statusPost.trim())) {
                    stm.setInt(1, 1);
                } else if ("Expired".equals(statusPost.trim())) {
                    stm.setInt(1, 3);
                }

                stm.setString(2, companyID);
                rs = stm.executeQuery();

                while (rs.next()) {
                    int postID = rs.getInt("postID");
                    String title_Post = rs.getNString("title_Post");
                    String job_Description = rs.getNString("job_Description");
                    String job_Requirement = rs.getNString("job_Requirement");
                    String remuneration = rs.getNString("remuneration");

                    String workLocation = rs.getNString("workLocation");
                    int quantityInterns = rs.getInt("quantityInterns");
                    Date postingDate = rs.getDate("postingDate");
                    Date expirationDate = rs.getDate("expirationDate");
                    boolean schoolConfirm = rs.getBoolean("school_Confirm");
                    int status = rs.getInt("statusPost");
                    String company_ID = rs.getString("companyID");
                    int majorID = rs.getInt("majorID");
                    String vacancy = rs.getNString("vacancy");

                    TblCompanyDAO companyDAO = new TblCompanyDAO();
                    TblCompanyDTO company = companyDAO.getCompany(company_ID);

                    TblMajorDAO majorDAO = new TblMajorDAO();
                    TblMajorDTO major = majorDAO.getMajor(majorID);

                    TblCompany_PostDTO companyPost = new TblCompany_PostDTO();
                    companyPost.setPostID(postID);
                    companyPost.setTitle_Post(title_Post);
                    companyPost.setJob_Description(job_Description);
                    companyPost.setJob_Requirement(job_Requirement);
                    companyPost.setRemuneration(remuneration);
                    companyPost.setQuantityIterns(quantityInterns);
                    companyPost.setWorkLocation(workLocation);
                    companyPost.setPostingDate(postingDate);
                    companyPost.setExpirationDate(expirationDate);
                    LocalDate timeDay = LocalDate.now();
                    DateTimeFormatter dayFormat
                            = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    // convert String to date type
                    java.util.Date currentDate = Date.valueOf(timeDay.format(dayFormat));
                    if (quantityInterns == 0 || expirationDate.before(currentDate)) {
                        companyPost.setStatusPost(3);
                        companyPost.setSchool_confirm(false);
                    } else {
                        companyPost.setStatusPost(status);
                        companyPost.setSchool_confirm(schoolConfirm);
                    }

                    companyPost.setCompany(company);
                    companyPost.setMajor(major);
                    companyPost.setVacancy(vacancy);

                    if (listPosts == null) {
                        listPosts = new ArrayList<>();
                    }

                    listPosts.add(companyPost);
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
        return listPosts;
    }
    // get all post of 1 company by companyID

    public List<TblCompany_PostDTO> getAllPostByCompanyID(String companyID) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        List<TblCompany_PostDTO> listPost = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT postID, title_Post, job_Description, job_Requirement, remuneration, workLocation, "
                        + "quantityInterns, postingDate, expirationDate, school_confirm, statusPost, companyID, majorID, vacancy "
                        + "FROM tblCompany_Post "
                        + "WHERE companyID = ? ";
                stm = con.prepareStatement(sql);
                stm.setString(1, companyID);

                rs = stm.executeQuery();
                while (rs.next()) {
                    TblCompany_PostDTO companyPostDTO = new TblCompany_PostDTO();

                    int postID = rs.getInt("postID");
                    String title_Post = rs.getString("title_Post");
                    String job_Description = rs.getString("job_Description");
                    String job_Requirement = rs.getString("job_Requirement");
                    String remuneration = rs.getString("remuneration");
                    String workLocation = rs.getString("workLocation");
                    int quantityIterns = rs.getInt("quantityInterns");
                    Date postingDate = rs.getDate("postingDate");
                    Date expirationDate = rs.getDate("expirationDate");
                    boolean school_confirm = rs.getBoolean("school_confirm");
                    int statusPost = rs.getInt("statusPost");
                    String vacancy = rs.getString("vacancy");

                    TblCompanyDAO companyDAO = new TblCompanyDAO();
                    TblCompanyDTO companyDTO = companyDAO.getCompany(companyID);

                    TblMajorDAO majorDAO = new TblMajorDAO();
                    TblMajorDTO major = majorDAO.getMajor(rs.getInt("majorID"));

                    companyPostDTO.setPostID(postID);
                    companyPostDTO.setTitle_Post(title_Post);
                    companyPostDTO.setJob_Description(job_Description);
                    companyPostDTO.setJob_Requirement(job_Requirement);
                    companyPostDTO.setRemuneration(remuneration);
                    companyPostDTO.setWorkLocation(workLocation);
                    companyPostDTO.setQuantityIterns(quantityIterns);
                    companyPostDTO.setPostingDate(postingDate);
                    companyPostDTO.setExpirationDate(expirationDate);
                    companyPostDTO.setSchool_confirm(school_confirm);
                    companyPostDTO.setStatusPost(statusPost);
                    companyPostDTO.setVacancy(vacancy);

                    companyPostDTO.setCompany(companyDTO);
                    companyPostDTO.setMajor(major);

                    if (listPost == null) {
                        listPost = new ArrayList<>();
                    }
                    listPost.add(companyPostDTO);
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
        return listPost;
    }

    public boolean createNewCompanyPost(String companyID, int majorID, String tiltePost,
            String job_Description, String job_Requirement, String remuneration,
            String workLocation, int quantityInterns, Date postDate, Date expirationDate,
            int statusPost, String vacancy)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBHelper.makeConnection();

            if (con != null) {
                String sql = "INSERT INTO tblCompany_Post (title_Post, job_Description, job_Requirement, "
                        + "remuneration, workLocation, quantityInterns, postingDate, expirationDate, "
                        + "statusPost, companyID, majorID, vacancy ) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
                stm = con.prepareStatement(sql);
                stm.setNString(1, tiltePost);
                stm.setNString(2, job_Description);
                stm.setNString(3, job_Requirement);
                stm.setNString(4, remuneration);
                stm.setNString(5, workLocation);
                stm.setInt(6, quantityInterns);
                stm.setDate(7, postDate);
                stm.setDate(8, expirationDate);
                stm.setInt(9, statusPost);
                stm.setString(10, companyID);
                stm.setInt(11, majorID);
                stm.setNString(12, vacancy);

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
    // update quantityInterns By postID

    public boolean updateQuantityInterns(int postID) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "UPDATE tblCompany_Post "
                        + "SET quantityInterns = quantityInterns - ? "
                        + "WHERE postID = ? ";
                stm = con.prepareStatement(sql);
                stm.setInt(1, 1);
                stm.setInt(2, postID);

                int effectRows = stm.executeUpdate();
                if (effectRows > 0) {
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

    public List<TblCompany_PostDTO> getListExpirationPost() throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        List<TblCompany_PostDTO> listExpirationPost = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT postID, expirationDate, statusPost "
                        + "FROM tblCompany_Post ";
                stm = con.prepareStatement(sql);
                rs = stm.executeQuery();
                while (rs.next()) {
                    int postID = rs.getInt("postID");
                    Date expirationDate = rs.getDate("expirationDate");
                    int statusPost = rs.getInt("statusPost");
                    LocalDate currentDate = LocalDate.now();
                    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    Date today = Date.valueOf(currentDate.format(dateFormat));

                    if (expirationDate.before(today) && statusPost != 3) {
                        TblCompany_PostDTO companyExpirationPost = new TblCompany_PostDTO();
                        companyExpirationPost.setPostID(postID);
                        companyExpirationPost.setExpirationDate(expirationDate);

                        if (listExpirationPost == null) {
                            listExpirationPost = new ArrayList<>();
                        }
                        listExpirationPost.add(companyExpirationPost);
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
        return listExpirationPost;
    }

    public void updateStatusForExpirationPost(int postID) throws SQLException, NamingException {
        Connection con  = null;
        PreparedStatement stm = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "UPDATE tblCompany_Post "
                        + "SET statusPost = ? "
                        + "WHERE postID = ? ";
                stm = con.prepareStatement(sql);
                stm.setInt(1, 3);
                stm.setInt(2, postID);
                
                stm.executeUpdate();
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
    public void updateStatusForExpirationPost(int postID, int statusPost) throws SQLException, NamingException {
        Connection con  = null;
        PreparedStatement stm = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "UPDATE tblCompany_Post "
                        + "SET statusPost = ? "
                        + "WHERE postID = ? ";
                stm = con.prepareStatement(sql);
                stm.setInt(1, statusPost);
                stm.setInt(2, postID);
                
                stm.executeUpdate();
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }       
}
