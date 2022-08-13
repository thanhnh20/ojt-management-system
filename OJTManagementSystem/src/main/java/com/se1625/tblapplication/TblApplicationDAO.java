/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.se1625.tblapplication;

import com.se1625.tblaccount.TblAccountDAO;
import com.se1625.tblaccount.TblAccountDTO;
import com.se1625.tblcompany.TblCompanyDAO;
import com.se1625.tblcompany.TblCompanyDTO;
import com.se1625.tblcompany_post.TblCompany_PostDAO;
import com.se1625.tblcompany_post.TblCompany_PostDTO;
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
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author Thai Quoc Toan <toantqse151272@fpt.edu.vn>
 */
public class TblApplicationDAO implements Serializable {

    private List<TblApplicationDTO> listApplication;

    public List<TblApplicationDTO> getListApplication() {
        return listApplication;
    }

    public void getApplicationToWriteExcel(int semesterID) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        TblStudentDAO studentDAO = new TblStudentDAO();
        TblCompany_PostDAO companyPostDAO = new TblCompany_PostDAO();
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT studentCode, postID, grade, evaluation, applicationID, is_Pass "
                        + "FROM tblApplication "
                        + "WHERE student_Confirm = ? and school_Confirm = ? and company_Confirm = ? and semesterID = ? ";
                stm = con.prepareStatement(sql);
                stm.setBoolean(1, true);
                stm.setBoolean(2, true);
                stm.setBoolean(3, true);
                stm.setInt(4, semesterID);
                rs = stm.executeQuery();
                while (rs.next()) {
                    String studentCode = rs.getString("studentCode");
                    int postID = rs.getInt("postID");
                    float grade = rs.getFloat("grade");
                    String evaluation = rs.getNString("evaluation");
                    int applicationID = rs.getInt("applicationID");
                    int isPass = rs.getInt("is_Pass");

                    if (grade >= 0 && evaluation != null) {
                        TblStudentDTO student = studentDAO.getStudentInformationExport(studentCode);
                        TblCompany_PostDTO companyPost = companyPostDAO.getCompanyPost(postID);

                        TblApplicationDTO application = new TblApplicationDTO();
                        application.setApplicationID(applicationID);
                        application.setGrade(grade);
                        application.setIsPass(isPass);
                        application.setEvaluation(evaluation);
                        application.setCompanyPost(companyPost);
                        application.setStudent(student);

                        if (listApplication == null) {
                            listApplication = new ArrayList<>();
                        }
                        listApplication.add(application);
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

    public boolean addApplication(TblApplicationDTO application) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "INSERT INTO tblApplication (attachmentPath, expected_Job, technology, experience, "
                        + "foreign_Language, otherSkills, studentCode, postID, student_Confirm, semesterID) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
                stm = con.prepareStatement(sql);
                stm.setString(1, application.getAttachmentPath());
                stm.setNString(2, application.getExpected_job());
                stm.setNString(3, application.getTechnology());
                stm.setNString(4, application.getExperience());
                stm.setNString(5, application.getForeign_Language());
                stm.setNString(6, application.getOtherSkills());
                stm.setString(7, application.getStudent().getStudentCode());
                stm.setInt(8, application.getCompanyPost().getPostID());
                stm.setBoolean(9, true);
                stm.setInt(10, application.getSemester().getSemesterID());

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

    public TblApplicationDTO getApplication(String studentCode, int semesterID) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        TblStudentDAO studentDAO = new TblStudentDAO();
        TblCompany_PostDAO companyPostDAO = new TblCompany_PostDAO();
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT studentCode, postID, grade, evaluation, applicationID, is_Pass "
                        + "FROM tblApplication "
                        + "WHERE student_Confirm = ? and school_Confirm = ? "
                        + "and company_Confirm = ? and studentCode = ? and semesterID = ? ";
                stm = con.prepareStatement(sql);
                stm.setBoolean(1, true);
                stm.setInt(2, 1);
                stm.setInt(3, 1);
                stm.setString(4, studentCode);
                stm.setInt(5, semesterID);
                rs = stm.executeQuery();
                if (rs.next()) {
                    int postID = rs.getInt("postID");
                    float grade = rs.getFloat("grade");
                    String evaluation = rs.getNString("evaluation");
                    int applicationID = rs.getInt("applicationID");
                    int isPass = rs.getInt("is_Pass");

                    TblStudentDTO student = studentDAO.getStudentInformation(studentCode);
                    TblCompany_PostDTO companyPost = companyPostDAO.getCompanyPost(postID);

                    TblApplicationDTO application = new TblApplicationDTO();
                    application.setApplicationID(applicationID);
                    application.setEvaluation(evaluation);
                    application.setIsPass(isPass);
                    application.setCompanyPost(companyPost);
                    application.setStudent(student);
                    application.setGrade(grade);

                    return application;
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

    public TblApplicationDTO getApplication(String studentCode) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        TblStudentDAO studentDAO = new TblStudentDAO();
        TblCompany_PostDAO companyPostDAO = new TblCompany_PostDAO();
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT studentCode, postID, grade, evaluation, applicationID, is_Pass, semesterID "
                        + "FROM tblApplication "
                        + "WHERE student_Confirm = ? and school_Confirm = ? "
                        + "and company_Confirm = ? and studentCode = ? ";
                stm = con.prepareStatement(sql);
                stm.setBoolean(1, true);
                stm.setInt(2, 1);
                stm.setInt(3, 1);
                stm.setString(4, studentCode);
                rs = stm.executeQuery();
                if (rs.next()) {
                    int postID = rs.getInt("postID");
                    float grade = rs.getFloat("grade");
                    String evaluation = rs.getNString("evaluation");
                    int applicationID = rs.getInt("applicationID");
                    //boolean isPass = rs.getBoolean("is_Pass");
                    int semesterID = rs.getInt("semesterID");
                    int isPass = rs.getInt("is_Pass");

                    TblSemesterDAO semesterDAO = new TblSemesterDAO();
                    TblSemesterDTO currentSemester = semesterDAO.getCurrentSemester();
                    if (semesterID == currentSemester.getSemesterID()) {
                        TblStudentDTO student = studentDAO.getStudentInformation(studentCode);
                        TblCompany_PostDTO companyPost = companyPostDAO.getCompanyPost(postID);

                        TblApplicationDTO application = new TblApplicationDTO();
                        application.setApplicationID(applicationID);
                        application.setEvaluation(evaluation);
                        application.setIsPass(isPass);
                        application.setCompanyPost(companyPost);
                        application.setStudent(student);
                        application.setGrade(grade);

                        return application;
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
        return null;
    }

    public boolean updateApplication(TblApplicationDTO application) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "UPDATE tblApplication (attachmentPath, expected_Job, technology, experience, "
                        + "foreign_Language, otherSkills, studentCode, postID ) "
                        + "SET (?, ?, ?, ?, ?, ?, ?, ?) "
                        + "WHERE ? ";
                stm = con.prepareStatement(sql);
                stm.setString(1, application.getAttachmentPath());
                stm.setNString(2, application.getExpected_job());
                stm.setNString(3, application.getTechnology());
                stm.setNString(4, application.getExperience());
                stm.setNString(5, application.getForeign_Language());
                stm.setNString(6, application.getOtherSkills());
                stm.setString(7, application.getStudent().getStudentCode());
                stm.setInt(8, application.getCompanyPost().getPostID());
                stm.setInt(9, application.getApplicationID());

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

    public List<TblApplicationDTO> getApplicationOfAStudent(TblStudentDTO student) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        List<TblApplicationDTO> listApplicationOfAStudent = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT applicationID, attachmentPath, expected_Job, "
                        + "technology, experience, foreign_Language, otherSkills, "
                        + "evaluation, grade, is_Pass, student_Confirm, school_Confirm, "
                        + "company_Confirm, studentCode, postID, semesterID "
                        + "FROM tblApplication "
                        + "WHERE studentCode = ? ";
                stm = con.prepareStatement(sql);
                stm.setString(1, student.getStudentCode());

                rs = stm.executeQuery();

                while (rs.next()) {
                    int applicationID = rs.getInt("applicationID");
                    String attachmentPath = rs.getString("attachmentPath");
                    String expectedJob = rs.getNString("expected_Job");
                    String technology = rs.getNString("technology");
                    String experience = rs.getNString("experience");
                    String foreign_Language = rs.getNString("foreign_Language");
                    String otherSkills = rs.getNString("otherSkills");
                    String evaluation = rs.getNString("evaluation");
                    float grade = rs.getFloat("grade");
                    int isPass = rs.getInt("is_Pass");
                    boolean studentConfirm = rs.getBoolean("student_Confirm");
                    int schoolConfirm = rs.getInt("school_Confirm");
                    int companyConfirm = rs.getInt("company_Confirm");
                    int semesterID = rs.getInt("semesterID");

                    int postID = rs.getInt("postID");
                    TblCompany_PostDAO companyPostDAO = new TblCompany_PostDAO();
                    TblCompany_PostDTO companyPost = companyPostDAO.getCompanyPost(postID);

                    TblApplicationDTO applicationDTO = new TblApplicationDTO();
                    applicationDTO.setApplicationID(applicationID);
                    applicationDTO.setAttachmentPath(attachmentPath);
                    applicationDTO.setExpected_job(expectedJob);
                    applicationDTO.setTechnology(technology);
                    applicationDTO.setExperience(experience);
                    applicationDTO.setForeign_Language(foreign_Language);
                    applicationDTO.setOtherSkills(otherSkills);
                    applicationDTO.setEvaluation(evaluation);
                    applicationDTO.setGrade(grade);
                    applicationDTO.setIsPass(isPass);
                    applicationDTO.setStudentConfirm(studentConfirm);
                    applicationDTO.setSchoolConfirm(schoolConfirm);
                    applicationDTO.setCompanyConfirm(companyConfirm);
                    applicationDTO.setStudent(student);
                    applicationDTO.setCompanyPost(companyPost);

                    TblSemesterDAO semesterDAO = new TblSemesterDAO();
                    TblSemesterDTO semester = semesterDAO.getSemesterByID(semesterID);
                    applicationDTO.setSemester(semester);
                    if (listApplicationOfAStudent == null) {
                        listApplicationOfAStudent = new ArrayList<>();
                    }

                    listApplicationOfAStudent.add(applicationDTO);
                }
                return listApplicationOfAStudent;
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

    public List<TblApplicationDTO> getListByPage(List<TblApplicationDTO> list, int start, int end) {
        List<TblApplicationDTO> listPage = new ArrayList<>();
        for (int i = start; i < end; i++) {
            listPage.add(list.get(i));
        }
        return listPage;
    }

    public boolean updateApplyCV(int applicationID) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "UPDATE tblApplication "
                        + "SET student_Confirm = ? "
                        + "WHERE applicationID = ? ";
                stm = con.prepareStatement(sql);
                stm.setBoolean(1, false);
                stm.setInt(2, applicationID);

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

    public TblApplicationDTO getApplication(int applicationCode) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        TblStudentDAO studentDAO = new TblStudentDAO();
        TblCompany_PostDAO companyPostDAO = new TblCompany_PostDAO();
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT studentCode, postID, grade, evaluation, applicationID, is_Pass, "
                        + "student_Confirm, school_Confirm, company_Confirm "
                        + "FROM tblApplication "
                        + "WHERE applicationId = ? ";
                stm = con.prepareStatement(sql);
                stm.setInt(1, applicationCode);

                rs = stm.executeQuery();
                if (rs.next()) {

                    int postID = rs.getInt("postID");
                    float grade = rs.getFloat("grade");
                    String evaluation = rs.getNString("evaluation");
                    int applicationID = rs.getInt("applicationID");
                    int isPass = rs.getInt("is_Pass");
                    String studentCode = rs.getString("studentCode");
                    boolean studentConfirm = rs.getBoolean("student_Confirm");
                    int schoolConfirm = rs.getInt("school_Confirm");
                    int company_Confirm = rs.getInt("company_Confirm");

                    TblStudentDTO student = studentDAO.getStudentInformation(studentCode);
                    TblCompany_PostDTO companyPost = companyPostDAO.getCompanyPost(postID);

                    TblApplicationDTO application = new TblApplicationDTO();
                    application.setApplicationID(applicationID);
                    application.setEvaluation(evaluation);
                    application.setIsPass(isPass);
                    application.setCompanyPost(companyPost);
                    application.setStudent(student);
                    application.setGrade(grade);
                    application.setStudentConfirm(studentConfirm);
                    application.setCompanyConfirm(company_Confirm);
                    application.setSchoolConfirm(schoolConfirm);

                    return application;
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

    public List<TblApplicationDTO> getApplicationByFilter(TblStudentDTO student,
            String companyName, String nameTypeJob, String nameLocation, String nameStatus)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        List<TblApplicationDTO> listApplicationByFilter = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT app.school_Confirm, app.student_Confirm, app.company_Confirm, app.applicationID, "
                        + "cp.title_Post, cp.workLocation, cp.expirationDate, cp.postID, "
                        + "ac.name "
                        + "FROM tblApplication AS app INNER JOIN tblCompany_Post AS cp ON (app.postID = cp.postID) "
                        + "INNER JOIN tblCompany as com ON (cp.companyID = com.companyID) "
                        + "INNER JOIN tblAccount as ac ON (com.username = ac.username) ";
                if ("".equals(companyName) == false && "".equals(nameTypeJob) == false
                        && "".equals(nameLocation) == false && "".equals(nameStatus) == false) {
                    sql += "WHERE app.studentCode = ? and ac.name LIKE ? and cp.title_Post LIKE ? "
                            + "and cp.workLocation LIKE ? ";
                    if (nameStatus.equals("Denied")) {
                        sql += "and app.student_Confirm = ? and (app.company_Confirm = ? or "
                                + " app.school_Confirm = ? ) ";
                        stm = con.prepareStatement(sql);
                        stm.setString(1, student.getStudentCode());
                        stm.setNString(2, "%" + companyName + "%");
                        stm.setNString(3, "%" + nameTypeJob + "%");
                        stm.setNString(4, "%" + nameLocation + "%");
                        stm.setBoolean(5, true);
                        stm.setInt(6, -1);
                        stm.setInt(7, 0);
                    } else if (nameStatus.equals("Waiting")) {
                        sql += "and app.student_Confirm = ? and (app.company_Confirm = ? or "
                                + " app.school_Confirm = ? )";
                        stm = con.prepareStatement(sql);
                        stm.setString(1, student.getStudentCode());
                        stm.setNString(2, "%" + companyName + "%");
                        stm.setNString(3, "%" + nameTypeJob + "%");
                        stm.setNString(4, "%" + nameLocation + "%");
                        stm.setBoolean(5, true);
                        stm.setInt(6, 0);
                        stm.setInt(7, -1);
                    } else if (nameStatus.equals("Success")) {
                        sql += "and app.student_Confirm = ? and app.company_Confirm = ? and "
                                + " app.school_Confirm = ? ";
                        stm = con.prepareStatement(sql);
                        stm.setString(1, student.getStudentCode());
                        stm.setNString(2, "%" + companyName + "%");
                        stm.setNString(3, "%" + nameTypeJob + "%");
                        stm.setNString(4, "%" + nameLocation + "%");
                        stm.setBoolean(5, true);
                        stm.setInt(6, 1);
                        stm.setInt(7, 1);
                    } else if (nameStatus.equals("Interviewing")) {
                        sql += "and app.student_Confirm = ? and app.company_Confirm = ? and "
                                + " app.school_Confirm = ? ";
                        stm = con.prepareStatement(sql);
                        stm.setString(1, student.getStudentCode());
                        stm.setNString(2, "%" + companyName + "%");
                        stm.setNString(3, "%" + nameTypeJob + "%");
                        stm.setNString(4, "%" + nameLocation + "%");
                        stm.setBoolean(5, true);
                        stm.setInt(6, 2);
                        stm.setInt(7, 1);
                    } else if (nameStatus.equals("Failed")) {
                        sql += "and app.student_Confirm = ? and app.company_Confirm = ? and "
                                + " app.school_Confirm = ? ";
                        stm = con.prepareStatement(sql);
                        stm.setString(1, student.getStudentCode());
                        stm.setNString(2, "%" + companyName + "%");
                        stm.setNString(3, "%" + nameTypeJob + "%");
                        stm.setNString(4, "%" + nameLocation + "%");
                        stm.setBoolean(5, true);
                        stm.setInt(6, -2);
                        stm.setInt(7, 1);
                    } else if (nameStatus.equals("Canceled")) {
                        sql += "and app.student_Confirm = ? ";
                        stm = con.prepareStatement(sql);
                        stm.setString(1, student.getStudentCode());
                        stm.setNString(2, "%" + companyName + "%");
                        stm.setNString(3, "%" + nameTypeJob + "%");
                        stm.setNString(4, "%" + nameLocation + "%");
                        stm.setBoolean(5, false);
                    }
                }

                if (companyName.trim().isEmpty() == false && nameTypeJob.trim().isEmpty() == false
                        && nameLocation.trim().isEmpty() == false && "".equals(nameStatus) == true) {
                    sql += "WHERE app.studentCode = ? and ac.name LIKE ? and cp.title_Post LIKE ? "
                            + "and cp.workLocation LIKE ? ";
                    stm = con.prepareStatement(sql);
                    stm.setString(1, student.getStudentCode());
                    stm.setNString(2, "%" + companyName + "%");
                    stm.setNString(3, "%" + nameTypeJob + "%");
                    stm.setNString(4, "%" + nameLocation + "%");
                }

                if (companyName.trim().isEmpty() == false && nameTypeJob.trim().isEmpty() == false
                        && "".equals(nameLocation) == true && nameStatus.trim().isEmpty() == false) {
                    sql += "WHERE app.studentCode = ? and ac.name LIKE ? and cp.title_Post LIKE ? ";
                    if (nameStatus.equals("Denied")) {
                        sql += "and app.student_Confirm = ? and (app.company_Confirm = ? or "
                                + " app.school_Confirm = ? )";
                        stm = con.prepareStatement(sql);
                        stm.setString(1, student.getStudentCode());
                        stm.setNString(2, "%" + companyName + "%");
                        stm.setNString(3, "%" + nameTypeJob + "%");
                        stm.setBoolean(4, true);
                        stm.setInt(5, -1);
                        stm.setInt(6, 0);
                    } else if (nameStatus.equals("Waiting")) {
                        sql += "and app.student_Confirm = ? and (app.company_Confirm = ? or "
                                + " app.school_Confirm = ?) ";
                        stm = con.prepareStatement(sql);
                        stm.setString(1, student.getStudentCode());
                        stm.setNString(2, "%" + companyName + "%");
                        stm.setNString(3, "%" + nameTypeJob + "%");
                        stm.setBoolean(4, true);
                        stm.setInt(5, 0);
                        stm.setInt(6, -1);
                    } else if (nameStatus.equals("Success")) {
                        sql += "and app.student_Confirm = ? and app.company_Confirm = ? and "
                                + " app.school_Confirm = ? ";
                        stm = con.prepareStatement(sql);
                        stm.setString(1, student.getStudentCode());
                        stm.setNString(2, "%" + companyName + "%");
                        stm.setNString(3, "%" + nameTypeJob + "%");
                        stm.setBoolean(4, true);
                        stm.setInt(5, 1);
                        stm.setInt(6, 1);
                    } else if (nameStatus.equals("Interviewing")) {
                        sql += "and app.student_Confirm = ? and (app.company_Confirm = ? or "
                                + " app.school_Confirm = ?) ";
                        stm = con.prepareStatement(sql);
                        stm.setString(1, student.getStudentCode());
                        stm.setNString(2, "%" + companyName + "%");
                        stm.setNString(3, "%" + nameTypeJob + "%");
                        stm.setBoolean(4, true);
                        stm.setInt(5, 2);
                        stm.setInt(6, 1);
                    } else if (nameStatus.equals("Failed")) {
                        sql += "and app.student_Confirm = ? and (app.company_Confirm = ? or "
                                + " app.school_Confirm = ?) ";
                        stm = con.prepareStatement(sql);
                        stm.setString(1, student.getStudentCode());
                        stm.setNString(2, "%" + companyName + "%");
                        stm.setNString(3, "%" + nameTypeJob + "%");
                        stm.setBoolean(4, true);
                        stm.setInt(5, -2);
                        stm.setInt(6, 1);
                    } else if (nameStatus.equals("Canceled")) {
                        sql += "and app.student_Confirm = ? ";
                        stm = con.prepareStatement(sql);
                        stm.setString(1, student.getStudentCode());
                        stm.setNString(2, "%" + companyName + "%");
                        stm.setNString(3, "%" + nameTypeJob + "%");
                        stm.setBoolean(4, false);
                    }
                }

                if (companyName.trim().isEmpty() == false && "".equals(nameTypeJob) == true
                        && nameLocation.trim().isEmpty() == false && nameStatus.trim().isEmpty() == false) {
                    sql += "WHERE app.studentCode = ? and ac.name LIKE ? "
                            + "and cp.workLocation LIKE ? ";

                    if (nameStatus.equals("Denied")) {
                        sql += "and app.student_Confirm = ? and (app.company_Confirm = ? or "
                                + " app.school_Confirm = ?) ";
                        stm = con.prepareStatement(sql);
                        stm.setString(1, student.getStudentCode());
                        stm.setNString(2, "%" + companyName + "%");
                        stm.setNString(3, "%" + nameLocation + "%");
                        stm.setBoolean(4, true);
                        stm.setInt(5, -1);
                        stm.setInt(6, 0);
                    } else if (nameStatus.equals("Waiting")) {
                        sql += "and app.student_Confirm = ? and (app.company_Confirm = ? or "
                                + " app.school_Confirm = ? )";
                        stm = con.prepareStatement(sql);
                        stm.setString(1, student.getStudentCode());
                        stm.setNString(2, "%" + companyName + "%");
                        stm.setNString(3, "%" + nameLocation + "%");
                        stm.setBoolean(4, true);
                        stm.setInt(5, 0);
                        stm.setInt(6, -1);
                    } else if (nameStatus.equals("Success")) {
                        sql += "and app.student_Confirm = ? and app.company_Confirm = ? and "
                                + " app.school_Confirm = ? ";
                        stm = con.prepareStatement(sql);
                        stm.setString(1, student.getStudentCode());
                        stm.setNString(2, "%" + companyName + "%");
                        stm.setNString(3, "%" + nameLocation + "%");
                        stm.setBoolean(4, true);
                        stm.setInt(5, 1);
                        stm.setInt(6, 1);
                    } else if (nameStatus.equals("Interviewing")) {
                        sql += "and app.student_Confirm = ? and app.company_Confirm = ? and "
                                + " app.school_Confirm = ? ";
                        stm = con.prepareStatement(sql);
                        stm.setString(1, student.getStudentCode());
                        stm.setNString(2, "%" + companyName + "%");
                        stm.setNString(3, "%" + nameLocation + "%");
                        stm.setBoolean(4, true);
                        stm.setInt(5, 2);
                        stm.setInt(6, 1);
                    } else if (nameStatus.equals("Failed")) {
                        sql += "and app.student_Confirm = ? and app.company_Confirm = ? and "
                                + " app.school_Confirm = ? ";
                        stm = con.prepareStatement(sql);
                        stm.setString(1, student.getStudentCode());
                        stm.setNString(2, "%" + companyName + "%");
                        stm.setNString(3, "%" + nameLocation + "%");
                        stm.setBoolean(4, true);
                        stm.setInt(5, -2);
                        stm.setInt(6, 1);
                    } else if (nameStatus.equals("Canceled")) {
                        sql += "and app.student_Confirm = ? ";
                        stm = con.prepareStatement(sql);
                        stm.setString(1, student.getStudentCode());
                        stm.setNString(2, "%" + companyName + "%");
                        stm.setNString(3, "%" + nameLocation + "%");
                        stm.setBoolean(4, false);
                    }
                }

                if ("".equals(companyName) == true && nameTypeJob.trim().isEmpty() == false
                        && nameLocation.trim().isEmpty() == false && nameStatus.trim().isEmpty() == false) {
                    sql += "WHERE app.studentCode = ? and cp.title_Post LIKE ? "
                            + "and cp.workLocation LIKE ? and ";

                    if (nameStatus.equals("Denied")) {
                        sql += "app.student_Confirm = ? and (app.company_Confirm = ? or "
                                + " app.school_Confirm = ? )";
                        stm = con.prepareStatement(sql);
                        stm.setString(1, student.getStudentCode());
                        stm.setNString(2, "%" + nameTypeJob + "%");
                        stm.setNString(3, "%" + nameLocation + "%");
                        stm.setBoolean(4, true);
                        stm.setInt(5, -1);
                        stm.setInt(6, 0);
                    } else if (nameStatus.equals("Waiting")) {
                        sql += "app.student_Confirm = ? and (app.company_Confirm = ? or "
                                + " app.school_Confirm = ? )";
                        stm = con.prepareStatement(sql);
                        stm.setString(1, student.getStudentCode());
                        stm.setNString(2, "%" + nameTypeJob + "%");
                        stm.setNString(3, "%" + nameLocation + "%");
                        stm.setBoolean(4, true);
                        stm.setInt(5, 0);
                        stm.setInt(6, -1);
                    } else if (nameStatus.equals("Success")) {
                        sql += "app.student_Confirm = ? and app.company_Confirm = ? and "
                                + " app.school_Confirm = ? ";
                        stm = con.prepareStatement(sql);
                        stm.setString(1, student.getStudentCode());
                        stm.setNString(2, "%" + nameTypeJob + "%");
                        stm.setNString(3, "%" + nameLocation + "%");
                        stm.setBoolean(4, true);
                        stm.setInt(5, 1);
                        stm.setInt(6, 1);
                    } else if (nameStatus.equals("Interviewing")) {
                        sql += "app.student_Confirm = ? and app.company_Confirm = ? and "
                                + " app.school_Confirm = ? ";
                        stm = con.prepareStatement(sql);
                        stm.setString(1, student.getStudentCode());
                        stm.setNString(2, "%" + nameTypeJob + "%");
                        stm.setNString(3, "%" + nameLocation + "%");
                        stm.setBoolean(4, true);
                        stm.setInt(5, 2);
                        stm.setInt(6, 1);
                    } else if (nameStatus.equals("Failed")) {
                        sql += "app.student_Confirm = ? and app.company_Confirm = ? and "
                                + " app.school_Confirm = ? ";
                        stm = con.prepareStatement(sql);
                        stm.setString(1, student.getStudentCode());
                        stm.setNString(2, "%" + nameTypeJob + "%");
                        stm.setNString(3, "%" + nameLocation + "%");
                        stm.setBoolean(4, true);
                        stm.setInt(5, -2);
                        stm.setInt(6, 1);
                    } else if (nameStatus.equals("Canceled")) {
                        sql += "app.student_Confirm = ? ";
                        stm = con.prepareStatement(sql);
                        stm.setString(1, student.getStudentCode());
                        stm.setNString(2, "%" + nameTypeJob + "%");
                        stm.setNString(3, "%" + nameLocation + "%");
                        stm.setBoolean(4, false);
                    }
                }

                if (companyName.trim().isEmpty() == false && nameTypeJob.trim().isEmpty() == false
                        && "".equals(nameLocation) == true && "".equals(nameStatus) == true) {
                    sql += "WHERE app.studentCode = ? and ac.name LIKE ? and cp.title_Post LIKE ? ";
                    stm = con.prepareStatement(sql);
                    stm.setString(1, student.getStudentCode());
                    stm.setNString(2, "%" + companyName + "%");
                    stm.setNString(3, "%" + nameTypeJob + "%");
                }

                if (companyName.trim().isEmpty() == false && "".equals(nameTypeJob) == true
                        && nameLocation.trim().isEmpty() == false && "".equals(nameStatus) == true) {
                    sql += "WHERE app.studentCode = ? and cp.workLocation LIKE ? and ac.name LIKE ? ";
                    stm = con.prepareStatement(sql);
                    stm.setString(1, student.getStudentCode());
                    stm.setNString(2, "%" + nameLocation + "%");
                    stm.setNString(3, "%" + companyName + "%");
                }

                if ("".equals(companyName) == true && nameTypeJob.trim().isEmpty() == false
                        && nameLocation.trim().isEmpty() == false && "".equals(nameStatus) == true) {
                    sql += "WHERE app.studentCode = ? and cp.workLocation LIKE ? and cp.title_Post LIKE ? ";
                    stm = con.prepareStatement(sql);
                    stm.setString(1, student.getStudentCode());
                    stm.setNString(2, "%" + nameLocation + "%");
                    stm.setNString(3, "%" + nameTypeJob + "%");
                }

                if (companyName.trim().isEmpty() == false && "".equals(nameTypeJob) == true
                        && "".equals(nameLocation) == true && nameStatus.trim().isEmpty() == false) {
                    sql += "WHERE app.studentCode = ? and ac.name LIKE ? and ";

                    if (nameStatus.equals("Denied")) {
                        sql += "app.student_Confirm = ? and (app.company_Confirm = ? or "
                                + " app.school_Confirm = ? )";
                        stm = con.prepareStatement(sql);
                        stm.setString(1, student.getStudentCode());
                        stm.setNString(2, "%" + companyName + "%");
                        stm.setBoolean(3, true);
                        stm.setInt(4, -1);
                        stm.setInt(5, 0);
                    } else if (nameStatus.equals("Waiting")) {
                        sql += "app.student_Confirm = ? and (app.company_Confirm = ? or "
                                + " app.school_Confirm = ? )";
                        stm = con.prepareStatement(sql);
                        stm.setString(1, student.getStudentCode());
                        stm.setNString(2, "%" + companyName + "%");
                        stm.setBoolean(3, true);
                        stm.setInt(4, 0);
                        stm.setInt(5, -1);
                    } else if (nameStatus.equals("Success")) {
                        sql += "app.student_Confirm = ? and app.company_Confirm = ? and "
                                + " app.school_Confirm = ? ";
                        stm = con.prepareStatement(sql);
                        stm.setString(1, student.getStudentCode());
                        stm.setNString(2, "%" + companyName + "%");
                        stm.setBoolean(3, true);
                        stm.setInt(4, 1);
                        stm.setInt(5, 1);
                    } else if (nameStatus.equals("Interviewing")) {
                        sql += "app.student_Confirm = ? and app.company_Confirm = ? and "
                                + " app.school_Confirm = ? ";
                        stm = con.prepareStatement(sql);
                        stm.setString(1, student.getStudentCode());
                        stm.setNString(2, "%" + companyName + "%");
                        stm.setBoolean(3, true);
                        stm.setInt(4, 2);
                        stm.setInt(5, 1);
                    } else if (nameStatus.equals("Failed")) {
                        sql += "app.student_Confirm = ? and app.company_Confirm = ? and "
                                + " app.school_Confirm = ? ";
                        stm = con.prepareStatement(sql);
                        stm.setString(1, student.getStudentCode());
                        stm.setNString(2, "%" + companyName + "%");
                        stm.setBoolean(3, true);
                        stm.setInt(4, -2);
                        stm.setInt(5, 1);
                    } else if (nameStatus.equals("Canceled")) {
                        sql += "app.student_Confirm = ? ";
                        stm = con.prepareStatement(sql);
                        stm.setString(1, student.getStudentCode());
                        stm.setNString(2, "%" + companyName + "%");
                        stm.setBoolean(3, false);
                    }
                }

                if ("".equals(companyName) == true && nameTypeJob.trim().isEmpty() == false
                        && "".equals(nameLocation) == true && nameStatus.trim().isEmpty() == false) {
                    sql += "WHERE app.studentCode = ? and cp.title_Post LIKE ? and ";

                    if (nameStatus.equals("Denied")) {
                        sql += "app.student_Confirm = ? and (app.company_Confirm = ? or "
                                + " app.school_Confirm = ? )";
                        stm = con.prepareStatement(sql);
                        stm.setString(1, student.getStudentCode());
                        stm.setNString(2, "%" + nameTypeJob + "%");
                        stm.setBoolean(3, true);
                        stm.setInt(4, -1);
                        stm.setInt(5, 0);
                    } else if (nameStatus.equals("Waiting")) {
                        sql += "app.student_Confirm = ? and (app.company_Confirm = ? or "
                                + " app.school_Confirm = ? )";
                        stm = con.prepareStatement(sql);
                        stm.setString(1, student.getStudentCode());
                        stm.setNString(2, "%" + nameTypeJob + "%");
                        stm.setBoolean(3, true);
                        stm.setInt(4, 0);
                        stm.setInt(5, -1);
                    } else if (nameStatus.equals("Success")) {
                        sql += "app.student_Confirm = ? and app.company_Confirm = ? and "
                                + " app.school_Confirm = ? ";
                        stm = con.prepareStatement(sql);
                        stm.setString(1, student.getStudentCode());
                        stm.setNString(2, "%" + nameTypeJob + "%");
                        stm.setBoolean(3, true);
                        stm.setInt(4, 1);
                        stm.setInt(5, 1);
                    } else if (nameStatus.equals("Interviewing")) {
                        sql += "app.student_Confirm = ? and app.company_Confirm = ? and "
                                + " app.school_Confirm = ? ";
                        stm = con.prepareStatement(sql);
                        stm.setString(1, student.getStudentCode());
                        stm.setNString(2, "%" + nameTypeJob + "%");
                        stm.setBoolean(3, true);
                        stm.setInt(4, 2);
                        stm.setInt(5, 1);
                    } else if (nameStatus.equals("Failed")) {
                        sql += "app.student_Confirm = ? and app.company_Confirm = ? and "
                                + " app.school_Confirm = ? ";
                        stm = con.prepareStatement(sql);
                        stm.setString(1, student.getStudentCode());
                        stm.setNString(2, "%" + nameTypeJob + "%");
                        stm.setBoolean(3, true);
                        stm.setInt(4, -2);
                        stm.setInt(5, 1);
                    } else if (nameStatus.equals("Canceled")) {
                        sql += "app.student_Confirm = ? ";
                        stm = con.prepareStatement(sql);
                        stm.setString(1, student.getStudentCode());
                        stm.setNString(2, "%" + nameTypeJob + "%");
                        stm.setBoolean(3, false);
                    }
                }

                if ("".equals(companyName) == true && "".equals(nameTypeJob) == true
                        && nameLocation.trim().isEmpty() == false && nameStatus.trim().isEmpty() == false) {
                    sql += "WHERE app.studentCode = ? and cp.workLocation LIKE ? and ";
                    if (nameStatus.equals("Denied")) {
                        sql += "app.student_Confirm = ? and (app.company_Confirm = ? or "
                                + " app.school_Confirm = ?) ";
                        stm = con.prepareStatement(sql);
                        stm.setString(1, student.getStudentCode());
                        stm.setNString(2, "%" + nameLocation + "%");
                        stm.setBoolean(3, true);
                        stm.setInt(4, -1);
                        stm.setInt(5, 0);
                    } else if (nameStatus.equals("Waiting")) {
                        sql += "app.student_Confirm = ? and (app.company_Confirm = ? or "
                                + " app.school_Confirm = ?) ";
                        stm = con.prepareStatement(sql);
                        stm.setString(1, student.getStudentCode());
                        stm.setNString(2, "%" + nameLocation + "%");
                        stm.setBoolean(3, true);
                        stm.setInt(4, 0);
                        stm.setInt(5, -1);
                    } else if (nameStatus.equals("Success")) {
                        sql += "app.student_Confirm = ? and app.company_Confirm = ? and "
                                + " app.school_Confirm = ? ";
                        stm = con.prepareStatement(sql);
                        stm.setString(1, student.getStudentCode());
                        stm.setNString(2, "%" + nameLocation + "%");
                        stm.setBoolean(3, true);
                        stm.setInt(4, 1);
                        stm.setInt(5, 1);
                    } else if (nameStatus.equals("Interviewing")) {
                        sql += "app.student_Confirm = ? and app.company_Confirm = ? and "
                                + " app.school_Confirm = ? ";
                        stm = con.prepareStatement(sql);
                        stm.setString(1, student.getStudentCode());
                        stm.setNString(2, "%" + nameLocation + "%");
                        stm.setBoolean(3, true);
                        stm.setInt(4, 2);
                        stm.setInt(5, 1);
                    } else if (nameStatus.equals("Failed")) {
                        sql += "app.student_Confirm = ? and app.company_Confirm = ? and "
                                + " app.school_Confirm = ? ";
                        stm = con.prepareStatement(sql);
                        stm.setString(1, student.getStudentCode());
                        stm.setNString(2, "%" + nameLocation + "%");
                        stm.setBoolean(3, true);
                        stm.setInt(4, -2);
                        stm.setInt(5, 1);
                    } else if (nameStatus.equals("Canceled")) {
                        sql += "app.student_Confirm = ? ";
                        stm = con.prepareStatement(sql);
                        stm.setString(1, student.getStudentCode());
                        stm.setNString(2, "%" + nameLocation + "%");
                        stm.setBoolean(3, false);
                    }
                }

                if ("".equals(companyName) == true && "".equals(nameTypeJob) == true
                        && "".equals(nameLocation) == true && nameStatus.trim().isEmpty() == false) {
                    sql += "WHERE app.studentCode = ? and ";

                    if (nameStatus.equals("Denied")) {
                        sql += "app.student_Confirm = ? and (app.company_Confirm = ? or "
                                + " app.school_Confirm = ? )";
                        stm = con.prepareStatement(sql);
                        stm.setString(1, student.getStudentCode());
                        stm.setBoolean(2, true);
                        stm.setInt(3, -1);
                        stm.setInt(4, 0);
                    } else if (nameStatus.equals("Waiting")) {
                        sql += "app.student_Confirm = ? and (app.company_Confirm = ? or "
                                + " app.school_Confirm = ? )";
                        stm = con.prepareStatement(sql);
                        stm.setString(1, student.getStudentCode());
                        stm.setBoolean(2, true);
                        stm.setInt(3, 0);
                        stm.setInt(4, -1);
                    } else if (nameStatus.equals("Success")) {
                        sql += "app.student_Confirm = ? and app.company_Confirm = ? and "
                                + " app.school_Confirm = ? ";
                        stm = con.prepareStatement(sql);
                        stm.setString(1, student.getStudentCode());
                        stm.setBoolean(2, true);
                        stm.setInt(3, 1);
                        stm.setInt(4, 1);
                    } else if (nameStatus.equals("Interviewing")) {
                        sql += "app.student_Confirm = ? and app.company_Confirm = ? and "
                                + " app.school_Confirm = ? ";
                        stm = con.prepareStatement(sql);
                        stm.setString(1, student.getStudentCode());
                        stm.setBoolean(2, true);
                        stm.setInt(3, 2);
                        stm.setInt(4, 1);
                    } else if (nameStatus.equals("Failed")) {
                        sql += "app.student_Confirm = ? and app.company_Confirm = ? and "
                                + " app.school_Confirm = ? ";
                        stm = con.prepareStatement(sql);
                        stm.setString(1, student.getStudentCode());
                        stm.setBoolean(2, true);
                        stm.setInt(3, -2);
                        stm.setInt(4, 1);
                    } else if (nameStatus.equals("Canceled")) {
                        sql += "app.student_Confirm = ? ";
                        stm = con.prepareStatement(sql);
                        stm.setString(1, student.getStudentCode());
                        stm.setBoolean(2, false);
                    }
                }

                if ("".equals(companyName) == true && "".equals(nameTypeJob) == true
                        && nameLocation.trim().isEmpty() == false && "".equals(nameStatus) == true) {
                    sql += "WHERE app.studentCode = ? and cp.workLocation LIKE ? ";
                    stm = con.prepareStatement(sql);
                    stm.setString(1, student.getStudentCode());
                    stm.setNString(2, "%" + nameLocation + "%");
                }

                if ("".equals(companyName) == true && nameTypeJob.trim().isEmpty() == false
                        && "".equals(nameLocation) == true && "".equals(nameStatus) == true) {
                    sql += "WHERE app.studentCode = ? and cp.title_Post LIKE ? ";
                    stm = con.prepareStatement(sql);
                    stm.setString(1, student.getStudentCode());
                    stm.setNString(2, "%" + nameTypeJob + "%");
                }

                if (companyName.trim().isEmpty() == false && "".equals(nameTypeJob) == true
                        && "".equals(nameLocation) == true && "".equals(nameStatus) == true) {
                    sql += "WHERE app.studentCode = ? and ac.name LIKE ? ";
                    stm = con.prepareStatement(sql);
                    stm.setString(1, student.getStudentCode());
                    stm.setNString(2, "%" + companyName + "%");
                }
                rs = stm.executeQuery();
                while (rs.next()) {
                    int schoolConfirm = rs.getInt("school_Confirm");
                    int companyConfirm = rs.getInt("company_Confirm");
                    boolean studentConfirm = rs.getBoolean("student_Confirm");
                    int applicationID = rs.getInt("applicationID");
                    String tiltlePost = rs.getNString("title_Post");
                    String workLocation = rs.getNString("workLocation");
                    Date expirationDate = rs.getDate("expirationDate");
                    int postID = rs.getInt("postID");
                    String nameCompany = rs.getNString("name");

                    if (nameStatus.equals("Waiting") && schoolConfirm == 0 && companyConfirm == -1) {
                        continue;
                    }

                    TblApplicationDTO application = new TblApplicationDTO();
                    application.setApplicationID(applicationID);
                    application.setCompanyConfirm(companyConfirm);
                    application.setStudentConfirm(studentConfirm);
                    application.setSchoolConfirm(schoolConfirm);

                    TblCompany_PostDTO companyPost = new TblCompany_PostDTO();
                    companyPost.setTitle_Post(tiltlePost);
                    companyPost.setExpirationDate(expirationDate);
                    companyPost.setWorkLocation(workLocation);
                    companyPost.setPostID(postID);

                    TblAccountDTO account = new TblAccountDTO();
                    account.setName(nameCompany);

                    TblCompanyDTO company = new TblCompanyDTO();
                    company.setAccount(account);

                    companyPost.setCompany(company);
                    application.setCompanyPost(companyPost);

                    if (listApplicationByFilter == null) {
                        listApplicationByFilter = new ArrayList<>();
                    }
                    listApplicationByFilter.add(application);
                }
                return listApplicationByFilter;
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
        return listApplicationByFilter;
    }

    public boolean deleteApplicationOfStudent(String studentCode)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "DELETE FROM tblApplication "
                        + "WHERE studentCode = ?";
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

    // nơi dùng: AdminShowInternApplicationServlet
    public List<TblApplicationDTO> getApplicationByFilterInAdminIternAppl(String studentID,
            String companyID, String titleJob, String schoolStatus, int semesterID)
            throws SQLException, NamingException, NumberFormatException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        List<TblApplicationDTO> listApplicationByFilter = null;
        int iSchoolStatus = 7; // gắn giá trị khác status(-1:Waiting, 0:Denied, 1:Accept) là đc  

        if (studentID == null) {
            studentID = "";
        } else {
            studentID = studentID.trim();
        }
        if (companyID == null) {
            companyID = "";
        }
        if (titleJob == null) {
            titleJob = "";
        } else {
            titleJob = titleJob.trim();
        }
        if (schoolStatus != null && !"".equals(schoolStatus)) {
            iSchoolStatus = Integer.parseInt(schoolStatus);
        }

        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT app.studentCode, app.student_Confirm, app.school_Confirm, app.company_Confirm, "
                        + "acc.name, post.title_Post, app.applicationID "
                        + "FROM tblApplication app JOIN tblCompany_Post post on app.postID = post.postID "
                        + "JOIN tblCompany com on com.companyID = post.companyID "
                        + "JOIN tblAccount acc on acc.username = com.username "
                        + "WHERE app.studentCode Like ? and com.companyID Like ?  and post.title_Post Like ? and app.semesterID = ? ";
                if (iSchoolStatus != 7) {
                    sql += "and app.school_Confirm Like ? ";
                }
                stm = con.prepareStatement(sql);
                stm.setString(1, "%" + studentID + "%");
                stm.setNString(2, "%" + companyID + "%");
                stm.setNString(3, "%" + titleJob + "%");
                stm.setInt(4, semesterID);
                if (iSchoolStatus != 7) {
                    stm.setInt(5, iSchoolStatus);
                }
                rs = stm.executeQuery();
                int nu = 0;
                while (rs.next()) {
                    String stuID = rs.getString("studentCode");
                    String nameCompany = rs.getNString("name");
                    String titlePost = rs.getNString("title_Post");
                    boolean studentConfirm = rs.getBoolean("student_Confirm");
                    int companyConfirm = rs.getInt("company_Confirm");
                    int schoolConfirm = rs.getInt("school_Confirm");
                    int applID = rs.getInt("applicationID");

                    TblStudentDTO student = new TblStudentDTO();
                    student.setStudentCode(stuID);

                    TblAccountDTO account = new TblAccountDTO();
                    account.setName(nameCompany);

                    TblCompanyDTO company = new TblCompanyDTO();
                    company.setAccount(account);

                    TblCompany_PostDTO post = new TblCompany_PostDTO();
                    post.setCompany(company);
                    post.setTitle_Post(titlePost);

                    TblApplicationDTO application = new TblApplicationDTO();
                    application.setApplicationID(applID);
                    application.setStudent(student);
                    application.setCompanyPost(post);
                    application.setStudentConfirm(studentConfirm);
                    application.setCompanyConfirm(companyConfirm);
                    application.setSchoolConfirm(schoolConfirm);

                    if (listApplicationByFilter == null) {
                        listApplicationByFilter = new ArrayList<>();
                    }
                    listApplicationByFilter.add(application);
                }
                return listApplicationByFilter;
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

    public boolean changeStatusSchool(int applID, int schoolStatus) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "UPDATE tblApplication "
                        + "SET school_confirm = ? "
                        + "WHERE applicationID = ?";
                stm = con.prepareStatement(sql);
                stm.setInt(1, schoolStatus);
                stm.setInt(2, applID);
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

    public List<String> getAttachmentOfStudent(String studentCode)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        List<String> listAttachment = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT attachmentPath "
                        + "FROM tblApplication "
                        + "WHERE studentCode = ? ";
                stm = con.prepareStatement(sql);
                stm.setString(1, studentCode);

                rs = stm.executeQuery();
                while (rs.next()) {
                    String attachmentPath = rs.getString("attachmentPath");
                    if (listAttachment == null) {
                        listAttachment = new ArrayList<>();
                    }
                    listAttachment.add(attachmentPath);
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
        return listAttachment;
    }

    public List<TblApplicationDTO> getListStudentApplications(int currentSemesterID) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        List<TblApplicationDTO> listApplication = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT applicationID, attachmentPath, expected_Job, "
                        + "technology, experience, foreign_Language, otherSkills, "
                        + "evaluation, grade, is_Pass, student_Confirm, "
                        + "school_Confirm, company_Confirm, student.studentCode, postID "
                        + "FROM tblApplication AS app "
                        + "INNER JOIN tblStudent AS student "
                        + "ON (app.studentCode = student.studentCode) "
                        + "WHERE student_Confirm = ? "
                        + "and school_Confirm = ? and company_Confirm = ? and app.semesterID = ? "
                        + "ORDER BY grade DESC";
                stm = con.prepareStatement(sql);
                stm.setBoolean(1, true);
                stm.setInt(2, 1);
                stm.setInt(3, 1);
                stm.setInt(4, currentSemesterID);

                rs = stm.executeQuery();

                while (rs.next()) {
                    int applicationID = rs.getInt("applicationID");
                    String attachmentPath = rs.getString("attachmentPath");
                    String expected_Job = rs.getNString("expected_Job");
                    String technology = rs.getNString("technology");
                    String experience = rs.getNString("experience");
                    String foreign_Language = rs.getNString("foreign_Language");
                    String otherSkills = rs.getNString("otherSkills");
                    String evaluation = rs.getNString("evaluation");
                    float grade = rs.getFloat("grade");
                    int is_Pass = rs.getInt("is_Pass");
                    boolean student_Confirm = rs.getBoolean("student_Confirm");
                    int school_Confirm = rs.getInt("school_Confirm");
                    int company_Confirm = rs.getInt("company_Confirm");
                    String studentCode = rs.getString("studentCode");
                    int postID = rs.getInt("postID");

                    if (grade >= 0 && evaluation != null) {
                        TblApplicationDTO application = new TblApplicationDTO();
                        application.setApplicationID(applicationID);
                        application.setAttachmentPath(attachmentPath);
                        application.setCompanyConfirm(company_Confirm);
                        application.setEvaluation(evaluation);
                        application.setExpected_job(expected_Job);
                        application.setExperience(experience);
                        application.setForeign_Language(foreign_Language);
                        application.setGrade(grade);
                        application.setIsPass(is_Pass);
                        application.setOtherSkills(otherSkills);
                        application.setSchoolConfirm(school_Confirm);
                        application.setStudentConfirm(student_Confirm);
                        application.setTechnology(technology);

                        TblStudentDAO studentDAO = new TblStudentDAO();
                        TblStudentDTO student = studentDAO.getStudentInfor(studentCode);
                        application.setStudent(student);

                        TblCompany_PostDAO companyPostDAO = new TblCompany_PostDAO();
                        TblCompany_PostDTO companyPost = companyPostDAO.getCompanyPost(postID);
                        application.setCompanyPost(companyPost);

                        if (listApplication == null) {
                            listApplication = new ArrayList<>();
                        }

                        listApplication.add(application);
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
        return listApplication;
    }

    public List<TblApplicationDTO> searchListStudentApplicationByFilter(int currentSemesterID,
            float grade, String studentCode, String companyID, String isPass)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        List<TblApplicationDTO> listApplication = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT applicationID, attachmentPath, expected_Job, "
                        + "technology, experience, foreign_Language, otherSkills, "
                        + "evaluation, grade, is_Pass, student_Confirm, student.is_Disabled, "
                        + "app.school_Confirm, company_Confirm, student.studentCode, app.postID, post.companyID "
                        + "FROM tblApplication AS app "
                        + "INNER JOIN tblStudent AS student "
                        + "ON (app.studentCode = student.studentCode) "
                        + "INNER JOIN tblCompany_Post AS post "
                        + "ON (app.postID = post.postID) ";

                if (grade != -1 && "".equals(studentCode) == false
                        && "".equals(companyID) == false && "".equals(isPass) == false) {
                    sql += "WHERE student_Confirm = ? "
                            + "and app.school_Confirm = ? and company_Confirm = ? and app.semesterID = ? "
                            + "and student.studentCode like ? and grade = ? and is_Pass = ? and post.companyID = ? ";

                    stm = con.prepareStatement(sql);
                    stm.setBoolean(1, true);
                    stm.setInt(2, 1);
                    stm.setInt(3, 1);
                    stm.setInt(4, currentSemesterID);
                    stm.setString(5, "%" + studentCode + "%");
                    stm.setFloat(6, grade);
                    if (isPass.equals("true")) {
                        stm.setInt(7, 1);
                    } else {
                        stm.setInt(7, -1);
                    }
                    stm.setString(8, companyID);
                }

                if (grade == -1 && "".equals(studentCode) == false
                        && "".equals(companyID) == false && "".equals(isPass) == false) {
                    sql += "WHERE student_Confirm = ? "
                            + "and app.school_Confirm = ? and company_Confirm = ? and app.semesterID = ? "
                            + "and student.studentCode like ? and is_Pass = ? and post.companyID = ? ";
                    stm = con.prepareStatement(sql);
                    stm.setBoolean(1, true);
                    stm.setInt(2, 1);
                    stm.setInt(3, 1);
                    stm.setInt(4, currentSemesterID);
                    stm.setString(5, "%" + studentCode + "%");
                    if (isPass.equals("true")) {
                        stm.setInt(6, 1);
                    } else {
                        stm.setInt(6, -1);
                    }
                    stm.setString(7, companyID);
                }

                if (grade != -1 && "".equals(studentCode) == true
                        && "".equals(companyID) == false && "".equals(isPass) == false) {
                    sql += "WHERE student_Confirm = ? "
                            + "and app.school_Confirm = ? and company_Confirm = ? and app.semesterID = ? "
                            + " and grade = ? and is_Pass = ? and post.companyID = ? ";
                    stm = con.prepareStatement(sql);
                    stm.setBoolean(1, true);
                    stm.setInt(2, 1);
                    stm.setInt(3, 1);
                    stm.setInt(4, currentSemesterID);
                    stm.setFloat(5, grade);
                    if (isPass.equals("true")) {
                        stm.setInt(6, 1);
                    } else {
                        stm.setInt(6, -1);
                    }
                    stm.setString(7, companyID);
                }

                if (grade != -1 && "".equals(studentCode) == false
                        && "".equals(companyID) == true && "".equals(isPass) == false) {
                    sql += "WHERE student_Confirm = ? "
                            + "and app.school_Confirm = ? and company_Confirm = ? and app.semesterID = ? "
                            + "and student.studentCode like ? and grade = ? and is_Pass = ? ";
                    stm = con.prepareStatement(sql);
                    stm.setBoolean(1, true);
                    stm.setInt(2, 1);
                    stm.setInt(3, 1);
                    stm.setInt(4, currentSemesterID);
                    stm.setString(5, "%" + studentCode + "%");
                    stm.setFloat(6, grade);
                    if (isPass.equals("true")) {
                        stm.setInt(7, 1);
                    } else {
                        stm.setInt(7, -1);
                    }
                }

                if (grade != -1 && "".equals(studentCode) == false
                        && "".equals(companyID) == false && "".equals(isPass) == true) {
                    sql += "WHERE student_Confirm = ? "
                            + "and app.school_Confirm = ? and company_Confirm = ? and app.semesterID = ? "
                            + "and student.studentCode like ? and grade = ? and post.companyID = ? ";
                    stm = con.prepareStatement(sql);
                    stm.setBoolean(1, true);
                    stm.setInt(2, 1);
                    stm.setInt(3, 1);
                    stm.setInt(4, currentSemesterID);
                    stm.setString(5, "%" + studentCode + "%");
                    stm.setFloat(6, grade);
                    stm.setString(7, companyID);
                }

                if (grade == -1 && "".equals(studentCode) == true
                        && "".equals(companyID) == false && "".equals(isPass) == false) {
                    sql += "WHERE student_Confirm = ? "
                            + "and app.school_Confirm = ? and company_Confirm = ? and app.semesterID = ? "
                            + " and is_Pass = ? and post.companyID = ? ";
                    stm = con.prepareStatement(sql);
                    stm.setBoolean(1, true);
                    stm.setInt(2, 1);
                    stm.setInt(3, 1);
                    stm.setInt(4, currentSemesterID);
                    if (isPass.equals("true")) {
                        stm.setInt(5, 1);
                    } else {
                        stm.setInt(5, -1);
                    }
                    stm.setString(6, companyID);
                }

                if (grade == -1 && "".equals(studentCode) == false
                        && "".equals(companyID) == true && "".equals(isPass) == false) {
                    sql += "WHERE student_Confirm = ? "
                            + "and app.school_Confirm = ? and company_Confirm = ? and app.semesterID = ? "
                            + "and student.studentCode like ? and is_Pass = ? ";
                    stm = con.prepareStatement(sql);
                    stm.setBoolean(1, true);
                    stm.setInt(2, 1);
                    stm.setInt(3, 1);
                    stm.setInt(4, currentSemesterID);
                    stm.setString(5, "%" + studentCode + "%");
                    if (isPass.equals("true")) {
                        stm.setInt(6, 1);
                    } else {
                        stm.setInt(6, -1);
                    }
                }

                if (grade == -1 && "".equals(studentCode) == false
                        && "".equals(companyID) == false && "".equals(isPass) == true) {
                    sql += "WHERE student_Confirm = ? "
                            + "and app.school_Confirm = ? and company_Confirm = ? and app.semesterID = ? "
                            + "and student.studentCode like ? and post.companyID = ? ";
                    stm = con.prepareStatement(sql);
                    stm.setBoolean(1, true);
                    stm.setInt(2, 1);
                    stm.setInt(3, 1);
                    stm.setInt(4, currentSemesterID);
                    stm.setString(5, "%" + studentCode + "%");
                    stm.setString(6, companyID);
                }

                if (grade != -1 && "".equals(studentCode) == true
                        && "".equals(companyID) == true && "".equals(isPass) == false) {
                    sql += "WHERE student_Confirm = ? "
                            + "and app.school_Confirm = ? and company_Confirm = ? and app.semesterID = ? "
                            + "and grade = ? and is_Pass = ? ";
                    stm = con.prepareStatement(sql);
                    stm.setBoolean(1, true);
                    stm.setInt(2, 1);
                    stm.setInt(3, 1);
                    stm.setInt(4, currentSemesterID);
                    stm.setFloat(5, grade);
                    if (isPass.equals("true")) {
                        stm.setInt(6, 1);
                    } else {
                        stm.setInt(6, -1);
                    }
                }

                if (grade != -1 && "".equals(studentCode) == true
                        && "".equals(companyID) == false && "".equals(isPass) == true) {
                    sql += "WHERE student_Confirm = ? "
                            + "and app.school_Confirm = ? and company_Confirm = ? and app.semesterID = ? "
                            + " and grade = ? and post.companyID = ? ";
                    stm = con.prepareStatement(sql);
                    stm.setBoolean(1, true);
                    stm.setInt(2, 1);
                    stm.setInt(3, 1);
                    stm.setInt(4, currentSemesterID);
                    stm.setFloat(5, grade);
                    stm.setString(6, companyID);
                }

                if (grade != -1 && "".equals(studentCode) == false
                        && "".equals(companyID) == true && "".equals(isPass) == true) {
                    sql += "WHERE student_Confirm = ? "
                            + "and app.school_Confirm = ? and company_Confirm = ? and app.semesterID = ? "
                            + "and student.studentCode like ? and grade = ? ";
                    stm = con.prepareStatement(sql);
                    stm.setBoolean(1, true);
                    stm.setInt(2, 1);
                    stm.setInt(3, 1);
                    stm.setInt(4, currentSemesterID);
                    stm.setString(5, "%" + studentCode + "%");
                    stm.setFloat(6, grade);
                }

                if (grade == -1 && "".equals(studentCode) == true
                        && "".equals(companyID) == true && "".equals(isPass) == false) {
                    sql += "WHERE student_Confirm = ? "
                            + "and app.school_Confirm = ? and company_Confirm = ? and app.semesterID = ? "
                            + "and is_Pass = ? ";
                    stm = con.prepareStatement(sql);
                    stm.setBoolean(1, true);
                    stm.setInt(2, 1);
                    stm.setInt(3, 1);
                    stm.setInt(4, currentSemesterID);
                    if (isPass.equals("true")) {
                        stm.setInt(5, 1);
                    } else {
                        stm.setInt(5, -1);
                    }
                }

                if (grade == -1 && "".equals(studentCode) == true
                        && "".equals(companyID) == false && "".equals(isPass) == true) {
                    sql += "WHERE student_Confirm = ? "
                            + "and app.school_Confirm = ? and company_Confirm = ? and app.semesterID = ? "
                            + " and post.companyID = ? ";
                    stm = con.prepareStatement(sql);
                    stm.setBoolean(1, true);
                    stm.setInt(2, 1);
                    stm.setInt(3, 1);
                    stm.setInt(4, currentSemesterID);
                    stm.setString(5, companyID);
                }

                if (grade == -1 && "".equals(studentCode) == false
                        && "".equals(companyID) == true && "".equals(isPass) == true) {
                    sql += "WHERE student_Confirm = ? "
                            + "and app.school_Confirm = ? and company_Confirm = ? and app.semesterID = ? "
                            + "and student.studentCode like ? ";
                    stm = con.prepareStatement(sql);
                    stm.setBoolean(1, true);
                    stm.setInt(2, 1);
                    stm.setInt(3, 1);
                    stm.setInt(4, currentSemesterID);
                    stm.setString(5, "%" + studentCode + "%");
                }

                if (grade != -1 && "".equals(studentCode) == true
                        && "".equals(companyID) == true && "".equals(isPass) == true) {
                    sql += "WHERE student_Confirm = ? "
                            + "and app.school_Confirm = ? and company_Confirm = ? and app.semesterID = ? "
                            + " and grade = ? ";
                    stm = con.prepareStatement(sql);
                    stm.setBoolean(1, true);
                    stm.setInt(2, 1);
                    stm.setInt(3, 1);
                    stm.setInt(4, currentSemesterID);
                    stm.setFloat(5, grade);
                }

                rs = stm.executeQuery();
                while (rs.next()) {
                    int applicationID = rs.getInt("applicationID");
                    String attachmentPath = rs.getString("attachmentPath");
                    String expected_Job = rs.getNString("expected_Job");
                    String technology = rs.getNString("technology");
                    String experience = rs.getNString("experience");
                    String foreign_Language = rs.getNString("foreign_Language");
                    String otherSkills = rs.getNString("otherSkills");
                    String evaluation = rs.getNString("evaluation");
                    float gradeApplication = rs.getFloat("grade");
                    int is_Pass = rs.getInt("is_Pass");
                    boolean student_Confirm = rs.getBoolean("student_Confirm");
                    int school_Confirm = rs.getInt("school_Confirm");
                    int company_Confirm = rs.getInt("company_Confirm");
                    String student_Code = rs.getString("studentCode");
                    int postID = rs.getInt("postID");
                    String company_ID = rs.getString("companyID");

                    TblApplicationDTO application = new TblApplicationDTO();
                    application.setApplicationID(applicationID);
                    application.setAttachmentPath(attachmentPath);
                    application.setCompanyConfirm(company_Confirm);
                    application.setEvaluation(evaluation);
                    application.setExpected_job(expected_Job);
                    application.setExperience(experience);
                    application.setForeign_Language(foreign_Language);
                    application.setGrade(gradeApplication);
                    application.setIsPass(is_Pass);
                    application.setOtherSkills(otherSkills);
                    application.setSchoolConfirm(school_Confirm);
                    application.setStudentConfirm(student_Confirm);
                    application.setTechnology(technology);

                    TblStudentDAO studentDAO = new TblStudentDAO();
                    TblStudentDTO student = studentDAO.getStudentInfor(student_Code);
                    application.setStudent(student);
                    if(student.getIsIntern() != 2){
                        continue;
                    }
                    TblCompanyDAO companyDAO = new TblCompanyDAO();
                    TblCompanyDTO company = companyDAO.getCompany(company_ID);

                    TblCompany_PostDAO companyPostDAO = new TblCompany_PostDAO();
                    TblCompany_PostDTO companyPost = companyPostDAO.getCompanyPost(postID);
                    companyPost.setCompany(company);
                    application.setCompanyPost(companyPost);

                    if (listApplication == null) {
                        listApplication = new ArrayList<>();
                    }

                    listApplication.add(application);
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
        return listApplication;
    }

    //get Application of Company by email
    public List<TblApplicationDTO> getApplicationByEmail(String email, int companyConfirm, int currentSemester) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        List<TblApplicationDTO> listApplication = null;
        try {
            con = DBHelper.makeConnection();
            String sql = "SELECT app.attachmentPath, app.expected_Job, app.technology, app.experience, app.foreign_Language, app.otherSkills, "
                    + "app.evaluation,app.grade,app.studentCode,app.school_Confirm, "
                    + "app.student_Confirm, app.company_Confirm, app.applicationID, app.is_Pass, "
                    + "cp.title_Post, cp.postID,ac.name "
                    + "FROM tblApplication AS app "
                    + "INNER JOIN tblCompany_Post as cp ON (app.postID = cp.postID) "
                    + "INNER JOIN tblCompany as com ON (cp.companyID = com.companyID) "
                    + "INNER JOIN tblAccount as ac ON (com.username = ac.username) "
                    + "Where ac.username = ? AND app.student_Confirm = ? AND app.school_Confirm = ? AND semesterID = ? ";
            if (companyConfirm == -3) {
                stm = con.prepareStatement(sql);
                stm.setString(1, email);
                stm.setBoolean(2, true);
                stm.setInt(3, 1);
                stm.setInt(4, currentSemester);
            } else {
                sql += "AND app.company_Confirm = ? ";
                stm = con.prepareStatement(sql);
                stm.setString(1, email);
                stm.setBoolean(2, true);
                stm.setInt(3, 1);
                stm.setInt(4, currentSemester);
                stm.setInt(5, companyConfirm);
            }
            rs = stm.executeQuery();

            int check_company_confirm = 1;
            while (rs.next()) {

                String attachmentPath = rs.getString("attachmentPath");
                String expected_job = rs.getString("expected_Job");
                String technology = rs.getString("technology");
                String experience = rs.getString("experience");
                String foreign_Language = rs.getString("foreign_Language");
                String otherSkills = rs.getString("otherSkills");
                String evaluation = rs.getString("evaluation");
                float grade = rs.getFloat("grade");

                String studentCode = rs.getString("studentCode");
                int company_confirm = rs.getInt("company_Confirm");
                int applicationID = rs.getInt("applicationID");
                String title_Post = rs.getString("title_Post");
                int postID = rs.getInt("postID");
                String companyName = rs.getString("name");
                int isPass = rs.getInt("is_Pass");

                //get Student
                TblStudentDAO studentDAO = new TblStudentDAO();
                TblStudentDTO studentDTO = studentDAO.getStudentInformation(studentCode);
                //get Company post
                TblCompany_PostDAO company_postDAO = new TblCompany_PostDAO();
                TblCompany_PostDTO company_postDTO = company_postDAO.getCompanyPost(postID);

                TblApplicationDTO applicationDTO = new TblApplicationDTO();
                applicationDTO.setApplicationID(applicationID);
                applicationDTO.setAttachmentPath(attachmentPath);
                applicationDTO.setExpected_job(expected_job);
                applicationDTO.setTechnology(technology);
                applicationDTO.setExperience(experience);
                applicationDTO.setForeign_Language(foreign_Language);
                applicationDTO.setOtherSkills(otherSkills);
                applicationDTO.setEvaluation(evaluation);
                applicationDTO.setGrade(grade);
                applicationDTO.setIsPass(isPass);
                applicationDTO.setCompanyConfirm(company_confirm);
                applicationDTO.setStudent(studentDTO);
                applicationDTO.setCompanyPost(company_postDTO);
                if (listApplication == null) {
                    listApplication = new ArrayList<>();
                }

                listApplication.add(applicationDTO);
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
        return listApplication;
    }

    public boolean updateStatusCompanyConfirm(String studentCode, int companyPostID, int companyConfirm) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBHelper.makeConnection();
            String sql = "UPDATE tblApplication "
                    + "SET company_Confirm = ? "
                    + "WHERE studentCode = ? AND postID = ? AND company_Confirm = ? ";
            stm = con.prepareStatement(sql);
            stm.setInt(1, companyConfirm);
            stm.setString(2, studentCode);
            stm.setInt(3, companyPostID);
            if (companyConfirm == 1 || companyConfirm == -1) {
                stm.setInt(4, 2);
            } else if (companyConfirm == 2 || companyConfirm == -2) {
                stm.setInt(4, 0);
            }

            int effectRows = stm.executeUpdate();
            if (effectRows > 0) {
                return true;
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

    public boolean updateGradeAndEvaluation(String studentCode, int postID, float grade, String evaluation, int is_Pass) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "UPDATE tblApplication ";
                if (evaluation.isEmpty()) {
                    sql += "SET grade = ?, is_Pass = ? "
                            + "WHERE studentCode = ? AND postID = ? AND company_Confirm = ? ";
                    stm = con.prepareStatement(sql);
                    stm.setFloat(1, grade);
                    stm.setInt(2, is_Pass);
                    stm.setString(3, studentCode);
                    stm.setInt(4, postID);
                    stm.setInt(5, 1);

                } else {
                    sql += "SET grade = ?, is_Pass = ?, evaluation = ? "
                            + "WHERE studentCode = ? AND postID = ? AND company_Confirm = ? ";
                    stm = con.prepareStatement(sql);
                    stm.setFloat(1, grade);
                    stm.setInt(2, is_Pass);
                    stm.setString(3, evaluation);
                    stm.setString(4, studentCode);
                    stm.setInt(5, postID);
                    stm.setInt(6, 1);
                }
                int effectRows = stm.executeUpdate();
                if (effectRows > 0) {
                    return true;
                }
            }
        } finally {
            if (con != null) {
                con.close();
            }
        }
        return false;
    }

    public TblApplicationDTO getApplication(String studentCode,
            int postID, int semesterID)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT applicationID "
                        + "FROM tblApplication "
                        + "WHERE studentCode = ? and postID = ? and semesterID = ? ";
                stm = con.prepareStatement(sql);
                stm.setString(1, studentCode);
                stm.setInt(2, postID);
                stm.setInt(3, semesterID);

                rs = stm.executeQuery();

                if (rs.next()) {
                    int applicationID = rs.getInt("applicationID");
                    TblApplicationDTO application = getApplication(applicationID);
                    return application;
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

    public TblApplicationDTO getApplicationStudentWorking(String studentCode, int semesterID)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT applicationID "
                        + "FROM tblApplication "
                        + "WHERE studentCode = ? and semesterID = ? ";
                stm = con.prepareStatement(sql);
                stm.setString(1, studentCode);
                stm.setInt(2, semesterID);

                rs = stm.executeQuery();

                if (rs.next()) {
                    int applicationID = rs.getInt("applicationID");
                    TblApplicationDTO application = getApplication(applicationID);
                    if (application.isStudentConfirm() == true
                            && application.getSchoolConfirm() == 1
                            && application.getCompanyConfirm() == 1) {
                        return application;
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
        return null;
    }

    public void changeStudentConfirmStatus(List<TblApplicationDTO> listApplication)
            throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                for (TblApplicationDTO application : listApplication) {
                    String sql = "UPDATE tblApplication "
                            + "SET student_Confirm = ? "
                            + "WHERE applicationID = ?";
                    stm = con.prepareStatement(sql);
                    stm.setBoolean(1, false);
                    stm.setInt(2, application.getApplicationID());

                    int rows = stm.executeUpdate();
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
    }

    public List<TblApplicationDTO> getListApplicationOfCompany(String companyCode, int currentSemester)
            throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        List<TblApplicationDTO> listApp = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT applicationID "
                        + "FROM tblApplication AS app INNER JOIN TblCompany_Post as comPost "
                        + "ON (app.postID = comPost.postID)"
                        + "WHERE comPost.companyID = ? and semesterID = ? ";
                stm = con.prepareStatement(sql);
                stm.setString(1, companyCode);
                stm.setInt(2, currentSemester);

                rs = stm.executeQuery();
                while (rs.next()) {
                    int applicationID = rs.getInt("applicationID");
                    TblApplicationDTO application = getApplication(applicationID);
                    if (listApp == null) {
                        listApp = new ArrayList<>();
                    }
                    listApp.add(application);
                }
                if (listApp != null) {
                    if (listApp.isEmpty()) {
                        listApp = null;
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
        return listApp;
    }

}
